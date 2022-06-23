package it.polimi.ingsw.View.CLI;

import it.polimi.ingsw.Network.ClientSide.ClientController;
import it.polimi.ingsw.Model.CharacterCards.CharacterCardsName;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.Network.Messages.NetworkMessages.ServiceMessage;
import it.polimi.ingsw.Observer.ViewObserver;
import it.polimi.ingsw.Observer.Subjects.ViewSubject;
import it.polimi.ingsw.View.GUI.GUIDrawer;
import it.polimi.ingsw.View.View;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.*;
//import org.fusesource.jansi.AnsiConsole;


/**
 * this class implements the command line interface to play trough terminal,
 * it's observed by the ClientController which sends messages to the server according to the CLI updates,
 * and it also receives update messages from the virtual view to manage the interactions with the player properly.
 */

public class CLI extends ViewSubject implements View {

    /**
     * Drawer of the CLI, draws the game table and other things.
     */
    private final CLIDrawer cliDrawer;

    // Read input utilities

    private final BufferedReader br;

    private final Callable<String> readTask;

    private final ExecutorService taskQueue;

    private Future<String> asyncRead;

    /**
     * Constants used for a better readability: they determine if it is necessary to change execution flow or not
     */
    private static final int CONTINUE_TASK = 200;
    private static final int RESTART_TASK = 400;
    private static final int DELETE_TASK = 401;

    /**
     * Integer that contains the value of the current execution flow
     */
    private int statusFlow = 200;

    /**
     * Boolean that is true if we are playing in a expert mode game
     */
    private boolean expertMode;

    /**
     * Boolean that is true if the askCharacterCard method has been called,
     * it prevents the possibility that a character card could be played while another one is going to be activated
     */
    private boolean characterActivated = false;

    /**
     * Constructor of the CLI.
     */
    public CLI() {
        cliDrawer = new CLIDrawer();
        br = new BufferedReader(new InputStreamReader(System.in));
        taskQueue = Executors.newSingleThreadExecutor();
        readTask = () -> { String input; input = br.readLine(); return input; };
    }

    /**
     * Launches a thread for user input reading.
     */
    public String readUserInput() throws InterruptedException, ExecutionException {
        asyncRead = taskQueue.submit(readTask);
        return asyncRead.get();
    }

    /**
     * Starts the CLI.
     */
    public void ViewStart(){
        //AnsiConsole.systemInstall();
        System.out.println(cliDrawer.printTitle());
        askUsername();
    }

    /**
     * Adds an observer to the view (the CLI itself).
     * @param clientController the observer that observe the view.
     */
    public void addObs(ClientController clientController) { addObserver(clientController); }

    /**
     * Prints messages on screen.
     * @param message the message that will be printed.
     */
    public void printMessage(ServiceMessage message) { System.out.println(message.getMessage()); }

    /**
     * Each time the player does an action that modifies the game table, then this method will be called,
     * and then it will be printed again the game table with the ultimate changes.
     */
    public void printChanges() { System.out.println(cliDrawer.printGameTable()); }

    /**
     * Prints end game message
     * @param winner is the name of the winner
     * @param winnerID is -1 if the game ended in a draw
     */
    public void printWinner(String winner, int winnerID) { cliDrawer.printWinner(winner, winnerID); }

    /**
     * Reads the username chosen by the player and notifies it to the view.
     */
    public void askUsername() {
        try {
            System.out.println("> What is your nickname? [NOTE: it must be between 2 and 20 characters long]");
            System.out.println("> ");
            String username = readUserInput();
            if (username.length() >= 2 && username.length() <= 20)
                notifyObserver(obs -> obs.onUsername(username));
            else
                askUsername();
        } catch (InterruptedException | ExecutionException ee) {
            asyncRead.cancel(true);
        }
    }

    /**
     * Notifies the view with the number of players ad the game mode the player decided to play with.
     */
    public void askGamePreferences() { notifyObserver(obs -> obs.onGamePreferences(askNumOfPlayers(), askGameMode())); }

    /**
     * Asks the player to insert the number of players he wants to play with.
     * Throws a NumberFormatException when inserted a wrong number format.
     * @return the number of players he wants to play with.
     */
    private int askNumOfPlayers() {
        try {
            int numPlayers;
            do{
                System.out.println("> How many players do you want to play with? [2, 3 or 4]");
                System.out.println("> ");
                numPlayers = Integer.parseInt(readUserInput());
            }while (numPlayers!=2 && numPlayers!=3 && numPlayers!=4);
            return numPlayers;
        } catch (NumberFormatException nf) {
            System.out.println("You didn't insert a suitable number! Please, try again...");
            return askNumOfPlayers();
        } catch (InterruptedException | ExecutionException ee) {
            asyncRead.cancel(true);
            return 0;
        }
    }

    /**
     * Asks the player the game mode he wants to play with.
     * If inserting a value that differs from "y" the game mode will be set automatically to the base one.
     * @return the game mode the player wants to play with.
     */
    private boolean askGameMode() {
        try {
            String modePreference;
            System.out.println("> Do you want to play in Expert mode? [y/n]");
            System.out.println("> ");
            modePreference = readUserInput();
            expertMode = modePreference.equalsIgnoreCase("y");
            return expertMode;
        } catch (InterruptedException | ExecutionException ee) {
            asyncRead.cancel(true);
            return false;
        }
    }

    /**
     * Asks the player which assistant card he wants to play.
     * Notifies the choice to the ClientController.
     * @param playerID the id of the player who is playing the assistant card.
     */
    public void askAssistantCard(int playerID) {
        characterActivated = false;
        try {
            System.out.println(cliDrawer.printAssistantCards(playerID));
            System.out.println("Which Assistant Card you want to play? Insert the turn order of the Assistant Card you want to play (T)");
            String choice = readUserInput();
            checkInput(choice, true);
            if (statusFlow == CONTINUE_TASK) {
                int chosenCard = Integer.parseInt(choice);
                notifyObserver(obs -> obs.onAssistantCard(chosenCard));
            }
            else if (statusFlow == RESTART_TASK)
                askAssistantCard(playerID);
        } catch (NumberFormatException nf) {
            System.out.println("You didn't insert a suitable number! Please, try again...");
            askAssistantCard(playerID);
        } catch (InterruptedException | ExecutionException ee) {
            asyncRead.cancel(true);
        }
    }

    /**
     * Asks the player what does he want to do when the game is at the beginning of the action phase in its first step.
     */
    public void askMove() {
        characterActivated = false;
        try {
            askStudent("Entrance");
            if (statusFlow == CONTINUE_TASK) {
                String choice;
                do {
                    System.out.println("> What do you want to do now?");
                    System.out.println("> 0 - CHANGE COLOR");
                    System.out.println("> 1 - MOVE SELECTED STUDENT IN YOUR DINING ROOM");
                    System.out.println("> 2 - MOVE SELECTED STUDENT ON AN ISLE");
                    choice = readUserInput();
                    checkInput(choice, expertMode);
                } while (statusFlow == RESTART_TASK);
                if (statusFlow == CONTINUE_TASK) {
                    int chosenOption = Integer.parseInt(choice);
                    switch (chosenOption) {
                        case 0 -> askMove();
                        case 1 -> notifyObserver(ViewObserver::onStudentMovement_toDining);
                        case 2 -> askIsleMovement();
                        default -> {
                            System.out.println("Not acceptable value. Please, try again.");
                            askMove();
                        }
                    }
                }
            }
        } catch (NumberFormatException nf) {
            System.out.println("You didn't insert a suitable number! Please, try again...");
            askMove();
        } catch (InterruptedException | ExecutionException ee) {
            asyncRead.cancel(true);
        }
    }

    /**
     * Asks the player what does he want to do when the game is in the action phase in its second step.
     */
    public void askMNMovement() {
        characterActivated = false;
        try {
            int choice = askNumber("Which isle do you want to move Mother Nature to?", false, (cliDrawer.getStorage().getNumberOfIsles() - 1));
            if (statusFlow == CONTINUE_TASK)
                notifyObserver(obs -> obs.onMNMovement(choice));
        } catch (InterruptedException | ExecutionException ee) {
            asyncRead.cancel(true);
        }
    }

    /**
     * Asks the player what does he want to do when the game is in the action phase in its third step.
     */
    public void askCloud() {
        characterActivated = false;
        try {
            int choice = askNumber("Which cloud do you want to take students from?", false, cliDrawer.getStorage().getGameTable().getNumOfClouds());
            if (statusFlow == CONTINUE_TASK)
                notifyObserver(obs -> obs.onCloudChoice(choice));
        } catch (InterruptedException | ExecutionException ee) {
            asyncRead.cancel(true);
        }
    }

    /**
     * Asks the player certain parameters for the correct activation of the effect of the character cards.
     * @param characterName the name of the character card played.
     */
    public void askCharacterEffectParameters(CharacterCardsName characterName) {
        try {
            System.out.println("> You activated the " + characterName.toString() + "!");
            switch (characterName) {
                case FARMER, MAGICAL_LETTER_CARRIER, CENTAUR, KNIGHT -> notifyObserver(obs -> obs.onAtomicEffect(-1));
                case MONK -> {
                    int chosenStudent = askNumber("Which student do you want to move from the character card?", true, 5);
                    notifyObserver(obs -> obs.onColorChoice(chosenStudent));
                    int chosenIsle = askNumber("Which isle do you want to move your student to? (Select between 0 and " + (cliDrawer.getStorage().getNumberOfIsles() - 1) + ")", false, (cliDrawer.getStorage().getNumberOfIsles() - 1));
                    notifyObserver(obs -> obs.onAtomicEffect(chosenIsle));
                }
                case HERALD -> {
                    int chosenIsle = askNumber("Which isle do you want to calculate influence on? (Select between 0 and " + (cliDrawer.getStorage().getNumberOfIsles() - 1) + ")", false, (cliDrawer.getStorage().getNumberOfIsles() - 1));
                    notifyObserver(obs -> obs.onAtomicEffect(chosenIsle));
                }
                case GRANDMA_HERBS -> {
                    int chosenIsle = askNumber("Which isle do you want to put a deny card on? (Select between 0 and " + (cliDrawer.getStorage().getNumberOfIsles() - 1) + ")", false, (cliDrawer.getStorage().getNumberOfIsles() - 1));
                    notifyObserver(obs -> obs.onAtomicEffect(chosenIsle));
                }
                case JESTER -> {
                    if (askExchange())
                        notifyObserver(ViewObserver::onEndCharacterPhase);
                    else {
                        int chosenCharacterStudent = askNumber("Which student do you want to move from the character card?", true, 5);
                        notifyObserver(obs -> obs.onColorChoice(chosenCharacterStudent));
                        int chosenEntranceStudent = askNumber("Which student do you want to move from your Entrance?", true, 5);
                        notifyObserver(obs -> obs.onAtomicEffect(chosenEntranceStudent));
                    }
                }
                case FUNGIST -> {
                    int chosenColor = askNumber("Which color you don't want to consider for influence calculation in this turn?", true, 5);
                    notifyObserver(obs -> obs.onAtomicEffect(chosenColor));
                }
                case MINSTREL -> {
                    if (askExchange())
                        notifyObserver(ViewObserver::onEndCharacterPhase);
                    else {
                        askStudent("Dining Room");
                        int chosenStudent = askNumber("Which student do you want to move from your Entrance?", true, 5);
                        notifyObserver(obs -> obs.onAtomicEffect(chosenStudent));
                    }
                }
                case SPOILED_PRINCESS -> {
                    int chosenStudent = askNumber(" Which student do you want to move from the character card to your Dining?", true, 5);
                    notifyObserver(obs -> obs.onAtomicEffect(chosenStudent));
                }
                case THIEF -> {
                    int chosenColor = askNumber("Which color you want to be removed from Dining Rooms?", true, 5);
                    notifyObserver(obs -> obs.onAtomicEffect(chosenColor));
                }
            }
        } catch (InterruptedException | ExecutionException ee) {
            asyncRead.cancel(true);
        }
    }

    /**
     * Asks if the player wants to exchange students.
     * @return true if the player wants to exchange students, else false.
     */
    private boolean askExchange() throws InterruptedException, ExecutionException {
        String exchange;
        System.out.println("> Do you want to exchange students? [y/n]");
        System.out.println("> ");
        exchange = readUserInput();
        return exchange.equalsIgnoreCase("n");
    }

    /**
     * Asks the player to write a number that will be used as color, isle id or character card index,
     * based on what the string request asks him.
     * @param request asks the player to insert a value basing on what is needed.
     * @param color true if the value required refers to a color, else false.
     * @param max maximum value of the choice.
     * @return the choice of the player.
     */
    private int askNumber(String request, boolean color, int max) throws InterruptedException, ExecutionException {
        try {
            System.out.println("> "+ request);
            if (color) {
                System.out.println("> 0 - YELLOW");
                System.out.println("> 1 - PINK");
                System.out.println("> 2 - BLUE");
                System.out.println("> 3 - RED");
                System.out.println("> 4 - GREEN");
                System.out.println("> ");
            }
            else
                System.out.println("> ");
            String choice = readUserInput();
            checkInput(choice, false);
            if (statusFlow == CONTINUE_TASK) {
                int chosenNumber = Integer.parseInt(choice);
                if (chosenNumber > max || chosenNumber < 0) {
                    System.out.println("You didn't insert a suitable number! Please, try again...");
                    return askNumber(request, color, max);
                }
                return chosenNumber;
            }
            else if (statusFlow == RESTART_TASK)
                return askNumber(request, color, max);
            else
                return -1;
        } catch (NumberFormatException nf) {
            System.out.println("You didn't insert a suitable number! Please, try again...");
            return askNumber(request, color, max);
        }
    }

    // SINGLE METHODS FOR ASKING REQUEST:

    /**
     * Asks the player which student he wants to move from a certain place.
     * Notifies the choice to the ClientController.
     * @param place the place from where thw student will be removed.
     */
    private void askStudent(String place) throws InterruptedException, ExecutionException {
        int choice = askNumber("Which student do you want to move from your "+ place + "?",true,5);
        if (statusFlow == CONTINUE_TASK)
            notifyObserver(obs -> obs.onColorChoice(choice));
    }

    /**
     * Asks the player in which isle he wants to move the students.
     * Notifies the choice to the ClientController.
     */
    private void askIsleMovement() throws InterruptedException, ExecutionException {
        int choice = askNumber("Which isle do you want to move your student to? (Select between 0 and " + (cliDrawer.getStorage().getNumberOfIsles() - 1) + ")",false,(cliDrawer.getStorage().getNumberOfIsles() - 1));
        if (statusFlow == CONTINUE_TASK)
            notifyObserver(obs -> obs.onStudentMovement_toIsle(choice));
    }

    /**
     * Asks the player which character card he wants to play.
     * Notifies the choice to the ClientController.
     */
    private void askCharacterCard() throws InterruptedException, ExecutionException {
        characterActivated = true;
        int choice = askNumber("Which character card do you want to activate?",false,2);
        notifyObserver(obs -> obs.onCharacterCard(choice));
    }

    /**
     * it checks if the user inserted one of the three special characters (L/C/A) and sets a variable that changes the execution flow of various instructions
     * @param userChoice is the input string
     * @param planning is true when we currently are in the Planning Phase,
 *                     and it is used in order to prevent the possibility to activate a character card during this phase
     * @throws ExecutionException when disconnect method has been called by the ClientController
     */
    private void checkInput(String userChoice, boolean planning) throws InterruptedException, ExecutionException {
        if (expertMode && !characterActivated) {
            if (userChoice.equalsIgnoreCase("l")) {
                System.out.println("> You are logging out... are you sure? [y/n]");
                System.out.println("> ");
                String answer = readUserInput();
                if (answer.equalsIgnoreCase("y")) {
                    notifyObserver(ViewObserver::onLogout);
                    statusFlow = DELETE_TASK;
                }
                else
                    statusFlow = RESTART_TASK;
            }
            else if (userChoice.equalsIgnoreCase("c")) {
                System.out.println(cliDrawer.drawCharacterCardsEffects());
                statusFlow = RESTART_TASK;
            }
            else if (userChoice.equalsIgnoreCase("a")) {
                if (planning) {
                    System.out.println("You are in the Assistant Card Phase. You can't play a character card now!");
                    statusFlow = RESTART_TASK;
                }
                else {
                    askCharacterCard();
                    statusFlow = DELETE_TASK;
                }
            }
            else
                statusFlow = CONTINUE_TASK;
        }
        else {
            if (userChoice.equalsIgnoreCase("l")) {
                System.out.println("> You are logging out... are you sure? [y/n]");
                System.out.println("> ");
                String answer = readUserInput();
                if (answer.equalsIgnoreCase("y")) {
                    notifyObserver(ViewObserver::onLogout);
                    statusFlow = DELETE_TASK;
                }
                else
                    statusFlow = RESTART_TASK;
            }
            else
                statusFlow = CONTINUE_TASK;
        }
    }

    /**
     * Handles the disconnection of the client by shutting down the thread for the input of the keyboard
     * and by printing why the client has to be disconnected.
     * @param message containing information about why the player is being disconnected that will be printed.
     */
    @Override
    public void disconnect(ServiceMessage message) {
        taskQueue.shutdownNow();
        if (message.getMessageType() != MessageType.END_GAME)
            System.out.println(message.getMessage());
        System.exit(1);
    }

    // GETTER:

    /**
     * Getter method for the CLIDrawer.
     * @return the CLIDrawer.
     */
    @Override
    public CLIDrawer getCLIDrawer() { return cliDrawer; }

    /**
     * Getter method for the GUIDrawer.
     * @return null because the CLI doesn't have a GUIDrawer.
     */
    @Override
    public GUIDrawer getGUIDrawer() { return null; }
}

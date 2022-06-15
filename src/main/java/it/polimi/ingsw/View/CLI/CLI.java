package it.polimi.ingsw.View.CLI;

import it.polimi.ingsw.Controller.ClientController;
import it.polimi.ingsw.Model.CharacterCards.CharacterCardsName;
import it.polimi.ingsw.Network.Messages.NetworkMessages.ServiceMessage;
import it.polimi.ingsw.Observer.ViewObserver;
import it.polimi.ingsw.Observer.ViewSubject;
import it.polimi.ingsw.View.GUI.GuiDrawer;
import it.polimi.ingsw.View.View;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
//import org.fusesource.jansi.AnsiConsole;


/**
 * this class implements the command line interface to play trough terminal,
 * it's observed by the ClientController which sends messages to the server according to the CLI updates,
 * and it also receives update messages from the virtual view to manage the interactions with the player properly.
 */

public class CLI extends ViewSubject implements View {

    Thread inputThread;
    /**
     * Drawer of the CLI, draws the game table and other things.
     */
    private final CLIDrawer cliDrawer;

    /**
     * Constructor of the CLI.
     */
    public CLI() { cliDrawer = new CLIDrawer(); }

    /**
     * Launches a thread for user input reading.
     */
    public String readUserInput() throws ExecutionException {
        FutureTask<String> asyncInput = new FutureTask<>(new InputReadCall());
        inputThread = new Thread(asyncInput);
        inputThread.start();
        String userInput = null;
        try {
             userInput = asyncInput.get();
        }
        catch (InterruptedException e){
            asyncInput.cancel(true);
            Thread.currentThread().interrupt();
        }
        return userInput;
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

    // at the end of the game -> cliDrawer.printWinner(winnerID); !!!!!!!!!!!!!

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
     * Prints all available character cards for a certain game with a brief description.
     */
    private void printAvailableCharacters() { System.out.println(cliDrawer.drawCharacterCardsEffects()); }

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
        } catch (ExecutionException ee) {
            System.out.println("Closing input read thread");
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
        } catch (ExecutionException ee) {
            System.out.println("Closing input read thread");
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
            boolean expertMode;
            System.out.println("> Do you want to play in Expert mode? [y/n]");
            System.out.println("> ");
            modePreference = readUserInput();
            expertMode = modePreference.equalsIgnoreCase("y");
            return expertMode;
        } catch (ExecutionException ee) {
            System.out.println("Closing input read thread");
            return false;
        }
    }

    /**
     * Asks the player what does he want to do when the game is at the beginning of the action phase in its first step.
     * @param expertMode true if expert game mode, else false.
     */
    public void askMove(boolean expertMode) {
        try {
            int initialChoice = 0;
            if (expertMode)
                initialChoice = askWhatToDo("SELECT A STUDENT TO MOVE", true);
            else
                askStudent("Entrance");
            System.out.println("> What do you want to do now?");
            System.out.println("> 0 - GO BACK TO THE PREVIOUS CHOICE");
            if (initialChoice == 0) {
                System.out.println("> 1 - MOVE SELECTED STUDENT IN YOUR DINING ROOM");
                System.out.println("> 2 - MOVE SELECTED STUDENT ON AN ISLE");
            }
            if (initialChoice == 1)
                System.out.println("> 1 - CHOOSE CHARACTER CARD TO ACTIVATE");
            switch (Integer.parseInt(readUserInput())) {
                case 0 -> askMove(expertMode);
                case 1 -> {
                    if (initialChoice == 0)
                        askDiningRoomMovement();
                    else if (initialChoice == 1)
                        askCharacterCard();
                    else
                        askMove(true);
                }
                case 2 -> {
                    if (initialChoice == 0)
                        askIsleMovement();
                    else {
                        System.out.println("Not acceptable value. Please, try again.");
                        askMove(true);
                    }
                }
                default -> {
                    System.out.println("Not acceptable value. Please, try again.");
                    askMove(expertMode);
                }
            }
        } catch (ExecutionException ee) {
            System.out.println("Closing input read thread");
        }
    }

    /**
     * Asks the player what does he want to do when the game is in the action phase in its second step.
     * @param expertMode true if expert game mode, else false.
     */
    public void askMNMovement(boolean expertMode) {
        try {
            if (expertMode) {
                int initialChoice = askWhatToDo("SELECT THE ISLE FOR MOTHER NATURE MOVEMENT", false);
                int choice;
                System.out.println("> What do you want to do now?");
                System.out.println("> 0 - GO BACK TO THE PREVIOUS CHOICE");
                if (initialChoice == 0)
                    System.out.println("> 1 - CHOOSE ISLE");
                if (initialChoice == 1)
                    System.out.println("> 1 - CHOOSE CHARACTER CARD TO ACTIVATE");
                choice = Integer.parseInt(readUserInput());
                switch (choice) {
                    case 0 -> askMNMovement(true);
                    case 1 -> {
                        if (initialChoice == 0)
                            askIsleMotherNature();
                        else if (initialChoice == 1)
                            askCharacterCard();
                        else
                            askMNMovement(true);
                    }
                    default -> {
                        System.out.println("Not acceptable value. Please, try again.");
                        askMNMovement(true);
                    }
                }
            } else
                askIsleMotherNature();
        } catch (ExecutionException ee) {
            System.out.println("Closing input read thread");
        }
    }

    /**
     * Asks the player what does he want to do when the game is in the action phase in its third step.
     * @param expertMode true if expert game mode, else false.
     */
    public void askCloud(boolean expertMode) {
        try {
            if (expertMode) {
                int initialChoice = askWhatToDo("SELECT THE CLOUD YOU WANT TO PICK STUDENTS FROM", false);
                int choice;
                System.out.println("> What do you want to do now?");
                System.out.println("> 0 - GO BACK TO THE PREVIOUS CHOICE");
                if (initialChoice == 0) {
                    System.out.println("> 1 - CHOOSE CLOUD");
                }
                if (initialChoice == 1)
                    System.out.println("> 1 - CHOOSE CHARACTER CARD TO ACTIVATE");
                choice = Integer.parseInt(readUserInput());
                switch (choice) {
                    case 0 -> askCloud(true);
                    case 1 -> {
                        if (initialChoice == 0)
                            askCloud();
                        else if (initialChoice == 1)
                            askCharacterCard();
                        else
                            askCloud(true);
                    }
                    default -> {
                        System.out.println("Not acceptable value. Please, try again.");
                        askCloud(true);
                    }
                }
            }
            else
                askCloud();
        } catch (NumberFormatException nf) {
            System.out.println("You didn't insert a suitable number! Please, try again...");
            askCloud(expertMode);
        } catch (ExecutionException ee) {
            System.out.println("Closing input read thread");
        }
    }

    /**
     * Asks the player certain parameters for the correct activation of the effect of the character cards.
     * @param characterName the name of the character card played.
     */
    public void askCharacterEffectParameters(CharacterCardsName characterName) {
        System.out.println("> You activated the " + characterName.toString() + "!");
        switch (characterName) {
            case FARMER, MAGICAL_LETTER_CARRIER, CENTAUR, KNIGHT -> notifyObserver(obs -> obs.onAtomicEffect(-1));
            case MONK -> {
                askMoveStudentFromCard();
                askIsleMovementMONK();
            }
            case HERALD -> askIsleInfluenceCount();
            case GRANDMA_HERBS -> askIsleDenyCard();
            case JESTER -> {
                if (askExchange())
                    notifyObserver(ViewObserver::onEndCharacterPhase);
                else {
                    askMoveStudentFromCard();
                    askStudentCharacter();
                }
            }
            case FUNGIST -> askNotConsideredColor();
            case MINSTREL -> {
                if (askExchange())
                    notifyObserver(ViewObserver::onEndCharacterPhase);
                else {
                    askStudent("Dining Room");
                    askStudentCharacter();
                }
            }
            case SPOILED_PRINCESS -> askStudentFromCharacterToDining();
            case THIEF -> askRemoveStudentDining();
        }
    }

    /**
     * Asks if the player wants to exchange students.
     * @return true if the player wants to exchange students, else false.
     */
    private boolean askExchange() {
        try {
            String exchange;
            System.out.println("> Do you want to exchange students? [y/n]");
            System.out.println("> ");
            exchange = readUserInput();
            return exchange.equalsIgnoreCase("n");
        } catch (ExecutionException ee) {
            System.out.println("Closing input read thread");
            return false;
        }
    }

    /**
     * Asks the player to write a number that will be used as color, isle id or character card index,
     * based on what the string request asks him.
     * @param request asks the player to insert a value basing on what is needed.
     * @param color true if the value required refers to a color, else false.
     * @param max maximum value of the choice.
     * @return the choice of the player.
     */
    private int askNumber(String request, boolean color, int max) {
        try {
            int choice;
            System.out.println("> "+ request);
            if (color)
                printColors();
            else
                System.out.println("> ");
            choice = Integer.parseInt(readUserInput());
            if (choice>max || choice<0){
                System.out.println("You didn't insert a suitable number! Please, try again...");
                return askNumber(request,color,max);
            }
            return choice;
        } catch (NumberFormatException nf) {
            System.out.println("You didn't insert a suitable number! Please, try again...");
            return askNumber(request,color,max);
        } catch (ExecutionException ee) {
            System.out.println("Closing input read thread");
            return 0;
        }
    }

    /**
     * Prints the integer value associated to each color, in order to permit the player to choose a color.
     */
    private void printColors() {
        System.out.println("> 0 - YELLOW");
        System.out.println("> 1 - PINK");
        System.out.println("> 2 - BLUE");
        System.out.println("> 3 - RED");
        System.out.println("> 4 - GREEN");
        System.out.println("> ");
    }

    // SINGLE METHODS FOR ASKING REQUEST:

    /**
     * Asks the player what he wants to do between two alternatives during the three steps of the action phase.
     * @param S_0 first possible alternative.
     * @param move true if in the first step of the action phase, else false.
     * @return the choice of the player.
     */
    private int askWhatToDo(String S_0, boolean move) {
        try {
            int choice;
            System.out.println("> What do you want to do?");
            System.out.println("> 0 - " + S_0);
            System.out.println("> 1 - " + "PLAY A CHARACTER CARD");
            choice = Integer.parseInt(readUserInput());
            switch (choice) {
                case 0 -> {
                    if (move)
                        askStudent("Entrance");
                    else
                        System.out.println("Okay!");
                }
                case 1 -> printAvailableCharacters();
                default -> System.out.println("Not acceptable value. You will be asked to try again");
            }
            return choice;
        } catch (ExecutionException ee) {
            System.out.println("Closing input read thread");
            return 0;
        }
    }

    /**
     * Asks the player which assistant card he wants to play.
     * Notifies the choice to the ClientController.
     * @param playerID the id of the player who is playing the assistant card.
     */
    public void askAssistantCard(int playerID) {
        int choice = 0;
        try {
            System.out.println(cliDrawer.printAssistantCards(playerID));
            System.out.println("Which Assistant Card you want to play? Insert the turn order of the Assistant Card you want to play (T)");
            choice = Integer.parseInt(readUserInput());
            if (choice>cliDrawer.getStorage().getDashboard(playerID).getMaxAssistantCard() || choice<1){
                System.out.println("You don't have this card, please select another one");
                askAssistantCard(playerID);
            }
        } catch (NumberFormatException nf) {
            System.out.println("You didn't insert a suitable number! Please, try again...");
            askAssistantCard(playerID);
        } catch (ExecutionException ee) {
            System.out.println("Closing input read thread");
        }
        int finalChoice = choice;
        notifyObserver(obs -> obs.onAssistantCard(finalChoice));
    }

    /**
     * Asks the player which student he wants to move from a certain place.
     * Notifies the choice to the ClientController.
     * @param place the place from where thw student will be removed.
     */
    private void askStudent(String place) {
        int choice = askNumber("Which student do you want to move from your "+ place + "?",true,5);
        notifyObserver(obs -> obs.onColorChoice(choice));
    }

    /**
     * Notifies the ClientController that the student is going to be moved in the dining room.
     */
    private void askDiningRoomMovement() { notifyObserver(ViewObserver::onStudentMovement_toDining); }

    /**
     * Asks the player in which isle he wants to move the students.
     * Notifies the choice to the ClientController.
     */
    private void askIsleMovement() {
        int choice = askNumber("Which isle do you want to move your student to? (Select between 0 and " + (cliDrawer.getStorage().getNumberOfIsles() - 1) + ")",false,(cliDrawer.getStorage().getNumberOfIsles() - 1));
        notifyObserver(obs -> obs.onStudentMovement_toIsle(choice));
    }

    /**
     * Asks the player in which isle he wants to move mother nature.
     * Notifies the choice to the ClientController.
     */
    private void askIsleMotherNature() {
        int choice = askNumber("Which isle do you want to move Mother Nature to?",false,(cliDrawer.getStorage().getNumberOfIsles() - 1));
        notifyObserver(obs -> obs.onMNMovement(choice));
    }

    /**
     * Asks the player which cloud he wants to take the students from.
     * Notifies the choice to the ClientController.
     */
    private void askCloud() {
        int choice = askNumber("Which cloud do you want to take students from?",false,cliDrawer.getStorage().getGameTable().getNumOfClouds());
        notifyObserver(obs -> obs.onCloudChoice(choice));
    }

    /**
     * Asks the player which character card he wants to play.
     * Notifies the choice to the ClientController.
     */
    private void askCharacterCard() {
        int choice = askNumber("Which character card do you want to activate?",false,2);
        notifyObserver(obs -> obs.onCharacterCard(choice));
    }

    /**
     * Asks the player in which isle he wants to move the students after having played the MONK.
     * Notifies the choice to the ClientController.
     */
    private void askIsleMovementMONK() {
        int choice = askNumber("Which isle do you want to move your student to? (Select between 0 and " + (cliDrawer.getStorage().getNumberOfIsles() - 1) + ")",false,(cliDrawer.getStorage().getNumberOfIsles() - 1));
        notifyObserver(obs -> obs.onAtomicEffect(choice));
    }

    /**
     * Asks the player which student he wants to move from a certain place.
     * Notifies the choice to the ClientController.
     */
    private void askStudentCharacter() {
        int choice = askNumber("Which student do you want to move from your Entrance?",true,5);
        notifyObserver(obs -> obs.onAtomicEffect(choice));
    }

    /**
     * Asks the player in which isle he wants to calculate the influence after having played the Herald.
     * Notifies the choice to the ClientController.
     */
    private void askIsleInfluenceCount() {
        int choice = askNumber("Which isle do you want to calculate influence on? (Select between 0 and " + (cliDrawer.getStorage().getNumberOfIsles() - 1) + ")",false,(cliDrawer.getStorage().getNumberOfIsles() - 1));
        notifyObserver(obs -> obs.onAtomicEffect(choice));
    }

    /**
     * Asks the player in which isle he wants to put a deny card after having played the Grandma Herbs.
     * Notifies the choice to the ClientController.
     */
    private void askIsleDenyCard() {
        int choice = askNumber("Which isle do you want to put a deny card on? (Select between 0 and " + (cliDrawer.getStorage().getNumberOfIsles() - 1) + ")",false,(cliDrawer.getStorage().getNumberOfIsles() - 1));
        notifyObserver(obs -> obs.onAtomicEffect(choice));
    }

    /**
     * Asks the player which student he wants to move from the character card after having played the Monk or the Jester.
     * Notifies the choice to the ClientController.
     */
    private void askMoveStudentFromCard() {
        int choice = askNumber("Which student do you want to move from the character card?",true,5);
        notifyObserver(obs -> obs.onColorChoice(choice));
    }

    /**
     * Asks the player which student he doesn't want to consider during this turn count of influence after having played the Fungist.
     * Notifies the choice to the ClientController.
     */
    private void askNotConsideredColor() {
        int choice = askNumber("Which color you don't want to consider for influence calculation in this turn?",true,5);
        notifyObserver(obs -> obs.onAtomicEffect(choice));
    }

    /**
     * Asks the player which student he wants to move from the character card to the dining room after having played the Spoiled Princess.
     * Notifies the choice to the ClientController.
     */
    private void askStudentFromCharacterToDining() {
        int choice = askNumber(" Which student do you want to move from the character card to your Dining?",true,5);
        notifyObserver(obs -> obs.onAtomicEffect(choice));
    }

    /**
     * Asks the player which color he wants to remove from the dining room after having played the Thief.
     * Notifies the choice to the ClientController.
     */
    private void askRemoveStudentDining() {
        int choice = askNumber("Which color you want to be removed from Dining Rooms?",true,5);
        notifyObserver(obs -> obs.onAtomicEffect(choice));
    }

    @Override
    public void disconnect(ServiceMessage message) {
        inputThread.interrupt();
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
    public GuiDrawer getGUIDrawer() { return null; }
}

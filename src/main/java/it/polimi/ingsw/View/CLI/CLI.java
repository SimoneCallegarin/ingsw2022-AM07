package it.polimi.ingsw.View.CLI;

import it.polimi.ingsw.Controller.ClientController;
import it.polimi.ingsw.Model.CharacterCards.CharacterCardsName;
import it.polimi.ingsw.Network.ConnectionSocket;
import it.polimi.ingsw.Network.Messages.NetworkMessages.ServiceMessage;
import it.polimi.ingsw.Observer.ViewObserver;
import it.polimi.ingsw.Observer.ViewSubject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.SQLOutput;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * this class implements the command line interface to play trough terminal, it's observed by the connectionSocket which sends messages
 * to the server according to the CLI updates and, in addition, it receives update messages from the virtual view to manage the interactions
 * with the player accordingly.
 */

public class CLI extends ViewSubject {
    BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
    CLIDrawer cliDrawer;

    public CLI() {
        cliDrawer = new CLIDrawer();
    }

    /**
     * method used to launch a thread for user input reading
     */
    //need to make it always run not only when the method is called
    public String readUserInput(){

        FutureTask<String> asyncInput=new FutureTask<>(() -> br.readLine());
        Thread inputThread = new Thread(asyncInput);
        inputThread.start();
        String userInput = null;
        try {
             userInput = asyncInput.get();
        }
        catch (InterruptedException | ExecutionException e){
            asyncInput.cancel(true);
            Thread.currentThread().interrupt();
        }
        return userInput;


    }

    /**
     * CLI start
     */
    public void CLIstart(){
        System.out.println("Welcome to Eriantys game!\n");
        System.out.println(cliDrawer.printTitle());
        askUsername();
        // The model receives these data through the network then after that it updates,
        // it sends the new data to the Client through the VirtualView.
        // We don't send the game reference trough messages, so we need to pass each one of the objects in order to draw them
    }

    /**
     * Reads the username chosen by the player and notifies it to the view.
     */
    public void askUsername() {
        System.out.println("> Nickname? [NOTE: it must be between 2 and 20 characters long]");
        System.out.println("> ");
        String username = readUserInput();
        if (username.length() >= 2 && username.length() <= 20)
            notifyObserver(obs -> obs.onUsername(username));
        else
            askUsername();
    }

    /**
     * Used to ask the user the game settings he desires, which are the game mode and the number of Players
     * then it notifies them to the view.
     */
    public void askGamePreferences() {
        try {
            int numPlayers;
            String modePreference;
            System.out.println("> How many players do you want to play with? [2, 3 or 4]");
            System.out.println("> ");
            numPlayers = Integer.parseInt(readUserInput());
            boolean expertMode;
            System.out.println("> Do you want to play in Expert mode? [y/n]");
            System.out.println("> ");
            modePreference = readUserInput();
            expertMode = modePreference.equalsIgnoreCase("y");
            if ((modePreference.equalsIgnoreCase("y") || modePreference.equalsIgnoreCase("n")) && (numPlayers >= 2 && numPlayers <= 4))
                notifyObserver(obs -> obs.onGamePreferences(numPlayers, expertMode));
            else
                askGamePreferences();
        } catch (NumberFormatException nf) {
            System.out.println("You didn't insert a suitable number! Please, try again...");
            askGamePreferences();
        }
    }

    public void askAssistantCard(int playerID) {
        try {
            int choice;
            System.out.println(cliDrawer.printAssistantCards(playerID));
            System.out.println("> Which Assistant Card you want to play? Insert the turn order of the Assistant Card you want to play (T)");
            System.out.println("> ");
            choice = Integer.parseInt(readUserInput());
            notifyObserver(obs -> obs.onAssistantCard(choice));
        } catch (NumberFormatException nf) {
            System.out.println("You didn't insert a suitable number! Please, try again...");
            askAssistantCard(playerID);
        }
    }

    public void askMove(boolean expertMode) {
        try {
            int initialChoice = 0;
            if (expertMode) {
                System.out.println("> What do you want to do?");
                System.out.println("> 0 - SELECT A STUDENT TO MOVE");
                System.out.println("> 1 - PLAY A CHARACTER CARD");
                initialChoice = Integer.parseInt(readUserInput());
                switch (initialChoice) {
                    case 0 -> askRealmColor("your Entrance");
                    case 1 -> printAvailableCharacters();
                    default -> System.out.println("Not acceptable value. You will be asked to try again");
                }
            } else
                askRealmColor("your Entrance");
            int choice;
            System.out.println("> What do you want to do now?");
            System.out.println("> 0 - GO BACK TO THE PREVIOUS CHOICE");
            if (initialChoice == 0) {
                System.out.println("> 1 - MOVE SELECTED STUDENT IN YOUR DINING ROOM");
                System.out.println("> 2 - MOVE SELECTED STUDENT ON AN ISLE");
            }
            if (initialChoice == 1)
                System.out.println("> 1 - CHOOSE CHARACTER CARD TO ACTIVATE");
            choice = Integer.parseInt(readUserInput());
            switch (choice) {
                case 0 -> askMove(expertMode);
                case 1 -> {
                    if (initialChoice == 0)
                        askDiningRoomMovement();
                    else if (initialChoice == 1)
                        askCharacterToPlay();
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
        } catch (NumberFormatException nf) {
            System.out.println("You didn't insert a suitable number! Please, try again...");
            askMove(expertMode);
        }
    }

    private void askRealmColor(String place) {
        try {
            int choice;
            System.out.println("> Which student do you want to move from " + place + "?");
            System.out.println("> 0 - YELLOW");
            System.out.println("> 1 - PINK");
            System.out.println("> 2 - BLUE");
            System.out.println("> 3 - RED");
            System.out.println("> 4 - GREEN");
            System.out.println("> ");
            choice = Integer.parseInt(readUserInput());
            notifyObserver(obs -> obs.onColorChoice(choice));
        } catch (NumberFormatException nf) {
            System.out.println("You didn't insert a suitable number! Please, try again...");
            askRealmColor(place);
        }
    }

    private void askDiningRoomMovement() {
        notifyObserver(ViewObserver::onStudentMovement_toDining);
    }

    private void askIsleMovement() {
        try {
            int choice;
            System.out.println("> Which isle do you want to move your student to? (Select between 0 and " + (cliDrawer.getStorage().getNumberOfIsles() - 1) + ")");
            System.out.println("> ");
            choice = Integer.parseInt(readUserInput());
            notifyObserver(obs -> obs.onStudentMovement_toIsle(choice));
        } catch (NumberFormatException nf) {
            System.out.println("You didn't insert a suitable number! Please, try again...");
            askIsleMovement();
        }
    }

    public void askMNMovement(boolean expertMode) {
        try {
            int initialChoice = 0;
            if (expertMode) {
                System.out.println("> What do you want to do?");
                System.out.println("> 0 - SELECT THE ISLE FOR MOTHER NATURE MOVEMENT");
                System.out.println("> 1 - PLAY A CHARACTER CARD");
                initialChoice = Integer.parseInt(readUserInput());
                switch (initialChoice) {
                    case 0 -> System.out.println("Okay!");
                    case 1 -> printAvailableCharacters();
                    default -> System.out.println("Not acceptable value. You will be asked to try again");
                }
            }
            if (expertMode) {
                int choice;
                System.out.println("> What do you want to do now?");
                System.out.println("> 0 - GO BACK TO THE PREVIOUS CHOICE");
                if (initialChoice == 0) {
                    System.out.println("> 1 - CHOOSE ISLE");
                }
                if (initialChoice == 1)
                    System.out.println("> 1 - CHOOSE CHARACTER CARD TO ACTIVATE");
                choice = Integer.parseInt(readUserInput());
                switch (choice) {
                    case 0 -> askMNMovement(true);
                    case 1 -> {
                        if (initialChoice == 0) {
                            System.out.println("> Which isle do you want to move Mother Nature to?");
                            System.out.println("> ");
                            int isleChoice = Integer.parseInt(readUserInput());
                            notifyObserver(obs -> obs.onMNMovement(isleChoice));
                        }
                        else if (initialChoice == 1)
                            askCharacterToPlay();
                        else
                            askMNMovement(true);
                    }
                    default -> {
                        System.out.println("Not acceptable value. Please, try again.");
                        askMNMovement(true);
                    }
                }
            } else {
                System.out.println("> Which isle do you want to move Mother Nature to?");
                System.out.println("> ");
                int isleChoice = Integer.parseInt(readUserInput());
                notifyObserver(obs -> obs.onMNMovement(isleChoice));
            }
        } catch (NumberFormatException nf) {
            System.out.println("You didn't insert a suitable number! Please, try again...");
            askMNMovement(expertMode);
        }
    }

    public void askCloud(boolean expertMode) {
        try {
            int initialChoice = 0;
            if (expertMode) {
                System.out.println("> What do you want to do?");
                System.out.println("> 0 - SELECT THE CLOUD YOU WANT TO PICK STUDENTS FROM");
                System.out.println("> 1 - PLAY A CHARACTER CARD");
                initialChoice = Integer.parseInt(readUserInput());
                switch (initialChoice) {
                    case 0 -> System.out.println("Okay!");
                    case 1 -> printAvailableCharacters();
                    default -> System.out.println("Not acceptable value. You will be asked to try again");
                }
            }
            if (expertMode) {
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
                        if (initialChoice == 0) {
                            System.out.println("> Which cloud do you want to take students from?");
                            System.out.println("> ");
                            int cloudChoice = Integer.parseInt(readUserInput());
                            notifyObserver(obs -> obs.onCloudChoice(cloudChoice));
                        }
                        else if (initialChoice == 1)
                            askCharacterToPlay();
                        else
                            askCloud(true);
                    }
                    default -> {
                        System.out.println("Not acceptable value. Please, try again.");
                        askCloud(true);
                    }
                }
            } else {
                System.out.println("> Which cloud do you want to take students from?");
                System.out.println("> ");
                int cloudChoice = Integer.parseInt(readUserInput());
                notifyObserver(obs -> obs.onCloudChoice(cloudChoice));
            }
        } catch (NumberFormatException nf) {
            System.out.println("You didn't insert a suitable number! Please, try again...");
            askCloud(expertMode);
        }
    }

    public void askCharacterToPlay(){
        try {
            final int choice;
            System.out.println("> Which character card do you want to activate?");
            System.out.println("> ");
            choice = Integer.parseInt(readUserInput());
            notifyObserver(obs -> obs.onCharacterCard(choice));
        } catch (NumberFormatException nf) {
            System.out.println("You didn't insert a suitable number! Please, try again...");
            askCharacterToPlay();
        }
    }

    public void askCharacterEffectParameters(CharacterCardsName characterName) {
        try {
            int choice;
            System.out.println("> You activated the " + characterName.toString() + "!");
            switch (characterName) {
                case FARMER, MAGICAL_LETTER_CARRIER, CENTAUR, KNIGHT ->
                    notifyObserver(obs -> obs.onAtomicEffect(-1));
                case MONK -> {
                    askRealmColor("the character card");
                    System.out.println("> Which isle do you want to move the student to? (Select between 0 and " + (cliDrawer.getStorage().getNumberOfIsles() - 1) + ")");
                    System.out.println("> ");
                    choice = Integer.parseInt(readUserInput());
                    notifyObserver(obs -> obs.onAtomicEffect(choice));
                }
                case HERALD -> {
                    System.out.println("> Which isle do you want to calculate influence on? (Select between 0 and " + (cliDrawer.getStorage().getNumberOfIsles() - 1) + ")");
                    System.out.println("> ");
                    choice = Integer.parseInt(readUserInput());
                    notifyObserver(obs -> obs.onAtomicEffect(choice));
                }
                case GRANDMA_HERBS -> {
                    System.out.println("> Which isle do you want to put a deny card on? (Select between 0 and " + (cliDrawer.getStorage().getNumberOfIsles() - 1) + ")");
                    System.out.println("> ");
                    choice = Integer.parseInt(readUserInput());
                    notifyObserver(obs -> obs.onAtomicEffect(choice));
                }
                case JESTER -> {
                    boolean endPhase;
                    String exchange;
                    System.out.println("> Do you want to exchange students? [y/n]");
                    System.out.println("> ");
                    exchange = readUserInput();
                    endPhase = exchange.equalsIgnoreCase("n");
                    if (endPhase)
                        notifyObserver(ViewObserver::onEndCharacterPhase);
                    else {
                        askRealmColor("the character card");
                        int exchangeChoice;
                        System.out.println("> Which student do you want to remove from your Entrance?");
                        System.out.println("> 0 - YELLOW");
                        System.out.println("> 1 - PINK");
                        System.out.println("> 2 - BLUE");
                        System.out.println("> 3 - RED");
                        System.out.println("> 4 - GREEN");
                        System.out.println("> ");
                        exchangeChoice = Integer.parseInt(readUserInput());
                        notifyObserver(obs -> obs.onAtomicEffect(exchangeChoice));
                    }
                }
                case FUNGIST -> {
                    System.out.println("> Which color you don't want to consider for influence calculation in this turn?");
                    System.out.println("> 0 - YELLOW");
                    System.out.println("> 1 - PINK");
                    System.out.println("> 2 - BLUE");
                    System.out.println("> 3 - RED");
                    System.out.println("> 4 - GREEN");
                    System.out.println("> ");
                    choice = Integer.parseInt(readUserInput());
                    notifyObserver(obs -> obs.onAtomicEffect(choice));
                }
                case MINSTREL -> {
                    boolean endPhase;
                    String exchange;
                    System.out.println("> Do you want to exchange students? [y/n]");
                    System.out.println("> ");
                    exchange = readUserInput();
                    endPhase = exchange.equalsIgnoreCase("n");
                    if (endPhase)
                        notifyObserver(ViewObserver::onEndCharacterPhase);
                    else {
                        askRealmColor("your Dining");
                        int exchangeChoice;
                        System.out.println("> Which student do you want to remove from your Entrance?");
                        System.out.println("> 0 - YELLOW");
                        System.out.println("> 1 - PINK");
                        System.out.println("> 2 - BLUE");
                        System.out.println("> 3 - RED");
                        System.out.println("> 4 - GREEN");
                        System.out.println("> ");
                        exchangeChoice = Integer.parseInt(readUserInput());
                        notifyObserver(obs -> obs.onAtomicEffect(exchangeChoice));
                    }
                }
                case SPOILED_PRINCESS -> {
                    System.out.println("> Which student do you want to move from the character card to your Dining?");
                    System.out.println("> 0 - YELLOW");
                    System.out.println("> 1 - PINK");
                    System.out.println("> 2 - BLUE");
                    System.out.println("> 3 - RED");
                    System.out.println("> 4 - GREEN");
                    System.out.println("> ");
                    choice = Integer.parseInt(readUserInput());
                    notifyObserver(obs -> obs.onAtomicEffect(choice));
                }
                case THIEF -> {
                    System.out.println("> Which color you want to be removed from Dining Rooms?");
                    System.out.println("> 0 - YELLOW");
                    System.out.println("> 1 - PINK");
                    System.out.println("> 2 - BLUE");
                    System.out.println("> 3 - RED");
                    System.out.println("> 4 - GREEN");
                    System.out.println("> ");
                    choice = Integer.parseInt(readUserInput());
                    notifyObserver(obs -> obs.onAtomicEffect(choice));
                }
            }
        } catch (NumberFormatException nf) {
            System.out.println("You didn't insert a suitable number! Please, try again...");
            askCharacterEffectParameters(characterName);
        }
    }

    // at the end of the game -> cliDrawer.printWinner(winnerID);

    public void printMessage(ServiceMessage message) {
        System.out.println(message.getMessage());
    }

    public void printChanges() { System.out.println(cliDrawer.printGameTable()); }

    private void printAvailableCharacters() { System.out.println(cliDrawer.drawCharacterCardsEffects()); }

    public CLIDrawer getCliDrawer() {
        return cliDrawer;
    }

    public static void main(String[] args) throws ClassNotFoundException {
        CLI cli = new CLI();
        ConnectionSocket connectionSocket = new ConnectionSocket();
        ClientController clientController = new ClientController(cli, connectionSocket, cli.getCliDrawer());
        cli.addObserver(clientController);
        connectionSocket.startConnection();
        connectionSocket.getClientListener().addObserver(clientController);
        cli.CLIstart();
    }

}

package it.polimi.ingsw.View.CLI;

import it.polimi.ingsw.Controller.ClientController;
import it.polimi.ingsw.Network.ConnectionSocket;
import it.polimi.ingsw.Network.Messages.NetworkMessages.ServiceMessage;
import it.polimi.ingsw.Observer.ViewObserver;
import it.polimi.ingsw.Observer.ViewSubject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
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
        //addObserver(connectionSocket);
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
        System.out.println("> Nickname? ");
        System.out.println("> ");
        String username = readUserInput();
        notifyObserver(obs -> obs.onUsername(username));
    }

    /**
     * Used to ask the user the game settings he desires, which are the game mode and the number of Players
     * then it notifies them to the view.
     */
    public void askGamePreferences() {
        int numPlayers;

        System.out.println("> How many players do you want to play with? [2, 3 or 4]");
        System.out.println("> ");
        numPlayers = Integer.parseInt(readUserInput());
        boolean expertMode;
        String modePreference;

        System.out.println("> Do you want to play in Expert mode? [y/n]");
        System.out.println("> ");
        modePreference = readUserInput();
        expertMode = modePreference.equalsIgnoreCase("y");
        int finalNumPlayers = numPlayers;
        boolean finalExpertMode = expertMode;
        notifyObserver(obs -> obs.onGamePreferences(finalNumPlayers, finalExpertMode));
    }

    public void askAssistantCard(int playerID) {
        int choice;
        System.out.println(cliDrawer.printAssistantCards(playerID));
        System.out.println("> Which Assistant Card you want to play?");//to update with the available assistant cards
        System.out.println("> ");
        choice=Integer.parseInt(readUserInput());
        notifyObserver(obs->obs.onAssistantCard(choice));
        //System.out.println(cliDrawer.printGameTable());
    }

    public void askMove() {
        askRealmColor();
        int choice;
        System.out.println("> What do you want to do now?");
        System.out.println("> 0 - CHANGE COLOR");
        System.out.println("> 1 - MOVE SELECTED STUDENT IN YOUR DINING ROOM");
        System.out.println("> 2 - MOVE SELECTED STUDENT ON AN ISLE");
        choice=Integer.parseInt(readUserInput());
        switch (choice) {
            case 0 -> askMove();
            case 1 -> askDiningRoomMovement();
            case 2 -> askIsleMovement();
        }
    }

    private void askRealmColor() {
        int choice;
        System.out.println("> Which student do you want to move from your Entrance?");
        System.out.println("> 0 - YELLOW");
        System.out.println("> 1 - PINK");
        System.out.println("> 2 - BLUE");
        System.out.println("> 3 - RED");
        System.out.println("> 4 - GREEN");
        System.out.println("> ");
        choice=Integer.parseInt(readUserInput());
        notifyObserver(obs -> obs.onColorChoice(choice));
    }

    private void askDiningRoomMovement() {
        notifyObserver(obs -> obs.onStudentMovement_toDining());
    }

    private void askIsleMovement() {
        int choice;
        System.out.println("> Which isle do you want to move your student to? (Select between 0 and " + cliDrawer.getStorage().getNumberOfIsles() + ")");
        System.out.println("> ");
        choice=Integer.parseInt(readUserInput());
        notifyObserver(obs -> obs.onStudentMovement_toIsle(choice));
    }

    public void askMNMovement() {
        int choice;
        System.out.println("> > Which isle do you want to move Mother Nature to?");
        System.out.println("> ");
        choice=Integer.parseInt(readUserInput());
        notifyObserver(obs->obs.onMNMovement(choice));
    }

    public void askCloud(){
        int choice;
        System.out.println("> > Which cloud do you want to take students from?");
        System.out.println("> ");
        choice=Integer.parseInt(readUserInput());
        notifyObserver(obs->obs.onCloudChoice(choice));
    }

    /*public void askMove(){
        int studentMoves=3;
        int choice;
        int color;
        boolean characterUsed=false;
        boolean turnFinished=false;

            //continua a chiedere finchè non esaurisce le mosse per il primo turno o sbaglia l'input
        while(!turnFinished) {
            System.out.println("What is your next move? Write:");
            System.out.println("-1 to move a student(max " + studentMoves + " other time/s this turn");
            if (!characterUsed) {
                System.out.println("-2 to play a Character Card");
            }
            choice = Integer.parseInt(readUserInput());

            if (choice == 1) {
                askStudentMovement();
                studentMoves--;
                if (studentMoves == 0) {
                    turnFinished = true;
                }
            } else if (choice == 2 && !characterUsed) {
                askCharacterToPlay();
            } else {
                System.out.println("Invalid choice, repeat");
            }
        }

    }*/

    public void askCharacterToPlay(){
        final int choice;
        System.out.println("Choose a CharacterCard to Play");
        System.out.println("-1");
        System.out.println("-2");
        System.out.println("-3");
        choice=Integer.parseInt(readUserInput());
        notifyObserver(obs->obs.onCharacterCard(choice));
        // Not finished yet!!!
    }

    // at the end of the game -> cliDrawer.printWinner(winnerID);

    public void printMessage(ServiceMessage message) {
        System.out.println(message.getMessage());
    }

    public void printChanges() { System.out.println(cliDrawer.printGameTable()); }

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

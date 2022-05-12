package it.polimi.ingsw.View;



import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Network.ConnectionSocket;
import it.polimi.ingsw.Network.Server;
import it.polimi.ingsw.Observer.ViewSubject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * this class implements the command line interface to play trough terminal, it's observed by the connectionSocket which sends messages
 * to the server according to the CLI updates and, in addition, it receives update messages from the virtual view to manage the interactions
 * with the player accordingly.
 */

public class CLI extends ViewSubject {
    BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
    CLIDrawer cliDrawer=new CLIDrawer();

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
        cliDrawer.printTitle();
        askGamePreferences();
        askUsername();
        //the model receives these data through the network then after it updates, it sends the new datas trough to the Client
        //trough the VirtualView
        //we can't send the game reference trough messages so we need to pass each one of the objects to draw them
        System.out.println(cliDrawer.printGameTable());
        askAssistantCard();
        askMove();
        askMNMovement();
        askCloud();
        //the turn changes




    }

    /**
     * method used to read the username choice by the player
     */
    public void askUsername() {
        AtomicBoolean usernameTaken = new AtomicBoolean(true);
        System.out.println("Enter your username:\n");
        String username = readUserInput();
        while (usernameTaken.get()) {
                notifyObserver(obs -> {
                    try {
                        obs.onUsername(username);
                        usernameTaken.set(false);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                });
        }
    }

    /**
     * this method is used to ask the user the game settings he desires, which are the game mode and the number of Players
     */
    public void askGamePreferences() {
        int numPlayers = 3;
        do {
            if (numPlayers < 2 || numPlayers > 4) {
                System.out.println("Invalid input, repeat");
            }
            System.out.println("Enter the number of players desired:\n");
            numPlayers = Integer.parseInt(readUserInput());
        } while (numPlayers < 2 || numPlayers > 4);
        boolean gamemode;
        String choiceGamemode;
        do {
            System.out.println("Now type the game mode preferred: Expert or Base?");
            choiceGamemode = readUserInput();
            gamemode = choiceGamemode.equals("Expert");
        } while (choiceGamemode != "Expert" || choiceGamemode!="Base");

        int finalNumPlayers = numPlayers;
        boolean finalGamemode = gamemode;
        notifyObserver(obs -> obs.onGamePreferences(finalNumPlayers, finalGamemode));
    }

    public void askCloud(){
        int choice;
        System.out.println("Select the cloud where do you want to take the students from");//to update with the available clouds
        choice=Integer.parseInt(readUserInput());
        notifyObserver(obs->obs.onCloudChoice(choice));
        System.out.println(cliDrawer.printGameTable());
    }

    public void askMNMovement(){
        int choice;
        System.out.println("Select the isle id where you want to move Mother Nature");//to update with the available movements
        choice=Integer.parseInt(readUserInput());
        notifyObserver(obs->obs.onMNMovement(choice));
        System.out.println(cliDrawer.printGameTable());
    }


    public void askAssistantCard(){
        int choice;
        System.out.println("Choose an Assistant Card to play:");//to update with the available assistant cards
        choice=Integer.parseInt(readUserInput());
        notifyObserver(obs->obs.onAssistantCard(choice));
        System.out.println(cliDrawer.printGameTable());
    }


    public void askMove(){
        int studentMoves=3;
        int choice=0;
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

    }

    public void askCharacterToPlay(){
        final int choice;
        System.out.println("Choose a CharacterCard to Play");
        System.out.println("-1");
        System.out.println("-2");
        System.out.println("-3");
        choice=Integer.parseInt(readUserInput());
        notifyObserver(obs->obs.onCharacterCard(choice));
        //to finish
    }


    public void askStudentMovement(){
        int color;
        int choice;
        System.out.println("Choose a student color to move from the Entrance");
        System.out.println("Colors available:");
        color=(Integer.parseInt(readUserInput()));
        notifyObserver(obs->obs.onColorChoice(color));

        System.out.println("Choose where to move the student");
        System.out.println("-1 to DiningRoom\n-2 to an Isle");
        choice=Integer.parseInt(readUserInput());
        if(choice==1){
            notifyObserver(obs->obs.onStudentmovement_toDining(0));//to update with the idPlayer value
            System.out.println(cliDrawer.printGameTable());
        }else{
            System.out.println("Write the id of the isle where you want to move the student");
            choice=Integer.parseInt(readUserInput());
            int finalChoice = choice;
            notifyObserver(obs->obs.onStudentmovement_toIsle(finalChoice));
            System.out.println(cliDrawer.printGameTable());
        }

    }

    public static void main(String[] args) throws IOException {
        Server server=new Server(1234);
        ConnectionSocket connectionSocket=new ConnectionSocket();

    }

}

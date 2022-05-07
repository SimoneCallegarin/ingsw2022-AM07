package it.polimi.ingsw.View;

import it.polimi.ingsw.Observer.ModelObserver;
import it.polimi.ingsw.Observer.ViewObserver;
import it.polimi.ingsw.Observer.ViewSubject;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * this class implements the command line interface to play trough terminal, it's observed by the clientSocket which sends messages
 * to the server according to the CLI updates and, in addition, it observes the model to update the graphics accordingly.
 */

public class CLI extends ViewSubject implements ModelObserver {

    private final BufferedReader br=new BufferedReader(new InputStreamReader(System.in));

    /**
     * method used to launch a thread for user input reading
     * @return the String read from the standard input
     */
    public String readUserInput() throws ExecutionException, InterruptedException {
        FutureTask<String> asyncInput=new FutureTask<>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                 return br.readLine();
            }
        });
        Thread inputThread = new Thread(asyncInput);
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
     * CLI start
     */
    public void CLIstart(){
        System.out.println(" .----------------.  .----------------.  .----------------.  .----------------.  .-----------------. .----------------.  .----------------.  .----------------. \n" +
                "| .--------------. || .--------------. || .--------------. || .--------------. || .--------------. || .--------------. || .--------------. || .--------------. |\n" +
                "| |  _________   | || |  _______     | || |     _____    | || |      __      | || | ____  _____  | || |  _________   | || |  ____  ____  | || |    _______   | |\n" +
                "| | |_   ___  |  | || | |_   __ \\    | || |    |_   _|   | || |     /  \\     | || ||_   \\|_   _| | || | |  _   _  |  | || | |_  _||_  _| | || |   /  ___  |  | |\n" +
                "| |   | |_  \\_|  | || |   | |__) |   | || |      | |     | || |    / /\\ \\    | || |  |   \\ | |   | || | |_/ | | \\_|  | || |   \\ \\  / /   | || |  |  (__ \\_|  | |\n" +
                "| |   |  _|  _   | || |   |  __ /    | || |      | |     | || |   / ____ \\   | || |  | |\\ \\| |   | || |     | |      | || |    \\ \\/ /    | || |   '.___`-.   | |\n" +
                "| |  _| |___/ |  | || |  _| |  \\ \\_  | || |     _| |_    | || | _/ /    \\ \\_ | || | _| |_\\   |_  | || |    _| |_     | || |    _|  |_    | || |  |`\\____) |  | |\n" +
                "| | |_________|  | || | |____| |___| | || |    |_____|   | || ||____|  |____|| || ||_____|\\____| | || |   |_____|    | || |   |______|   | || |  |_______.'  | |\n" +
                "| |              | || |              | || |              | || |              | || |              | || |              | || |              | || |              | |\n" +
                "| '--------------' || '--------------' || '--------------' || '--------------' || '--------------' || '--------------' || '--------------' || '--------------' |\n" +
                " '----------------'  '----------------'  '----------------'  '----------------'  '----------------'  '----------------'  '----------------'  '----------------' ");
        System.out.println("Welcome to Eriantys game!\n");
        askGamePreferences();
        askUsername();

    }

    /**
     * method used to read the username choice by the player
     */
    public void askUsername(){
        System.out.println("Enter your username:\n");
        try{
            String username=readUserInput();
            notifyObserver(obs->obs.onUsername(username));
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void askGamePreferences(){
        System.out.println("Enter the number of players desired:\n");
        try{
            int numPlayers=Integer.parseInt(readUserInput());
            System.out.println("Now type the game mode preffered: Expert or Base?");
            String choiceGamemode=readUserInput();
            boolean gamemode=choiceGamemode.equals("Expert");
            notifyObserver(obs->obs.onGamePreferences(numPlayers, gamemode));
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void drawBoard(){

    }



}

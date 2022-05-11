package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.DashboardObjects.Dashboard;
import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Observer.ModelObserver;
import it.polimi.ingsw.Observer.ViewSubject;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * this class implements the command line interface to play trough terminal, it's observed by the clientSocket which sends messages
 * to the server according to the CLI updates and, in addition, it observes the model to update the graphics accordingly.
 */

public class CLI extends ViewSubject implements ModelObserver {
    BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
    static CLIDrawer drawer = new CLIDrawer();

    /**
     * method used to launch a thread for user input reading
     */
    //need to make it always run not only when the method is called
    public String readUserInput(){

        FutureTask<String> asyncInput=new FutureTask<>(new Callable<>() {
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
        drawer.printTitle();
        askGamePreferences();
        askUsername();

    }

    /**
     * method used to read the username choice by the player
     */
    public void askUsername(){
        System.out.println("Enter your username:\n");
        String username=readUserInput();
        notifyObserver(obs->obs.onUsername(username));
    }

    public void askGamePreferences(){
        System.out.println("Enter the number of players desired:\n");
        int numPlayers=Integer.parseInt(readUserInput());
        System.out.println("Now type the game mode preferred: Expert or Base?");
        String choiceGamemode=readUserInput();
        boolean gamemode=choiceGamemode.equals("Expert");
        notifyObserver(obs->obs.onGamePreferences(numPlayers, gamemode));
    }

    public static void drawDashboard(Dashboard dashboard){

    }

    public static StringBuilder printScreen(){
        return drawer.drawGameTable();
    }

    public static StringBuilder drawEntrance(HashMap<RealmColors, Integer> students){
        StringBuilder toPrint= new StringBuilder();
        toPrint.append("Entrance\n");
        for(RealmColors color:students.keySet()){
            toPrint.append((CLIColors.realmColorsConverter(color) + "O " + CLIColors.RESET).repeat(Math.max(0, students.get(color))));
            toPrint.append("\n");
        }
        return toPrint;
    }

    //need to add the color of the Tower. The TowerColor are Grey, Black, White
    public static StringBuilder drawTowerStorage(int numTower){
        StringBuilder toPrint=new StringBuilder();
        for(int i=0;i<numTower && i<8;i++){
            toPrint.append("O ");
            if(i%2!=0){
                toPrint.append("\n");
            }
        }
        return toPrint;
    }

    public static void main(String[] args) {
        //drawer.printTitle();
        System.out.println(printScreen());
    }
}

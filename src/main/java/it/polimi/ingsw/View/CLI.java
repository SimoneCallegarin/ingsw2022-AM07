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
        System.out.println(
                """
                        ╔════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╗
                        ║              ¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶                        ¶¶¶¶¶                                                                                                       ║
                        ║          ¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶                      ¶¶¶¶¶¶¶                                                                                                      ║
                        ║        ¶¶¶¶¶¶¶   ¶¶¶¶¶¶¶         ¶¶¶¶¶¶¶¶                     ¶¶¶¶¶¶¶                                                 ¶¶¶                                                  ║
                        ║       ¶¶¶¶¶      ¶¶¶¶¶¶¶            ¶¶¶¶¶¶                    ¶¶¶¶¶¶¶                                                ¶¶¶¶                                                  ║
                        ║      ¶¶¶¶¶      ¶¶¶¶¶¶¶¶      ¶¶¶     ¶¶¶¶                     ¶¶¶¶                                                ¶¶¶¶¶¶        ¶¶¶¶       ¶¶¶¶        ¶¶¶¶¶¶¶¶¶          ║
                        ║      ¶¶¶¶       ¶¶¶¶¶¶¶¶  ¶¶¶¶¶¶¶¶¶¶        ¶¶¶¶¶   ¶¶¶¶¶¶¶   ¶¶¶¶¶¶¶         ¶¶¶¶¶¶¶¶¶¶¶      ¶¶¶¶¶    ¶¶¶¶¶¶    ¶¶¶¶¶¶¶¶¶¶¶   ¶¶¶¶¶¶     ¶¶¶¶¶¶     ¶¶¶¶¶¶¶¶¶¶¶¶¶        ║
                        ║      ¶¶¶¶       ¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶     ¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶  ¶¶¶¶¶¶¶      ¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶    ¶¶¶¶¶¶¶ ¶¶¶¶¶¶¶¶¶  ¶¶¶¶¶¶¶¶¶¶¶¶¶¶ ¶¶¶¶¶¶¶    ¶¶¶¶¶¶¶   ¶¶¶¶¶¶¶   ¶¶¶¶        ║
                        ║      ¶¶¶¶¶      ¶¶¶¶¶¶¶¶¶¶¶¶¶    ¶¶¶     ¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶  ¶¶¶¶¶¶¶     ¶¶¶¶¶¶   ¶¶¶¶¶¶¶    ¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶    ¶¶¶¶¶¶¶ ¶¶¶¶  ¶¶¶¶¶¶¶    ¶¶¶¶¶¶¶  ¶¶¶¶¶¶¶    ¶¶¶        ║
                        ║      ¶¶¶¶¶      ¶¶¶¶¶¶¶¶¶¶¶       ¶¶       ¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶    ¶¶¶¶¶¶¶    ¶¶¶¶¶¶     ¶¶¶¶¶¶    ¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶    ¶¶¶¶¶¶¶ ¶¶¶    ¶¶¶¶¶¶¶¶  ¶¶¶¶¶¶¶  ¶¶¶¶¶¶¶¶¶             ║
                        ║       ¶¶¶¶¶     ¶¶¶¶¶¶¶¶¶¶                 ¶¶¶¶¶¶¶   ¶¶¶¶     ¶¶¶¶¶¶¶   ¶¶¶¶¶¶      ¶¶¶¶¶¶    ¶¶¶¶¶¶¶¶   ¶¶¶¶¶¶    ¶¶¶¶¶¶¶         ¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶     ¶¶¶¶¶¶¶¶¶           ║
                        ║        ¶¶¶¶¶¶   ¶¶¶¶¶¶¶¶¶                  ¶¶¶¶¶¶¶   ¶¶       ¶¶¶¶¶¶¶   ¶¶¶¶¶¶     ¶¶¶¶¶¶¶    ¶¶¶¶¶¶¶    ¶¶¶¶¶¶    ¶¶¶¶¶¶¶          ¶¶¶¶¶¶¶¶¶¶¶¶¶       ¶¶¶¶¶¶¶¶¶          ║
                        ║         ¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶               ¶¶¶ ¶¶¶¶¶¶¶            ¶¶¶¶¶¶¶  ¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶    ¶¶¶¶¶¶¶    ¶¶¶¶¶¶    ¶¶¶¶¶¶¶           ¶¶¶¶¶¶¶¶¶¶¶   ¶¶¶   ¶¶¶¶¶¶¶¶¶         ║
                        ║                 ¶¶¶¶¶¶¶¶              ¶¶¶¶ ¶¶¶¶¶¶¶            ¶¶¶¶¶¶¶  ¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶   ¶¶¶¶¶¶¶    ¶¶¶¶¶¶    ¶¶¶¶¶¶¶¶¶¶¶        ¶¶¶¶¶¶¶¶¶    ¶¶¶¶    ¶¶¶¶¶¶¶         ║
                        ║                 ¶¶¶¶¶¶¶¶            ¶¶¶¶¶  ¶¶¶¶¶¶             ¶¶¶¶¶¶¶   ¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶ ¶¶¶¶¶¶¶    ¶¶¶¶¶¶¶¶  ¶¶¶¶¶¶¶¶¶¶          ¶¶¶¶¶¶¶     ¶¶¶¶¶¶¶¶¶¶¶¶¶¶          ║
                        ║                ¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶   ¶¶¶¶¶              ¶¶¶¶¶¶¶    ¶¶¶¶¶¶¶¶    ¶¶¶¶¶¶¶   ¶¶¶¶¶     ¶¶¶¶¶¶¶    ¶¶¶¶¶¶¶           ¶¶¶¶¶¶¶       ¶¶¶¶¶¶¶¶¶¶¶¶           ║
                        ║              ¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶¶                                                                             ¶¶¶¶¶           ¶¶¶¶¶¶¶          ¶¶¶¶¶¶¶¶             ║
                        ║                                                                                                                                     ¶¶¶¶¶¶¶                                ║
                        ║                                                                                                                                     ¶¶¶¶¶¶                                 ║
                        ╚════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╝""");
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

    public static StringBuilder drawDiningRoom(HashMap<RealmColors, Integer> students){
        StringBuilder toPrint=new StringBuilder();
        toPrint.append("DiningRoom\n");
        for(RealmColors color: RealmColors.values()){
            for(int i=0;i<10;i++){
                if(i<students.get(color)){
                    toPrint.append(CLIColors.realmColorsConverter(color)).append("O ").append(CLIColors.RESET);
                }else{
                    toPrint.append(CLIColors.realmColorsConverter(color)).append("X ").append(CLIColors.RESET);
                }
            }
            toPrint.append("\n");
        }
        return toPrint;

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
        HashMap<RealmColors,Integer> students=new HashMap<>();
        students.put(RealmColors.RED,2);
        students.put(RealmColors.YELLOW,4);
        students.put(RealmColors.PINK,3);
        students.put(RealmColors.BLUE,1);
        students.put(RealmColors.GREEN,0);
        System.out.println(drawEntrance(students));
        System.out.println(drawDiningRoom(students));
        System.out.println(drawTowerStorage(4));
    }
}

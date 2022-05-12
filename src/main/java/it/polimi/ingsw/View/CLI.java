package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Observer.ViewSubject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * this class implements the command line interface to play trough terminal, it's observed by the connectionSocket which sends messages
 * to the server according to the CLI updates and, in addition, it observes the model to update the graphics accordingly.
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
        System.out.println(cliDrawer.drawGameTable(new Game()));


    }

    /**
     * method used to read the username choice by the player
     */
    public void askUsername(){
        System.out.println("Enter your username:\n");
        String username=readUserInput();
        notifyObserver(obs->obs.onUsername(username));
    }

    /**
     * this method is used to ask the user the game settings he desires, which are the game mode and the number of Players
     */
    public void askGamePreferences(){
        System.out.println("Enter the number of players desired:\n");
        int numPlayers=Integer.parseInt(readUserInput());
        System.out.println("Now type the game mode preferred: Expert or Base?");
        String choiceGamemode=readUserInput();
        boolean gamemode=choiceGamemode.equals("Expert");
        notifyObserver(obs->obs.onGamePreferences(numPlayers, gamemode));
    }
    /*
    public static StringBuilder drawClouds(Cloud cloud1, Cloud cloud2){//~
        StringBuilder toPrint=new StringBuilder();
        toPrint.append(" ~~~~~     ~~~~~\n" +
                       "{     }   {     }\t\n" +
                       " ~~~~~     ~~~~~").append("\n");
        for(RealmColors color:RealmColors.values()){
            if(cloud1.getStudentsByColor(color)!=0){
                StringBuilder support=new StringBuilder();

                toPrint.append(CLIColors.realmColorsConverter(color));
                toPrint.append(cloud1.getStudentsByColor(color)).append(color);

                support.append(CLIColors.realmColorsConverter(color));
                support.append(cloud1.getStudentsByColor(color)).append(color);
                int offset=CLIColors.realmColorsConverter(color).toString().length();
                for(int i=0;support.length()-offset+i<10;i++){
                    toPrint.append(" ");
                }
            }
            if(cloud2.getStudentsByColor(color)!=0){
                toPrint.append(CLIColors.realmColorsConverter(color));
                toPrint.append(cloud2.getStudentsByColor(color)).append(color);
                toPrint.append("\n");
            }
        }
        return toPrint;
    }

    public static StringBuilder drawIsles(IsleManager isleManager){
        StringBuilder toPrint=new StringBuilder();

        for(int i=0;i<isleManager.getIsles().size();i++){
            toPrint.append("/¯¯¯¯¯¯¯\\   ");
        }
        toPrint.append("\n");
        for (int i=0;i<isleManager.getIsles().size();i++){
            toPrint.append("│     ");
            if(isleManager.getIsle(i).getMotherNature()){
                toPrint.append("X");
            }else{
                toPrint.append(" ");
            }

            if(isleManager.getIsle(i).getTowersColor()!=TowerColors.NOCOLOR){
                toPrint.append(CLIColors.towerColorsConverter(isleManager.getIsle(i).getTowersColor()));
                toPrint.append("T");
                toPrint.append(CLIColors.RESET);
            }else{
                toPrint.append(" ");
            }
            toPrint.append("│   ");
        }
        toPrint.append("\n");
        for (int i=0;i<isleManager.getIsles().size();i++){
            toPrint.append("│       │   ");
        }
        toPrint.append("\n");
        for (int i=0;i<isleManager.getIsles().size();i++) {
            toPrint.append("\\_______/   ");
        }
        toPrint.append("\n");

        for(RealmColors color:RealmColors.values()){
            for(int i=0;i< isleManager.getIsles().size();i++){
                StringBuilder subColor=new StringBuilder();

                toPrint.append(CLIColors.realmColorsConverter(color));
                toPrint.append(isleManager.getIsle(i).getStudentsByColor(color)).append(color);

                subColor.append(CLIColors.realmColorsConverter(color)).append(isleManager.getIsle(i).getStudentsByColor(color)).append(color);

                //number of students alignment
                //offset eliminates the characters used to codify the colors from the length of the string
                int offset=CLIColors.realmColorsConverter(color).toString().length();
                for(int j=0;subColor.length()-offset+j<12;j++){
                    toPrint.append(" ");
                }
            }
            toPrint.append("\n");
        }
        toPrint.append(CLIColors.RESET);
        return toPrint;
    }

    public static StringBuilder drawDashboard(Dashboard dashboard){
        int numTower=dashboard.getTowerStorage().getNumberOfTowers();

        StringBuilder entrance=drawEntrance(dashboard.getEntrance());
        StringBuilder diningRoom=drawDiningRoom(dashboard.getDiningRoom());

        StringBuilder toPrint=new StringBuilder();

        //titles
        String entranceTitle="Entrance";
        String diningTitle="DiningRoom";
        String towerTitle="TowerStorage";

        toPrint.append(entranceTitle).append("    ").append(diningTitle).append("                  ").append(towerTitle).append("\n");
        //Students cut and paste
        for(RealmColors color:RealmColors.values()){
            String substringEntrance= entrance.substring(entrance.indexOf(CLIColors.realmColorsConverter(color).toString()),entrance.indexOf("\n",entrance.indexOf(CLIColors.realmColorsConverter(color).toString())));
            String substringDining= diningRoom.substring(diningRoom.indexOf(CLIColors.realmColorsConverter(color).toString()),diningRoom.indexOf("\n",diningRoom.indexOf(CLIColors.realmColorsConverter(color).toString())));
            toPrint.append(substringEntrance);

            //Entrance and DiningRoom representation alignment
            //offset eliminates the characters used to codify the colors from the length of the string
            int offset=CLIColors.realmColorsConverter(color).toString().length()+CLIColors.RESET.toString().length();
            for(int i=1;substringEntrance.length()-offset+i<=10;i++){
                toPrint.append(" ");
            }
            toPrint.append("||  ").append(substringDining).append("  ");

            //Tower storage drawing
            toPrint.append(CLIColors.towerColorsConverter(dashboard.getTowerStorage().getTowerColor()));
            for(int j=0;j<2 && numTower>0;j++){
                toPrint.append("T ");
                numTower--;
            }
            toPrint.append(CLIColors.RESET).append("\n");



        }
        //tower cut and paste
        return toPrint;



    }

    public static StringBuilder drawDiningRoom(DiningRoom diningRoom){
        StringBuilder toPrint=new StringBuilder();
        toPrint.append("DiningRoom\n");
        for(RealmColors color: RealmColors.values()){
            toPrint.append(CLIColors.realmColorsConverter(color));
            for(int i=0;i<10;i++){
                if(i<diningRoom.getStudentsByColor(color)){
                    toPrint.append("S ");
                }else{
                    toPrint.append("X ");
                }
            }
            toPrint.append(CLIColors.RESET);
            toPrint.append("||");
            toPrint.append(CLIColors.realmColorsConverter(color));
            if(diningRoom.getProfessorByColor(color)==1){
                toPrint.append(" P");
            }else {
                toPrint.append(" X");
            }
            toPrint.append(CLIColors.RESET);
            toPrint.append("\n");
        }
        return toPrint;
    }

    public static StringBuilder drawEntrance(Entrance entrance){
        StringBuilder toPrint= new StringBuilder();

        toPrint.append("Entrance\n");
        for(RealmColors color:RealmColors.values()) {
            if (entrance.getStudentsByColor(color) == 0) {
                toPrint.append(CLIColors.realmColorsConverter(color)).append(CLIColors.RESET);
                toPrint.append("\n");
            } else {
                toPrint.append(CLIColors.realmColorsConverter(color));
                for (int i = 0; i < entrance.getStudentsByColor(color); i++) {
                    toPrint.append("S");
                }
                toPrint.append(CLIColors.RESET);
                toPrint.append("\n");
            }
        }
        return toPrint;
    }
    */

    public static void main(String[] args) {
        CLIDrawer cliDrawer=new CLIDrawer();
        cliDrawer.printTitle();
       // System.out.println(cliDrawer.drawGameTable());

    }

}

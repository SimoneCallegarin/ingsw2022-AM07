package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.DashboardObjects.Dashboard;
import it.polimi.ingsw.Model.DashboardObjects.DiningRoom;
import it.polimi.ingsw.Model.DashboardObjects.Entrance;
import it.polimi.ingsw.Model.DashboardObjects.TowerStorage;
import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Model.Enumeration.TowerColors;
import it.polimi.ingsw.Model.GameTableObjects.Cloud;
import it.polimi.ingsw.Model.GameTableObjects.Isle;
import it.polimi.ingsw.Model.GameTableObjects.IsleManager;
import it.polimi.ingsw.Observer.ModelObserver;
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
    BufferedReader br=new BufferedReader(new InputStreamReader(System.in));

    /**
     * method used to launch a thread for user input reading
     */
    //need to make it always run not only when the method is called
    public String readUserInput(){

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

    /**
     * this method draw the Dashboard. It uses other methods to get the StringBuilder objects which represent the entrance
     * and the dining room and then it cuts and paste them to make them show aligned on the CLI
     * @param dashboard the dashboard object which attributes are used to draw on the CLI
     * @return the string to print to show the Dashboard
     */
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

    /**
     * this method is used to draw the DiningRoom part of the Dashboard
     * @param diningRoom the DiningRoom object which attributes are used to draw on the CLI
     * @return the StringBuilder object passed to the drawDashboard method
     */
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


    public static void main(String[] args) {
        Dashboard dashboard=new Dashboard(4,1);
        IsleManager isleManager=new IsleManager();

        Entrance entrance=dashboard.getEntrance();
        DiningRoom diningRoom=dashboard.getDiningRoom();

        entrance.addStudent(RealmColors.RED);
        entrance.addStudent(RealmColors.RED);
        entrance.addStudent(RealmColors.RED);
        entrance.addStudent(RealmColors.YELLOW);
        entrance.addStudent(RealmColors.YELLOW);
        entrance.addStudent(RealmColors.PINK);
        entrance.addStudent(RealmColors.BLUE);

        diningRoom.addStudent(RealmColors.BLUE);
        diningRoom.addStudent(RealmColors.BLUE);
        diningRoom.addStudent(RealmColors.BLUE);
        diningRoom.addStudent(RealmColors.YELLOW);
        diningRoom.addStudent(RealmColors.YELLOW);
        diningRoom.addStudent(RealmColors.YELLOW);
        diningRoom.addStudent(RealmColors.RED);
        diningRoom.addStudent(RealmColors.GREEN);
        diningRoom.addStudent(RealmColors.GREEN);
        diningRoom.addStudent(RealmColors.GREEN);

        for(int i=0;i<isleManager.getIsles().size();i++){
            for(RealmColors colors:RealmColors.values()){
                for(int j=0;j<10;j++){
                    isleManager.getIsle(i).addStudent(colors);
                }
            }
        }
        //System.out.println(drawDashboard(dashboard));
        System.out.println(drawIsles(isleManager));
        Cloud cloud1=new Cloud(1,4);
        Cloud cloud2=new Cloud(2,4);
        cloud1.addStudent(RealmColors.BLUE);
        cloud1.addStudent(RealmColors.BLUE);
        cloud1.addStudent(RealmColors.BLUE);
        cloud2.addStudent(RealmColors.GREEN);
        cloud1.addStudent(RealmColors.GREEN);
        cloud2.addStudent(RealmColors.GREEN);
        cloud1.addStudent(RealmColors.GREEN);
        cloud2.addStudent(RealmColors.GREEN);
        cloud1.addStudent(RealmColors.GREEN);
        System.out.println(drawClouds(cloud1,cloud2));

    }
}

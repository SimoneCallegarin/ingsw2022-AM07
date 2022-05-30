package it.polimi.ingsw.View.GUI;




import it.polimi.ingsw.Controller.ClientController;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Network.ConnectionSocket;
import it.polimi.ingsw.Observer.ViewSubject;
import it.polimi.ingsw.View.View;

import javax.swing.*;

public class GUIApp extends ViewSubject {

    static GuiDrawer guiDrawer;


    public static void main(String[] args) {
        //create the EDT to manage the events
        SwingUtilities.invokeLater(GUIApp::createAndShowGUI);
        ConnectionSocket connectionSocket=new ConnectionSocket();
    }

    private static void createAndShowGUI(){
        System.out.println("Created GUI on EDT? "+
                SwingUtilities.isEventDispatchThread());
        Game game=new Game();
        game.addFirstPlayer("filo",true,3);
        game.addAnotherPlayer("calle");
        game.addAnotherPlayer("jack");
        //game.addAnotherPlayer("comfy");
        /*
        for(RealmColors color:RealmColors.values()){
            for(int i=0;i<10;i++){
                game.getPlayerByIndex(0).getDashboard().getDiningRoom().addStudent(color);
            }
            game.getPlayerByIndex(0).getDashboard().getDiningRoom().addProfessor(color);
        }
        */
        guiDrawer=new GuiDrawer(game);
    }

    public void askServerPreferences(){
        guiDrawer.ShowServerPreferencesForm();
        //need to notify the observer
    }

    public void askUserPreferences(){
        guiDrawer.ShowUserPreferencesForm();

    }

    public void askAssistantCard(int playerID){
        int turnOrder=guiDrawer.ShowAssistantCardForm(playerID);
        notifyObserver(obs->obs.onAssistantCard(turnOrder));
    }

    public void askMove(){
        //devi rendere cliccabili gli studenti nella entrance, la dining e le isole
        //oltre anche alle character cards
    }



}

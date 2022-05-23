package it.polimi.ingsw.View.GUI;



import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Model.Game;

import javax.swing.*;

public class GUIApp {

    public static void main(String[] args) {
        //create the EDT to manage the event
        SwingUtilities.invokeLater(GUIApp::createAndShowGUI);
    }

    private static void createAndShowGUI(){
        System.out.println("Created GUI on EDT? "+
                SwingUtilities.isEventDispatchThread());
        Game game=new Game();
        game.addFirstPlayer("filo",true,4);
        game.addAnotherPlayer("calle");
        game.addAnotherPlayer("jack");
        game.addAnotherPlayer("comfy");
        game.getPlayerByIndex(0).getDashboard().getDiningRoom().addStudent(RealmColors.RED);
        game.getPlayerByIndex(0).getDashboard().getDiningRoom().addStudent(RealmColors.RED);
        game.getPlayerByIndex(0).getDashboard().getDiningRoom().addStudent(RealmColors.RED);
        new GuiDrawer(game);
    }

}

package it.polimi.ingsw.View.GUI;



import it.polimi.ingsw.Model.Game;

import javax.swing.*;

public class GUIApp {

static Game game=new Game();

    public static void main(String[] args) {
        //create the EDT to manage the event
        SwingUtilities.invokeLater(() -> {
            GuiDrawer gd=createAndShowGUI();
        });
    }

    private static GuiDrawer createAndShowGUI(){
        System.out.println("Created GUI on EDT? "+
                SwingUtilities.isEventDispatchThread());
        game.addFirstPlayer("filo",true,4);
        game.addAnotherPlayer("jack");
        game.addAnotherPlayer("calle");
        game.addAnotherPlayer("comfy");

        GuiDrawer gd=new GuiDrawer(game);
        return gd;
    }



}

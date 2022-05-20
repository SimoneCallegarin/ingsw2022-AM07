package it.polimi.ingsw.View.GUI;



import javax.swing.*;

public class GUIApp {

    public static void main(String[] args) {
        //create the EDT to manage the event
        SwingUtilities.invokeLater(() -> {
            GuiDrawer gd=createAndShowGUI();
        });
    }

    private static GuiDrawer createAndShowGUI(){
        System.out.println("Created GUI on EDT? "+
                SwingUtilities.isEventDispatchThread());
        GuiDrawer gd=new GuiDrawer();
        return gd;
    }



}

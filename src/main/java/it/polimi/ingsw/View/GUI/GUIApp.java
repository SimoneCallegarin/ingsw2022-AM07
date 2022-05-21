package it.polimi.ingsw.View.GUI;



import javax.swing.*;

public class GUIApp {

    public static void main(String[] args) {
        //create the EDT to manage the event
        SwingUtilities.invokeLater(GUIApp::createAndShowGUI);
    }

    private static void createAndShowGUI(){
        System.out.println("Created GUI on EDT? "+
                SwingUtilities.isEventDispatchThread());
        new GuiDrawer();
    }

}

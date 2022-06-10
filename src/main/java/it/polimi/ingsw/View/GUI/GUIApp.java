package it.polimi.ingsw.View.GUI;

import it.polimi.ingsw.Controller.ClientController;
import it.polimi.ingsw.Model.CharacterCards.CharacterCardsName;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Network.ConnectionSocket;
import it.polimi.ingsw.Network.Messages.NetworkMessages.ServiceMessage;
import it.polimi.ingsw.Observer.ViewSubject;
import it.polimi.ingsw.View.StorageOfModelInformation.ModelStorage;
import it.polimi.ingsw.View.View;

import javax.swing.*;

public class GUIApp extends ViewSubject implements View {

    static GuiDrawer guiDrawer;
    static int playerID;

    public static void main(String[] args) {
        //create the EDT to manage the events
        GUIApp guiApp=new GUIApp();
        SwingUtilities.invokeLater(GUIApp::createAndShowGUI);
        ConnectionSocket connectionSocket=new ConnectionSocket();

    }

    private static void createAndShowGUI(){
        System.out.println("Created GUI on EDT? "+
                SwingUtilities.isEventDispatchThread());
        Game game=new Game();
        game.addFirstPlayer("filo",true,4);
        game.addAnotherPlayer("calle");
        game.addAnotherPlayer("jack");
        game.addAnotherPlayer("comfy");
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


    @Override
    public void askUsername() {

    }

    @Override
    public void askGamePreferences() {

    }

    @Override
    public void askAssistantCard(int playerID) {
        int turnOrder=guiDrawer.ShowAssistantCardForm(playerID);
        notifyObserver(obs->obs.onAssistantCard(turnOrder));
    }

    @Override
    public void askMove(boolean expertMode) {

    }

    @Override
    public void printMessage(ServiceMessage message) {

    }

    @Override
    public void printChanges() {

    }

    @Override
    public void askMNMovement(boolean expertMode) {

    }

    @Override
    public void askCloud(boolean expertMode) {

    }

    @Override
    public void askCharacterEffectParameters(CharacterCardsName characterName) {

    }

    @Override
    public void disconnect(ServiceMessage message) {

    }

}

package it.polimi.ingsw.View.GUI;

import it.polimi.ingsw.Controller.ClientController;
import it.polimi.ingsw.Model.CharacterCards.CharacterCardsName;
import it.polimi.ingsw.Network.ConnectionSocket;
import it.polimi.ingsw.Network.Messages.NetworkMessages.ServiceMessage;
import it.polimi.ingsw.Observer.ViewSubject;
import it.polimi.ingsw.View.View;

import javax.swing.*;

public class GUIApp extends ViewSubject implements View {
    GuiDrawer guiDrawer;


    public static void main(String[] args) {
        GUIApp guiApp=new GUIApp();
        GuiDrawer guiDrawer=new GuiDrawer();
        ConnectionSocket connectionSocket=new ConnectionSocket();
        ClientController clientController=new ClientController(guiApp,connectionSocket,true);
        clientController.setGuiDrawer(guiDrawer);
        guiApp.addObserver(clientController);
        guiDrawer.addObserver(clientController);
        connectionSocket.startConnection();
        connectionSocket.getClientListener().addObserver(clientController);
        SwingUtilities.invokeLater(() -> guiApp.GUIstart(guiDrawer));
    }

    public void GUIstart(GuiDrawer drawer){
        //it creates the first screen where the user inputs his game preferences
        this.guiDrawer=drawer;
        drawer.screeInitialization();
    }


    @Override
    public void askUsername() {
       guiDrawer.showUsernameForm();
    }

    @Override
    public void askGamePreferences() {
        guiDrawer.showGamePreferencesForm();
    }

    @Override
    public void askAssistantCard(int playerID) {
        int turnOrder=guiDrawer.ShowAssistantCardForm(playerID);
        notifyObserver(obs->obs.onAssistantCard(turnOrder));

    }

    @Override
    public void askMove(boolean expertMode) {
        guiDrawer.showMoveOptions(expertMode);
    }

    @Override
    public void printMessage(ServiceMessage message) {
        guiDrawer.showServiceMessage(message.getMessage());
    }

    @Override
    public void printChanges(int playerID) {
        guiDrawer.updateGameScreenPanel(playerID);
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

package it.polimi.ingsw.View.GUI;

import it.polimi.ingsw.Controller.ClientController;
import it.polimi.ingsw.Model.CharacterCards.CharacterCardsName;
import it.polimi.ingsw.Network.ConnectionSocket;
import it.polimi.ingsw.Network.Messages.NetworkMessages.ServiceMessage;
import it.polimi.ingsw.Observer.ViewSubject;
import it.polimi.ingsw.View.View;

import javax.swing.*;

public class GUIApp extends ViewSubject implements View {

    private GuiDrawer guiDrawer;

    private static void createAndShowGUI(){
        guiDrawer = new GuiDrawer();
        SwingUtilities.invokeLater(() -> GUIstart(guiDrawer));
    }

    public void GUIstart(GuiDrawer drawer){
        //it creates the first screen where the user inputs his game preferences
        this.guiDrawer = drawer;
        drawer.screeInitialization();
    }

    @Override
    public void askUsername() {
       String username= guiDrawer.showUsernameForm();
       notifyObserver(obs->obs.onUsername(username));
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
    public CLIDrawer getDrawer() {
        //return guiDrawer;
        return null;
    }

    @Override
    public void addObs(ClientController clientController) { addObserver(clientController); }

    @Override
    public void VIEWstart() {

    }

}

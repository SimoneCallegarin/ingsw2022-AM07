package it.polimi.ingsw.View.GUI;

import it.polimi.ingsw.Controller.ClientController;
import it.polimi.ingsw.Model.CharacterCards.CharacterCardsName;
import it.polimi.ingsw.Network.Messages.NetworkMessages.ServiceMessage;
import it.polimi.ingsw.Observer.ViewSubject;
import it.polimi.ingsw.View.CLI.CLIDrawer;
import it.polimi.ingsw.View.View;

import javax.swing.*;

public class GUIApp extends ViewSubject implements View {

    private final GuiDrawer guiDrawer;

    public GUIApp() {
        guiDrawer = new GuiDrawer();
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
    public void askMove() {
        guiDrawer.showMoveOptions(getGUIDrawer().getModelStorage().isGameMode());
    }

    @Override
    public void printMessage(ServiceMessage message) {
        guiDrawer.showServiceMessage(message.getMessage());
    }

    @Override
    public void printChanges() {
        guiDrawer.updateGameScreenPanel();
    }

    @Override
    public void printWinner(String winner, int winnerID) {}

    @Override
    public void askMNMovement() {
        guiDrawer.showMNMovement();
    }

    @Override
    public void askCloud() {

    }

    @Override
    public void askCharacterEffectParameters(CharacterCardsName characterName) {

    }

    @Override
    public CLIDrawer getCLIDrawer() {
        //return guiDrawer;
        return null;
    }

    @Override
    public void addObs(ClientController clientController) { addObserver(clientController); }

    @Override
    public void ViewStart() {
        SwingUtilities.invokeLater(guiDrawer::screeInitialization);
    }

    @Override
    public void disconnect(ServiceMessage message) {

    }

    public GuiDrawer getGUIDrawer() { return guiDrawer; }

}

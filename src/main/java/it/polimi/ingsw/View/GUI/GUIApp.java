package it.polimi.ingsw.View.GUI;

import it.polimi.ingsw.Network.ClientSide.ClientController;
import it.polimi.ingsw.Model.CharacterCards.CharacterCardsName;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.Network.Messages.NetworkMessages.ServiceMessage;
import it.polimi.ingsw.Observer.Subjects.ViewSubject;
import it.polimi.ingsw.View.CLI.CLIDrawer;
import it.polimi.ingsw.View.View;

import javax.swing.*;

public class GUIApp extends ViewSubject implements View {

    private final GUIDrawer guiDrawer;

    public GUIApp() { guiDrawer = new GUIDrawer(); }

    @Override
    public void askUsername() {
       guiDrawer.showUsernameForm();
       guiDrawer.userInputPanel.remove(0);
    }

    @Override
    public void askGamePreferences() { guiDrawer.showGamePreferencesForm(); }

    @Override
    public void askAssistantCard(int playerID) {
        int turnOrder = guiDrawer.ShowAssistantCardForm(playerID);
        notifyObserver(obs->obs.onAssistantCard(turnOrder));
    }

    @Override
    public void askMove() { guiDrawer.showMoveOptions(getGUIDrawer().getModelStorage().isExpertMode()); }

    @Override
    public void printMessage(ServiceMessage message) { guiDrawer.showServiceMessage(message.getMessage()); }

    @Override
    public void printKO(ServiceMessage message) { guiDrawer.showKOMessage(message.getMessage()); }

    @Override
    public void printChanges() { guiDrawer.updateGameScreenPanel(); }

    @Override
    public void printWinner(String winner, int winnerID) { /*!!!!!!!!!!!!!!!*/ }

    @Override
    public void askMNMovement() { guiDrawer.showMNMovement(); }

    @Override
    public void askCloud() { guiDrawer.showCloudChoice(); }

    @Override
    public void askCharacterEffectParameters(CharacterCardsName characterName) { guiDrawer.showEffectActivationChoice(characterName); }

    @Override
    public void addObs(ClientController clientController) { addObserver(clientController); }

    @Override
    public void ViewStart() { SwingUtilities.invokeLater(guiDrawer::screeInitialization); }

    @Override
    public void disconnect(ServiceMessage message) {
        getGUIDrawer().showErrorMessage(message.getMessage());
        System.out.println(message.getMessage());
        System.exit(1);
    }

    @Override
    public CLIDrawer getCLIDrawer() { return null; }

    @Override
    public GUIDrawer getGUIDrawer() { return guiDrawer; }

}

package it.polimi.ingsw.View.GUI;

import it.polimi.ingsw.Network.ClientSide.ClientController;
import it.polimi.ingsw.Model.CharacterCards.CharacterCardsName;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.Network.Messages.NetworkMessages.ServiceMessage;
import it.polimi.ingsw.Observer.Subjects.ViewSubject;
import it.polimi.ingsw.View.CLI.CLIDrawer;
import it.polimi.ingsw.View.View;

import javax.swing.*;

/**
 * GUI main class responsible to start the GUIDrawer and interface with the controller and the server architecture
 */
public class GUI extends ViewSubject implements View {
    /**
     * Gui Drawer reference used to interact with the GUI
     */
    private final GUIDrawer guiDrawer;

    /**
     * Constructor of GUI
     */
    public GUI() { guiDrawer = new GUIDrawer(); }

    /**
     * Adds an observer to the view (the GUI itself).
     * @param clientController the observer that observe the view.
     */
    @Override
    public void addObs(ClientController clientController) { addObserver(clientController); }

    /**
     * Starts the GUI.
     */
    @Override
    public void viewStart() { SwingUtilities.invokeLater(guiDrawer::screeInitialization); }

    /**
     * Prints messages on screen.
     * @param message the message that will be printed.
     */
    @Override
    public void printMessage(ServiceMessage message) { guiDrawer.showServiceMessage(message.getMessage()); }

    /**
     * Prints a KO message on screen.
     * @param message the KO message that has to be printed.
     */
    @Override
    public void printKO(ServiceMessage message) { guiDrawer.showKOMessage(message.getMessage()); }

    /**
     * Each time the player does an action that modifies the game table, then this method will be called,
     * and then it will be printed again the game table with the ultimate changes.
     */
    @Override
    public void printChanges() { guiDrawer.updateGameScreenPanel(); }

    /**
     * Prints end game message.
     * @param winner is the name of the winner.
     * @param winnerID is -1 if the game ended in a draw.
     */
    @Override
    public void printWinner(String winner, int winnerID) { guiDrawer.showWinner(winner); }

    /**
     * Reads the username chosen by the player and notifies it to the view.
     */
    @Override
    public void askUsername() {
       guiDrawer.showUsernameForm();
       guiDrawer.getUserInputPanel().remove(0);
    }

    /**
     * Notifies the view with the number of players ad the game mode the player decided to play with.
     */
    @Override
    public void askGamePreferences() { guiDrawer.showGamePreferencesForm(); }

    /**
     * Asks the player which assistant card he wants to play.
     * Notifies the choice to the ClientController.
     * @param playerID the ID of the player who is playing the assistant card.
     */
    @Override
    public void askAssistantCard(int playerID) {
        int turnOrder = guiDrawer.ShowAssistantCardForm(playerID);
        notifyObserver(obs->obs.onAssistantCard(turnOrder));
    }

    /**
     * Asks the player what does he want to do when the game is at the beginning of the action phase in its first step.
     */
    @Override
    public void askMove() { guiDrawer.showMoveOptions(getGUIDrawer().getModelStorage().isExpertMode()); }

    /**
     * Asks the player what does he want to do when the game is in the action phase in its second step.
     */
    @Override
    public void askMNMovement() { guiDrawer.showMNMovement(); }

    /**
     * Asks the player what does he want to do when the game is in the action phase in its third step.
     */
    @Override
    public void askCloud() { guiDrawer.showCloudChoice(); }

    /**
     * Asks the player certain parameters for the correct activation of the effect of the character cards.
     * @param characterName the name of the character card played.
     */
    @Override
    public void askCharacterEffectParameters(CharacterCardsName characterName) { guiDrawer.showEffectActivationChoice(characterName); }

    /**
     * Handles the disconnection of the client by shutting down the thread for the input
     * and by printing why the client has to be disconnected.
     * @param message containing information about why the player is being disconnected that will be printed.
     */
    @Override
    public void disconnect(ServiceMessage message) {
        getGUIDrawer().showErrorMessage(message.getMessage());
        System.out.println(message.getMessage());
        System.exit(1);
    }

    /**
     * Getter method for the CLIDrawer.
     * @return null because the GUI doesn't have a CLIDrawer.
     */
    @Override
    public CLIDrawer getCLIDrawer() { return null; }

    /**
     * Getter method for the GUIDrawer.
     * @return the GUIDrawer.
     */
    @Override
    public GUIDrawer getGUIDrawer() { return guiDrawer; }

}

package it.polimi.ingsw.View;

import it.polimi.ingsw.Network.ClientSide.ClientController;
import it.polimi.ingsw.Model.CharacterCards.CharacterCardsName;
import it.polimi.ingsw.Network.Messages.NetworkMessages.ServiceMessage;
import it.polimi.ingsw.View.CLI.CLIDrawer;
import it.polimi.ingsw.View.GUI.GUIDrawer;

/**
 * Interface for the CLi and the GUI, offers common method to print on screen game things.
 */
public interface View {

     /**
      * Starts the view.
      */
     void ViewStart();

     /**
      * Asks the player to write his nickname.
      */
     void askUsername();

     /**
      * Asks the player to select his game preferences.
      */
     void askGamePreferences();

     /**
      * Asks the player which assistant card he wants to play.
      */
     void askAssistantCard(int playerID);

     /**
      * Asks the player where he wants to move mother nature.
      */
     void askMNMovement();

     /**
      * Asks the player which cloud he wants to pick the students from.
      */
     void askCloud();

     /**
      * Asks the player the values needed for the activation of a character card effect.
      */
     void askCharacterEffectParameters(CharacterCardsName characterName);

     /**
      * Asks the player what does he want to do when the game is at the beginning of the action phase in its first step.
      */
     void askMove();

     /**
      * Prints a message.
      * @param message the message that has to be printed.
      */
     void printMessage(ServiceMessage message);

     /**
      * Prints a KO message.
      * @param message the KO message that has to be printed.
      */
     void printKO(ServiceMessage message);

     /**
      * Prints the changes of the model.
      */
     void printChanges();

     /**
      * Prints the nickname of who won.
      * @param winner nickname of the winner.
      * @param winnerID ID of the winner.
      */
     void printWinner(String winner, int winnerID);

     /**
      * Handles the disconnection of a player.
      * @param message message containing disconnection information.
      */
     void disconnect(ServiceMessage message);

     /**
      * Adds an observer to the view.
      * @param clientController the Observer of the view.
      */
     void addObs(ClientController clientController);

     /**
      * Getter method for the CLIDrawer.
      * @return the CLIDrawer.
      */
     CLIDrawer getCLIDrawer();

     /**
      * Getter method for the GUIDrawer.
      * @return the GUIDrawer.
      */
     GUIDrawer getGUIDrawer();
}

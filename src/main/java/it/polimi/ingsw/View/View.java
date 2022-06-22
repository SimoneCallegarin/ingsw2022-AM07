package it.polimi.ingsw.View;

import it.polimi.ingsw.Network.ClientSide.ClientController;
import it.polimi.ingsw.Model.CharacterCards.CharacterCardsName;
import it.polimi.ingsw.Network.Messages.NetworkMessages.ServiceMessage;
import it.polimi.ingsw.View.CLI.CLIDrawer;
import it.polimi.ingsw.View.GUI.GUIDrawer;

public interface View {

     void askUsername();

     void askGamePreferences();

     void askAssistantCard(int playerID);

     void askMove();

     void printMessage(ServiceMessage message);

     void printChanges();

     void printWinner(String winner, int winnerID);

     void askMNMovement();

     void askCloud();

     void askCharacterEffectParameters(CharacterCardsName characterName);

     void disconnect(ServiceMessage message);

     CLIDrawer getCLIDrawer();

     GUIDrawer getGUIDrawer();

     void ViewStart();

     void addObs(ClientController clientController);
}

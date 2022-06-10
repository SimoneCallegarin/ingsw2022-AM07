package it.polimi.ingsw.View;

import it.polimi.ingsw.Controller.ClientController;
import it.polimi.ingsw.Model.CharacterCards.CharacterCardsName;
import it.polimi.ingsw.Network.Messages.NetworkMessages.ServiceMessage;
import it.polimi.ingsw.View.CLI.CLIDrawer;
import it.polimi.ingsw.View.GUI.GuiDrawer;

public interface View {

     void askUsername();

     void askGamePreferences();

     void askAssistantCard(int playerID);

     void askMove(boolean expertMode);

     void printMessage(ServiceMessage message);

     void printChanges(int playerID);

     void askMNMovement(boolean expertMode);

     void askCloud(boolean expertMode);

     void askCharacterEffectParameters(CharacterCardsName characterName);

     void disconnect(ServiceMessage message);

     CLIDrawer getCLIDrawer();

     GuiDrawer getGUIDrawer();

     void ViewStart();

     void addObs(ClientController clientController);
}

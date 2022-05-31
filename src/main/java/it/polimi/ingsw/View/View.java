package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.CharacterCards.CharacterCardsName;
import it.polimi.ingsw.Network.Messages.NetworkMessages.ServiceMessage;
import it.polimi.ingsw.View.StorageOfModelInformation.ModelStorage;

public interface View {

     void askUsername();

     void askGamePreferences();

     void askAssistantCard(int playerID);

     void askMove(boolean expertMode);

     void printMessage(ServiceMessage message);

     void printChanges();

     void askMNMovement();

     void askCloud();

     void askCharacterEffectParameters(CharacterCardsName characterName);



}

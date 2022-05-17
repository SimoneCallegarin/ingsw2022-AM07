package it.polimi.ingsw.Network.Messages.NetworkMessages;

import it.polimi.ingsw.Network.Messages.MessageType;

/**
 * this message is sent everytime a player plays an Assistant Card
 */
public class AssistCard_UpdateMsg extends NetworkMessage {
    MessageType messageType;
    int idPlayer;
    int turnOrderPlayed;

    public AssistCard_UpdateMsg(MessageType messageType, int idPlayer, int turnOrderPlayed) {
        super(messageType);
        this.idPlayer=idPlayer;
        this.turnOrderPlayed=turnOrderPlayed;
    }
}

package it.polimi.ingsw.Network.Messages.NetworkMessages;

import it.polimi.ingsw.Network.Messages.MessageType;

/**
 * this message is sent everytime a player plays an Assistant Card
 */
public class AssistCard_UpdateMsg extends NetworkMessage {

    int idPlayer;

    int turnOrderPlayed;
    int mnMovement;

    public AssistCard_UpdateMsg(MessageType messageType, int idPlayer, int turnOrderPlayed, int mnMovement) {
        super(messageType);
        this.idPlayer=idPlayer;
        this.turnOrderPlayed=turnOrderPlayed;
        this.mnMovement = mnMovement;
    }

    public int getIdPlayer() {
        return idPlayer;
    }

    public int getTurnOrderPlayed() {
        return turnOrderPlayed;
    }

    public int getMnMovement() {
        return mnMovement;
    }
}

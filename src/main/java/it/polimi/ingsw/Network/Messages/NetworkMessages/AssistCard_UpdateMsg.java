package it.polimi.ingsw.Network.Messages.NetworkMessages;

import it.polimi.ingsw.Network.Messages.MessageType;

import java.util.List;

/**
 * this message is sent everytime a player plays an Assistant Card
 */
public class AssistCard_UpdateMsg extends NetworkMessage {

    int idPlayer;
    int turnOrderPlayed;
    int movementMNPlayed;
    List<Integer> turnOrderDiscardPile;
    List<Integer> movementMNDiscardPile;

    public AssistCard_UpdateMsg(MessageType messageType, int idPlayer, int turnOrderPlayed, int movementMNPlayed, List<Integer> turnOrderDiscardPile,List<Integer> movementMNDiscardPile) {
        super(messageType);
        this.idPlayer=idPlayer;
        this.turnOrderPlayed=turnOrderPlayed;
        this.movementMNPlayed=movementMNPlayed;
        this.turnOrderDiscardPile=turnOrderDiscardPile;
        this.movementMNDiscardPile=movementMNDiscardPile;
    }

    public int getIdPlayer() {
        return idPlayer;
    }

    public int getTurnOrderPlayed() {
        return turnOrderPlayed;
    }

    public int getMovementMNPlayed() {
        return movementMNPlayed;
    }
}

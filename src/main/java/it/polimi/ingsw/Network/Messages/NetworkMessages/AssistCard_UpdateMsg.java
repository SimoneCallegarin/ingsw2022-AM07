package it.polimi.ingsw.Network.Messages.NetworkMessages;

import it.polimi.ingsw.Network.Messages.MessageType;

import java.util.ArrayList;

/**
 * this message is sent everytime a player plays an Assistant Card
 */
public class AssistCard_UpdateMsg extends NetworkMessage {
    private final int idPlayer;
    private final int turnOrderPlayed;
    private final int movementMNPlayed;
    private final ArrayList<Integer> turnOrders;
    private final ArrayList<Integer> movementsMN;

    public AssistCard_UpdateMsg(MessageType messageType, int idPlayer, int turnOrderPlayed, int movementMNPlayed, ArrayList<Integer> turnOrdersAvailable, ArrayList<Integer> movementsMNAvailable) {
        super(messageType);
        this.idPlayer=idPlayer;
        this.turnOrderPlayed=turnOrderPlayed;
        this.movementMNPlayed=movementMNPlayed;
        this.turnOrders= turnOrdersAvailable;
        this.movementsMN= movementsMNAvailable;
    }

    public int getIdPlayer() { return idPlayer; }

    public int getTurnOrderPlayed() { return turnOrderPlayed; }

    public int getMovementMNPlayed() { return movementMNPlayed; }

    public ArrayList<Integer> getTurnOrders() { return turnOrders; }

    public ArrayList<Integer> getMovementsMN() { return movementsMN; }

}

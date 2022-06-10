package it.polimi.ingsw.Network.Messages.NetworkMessages;

import it.polimi.ingsw.Network.Messages.MessageType;

import java.util.ArrayList;

/**
 * Message sent everytime a player plays an Assistant Card.
 */
public class AssistCard_UpdateMsg extends NetworkMessage {
    /**
     * ID of the player that played the assistant card.
     */
    private final int playerID;
    /**
     * Turn order of the assistant card played that now is in the discard pile.
     */
    private final int turnOrderPlayed;
    /**
     * Mother nature possible movement of the assistant card played that now is in the discard pile.
     */
    private final int movementMNPlayed;
    /**
     * List of all the turn orders of the assistant cards that the player hasn't played yet.
     */
    private final ArrayList<Integer> turnOrders;
    /**
     * List of all the possible mother nature movement of the assistant cards that the player hasn't played yet.
     */
    private final ArrayList<Integer> movementsMN;

    /**
     *
     * @param messageType It will be ASSISTANTCARD_UPDATE.
     * @param playerID ID of the player that played the assistant card.
     * @param turnOrderPlayed Turn order of the assistant card played that now is in the discard pile.
     * @param movementMNPlayed Mother nature possible movement of the assistant card played that now is in the discard pile.
     * @param turnOrdersAvailable List of all the turn orders of the assistant cards that the player hasn't played yet.
     * @param movementsMNAvailable List of all the possible mother nature movement of the assistant cards that the player hasn't played yet.
     */
    public AssistCard_UpdateMsg(MessageType messageType, int playerID, int turnOrderPlayed, int movementMNPlayed, ArrayList<Integer> turnOrdersAvailable, ArrayList<Integer> movementsMNAvailable) {
        super(messageType);
        this.playerID = playerID;
        this.turnOrderPlayed=turnOrderPlayed;
        this.movementMNPlayed=movementMNPlayed;
        this.turnOrders= turnOrdersAvailable;
        this.movementsMN= movementsMNAvailable;
    }

    // GETTERS:

    public int getPlayerID() { return playerID; }

    public int getTurnOrderPlayed() { return turnOrderPlayed; }

    public int getMovementMNPlayed() { return movementMNPlayed; }

    public ArrayList<Integer> getTurnOrders() { return turnOrders; }

    public ArrayList<Integer> getMovementsMN() { return movementsMN; }

}

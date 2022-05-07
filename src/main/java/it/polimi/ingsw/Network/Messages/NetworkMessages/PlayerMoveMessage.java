package it.polimi.ingsw.Network.Messages.NetworkMessages;

import it.polimi.ingsw.Network.Messages.MessageType;

/**
 * this is the class used to communicate the moves made by the different players
 */
public class PlayerMoveMessage extends NetworkMessages {

    private final int playerID;
    /**
     * Contains:
     * - the ids of the places where the player makes the move
     * - the color index chosen by the player
     * - the assistant card turn order
     * - the chosen character card index that the player wants to play
     */
    private final int genericValue;

    public PlayerMoveMessage(MessageType messageType, int playerID, int genericValue) {
        super(messageType);
        this.playerID = playerID;
        this.genericValue = genericValue;
    }

    public int getPlayerID() { return playerID; }

    public int getGenericValue() { return genericValue; }

    @Override
    public String toString() {
        return "Message{" +
                "messageType=" + getMessageType() +
                ", playerID=" + playerID +
                ", genericValue=" + genericValue +
                '}';
    }
}
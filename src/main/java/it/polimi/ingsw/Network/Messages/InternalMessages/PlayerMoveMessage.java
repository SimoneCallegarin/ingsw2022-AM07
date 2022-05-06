package it.polimi.ingsw.Network.Messages.InternalMessages;

import it.polimi.ingsw.Network.Messages.MessageType;

/**
 * this is the class used to communicate the moves made by the different players
 */
public class PlayerMoveMessage extends InternalMessages {

    /**
     * Contains:
     * - the ids of the places where the player makes the move
     * - the color index chosen by the player
     * - the assistant card turn order
     * - the chosen character card index that the player wants to play
     */
    public final int genericValue;

    public PlayerMoveMessage(MessageType messageType, int playerID, int genericValue) {
        super(playerID, messageType);
        this.genericValue = genericValue;
    }

    public int getGenericValue() { return genericValue; }

    @Override
    public String toString() {
        return "Message{" +
                "messageType=" + getMessageType() +
                ", playerID=" + getPlayerID() +
                ", genericValue=" + genericValue +
                '}';
    }
}

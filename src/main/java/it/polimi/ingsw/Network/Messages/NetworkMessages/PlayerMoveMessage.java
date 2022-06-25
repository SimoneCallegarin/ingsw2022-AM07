package it.polimi.ingsw.Network.Messages.NetworkMessages;

import it.polimi.ingsw.Network.Messages.MessageType;

/**
 * Class used to communicate the moves made by the different players.
 */
public class PlayerMoveMessage extends NetworkMessage {

    /**
     * The ID of the player that is making the move.
     */
    private final int playerID;
    /**
     * Contains:
     * - the IDs of the places where the player makes the move;
     * - the color index chosen by the player;
     * - the assistant card turn order;
     * - the chosen character card index that the player wants to play.
     */
    private final int genericValue;

    /**
     * PlayerMoveMessage constructor.
     * @param messageType the type of the message.
     * @param playerID the ID of the player that is making the move.
     * @param genericValue assumes different meaning relying on the scope where is used.
     */
    public PlayerMoveMessage(MessageType messageType, int playerID, int genericValue) {
        super(messageType);
        this.playerID = playerID;
        this.genericValue = genericValue;
    }

    /**
     * Getter method for the playerID.
     * @return the ID of the player that is making the move.
     */
    public int getPlayerID() { return playerID; }

    /**
     * Getter method for the generic value.
     * @return the value itself.
     */
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
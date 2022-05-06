package it.polimi.ingsw.Network.Messages.InternalMessages;

import it.polimi.ingsw.Network.Messages.MessageType;

public abstract class InternalMessages {

    private final MessageType messageType;
    /**
     * The ID associated to the player in the match that he is playing.
     */
    private final int playerID;

    InternalMessages(int playerID, MessageType messageType) {
        this.playerID = playerID;
        this.messageType = messageType;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public int getPlayerID() {
        return playerID;
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageType=" + messageType +
                "playerID=" + playerID +
                '}';
    }
}

package it.polimi.ingsw.Network.Messages.NetworkMessages;


import it.polimi.ingsw.Network.Messages.MessageType;

import java.io.Serializable;

/**
 * Messages exchanged on the net.
 */
public abstract class NetworkMessage implements Serializable {

    /**
     * Identifies the message exchanged.
     */
    private final MessageType messageType;

    /**
     * NetworkMessage constructor.
     * @param messageType that identifies the message.
     */
    NetworkMessage(MessageType messageType) { this.messageType = messageType; }

    /**
     * Getter method for the type of the message.
     * @return the message type.
     */
    public MessageType getMessageType() { return messageType; }

}



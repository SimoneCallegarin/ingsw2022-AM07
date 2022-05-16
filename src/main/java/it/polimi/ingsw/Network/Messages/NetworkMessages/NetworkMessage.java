package it.polimi.ingsw.Network.Messages.NetworkMessages;


import it.polimi.ingsw.Network.Messages.MessageType;

import java.io.Serializable;

public abstract class NetworkMessage implements Serializable {

    private final MessageType messageType;

    NetworkMessage(MessageType messageType) { this.messageType = messageType; }

    public MessageType getMessageType() { return messageType; }

    /*@Override
    public String toString() {
        return "Message{" +
                "messageType=" + messageType +
                '}';
    }*/
}



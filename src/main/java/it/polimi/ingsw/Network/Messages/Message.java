package it.polimi.ingsw.Network.Messages;

/**
 * the main Message class which defines the utility methods
 */
public class Message {
    public final MessageType messageType;

    public Message(MessageType messageType) {
        this.messageType = messageType;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageType=" + messageType +
                '}';
    }
}

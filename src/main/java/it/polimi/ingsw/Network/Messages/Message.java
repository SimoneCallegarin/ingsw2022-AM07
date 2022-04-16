package it.polimi.ingsw.Network.Messages;

/**
 * the main Message class which defines the utility methods
 */
public class Message {
    public MessageType messageType;
    public int playerID;
    public int genericValue;//this value contains the ids of the places where the player makes the move


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

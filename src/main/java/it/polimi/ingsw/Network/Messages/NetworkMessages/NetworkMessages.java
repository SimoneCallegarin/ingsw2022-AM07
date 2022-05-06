package it.polimi.ingsw.Network.Messages.NetworkMessages;


import it.polimi.ingsw.Network.Messages.MessageType;

public abstract class NetworkMessages {

    private final MessageType messageType;
    private final String nickName;

    NetworkMessages(String nickName, MessageType messageType) {
        this.messageType = messageType;
        this.nickName = nickName;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public String getNickName() { return nickName; }

    @Override
    public String toString() {
        return "Message{" +
                "messageType=" + messageType +
                ", nickName=" + nickName + '\'' +
                '}';
    }
}



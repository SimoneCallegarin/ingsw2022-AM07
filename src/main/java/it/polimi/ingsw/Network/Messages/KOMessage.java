package it.polimi.ingsw.Network.Messages;

public class KOMessage {
    public MessageType messageType;
    public String ko = "KO";

    public MessageType getMessageType() {
        return messageType;
    }

    @Override
    public String toString() {
        return "RequestNicknameMessage{" +
                "messageType=" + messageType +
                ", nickName='" + ko + '\'' +
                '}';
    }
}

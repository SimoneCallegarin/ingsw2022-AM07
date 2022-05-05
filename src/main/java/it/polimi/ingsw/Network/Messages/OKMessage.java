package it.polimi.ingsw.Network.Messages;

public class OKMessage {
    public MessageType messageType;
    public String ok = "OK";

    public MessageType getMessageType() {
        return messageType;
    }

    @Override
    public String toString() {
        return "RequestNicknameMessage{" +
                "messageType=" + messageType +
                ", nickName='" + ok + '\'' +
                '}';
    }
}

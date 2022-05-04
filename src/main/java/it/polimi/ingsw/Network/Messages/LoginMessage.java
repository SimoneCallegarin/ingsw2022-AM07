package it.polimi.ingsw.Network.Messages;

public class LoginMessage {
    public MessageType messageType;
    public String nickName;

    public MessageType getMessageType() { return messageType; }

    public String getNickName() { return nickName; }

    @Override
    public String toString() {
        return "RequestNicknameMessage{" +
                "messageType=" + messageType +
                ", nickName='" + nickName + '\'' +
                '}';
    }
}

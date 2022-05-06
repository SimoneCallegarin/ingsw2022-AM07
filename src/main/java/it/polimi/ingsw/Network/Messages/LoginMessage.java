package it.polimi.ingsw.Network.Messages;

public class LoginMessage implements Message {
    public MessageType messageType;
    public String nickname;

    public LoginMessage(String nickname) {
        this.messageType = MessageType.LOGIN;
        this.nickname = nickname;
    }

    @Override
    public MessageType getMessageType() { return messageType; }

    public String getNickname() { return nickname; }

    @Override
    public String toString() {
        return "RequestNicknameMessage{" +
                "messageType=" + messageType +
                ", nickName='" + nickname + '\'' +
                '}';
    }
}

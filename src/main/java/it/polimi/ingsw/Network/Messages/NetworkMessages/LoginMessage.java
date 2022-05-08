package it.polimi.ingsw.Network.Messages.NetworkMessages;

import it.polimi.ingsw.Network.Messages.MessageType;

public class LoginMessage extends NetworkMessage {

    private final String nickname;

    public LoginMessage(String nickname) {
        super(MessageType.LOGIN);
        this.nickname = nickname;
    }

    public String getNickname(){ return nickname; }

    @Override
    public String toString() {
        return "RequestNicknameMessage{" +
                "messageType=" + getMessageType() +
                ", nickname=" + nickname +
                '}';
    }
}
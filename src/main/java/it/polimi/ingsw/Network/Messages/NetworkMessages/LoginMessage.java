package it.polimi.ingsw.Network.Messages.NetworkMessages;

import it.polimi.ingsw.Network.Messages.MessageType;

public class LoginMessage extends NetworkMessages {

    LoginMessage(String nickname) { super(nickname, MessageType.LOGIN); }

    @Override
    public String toString() {
        return "RequestNicknameMessage{" +
                "messageType=" + getMessageType() +
                ", nickName='" + getNickName() + '\'' +
                '}';
    }
}
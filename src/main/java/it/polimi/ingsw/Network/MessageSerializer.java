package it.polimi.ingsw.Network;

import com.google.gson.Gson;
import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.Network.Messages.NetworkMessages.GamePreferencesMessage;
import it.polimi.ingsw.Network.Messages.NetworkMessages.LoginMessage;
import it.polimi.ingsw.Network.Messages.NetworkMessages.NetworkMessage;

public class MessageSerializer {

    private final Gson g;

    public MessageSerializer() {
        this.g = new Gson();
    }

    public String serializeMessage(NetworkMessage message, MessageType mt) {

        switch (mt) {
            case LOGIN -> {
                LoginMessage logMessage = (LoginMessage) message;
                return g.toJson(logMessage);
            }
            case GAME_SETUP_INFO -> {
                GamePreferencesMessage gSetMessage = (GamePreferencesMessage) message;
                return g.toJson(gSetMessage);
            }
            default -> {
                return "null";
            }
        }
    }
}

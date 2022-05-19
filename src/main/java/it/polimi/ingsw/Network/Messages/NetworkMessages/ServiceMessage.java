package it.polimi.ingsw.Network.Messages.NetworkMessages;

import it.polimi.ingsw.Network.Messages.MessageType;

public class ServiceMessage extends NetworkMessage {

    private final String message;
    private final int playerID;

    public ServiceMessage(MessageType messageType, String message, int playerID) {
        super(messageType);
        this.message = message;
        this.playerID = playerID;
    }

    public ServiceMessage(MessageType messageType, String message) {
        super(messageType);
        this.message = message;
        this.playerID = -1;
    }

    public ServiceMessage(MessageType messageType) {
        super(messageType);
        this.message = "null";
        this.playerID = -1;
    }

    public int getPlayerID() {
        return playerID;
    }

    public String getMessage(){ return message; }

}

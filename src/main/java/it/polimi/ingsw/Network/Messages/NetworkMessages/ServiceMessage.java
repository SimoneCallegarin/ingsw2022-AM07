package it.polimi.ingsw.Network.Messages.NetworkMessages;

import it.polimi.ingsw.Network.Messages.MessageType;

/**
 * Message sent everytime it's necessary to notify the player with a service communication.
 */
public class ServiceMessage extends NetworkMessage {

    /**
     * Service communication that contains useful information for the player.
     */
    private final String message;
    /**
     * ID of the player that will receive the service communication.
     */
    private final int playerID;

    /**
     * ServiceMessage first constructor.
     * @param messageType it will be MATCH_JOINED or END_GAME.
     * @param message contains useful information for the player based on the messageType.
     * @param playerID ID of the player associated to the player that joined the match or that won the game.
     */
    public ServiceMessage(MessageType messageType, String message, int playerID) {
        super(messageType);
        this.message = message;
        this.playerID = playerID;
    }

    /**
     * ServiceMessage second constructor.
     * @param messageType it will be QUIT, KO, OK, GAMEPHASE_UPDATE or UNAVAILABLE_USERNAME.
     * @param message contains useful information for the player based on the messageType.
     */
    public ServiceMessage(MessageType messageType, String message) {
        super(messageType);
        this.message = message;
        this.playerID = -1;
    }

    /**
     * ServiceMessage third constructor.
     * @param messageType it will be PING, LOGOUT or USERNAME_ACCEPTED.
     */
    public ServiceMessage(MessageType messageType) {
        super(messageType);
        this.message = "null";
        this.playerID = -1;
    }

    /**
     * Getter method for the player ID.
     * @return the player ID contained in the message.
     */
    public int getPlayerID() { return playerID; }

    /**
     * Getter method for the service communication.
     * @return the service communication contained in the message.
     */
    public String getMessage(){ return message; }

}

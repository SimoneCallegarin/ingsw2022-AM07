package it.polimi.ingsw.Network.Messages;

/**
 * this type of message is used to communicate the game setup choices by the first player and the username choices
 */
public class SetupMessage {
    public MessageType messageType;
    public String user_choice;//for the username and the number of players
    public boolean gamemode;//false for base game, true for expert game

    public MessageType getMessageType() {
        return messageType;
    }

    @Override
    public String toString() {
        return "SetupMessage{" +
                "messageType=" + messageType +
                ", user_choice='" + user_choice + '\'' +
                ", gamemode=" + gamemode +
                '}';
    }
}

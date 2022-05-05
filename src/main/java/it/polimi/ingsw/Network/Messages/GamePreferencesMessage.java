package it.polimi.ingsw.Network.Messages;

public class GamePreferencesMessage {
    public MessageType messageType;
    public int numberOfPlayers;
    public boolean gameMode;

    public MessageType getMessageType() {
        return messageType;
    }

    public int getNumberOfPlayers() { return numberOfPlayers; }

    public boolean isGameMode() { return gameMode; }

    @Override
    public String toString() {
        return "GamePreferencesMessage{" +
                "messageType=" + messageType +
                ", numberOfPlayers=" + numberOfPlayers +
                ", gameMode=" + gameMode +
                '}';
    }
}

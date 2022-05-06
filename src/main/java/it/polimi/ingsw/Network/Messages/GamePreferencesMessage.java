package it.polimi.ingsw.Network.Messages;

public class GamePreferencesMessage implements Message {
    public MessageType messageType;
    public int numberOfPlayers;
    public boolean expertMode;

    public GamePreferencesMessage(int numberOfPlayers, boolean expertMode) {
        this.messageType = MessageType.GAME_SETUP_INFO;
        this.numberOfPlayers = numberOfPlayers;
        this.expertMode = expertMode;
    }

    @Override
    public MessageType getMessageType() {
        return messageType;
    }

    public int getNumberOfPlayers() { return numberOfPlayers; }

    public boolean isExpert() { return expertMode; }

    @Override
    public String toString() {
        return "GamePreferencesMessage{" +
                "messageType=" + messageType +
                ", numberOfPlayers=" + numberOfPlayers +
                ", expertMode=" + expertMode +
                '}';
    }
}

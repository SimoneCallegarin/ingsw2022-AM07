package it.polimi.ingsw.Network.Messages.NetworkMessages;

import it.polimi.ingsw.Network.Messages.MessageType;

public class GamePreferencesMessage extends NetworkMessages {
    public int numberOfPlayers;
    public boolean gameMode;

    GamePreferencesMessage(String playerNickName, int numberOfPlayers, boolean gameMode) {
        super(playerNickName, MessageType.GAME_SETUP_INFO);
        this.numberOfPlayers = numberOfPlayers;
        this.gameMode = gameMode;
    }

    public int getNumberOfPlayers() { return numberOfPlayers; }

    public boolean isGameMode() { return gameMode; }

    @Override
    public String toString() {
        return "GamePreferencesMessage{" +
                "messageType=" + getMessageType() +
                ", nickName=" + getNickName() + '\'' +
                ", numberOfPlayers=" + numberOfPlayers +
                ", gameMode=" + gameMode +
                '}';
    }
}

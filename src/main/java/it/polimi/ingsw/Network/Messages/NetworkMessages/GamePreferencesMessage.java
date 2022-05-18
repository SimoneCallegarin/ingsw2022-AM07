package it.polimi.ingsw.Network.Messages.NetworkMessages;

import it.polimi.ingsw.Network.Messages.MessageType;

public class GamePreferencesMessage extends NetworkMessage {
    private final int numberOfPlayers;
    private final boolean expertMode;

    public GamePreferencesMessage(int numberOfPlayers, boolean gameMode) {
        super(MessageType.GAME_SETUP_INFO);
        this.numberOfPlayers = numberOfPlayers;
        this.expertMode = gameMode;
    }

    public int getNumberOfPlayers() { return numberOfPlayers; }

    public boolean isExpert() { return expertMode; }

}

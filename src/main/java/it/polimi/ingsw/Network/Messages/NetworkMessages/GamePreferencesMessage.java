package it.polimi.ingsw.Network.Messages.NetworkMessages;

import it.polimi.ingsw.Network.Messages.MessageType;

/**
 * Message sent when the player chose the game preferences (number of players and game mode).
 */
public class GamePreferencesMessage extends NetworkMessage {

    /**
     * Number of players chosen by the player.
     */
    private final int numberOfPlayers;
    /**
     * Game mode chosen by the player.
     */
    private final boolean expertMode;

    /**
     * GamePreferencesMessage constructor.
     * @param numberOfPlayers number of players chosen by the player.
     * @param gameMode game mode chosen by the player.
     */
    public GamePreferencesMessage(int numberOfPlayers, boolean gameMode) {
        super(MessageType.GAME_SETUP_INFO);
        this.numberOfPlayers = numberOfPlayers;
        this.expertMode = gameMode;
    }

    /**
     * Getter method for the number of players.
     * @return the number of players chosen.
     */
    public int getNumberOfPlayers() { return numberOfPlayers; }

    /**
     * Getter method for the game mode.
     * @return true when the game mode is expert, else false.
     */
    public boolean isExpert() { return expertMode; }

}

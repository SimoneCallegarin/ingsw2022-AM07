package it.polimi.ingsw.Network.Utils;

import it.polimi.ingsw.Network.ServerSide.ClientHandler;

/**
 * Class that contains all useful data for the server about a player playing a game.
 */
public class PlayerInfo {

    /**
     * ID of the player.
     */
    private int playerID;
    /**
     * ID of the match the player is playing.
     */
    private int matchID;
    /**
     * ClientHandler associated to the player.
     */
    private ClientHandler clientHandler;

    // GETTERS:

    public int getPlayerID() { return playerID; }

    public int getMatchID() { return matchID; }

    public ClientHandler getClientHandler() { return clientHandler; }

    // SETTERS:

    public void setPlayerID(int playerID) { this.playerID = playerID; }

    public void setMatchID(int matchID) { this.matchID = matchID; }

    public void setClientHandler(ClientHandler clientHandler) { this.clientHandler = clientHandler; }

}

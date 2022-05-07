package it.polimi.ingsw.Network;

public class PlayerInfo {
    private int matchID;
    private int playerID;
    private ClientHandler clientHandler;

    public void setMatchID(int matchID) { this.matchID = matchID; }

    public void setPlayerID(int playerID) { this.playerID = playerID; }

    public void setClientHandler(ClientHandler clientHandler) { this.clientHandler = clientHandler; }

    public int getMatchID() { return matchID; }

    public int getPlayerID() { return playerID; }

    public ClientHandler getClientHandler() { return clientHandler; }
}

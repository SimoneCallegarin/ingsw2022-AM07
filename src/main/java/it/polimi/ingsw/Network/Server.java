package it.polimi.ingsw.Network;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Model.Enumeration.GameMode;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Network.Messages.SetupMessage;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Main Server Class which manages the connection of the clients
 */

public class Server {

    private static ExecutorService executor = Executors.newCachedThreadPool();

    final int port = 1234;

    private SocketServer socketServer;
    /*
      List of Games created.
    */
    public ArrayList<GameController> activeMatches;
    /*
      HashMap that contains the name ID given by the server
      to a certain client requesting connection (starting from 0).
      it will save for each one also the nickname of each client.
     */
    private HashMap<Integer,String> clientRequestedConnection;

    public Server() {
        socketServer = new SocketServer(port,this);
    }

    public int checkConnectionMessageValidity(SetupMessage setupMessage){
        if (clientRequestedConnection.containsValue(setupMessage.nickName))
            return 2;
        if (setupMessage.numberOfPlayers<2 || setupMessage.numberOfPlayers>4)
            return 1;
        return 0;

    }

    public void setClientRequestedConnection(SetupMessage setupMessage) {
        if(checkConnectionMessageValidity(setupMessage)==0){
            int k=0;
            while (clientRequestedConnection.get(k) != null)
                k++;
            clientRequestedConnection.put(k,setupMessage.nickName);
            addPlayer(setupMessage);
        }
        else{
            if(checkConnectionMessageValidity(setupMessage)==1)
                System.err.println("Error: Wrong number of player requested, (2-4 players only)");
            if(checkConnectionMessageValidity(setupMessage)==2)
                System.err.println("Error: A player with this nickname already exists!");
        }
    }

    public void addPlayer(SetupMessage setupMessage){
        int lobbyID=0;
        for(int i=0; activeMatches.get(i)!=null; i++){
            if(activeMatches.get(i).game.getGameMode()==setupMessage.gameMode && activeMatches.get(i).game.getNumberOfPlayers()==setupMessage.numberOfPlayers && activeMatches.get(i).game.getActualNumberOfPlayers()!=activeMatches.get(i).game.getNumberOfPlayers()){
                lobbyID=i;
                break;
            }
        }
        GameController gameController;
        if(activeMatches.get(lobbyID)==null)
            gameController = newGame(setupMessage);
        else
            gameController = addPlayerToAnExistingLobby(lobbyID,setupMessage);
    }

    public GameController newGame(SetupMessage setupMessage){
        Game game = new Game();
        GameController gameController = new GameController(game);
        gameController.onSetup_Message(setupMessage);
        return gameController;
    }

    public GameController addPlayerToAnExistingLobby(int lobbyID, SetupMessage setupMessage){
        activeMatches.get(lobbyID).onSetup_Message(setupMessage);
        return activeMatches.get(lobbyID);
    }

    public static void main(String[] args) {

        executor.shutdown();
    }
}
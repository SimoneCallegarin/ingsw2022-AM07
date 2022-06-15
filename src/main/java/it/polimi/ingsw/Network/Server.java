package it.polimi.ingsw.Network;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Network.JSONmessagesTestingServer.ServerSettings;

import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.Network.Messages.NetworkMessages.GamePreferencesMessage;
import it.polimi.ingsw.Network.Messages.NetworkMessages.LoginMessage;

import it.polimi.ingsw.Network.Messages.NetworkMessages.ServiceMessage;
import it.polimi.ingsw.View.VirtualView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Server Class which manages the connection of the clients.
 */
public class Server {

    /**
     * Socket Server that is responsible for setting up connections with the clients.
     */
    private final SocketServer socketServer;
    /**
     * List of Games created.
     */
    private final ArrayList<GameController> activeMatches;
    /**
     * List containing the virtual views initialized
     */
    private final ArrayList<VirtualView> virtualViews;
    /**
     * List that contains the nickname chosen by each player requesting connection with a valid nickname.
     */
    private final ArrayList<String> chosenNicknames;
    /**
     * List of players that need to have their nicknames removed from server due to the fact
     * that they had their connection closed due to an error (timeout) or a logout request.
     */
    private final ArrayList<String> playersToRemove;
    /**
     * HashMap that permits to find the client handler associated to a certain nickname,
     * the matchID of the game he is playing and his playerID in that game.
     */
    private final HashMap<String,PlayerInfo> players;

    /**
     * constructor of the Server
     * @param portNumber logical endpoints of a network connection
     *                   used to exchange information between a web server and a web client
     */
    public Server(int portNumber) {
        this.socketServer = new SocketServer(portNumber,this);
        this.activeMatches = new ArrayList<>();
        this.virtualViews=new ArrayList<>();
        this.chosenNicknames = new ArrayList<>();
        this.playersToRemove = new ArrayList<>();
        this.players = new HashMap<>();
    }

    /**
     * Checks if the nickname has been already chosen by another player
     * @param loginMessage it contains the nickname that has to be checked
     * @return true if it's a valid nickname, else false
     */
    public boolean checkNickNameValidity(LoginMessage loginMessage){
        return !chosenNicknames.contains(loginMessage.getNickname());
    }

    /**
     * Checks if the number of players chosen by the player to play with is in range of 2-4
     * @param preferences it contains the number of players and the game mode chosen by the player
     * @return true if it's a valid number of players, else false
     */
    public boolean checkValuesValidity(GamePreferencesMessage preferences){
        if(preferences.getNumberOfPlayers() >= 2 && preferences.getNumberOfPlayers() <= 4)
            return true;
        else{
            System.err.println("Error: Wrong number of players requested (2-4 players only)!");
            //SEND ERROR!
            return false;
        }
    }

    /**
     * After checking if it's a valid nickname, adds the nickname to the list of already chosen nickname (nickNamesChosen)
     * If the nickname results to be already chosen it occurs in an error
     * @param loginMessage it contains the nickname that has to be checked and added to the list if valid
     * @return true if the nickname chosen by the player is available, else false
     */
    public boolean setNickNamesChosen(LoginMessage loginMessage) {
        if(checkNickNameValidity(loginMessage)){
            chosenNicknames.add(loginMessage.getNickname());
            System.out.println("Updated chosenNicknames ArrayList with: " + loginMessage.getNickname());
            return true;
        }
        else{
            System.err.println("Error: A player with this nickname already exists!");
            System.out.println("\"" + loginMessage.getNickname() + "\"" + " isn't valid: ");
            //SEND ERROR!
            return false;
        }
    }

    /**
     * After checking if the number of player chosen is valid, it will check if between the active games there's one
     * with the requested number of players and game mode chosen by the player and with an available place
     * for the player that wants to join.
     * When found a game with this characteristics then the player is added to that game, otherwise it will be created
     * a game with the selected characteristics with the player as first player.
     * @param nickname of the player that wants to play
     * @param preferences of the player, about the number of players to play with and the game mode chosen
     */
    public boolean addPlayerToGame(String nickname, GamePreferencesMessage preferences, ClientHandler clientHandler){
        if(!checkValuesValidity(preferences))
            return false;
        int matchID =0;
        for(int i=0; i<activeMatches.size(); i++){
            matchID=i+1;
            if(activeMatches.get(i).getGameMode()==preferences.isExpert() && activeMatches.get(i).getNumberOfPlayers()==preferences.getNumberOfPlayers() && activeMatches.get(i).getActualNumberOfPlayers()!=activeMatches.get(i).getNumberOfPlayers()){
                matchID=i;
                break;
            }
        }
        players.get(nickname).setMatchID(matchID);
        //players.get(nickname).setPlayerID(activeMatches.get(matchID).getActualNumberOfPlayers()-1);
        if(matchID==activeMatches.size()) {
            newGame(nickname, preferences);
            players.get(nickname).setPlayerID(activeMatches.get(matchID).getActualNumberOfPlayers()-1);
            virtualViews.get(matchID).setClientHandler(clientHandler);
        }
        else {
            players.get(nickname).setPlayerID(activeMatches.get(matchID).getActualNumberOfPlayers());
            virtualViews.get(matchID).setClientHandler(clientHandler);
            if (getMatch(getPlayerInfo(nickname).getMatchID()).getActualNumberOfPlayers() == getMatch(getPlayerInfo(nickname).getMatchID()).getNumberOfPlayers()-1)
                clientHandler.send(new ServiceMessage(MessageType.MATCH_JOINED, "You are Player " + getPlayerInfo(nickname).getPlayerID() + "! Game starting soon...", getPlayerInfo(nickname).getPlayerID()));
            addPlayerToAnExistingLobby(matchID, nickname, preferences);
        }
        return true;
    }

    /**
     * When called it creates a new game and a game controller and joins the player to the new game
     * @param nickName of the player that wants to play
     * @param preferences of the player, about the number of players to play with and the game mode chosen
     */
    public void newGame(String nickName, GamePreferencesMessage preferences){
        Game game = new Game();
        GameController gameController = new GameController(game);
        VirtualView virtualView= new VirtualView();
        game.addObserver(virtualView);
        gameController.addPlayerToGame(nickName,preferences,true);
        activeMatches.add(gameController);
        virtualViews.add(virtualView);
        //SEND NEW GAME!
    }

    /**
     * When called it permits the player to join an already existing game
     * @param matchID the ID of the match he is going to play
     * @param nickName of the player that wants to play
     * @param preferences of the player, about the number of players to play with and the game mode chosen
     */
    public void addPlayerToAnExistingLobby(int matchID, String nickName, GamePreferencesMessage preferences){
        activeMatches.get(matchID).addPlayerToGame(nickName,preferences,false);
        //SEND ADDED TO AN ALREADY EXISTING GAME!
    }

    /**
     * Set a new element of the HashMap that contains the nicknames associated with their client handler
     * @param nickName of the player that wants to play
     * @param clientHandler of the player that wants to play
     */
    public void setPlayer(String nickName, ClientHandler clientHandler){
        PlayerInfo playerInfo = new PlayerInfo();
        playerInfo.setClientHandler(clientHandler);
        players.put(nickName,playerInfo);
        System.out.println("Put " + nickName + "'s PlayerInfo in the players HashMap");
    }

    /**
     * Removes all data associated to a certain player that ended a match.
     * @param nickname the nickname of the player that just ended a match.
     */
    public void removePlayer(String nickname) {
        players.remove(nickname);
        chosenNicknames.remove(nickname);
    }

    /**
     * Getter method to obtain the game controller of a certain match
     * @param matchID the ID of the match he is going to play
     * @return the game controller associated to the matchID given
     */
    public GameController getMatch(int matchID) { return activeMatches.get(matchID); }

    /**
     * Getter method to obtain information associated to a player with a certain nickname.
     * @param nickname the nickname of the  player we want to know the information.
     * @return the information of that player (playerID, matchID and client handler of the player).
     */
    public PlayerInfo getPlayerInfo(String nickname) { return players.get(nickname); }

    public void onDisconnection(String nickname) {
        System.out.println("Deleting match number " + getPlayerInfo(nickname).getMatchID());
        int matchToEnd = getPlayerInfo(nickname).getMatchID();
        activeMatches.remove(matchToEnd);
        virtualViews.remove(matchToEnd);
        for (String player : chosenNicknames)
            if(players.get(player).getMatchID() == matchToEnd && !player.equals(nickname) && players.get(player).getClientHandler().isConnected())
                players.get(player).getClientHandler().disconnect(nickname + " has left the lobby. The game will now end.");
        for (String playerToRemove : playersToRemove) {
            removePlayer(playerToRemove);
            System.out.println("Removed player " + playerToRemove);
        }
        playersToRemove.clear();
        System.out.println("Game number " + matchToEnd + " ended because player " + nickname + " left the game.");
    }

    /**
     * Adds a player's nickname that has to be removed from the server.
     * @param nickname the nickname of the player that has to be removed.
     */
    public void addPlayerToRemove(String nickname) { playersToRemove.add(nickname); }

    public static void main(String[] args) {

        String hostName;
        int portNumber;

        if (args.length==2){
            hostName = args[0];
            portNumber = Integer.parseInt(args[1]);
        }else{
            hostName = ServerSettings.getHostName();
            portNumber = ServerSettings.getPort();
        }

        Server server = new Server(portNumber);
        System.out.println(hostName+" Server started at port " + portNumber + "!");
        ExecutorService executor = Executors.newCachedThreadPool();
        executor.submit(server.socketServer);
        //executor.shutdown();
    }
}
package it.polimi.ingsw.Network;

import it.polimi.ingsw.Controller.GameController;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Network.JSONmessagesTestingServer.ServerSettings;
import it.polimi.ingsw.Network.Messages.GamePreferencesMessage;
import it.polimi.ingsw.Network.Messages.LoginMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Server Class which manages the connection of the clients
 */
public class Server {

    public SocketServer socketServer;
    /**
     * List of Games created.
     */
    public ArrayList<GameController> activeMatches;
    /**
     * List that contains the nickname chosen by each player requesting connection with a valid nickname
     */
    private final ArrayList<String> nickNamesChosen;
    /**
     * HashMap that permits to find the client handler associated to a certain nickname
     */
    private final HashMap<String,ClientHandler> players = new HashMap<>();

    /**
     * constructor of the Server
     * @param portNumber logical endpoints of a network connection
     *                   used to exchange information between a web server and a web client
     */
    public Server(int portNumber) {
        this.socketServer = new SocketServer(portNumber,this);
        this.activeMatches = new ArrayList<>();
        this.nickNamesChosen = new ArrayList<>();
    }

    /**
     * Checks if the nickname has been already chosen by another player
     * @param loginMessage it contains the nickname that has to be checked
     * @return true if it's a valid nickname, else false
     */
    public boolean checkNickNameValidity(LoginMessage loginMessage){
        return !nickNamesChosen.contains(loginMessage.getNickName());
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
            nickNamesChosen.add(loginMessage.getNickName());
            return true;
        }
        else{
            System.err.println("Error: A player with this nickname already exists!");
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
     * @param nickName of the player that wants to play
     * @param preferences of the player, about the number of players to play with and the game mode chosen
     * @return the ID of the match he is going to play
     */
    public int addPlayerToGame(String nickName, GamePreferencesMessage preferences){
        if(!checkValuesValidity(preferences))
            return -1;
        int matchID =0;
        for(int i=0; i<activeMatches.size(); i++){
            matchID=i+1;
            if(activeMatches.get(i).game.getGameMode()==preferences.gameMode && activeMatches.get(i).game.getNumberOfPlayers()==preferences.numberOfPlayers && activeMatches.get(i).game.getActualNumberOfPlayers()!=activeMatches.get(i).game.getNumberOfPlayers()){
                matchID=i;
                break;
            }
        }
        if(matchID==activeMatches.size())
            newGame(nickName,preferences);
        else
            addPlayerToAnExistingLobby(matchID,nickName,preferences);
        return matchID;
    }

    /**
     * When called it creates a new game and a game controller and joins the player to the new game
     * @param nickName of the player that wants to play
     * @param preferences of the player, about the number of players to play with and the game mode chosen
     */
    public void newGame(String nickName, GamePreferencesMessage preferences){
        Game game = new Game();
        GameController gameController = new GameController(game);
        gameController.addPlayerToGame(nickName,preferences,true);
        activeMatches.add(gameController);
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
    public void setPlayers(String nickName, ClientHandler clientHandler){ players.put(nickName,clientHandler); }

    /**
     * Getter method to obtain the game controller of a certain match
     * @param matchID the ID of the match he is going to play
     * @return the game controller associated to the matchID given
     */
    public GameController getMatch(int matchID) { return activeMatches.get(matchID); }

    public static void main(String[] args) {

        String hostName;
        int portNumber;

        if (args.length==2){
            hostName = args[0];
            portNumber = Integer.parseInt(args[1]);
        }else{
            hostName = ServerSettings.ReadHostFromJSON();
            portNumber = ServerSettings.ReadPortFromJSON();
        }

        Server server = new Server(portNumber);
        System.out.println(hostName+" Server started !");
        ExecutorService executor = Executors.newCachedThreadPool();
        executor.submit(server.socketServer);
        //executor.shutdown();
    }
}
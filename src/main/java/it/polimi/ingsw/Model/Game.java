package it.polimi.ingsw.Model;

import java.util.ArrayList;

public class Game {
    private final ArrayList<Player> players;
    private GameMode gameMode;
    private int numberOfPlayers;
    private GameTable gameTable;
    private int actualNumberOfPlayers;

    private final GamePhases gamePhases;
    private final ActionPhases actionPhases;
    private final PlanificationPhases planificationPhases;

    private final int sameStudents;   //used for the character cards in recalculate effect
    private final int noTowerCount;   //used for the character cards in recalculate effect
    private final int moreMNMovement;   //used for the character cards in recalculate effect
    private final int moreInfluence;   //used for the character cards in recalculate effect
    private final int noColorInfluence;   //used for the character cards in recalculate effect

    public Game() {
        this.players = new ArrayList<>(4);
        this.gameMode = GameMode.BASE;
        this.numberOfPlayers = 0;

        this.gamePhases = GamePhases.SETUP_PHASE;                       //not already used but ok, maybe we can add a "neutral" phase
        this.actionPhases = ActionPhases.MOVE_STUDENTS;
        this.planificationPhases = PlanificationPhases.FILL_CLOUDS;

        this.sameStudents = 0;
        this.noTowerCount = 0;
        this.moreMNMovement = 0;
        this.moreInfluence = 0;
        this.noColorInfluence = 0;
    }

    public int getPlayerID(String nickName) {
        int i = 0;
        while (!players.get(i).nickname.equals(nickName))
            i++;
        return i;
    }

    public Player getPlayer(String nickName) {
        int i = 0;
        while (!players.get(i).nickname.equals(nickName))
            i++;
        return players.get(i);
    }

    public GameTable getGameTable() {
        return gameTable;
    }

    /**
     * setting the game mode when selected by the first player
     * @param gameMode expert or base
     */
    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    /**
     * setting the number of player when selected by the first player
     * @param numberOfPlayers to play with in total
     */
    public void setPlayerNumbers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    //it is possible to split this method in 4 sub methods that permit to build the player by asking him 1 param per times
    /**
     * First method that has to be called to properly start the game
     * adding the first player following his desire about which game mode and with how many players he wants to play
     * @param nickName selected by the player
     * @param gameMode selected by the player (expert or base)
     * @param numberOfPlayers selected by the player (from 2 to 4)
     * @param mage selected by the player between 4 possible mages
     */
    public void addFirstPlayer(String nickName, GameMode gameMode, int numberOfPlayers, Mages mage){

        setPlayerNumbers(numberOfPlayers);
        setGameMode(gameMode);

        Player newPlayer;

        gameTable = new GameTable(numberOfPlayers, gameMode);
        gameTable.buildDashboard(0);

        if (numberOfPlayers==4)
            newPlayer = new Player(nickName, Squads.SQUAD1, mage, gameTable.getDashboard(0), gameMode);
        else
            newPlayer = new Player(nickName, Squads.NOSQUAD, mage, gameTable.getDashboard(0), gameMode);

        players.add(newPlayer);
        actualNumberOfPlayers = 1;

    }

    /**
     * first action to do when a player that is not the first one asks to join a match
     * (after having checked that the number of players is respected)
     * @param nickName the nickname of the player, must be checked if other players already got the same
     */
    public void addAnOtherPlayer(String nickName, Mages mage){

        for (int i=0; i<actualNumberOfPlayers; i++){
            if (nickName.equals(players.get(i).nickname))
                return;
                //new name please
        }

        if ((actualNumberOfPlayers<numberOfPlayers)) {
            gameTable.buildDashboard(actualNumberOfPlayers);
            Player newPlayer;
                if(numberOfPlayers == 2 || numberOfPlayers == 3)
                    newPlayer = new Player(nickName, Squads.NOSQUAD, mage, gameTable.getDashboard(actualNumberOfPlayers), gameMode);
                else
                    if(actualNumberOfPlayers == 1)
                        newPlayer = new Player(nickName, Squads.SQUAD1, mage, gameTable.getDashboard(actualNumberOfPlayers), gameMode);
                    else
                        newPlayer = new Player(nickName, Squads.SQUAD2, mage, gameTable.getDashboard(actualNumberOfPlayers), gameMode);

            players.add(newPlayer);
            actualNumberOfPlayers += 1;
        }
        else
            return;         //it is not possible to add more player than the number of players selected by the first player

    }



}

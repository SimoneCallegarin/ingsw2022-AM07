package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.Enumeration.*;
import it.polimi.ingsw.Model.GameTableObjects.GameTable;
import it.polimi.ingsw.Model.Player.AssistantCard;
import it.polimi.ingsw.Model.Player.Player;

import java.util.ArrayList;

public class Game {
    private final ArrayList<Player> players;
    public int firstPlayerIndex;
    private GameMode gameMode;

    private int numberOfPlayers;
    private int studentsMovable;
    private GameTable gameTable;
    private int actualNumberOfPlayers;

    public int playerCounter = 0;
    private int studentsCounter = 0;

    private GamePhases gamePhase;
    public PlanningPhases planningPhase;
    private ActionPhases actionPhase;
    private CurrentOrder currentActivePlayer;

    private final int sameStudents;   //used for the character cards in recalculate effect
    private final int noTowerCount;   //used for the character cards in recalculate effect
    private final int moreMNMovement;   //used for the character cards in recalculate effect
    private final int moreInfluence;   //used for the character cards in recalculate effect
    private final int noColorInfluence;   //used for the character cards in recalculate effect

    public Game() {
        this.players = new ArrayList<>(4);
        this.gameMode = GameMode.BASE;
        this.numberOfPlayers = 0;

        this.gamePhase = GamePhases.SETUP_PHASE;                       //not already used but ok, maybe we can add a "neutral" phase
        this.planningPhase = PlanningPhases.FILL_CLOUDS;
        this.actionPhase = ActionPhases.MOVE_STUDENTS;

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

    public Player getPlayerByIndex(int playerIndex) {
        return players.get(playerIndex);
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

        if (numberOfPlayers == 3)
            studentsMovable = 4;
        else
            studentsMovable = 3;
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
            newPlayer = new Player(nickName, Squads.SQUAD1, gameTable.getDashboard(0), gameMode);
        else
            newPlayer = new Player(nickName, Squads.NOSQUAD, gameTable.getDashboard(0), gameMode);

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
                if(numberOfPlayers == 2)
                    newPlayer = new Player(nickName, Squads.NOSQUAD, gameTable.getDashboard(actualNumberOfPlayers), gameMode);
                else
                    if(numberOfPlayers == 3)
                        newPlayer = new Player(nickName, Squads.NOSQUAD, gameTable.getDashboard(actualNumberOfPlayers), gameMode);
                    else
                        if(actualNumberOfPlayers == 1)
                            newPlayer = new Player(nickName, Squads.SQUAD1, gameTable.getDashboard(actualNumberOfPlayers), gameMode);
                        else
                            newPlayer = new Player(nickName, Squads.SQUAD2, gameTable.getDashboard(actualNumberOfPlayers), gameMode);
            players.add(newPlayer);
            actualNumberOfPlayers += 1;

            if (actualNumberOfPlayers == numberOfPlayers) {     // we are ready to go
                this.gamePhase = GamePhases.PLANNING_PHASE;
                fillClouds();
                firstPlayerIndex = (int)(Math.random()*(numberOfPlayers));
                updateOrder(gamePhase);
            }

        }
        else
            return;         //it is not possible to add more player than the number of players selected by the first player

    }

    /**
     * this is a method used during PLANIFICATION_PHASE and it fills clouds automatically
     */
    private void fillClouds() {
        if (gamePhase == GamePhases.PLANNING_PHASE && planningPhase == PlanningPhases.FILL_CLOUDS) {
            for (int i = 0; i < numberOfPlayers; i++) {
                for (int j = 0; j < studentsMovable; j++) {
                    gameTable.getCloud(i).addStudent(gameTable.getBag().draw());
                }
            }
            planningPhase = PlanningPhases.ASSISTANT_CARD_PHASE;
        }
    }

    public void playAssistantCard(int IDPlayer, AssistantCard assistantCardPlayed) {
        boolean alreadyPlayed = false;
        if (gamePhase == GamePhases.PLANNING_PHASE && planningPhase == PlanningPhases.ASSISTANT_CARD_PHASE && currentActivePlayer == players.get(IDPlayer).getOrder()) {
            for (Player p : players) {   // next round incoming... discard piles need to be set null (otherwise it doesn't work)
                if (p.discardPile != null) {
                    if (p.discardPile.getTurnOrder() == assistantCardPlayed.getTurnOrder()) {
                        alreadyPlayed = true;
                        break;
                    }
                }
            }
            if (!alreadyPlayed) {
                players.get(IDPlayer).playAssistantCard(assistantCardPlayed);
                playerCounter++;
                if (playerCounter == numberOfPlayers) {
                    gamePhase = GamePhases.ACTION_PHASE;
                    actionPhase = ActionPhases.MOVE_STUDENTS;
                }
                nextPlayer();
            }
        }
    }

    private void updateOrder(GamePhases gamePhase) {
        int i = firstPlayerIndex;
        if (gamePhase == GamePhases.PLANNING_PHASE) {
            players.get(firstPlayerIndex).setOrder(CurrentOrder.FIRST_PLAYER);
            currentActivePlayer = CurrentOrder.FIRST_PLAYER;
            playerCounter++;
            while (playerCounter < numberOfPlayers) {
                i++;
                if (i == numberOfPlayers)
                    i = 0;
                if (playerCounter == 1)
                    players.get(i).setOrder(CurrentOrder.SECOND_PLAYER);
                else if (playerCounter == 2)
                    players.get(i).setOrder(CurrentOrder.THIRD_PLAYER);
                else if (playerCounter == 3)
                    players.get(i).setOrder(CurrentOrder.FOURTH_PLAYER);
                playerCounter++;
            }
            playerCounter = 0;
        }

        if (gamePhase == GamePhases.ACTION_PHASE) {
            if (actionPhase == ActionPhases.MOVE_STUDENTS) {
               // check of every discard pile
            }
        }
    }

    private void nextPlayer() {
        if (currentActivePlayer == CurrentOrder.FIRST_PLAYER)
            currentActivePlayer = CurrentOrder.SECOND_PLAYER;
        else if (currentActivePlayer == CurrentOrder.SECOND_PLAYER) {
            if (playerCounter == numberOfPlayers)
                updateOrder(GamePhases.ACTION_PHASE);
            else
                currentActivePlayer = CurrentOrder.THIRD_PLAYER;
        }
        else if (currentActivePlayer == CurrentOrder.THIRD_PLAYER) {
            if (playerCounter == numberOfPlayers)
                updateOrder(GamePhases.ACTION_PHASE);
            else
                currentActivePlayer = CurrentOrder.FOURTH_PLAYER;
        }
        else if (currentActivePlayer == CurrentOrder.FOURTH_PLAYER)
            updateOrder(GamePhases.ACTION_PHASE);
    }

    public String getState() {
        String gp = null, pp = null, ap = null, cap = null;

        if (gamePhase == GamePhases.SETUP_PHASE)
            gp = "setup_phase";
        else if (gamePhase == GamePhases.PLANNING_PHASE)
            gp = "planning_phase";
        else if (gamePhase == GamePhases.ACTION_PHASE)
            gp = "action_phase";

        if (planningPhase == PlanningPhases.FILL_CLOUDS)
            pp = "fill_clouds";
        else if (planningPhase == PlanningPhases.ASSISTANT_CARD_PHASE)
            pp = "assistant_card_phase";

        if (actionPhase == ActionPhases.MOVE_STUDENTS)
            ap = "move_students";
        else if (actionPhase == ActionPhases.MOVE_MOTHER_NATURE)
            ap = "move_mother_nature";
        else if (actionPhase == ActionPhases.CHOOSE_CLOUD)
            ap = "choose_cloud";

        if (currentActivePlayer == CurrentOrder.FIRST_PLAYER)
            cap = "player_1";
        else if (currentActivePlayer == CurrentOrder.SECOND_PLAYER)
            cap = "player_2";
        else if (currentActivePlayer == CurrentOrder.THIRD_PLAYER)
            cap = "player_3";
        else if (currentActivePlayer == CurrentOrder.FOURTH_PLAYER)
            cap = "player_4";

        return "GAME_PHASE: " + gp + "," + "PLANNING_PHASE: " + pp + "," + "ACTION_PHASE: " + ap + "," + "ACTIVE_PLAYER: " + cap;

    }

}

package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.CharacterCards.CharacterCard;
import it.polimi.ingsw.Model.CharacterCards.EffectFactory;
import it.polimi.ingsw.Model.Enumeration.*;
import it.polimi.ingsw.Model.GameTableObjects.GameTable;
import it.polimi.ingsw.Model.Interface.StudentManager;
import it.polimi.ingsw.Model.Player.AssistantCard;
import it.polimi.ingsw.Model.Player.Player;

import java.util.ArrayList;
import java.util.List;

public class Game {
    /**
     * the game mode of the game, it is decided by the first player that joins the game
     */
    private GameMode gameMode;
    /**
     * the number of players that are going to play the game, it is decided by the first player that joins the game
     */
    private int numberOfPlayers;
    /**
     * the number of players that already joined the game
     */
    private int actualNumberOfPlayers = 0;
    /**
     * the game table where the game is going to be played, it contains many different objects that compose the game itself
     */
    private GameTable gameTable;
    /**
     * the list of players that are playing the game
     */
    private final ArrayList<Player> players;
    /**
     * random index used to say who will be the first player to play in the first turn
     */
    public int firstPlayerIndex;
    /**
     * the number of students that is possible to move in a turn for each player
     */
    private int maxMovableStudents;
    /**
     * counter used to check how many players already played their turn in a game turn
     */
    public int playerCounter = 0;

    private int studentsCounter = 0;

    /**
     * Phases in which it's divided the game
     */
    public GamePhases gamePhase;
    /**
     * it divide the planning phase in 2 different sub phases and it indicates that the player turn is in the planning phase
     */
    public PlanningPhases planningPhase;
    /**
     * it divide the planning phase in 3 different sub phases and it indicates that the player turn is in the action phase
     */
    private ActionPhases actionPhase;
    /**
     * it indicates who is the current active player that is playing his turn
     */
    public CurrentOrder currentActivePlayer;
    /**
     * its the factory that permits to build the effect of each playable character card
     */
    private final EffectFactory effectFactory;

    /**
     * Game constructor
     */
    public Game() {
        this.players = new ArrayList<>(4);
        this.gameMode = GameMode.BASE;
        this.numberOfPlayers = 0;

        this.gamePhase = GamePhases.SETUP_PHASE;                       //not already used but ok, maybe we can add a "neutral" phase
        this.planningPhase = PlanningPhases.FILL_CLOUDS;
        this.actionPhase = ActionPhases.MOVE_STUDENTS;

        this.effectFactory = new EffectFactory();
    }


    /*          (still not used)
    public int getPlayerID(String nickName) {
        int i = 0;
        while (!players.get(i).nickname.equals(nickName))
            i++;
        return i;
    }*/

    /**
     * getter method that permits to know whats the  in the list of players that has a certain index
     * @param playerIndex the index of the player that is in the list
     * @return the player associated with that index
     */
    public Player getPlayerByIndex(int playerIndex) {
        return players.get(playerIndex);
    }

    /**
     * getter method that permits to pick the current game table
     * @return the current game table
     */
    public GameTable getGameTable() {
        return gameTable;
    }

    /**
     * setting the number of player when selected by the first player
     * @param numberOfPlayers to play with in total
     */
    public void setPlayerNumbers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;

        if (numberOfPlayers == 3)
            maxMovableStudents = 4;
        else
            maxMovableStudents = 3;
    }

    //it is possible to split this method in 4 sub methods that permit to build the player by asking him 1 param per times
    /**
     * First method that has to be called to properly start the game
     * adding the first player following his desire about which game mode and with how many players he wants to play
     * @param nickName selected by the player
     * @param gameMode selected by the player (expert or base)
     * @param numberOfPlayers selected by the player (from 2 to 4)
     */
    public void addFirstPlayer(String nickName, GameMode gameMode, int numberOfPlayers){

        setPlayerNumbers(numberOfPlayers);
        this.gameMode = gameMode;

        Player newPlayer;

        gameTable = new GameTable(numberOfPlayers, gameMode);

        if (numberOfPlayers==4)
            newPlayer = new Player(nickName, numberOfPlayers, actualNumberOfPlayers, Squads.SQUAD1, gameMode);
        else
            newPlayer = new Player(nickName, numberOfPlayers, actualNumberOfPlayers, Squads.NOSQUAD, gameMode);

        players.add(newPlayer);
        actualNumberOfPlayers = 1;

    }

    /**
     * first action to do when a player that is not the first one asks to join a match
     * (after having checked that the number of players is respected)
     * @param nickName the nickname of the player, must be checked if other players already got the same
     */
    public void addAnotherPlayer(String nickName) {

        for (int i=0; i<actualNumberOfPlayers; i++){
            if (nickName.equals(players.get(i).nickname))
                return;
                //new name please
        }

        if (actualNumberOfPlayers < numberOfPlayers) {
            Player newPlayer;
            if (numberOfPlayers == 2 || numberOfPlayers == 3)
                newPlayer = new Player(nickName, numberOfPlayers, actualNumberOfPlayers, Squads.NOSQUAD, gameMode);
            else {
                if (actualNumberOfPlayers == 1)
                    newPlayer = new Player(nickName, numberOfPlayers, actualNumberOfPlayers, Squads.SQUAD1, gameMode);
                else
                    newPlayer = new Player(nickName, numberOfPlayers, actualNumberOfPlayers, Squads.SQUAD2, gameMode);
            }
            players.add(newPlayer);
            actualNumberOfPlayers++;

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
     * this is a method used during PLANNING_PHASE that fills clouds automatically
     */
    private void fillClouds() {
        if (gamePhase == GamePhases.PLANNING_PHASE && planningPhase == PlanningPhases.FILL_CLOUDS) {
            for (int i = 0; i < numberOfPlayers; i++) {
                for (int j = 0; j < maxMovableStudents; j++) {
                    gameTable.getCloud(i).addStudent(gameTable.getBag().draw());
                }
            }
            planningPhase = PlanningPhases.ASSISTANT_CARD_PHASE;
        }
    }

    /**
     * this method is responsible for changing the discard pile of a certain player
     * @param idPlayer is an integer that represents the index of the players ArrayList which corresponds to the player who played the assistant card
     * @param assistantCardPlayed is the AssistantCard played
     */
    public void playAssistantCard(int idPlayer, AssistantCard assistantCardPlayed) {
        boolean alreadyPlayed = false;
        if (gamePhase == GamePhases.PLANNING_PHASE && planningPhase == PlanningPhases.ASSISTANT_CARD_PHASE && currentActivePlayer == players.get(idPlayer).getOrder()) {
            for (Player p : players) {   // next round incoming... discard piles need to be set null (otherwise it doesn't work)
                if (p.discardPile != null) {
                    if (p.discardPile.equals(assistantCardPlayed)) {
                        alreadyPlayed = true;
                        break;
                    }
                }
            }
            if (!alreadyPlayed) {
                players.get(idPlayer).playAssistantCard(assistantCardPlayed);
                playerCounter++;
                nextPlayer();
            }
        }
    }



    /**
     * according to the game phase, this method updates when a player has the right to play
     * @param gamePhase is used in order to execute different statements
     */
    private void updateOrder(GamePhases gamePhase) {
        int i = firstPlayerIndex;
        if (gamePhase == GamePhases.PLANNING_PHASE) {
            for (playerCounter = 0; playerCounter < numberOfPlayers; playerCounter++) {
                players.get(i).setOrder(CurrentOrder.getCurrentOrder(playerCounter));
                i++;
                if (i == numberOfPlayers)
                    i = 0;
            }
            currentActivePlayer = CurrentOrder.FIRST_PLAYER;
            playerCounter = 0;
        }

        if (gamePhase == GamePhases.ACTION_PHASE) {
            this.gamePhase = GamePhases.ACTION_PHASE;
            actionPhase = ActionPhases.MOVE_STUDENTS;
        }
    }

    /**
     * this method updates an attribute of Game that is used to identify which player has the right to play in a specific moment
     */
    private void nextPlayer() {
        if (playerCounter == numberOfPlayers)
            updateOrder(GamePhases.ACTION_PHASE);
        else
            currentActivePlayer = CurrentOrder.getCurrentOrder(playerCounter);
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

    public void playCharacterCard(int idPlayer, CharacterCard characterCard) {

        if (gamePhase == GamePhases.ACTION_PHASE && !getPlayerByIndex(idPlayer).getAlreadyPlayedACardThisTurn() && currentActivePlayer == players.get(idPlayer).getOrder()) {
            getPlayerByIndex(idPlayer).playCharacterCard(characterCard);
            effectFactory.getEffect(getPlayerByIndex(idPlayer), gameTable , characterCard);
        }
    }

}

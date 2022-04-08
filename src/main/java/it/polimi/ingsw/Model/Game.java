package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.DashboardObjects.Dashboard;
import it.polimi.ingsw.Model.Enumeration.*;
import it.polimi.ingsw.Model.GameTableObjects.GameTable;
import it.polimi.ingsw.Model.Player.AssistantCard;
import it.polimi.ingsw.Model.Player.Player;

import java.util.ArrayList;
import java.util.TreeSet;

public class Game {
    private final ArrayList<Player> players;
    public int firstPlayerIndex;
    private GameMode gameMode;

    private int numberOfPlayers;
    private int maxMovableStudents;
    private GameTable gameTable;
    private int actualNumberOfPlayers = 0;

    public int playerCounter = 0;
    private int studentsCounter = 0;

    public GamePhases gamePhase;
    public PlanningPhases planningPhase;
    public ActionPhases actionPhase;
    public CurrentOrder currentActivePlayer;

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
     * this method is responsible for changing the discard pile of a certain player
     * @param idPlayer is an integer that represents the index of the players ArrayList which corresponds to the player who played the assistant card
     * @param assistantCardPlayed is the AssistantCard played
     */
    public void playAssistantCard(int idPlayer, AssistantCard assistantCardPlayed) {
        boolean alreadyPlayed = false;
        if (gamePhase == GamePhases.PLANNING_PHASE && planningPhase == PlanningPhases.ASSISTANT_CARD_PHASE && currentActivePlayer == players.get(idPlayer).getOrder()) {
            for (Player p : players) {   // next round incoming... discard piles need to be set null (otherwise it doesn't work)
                if (p.getDiscardPile() != null) {
                    if (p.getDiscardPile().equals(assistantCardPlayed)) {
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
     * this method takes a student from the dashboard of a certain player and puts it on a specified island
     * @param idPlayer is an integer that represents the index of the players ArrayList which corresponds to the player who moved the student
     * @param idIsle is the index of the chosen island
     * @param color is the color of the student that has been moved
     */
    public void moveStudentInIsle(int idPlayer, int idIsle, RealmColors color) {
        if (gamePhase == GamePhases.ACTION_PHASE && actionPhase == ActionPhases.MOVE_STUDENTS && currentActivePlayer == players.get(idPlayer).getOrder()) {
            players.get(idPlayer).getDashboard().getEntrance().removeStudent(color);
            gameTable.getIsleManager().getIsle(idIsle).addStudent(color);
            studentsCounter++;

            if (studentsCounter == maxMovableStudents) {
                actionPhase = ActionPhases.MOVE_MOTHER_NATURE;
                studentsCounter = 0;
            }
        }
    }

    /**
     * this method takes a student from the dashboard of a certain player and puts it in the dining room
     * @param idPlayer is an integer that represents the index of the players ArrayList which corresponds to the player who moved the student
     * @param color is the color of the student that has been moved
     */
    public void moveStudentInDiningRoom(int idPlayer, RealmColors color) {
        if (gamePhase == GamePhases.ACTION_PHASE && actionPhase == ActionPhases.MOVE_STUDENTS && currentActivePlayer == players.get(idPlayer).getOrder()) {
            players.get(idPlayer).getDashboard().getEntrance().removeStudent(color);
            players.get(idPlayer).getDashboard().getDiningRoom().addStudent(color);
            checkUpdateProfessor(idPlayer, color);
            studentsCounter++;

            if (studentsCounter == maxMovableStudents) {
                actionPhase = ActionPhases.MOVE_MOTHER_NATURE;
                studentsCounter = 0;
            }
        }
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
            TreeSet<Integer> turnOrderTree = new TreeSet<>();
            int lowestTurnOrder = 0;

            for (Player p : players)
                turnOrderTree.add(p.getDiscardPile().getTurnOrder());

            for (playerCounter = 0; playerCounter < numberOfPlayers; playerCounter++) {
                if (!turnOrderTree.isEmpty())
                    lowestTurnOrder = turnOrderTree.pollFirst();
                for (Player p : players) {
                    if (p.getDiscardPile().getTurnOrder() == lowestTurnOrder) {
                        p.setOrder(CurrentOrder.getCurrentOrder(playerCounter));
                        break;
                    }
                }
            }

            for (Player p : players) {
                if (p.getOrder() == CurrentOrder.FIRST_PLAYER)
                    firstPlayerIndex = p.getDashboard().getIdDashboard();
            }
            currentActivePlayer = CurrentOrder.FIRST_PLAYER;
            playerCounter = 0;
        }
    }

    /**
     * this method updates an attribute of Game that is used to identify which player has the right to play in a specific moment
     */
    private void nextPlayer() {
        if (playerCounter == numberOfPlayers) {
            playerCounter = 0;
            updateOrder(GamePhases.ACTION_PHASE);
        }
        else
            currentActivePlayer = CurrentOrder.getCurrentOrder(playerCounter);
    }

    /**
     * this method is invoked when a student has been added to the dining room of a certain player and updates the professors owned by players, if it is necessary
     * @param idPlayer is an integer that represents the index of the players ArrayList which corresponds to the player whose dining room has been updated
     * @param color is the color of the student that has been moved to the dining room, therefore it is the color of the professor we need to check
     */
    private void checkUpdateProfessor(int idPlayer, RealmColors color) {
        if (players.get(idPlayer).getDashboard().getDiningRoom().getProfessorByColor(color) == 0) {
            int playerWhoHasProfessorIndex = 0;
            boolean someoneHasProfessor = false;

            for (Player p : players) {
                if (p.getDashboard().getDiningRoom().getProfessorByColor(color) == 1) {
                    playerWhoHasProfessorIndex = p.getDashboard().getIdDashboard();
                    someoneHasProfessor = true;
                    break;
                }
            }

            if (someoneHasProfessor) {
                if (players.get(idPlayer).getDashboard().getDiningRoom().getStudentsByColor(color) > players.get(playerWhoHasProfessorIndex).getDashboard().getDiningRoom().getStudentsByColor(color)) {
                    players.get(playerWhoHasProfessorIndex).getDashboard().getDiningRoom().removeProfessor(color);
                    players.get(idPlayer).getDashboard().getDiningRoom().addProfessor(color);
                }
            }
            else
                players.get(idPlayer).getDashboard().getDiningRoom().addProfessor(color);
        }
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

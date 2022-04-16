package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.CharacterCards.CharacterCard;
import it.polimi.ingsw.Model.CharacterCards.EffectInGameFactory;
import it.polimi.ingsw.Model.Enumeration.*;
import it.polimi.ingsw.Model.GameTableObjects.Cloud;
import it.polimi.ingsw.Model.GameTableObjects.GameTable;
import it.polimi.ingsw.Model.GameTableObjects.IsleManager;
import it.polimi.ingsw.Model.Player.AssistantCard;
import it.polimi.ingsw.Model.Player.Player;

import java.util.ArrayList;
import java.util.TreeSet;

public class Game {
    /**
     * the game mode of the game, it is decided by the first player that joins the game
     */
    private GameMode gameMode;
    /**
     * the number of players that are going to play the game, it is decided by the first player that joins the game
     */
    public int numberOfPlayers;
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
     * counter used to check how many players already played their turn in a game phase
     */
    public int playerCounter = 0;

    private int studentsCounter = 0;

    private boolean lastRound = false;

    private boolean endGame = false;
    private boolean drawEndGame = false;
    private Player winner;

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
    public ActionPhases actionPhase;
    /**
     * it indicates who is the current active player that is playing his turn
     */
    public CurrentOrder currentActivePlayer;
    /**
     * its the factory that permits to build the effect of each playable character card
     */
    private final EffectInGameFactory effectInGameFactory;

    /**
     * Game constructor
     */
    public Game() {
        this.players = new ArrayList<>(4);
        this.gameMode = GameMode.BASE;
        this.numberOfPlayers = 0;

        this.gamePhase = GamePhases.SETUP_PHASE;
        this.planningPhase = PlanningPhases.FILL_CLOUDS;
        this.actionPhase = ActionPhases.MOVE_STUDENTS;

        this.effectInGameFactory = new EffectInGameFactory();
    }

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
    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;

        if (numberOfPlayers == 3)
            maxMovableStudents = 4;
        else
            maxMovableStudents = 3;
    }

    public int getNumberOfPlayers() {return numberOfPlayers;}

    /**
     * First method that has to be called to properly start the game
     * adding the first player following his desire about which game mode and with how many players he wants to play
     * @param nickName selected by the player
     * @param gameMode selected by the player (expert or base)
     * @param numberOfPlayers selected by the player (from 2 to 4)
     */
    public void addFirstPlayer(String nickName, GameMode gameMode, int numberOfPlayers){

        setNumberOfPlayers(numberOfPlayers);
        this.gameMode = gameMode;

        Player newPlayer;

        gameTable = new GameTable(numberOfPlayers, gameMode);

        if (numberOfPlayers==4)
            newPlayer = new Player(nickName, numberOfPlayers, actualNumberOfPlayers, Squads.SQUAD1, gameMode);
        else
            newPlayer = new Player(nickName, numberOfPlayers, actualNumberOfPlayers, Squads.NOSQUAD, gameMode);

        players.add(newPlayer);
        initializeEntrance(actualNumberOfPlayers);
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
                if (actualNumberOfPlayers == 2)
                    newPlayer = new Player(nickName, numberOfPlayers, actualNumberOfPlayers, Squads.SQUAD1, gameMode);
                else
                    newPlayer = new Player(nickName, numberOfPlayers, actualNumberOfPlayers, Squads.SQUAD2, gameMode);
            }
            players.add(newPlayer);
            initializeEntrance(actualNumberOfPlayers);
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
     * it is responsible for changing the discard pile of a certain player
     * @param idPlayer is an integer that represents the index of the players ArrayList which corresponds to the player who played the assistant card
     * @param turnOrderPlayed is the turn order of the AssistantCard played
     */
    public void playAssistantCard(int idPlayer, int turnOrderPlayed) {
        boolean alreadyPlayed = false;
        AssistantCard assistantCardPlayed = players.get(idPlayer).getAssistantCardByTurnOrder(turnOrderPlayed);

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
                if (players.get(idPlayer).isMageDeckEmpty())
                    lastRound = true;
                playerCounter++;
                nextPlayer();
            }
        }
    }

    /**
     * it takes a student from the dashboard of a certain player and puts it on a specified island
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
     * it takes a student from the dashboard of a certain player and puts it in the dining room
     * @param idPlayer is an integer that represents the index of the players ArrayList which corresponds to the player who moved the student
     * @param color is the color of the student that has been moved
     */
    public void moveStudentInDiningRoom(int idPlayer, RealmColors color) {
        if (gamePhase == GamePhases.ACTION_PHASE && actionPhase == ActionPhases.MOVE_STUDENTS && currentActivePlayer == players.get(idPlayer).getOrder()) {
            if (players.get(idPlayer).getDashboard().getDiningRoom().getStudentsByColor(color) < 10) {
                players.get(idPlayer).getDashboard().getEntrance().removeStudent(color);
                players.get(idPlayer).getDashboard().getDiningRoom().addStudent(color);
                if (gameMode == GameMode.EXPERT && players.get(idPlayer).getDashboard().getDiningRoom().getStudentsByColor(color)%3 == 0)
                    players.get(idPlayer).gainMoney();
                checkUpdateProfessor(idPlayer, color);
                studentsCounter++;

                if (studentsCounter == maxMovableStudents) {
                    actionPhase = ActionPhases.MOVE_MOTHER_NATURE;
                    studentsCounter = 0;
                }
            }
        }
    }

    /**
     * it takes a student from the dashboard of a certain player and puts it on a certain isle
     * @param idPlayer is an integer that represents the index of the players ArrayList which corresponds to the player who moved the student
     * @param idIsle is the index of the isle which we want to move the student on
     */
    public void moveMotherNature(int idPlayer, int idIsle) {
        if (gamePhase == GamePhases.ACTION_PHASE && actionPhase == ActionPhases.MOVE_MOTHER_NATURE && currentActivePlayer == players.get(idPlayer).getOrder()) {
            if (gameTable.getIsleManager().isMNMovementAcceptable(idIsle, players.get(idPlayer).discardPile.getMnMovement())) {
                gameTable.getIsleManager().getIsle(gameTable.getIsleManager().getIsleWithMotherNatureIndex()).setMotherNature(false);
                gameTable.getIsleManager().getIsle(idIsle).setMotherNature(true);
                gameTable.getIsleManager().setIsleWithMotherNatureIndex(idIsle);
                checkUpdateInfluence(idIsle);
                checkEndGame();
                if (!lastRound)
                    actionPhase = ActionPhases.CHOOSE_CLOUD;
                else {
                    playerCounter++;
                    actionPhase = ActionPhases.MOVE_STUDENTS;
                    nextPlayer();
                }
            }
        }
    }

    /**
     * it picks the students present on a certain cloud and puts them in the entrance of the player
     * @param idPlayer is an integer that represents the index of the players ArrayList which corresponds to the player who chose the cloud
     * @param idCloud is the index of the chosen cloud
     */
    public void pickStudentsFromCloud(int idPlayer, int idCloud) {
        if (gamePhase == GamePhases.ACTION_PHASE && actionPhase == ActionPhases.CHOOSE_CLOUD && currentActivePlayer == players.get(idPlayer).getOrder()) {
            if (!gameTable.getCloud(idCloud).isEmpty()) {
                while (studentsCounter < maxMovableStudents) {
                    for (RealmColors rc : RealmColors.values()) {
                        if (gameTable.getCloud(idCloud).getStudentsByColor(rc) >= 1) {
                            gameTable.getCloud(idCloud).removeStudent(rc);
                            players.get(idPlayer).getDashboard().getEntrance().addStudent(rc);
                            break;
                        }
                    }
                    studentsCounter++;
                }
                studentsCounter = 0;
                playerCounter++;
                actionPhase = ActionPhases.MOVE_STUDENTS;
                nextPlayer();
            }
        }
    }

    public boolean isLastRound() { return lastRound;}

    /**
     * it returns a boolean that tells if the game is ended or not
     * @return the "endGame" boolean
     */
    public boolean isGameEnded() {
        return endGame;
    }

    /**
     * it returns a boolean that tells if the game is ended in a draw or not
     * @return the "drawEndGame" boolean
     */
    public boolean isGameEndedInADraw() {
        return drawEndGame;
    }

    /**
     * it returns the nickname of the player who won (or, if it is a 4 Players match, the squad)
     * @return the string of the winner
     */
    public String getWinner() {
        if (numberOfPlayers == 4) {
            return winner.getSquad().toString();
        } else
            return winner.nickname;
    }

    /**
     * depending on the number of players. it adds 7 or 9 students taken from the bag to a specific dashboard
     * @param idDashboard is the id of the dashboard whose entrance we want to fill
     */
    private void initializeEntrance(int idDashboard) {
        for (int i = 0; i < players.get(idDashboard).getDashboard().getEntrance().getMaxStudents(); i++) {
            players.get(idDashboard).getDashboard().getEntrance().addStudent(gameTable.getBag().draw());
        }
    }

    /**
     * it is used during PLANNING_PHASE, it fills clouds automatically
     */
    private void fillClouds() {
        if (gamePhase == GamePhases.PLANNING_PHASE && planningPhase == PlanningPhases.FILL_CLOUDS) {
            if (getGameTable().getBag().getNumberOfStudents() >= numberOfPlayers*maxMovableStudents) {
                for (int i = 0; i < numberOfPlayers; i++) {
                    Cloud cloud = gameTable.getCloud(i);
                    for (int j = 0; j < maxMovableStudents; j++) {
                        RealmColors color = gameTable.getBag().draw();
                        cloud.addStudent(color);
                    }
                }
            }
            else
                lastRound = true;
            planningPhase = PlanningPhases.ASSISTANT_CARD_PHASE;
        }
    }

    /**
     * according to the game phase, it updates when a player has the right to play
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
     * it updates an attribute of Game that is used to identify which player has the right to play in a specific moment
     */
    private void nextPlayer() {
        if (playerCounter == numberOfPlayers) {
            playerCounter = 0;
            if (gamePhase == GamePhases.PLANNING_PHASE) {
                gamePhase = GamePhases.ACTION_PHASE;
                planningPhase = PlanningPhases.FILL_CLOUDS;
            }
            else {
                gamePhase = GamePhases.PLANNING_PHASE;
                for (Player p : players) {
                    p.setDiscardPileNull();
                }
                fillClouds();
            }
            updateOrder(gamePhase);
        }
        else
            currentActivePlayer = CurrentOrder.getCurrentOrder(playerCounter);
    }

    /**
     * it is invoked when a student has been added to the dining room of a certain player and updates the professors owned by players, if it is necessary
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
            else {
                gameTable.removeProfessor(color);
                players.get(idPlayer).getDashboard().getDiningRoom().addProfessor(color);
            }
        }
    }

    /**
     * it is invoked when mother nature ends on acceptable isle and updates all tower references, if it is necessary (it also calls a method to verify if isles union has to occur)
     * @param idIsle is the index of the isle which has mother nature on it
     */
    private void checkUpdateInfluence(int idIsle) {
        int majorInfluence = 0;
        int conquerorIndex = 0;
        boolean draw = false;

        if (numberOfPlayers == 4) {
            int tempInfluence = 0;
            for (int i = 0; i < 2; i++) {
                for (Player p : players) {
                    if (p.getSquad() == Squads.getSquads(i))
                        tempInfluence = tempInfluence + gameTable.getIsleManager().getIsle(idIsle).getInfluence(p);
                }
                if (tempInfluence == majorInfluence)
                    draw = true;
                if (tempInfluence > majorInfluence) {
                    majorInfluence = tempInfluence;
                    conquerorIndex = i;
                    draw = false;
                }
                tempInfluence = 0;
            }
        }
        else {
            for (Player p : players) {
                if (gameTable.getIsleManager().getIsle(idIsle).getInfluence(p) == majorInfluence)
                    draw = true;
                if (gameTable.getIsleManager().getIsle(idIsle).getInfluence(p) > majorInfluence) {
                    majorInfluence = gameTable.getIsleManager().getIsle(idIsle).getInfluence(p);
                    conquerorIndex = p.getDashboard().getIdDashboard();
                    draw = false;
                }
            }
        }

        if (!draw) {
            if (gameTable.getIsleManager().getIsle(idIsle).getTowersColor() != players.get(conquerorIndex).getDashboard().getTowerStorage().getTowerColor()) {
                if (gameTable.getIsleManager().getIsle(idIsle).getTowersColor() == TowerColors.NOCOLOR)
                    players.get(conquerorIndex).getDashboard().getTowerStorage().removeTower();
                else {
                    for (Player p : players) {
                        if (p.getDashboard().getTowerStorage().getTowerColor() == gameTable.getIsleManager().getIsle(idIsle).getTowersColor()) {
                            for (int i = 0; i < gameTable.getIsleManager().getIsle(idIsle).getNumOfIsles(); i++) {
                                p.getDashboard().getTowerStorage().addTower();
                                players.get(conquerorIndex).getDashboard().getTowerStorage().removeTower();
                            }
                            break;
                        }
                    }
                }
                gameTable.getIsleManager().getIsle(idIsle).setTower(players.get(conquerorIndex).getDashboard().getTowerStorage().getTowerColor());
                gameTable.getIsleManager().checkUnifyIsle(idIsle);
            }
        }
    }

    /**
     * it checks if the game has ended and updates the proper end game variables
     */
    private void checkEndGame() {
        for (Player p : players) {
            if (p.getDashboard().getTowerStorage().getNumberOfTowers() == 0 && p.getDashboard().getTowerStorage().getTowerColor() != TowerColors.NOCOLOR) {
                endGame = true;
                winner = p;
                return;
            }
        }

        if ((gameTable.getIsleManager().getIsles().size() == 3) || (lastRound && playerCounter == numberOfPlayers-1)) {
            endGame = true;

            int minorNumOfTowersInStorage = 8;
            int winnerIndex = 0;
            boolean draw = false;

            for (Player p : players) {
                if (p.getDashboard().getTowerStorage().getNumberOfTowers() == minorNumOfTowersInStorage && p.getDashboard().getTowerStorage().getTowerColor() != TowerColors.NOCOLOR)
                    draw = true;
                if (p.getDashboard().getTowerStorage().getNumberOfTowers() < minorNumOfTowersInStorage) {
                    minorNumOfTowersInStorage = p.getDashboard().getTowerStorage().getNumberOfTowers();
                    winnerIndex = p.getDashboard().getIdDashboard();
                    draw = false;
                }
            }

            if (draw) {
                int majorProfessors = 0;

                for (Player p : players) {
                    if (p.getDashboard().getDiningRoom().getNumberOfProfessors() == majorProfessors && p.getDashboard().getTowerStorage().getNumberOfTowers() == minorNumOfTowersInStorage)
                        drawEndGame = true;
                    if (p.getDashboard().getDiningRoom().getNumberOfProfessors() > majorProfessors && p.getDashboard().getTowerStorage().getNumberOfTowers() == minorNumOfTowersInStorage) {
                        majorProfessors = p.getDashboard().getDiningRoom().getNumberOfProfessors();
                        winnerIndex = p.getDashboard().getIdDashboard();
                        drawEndGame = false;
                    }
                }
            }

            winner = players.get(winnerIndex);
        }

    }

    /*public String getState() {
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
    }*/

    public void playCharacterCard(int idPlayer, CharacterCard characterCard) {

        //if (gamePhase == GamePhases.ACTION_PHASE && !getPlayerByIndex(idPlayer).getAlreadyPlayedACardThisTurn() && currentActivePlayer == players.get(idPlayer).getOrder()) {
            getPlayerByIndex(idPlayer).playCharacterCard(characterCard);
            effectInGameFactory.getEffect(characterCard, players, gameTable, getPlayerByIndex(idPlayer));
        //}
    }

    public GamePhases getGamePhase() {
        return gamePhase;
    }

    public PlanningPhases getPlanningPhase() {
        return planningPhase;
    }

    public ActionPhases getActionPhase() {
        return actionPhase;
    }

    public CurrentOrder getCurrentActivePlayer() {
        return currentActivePlayer;
    }

}

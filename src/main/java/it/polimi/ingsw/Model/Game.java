package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.CharacterCards.CharacterCard;
import it.polimi.ingsw.Model.CharacterCards.CharacterCardsName;
import it.polimi.ingsw.Model.CharacterCards.EffectInGameFactory;
import it.polimi.ingsw.Model.Enumeration.*;
import it.polimi.ingsw.Model.GameTableObjects.Cloud;
import it.polimi.ingsw.Model.GameTableObjects.GameTable;
import it.polimi.ingsw.Model.GameTableObjects.Isle;
import it.polimi.ingsw.Model.Player.AssistantCard;
import it.polimi.ingsw.Model.Player.Player;
import it.polimi.ingsw.Observer.ModelObserver;
import it.polimi.ingsw.Observer.Subjects.ModelSubject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

public class Game extends ModelSubject {

    /**
     * The game mode of the game, it is decided by the first player that joins the game.
     */
    private GameMode gameMode;
    /**
     * The number of players that are going to play the game, it is decided by the first player that joins the game.
     */
    private int numberOfPlayers;
    /**
     * The number of players that already joined the game.
     */
    private int actualNumberOfPlayers = 0;
    /**
     * The game table where the game is going to be played, it contains many objects that compose the game itself.
     */
    private GameTable gameTable;
    /**
     * The list of players that are playing the game.
     */
    private final ArrayList<Player> players;
    /**
     * Random index used to say who will be the first player to play in the first turn.
     */
    private int firstPlayerIndex;
    /**
     * The number of students that is possible to move in a turn for each player.
     */
    private int maxMovableStudents;
    /**
     * Counter used to check how many players already played their turn in a game phase.
     */
    private int playerCounter = 0;
    /**
     * Counter of the students moved.
     */
    private int studentsCounter = 0;
    /**
     * Boolean value that indicates if it is the last round of a game because a player already played
     * all his assistant cards or if the students in the bag were not enough to end the round for everyone.
     */
    private boolean lastRound = false;
    /**
     * When true it means the game ended, else false.
     */
    private boolean endGame = false;
    /**
     * When true it means the game ended in a draw, else false.
     */
    private boolean drawEndGame = false;
    /**
     * Indicates the player that won the game.
     */
    private Player winner;
    /**
     * Phases in which it's divided the game.
     */
    private GamePhases gamePhase;
    /**
     * Divides the planning phase in 2 different sub phases, and it indicates that the player turn is in the planning phase.
     */
    private PlanningPhases planningPhase;
    /**
     * Divides the planning phase in 3 different sub phases, and it indicates that the player turn is in the action phase.
     */
    private ActionPhases actionPhase;
    /**
     * Indicates which was the phase of the action phase before entering in the CHARACTER_CARD_PHASE.
     */
    private ActionPhases lastActionPhase;
    /**
     * Indicates who is the current active player that is playing his turn.
     */
    private CurrentOrder currentActivePlayer;
    /**
     * Factory that permits to build the effect of each playable character card.
     */
    private final EffectInGameFactory effectInGameFactory;
    /**
     * Saves the color chosen by the player when activating the FUNGIST character card.
     */
    private RealmColors colorForFungist;
    /**
     * Saves the number of students of the chosen color removed from the isle
     * when checking the influence the turn the FUNGIST has been played.
     */
    private int studentsRemovedByFungist=0;
    /**
     * Offset that changes professor conquest rule when a FARMER has been activated.
     */
    private int farmerOffset = 0;
    /**
     * Counts the number of time the atomic effect of a character card has been activated.
     */
    private int atomicEffectCounter = 0;

    /**
     * Game constructor.
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
     * First method that has to be called to properly start the game.
     * Adds the first player to the game and sets game mode and number of players basing on his choices.
     * @param nickName chosen by the player.
     * @param expertMode chosen by the player (true for expert, else base).
     * @param numberOfPlayers chosen by the player (from 2 to 4).
     */
    public void addFirstPlayer(String nickName, boolean expertMode, int numberOfPlayers){

        setNumberOfPlayers(numberOfPlayers);

        if(expertMode)
            this.gameMode=GameMode.EXPERT;
        else
            this.gameMode=GameMode.BASE;

        Player newPlayer;

        gameTable = new GameTable(numberOfPlayers, this.gameMode);

        if (numberOfPlayers==4)
            newPlayer = new Player(nickName, numberOfPlayers, actualNumberOfPlayers, Squads.SQUAD1);
        else
            newPlayer = new Player(nickName, numberOfPlayers, actualNumberOfPlayers, Squads.NO_SQUAD);

        players.add(newPlayer);
        initializeEntrance(actualNumberOfPlayers);
        actualNumberOfPlayers = 1;

    }

    /**
     * Sets the number of player when selected by the first player.
     * @param numberOfPlayers to play with in total.
     */
    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
        if (numberOfPlayers == 3)
            maxMovableStudents = 4;
        else
            maxMovableStudents = 3;
    }

    /**
     * First action to do when a player that is not the first one asks to join a match.
     * @param nickname the nickname of the player that has to be added.
     */
    public void addAnotherPlayer(String nickname) {
        Player newPlayer;
        if (numberOfPlayers == 2 || numberOfPlayers == 3)
            newPlayer = new Player(nickname, numberOfPlayers, actualNumberOfPlayers, Squads.NO_SQUAD);
        else {
            if (actualNumberOfPlayers == 2)
                newPlayer = new Player(nickname, numberOfPlayers, actualNumberOfPlayers, Squads.SQUAD1);
            else
                newPlayer = new Player(nickname, numberOfPlayers, actualNumberOfPlayers, Squads.SQUAD2);
        }
        players.add(newPlayer);
        initializeEntrance(actualNumberOfPlayers);
        actualNumberOfPlayers++;

        if (actualNumberOfPlayers == numberOfPlayers) {     // we are ready to go
            this.gamePhase = GamePhases.PLANNING_PHASE;
            firstPlayerIndex = (int)(Math.random()*(numberOfPlayers));
            notifyGameCreationValues();
            fillClouds();
            updateOrder(gamePhase);
        }
    }

    /**
     * Initialization of data to notify the virtual view with.
     */
    private void notifyGameCreationValues() {
        // DASHBOARDS:
        ArrayList<String> nicknames = new ArrayList<>();
        ArrayList<HashMap<RealmColors,Integer>> entrances = new ArrayList<>();
        ArrayList<TowerColors> towerColors = new ArrayList<>();
        ArrayList<Integer> numTowers = new ArrayList<>();
        ArrayList<Squads> squads=new ArrayList<>();
        for(Player p:players){
            towerColors.add(p.getDashboard().getTowerStorage().getTowerColor());
            entrances.add(p.getDashboard().getEntrance().getStudentsHashMap());
            nicknames.add(p.getNickname());
            numTowers.add(p.getDashboard().getTowerStorage().getNumberOfTowers());
            squads.add(p.getSquad());
        }
        int whereMNId=gameTable.getIsleManager().getIsleWithMotherNatureIndex();
        int money=players.get(0).getMoney();

        // GAME TABLE:
        ArrayList<HashMap<RealmColors,Integer>> emptyClouds=new ArrayList<>();
        for(Cloud c:gameTable.getClouds()){ emptyClouds.add(c.getStudentsHashMap()); }
        int generalReserve=gameTable.getGeneralMoneyReserve();

        ArrayList<HashMap<RealmColors,Integer>> studentsOnIsle=new ArrayList<>();
        for(Isle i:gameTable.getIsleManager().getIsles()){ studentsOnIsle.add(i.getStudentsHashMap()); }

        ArrayList<String> characterNames=new ArrayList<>();
        ArrayList<Integer> characterCost=new ArrayList<>();
        ArrayList<Integer> denyCards=new ArrayList<>();
        ArrayList<HashMap<RealmColors,Integer>> studentsOnCard=new ArrayList<>();
        ArrayList<String> characterCardsDescription = new ArrayList<>();
        for(CharacterCard card : gameTable.getCharacterCards()){
            characterNames.add(card.getCharacterCardName().toString());
            characterCost.add(card.getCost());
            denyCards.add(card.getDenyCards());
            studentsOnCard.add(card.getStudentsHashMap());
            characterCardsDescription.add(card.getCharacterCardDescription());
        }

        notifyObserver(obs->obs.onGameCreation(numberOfPlayers,nicknames,gameMode,whereMNId,entrances,emptyClouds,studentsOnIsle,studentsOnCard,numTowers,money,generalReserve,towerColors,characterNames,characterCost,denyCards,characterCardsDescription,squads));
    }

    /**
     * Handles a player that plays an assistant card.
     * It is responsible for changing the discard pile of a certain player.
     * @param playerID is an integer that represents the index of the players ArrayList which corresponds to the player who played the assistant card.
     * @param turnOrderPlayed is the turn order of the AssistantCard played.
     */
    public void playAssistantCard(int playerID, int turnOrderPlayed) {
        boolean alreadyPlayed = false;
        boolean onlyChoice = true;
        AssistantCard assistantCardPlayed = players.get(playerID).getAssistantCardByTurnOrder(turnOrderPlayed);
        if (gamePhase == GamePhases.PLANNING_PHASE && planningPhase == PlanningPhases.ASSISTANT_CARD_PHASE && currentActivePlayer == players.get(playerID).getOrder()) {
            if (assistantCardPlayed != null) {
                for (Player p : players) {   // next round incoming... discard piles need to be set null (otherwise it doesn't work)
                    if (p.getDiscardPile() != null) {
                        if (p.getDiscardPile().equals(assistantCardPlayed)) {
                            alreadyPlayed = true;
                            break;
                        }
                    }
                }

                if (alreadyPlayed) {
                    alreadyPlayed = false;
                    for (AssistantCard ac : players.get(playerID).getMageDeck()) {
                        for (Player p : players)
                            if (p.getDiscardPile() != null)
                                if (p.getDiscardPile().equals(ac)) {
                                    alreadyPlayed = true;
                                    break;
                                }
                        if (!alreadyPlayed) {
                            onlyChoice = false;
                            break;
                        }
                        alreadyPlayed = false;
                    }
                    alreadyPlayed = true;
                }

                if (!alreadyPlayed || onlyChoice) {
                    players.get(playerID).playAssistantCard(assistantCardPlayed);
                    players.get(playerID).setCardOrder(playerCounter + 1);

                notifyAssistantsCard(playerID);

                    if (players.get(playerID).isMageDeckEmpty())
                        lastRound = true;
                    playerCounter++;
                    nextPlayer();
                } else {
                    notifyObserver(obs -> obs.onKO(playerID, "You cannot play this card, please select another one"));
                    notifyTurn();
                }
            } else {
                notifyObserver(obs -> obs.onKO(playerID, "You don't have this card, please select another one"));
                notifyTurn();
            }
        }
    }

    /**
     * Initialize the deck of available assistant cards for the player and the assistant card in the discard pile in order to pass it to the view.
     * @param playerID the id of the player it will be passed the deck.
     */
    private void notifyAssistantsCard(int playerID) {
        ArrayList<Integer> turnOrders =new ArrayList<>();
        ArrayList<Integer> movementsMN =new ArrayList<>();
        for(AssistantCard ac : getPlayerByIndex(playerID).getMageDeck()){
            turnOrders.add(ac.getTurnOrder());
            movementsMN.add(ac.getMnMovement());
        }
        notifyObserver(obs->obs.onAssistantCard(playerID,getPlayerByIndex(playerID).getDiscardPile().getTurnOrder(),getPlayerByIndex(playerID).getDiscardPile().getMnMovement(), turnOrders, movementsMN));
    }

    /**
     * Takes a student from the dashboard of a certain player and puts it on a specified isle.
     * @param playerID ID of the player who moved the student.
     * @param isleID ID of the chosen island.
     * @param colorIndex is the index of the color of the student that has been moved.
     */
    public void moveStudentInIsle(int playerID, int isleID, int colorIndex) {
        RealmColors color = RealmColors.getColor(colorIndex);
        if (gamePhase == GamePhases.ACTION_PHASE && actionPhase == ActionPhases.MOVE_STUDENTS && currentActivePlayer == players.get(playerID).getOrder()) {
            if (colorIndex >= 0 && colorIndex <= 4) {
                if (players.get(playerID).getDashboard().getEntrance().getStudentsByColor(color) > 0) {
                    if (isleID >= 0 && isleID < gameTable.getIsleManager().getIsles().size()) {
                        players.get(playerID).getDashboard().getEntrance().removeStudent(color);
                        gameTable.getIsleManager().getIsle(isleID).addStudent(color);
                        studentsCounter++;
                        if (studentsCounter == maxMovableStudents) {
                            actionPhase = ActionPhases.MOVE_MOTHER_NATURE;
                            studentsCounter = 0;
                        }
                        notifyObserver(obs -> obs.onStudentMoving_toIsle(playerID, players.get(playerID).getDashboard().getEntrance().getStudentsHashMap(), isleID, gameTable.getIsleManager().getIsle(isleID).getStudentsHashMap()));
                    } else
                        notifyObserver(obs -> obs.onKO(playerID, "Isle " + isleID + " doesn't exist, please select another one"));
                } else
                    notifyObserver(obs -> obs.onKO(playerID, "You don't have enough " + color.toString() + " students, please select another color"));
            } else
                notifyObserver(obs -> obs.onKO(playerID, "This color doesn't exist, please select another one"));
        }

        notifyTurn();
    }

    /**
     * Takes a student from the dashboard of a certain player and puts it in his dining room.
     * @param playerID ID of the player who moved the student.
     * @param colorIndex color of the student that has been moved. 
     */
    public void moveStudentInDiningRoom(int playerID, int colorIndex) {
        RealmColors color = RealmColors.getColor(colorIndex);
        if (gamePhase == GamePhases.ACTION_PHASE && actionPhase == ActionPhases.MOVE_STUDENTS && currentActivePlayer == players.get(playerID).getOrder()) {
            if (colorIndex >= 0 && colorIndex <= 4) {
                if (players.get(playerID).getDashboard().getEntrance().getStudentsByColor(color) > 0) {
                    if (players.get(playerID).getDashboard().getDiningRoom().getStudentsByColor(color) <= 10) {
                        players.get(playerID).getDashboard().getEntrance().removeStudent(color);
                        players.get(playerID).getDashboard().getDiningRoom().addStudent(color);
                        // if FARMER character card is played by the player this turn
                        // then each time is checked if the player has an equal number of students of another
                        // player that owe the professor of that color
                        if (gameMode == GameMode.EXPERT && getPlayerByIndex(playerID).getAlreadyPlayedACardThisTurn() && getPlayerByIndex(playerID).getCharacterCardPlayed() == CharacterCardsName.FARMER)
                            farmerOffset = 1;
                        else
                            farmerOffset = 0;
                        // checking if the student is added in third, sixth or ninth position of the dining room
                        checkStudentInMoneyPosition(playerID,color);
                        checkUpdateProfessor(playerID, color);
                        studentsCounter++;
                        if (studentsCounter == maxMovableStudents) {
                            actionPhase = ActionPhases.MOVE_MOTHER_NATURE;
                            studentsCounter = 0;
                        }

                        notifyObserver(obs -> obs.onStudentMoving_toDining(playerID, players.get(playerID).getDashboard().getEntrance().getStudentsHashMap(), players.get(playerID).getDashboard().getDiningRoom().getStudentsHashMap()));
                    } else
                        notifyObserver(obs -> obs.onKO(playerID, "Your " + color.toString() + " Dining Room is full! You can't add " + color + " students, please select another color"));
                } else
                    notifyObserver(obs -> obs.onKO(playerID, "You don't have enough " + color.toString() + " students, please select another color"));
            } else
                notifyObserver(obs -> obs.onKO(playerID, "This color doesn't exist, please select another one"));
        }

        notifyTurn();
    }

    /**
     * Checking if the student is added in third, sixth or ninth position of the dining room (when playing expert mode).
     * @param playerID ID of the player that placed a student in his dining room.
     * @param color color of the student placed.
     */
    public void checkStudentInMoneyPosition(int playerID, RealmColors color) {
        if (isExpertMode() && players.get(playerID).getDashboard().getDiningRoom().getStudentsByColor(color) % 3 == 0 && gameTable.getGeneralMoneyReserve()>0) {
            players.get(playerID).gainMoney();
            gameTable.studentInMoneyPosition();
            notifyObserver(obs -> obs.onMoneyUpdate(playerID, players.get(playerID).getMoney(), gameTable.getGeneralMoneyReserve()));
        }
    }

    /**
     * Moves mother nature on a certain isle.
     * @param playerID ID of the player who moved the mother nature.
     * @param isleID ID of the isle where the player wants to move mother nature.
     */
    public void moveMotherNature(int playerID, int isleID) {
        if (gamePhase == GamePhases.ACTION_PHASE && actionPhase == ActionPhases.MOVE_MOTHER_NATURE && currentActivePlayer == players.get(playerID).getOrder()) {
            if (isleID >= 0 && isleID < gameTable.getIsleManager().getIsles().size()) {
                if (gameTable.getIsleManager().isMNMovementAcceptable(isleID, players.get(playerID).getDiscardPile().getMnMovement())) {
                    gameTable.getIsleManager().getIsle(gameTable.getIsleManager().getIsleWithMotherNatureIndex()).setMotherNature(false);
                    gameTable.getIsleManager().getIsle(isleID).setMotherNature(true);
                    gameTable.getIsleManager().setIsleWithMotherNatureIndex(isleID);
                    // if the FUNGIST card is played, then we remove the students of the chosen color from the selected isle
                    // then we proceed to calculate the influence on the isle
                    if (gameMode == GameMode.EXPERT && getPlayerByIndex(playerID).getAlreadyPlayedACardThisTurn() && getPlayerByIndex(playerID).getCharacterCardPlayed() == CharacterCardsName.FUNGIST)
                        while (gameTable.getIsleManager().getIsle(isleID).getStudentsByColor(colorForFungist) != 0) {
                            gameTable.getIsleManager().getIsle(isleID).removeStudent(colorForFungist);
                            studentsRemovedByFungist += 1;
                        }
                    if (gameTable.getIsleManager().getIsle(isleID).getDenyCards() == 0)
                        checkUpdateInfluence(isleID);
                    else {
                        gameTable.getIsleManager().getIsle(isleID).removeDenyCard();
                        int grandmaIndex = 0;
                        for (int i=0; i<3; i++)
                            if(gameTable.getCharacterCard(i).getCharacterCardName() == CharacterCardsName.GRANDMA_HERBS){
                                grandmaIndex = i;
                                break;
                            }
                        gameTable.getCharacterCard(grandmaIndex).addDenyCard();
                        int finalGrandmaIndex = grandmaIndex;
                        notifyObserver(obs->obs.onDenyCard(finalGrandmaIndex, gameTable.getCharacterCard(finalGrandmaIndex).getCost(), gameTable.getCharacterCard(finalGrandmaIndex).getDenyCards(), gameTable.getCharacterCard(finalGrandmaIndex).getStudentsHashMap(), isleID, gameTable.getIsleManager().getIsle(isleID).getDenyCards()));
                    }
                    // if the FUNGIST card is played, then we add the students of the chosen color
                    // that were removed from the selected isle
                    if (gameMode == GameMode.EXPERT && getPlayerByIndex(playerID).getAlreadyPlayedACardThisTurn() && getPlayerByIndex(playerID).getCharacterCardPlayed() == CharacterCardsName.FUNGIST)
                        while (gameTable.getIsleManager().getIsle(isleID).getStudentsByColor(colorForFungist) != studentsRemovedByFungist)
                            gameTable.getIsleManager().getIsle(isleID).addStudent(colorForFungist);
                    studentsRemovedByFungist = 0;
                    checkEndGame();
                    if (!lastRound) {
                        actionPhase = ActionPhases.CHOOSE_CLOUD;
                        notifyMNMovement(false);
                        notifyTurn();
                    } else {
                        playerCounter++;
                        actionPhase = ActionPhases.MOVE_STUDENTS;
                        notifyMNMovement(false);
                        nextPlayer();
                    }
                } else {
                    notifyObserver(obs -> obs.onKO(playerID, "You can't go that far! Please select a suitable isle"));
                    notifyTurn();
                }
            } else {
                notifyObserver(obs -> obs.onKO(playerID, "Isle " + isleID + " doesn't exist, please select another one"));
                notifyTurn();
            }
        }
    }

    /**
     * Initialize the parameters needed by the view consequently to the movement of mother nature.
     * @param isEffect if is true then it will notify that an atomic effect that involves the movement of mother nature has been played,
     *                 else false when mother nature is moved at the end of the turn.
     */
    private void notifyMNMovement(boolean isEffect) {
        ArrayList<HashMap<RealmColors,Integer>> students = new ArrayList<>();
        ArrayList<Integer> numIsles = new ArrayList<>();
        ArrayList<TowerColors> towerColors = new ArrayList<>();
        ArrayList<Boolean> denyCards = new ArrayList<>();
        ArrayList<Integer> numTowers = new ArrayList<>();
        int whereMnId = 0;
        for(Isle isle : gameTable.getIsleManager().getIsles()){
            students.add(isle.getStudentsHashMap());
            numIsles.add(isle.getNumOfIsles());
            towerColors.add(isle.getTowersColor());
            if(isle.getDenyCards()==0)
                denyCards.add(false);
            else
                denyCards.add(true);
            if(isle.getMotherNature())
                whereMnId=isle.getIsleIndex();
        }
        for (Player p : players)
            numTowers.add(p.getDashboard().getTowerStorage().getNumberOfTowers());
        int finalWhereMnId = whereMnId;
        if (isEffect)
            notifyObserver(obs->obs.onEffectActivation(gameTable.getIsleManager().getIsles().size(), students, towerColors, finalWhereMnId, denyCards, numIsles, numTowers));
        else
            notifyObserver(obs->obs.onMNMovement(gameTable.getIsleManager().getIsles().size(), students, towerColors, finalWhereMnId,denyCards,numIsles, numTowers));
    }

    /**
     * It picks the students present on a certain cloud and puts them on the entrance of the player.
     * @param playerID ID of the player who chose the cloud.
     * @param cloudID ID of the chosen cloud.
     */
    public void pickStudentsFromCloud(int playerID, int cloudID) {
        if (gamePhase == GamePhases.ACTION_PHASE && actionPhase == ActionPhases.CHOOSE_CLOUD && currentActivePlayer == players.get(playerID).getOrder()) {
            if (cloudID >= 0 && cloudID < numberOfPlayers) {
                if (!gameTable.getCloud(cloudID).isEmpty()) {
                    while (studentsCounter < maxMovableStudents) {
                        for (RealmColors rc : RealmColors.values())
                            if (gameTable.getCloud(cloudID).getStudentsByColor(rc) >= 1) {
                                gameTable.getCloud(cloudID).removeStudent(rc);
                                players.get(playerID).getDashboard().getEntrance().addStudent(rc);
                                break;
                            }
                        studentsCounter++;
                    }
                    studentsCounter = 0;
                    playerCounter++;
                    actionPhase = ActionPhases.MOVE_STUDENTS;
                    notifyObserver(obs -> obs.onCloudUpdate(playerID, players.get(playerID).getDashboard().getEntrance().getStudentsHashMap(), cloudID));
                    nextPlayer();
                } else {
                    notifyObserver(obs -> obs.onKO(playerID, "This cloud is empty! Please select another one"));
                    notifyTurn();
                }
            } else {
                notifyObserver(obs -> obs.onKO(playerID, "Cloud " + cloudID + " doesn't exist, please select another one"));
                notifyTurn();
            }
        }
    }

    /**
     * Getter method for the last round.
     * @return true if this was the last round, else false.
     */
    public boolean isLastRound() { return lastRound;}

    /**
     * Getter method for the end of the game state.
     * @return if the game ended or not.
     */
    public boolean isGameEnded() { return endGame; }

    /**
     * Getter method for the end of the game in a draw state.
     * @return if the game ended in a draw or not.
     */
    public boolean isGameEndedInADraw() { return drawEndGame; }

    /**
     * Getter method for the nickname of the player who won (or, if it is a 4 Players match, the squad).
     * @return the nickname of the winner.
     */
    public String getWinner() {
        if (numberOfPlayers == 4) 
            return winner.getSquad().toString();
        else
            return winner.getNickname();
    }

    /**
     * Adds 7 or 9 students taken from the bag to a specific dashboard (depending on the number of players).
     * @param dashboardID is the ID of the dashboard whose entrance we want to fill.
     */
    private void initializeEntrance(int dashboardID) {
        for (int i = 0; i < players.get(dashboardID).getDashboard().getEntrance().getMaxStudents(); i++) 
            players.get(dashboardID).getDashboard().getEntrance().addStudent(gameTable.getBag().draw());
    }

    /**
     * Fills clouds automatically (used during PLANNING_PHASE).
     */
    private void fillClouds() {
        if (gamePhase == GamePhases.PLANNING_PHASE && planningPhase == PlanningPhases.FILL_CLOUDS) {
            if (getGameTable().getBag().getNumberOfStudents() >= numberOfPlayers*maxMovableStudents) {
                ArrayList<HashMap<RealmColors,Integer>> cloudsList=new ArrayList<>();
                for (int i = 0; i < numberOfPlayers; i++) {
                    Cloud cloud = gameTable.getCloud(i);
                    for (int j = 0; j < maxMovableStudents; j++) {
                        RealmColors color = gameTable.getBag().draw();
                        cloud.addStudent(color);
                    }
                    cloudsList.add(cloud.getStudentsHashMap());
                }

                notifyObserver(obs->obs.onFillCloud(cloudsList));
            }
            else
                lastRound = true;
            planningPhase = PlanningPhases.ASSISTANT_CARD_PHASE;
        }
    }

    /**
     * Updates when a player has the right to play according to the game phase.
     * @param gamePhase used in order to execute different statements.
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
                turnOrderTree.add(Integer.valueOf(Integer.valueOf(p.getDiscardPile().getTurnOrder()).toString()+Integer.valueOf(p.getCardOrder()).toString()));

            for (playerCounter = 0; playerCounter < numberOfPlayers; playerCounter++) {
                if (!turnOrderTree.isEmpty())
                    lowestTurnOrder = turnOrderTree.pollFirst();
                for (Player p : players) {
                    if (Integer.parseInt(Integer.valueOf(p.getDiscardPile().getTurnOrder()).toString()+Integer.valueOf(p.getCardOrder()).toString()) == lowestTurnOrder) {
                        p.setOrder(CurrentOrder.getCurrentOrder(playerCounter));
                        break;
                    }
                }
            }

            for (Player p : players)
                if (p.getOrder() == CurrentOrder.FIRST_PLAYER)
                    firstPlayerIndex = p.getDashboard().getDashboardID();
            currentActivePlayer = CurrentOrder.FIRST_PLAYER;
            playerCounter = 0;
        }

        notifyTurn();

    }

    /**
     * Initialize the parameters that handle the turn order and notifies the view.
     */
    public void notifyTurn() {
        if (endGame) {
            if (drawEndGame)
                notifyObserver(obs->obs.onEndGame(getWinner(), -1));
            else
                notifyObserver(obs->obs.onEndGame(getWinner(), winner.getDashboard().getDashboardID()));
        }
        else {
            int currentPlayerIndex = 0;
            String currentPlayerNickname = null;
            for (Player p : players)
                if (p.getOrder().equals(currentActivePlayer)) {
                    currentPlayerIndex = players.indexOf(p);
                    currentPlayerNickname = p.getNickname();
                    break;
                }
            int finalCurrentPlayerIndex = currentPlayerIndex;
            String finalCurrentPlayerNickname = currentPlayerNickname;
            notifyObserver(obs -> obs.onGamePhases(finalCurrentPlayerIndex, finalCurrentPlayerNickname, gamePhase, actionPhase));
        }
    }

    /**
     * It updates an attribute of Game that is used to identify which player has the right to play in a specific moment.
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
                for (Player p : players)
                    p.setDiscardPileNull();
                fillClouds();
                if(gameMode==GameMode.EXPERT)
                    for(Player p : players)
                        p.setNotAlreadyPlayedACardThisTurn();
            }
            updateOrder(gamePhase);
        }
        else {
            currentActivePlayer = CurrentOrder.getCurrentOrder(playerCounter);
            notifyTurn();
        }
    }

    /**
     * Invoked when a student has been added to the dining room of a certain player and updates the professors owned by players, if it is necessary.
     * @param playerID index of the players ArrayList which corresponds to the player whose dining room has been updated.
     * @param color color of the student that has been moved to the dining room, therefore it is the color of the professor we need to check.
     */
    public void checkUpdateProfessor(int playerID, RealmColors color) {
        if (players.get(playerID).getDashboard().getDiningRoom().getProfessorByColor(color) == 0) {
            int playerWhoHasProfessorIndex = 0;
            boolean someoneHasProfessor = false;

            for (Player p : players)
                if (p.getDashboard().getDiningRoom().getProfessorByColor(color) == 1) {
                    playerWhoHasProfessorIndex = p.getDashboard().getDashboardID();
                    someoneHasProfessor = true;
                    break;
                }

            if (someoneHasProfessor) {
                if (players.get(playerID).getDashboard().getDiningRoom().getStudentsByColor(color) > players.get(playerWhoHasProfessorIndex).getDashboard().getDiningRoom().getStudentsByColor(color)-farmerOffset) {
                    players.get(playerWhoHasProfessorIndex).getDashboard().getDiningRoom().removeProfessor(color);
                    players.get(playerID).getDashboard().getDiningRoom().addProfessor(color);
                }
            }
            else {
                gameTable.removeProfessor(color);
                players.get(playerID).getDashboard().getDiningRoom().addProfessor(color);
            }
            //observer parameters initialization
            ArrayList<HashMap<RealmColors,Integer>> professors=new ArrayList<>();
            for(Player p:players)
                professors.add(p.getDashboard().getDiningRoom().getProfessors());
            notifyObserver(obs->obs.onProfessorUpdate(professors));
        }

    }

    /**
     * Invoked when mother nature ends on acceptable isle and updates all tower references, 
     * if it is necessary (it also calls a method to verify if isles union has to occur).
     * @param isleID index of the isle which has mother nature on it.
     */
    public void checkUpdateInfluence(int isleID) {
        int majorInfluence = 0;
        int conquerorIndex = 0;
        boolean draw = false;

        if (numberOfPlayers == 4) {
            int tempInfluence = 0;
            for (int i = 0; i < 2; i++) {
                for (Player p : players)
                    if (p.getSquad() == Squads.getSquads(i))
                        tempInfluence = tempInfluence + gameTable.getIsleManager().getIsle(isleID).getInfluences(players).get(p.getDashboard().getDashboardID());
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
                if (gameTable.getIsleManager().getIsle(isleID).getInfluences(players).get(p.getDashboard().getDashboardID()) == majorInfluence)
                    draw = true;
                if (gameTable.getIsleManager().getIsle(isleID).getInfluences(players).get(p.getDashboard().getDashboardID()) > majorInfluence) {
                    majorInfluence = gameTable.getIsleManager().getIsle(isleID).getInfluences(players).get(p.getDashboard().getDashboardID());
                    conquerorIndex = p.getDashboard().getDashboardID();
                    draw = false;
                }
            }
        }

        if (!draw) {
            if (gameTable.getIsleManager().getIsle(isleID).getTowersColor() != players.get(conquerorIndex).getDashboard().getTowerStorage().getTowerColor()) {
                if (gameTable.getIsleManager().getIsle(isleID).getTowersColor() == TowerColors.NOCOLOR)
                    players.get(conquerorIndex).getDashboard().getTowerStorage().removeTower();
                else {
                    for (Player p : players) {
                        if (p.getDashboard().getTowerStorage().getTowerColor() == gameTable.getIsleManager().getIsle(isleID).getTowersColor()) {
                            for (int i = 0; i < gameTable.getIsleManager().getIsle(isleID).getNumOfIsles(); i++) {
                                p.getDashboard().getTowerStorage().addTower();
                                players.get(conquerorIndex).getDashboard().getTowerStorage().removeTower();
                            }
                            break;
                        }
                    }
                }
                gameTable.getIsleManager().getIsle(isleID).setTower(players.get(conquerorIndex).getDashboard().getTowerStorage().getTowerColor());
                gameTable.getIsleManager().checkUnifyIsle(isleID);
            }
        }
    }

    /**
     * It checks if the game has ended and updates the proper end game variables.
     */
    public void checkEndGame() {
        for (Player p : players)
            if (p.getDashboard().getTowerStorage().getNumberOfTowers() == 0 && p.getDashboard().getTowerStorage().getTowerColor() != TowerColors.NOCOLOR) {
                endGame = true;
                winner = p;
                return;
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
                    winnerIndex = p.getDashboard().getDashboardID();
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
                        winnerIndex = p.getDashboard().getDashboardID();
                        drawEndGame = false;
                    }
                }
            }

            winner = players.get(winnerIndex);
        }

    }

    /**
     * Permits to play a character card during the action phase.
     * It set money and other booleans but doesn't activate the proper effect of the card.
     * @param playerID the id of the player who plays a certain character card during his action phase.
     * @param characterCardIndex the index of the character card that the player wants to play.
     */
    public void playCharacterCard(int playerID, int characterCardIndex) {
        if (gamePhase == GamePhases.ACTION_PHASE && currentActivePlayer == players.get(playerID).getOrder()){
            CharacterCardsName cardName = getGameTable().getCharacterCards().get(characterCardIndex).getCharacterCardName();
            if (!players.get(playerID).getAlreadyPlayedACardThisTurn()) {
                if (characterCardIndex >= 0 && characterCardIndex <= 2) {
                    if (players.get(playerID).getMoney() >= gameTable.getCharacterCard(characterCardIndex).getCost()) {
                        if (cardName != CharacterCardsName.GRANDMA_HERBS || getGameTable().getCharacterCards().get(characterCardIndex).getDenyCards() > 0) {
                            getGameTable().characterCardPlayed(characterCardIndex);
                            getPlayerByIndex(playerID).playCharacterCard(getGameTable().getCharacterCard(characterCardIndex));
                            lastActionPhase = actionPhase;
                            actionPhase = ActionPhases.CHARACTER_CARD_PHASE;
                            notifyObserver(obs -> obs.onCharacterCard(characterCardIndex, getGameTable().getCharacterCards().get(characterCardIndex).getCharacterCardName(), getGameTable().getCharacterCards().get(characterCardIndex).getCost(), playerID, getGameTable().getGeneralMoneyReserve(), getPlayerByIndex(playerID).getMoney(), getGameTable().getCharacterCard(characterCardIndex).getDenyCards(), getGameTable().getCharacterCard(characterCardIndex).getStudentsHashMap()));
                        }
                        else
                            notifyObserver(obs -> obs.onKO(playerID, "There's no deny cards left on GRANDMA_HERBS, you can't activate this card"));
                    }
                    else
                        notifyObserver(obs -> obs.onKO(playerID, "You don't have enough money to activate this card, please select another one"));
                }
                else
                    notifyObserver(obs -> obs.onKO(playerID, "Character card " + characterCardIndex + " doesn't exist, please select another one"));
            }
            else
                notifyObserver(obs -> obs.onKO(playerID, "You've already played a character card this turn, you can't play another one"));
            notifyTurn();
        }
    }

    /**
     * Permits to activate an atomic effect called in the controller for a certain number of times.
     * @param playerID the id of the player who plays the character card.
     * @param characterCardIndex the index of the character card played.
     * @param value1 it represents different values based on the played card.
     *               MONK          -> isle index where to move the student.
     *               HERALD        -> isle where calculate the influence now.
     *               When not used -> set to 0.
     * @param value2 the color of the student the player wants to remove from the staring StudentsManager.
     */
    public void activateAtomicEffect(int playerID, int characterCardIndex, int value1, int value2){
        if (gamePhase == GamePhases.ACTION_PHASE && getPlayerByIndex(playerID).getAlreadyPlayedACardThisTurn() && currentActivePlayer == players.get(playerID).getOrder()){
            CharacterCardsName cardName = getGameTable().getCharacterCards().get(characterCardIndex).getCharacterCardName();
            switch (cardName) {
                case THIEF, FUNGIST -> {
                    if (value1 >= 0 && value1 <= 4) {
                        effectInGameFactory.getEffect(getGameTable().getCharacterCard(characterCardIndex), this, getPlayerByIndex(playerID), value1, value2);
                        notifyEffectUpdate(characterCardIndex, playerID, value1, value2);
                    }
                    else
                        notifyObserver(obs -> obs.onKO(playerID, "You've selected a color that doesn't exist, please select another one"));
                    notifyTurn();
                }
                case SPOILED_PRINCESS -> {
                    if (value1 >= 0 && value1 <= 4) {
                        if (getGameTable().getCharacterCards().get(characterCardIndex).getStudentsByColor(RealmColors.getColor(value1)) > 0) {
                            effectInGameFactory.getEffect(getGameTable().getCharacterCard(characterCardIndex), this, getPlayerByIndex(playerID), value1, value2);
                            notifyEffectUpdate(characterCardIndex, playerID, value1, value2);
                        }
                        else
                            notifyObserver(obs -> obs.onKO(playerID, "There aren't enough " + RealmColors.getColor(value1).toString() + " students on the card, please select another color"));
                    }
                    else
                        notifyObserver(obs -> obs.onKO(playerID, "You've selected a color that doesn't exist, please select another one"));
                    notifyTurn();
                }
                case HERALD -> {
                    if (value1 >= 0 && value1 < gameTable.getIsleManager().getIsles().size()) {
                        effectInGameFactory.getEffect(getGameTable().getCharacterCard(characterCardIndex), this, getPlayerByIndex(playerID), value1, value2);
                        notifyEffectUpdate(characterCardIndex, playerID, value1, value2);
                    }
                    else
                        notifyObserver(obs -> obs.onKO(playerID, "You've selected an isle that doesn't exist, please select another one"));
                    notifyTurn();
                }
                case GRANDMA_HERBS -> {
                    if (value1 >= 0 && value1 < gameTable.getIsleManager().getIsles().size()) {
                        if (gameTable.getIsleManager().getIsles().get(value1).getDenyCards() == 0) {
                            effectInGameFactory.getEffect(getGameTable().getCharacterCard(characterCardIndex), this, getPlayerByIndex(playerID), value1, value2);
                            notifyEffectUpdate(characterCardIndex, playerID, value1, value2);
                        }
                        else
                            notifyObserver(obs -> obs.onKO(playerID, "There's already a deny card on this isle, please select another one"));
                    }
                    else
                        notifyObserver(obs -> obs.onKO(playerID, "You've selected an isle that doesn't exist, please select another one"));
                    notifyTurn();
                }
                case JESTER -> {
                    if ((value1 >= 0 && value1 <= 4) && (value2 >= 0 && value2 <= 4)) {
                        if (getGameTable().getCharacterCards().get(characterCardIndex).getStudentsByColor(RealmColors.getColor(value1)) > 0) {
                            if (players.get(playerID).getDashboard().getEntrance().getStudentsByColor(RealmColors.getColor(value2)) > 0) {
                                if (players.get(playerID).getDashboard().getDiningRoom().getStudentsByColor(RealmColors.getColor(value2)) <= 10) {
                                    effectInGameFactory.getEffect(getGameTable().getCharacterCard(characterCardIndex), this, getPlayerByIndex(playerID), value1, value2);
                                    notifyEffectUpdate(characterCardIndex, playerID, value1, value2);
                                }
                                else
                                    notifyObserver(obs -> obs.onKO(playerID, "Your " + RealmColors.getColor(value2).toString() + " Dining Room is full! You can't add " + RealmColors.getColor(value2) + " students, please select another color"));
                            }
                            else
                                notifyObserver(obs -> obs.onKO(playerID, "You don't have enough " + RealmColors.getColor(value2).toString() + " students, please select another color"));
                        }
                        else
                            notifyObserver(obs -> obs.onKO(playerID, "There aren't enough " + RealmColors.getColor(value1).toString() + " students on the card, please select another color"));
                    }
                    else
                        notifyObserver(obs -> obs.onKO(playerID, "You've selected a color that doesn't exist, please try again"));
                    notifyTurn();
                }
                case MINSTREL -> {
                    if ((value1 >= 0 && value1 <= 4) && (value2 >= 0 && value2 <= 4)) {
                        if (players.get(playerID).getDashboard().getDiningRoom().getStudentsByColor(RealmColors.getColor(value1)) > 0) {
                            if (players.get(playerID).getDashboard().getEntrance().getStudentsByColor(RealmColors.getColor(value2)) > 0) {
                                if (players.get(playerID).getDashboard().getDiningRoom().getStudentsByColor(RealmColors.getColor(value2)) <= 10) {
                                    effectInGameFactory.getEffect(getGameTable().getCharacterCard(characterCardIndex), this, getPlayerByIndex(playerID), value1, value2);
                                    notifyEffectUpdate(characterCardIndex, playerID, value1, value2);
                                }
                                else
                                    notifyObserver(obs -> obs.onKO(playerID, "Your " + RealmColors.getColor(value2).toString() + " Dining Room is full! You can't add " + RealmColors.getColor(value2) + " students, please select another color"));
                            }
                            else
                                notifyObserver(obs -> obs.onKO(playerID, "You don't have enough " + RealmColors.getColor(value2).toString() + " students, please select another color"));
                        }
                        else
                            notifyObserver(obs -> obs.onKO(playerID, "There aren't enough " + RealmColors.getColor(value1).toString() + " students in your dining room, please select another color"));
                    }
                    else
                        notifyObserver(obs -> obs.onKO(playerID, "You've selected a color that doesn't exist, please try again"));
                    notifyTurn();
                }
                case MONK -> {
                    if ((value1 >= 0 && value1 <= 4) && (value2 >= 0 && value2 < gameTable.getIsleManager().getIsles().size())) {
                        if (getGameTable().getCharacterCards().get(characterCardIndex).getStudentsByColor(RealmColors.getColor(value1)) > 0) {
                            effectInGameFactory.getEffect(getGameTable().getCharacterCard(characterCardIndex), this, getPlayerByIndex(playerID), value1, value2);
                            notifyEffectUpdate(characterCardIndex, playerID, value1, value2);
                        }
                        else
                            notifyObserver(obs -> obs.onKO(playerID, "There aren't enough " + RealmColors.getColor(value1).toString() + " students on the card, please select another color"));
                    }
                    else
                        notifyObserver(obs -> obs.onKO(playerID, "You've selected a color or an isle that doesn't exist, please try again"));
                    notifyTurn();
                }
                default -> {
                    effectInGameFactory.getEffect(getGameTable().getCharacterCard(characterCardIndex), this, getPlayerByIndex(playerID), value1, value2);
                    notifyEffectUpdate(characterCardIndex, playerID, value1, value2);
                    notifyTurn();
                }
            }
        }
    }

    /**
     * Initialize the parameters needed by the view consequently to the activation of the atomic effect of a character card.
     * @param characterCardIndex index of the character card played.
     * @param playerID ID of the player that played the character card.
     * @param value1 value that assumes a different meaning based on the character card played (color, isle index or -1 when not required).
     * @param value2 value that assumes a different meaning based on the character card played (color or -1 when not required).
     */
    public void notifyEffectUpdate(int characterCardIndex, int playerID, int value1, int value2) {
        CharacterCardsName cardName = getGameTable().getCharacterCards().get(characterCardIndex).getCharacterCardName();
        switch (cardName) {
            case MONK                       -> notifyObserver(obs-> obs.onEffectActivation(characterCardIndex, getGameTable().getCharacterCard(characterCardIndex).getCost(), getGameTable().getCharacterCard(characterCardIndex).getDenyCards(), getGameTable().getCharacterCard(characterCardIndex).getStudentsHashMap(), value2, getGameTable().getIsleManager().getIsle(value2).getStudentsHashMap()));
            case FARMER                     -> {
                ArrayList<HashMap<RealmColors, Integer>> professors = new ArrayList<>();
                for (Player p : players)
                    professors.add(p.getDashboard().getDiningRoom().getProfessors());
                notifyObserver(obs -> obs.onEffectActivation(professors));
            }
            case HERALD -> notifyMNMovement(true);
            case MAGICAL_LETTER_CARRIER     -> notifyObserver(obs->obs.onEffectActivation(playerID, players.get(playerID).getDiscardPile().getTurnOrder(), players.get(playerID).getDiscardPile().getMnMovement()));
            case GRANDMA_HERBS              -> notifyObserver(obs->obs.onEffectActivation(characterCardIndex, getGameTable().getCharacterCard(characterCardIndex).getCost(), getGameTable().getCharacterCard(characterCardIndex).getDenyCards(), getGameTable().getCharacterCard(characterCardIndex).getStudentsHashMap(), value1, getGameTable().getIsleManager().getIsle(value1).getDenyCards()));
            case CENTAUR, KNIGHT, FUNGIST   -> notifyObserver(ModelObserver::onEffectActivation);
            case JESTER                     -> notifyObserver(obs->obs.onEffectActivation(characterCardIndex, getGameTable().getCharacterCard(characterCardIndex).getCost(), getGameTable().getCharacterCard(characterCardIndex).getDenyCards(), gameTable.getCharacterCard(characterCardIndex).getStudentsHashMap(), playerID, players.get(playerID).getDashboard().getEntrance().getStudentsHashMap()));
            case MINSTREL, THIEF            -> {
                ArrayList<HashMap<RealmColors, Integer>> studentsInEntrances = new ArrayList<>();
                ArrayList<HashMap<RealmColors, Integer>> studentsInDinings = new ArrayList<>();
                for (Player p : players) {
                    studentsInEntrances.add(p.getDashboard().getEntrance().getStudentsHashMap());
                    studentsInDinings.add(p.getDashboard().getDiningRoom().getStudentsHashMap());
                }
                notifyObserver(obs -> obs.onEffectActivation(studentsInEntrances, studentsInDinings));
            }
            case SPOILED_PRINCESS           -> {
                ArrayList<HashMap<RealmColors, Integer>> studentsInDinings = new ArrayList<>();
                for (Player p : players)
                    studentsInDinings.add(p.getDashboard().getDiningRoom().getStudentsHashMap());
                notifyObserver(obs -> obs.onEffectActivation(characterCardIndex, getGameTable().getCharacterCard(characterCardIndex).getCost(), getGameTable().getCharacterCard(characterCardIndex).getDenyCards(), gameTable.getCharacterCard(characterCardIndex).getStudentsHashMap(), studentsInDinings));
            }
        }
    }

    /**
     * Saves a color in the class GAME for then FUNGIST character card when played.
     * @param indexColorFungist the index of the color that has to be saved.
     */
    public void setColorForFungist(int indexColorFungist) { this.colorForFungist = RealmColors.getColor(indexColorFungist); }

    /**
     * Getter method that permits to know who is the player in the list of players that has a certain index.
     * @param playerIndex the index of the player that is in the list.
     * @return the player associated with that index.
     */
    public Player getPlayerByIndex(int playerIndex) { return players.get(playerIndex); }

    /**
     * Getter method that permits to pick the current game table.
     * @return the current game table.
     */
    public GameTable getGameTable() { return gameTable; }

    /**
     * Used for testing purpose only.
     * @param gameP game phase.
     */
    public void setGamePhase(GamePhases gameP){ gamePhase = gameP; }

    /**
     * Getter method for the game phase.
     * @return the actual game phase.
     */
    public GamePhases getGamePhase() { return gamePhase; }

    /**
     * Getter method that tells if the game mode is expert or not.
     * @return true if expert, else false.
     */
    public boolean isExpertMode() { return gameMode == GameMode.EXPERT; }

    /**
     * Getter method that returns the list of players who are playing the game.
     * @return the list of players who are playing the game.
     */
    public ArrayList<Player> getPlayers() { return players; }

    /**
     * Getter method for the index of the first player to play this turn.
     * @return the index of the first player.
     */
    public int getFirstPlayerIndex() { return firstPlayerIndex; }

    /**
     * Getter method for the player counter.
     * @return the count of the players till the invocation.
     */
    public int getPlayerCounter() { return playerCounter; }

    /**
     * Getter method for the number of players of the game.
     * @return the number of players.
     */
    public int getNumberOfPlayers() {return numberOfPlayers;}

    /**
     * It returns the number of players that have been added to the game.
     * @return the number of players that have been added to the game.
     */
    public int getActualNumberOfPlayers() { return actualNumberOfPlayers; }

    /**
     * Getter method for the current active player.
     * @return the current active player.
     */
    public CurrentOrder getCurrentActivePlayer() { return currentActivePlayer; }

    /**
     * Setter method for the current active player.
     * @param currentActivePlayer the player that will become the current active player.
     */
    public void setCurrentActivePlayer(CurrentOrder currentActivePlayer) { this.currentActivePlayer = currentActivePlayer; }

    /**
     * Getter method for the planning phase.
     * @return in which phase of the planning phase we are.
     */
    public PlanningPhases getPlanningPhase() { return planningPhase; }

    /**
     * Setter method for the planning phase.
     * @param planningPhase indicates in which phase of the planning phase we are.
     */
    public void setPlanningPhase(PlanningPhases planningPhase) { this.planningPhase = planningPhase; }

    /**
     * Getter method for the action phase.
     * @return in which phase of the action phase we are.
     */
    public ActionPhases getActionPhase() { return actionPhase; }

    /**
     * Getter method for the last phase of the action phase before entering in the CHARACTER_CARD_PHASE.
     * @return in which phase of the action phase wwe were
     */
    public ActionPhases getLastActionPhase() { return lastActionPhase; }

    /**
     * Getter method for the atomic effect counter.
     * @return the number of times am atomic effect have been activated.
     */
    public int getAtomicEffectCounter() { return atomicEffectCounter; }

    /**
     * Increases the number of times an atomic effect is activated.
     */
    public void increaseAtomicEffectCounter() { atomicEffectCounter++; }

    /**
     * Setter method for the action phase.
     * @param actionPhase indicates in which phase of the action phase we are.
     */
    public void setActionPhase(ActionPhases actionPhase) {
        atomicEffectCounter = 0;
        this.actionPhase = actionPhase;
    }

}
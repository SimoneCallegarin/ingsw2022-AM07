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
import it.polimi.ingsw.Observer.ModelSubject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

public class Game extends ModelSubject {
    /**
     * The game mode of the game, it is decided by the first player that joins the game
     */
    private GameMode gameMode;
    /**
     * The number of players that are going to play the game, it is decided by the first player that joins the game
     */
    private int numberOfPlayers;
    /**
     * The number of players that already joined the game
     */
    private int actualNumberOfPlayers = 0;
    /**
     * The game table where the game is going to be played, it contains many different objects that compose the game itself
     */
    private GameTable gameTable;
    /**
     * The list of players that are playing the game
     */
    private final ArrayList<Player> players;
    /**
     * Random index used to say who will be the first player to play in the first turn
     */
    private int firstPlayerIndex;
    /**
     * The number of students that is possible to move in a turn for each player
     */
    private int maxMovableStudents;
    /**
     * Counter used to check how many players already played their turn in a game phase
     */
    private int playerCounter = 0;
    /**
     * Counter of the students moved
     */
    private int studentsCounter = 0;
    /**
     * Boolean value that indicates if it is the last round of a game because a player already played
     * all his assistant cards or if the students in the bag were not enough to end the round for everyone.
     */
    private boolean lastRound = false;
    /**
     * When true it means the game ended, else false
     */
    private boolean endGame = false;
    /**
     * When true it means the game ended in a draw, else false
     */
    private boolean drawEndGame = false;
    /**
     * Indicates the player that won the game
     */
    private Player winner;
    /**
     * Phases in which it's divided the game
     */
    private GamePhases gamePhase;
    /**
     * It divides the planning phase in 2 different sub phases, and it indicates that the player turn is in the planning phase
     */
    private PlanningPhases planningPhase;
    /**
     * It divides the planning phase in 3 different sub phases, and it indicates that the player turn is in the action phase
     */
    private ActionPhases actionPhase;

    private ActionPhases lastActionPhase;
    /**
     * It indicates who is the current active player that is playing his turn
     */
    private CurrentOrder currentActivePlayer;
    /**
     * It's the factory that permits to build the effect of each playable character card
     */
    private final EffectInGameFactory effectInGameFactory;
    /**
     * Saves the color chosen by the player when activating the FUNGIST character card
     */
    private RealmColors colorForFungist;
    /**
     * Saves the number of students of the chosen color removed from the isle
     * when checking the influence the turn the FUNGIST has been played
     */
    private int studentsRemovedByFungist=0;

    private int atomicEffectCounter = 0;

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

    /**
     * First method that has to be called to properly start the game
     * adding the first player following his desire about which game mode and with how many players he wants to play
     * @param nickName selected by the player
     * @param gameMode selected by the player (expert or base)
     * @param numberOfPlayers selected by the player (from 2 to 4)
     */
    public void addFirstPlayer(String nickName, boolean gameMode, int numberOfPlayers){

        setNumberOfPlayers(numberOfPlayers);

        if(gameMode){
            this.gameMode=GameMode.EXPERT;
        } else {
            this.gameMode=GameMode.BASE;
        }

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
     * first action to do when a player that is not the first one asks to join a match
     * (after having checked that the number of players is respected)
     * @param nickname the nickname of the player, must be checked if other players already got the same
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
            entrances.add(p.getDashboard().getEntrance().getStudents());
            nicknames.add(p.getNickname());
            numTowers.add(p.getDashboard().getTowerStorage().getNumberOfTowers());
            squads.add(p.getSquad());
        }
        int whereMNId=gameTable.getIsleManager().getIsleWithMotherNatureIndex();
        int money=players.get(0).getMoney();

        // GAME TABLE:
        ArrayList<HashMap<RealmColors,Integer>> emptyClouds=new ArrayList<>();
        for(Cloud c:gameTable.getClouds()){ emptyClouds.add(c.getStudents()); }
        int generalReserve=gameTable.getGeneralMoneyReserve();

        ArrayList<HashMap<RealmColors,Integer>> studentsOnIsle=new ArrayList<>();
        for(Isle i:gameTable.getIsleManager().getIsles()){ studentsOnIsle.add(i.getStudents()); }

        ArrayList<String> characterNames=new ArrayList<>();
        ArrayList<Integer> characterCost=new ArrayList<>();
        ArrayList<Integer> denyCards=new ArrayList<>();
        ArrayList<HashMap<RealmColors,Integer>> studentsOnCard=new ArrayList<>();
        ArrayList<String> characterCardsDescription = new ArrayList<>();
        for(CharacterCard card : gameTable.getCharacterCards()){
            characterNames.add(card.getCharacterCardName().toString());
            characterCost.add(card.getCost());
            denyCards.add(card.getDenyCards());
            studentsOnCard.add(card.getStudents());
            characterCardsDescription.add(card.getCharacterCardDescription());
        }

        notifyObserver(obs->obs.onGameCreation(numberOfPlayers,nicknames,gameMode,whereMNId,entrances,emptyClouds,studentsOnIsle,studentsOnCard,numTowers,money,generalReserve,towerColors,characterNames,characterCost,denyCards,characterCardsDescription,squads));
    }

    /**
     * it is responsible for changing the discard pile of a certain player
     * @param idPlayer is an integer that represents the index of the players ArrayList which corresponds to the player who played the assistant card
     * @param turnOrderPlayed is the turn order of the AssistantCard played
     */
    public void playAssistantCard(int idPlayer, int turnOrderPlayed) {
        boolean alreadyPlayed = false;
        boolean onlyChoice = true;
        AssistantCard assistantCardPlayed = players.get(idPlayer).getAssistantCardByTurnOrder(turnOrderPlayed);
        if (gamePhase == GamePhases.PLANNING_PHASE && planningPhase == PlanningPhases.ASSISTANT_CARD_PHASE && currentActivePlayer == players.get(idPlayer).getOrder()) {
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
                    for (AssistantCard ac : players.get(idPlayer).getMageDeck()) {
                        for (Player p : players) {
                            if (p.getDiscardPile() != null) {
                                if (p.getDiscardPile().equals(ac)) {
                                    alreadyPlayed = true;
                                    break;
                                }
                            }
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
                    players.get(idPlayer).playAssistantCard(assistantCardPlayed);
                    players.get(idPlayer).setCardOrder(playerCounter + 1);

                notifyAssistantsCard(idPlayer);

                    if (players.get(idPlayer).isMageDeckEmpty())
                        lastRound = true;
                    playerCounter++;
                    nextPlayer();
                } else {
                    notifyObserver(obs -> obs.onKO(idPlayer, "You cannot play this card, please select another one"));
                    notifyTurn();
                }
            } else {
                notifyObserver(obs -> obs.onKO(idPlayer, "You don't have this card, please select another one"));
                notifyTurn();
            }
        }
    }

    /**
     * Initialize the deck of available assistant cards for the player and the assistant card in the discard pile in order to pass it to the view.
     * @param idPlayer the id of the player it will be passed the deck.
     */
    private void notifyAssistantsCard(int idPlayer) {
        ArrayList<Integer> turnOrders =new ArrayList<>();
        ArrayList<Integer> movementsMN =new ArrayList<>();
        for(AssistantCard ac : getPlayerByIndex(idPlayer).getMageDeck()){
            turnOrders.add(ac.getTurnOrder());
            movementsMN.add(ac.getMnMovement());
        }
        notifyObserver(obs->obs.onAssistantCard(idPlayer,getPlayerByIndex(idPlayer).getDiscardPile().getTurnOrder(),getPlayerByIndex(idPlayer).getDiscardPile().getMnMovement(), turnOrders, movementsMN));
    }

    /**
     * it takes a student from the dashboard of a certain player and puts it on a specified island
     * @param idPlayer is an integer that represents the index of the players ArrayList which corresponds to the player who moved the student
     * @param idIsle is the index of the chosen island
     * @param colorIndex is the index of the color of the student that has been moved
     */
    public void moveStudentInIsle(int idPlayer, int idIsle, int colorIndex) {
        RealmColors color = RealmColors.getColor(colorIndex);
        if (gamePhase == GamePhases.ACTION_PHASE && actionPhase == ActionPhases.MOVE_STUDENTS && currentActivePlayer == players.get(idPlayer).getOrder()) {
            if (colorIndex >= 0 && colorIndex <= 4) {
                if (players.get(idPlayer).getDashboard().getEntrance().getStudentsByColor(color) > 0) {
                    if (idIsle >= 0 && idIsle < gameTable.getIsleManager().getIsles().size()) {
                        players.get(idPlayer).getDashboard().getEntrance().removeStudent(color);
                        gameTable.getIsleManager().getIsle(idIsle).addStudent(color);
                        studentsCounter++;

                        if (studentsCounter == maxMovableStudents) {
                            actionPhase = ActionPhases.MOVE_MOTHER_NATURE;
                            studentsCounter = 0;
                        }

                        notifyObserver(obs -> obs.onStudentMoving_toIsle(idPlayer, players.get(idPlayer).getDashboard().getEntrance().getStudents(), idIsle, gameTable.getIsleManager().getIsle(idIsle).getStudents()));
                    } else
                        notifyObserver(obs -> obs.onKO(idPlayer, "Isle " + idIsle + " doesn't exist, please select another one"));
                } else
                    notifyObserver(obs -> obs.onKO(idPlayer, "You don't have enough " + color.toString() + " students, please select another color"));
            } else
                notifyObserver(obs -> obs.onKO(idPlayer, "This color doesn't exist, please select another one"));
        }

        notifyTurn();
    }

    /**
     * it takes a student from the dashboard of a certain player and puts it in the dining room
     * @param idPlayer is an integer that represents the index of the players ArrayList which corresponds to the player who moved the student
     * @param colorIndex is the index of the color of the student that has been moved
     */
    public void moveStudentInDiningRoom(int idPlayer, int colorIndex) {
        RealmColors color = RealmColors.getColor(colorIndex);
        int indexFarmer=0;
        if (gamePhase == GamePhases.ACTION_PHASE && actionPhase == ActionPhases.MOVE_STUDENTS && currentActivePlayer == players.get(idPlayer).getOrder()) {
            if (colorIndex >= 0 && colorIndex <= 4) {
                if (players.get(idPlayer).getDashboard().getEntrance().getStudentsByColor(color) > 0) {
                    if (players.get(idPlayer).getDashboard().getDiningRoom().getStudentsByColor(color) < 10) {
                        players.get(idPlayer).getDashboard().getEntrance().removeStudent(color);
                        players.get(idPlayer).getDashboard().getDiningRoom().addStudent(color);
                        // if FARMER character card is played by the player this turn
                        // then each time is checked if the player has an equal number of students of another
                        // player that owe the professor of that color
                        if (gameMode == GameMode.EXPERT && getPlayerByIndex(idPlayer).getCharacterCardPlayed() == CharacterCardsName.FARMER) {
                            while (gameTable.getCharacterCard(indexFarmer).getCharacterCardName() == CharacterCardsName.FARMER)
                                indexFarmer++;
                            activateAtomicEffect(idPlayer, indexFarmer, 0, 0);
                        }
                        // checking if the student is added in third, sixth or ninth position of the dining room
                        if (gameMode == GameMode.EXPERT && players.get(idPlayer).getDashboard().getDiningRoom().getStudentsByColor(color) % 3 == 0) {
                            players.get(idPlayer).gainMoney();
                            gameTable.studentInMoneyPosition();
                            notifyObserver(obs -> obs.onMoneyUpdate(idPlayer, players.get(idPlayer).getMoney(), gameTable.getGeneralMoneyReserve()));
                        }
                        checkUpdateProfessor(idPlayer, color);
                        studentsCounter++;

                        if (studentsCounter == maxMovableStudents) {
                            actionPhase = ActionPhases.MOVE_MOTHER_NATURE;
                            studentsCounter = 0;
                        }

                        notifyObserver(obs -> obs.onStudentMoving_toDining(idPlayer, players.get(idPlayer).getDashboard().getEntrance().getStudents(), players.get(idPlayer).getDashboard().getDiningRoom().getStudents()));
                    } else
                        notifyObserver(obs -> obs.onKO(idPlayer, "Your " + color.toString() + " Dining Room is full! You can't add " + color + " students, please select another color"));
                } else
                    notifyObserver(obs -> obs.onKO(idPlayer, "You don't have enough " + color.toString() + " students, please select another color"));
            } else
                notifyObserver(obs -> obs.onKO(idPlayer, "This color doesn't exist, please select another one"));
        }

        notifyTurn();
    }

    /**
     * it takes a student from the dashboard of a certain player and puts it on a certain isle
     * @param idPlayer is an integer that represents the index of the players ArrayList which corresponds to the player who moved the student
     * @param idIsle is the index of the isle which we want to move the student on
     */
    public void moveMotherNature(int idPlayer, int idIsle) {
        if (gamePhase == GamePhases.ACTION_PHASE && actionPhase == ActionPhases.MOVE_MOTHER_NATURE && currentActivePlayer == players.get(idPlayer).getOrder()) {
            if (idIsle >= 0 && idIsle < gameTable.getIsleManager().getIsles().size()) {
                if (gameTable.getIsleManager().isMNMovementAcceptable(idIsle, players.get(idPlayer).getDiscardPile().getMnMovement())) {
                    gameTable.getIsleManager().getIsle(gameTable.getIsleManager().getIsleWithMotherNatureIndex()).setMotherNature(false);
                    gameTable.getIsleManager().getIsle(idIsle).setMotherNature(true);
                    gameTable.getIsleManager().setIsleWithMotherNatureIndex(idIsle);
                    // if the FUNGIST card is played, then we remove the students of the chosen color from the selected isle
                    // then we proceed to calculate the influence on the isle
                    if (gameMode == GameMode.EXPERT && getPlayerByIndex(idPlayer).getAlreadyPlayedACardThisTurn() && getPlayerByIndex(idPlayer).getCharacterCardPlayed() == CharacterCardsName.FUNGIST)
                        while (gameTable.getIsleManager().getIsle(idIsle).getStudentsByColor(colorForFungist) != 0) {
                            gameTable.getIsleManager().getIsle(idIsle).removeStudent(colorForFungist);
                            studentsRemovedByFungist += 1;
                        }
                    if (gameTable.getIsleManager().getIsle(idIsle).getDenyCards() == 0)
                        checkUpdateInfluence(idIsle);
                    else {
                        gameTable.getIsleManager().getIsle(idIsle).removeDenyCard();
                        int grandmaIndex = 0;
                        for (int i=0; i<3; i++)
                            if(gameTable.getCharacterCard(i).getCharacterCardName() == CharacterCardsName.GRANDMA_HERBS){
                                grandmaIndex = i;
                                break;
                            }
                        gameTable.getCharacterCard(grandmaIndex).addDenyCard();
                        notifyObserver(obs -> obs.onDenyCard(idPlayer, idIsle, false));
                        notifyEffectUpdate(grandmaIndex,idPlayer,idIsle,-1);
                    }
                    // if the FUNGIST card is played, then we add the students of the chosen color
                    // that were removed from the selected isle
                    if (gameMode == GameMode.EXPERT && getPlayerByIndex(idPlayer).getAlreadyPlayedACardThisTurn() && getPlayerByIndex(idPlayer).getCharacterCardPlayed() == CharacterCardsName.FUNGIST)
                        while (gameTable.getIsleManager().getIsle(idIsle).getStudentsByColor(colorForFungist) != studentsRemovedByFungist)
                            gameTable.getIsleManager().getIsle(idIsle).addStudent(colorForFungist);
                    studentsRemovedByFungist = 0;
                    checkEndGame();
                    if (!lastRound) {
                        actionPhase = ActionPhases.CHOOSE_CLOUD;
                        notifyMNMovement();
                        notifyTurn();
                    } else {
                        playerCounter++;
                        actionPhase = ActionPhases.MOVE_STUDENTS;
                        notifyMNMovement();
                        nextPlayer();
                    }
                } else {
                    notifyObserver(obs -> obs.onKO(idPlayer, "You can't go that far! Please select a suitable isle"));
                    notifyTurn();
                }
            } else {
                notifyObserver(obs -> obs.onKO(idPlayer, "Isle " + idIsle + " doesn't exist, please select another one"));
                notifyTurn();
            }
        }
    }

    /**
     * Initialize the parameters needed by the view consequently to the movement of mother nature.
     */
    private void notifyMNMovement() {
        ArrayList<HashMap<RealmColors,Integer>> students=new ArrayList<>();
        ArrayList<Integer> numIsles=new ArrayList<>();
        ArrayList<TowerColors> towerColors=new ArrayList<>();
        ArrayList<Boolean> denyCards=new ArrayList<>();
        ArrayList<Integer> numTowers = new ArrayList<>();
        int whereMnId=0;
        for(Isle isle:gameTable.getIsleManager().getIsles()){
            students.add(isle.getStudents());
            numIsles.add(isle.getNumOfIsles());
            towerColors.add(isle.getTowersColor());
            if(isle.getDenyCards()==0){
                denyCards.add(false);
            }else{
                denyCards.add(true);
            }
            if(isle.getMotherNature()){
                whereMnId=isle.getIdIsle();
            }
        }
        for (Player p : players)
            numTowers.add(p.getDashboard().getTowerStorage().getNumberOfTowers());
        int finalWhereMnId = whereMnId;
        notifyObserver(obs->obs.onMNMovement(gameTable.getIsleManager().getIsles().size(),students,towerColors, finalWhereMnId,denyCards,numIsles, numTowers));
    }

    /**
     * It picks the students present on a certain cloud and puts them in the entrance of the player
     * @param idPlayer is an integer that represents the index of the players ArrayList which corresponds to the player who chose the cloud
     * @param idCloud is the index of the chosen cloud
     */
    public void pickStudentsFromCloud(int idPlayer, int idCloud) {
        if (gamePhase == GamePhases.ACTION_PHASE && actionPhase == ActionPhases.CHOOSE_CLOUD && currentActivePlayer == players.get(idPlayer).getOrder()) {
            if (idCloud >= 0 && idCloud < numberOfPlayers) {
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
                    notifyObserver(obs -> obs.onCloudUpdate(idPlayer, players.get(idPlayer).getDashboard().getEntrance().getStudents(), idCloud));
                    nextPlayer();
                } else {
                    notifyObserver(obs -> obs.onKO(idPlayer, "Cloud " + idCloud + " is empty! Please select another one"));
                    notifyTurn();
                }
            } else {
                notifyObserver(obs -> obs.onKO(idPlayer, "Cloud " + idCloud + " doesn't exist, please select another one"));
                notifyTurn();
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
            return winner.getNickname();
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
                ArrayList<HashMap<RealmColors,Integer>> cloudsList=new ArrayList<>();
                for (int i = 0; i < numberOfPlayers; i++) {
                    Cloud cloud = gameTable.getCloud(i);
                    for (int j = 0; j < maxMovableStudents; j++) {
                        RealmColors color = gameTable.getBag().draw();
                        cloud.addStudent(color);
                    }
                    cloudsList.add(cloud.getStudents());
                }

                notifyObserver(obs->obs.onFillCloud(cloudsList));
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

            for (Player p : players) {
                if (p.getOrder() == CurrentOrder.FIRST_PLAYER)
                    firstPlayerIndex = p.getDashboard().getIdDashboard();
            }
            currentActivePlayer = CurrentOrder.FIRST_PLAYER;
            playerCounter = 0;
        }

        notifyTurn();

    }

    /**
     * Initialize the parameters that handle the turn order and notifies the view.
     */
    public void notifyTurn() {
        int currentPlayerIndex=0;
        for(Player p:players){
            if(p.getOrder().equals(currentActivePlayer)){
                currentPlayerIndex=players.indexOf(p);
                break;
            }
        }
        int finalCurrentPlayerIndex = currentPlayerIndex;
        notifyObserver(obs->obs.onGamePhases(finalCurrentPlayerIndex,gamePhase,actionPhase,-1));
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
     * it is invoked when a student has been added to the dining room of a certain player and updates the professors owned by players, if it is necessary
     * @param idPlayer is an integer that represents the index of the players ArrayList which corresponds to the player whose dining room has been updated
     * @param color is the color of the student that has been moved to the dining room, therefore it is the color of the professor we need to check
     */
    public void checkUpdateProfessor(int idPlayer, RealmColors color) {
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
                if (players.get(idPlayer).getDashboard().getDiningRoom().getStudentsByColor(color) > players.get(playerWhoHasProfessorIndex).getDashboard().getDiningRoom().getStudentsByColor(color) || (players.get(idPlayer).getDashboard().getDiningRoom().getStudentsByColor(color) >= players.get(playerWhoHasProfessorIndex).getDashboard().getDiningRoom().getStudentsByColor(color) && getPlayerByIndex(idPlayer).getAlreadyPlayedACardThisTurn() && getPlayerByIndex(idPlayer).getCharacterCardPlayed().equals(CharacterCardsName.FARMER))) {
                    players.get(playerWhoHasProfessorIndex).getDashboard().getDiningRoom().removeProfessor(color);
                    players.get(idPlayer).getDashboard().getDiningRoom().addProfessor(color);
                }
            }
            else {
                gameTable.removeProfessor(color);
                players.get(idPlayer).getDashboard().getDiningRoom().addProfessor(color);
            }
            //observer parameters initialization
            ArrayList<HashMap<RealmColors,Integer>> professors=new ArrayList<>();
            for(Player p:players){
                professors.add(p.getDashboard().getDiningRoom().getProfessors());
            }
            notifyObserver(obs->obs.onProfessorUpdate(professors));
        }

    }

    /**
     * it is invoked when mother nature ends on acceptable isle and updates all tower references, if it is necessary (it also calls a method to verify if isles union has to occur)
     * @param idIsle is the index of the isle which has mother nature on it
     */
    public void checkUpdateInfluence(int idIsle) {
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
     * It checks if the game has ended and updates the proper end game variables.
     */
    public void checkEndGame() {
        for (Player p : players) {
            if (p.getDashboard().getTowerStorage().getNumberOfTowers() == 0 && p.getDashboard().getTowerStorage().getTowerColor() != TowerColors.NOCOLOR) {
                endGame = true;
                winner = p;
                int currentPlayerIndex=0;
                for(Player player:players){
                    if(player.getOrder().equals(currentActivePlayer)){
                        currentPlayerIndex=players.indexOf(player);
                    }
                }
                int finalCurrentPlayerIndex = currentPlayerIndex;

                notifyObserver(obs->obs.onGamePhases(finalCurrentPlayerIndex, gamePhase ,actionPhase ,players.indexOf(winner)));
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

    /**
     * this method permits to play a character card during the action phase
     * it set money and other booleans but doesn't activate the proper effect of the card
     * @param idPlayer the id of the player who plays a certain character card during his action phase
     * @param characterCardIndex the index of the character card that the player wants to play
     */
    public void playCharacterCard(int idPlayer, int characterCardIndex) {
        if (gamePhase == GamePhases.ACTION_PHASE && currentActivePlayer == players.get(idPlayer).getOrder()){
            if (!players.get(idPlayer).getAlreadyPlayedACardThisTurn()) {
                if (characterCardIndex >= 0 && characterCardIndex <= 2) {
                    if (players.get(idPlayer).getMoney() >= gameTable.getCharacterCard(characterCardIndex).getCost()) {
                        getGameTable().characterCardPlayed(characterCardIndex);
                        getPlayerByIndex(idPlayer).playCharacterCard(getGameTable().getCharacterCard(characterCardIndex));
                        lastActionPhase = actionPhase;
                        actionPhase = ActionPhases.CHARACTER_CARD_PHASE;
                        notifyObserver(obs -> obs.onCharacterCard(characterCardIndex, getGameTable().getCharacterCards().get(characterCardIndex).getCharacterCardName(), getGameTable().getCharacterCards().get(characterCardIndex).getCost(), idPlayer, getGameTable().getGeneralMoneyReserve(), getPlayerByIndex(idPlayer).getMoney(), getGameTable().getCharacterCard(characterCardIndex).getDenyCards(), getGameTable().getCharacterCard(characterCardIndex).getStudents()));
                    }
                    else
                        notifyObserver(obs -> obs.onKO(idPlayer, "You don't have enough money to activate this card, please select another one"));
                }
                else
                    notifyObserver(obs -> obs.onKO(idPlayer, "Character card " + characterCardIndex + " doesn't exist, please select another one"));
            }
            else
                notifyObserver(obs -> obs.onKO(idPlayer, "You've already played a character card this turn, you can't play another one"));
            notifyTurn();
        }
    }

    /**
     * this method permits to activate an atomic effect called in the controller for a certain number of times
     * it is part of the concrete effect of a character card
     * @param idPlayer the id of the player who plays the character card
     * @param characterCardIndex the index of the character card played
     * @param value1 the color of the student the player wants to remove from the staring StudentsManager
     * @param value2 it represents different values based on the played card
     *              MONK          -> isle index where to move the student
     *              HERALD        -> isle where calculate the influence now
     *         When not used -> set to 0
     */
    public void activateAtomicEffect(int idPlayer, int characterCardIndex, int value1, int value2){
        if (gamePhase == GamePhases.ACTION_PHASE && getPlayerByIndex(idPlayer).getAlreadyPlayedACardThisTurn() && currentActivePlayer == players.get(idPlayer).getOrder()){
            CharacterCardsName cardName = getGameTable().getCharacterCards().get(characterCardIndex).getCharacterCardName();
            switch (cardName) {
                case SPOILED_PRINCESS, THIEF, FUNGIST -> {
                    if (value1 >= 0 && value1 <= 4) {
                        effectInGameFactory.getEffect(getGameTable().getCharacterCard(characterCardIndex), this, getPlayerByIndex(idPlayer), value1, value2);
                        notifyEffectUpdate(characterCardIndex, idPlayer, value1, value2);
                    }
                    else
                        notifyObserver(obs -> obs.onKO(idPlayer, "You've selected a color that doesn't exist, please select another one"));
                    notifyTurn();
                }
                case GRANDMA_HERBS, HERALD -> {
                    if (value1 >= 0 && value1 < gameTable.getIsleManager().getIsles().size()) {
                        effectInGameFactory.getEffect(getGameTable().getCharacterCard(characterCardIndex), this, getPlayerByIndex(idPlayer), value1, value2);
                        notifyEffectUpdate(characterCardIndex, idPlayer, value1, value2);
                    }
                    else
                        notifyObserver(obs -> obs.onKO(idPlayer, "You've selected an isle that doesn't exist, please select another one"));
                    notifyTurn();
                }
                case JESTER, MINSTREL -> {
                    if ((value1 >= 0 && value1 <= 4) && (value2 >= 0 && value2 <= 4)) {
                        effectInGameFactory.getEffect(getGameTable().getCharacterCard(characterCardIndex), this, getPlayerByIndex(idPlayer), value1, value2);
                        notifyEffectUpdate(characterCardIndex, idPlayer, value1, value2);
                    }
                    else
                        notifyObserver(obs -> obs.onKO(idPlayer, "You've selected a color that doesn't exist, please try again"));
                    notifyTurn();
                }
                case MONK -> {
                    if ((value1 >= 0 && value1 <= 4) && (value2 >= 0 && value2 < gameTable.getIsleManager().getIsles().size())) {
                        effectInGameFactory.getEffect(getGameTable().getCharacterCard(characterCardIndex), this, getPlayerByIndex(idPlayer), value1, value2);
                        notifyEffectUpdate(characterCardIndex, idPlayer, value1, value2);
                    }
                    else
                        notifyObserver(obs -> obs.onKO(idPlayer, "You've selected a color or an isle that doesn't exist, please try again"));
                    notifyTurn();
                }
                default -> {
                    effectInGameFactory.getEffect(getGameTable().getCharacterCard(characterCardIndex), this, getPlayerByIndex(idPlayer), value1, value2);
                    notifyEffectUpdate(characterCardIndex, idPlayer, value1, value2);
                    notifyTurn();
                }
            }
        }
    }

    private void notifyEffectUpdate(int characterCardIndex, int idPlayer, int value1, int value2) {
        CharacterCardsName cardName = getGameTable().getCharacterCards().get(characterCardIndex).getCharacterCardName();
        switch (cardName) {
            case MONK -> notifyObserver(obs-> obs.onEffectActivation(characterCardIndex, getGameTable().getCharacterCard(characterCardIndex).getCost(), getGameTable().getCharacterCard(characterCardIndex).getDenyCards(), getGameTable().getCharacterCard(characterCardIndex).getStudents(), value2, getGameTable().getIsleManager().getIsle(value2).getStudents()));
            case FARMER -> {
                ArrayList<HashMap<RealmColors, Integer>> professors = new ArrayList<>();
                for (Player p : players) {
                    professors.add(p.getDashboard().getDiningRoom().getProfessors());
                }
                notifyObserver(obs -> obs.onEffectActivation(professors));
            }
            case HERALD -> {
                ArrayList<HashMap<RealmColors,Integer>> students=new ArrayList<>();
                ArrayList<Integer> numIsles=new ArrayList<>();
                ArrayList<TowerColors> towerColors=new ArrayList<>();
                ArrayList<Boolean> denyCards=new ArrayList<>();
                ArrayList<Integer> numTowers = new ArrayList<>();
                int whereMnId=0;
                for(Isle isle:gameTable.getIsleManager().getIsles()){
                    students.add(isle.getStudents());
                    numIsles.add(isle.getNumOfIsles());
                    towerColors.add(isle.getTowersColor());
                    if(isle.getDenyCards()==0){
                        denyCards.add(false);
                    }else{
                        denyCards.add(true);
                    }
                    if(isle.getMotherNature()){
                        whereMnId=isle.getIdIsle();
                    }
                }
                for (Player p : players)
                    numTowers.add(p.getDashboard().getTowerStorage().getNumberOfTowers());
                int finalWhereMnId = whereMnId;
                notifyObserver(obs->obs.onEffectActivation(gameTable.getIsleManager().getIsles().size(),students,towerColors, finalWhereMnId, denyCards, numIsles, numTowers));
            }
            case MAGICAL_LETTER_CARRIER -> notifyObserver(obs->obs.onEffectActivation(idPlayer, players.get(idPlayer).getDiscardPile().getTurnOrder(), players.get(idPlayer).getDiscardPile().getMnMovement()));
            case GRANDMA_HERBS -> notifyObserver(obs->obs.onEffectActivation(characterCardIndex, getGameTable().getCharacterCard(characterCardIndex).getCost(), getGameTable().getCharacterCard(characterCardIndex).getDenyCards(), value1, getGameTable().getIsleManager().getIsle(value1).getDenyCards()));
            case CENTAUR, KNIGHT, FUNGIST -> notifyObserver(ModelObserver::onEffectActivation);
            case JESTER -> notifyObserver(obs->obs.onEffectActivation(characterCardIndex, getGameTable().getCharacterCard(characterCardIndex).getCost(), getGameTable().getCharacterCard(characterCardIndex).getDenyCards(), gameTable.getCharacterCard(characterCardIndex).getStudents(), idPlayer, players.get(idPlayer).getDashboard().getEntrance().getStudents()));
            case MINSTREL, THIEF -> {
                ArrayList<HashMap<RealmColors, Integer>> studentsInEntrances = new ArrayList<>();
                ArrayList<HashMap<RealmColors, Integer>> studentsInDinings = new ArrayList<>();
                for (Player p : players) {
                    studentsInEntrances.add(p.getDashboard().getEntrance().getStudents());
                    studentsInDinings.add(p.getDashboard().getDiningRoom().getStudents());
                }
                notifyObserver(obs -> obs.onEffectActivation(studentsInEntrances, studentsInDinings));
            }
            case SPOILED_PRINCESS -> {
                ArrayList<HashMap<RealmColors, Integer>> studentsInDinings = new ArrayList<>();
                for (Player p : players)
                    studentsInDinings.add(p.getDashboard().getDiningRoom().getStudents());
                notifyObserver(obs -> obs.onEffectActivation(characterCardIndex, getGameTable().getCharacterCard(characterCardIndex).getCost(), getGameTable().getCharacterCard(characterCardIndex).getDenyCards(), gameTable.getCharacterCard(characterCardIndex).getStudents(), studentsInDinings));
            }
        }
    }

    /**
     * this method saves a color in the class GAME for then FUNGIST character card when played
     * @param indexColorFungist the index of the color that has to be saved
     */
    public void setColorForFungist(int indexColorFungist) { this.colorForFungist = RealmColors.getColor(indexColorFungist); }

    /**
     * getter method that permits to know whats the  in the list of players that has a certain index
     * @param playerIndex the index of the player that is in the list
     * @return the player associated with that index
     */
    public Player getPlayerByIndex(int playerIndex) { return players.get(playerIndex); }

    /**
     * getter method that permits to pick the current game table
     * @return the current game table
     */
    public GameTable getGameTable() { return gameTable; }

    /**
     * used for testing purpose only
     * @param gameP game phase
     */
    public void setGamePhase(GamePhases gameP){ gamePhase = gameP; }

    /**
     * Getter method for the game phase
     * @return the actual game phase
     */
    public GamePhases getGamePhase() { return gamePhase; }

    /**
     * Getter method that tells if the game mode is expert or not.
     * @return true if expert, else false
     */
    public boolean isGameMode() {
        return gameMode == GameMode.EXPERT;
    }

    /**
     * Getter method that returns the list of players who are playing the game
     * @return the list of players who are playing the game
     */
    public ArrayList<Player> getPlayers() { return players; }

    /**
     * Getter method for the index of the first player to play this turn
     * @return the index of the first player
     */
    public int getFirstPlayerIndex() { return firstPlayerIndex; }

    /**
     * Getter method for the player counter
     * @return the count of the players till the invocation
     */
    public int getPlayerCounter() { return playerCounter; }

    /**
     * Getter method for the number of players of the game
     * @return the number of players
     */
    public int getNumberOfPlayers() {return numberOfPlayers;}

    /**
     * It returns the number of players that have been added to the game
     * @return the number of players that have been added to the game
     */
    public int getActualNumberOfPlayers() { return actualNumberOfPlayers; }

    /**
     * Getter method for the current active player
     * @return the current active player
     */
    public CurrentOrder getCurrentActivePlayer() { return currentActivePlayer; }

    /**
     * Setter method for the current active player
     * @param currentActivePlayer the player that will become the current active player
     */
    public void setCurrentActivePlayer(CurrentOrder currentActivePlayer) { this.currentActivePlayer = currentActivePlayer; }

    /**
     * Getter method for the planning phase
     * @return in which phase of the planning phase we are
     */
    public PlanningPhases getPlanningPhase() { return planningPhase; }

    /**
     * Setter method for the planning phase
     * @param planningPhase indicates in which phase of the planning phase we are
     */
    public void setPlanningPhase(PlanningPhases planningPhase) { this.planningPhase = planningPhase; }

    /**
     * Getter method for the action phase
     * @return in which phase of the action phase we are
     */
    public ActionPhases getActionPhase() { return actionPhase; }

    public ActionPhases getLastActionPhase() { return lastActionPhase; }

    public int getAtomicEffectCounter() {return atomicEffectCounter;}

    public void increaseAtomicEffectCounter() {atomicEffectCounter++;}

    /**
     * Setter method for the action phase
     * @param actionPhase indicates in which phase of the action phase we are
     */
    public void setActionPhase(ActionPhases actionPhase) {
        this.actionPhase = actionPhase;
    }

}
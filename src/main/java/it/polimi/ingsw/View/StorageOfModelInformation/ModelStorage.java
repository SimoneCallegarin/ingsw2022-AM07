package it.polimi.ingsw.View.StorageOfModelInformation;

import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Model.Enumeration.TowerColors;
import it.polimi.ingsw.Network.Messages.NetworkMessages.EffectActivation_UpdateMsg;
import it.polimi.ingsw.Network.Messages.NetworkMessages.GameCreation_UpdateMsg;
import it.polimi.ingsw.Network.Messages.NetworkMessages.MNMovement_UpdateMsg;


import java.util.ArrayList;
import java.util.HashMap;

public class ModelStorage {

    /**
     * Number of players that are playing the game.
     */
    private final int numberOfPlayers;
    /**
     * Game mode of the game.
     */
    private final boolean expertMode;
    /**
     * List containing all the data about each player (that is practically their dashboards).
     */
    private final ArrayList<PlayerInformation> dashboards = new ArrayList<>();
    /**
     * Data of the objects that are on the game table.
     */
    private GameTableInformation gameTable;
    /**
     * Contains data about the things that changed in the model.
     */
    private final ModelChanges modelChanges;

    /**
     * ModelStorage constructor.
     * @param numberOfPlayers number of player that are playing the game we want to store the data.
     * @param expertMode game mode of the game we want to store the data.
     */
    public ModelStorage(int numberOfPlayers, boolean expertMode) {
        this.numberOfPlayers = numberOfPlayers;
        this.expertMode = expertMode;
        modelChanges=new ModelChanges();
    }

    /**
     * Sets up the storage for the first time when a game starts.
     * @param message a GameCreation_UpdateMsg that contains all the useful data about the game
     *                (dashboards and game table with all their objects).
     */
    public void setupStorage (GameCreation_UpdateMsg message) {

        // DASHBOARDS:
        ArrayList<PlayerInformation> dashboards = new ArrayList<>();
        for(int i=0; i<message.getNumPlayers(); i++){
            PlayerInformation dashboard = new PlayerInformation(message.getNicknames().get(i),message.getSquads().get(i),message.getEntrances().get(i),message.getNumTowers().get(i),message.getTowerColors().get(i), message.getMoney());
            dashboards.add(i,dashboard);
        }

        // GAME TABLE:
        ArrayList<GameTableInformation.CharacterCard> characterCards = new ArrayList<>();
        ArrayList<GameTableInformation.Isle> isles = new ArrayList<>();
        ArrayList<GameTableInformation.Cloud> clouds = new ArrayList<>();

        if(message.getGameMode())
            for(int i=0; i<3; i++){
                GameTableInformation.CharacterCard characterCard = new GameTableInformation.CharacterCard(message.getCharacterNames().get(i),message.getCharacterCost().get(i),message.getStudentsOnCharacter().get(i),message.getDenyCards().get(i),message.getCharacterCardsDescription().get(i));
                characterCards.add(i,characterCard);
            }
        for(int i=0; i<12; i++){
            GameTableInformation.Isle isle;
            if(i== message.getWhereMNId())
                isle = new GameTableInformation.Isle(message.getIsleStudents().get(i),0, TowerColors.NOCOLOR,0,true);
            else
                isle = new GameTableInformation.Isle(message.getIsleStudents().get(i),0,TowerColors.NOCOLOR,0,false);
            isles.add(i,isle);
        }
        for(int i=0; i<message.getNumPlayers(); i++){
            GameTableInformation.Cloud cloud = new GameTableInformation.Cloud(message.getClouds().get(i));
            clouds.add(i,cloud);
        }

        GameTableInformation gameTable = new GameTableInformation(characterCards,isles,clouds,message.getGeneralReserve());

        for(int i=0;i<numberOfPlayers;i++)
            setDashboard(i,dashboards.get(i));
        setGameTable(gameTable);

    }

    /**
     * Setter method for the dashboard.
     * @param playerID ID of the player that we want to set the dashboard.
     * @param player data of the player.
     */
    public void setDashboard(int playerID, PlayerInformation player) { this.dashboards.add(playerID,player); }

    /**
     * Setter method for the game table.
     * @param gameTable data of the game table.
     */
    public void setGameTable(GameTableInformation gameTable) { this.gameTable = gameTable; }

    // Update dashboard:

    /**
     * Updates the students on the entrance of the player.
     * @param playerID ID of the player that will have his entrance updated.
     * @param students updated students on the entrance of the player.
     */
    public void updateStudentsInEntrance(int playerID, HashMap<RealmColors,Integer> students){
        dashboards.get(playerID).setEntranceStudents(students);
        modelChanges.getToUpdate().add(ToUpdate.ENTRANCE_CHANGED);
        modelChanges.setPlayingID(playerID);
    }

    /**
     * Updates the students on the dining room of the player.
     * @param playerID ID of the player that will have his dining room updated.
     * @param students updated students on the dining room of the player.
     */
    public void updateStudentsInDining(int playerID, HashMap<RealmColors,Integer> students){
        dashboards.get(playerID).setDiningStudents(students);
        modelChanges.getToUpdate().add(ToUpdate.STUDENTDINING_CHANGED);
        modelChanges.setPlayingID(playerID);
    }

    /**
     * Updates the professors of each dashboard.
     * @param professors updated professors for each dashboard.
     */
    public void updateProfessorsInDining(ArrayList<HashMap<RealmColors,Integer>> professors){
        for (int i = 0; i < numberOfPlayers; i++)
            dashboards.get(i).setDiningProfessors(professors.get(i));
        modelChanges.getToUpdate().add(ToUpdate.PROFDINING_CHANGED);
    }

    /**
     * Updates the number of towers in each tower storage.
     * @param numTowers updated number of towers in the tower storage.
     */
    public void updateNumberOfTowers(ArrayList<Integer> numTowers) {
        for (int i = 0; i < numberOfPlayers; i++)
            dashboards.get(i).setNumOfTowers(numTowers.get(i));
        modelChanges.getToUpdate().add(ToUpdate.TOWERSTORAGE_CHANGED);
    }

    /**
     * Updates the money of a player.
     * @param playerID ID of the player that will have his money changed.
     * @param money updated quantity of money.
     */
    public void updateMoney(int playerID, int money){
        dashboards.get(playerID).setMoney(money);
        modelChanges.getToUpdate().add(ToUpdate.COINS_CHANGED);
        modelChanges.setPlayingID(playerID);
    }

    /**
     * Updates the discard pile of a player.
     * @param playerID ID of the player that will have his discard pile changed.
     * @param turnOrder turn order of the updated assistant card on the discard pile.
     * @param mnMovement mother nature movement of the updated assistant card on the discard pile.
     */
    public void updateDiscardPile(int playerID, int turnOrder, int mnMovement){
        dashboards.get(playerID).setDiscardPileTurnOrder(turnOrder);
        dashboards.get(playerID).setDiscardPileMNMovement(mnMovement);
        modelChanges.getToUpdate().add(ToUpdate.DISCARDPILE_CHANGED);
        modelChanges.setPlayingID(playerID);
    }

    /**
     * Updates the deck of assistant cards of a player.
     * @param playerID ID of the player that will have his deck of assistant cards changed.
     * @param assistantCardsTurnOrder turn orders of the updated deck of assistant cards.
     * @param assistantCardsMNMovement mother nature movements of the updated deck of assistant cards.
     */
    public void updateAssistantsCard(int playerID, ArrayList<Integer> assistantCardsTurnOrder, ArrayList<Integer> assistantCardsMNMovement) {
        dashboards.get(playerID).setAssistantCardsTurnOrder(assistantCardsTurnOrder);
        dashboards.get(playerID).setAssistantCardsMNMovement(assistantCardsMNMovement);

    }

    // Update game table:

    /**
     * Updates a character card.
     * @param characterCardIndex index of the character card.
     * @param cost updated cost of the character card.
     * @param characterCardsStudents updated students on the character card.
     * @param denyCards updated quantity of deny cards on the character card.
     */
    public void updateCharacterCard(int characterCardIndex, int cost, HashMap<RealmColors,Integer> characterCardsStudents, int denyCards) {
        GameTableInformation.CharacterCard newCharacterCard = new GameTableInformation.CharacterCard(gameTable.getCharacterCard(characterCardIndex).characterCardName(),cost,characterCardsStudents,denyCards,gameTable.getCharacterCard(characterCardIndex).getDescription());
        gameTable.setCharacterCard(characterCardIndex,newCharacterCard);
        modelChanges.getToUpdate().add(ToUpdate.CHARACTERCARD_CHANGED);
        modelChanges.setCharacterID(characterCardIndex);
    }

    /**
     * Updates the students on a certain isle.
     * @param isleID ID of the isle that has to be updated.
     * @param newStudentsOnIsle updated students on the isle.
     */
    public void updateStudentsOnIsle(int isleID, HashMap<RealmColors,Integer> newStudentsOnIsle) {
        gameTable.setStudentsOnIsle(isleID,newStudentsOnIsle);
        modelChanges.getToUpdate().add(ToUpdate.ISLE_CHANGED);
        modelChanges.setIsleID(isleID);
    }

    /**
     * Updates the quantity of deny cards on a certain isle.
     * @param isleID ID of the isle that has to be updated.
     * @param denyCard updated quantity of deny cards on the isle.
     */
    public void updateDenyOnIsle(int isleID, int denyCard) { gameTable.setDenyOnIsle(isleID,denyCard);
        modelChanges.getToUpdate().add(ToUpdate.ISLE_CHANGED);
        modelChanges.setIsleID(isleID);
    }

    /**
     * Updates all the isles after mother nature has been moved.
     * @param mnm message containing the information about the movement of mother nature and the current state of the isles.
     */
    public void updateIsles(MNMovement_UpdateMsg mnm) {
        ArrayList<GameTableInformation.Isle> newIsles = new ArrayList<>();
        for (int i = 0; i < mnm.getTotalIsles(); i++) {
            boolean isMNPresent = mnm.getWhereMNId() == i;
            int isDenyCardPresent = 0;
            if (mnm.getDenyCards().get(i))
                isDenyCardPresent = 1;
            GameTableInformation.Isle newIsle = new GameTableInformation.Isle(mnm.getStudents().get(i), mnm.getNumberOfIsles().get(i), mnm.getTowerColors().get(i), isDenyCardPresent, isMNPresent);
            newIsles.add(newIsle);
        }
        gameTable.setIsles(newIsles);
        modelChanges.getToUpdate().add(ToUpdate.ISLELAYOUT_CHANGED);
        modelChanges.setIsleID(mnm.getWhereMNId());
    }

    /**
     * Updates all the isles after mother nature has been moved.
     * @param ea message containing the information about the activation of a character card that altered the state of the isles and their current state.
     */
    public void updateIsles(EffectActivation_UpdateMsg ea) {
        ArrayList<GameTableInformation.Isle> newIsles = new ArrayList<>();
        for (int i = 0; i < ea.getTotalIsles(); i++) {
            boolean isMNPresent = ea.getWhereMNId() == i;
            int isDenyCardPresent = 0;
            if (ea.getDenyCards().get(i))
                isDenyCardPresent = 1;
            GameTableInformation.Isle newIsle = new GameTableInformation.Isle(ea.getStudents().get(i), ea.getNumberOfIsles().get(i), ea.getTowerColors().get(i), isDenyCardPresent, isMNPresent);
            newIsles.add(newIsle);
        }
        gameTable.setIsles(newIsles);
        modelChanges.getToUpdate().add(ToUpdate.ISLELAYOUT_CHANGED);
    }

    /**
     * Updates a certain cloud.
     * @param newCloud updated cloud with new students.
     * @param cloudID ID of the updated cloud.
     */
    public void updateCloud(HashMap<RealmColors, Integer> newCloud, int cloudID) {
        GameTableInformation.Cloud updatedCloud = new GameTableInformation.Cloud(newCloud);
        gameTable.setCloud(cloudID,updatedCloud);
        modelChanges.getToUpdate().add(ToUpdate.CLOUDS_CHANGED);
        modelChanges.setCloudID(cloudID);
    }

    /**
     * Updates all the clouds.
     * @param clouds list of the clouds with the updated students.
     */
    public void updateFillClouds(ArrayList<HashMap<RealmColors, Integer>> clouds) {
        for (int i = 0; i < numberOfPlayers; i++) {
            GameTableInformation.Cloud newCloud = new GameTableInformation.Cloud(clouds.get(i));
            gameTable.setCloud(i, newCloud);
        }
        modelChanges.getToUpdate().add(ToUpdate.CLOUDS_CHANGED);
    }

    /**
     * Updates the general money reserve.
     * @param generalMoneyReserveNewValue updated quantity of money on the general money reserve.
     */
    public void updateGeneralMoneyReserve(int generalMoneyReserveNewValue) {
        gameTable.setGeneralMoneyReserve(generalMoneyReserveNewValue);
        modelChanges.getToUpdate().add(ToUpdate.GNRLRESERVE_CHANGED);
    }

    // GETTERS:

    public PlayerInformation getDashboard(int playerID) { return dashboards.get(playerID); }

    public GameTableInformation getGameTable() { return gameTable; }

    public int getNumberOfPlayers() { return numberOfPlayers; }

    public boolean isGameMode() { return expertMode; }

    public int getNumberOfIsles() { return gameTable.getIsles().size(); }

    public ModelChanges getModelChanges() { return modelChanges; }
}

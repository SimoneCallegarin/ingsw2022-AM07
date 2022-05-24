package it.polimi.ingsw.View.StorageOfModelInformation;

import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Model.Enumeration.TowerColors;
import it.polimi.ingsw.Network.Messages.NetworkMessages.GameCreation_UpdateMsg;
import it.polimi.ingsw.Network.Messages.NetworkMessages.MNMovement_UpdateMsg;
import it.polimi.ingsw.View.CLI.CLIDrawer;

import java.util.ArrayList;
import java.util.HashMap;

public class ModelStorage {

    private final int numberOfPlayers;
    private final boolean expertMode;
    private final ArrayList<PlayerInformation> dashboards = new ArrayList<>();
    private GameTableInformation gameTable;

    public ModelStorage(int numberOfPlayers, boolean expertMode) {
        this.numberOfPlayers = numberOfPlayers;
        this.expertMode = expertMode;
    }

    public void setupStorage (GameCreation_UpdateMsg message, CLIDrawer cliDrawer) { // Receive a message containing all the information of the game table.

        // DASHBOARDS:
        ArrayList<PlayerInformation> dashboards = new ArrayList<>();
        for(int i=0; i<message.getNumPlayers(); i++){
            PlayerInformation dashboard = new PlayerInformation(message.getNicknames().get(i),message.getEntrances().get(i),message.getNumTowers().get(i),message.getTowerColors().get(i), message.getMoney());
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
        cliDrawer.setStorage(this);
    }

    public void setDashboard(int playerID, PlayerInformation player) { this.dashboards.add(playerID,player); }

    public void setGameTable(GameTableInformation gameTable) { this.gameTable = gameTable; }

    // Update dashboard:

    public void updateStudentsInEntrance(int playerID, HashMap<RealmColors,Integer> students){ dashboards.get(playerID).setEntranceStudents(students); }

    public void updateStudentsInDining(int playerID, HashMap<RealmColors,Integer> students){ dashboards.get(playerID).setDiningStudents(students); }

    public void updateProfessorsInDining(ArrayList<HashMap<RealmColors,Integer>> professors){
        for (int i = 0; i < numberOfPlayers; i++)
            dashboards.get(i).setDiningProfessors(professors.get(i)); 
    }

    public void updateNumberOfTowers(ArrayList<Integer> numTowers) {
        for (int i = 0; i < numberOfPlayers; i++)
            dashboards.get(i).setNumOfTowers(numTowers.get(i));
    }

    public void updateColorOfTowers(int playerID, TowerColors towersColor){ dashboards.get(playerID).setTowerColor(towersColor); }

    public void updateMoney(int playerID, int money){ dashboards.get(playerID).setMoney(money); }

    public void updateDiscardPile(int playerID, int turnOrder, int mnMovement){
        dashboards.get(playerID).setDiscardPileTurnOrder(turnOrder);
        dashboards.get(playerID).setDiscardPileMNMovement(mnMovement);
    }

    public void updateAssistantsCard(int playerID, ArrayList<Integer> assistantCardsTurnOrder, ArrayList<Integer> assistantCardsMNMovement) {
        dashboards.get(playerID).setAssistantCardsTurnOrder(assistantCardsTurnOrder);
        dashboards.get(playerID).setAssistantCardsMNMovement(assistantCardsMNMovement);
    }

    // Update game table:

    public void updateCharacterCard(int characterCardIndex, int cost, HashMap<RealmColors,Integer> characterCardsStudents, int denyCards) {
        GameTableInformation.CharacterCard newCharacterCard = new GameTableInformation.CharacterCard(gameTable.getCharacterCard(characterCardIndex).characterCardName(),cost,characterCardsStudents,denyCards,gameTable.getCharacterCard(characterCardIndex).getDescription());
        gameTable.setCharacterCard(characterCardIndex,newCharacterCard);
    }

    public void updateStudentsOnIsle(int isleID, HashMap<RealmColors,Integer> newStudentsOnIsle) { gameTable.setStudentsOnIsle(isleID,newStudentsOnIsle); }

    public void updateIsle(GameTableInformation.Isle newIsle, int isleID) { gameTable.setNewIsle(isleID,newIsle); }

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
    }

    public void updateCloud(HashMap<RealmColors, Integer> newCloud, int cloudID) {
        GameTableInformation.Cloud updatedCloud = new GameTableInformation.Cloud(newCloud);
        gameTable.setCloud(cloudID,updatedCloud);
    }

    public void updateClouds(ArrayList<HashMap<RealmColors, Integer>> clouds) {
        for (int i = 0; i < numberOfPlayers; i++) {
            GameTableInformation.Cloud newCloud = new GameTableInformation.Cloud(clouds.get(i));
            gameTable.setCloud(i, newCloud);
        }
    }

    public void updateGeneralMoneyReserve(int generalMoneyReserveNewValue) { gameTable.setGeneralMoneyReserve(generalMoneyReserveNewValue); }

    // GETTERS:

    public int getNumberOfPlayers() { return numberOfPlayers; }

    public boolean isGameMode() { return expertMode; }

    public PlayerInformation getDashboard(int playerID) { return dashboards.get(playerID); }

    public GameTableInformation getGameTable() { return gameTable; }

    public int getNumberOfIsles() { return gameTable.getIsles().size(); }

}

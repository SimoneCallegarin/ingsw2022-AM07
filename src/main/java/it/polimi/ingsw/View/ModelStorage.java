package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Model.Enumeration.TowerColors;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Network.Messages.NetworkMessages.GameCreation_UpdateMsg;

import java.util.ArrayList;
import java.util.HashMap;

public class ModelStorage {

    private final int numberOfPlayers;
    private final boolean expertMode;
    private final ArrayList<PlayerModelView> dashboards = new ArrayList<>();
    private GameTableModelView gameTable;

    public ModelStorage(int numberOfPlayers, boolean expertMode) {
        this.numberOfPlayers = numberOfPlayers;
        this.expertMode = expertMode;
    }
    /*public StorageOfModel(int numberOfPlayers, ArrayList<PlayerModelView> dashboards, GameTableModelView gameTable, boolean gameMode) {
        this.numberOfPlayers = numberOfPlayers;
        this.gameMode = gameMode;
        for(int i=0;i<numberOfPlayers;i++)
            setDashboard(i,dashboards.get(i));
        setGameTable(gameTable);
    }*/

    public void setupStorage (GameCreation_UpdateMsg message, CLIDrawer cliDrawer) { // Receive a message containing all the information of the game table.

        // DASHBOARDS:
        ArrayList<PlayerModelView> dashboards = new ArrayList<>();
        for(int i=0; i<message.getNumPlayers(); i++){
            PlayerModelView dashboard = new PlayerModelView(message.getNicknames().get(i),message.getEntrances().get(i),message.getNumTowers().get(i),message.getTowerColors().get(i), message.getMoney());
            dashboards.add(i,dashboard);
        }

        // GAME TABLE:
        ArrayList<GameTableModelView.CharacterCard> characterCards = new ArrayList<>();
        ArrayList<GameTableModelView.Isle> isles = new ArrayList<>();
        ArrayList<GameTableModelView.Cloud> clouds = new ArrayList<>();

        if(message.getGameMode())
            for(int i=0; i<3; i++){
                GameTableModelView.CharacterCard characterCard = new GameTableModelView.CharacterCard(message.getCharacterNames().get(i),message.getCharacterCost().get(i),message.getStudentsOnCharacter().get(i),message.getDenyCards().get(i));
                characterCards.add(i,characterCard);
            }
        for(int i=0; i<12; i++){
            GameTableModelView.Isle isle;
            if(i== message.getWhereMNId())
                isle = new GameTableModelView.Isle(message.getIsleStudents().get(i),0, TowerColors.NOCOLOR,0,true);
            else
                isle = new GameTableModelView.Isle(message.getIsleStudents().get(i),0,TowerColors.NOCOLOR,0,false);
            isles.add(i,isle);
        }
        for(int i=0; i<message.getNumPlayers(); i++){
            GameTableModelView.Cloud cloud = new GameTableModelView.Cloud(message.getClouds().get(i));
            clouds.add(i,cloud);
        }

        GameTableModelView gameTable = new GameTableModelView(characterCards,isles,clouds,message.getGeneralReserve());

        for(int i=0;i<numberOfPlayers;i++)
            setDashboard(i,dashboards.get(i));
        setGameTable(gameTable);
        cliDrawer.setStorage(this);
    }

    public void setDashboard(int playerID, PlayerModelView player) { this.dashboards.add(playerID,player); }

    public void setGameTable(GameTableModelView gameTable) { this.gameTable = gameTable; }

    // Methods called by the update handler to update:

    // Update dashboard:

    public void updateStudentsInEntrance(int playerID, HashMap<RealmColors,Integer> students){ dashboards.get(playerID).setEntranceStudents(students); }

    public void updateStudentsInDining(int playerID, HashMap<RealmColors,Integer> students){ dashboards.get(playerID).setDiningStudents(students); }

    public void updateProfessorsInDining(int playerID, HashMap<RealmColors,Integer> professors){ dashboards.get(playerID).setDiningProfessors(professors); }

    public void updateNumberOfTowers(int playerID, int numberOfTowers){ dashboards.get(playerID).setNumOfTowers(numberOfTowers); }

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

    public void updateCharacterCard(GameTableModelView.CharacterCard newCharacterCard, int characterCardIndex) { gameTable.setCharacterCard(characterCardIndex,newCharacterCard); }

    public void updateIsle(GameTableModelView.Isle newIsle, int isleID) { gameTable.setIsles(isleID,newIsle); }

    public void updateCloud(GameTableModelView.Cloud newCloud, int cloudID) { gameTable.setClouds(cloudID,newCloud); }

    public void updateGeneralMoneyReserve(int generalMoneyReserveNewValue) { gameTable.setGeneralMoneyReserve(generalMoneyReserveNewValue); }

    // GETTERS:

    public int getNumberOfPlayers() { return numberOfPlayers; }

    public boolean isGameMode() { return expertMode; }

    public PlayerModelView getDashboard(int playerID) { return dashboards.get(playerID); }

    public GameTableModelView getGameTable() { return gameTable; }

    public int getNumberOfIsles() {
        return gameTable.getIsles().size();
    }

}

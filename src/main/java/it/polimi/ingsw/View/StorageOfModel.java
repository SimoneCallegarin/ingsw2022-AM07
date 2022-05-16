package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Model.Enumeration.TowerColors;
import it.polimi.ingsw.Model.Game;

import java.util.ArrayList;
import java.util.HashMap;

public class StorageOfModel {

    private final int numberOfPlayers;
    private final ArrayList<PlayerModelView> dashboards = new ArrayList<>();
    private GameTableModelView gameTable;


    Game game = new Game();
    public void runGame() {
        game.addFirstPlayer("simo_calle",true,4);
        game.addAnotherPlayer("jack");
        game.addAnotherPlayer("filippo");
        game.addAnotherPlayer("TrentAlexanderArnold");
    }

    public StorageOfModel(int numberOfPlayers, PlayerModelView[] dashboards, GameTableModelView gameTable) {
        this.numberOfPlayers = numberOfPlayers;
        for(int i=0;i<numberOfPlayers;i++)
            setDashboard(i,dashboards[i]);
        setGameTable(gameTable);
    }

    public void setDashboard(int playerID, PlayerModelView player) { this.dashboards.set(playerID,player); }

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

}

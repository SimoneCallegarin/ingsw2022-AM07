package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Model.Enumeration.TowerColors;
import it.polimi.ingsw.Model.Game;

import java.util.ArrayList;
import java.util.HashMap;

public class StorageOfModel {

    private int numberOfPlayers;
    private final ArrayList<PlayerModelView> dashboards = new ArrayList<>();
    private GameTableModelView gameTable;


    Game game = new Game();
    PlayerModelView test = new PlayerModelView();
    public void runGame() {
        game.addFirstPlayer("simo_calle",true,4);
        game.addAnotherPlayer("jack");
        game.addAnotherPlayer("filippo");
        game.addAnotherPlayer("TrentAlexanderArnold");
    }

    public StorageOfModel(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
        for(int i=0;i<numberOfPlayers;i++)
            setDashboard(i,test);
    }

    public void setDashboard(int playerID, PlayerModelView player) { this.dashboards.set(playerID,player); }

    public void updateStudentsInEntrance(int playerID, HashMap<RealmColors,Integer> students){ dashboards.get(playerID).setEntranceStudents(students); }

    public void updateStudentsInDining(int playerID, HashMap<RealmColors,Integer> students){ dashboards.get(playerID).setDiningStudents(students); }

    public void updateProfessorsInDining(int playerID, HashMap<RealmColors,Integer> professors){ dashboards.get(playerID).setEntranceStudents(professors); }

    public void updateNumberOfTowers(int playerID, int numberOfTowers){ dashboards.get(playerID).setNumOfTowers(numberOfTowers); }

    public void updateColorOfTowers(int playerID, TowerColors towersColor){ dashboards.get(playerID).setTowerColor(towersColor); }

    public void updateMoney(int playerID, int money){ dashboards.get(playerID).setMoney(money); }

    public void updateDiscardPile(int playerID, int turnOrder, int mnMovement){
        dashboards.get(playerID).setDiscardPileTurnOrder(turnOrder);
        dashboards.get(playerID).setDiscardPileMNMovement(mnMovement);
    }

    public void updateCharacterCard(int characterCardIndex, GameTableModelView.CharacterCard characterCard) { gameTable.setCharacterCard(characterCardIndex,characterCard);}

}

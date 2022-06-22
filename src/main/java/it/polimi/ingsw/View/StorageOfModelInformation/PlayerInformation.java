package it.polimi.ingsw.View.StorageOfModelInformation;

import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Model.Enumeration.Squads;
import it.polimi.ingsw.Model.Enumeration.TowerColors;

import java.util.ArrayList;
import java.util.HashMap;

public class PlayerInformation {

    private final String nickname;
    private final Squads team;
    private HashMap<RealmColors,Integer> entranceStudents;
    private HashMap<RealmColors,Integer> diningStudents;
    private HashMap<RealmColors,Integer> diningProfessors;
    private final HashMap<RealmColors,Integer> emptyHashMap = new HashMap<>();
    private int numOfTowers;
    private TowerColors towerColor;
    private int money;
    private int discardPileTurnOrder;
    private int discardPileMNMovement;
    private ArrayList<Integer> assistantCardsTurnOrder;
    private ArrayList<Integer> assistantCardsMNMovement;

    /**
     * Constructor used in the update manager to build an array of the PlayerModelView of each player
     * at the beginning of the game, containing all the information about each player.
     */
    public PlayerInformation(String nickname, Squads team, HashMap<RealmColors, Integer> entranceStudents, int numOfTowers, TowerColors towerColor, int money) {
        fillWith0HashMap();
        this.nickname = nickname;
        this.team = team;
        this.entranceStudents = entranceStudents;
        this.diningStudents = emptyHashMap;
        this.diningProfessors = emptyHashMap;
        this.numOfTowers = numOfTowers;
        this.towerColor = towerColor;
        this.money = money;
        this.discardPileTurnOrder = 0;
        this.discardPileMNMovement = 0;
        this.assistantCardsTurnOrder = new ArrayList<>();
        for (int i = 0; i < 10; i++)
            assistantCardsTurnOrder.add(i+1);
        this.assistantCardsMNMovement = new ArrayList<>();
        for (int i = 0; i < 10; i++)
            assistantCardsMNMovement.add(i/2+1);
    }

    private void fillWith0HashMap() {
        for (RealmColors color : RealmColors.values())
            emptyHashMap.put(color,0);
    }

    // GETTERS:

    public String getNickname() { return nickname; }

    public int getTeam() {
        if(team==Squads.SQUAD1)
            return 1;
        if(team==Squads.SQUAD2)
            return 2;
        else
            return 0;
    }

    public int getNumberOfEntranceStudents(){
        int total=0;
        for(RealmColors color:RealmColors.values()){
            total=total+getEntranceStudents(color);
        }
        return total;
    }

    public int getEntranceStudents(RealmColors color) { return entranceStudents.get(color); }

    public int getDiningStudents(RealmColors color) { return diningStudents.get(color); }

    public int getDiningProfessors(RealmColors color) { return diningProfessors.get(color); }

    public int getNumOfTowers() { return numOfTowers; }

    public TowerColors getTowerColor() { return towerColor; }

    public int getMoney() { return money; }

    public int getDiscardPileTurnOrder() { return discardPileTurnOrder; }

    public int getDiscardPileMNMovement() { return discardPileMNMovement; }

    public ArrayList<Integer> getAssistantCardsTurnOrder() { return assistantCardsTurnOrder; }

    public ArrayList<Integer> getAssistantCardsMNMovement() { return assistantCardsMNMovement; }

    public int getMaxAssistantCard() { return assistantCardsTurnOrder.get(assistantCardsTurnOrder.size()-1); }


    // SETTERS:

    public void setEntranceStudents(HashMap<RealmColors,Integer> entranceStudents) { this.entranceStudents = entranceStudents; }

    public void setDiningStudents(HashMap<RealmColors,Integer> diningStudents) { this.diningStudents = diningStudents; }

    public void setDiningProfessors(HashMap<RealmColors,Integer> diningProfessors) { this.diningProfessors = diningProfessors; }

    public void setNumOfTowers(int numOfTowers) { this.numOfTowers = numOfTowers; }

    public void setTowerColor(TowerColors towerColor) { this.towerColor = towerColor; }

    public void setMoney(int money) { this.money = money; }

    public void setDiscardPileTurnOrder(int turnOrder) { this.discardPileTurnOrder = turnOrder; }

    public void setDiscardPileMNMovement(int mnMovement) { this.discardPileMNMovement = mnMovement; }

    public void setAssistantCardsTurnOrder(ArrayList<Integer> assistantCardsTurnOrder) { this.assistantCardsTurnOrder = assistantCardsTurnOrder; }

    public void setAssistantCardsMNMovement(ArrayList<Integer> assistantCardsMNMovement) { this.assistantCardsMNMovement = assistantCardsMNMovement; }
}

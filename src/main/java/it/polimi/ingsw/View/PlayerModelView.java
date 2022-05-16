package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Model.Enumeration.TowerColors;

import java.util.ArrayList;
import java.util.HashMap;

public class PlayerModelView {

    private final String nickname;
    private HashMap<RealmColors,Integer> entranceStudents;
    private HashMap<RealmColors,Integer> diningStudents;
    private HashMap<RealmColors,Integer> diningProfessors;
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
    public PlayerModelView(String nickname, HashMap<RealmColors, Integer> entranceStudents, HashMap<RealmColors, Integer> diningStudents, HashMap<RealmColors, Integer> diningProfessors, int numOfTowers, TowerColors towerColor, int money, int discardPileTurnOrder, int discardPileMNMovement, ArrayList<Integer> assistantCardsTurnOrder, ArrayList<Integer> assistantCardsMNMovement) {
        this.nickname = nickname;
        this.entranceStudents = entranceStudents;
        this.diningStudents = diningStudents;
        this.diningProfessors = diningProfessors;
        this.numOfTowers = numOfTowers;
        this.towerColor = towerColor;
        this.money = money;
        this.discardPileTurnOrder = discardPileTurnOrder;
        this.discardPileMNMovement = discardPileMNMovement;
        this.assistantCardsTurnOrder = assistantCardsTurnOrder;
        this.assistantCardsMNMovement = assistantCardsMNMovement;
    }

    // GETTERS:

    public String getNickname() { return nickname; }

    public HashMap<RealmColors, Integer> getEntranceStudents() { return entranceStudents; }

    public HashMap<RealmColors, Integer> getDiningStudents() { return diningStudents; }

    public HashMap<RealmColors, Integer> getDiningProfessors() { return diningProfessors; }

    public int getNumOfTowers() { return numOfTowers; }

    public TowerColors getTowerColor() { return towerColor; }

    public int getMoney() { return money; }

    public int getDiscardPileTurnOrder() { return discardPileTurnOrder; }

    public int getDiscardPileMNMovement() { return discardPileMNMovement; }

    public ArrayList<Integer> getAssistantCardsTurnOrder() { return assistantCardsTurnOrder; }

    public ArrayList<Integer> getAssistantCardsMNMovement() { return assistantCardsMNMovement; }


    // SETTERS:

    public void setEntranceStudents(HashMap<RealmColors, Integer> entranceStudents) { this.entranceStudents = entranceStudents; }

    public void setDiningStudents(HashMap<RealmColors, Integer> diningStudents) { this.diningStudents = diningStudents; }

    public void setDiningProfessors(HashMap<RealmColors, Integer> diningProfessors) { this.diningProfessors = diningProfessors; }

    public void setNumOfTowers(int numOfTowers) { this.numOfTowers = numOfTowers; }

    public void setTowerColor(TowerColors towerColor) { this.towerColor = towerColor; }

    public void setMoney(int money) { this.money = money; }

    public void setDiscardPileTurnOrder(int turnOrder) { this.discardPileTurnOrder = turnOrder; }

    public void setDiscardPileMNMovement(int mnMovement) { this.discardPileMNMovement = mnMovement; }

    public void setAssistantCardsTurnOrder(ArrayList<Integer> assistantCardsTurnOrder) { this.assistantCardsTurnOrder = assistantCardsTurnOrder; }

    public void setAssistantCardsMNMovement(ArrayList<Integer> assistantCardsMNMovement) { this.assistantCardsMNMovement = assistantCardsMNMovement; }
}

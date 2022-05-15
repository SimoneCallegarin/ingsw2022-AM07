package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Model.Enumeration.TowerColors;

import java.util.ArrayList;
import java.util.HashMap;

public class PlayerModelView {

    private String nickname;
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
    public void setNickname(String nickname) { this.nickname = nickname; }

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

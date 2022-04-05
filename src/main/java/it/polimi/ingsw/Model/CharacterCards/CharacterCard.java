package it.polimi.ingsw.Model.CharacterCards;

import it.polimi.ingsw.Model.Enumeration.RealmColors;

import java.util.HashMap;
import java.util.Enumeration;

public class CharacterCard {
    private final int idCard;
    private int cost;
    private Effect effect;
    private boolean used;

    /**
     * This attribute contains the number of students that are on a character card if it has a setupEffect
     */
    private final HashMap<RealmColors,Integer> students;
    /**
     * This attribute contains the number of deny cards that are on a character card if it has a setupEffect
     */
    private int denyCards;


    public CharacterCard(int idCard, int cost) {
        this.idCard = idCard;
        this.cost = cost;
        this.used = false;

        this.students = new HashMap<>();
        this.denyCards = 0;
    }

    public int getIdCard() {
        return idCard;
    }

    public int getCost() {
        return cost;
    }

    public boolean getUsed() {
        return used;
    }

    public Effect getEffect() {
        return effect;
    }

    public void setEffect(Effect effect) {
        this.effect = effect;
    }

    public void isUsed() {
        used = true;
        cost += 1;
    }

    public void addStudent(RealmColors color){
        int temp;
        temp = students.get(color);
        temp++;
        students.put(color, temp);
    }

    public void removeStudent(RealmColors color){
        int temp;
        temp = students.get(color);
        temp--;
        students.put(color, temp);
    }

    public void addDenyCard(){
        denyCards += 1;
    }

    public void removeDenyCard(){
        denyCards -= 1;
    }


}

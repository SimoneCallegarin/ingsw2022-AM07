package it.polimi.ingsw.Model.CharacterCards;

import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Model.Interface.StudentManager;

import java.util.ArrayList;
import java.util.HashMap;

public class CharacterCard implements StudentManager {

    /**
     * This attribute indicates the name of the card
     */
    private final CharacterCardsName characterCardsName;
    /**
     * This attribute indicates the cost of the card
     */
    private int cost;
    /**
     * This attribute refers to the effect of the card
     */
    private ArrayList<AtomicEffect> effects;
    /**
     * This attribute indicates if the character card has been already used previously in the the game
     */
    private boolean used;
    /**
     * This attribute contains the number of students that are on a character card if it has a setupEffect
     */
    private final HashMap<RealmColors,Integer> students;
    /**
     * This attribute contains the number of deny cards that are on a character card if it has a setupEffect
     */
    private int denyCards;


    public CharacterCard(CharacterCardsName characterCardsName, int cost) {
        this.characterCardsName = characterCardsName;
        this.cost = cost;
        this.used = false;

        this.students = new HashMap<>();
        for (RealmColors rc : RealmColors.values())
            students.put(rc, 0);
        this.denyCards = 0;
    }

    /**
     * @return the actual cost of the card
     */
    public int getCost() {
        return cost;
    }

    /**
     * @return the name of the card
     */
    public CharacterCardsName getCharacterCardsName() {
        return characterCardsName;
    }

    /**
     * @return if the card has been already used previously in the game
     */
    public boolean getUsed() {
        return used;
    }

    public void setEffect(ArrayList<AtomicEffect> effects){
        this.effects = effects;
    }

    /**
     * @return the effect of the card
     */
    public ArrayList<AtomicEffect> getEffect() {
        return effects;
    }

    /**
     * when the card is used by the player its cost will increase by 1 and this method will set used to true
     * for the rest of the game in order to prevent other increase of the cost when played the same card another time
     */
    public void isUsed() {
        used = true;
        cost += 1;
    }

    /**
     * a getter method that counts the number of students on the character card
     * @return the number of students on the card
     */
    @Override
    public int getNumberOfStudents() {
        int totalNumberOfStudents = 0;
        for (RealmColors c : RealmColors.values()){
            totalNumberOfStudents = totalNumberOfStudents + students.get(c);
        }
        return totalNumberOfStudents;
    }

    /**
     * a getter method to receive a certain value contained in the students' hashmap
     * @param color is the key of the value we want to get
     * @return the value we want
     */
    @Override
    public int getStudentsByColor(RealmColors color) {
        return students.get(color);
    }

    /**
     * this method updates the students' hashmap incrementing by 1 the value specified by color
     * @param color is the key of the value we want to update in the students' hashmap
     */
    @Override
    public void addStudent(RealmColors color){
        int temp;
        temp = students.get(color);
        temp++;
        students.put(color, temp);
    }

    /**
     * this method updates the students' hashmap decrementing by 1 the value specified by color
     * @param color is the key of the value we want to update in the students' hashmap
     * @return the color of the student that has been removed
     */
    @Override
    public RealmColors removeStudent(RealmColors color){
        int temp;
        temp = students.get(color);
        temp--;
        students.put(color, temp);
        return color;
    }

    /**
     * @return the number of deny cards on the card
     */
    public int getDenyCards(){
        return denyCards;
    }

    /**
     * increase the number of deny cards by 1
     */
    public void addDenyCard(){
        denyCards += 1;
    }

    /**
     * decrease the number of deny cards by 1
     */
    public void removeDenyCard(){
        denyCards -= 1;
    }


}

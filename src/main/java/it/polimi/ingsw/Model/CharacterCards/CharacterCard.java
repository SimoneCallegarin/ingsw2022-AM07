package it.polimi.ingsw.Model.CharacterCards;

import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Model.Interface.DenyCardManager;
import it.polimi.ingsw.Model.Interface.StudentManager;

import java.util.HashMap;

/**
 * Character card class (it can contain students and deny cards on it).
 */
public class CharacterCard implements StudentManager, DenyCardManager {

    /**
     * This attribute indicates the name of the card.
     */
    private final CharacterCardsName characterCardName;
    /**
     * This attribute indicates the cost of the card.
     */
    private int cost;
    /**
     * This attribute indicates if the character card has been already used previously in the game.
     */
    private boolean used;
    /**
     * This attribute contains the number of students that are on a character card if it has a setupEffect.
     */
    private final HashMap<RealmColors,Integer> students;
    /**
     * This attribute contains the number of deny cards that are on a character card if it has a setupEffect.
     */
    private int denyCards;
    /**
     * The description of the effect of the character card.
     */
    private final String characterCardDescription;

    /**
     * Constructor of a character card
     * @param characterCardName the name of the character card
     */
    public CharacterCard(CharacterCardsName characterCardName) {
        this.characterCardName = characterCardName;
        this.cost = characterCardName.getCharacterCardStartingCost(characterCardName);
        this.used = false;

        this.students = new HashMap<>();
        for (RealmColors rc : RealmColors.values())
            students.put(rc, 0);
        this.denyCards = 0;
        this.characterCardDescription = characterCardName.getCharacterCardDescription(characterCardName);
    }

    /**
     * When the card is used by the player its cost will increase by 1 and this method will set used to true
     * for the rest of the game in order to prevent other increase of the cost when played the same card another time.
     */
    public void setUsed() {
        used = true;
        cost += 1;
    }

    /**
     * Updates the students' hashmap incrementing by 1 the value specified by the color.
     * @param color is the key of the value we want to update in the students' hashmap.
     */
    @Override
    public void addStudent(RealmColors color){
        int temp;
        temp = students.get(color);
        temp++;
        students.put(color, temp);
    }

    /**
     * Updates the students' hashmap decrementing by 1 the value specified by the color.
     * @param color is the key of the value we want to update in the students' hashmap.
     */
    @Override
    public void removeStudent(RealmColors color){
        int temp;
        temp = students.get(color);
        temp--;
        students.put(color, temp);
    }

    /**
     * Increase the number of deny cards by 1.
     */
    @Override
    public void addDenyCard(){ denyCards += 1; }

    /**
     * Decrease the number of deny cards by 1.
     */
    @Override
    public void removeDenyCard(){ denyCards -= 1; }

    /**
     * Getter method for the cost of the character card.
     * @return the actual cost of the card
     */
    public int getCost() { return cost; }

    /**
     * Getter method for the character card name.
     * @return the name of the card.
     */
    public CharacterCardsName getCharacterCardName() { return characterCardName; }

    /**
     * Getter method for the character card description.
     * @return the description of the effect of the card.
     */
    public String getCharacterCardDescription () { return characterCardDescription; }

    /**
     * Getter method to know if the character card has been already played in the game.
     * @return if the card has been already used previously in the game.
     */
    public boolean isUsed() { return used; }

    /**
     * Getter method for the deny cards.
     * @return the number of deny cards on the card.
     */
    @Override
    public int getDenyCards(){ return denyCards; }

    /**
     * Getter method that counts the number of students on the character card.
     * @return the number of students on the character card.
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
     * Getter method for the students.
     * @return the HashMap of the students on the character card.
     */
    @Override
    public HashMap<RealmColors, Integer> getStudentsHashMap() { return students; }

    /**
     * Getter method for a certain value contained in the students' hashmap.
     * @param color is the key of the value we want to get.
     * @return the quantity of students of that color on the character card.
     */
    @Override
    public int getStudentsByColor(RealmColors color) { return students.get(color); }

}

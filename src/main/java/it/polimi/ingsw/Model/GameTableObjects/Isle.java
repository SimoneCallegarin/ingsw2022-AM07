package it.polimi.ingsw.Model.GameTableObjects;

import it.polimi.ingsw.Model.CharacterCards.CharacterCardsName;
import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Model.Enumeration.TowerColors;
import it.polimi.ingsw.Model.Interface.DenyCardManager;
import it.polimi.ingsw.Model.Interface.StudentManager;
import it.polimi.ingsw.Model.Player.Player;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class represents the Isle game object.
 */
public class Isle implements StudentManager, DenyCardManager {

    /**
     * Index of the isle.
     */
    private int isleIndex;
    /**
     * Students on the isle.
     */
    private final HashMap<RealmColors,Integer> students;
    /**
     * Color of the tower on the isle.
     */
    private TowerColors tower;
    /**
     * Indicates if mother nature is present or not on the isle.
     */
    private boolean motherNature;
    /**
     * Indicates if on the isle is present or not a deny card
     */
    private boolean denyCard;
    /**
     * Number of isles that have been unified in order to compose this isle.
     */
    private int numOfIsles;

    /**
     * Isle constructor.
     * @param isleIndex the index of the isle we are creating.
     */
    public Isle(int isleIndex){
        this.isleIndex = isleIndex;
        this.students=new HashMap<>();
        for (RealmColors c: RealmColors.values())
            students.put(c,0);
        this.motherNature=false;
        this.denyCard=false;
        this.numOfIsles=1;
        this.tower=TowerColors.NOCOLOR;
    }

    /**
     * Updates the students' hashmap incrementing by 1 the specified color value.
     * @param color color of the student that has to be added.
     */
    @Override
    public void addStudent(RealmColors color) {
        int temp;
        temp = students.get(color);
        temp++;
        students.put(color,temp);
    }

    /**
     * Updates the students' hashmap decrementing by 1 the specified color value.
     * @param color color of the student that has to be removed.
     */
    @Override
    public void removeStudent(RealmColors color) {
        int temp;
        temp= students.get(color);
        temp--;
        if(temp>=0)
        students.put(color,temp);
    }

    /**
     * Getter method to receive a certain value contained in the students' hashmap.
     * @param color color of the student we want to get.
     * @return the number of students of a certain color.
     */
    @Override
    public int getStudentsByColor(RealmColors color) { return students.get(color); }

    /**
     * Getter method for the number of students on the isle.
     * @return the number of students actually in the isle.
     */
    @Override
    public int getNumberOfStudents() {
        int totalNumberOfStudents = 0;
        for (RealmColors rc : RealmColors.values()){
            totalNumberOfStudents = totalNumberOfStudents + students.get(rc);
        }
        return totalNumberOfStudents;
    }

    /**
     * Adds a deny card on the isle.
     */
    @Override
    public void addDenyCard() { this.denyCard=true; }

    /**
     * Remove a deny card from the isle.
     */
    @Override
    public void removeDenyCard() { this.denyCard=false; }

    /**
     * Gives the number of deny cards on the deny card manager.
     * @return the number of deny cards.
     */
    @Override
    public int getDenyCards() {
        int present;
        if (denyCard) present = 1;
        else present = 0;
        return present;
    }

    /**
     * Calculates the influences of each player on the Isle.
     * @param players players that are playing the game.
     * @return the influences of each player on the Isle.
     */
    public ArrayList<Integer> getInfluences(ArrayList<Player> players) {

        ArrayList<Integer> influences = new ArrayList<>();
        boolean centaurPlayed = false;

        for(Player p : players)
            if(p.getAlreadyPlayedACardThisTurn() && p.getCharacterCardPlayed().equals(CharacterCardsName.CENTAUR)){
                centaurPlayed = true;
                break;
            }

        for(int i=0; i<players.size(); i++) {
            influences.add(getInfluence(players.get(i)));
            if(centaurPlayed && players.get(i).getDashboard().getTowerStorage().getTowerColor().equals(getTowersColor()))
                influences.set(i,influences.get(i) - numOfIsles) ;
        }

        return influences;
    }

    /**
     * Used to calculate the Player influence on the Isle
     * according to the professors on his dashboard,
     * the students and the towers on the Isle.
     * @param player the player we want to calculate the influence of.
     * @return the influence of the player on the isle.
     */
    private int getInfluence(Player player){
        int influence = 0;
        for(RealmColors c: RealmColors.values()){
            if(player.getDashboard().getDiningRoom().getProfessorByColor(c)!=0)
                    influence += students.get(c);
        }
        if(player.getDashboard().getTowerStorage().getTowerColor().equals(getTowersColor()))
            influence += numOfIsles;
        if(player.getAlreadyPlayedACardThisTurn() && player.getCharacterCardPlayed() == CharacterCardsName.KNIGHT)
            influence += 2;
        return influence;
    }

    /**
     * Getter method that gives the index of the current isle.
     * @return the index of the isle.
     */
    public int getIsleIndex() { return isleIndex; }

    /**
     * Getter method that gives the number of current isles.
     * @return the number of current isles.
     */
    public int getNumOfIsles() { return numOfIsles; }

    /**
     * Getter method that permits to know if there's mother nature on the isle.
     * @return true if mother nature is present, else false.
     */
    public boolean getMotherNature() { return motherNature; }

    /**
     * Gives the color of the towers that are on the isle.
     * @return the color of the towers.
     */
    public TowerColors getTowersColor(){ return tower; }

    /**
     * Permits to update the index of the isle when unified (it is used by the isle manager).
     * @param isleIndex the new index of the isle.
     */
    public void setIsleIndex(int isleIndex) { this.isleIndex = isleIndex; }

    /**
     * Permits to update the number of isle of the isle when unified (it is used by the isle manager).
     * @param numOfIsles the new number of isle that now compose the isle.
     */
    public void setNumOfIsles(int numOfIsles) { this.numOfIsles = numOfIsles; }

    /**
     * Permits to update the color of the isle when conquered by another player (it is used by the isle manager).
     */
    public void setTower(TowerColors c){ this.tower=c; }

    /**
     * Permits setting if there's or not mother nature on an isle.
     * @param motherNature true if mother nature is present, false else.
     */
    public void setMotherNature(boolean motherNature) { this.motherNature = motherNature; }

    /**
     * Getter method to return the students.
     * @return the student HashMap.
     */
    public HashMap<RealmColors, Integer> getStudentsHashMap() { return students; }
}
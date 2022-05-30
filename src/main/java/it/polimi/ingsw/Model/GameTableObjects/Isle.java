package it.polimi.ingsw.Model.GameTableObjects;

import it.polimi.ingsw.Model.CharacterCards.CharacterCardsName;
import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Model.Enumeration.TowerColors;
import it.polimi.ingsw.Model.Interface.DenyCardManager;
import it.polimi.ingsw.Model.Interface.StudentManager;
import it.polimi.ingsw.Model.Player.Player;

import java.util.HashMap;

/**
 * this class represents the Isle game object
 */
public class Isle implements StudentManager, DenyCardManager {

    /**
     * this is the id of the isle
     */
    private int idIsle;
    /**
     * this is the students container
     */
    private final HashMap<RealmColors,Integer> students;
    /**
     * this is the color of the tower on the isle
     */
    private TowerColors tower;
    /**
     * this indicates if mother nature is present or not on the isle
     */
    private boolean motherNature;
    /**
     * this indicates if on the isle is present or not a deny card
     */
    private boolean denyCard;       //it may be useful to add a check if there is already a deny card on the isle, also in the view
    /**
     * this is the number of isle that are unified in order to compose this isle
     */
    private int numOfIsles;

    /**
     * Isle constructor
     * @param idIsle the id of the isle we are creating
     */
    public Isle(int idIsle){
        this.idIsle=idIsle;
        this.students=new HashMap<>();
        for (RealmColors c: RealmColors.values()){
            students.put(c,0);
        }
        this.motherNature=false;
        this.denyCard=false;
        this.numOfIsles=1;
        this.tower=TowerColors.NOCOLOR;
    }

    /**
     * this method updates the students' hashmap incrementing by 1 the value specified by color
     * @param color is the key of the value we want to update in the students' hashmap
     */
    @Override
    public void addStudent(RealmColors color) {
        int temp;
        temp = students.get(color);
        temp++;
        students.put(color,temp);
    }

    /**
     * this method updates the students' hashmap decrementing by 1 the value specified by color
     * @param color is the key of the value we want to update in the students' hashmap
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
     * a getter method to receive a certain value contained in the students' hashmap
     * @param color is the key of the value we want to get
     * @return the value we want
     */
    @Override
    public int getStudentsByColor(RealmColors color) { return students.get(color); }

    /**
     * this method when called gives the number of students in the cloud
     * @return the number of students actually in the bag
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
     * this method add a deny card to the isle
     */
    @Override
    public void addDenyCard() {
        this.denyCard=true;
    }

    /**
     * this method remove a deny card to the isle
     */
    @Override
    public void removeDenyCard() {
        this.denyCard=false;
    }

    /**
     * this method gives the number of deny cards on the deny card manager
     * @return the number of deny cards
     */
    @Override
    public int getDenyCards() {
        int present;
        if (denyCard) present = 1;
        else present = 0;
        return present;
    }


    /**
     * getInfluence is the method used to calculate the Player influence on the Isle
     * according to the professors on his dashboard and
     * the students and the professors on the Isle
     * @param player the player we want to calculate the influence of
     * @return the influence of that player in the isle
     */
    public int getInfluence(Player player){
        int influence=0;
        for(RealmColors c: RealmColors.values()){
            if(player.getDashboard().getDiningRoom().getProfessorByColor(c)!=0)
                    influence += students.get(c);
        }
        if(tower.equals(player.getDashboard().getTowerStorage().getTowerColor()) && player.getCharacterCardPlayed()!= CharacterCardsName.CENTAUR){
            influence += numOfIsles;
        }
        if(player.getAlreadyPlayedACardThisTurn() && player.getCharacterCardPlayed()== CharacterCardsName.KNIGHT)
            influence += 2;
        return influence;
    }

    /**
     * getter method that gives the id of the current isle
     * @return the id of the isle
     */
    public int getIdIsle() { return idIsle; }

    /**
     * getter method that gives the number of current isles
     * @return the number of current isles
     */
    public int getNumOfIsles() { return numOfIsles; }

    /**
     * getter method that permits to know if there's mother nature on the isle
     * @return true if mother nature is present, false instead
     */
    public boolean getMotherNature() { return motherNature; }

    /**
     * this method gives the color of the towers that are on the isle
     * @return the color of the towers
     */
    public TowerColors getTowersColor(){ return tower; }

    /**
     * this method permits to update the id of the isle when unified (it is used by the isle manager)
     * @param idIsle the new id of the isle
     */
    public void setIdIsle(int idIsle) { this.idIsle = idIsle; }

    /**
     * this method permits to update the number of isle of the isle when unified (it is used by the isle manager)
     * @param numOfIsles the new number of isle that noe compose the isle
     */
    public void setNumOfIsles(int numOfIsles) { this.numOfIsles = numOfIsles; }

    /**
     * this method permits to update the color of the isle when conquered by another player (it is used by the isle manager)
     */
    public void setTower(TowerColors c){ this.tower=c; }

    /**
     * this method permits setting if there's or not mother nature on an isle
     * @param motherNature true if mother nature is present, false else
     */
    public void setMotherNature(boolean motherNature) { this.motherNature = motherNature; }

    /**
     * getter for returning the students HashMap
     * @return the student HashMap
     */
    public HashMap<RealmColors, Integer> getStudents() { return students; }
}
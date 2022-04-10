package it.polimi.ingsw.Model.GameTableObjects;

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

    private int idIsle;
    private final HashMap<RealmColors,Integer> students;
    private TowerColors tower;
    private boolean motherNature;
    private boolean towerIsPresent;
    private boolean denyCard;
    private int numOfIsles;


    public Isle(int idIsle){
        this.idIsle=idIsle;
        this.students=new HashMap<>();
        for (RealmColors c: RealmColors.values()){
            students.put(c,0);
        }
        this.motherNature=false;
        this.towerIsPresent=false;
        this.denyCard=false;
        this.numOfIsles=1;
        this.tower=TowerColors.NOCOLOR;
    }

    @Override
    public void addStudent(RealmColors color) {
        int temp;
        temp = students.get(color);
        temp++;
        students.put(color,temp);
    }

    @Override
    public void removeStudent(RealmColors color) {
        int temp;
        temp= students.get(color);
        temp--;
        if(temp>=0)
        students.put(color,temp);
    }

    @Override
    public int getStudentsByColor(RealmColors color) {
        return students.get(color);
    }

    @Override
    public void addDenyCard() {
        this.denyCard=true;
    }

    @Override
    public void removeDenyCard() {
        this.denyCard=false;
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
            if(player.getDashboard().getDiningRoom().getProfessorByColor(c)!=0){
                influence += students.get(c);
            }
        }
        if(tower.equals(player.getDashboard().getTowerStorage().getTowerColor())){
            influence+= numOfIsles;
        }
        return influence;
    }

    public int getIdIsle() {
        return idIsle;
    }

    public int getNumOfIsles() {
        return numOfIsles;
    }

    public boolean getMotherNature() {
        return motherNature;
    }

    public boolean getDenyCard() {
        return denyCard;
    }

    public TowerColors getTowersColor(){
        return tower;
    }

    public void setIdIsle(int idIsle) {this.idIsle = idIsle;}

    public void setNumOfIsles(int numOfIsles) {this.numOfIsles = numOfIsles;}

    public void setTower(TowerColors c){this.tower=c;}

    public void setMotherNature(boolean motherNature) {
        this.motherNature = motherNature;
    }

    @Override
    public int getNumberOfStudents() {
        int totalNumberOfStudents = 0;
        for (RealmColors rc : RealmColors.values()){
            totalNumberOfStudents = totalNumberOfStudents + students.get(rc);
        }
        return totalNumberOfStudents;
    }
}

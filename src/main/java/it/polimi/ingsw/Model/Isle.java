package it.polimi.ingsw.Model;

import java.util.HashMap;

/**
 * this class represents the Isle game object
 */
public class Isle implements StudentManager,DenyCardManager {

    private int idIsle;
    public final HashMap<RealmColors,Integer> students;
    TowerColors tower;
    boolean motherNature;
    boolean towerIsPresent;
    boolean denyCard;
    private int numOfIsles;


    public Isle(int idIsle){
        this.idIsle=idIsle;
        this.students=new HashMap<RealmColors,Integer>();
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
            if(player.dashboard.getDiningRoom().getProfessorByColor(c)!=0){
                influence+= (int)this.students.get(c);
            }
        }
        if(tower.equals(player.dashboard.getTowerStorage().getTowerColor())){
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

    public void setIdIsle(int idIsle) {this.idIsle = idIsle;}

    public void setNumOfIsles(int numOfIsles) {this.numOfIsles = numOfIsles;}

    public void setTower(TowerColors c){this.tower=c;}

}

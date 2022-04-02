package it.polimi.ingsw.Model;

import java.util.HashMap;

/**
 * this class represents the Isle game object
 */
public class Isle {

    private int idIsle;
    HashMap students;
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
        this.numOfIsles=0;
    }

    public void updateTower(TowerColors c){
        this.tower=c;
    }

    /**
     * getInfluence is the method used to calculate the Player influence on the Isle according to the professors on hid dashboard and
     * the students and the professors on the Isle
     * @param p
     * @return
     */
    public int getInfluence(Player p){
        int influence=0;
        for(RealmColors c: RealmColors.values()){
            if(p.dashboard.getDiningRoom().getProfessorByColor(c)!=0){
                influence+= (int)this.students.get(c);
            }
        }
        if(tower.equals(p.dashboard.getTowerStorage().getTowerColor())){
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
}

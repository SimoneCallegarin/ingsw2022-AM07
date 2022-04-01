package it.polimi.ingsw.Model;

/**
 * This is the interface has the methods to add and remove a tower of a certain color
 */

public interface TowerManager {

    public void addTower(RealmColors color);
    public void removeTower(RealmColors color);

}

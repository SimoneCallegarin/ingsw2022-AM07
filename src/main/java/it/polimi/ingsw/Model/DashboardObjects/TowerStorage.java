package it.polimi.ingsw.Model.DashboardObjects;

import it.polimi.ingsw.Model.Enumeration.TowerColors;

public class TowerStorage {
    /**
     * The maximum amount of towers in the tower storage
     */
    private final int maxTowers;
    /**
     * The amount of towers in the tower storage
     */
    private int numOfTowers;
    /**
     * The color of the towers in the tower storage
     */
    private final TowerColors towerColor;

    public TowerStorage(int maxNumberOfTowers, TowerColors towerColor) {
        this.maxTowers = maxNumberOfTowers;
        this.numOfTowers = maxNumberOfTowers;
        this.towerColor = towerColor;
    }

    /**
     * this method updates the students' hashmap incrementing by 1 the value specified by color
     */
    public void addTower() {
        if (numOfTowers < maxTowers)
            numOfTowers++;
    }

    /**
     * this method reduces the number of towers of the TowerStorage
     */
    public void removeTower() {
        if (numOfTowers > 0)
            numOfTowers--;
    }

    /**
     * this method gives the actual number of towers in the tower storage
     * @return number of towers
     */
    public int getNumberOfTowers(){
        return numOfTowers;
    }

    /**
     * this method gives the color of the towers in the tower storage of a certain dashboard
     * @return color of the towers
     */
    public TowerColors getTowerColor() {
        return towerColor;
    }
}

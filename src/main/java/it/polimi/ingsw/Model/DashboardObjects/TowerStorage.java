package it.polimi.ingsw.Model.DashboardObjects;

import it.polimi.ingsw.Model.Enumeration.TowerColors;

/**
 * Tower storage of the dashboard of the player.
 */
public class TowerStorage {

    /**
     * Maximum amount of towers in the tower storage.
     */
    private final int maxTowers;
    /**
     * Amount of towers in the tower storage.
     */
    private int numOfTowers;
    /**
     * Color of the towers in the tower storage.
     */
    private final TowerColors towerColor;

    /**
     * Tower storage constructor.
     * @param maxNumberOfTowers Maximum amount of towers in the tower storage.
     * @param towerColor Color of the towers in the tower storage.
     */
    public TowerStorage(int maxNumberOfTowers, TowerColors towerColor) {
        this.maxTowers = maxNumberOfTowers;
        this.numOfTowers = maxNumberOfTowers;
        this.towerColor = towerColor;
    }

    /**
     * Increment by 1 the towers' quantity.
     */
    public void addTower() {
        if (numOfTowers < maxTowers)
            numOfTowers++;
    }

    /**
     * Reduce by 1 the towers' quantity.
     */
    public void removeTower() {
        if (numOfTowers > 0)
            numOfTowers--;
    }

    /**
     * Getter method for the actual number of towers in the tower storage.
     * @return number of towers in the tower storage.
     */
    public int getNumberOfTowers(){ return numOfTowers; }

    /**
     * Getter method for the color of the towers.
     * @return color of the towers in the tower storage
     */
    public TowerColors getTowerColor() { return towerColor; }

}

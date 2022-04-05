package it.polimi.ingsw.Model.DashboardObjects;

import it.polimi.ingsw.Model.Enumeration.TowerColors;
import it.polimi.ingsw.Model.Interface.Place;
import it.polimi.ingsw.Model.Interface.TowerManager;

public class TowerStorage implements Place, TowerManager {
    private final int maxTowers;
    private int numOfTowers;
    private TowerColors towerColor;

    public TowerStorage(int maxNumberOfTowers, TowerColors towerColor) {
        this.maxTowers = maxNumberOfTowers;
        this.numOfTowers = maxNumberOfTowers;
        this.towerColor = towerColor;
    }

    /**
     * this method updates the students' hashmap incrementing by 1 the value specified by color
     */
    @Override
    public void addTower() {
        if (numOfTowers < maxTowers)
            numOfTowers++;
    }

    /**
     * this method reduces the number of towers of the TowerStorage
     */
    @Override
    public void removeTower() {
        if (numOfTowers > 0)
            numOfTowers--;
    }

    @Override
    public int getNumberOfElements(){
        return numOfTowers;
    }

    public TowerColors getTowerColor() {
        return towerColor;
    }

    public void setTowerColor(TowerColors color){
        towerColor = color;
    }
}

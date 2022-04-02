package it.polimi.ingsw.Model;

public class TowerStorage implements Place, TowerManager{
    private int numberOfTowers;
    private final TowerColors towerColor;

    public TowerStorage(int numberOfTowers, TowerColors towerColor) {
        this.numberOfTowers = numberOfTowers;
        this.towerColor = towerColor;
    }

    /**
     * this method updates the students' hashmap incrementing by 1 the value specified by color
     */
    @Override
    public void addTower() {
        numberOfTowers += 1;
    }

    /**
     * this method reduces the number of towers of the TowerStorage
     */
    @Override
    public void removeTower() {
        numberOfTowers -= 1;
    }

    @Override
    public int getNumberOfElements(){
        return numberOfTowers;
    }

    public TowerColors getTowerColor() {
        return towerColor;
    }
}

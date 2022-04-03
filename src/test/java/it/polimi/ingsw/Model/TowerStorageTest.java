package it.polimi.ingsw.Model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TowerStorageTest {

    /**
     * here we are testing a tower storage in a 2 or 4 players game mode
     */
    TowerStorage towerStorageForTest2and4Players = new TowerStorage(8, TowerColors.BLACK);

    /**
     * we are testing if the number of towers is correctly increased and decreased by 1
     */
    @Test
    void addAnsRemoveTower2and4Players() {
        towerStorageForTest2and4Players.removeTower();
        towerStorageForTest2and4Players.removeTower();
        assertEquals(6,towerStorageForTest2and4Players.getNumberOfElements());
        towerStorageForTest2and4Players.addTower();
        assertEquals(7,towerStorageForTest2and4Players.getNumberOfElements());
    }

    /**
     * we are testing if the number of towers in the tower storage is correctly returned
     */
    @Test
    void getNumberOfTowers2and4Players() {
        assertEquals(8,towerStorageForTest2and4Players.getNumberOfElements());
    }




    /**
     * instead here we are testing a tower storage in a 3 players game mode
     */
    TowerStorage towerStorageForTest3Players = new TowerStorage(6, TowerColors.GREY);

    /**
     * we are testing if the number of towers is correctly increased and decreased by 1
     */
    @Test
    void addAndRemoveTower3Players() {
        towerStorageForTest2and4Players.removeTower();
        towerStorageForTest2and4Players.removeTower();
        assertEquals(6,towerStorageForTest2and4Players.getNumberOfElements());
        towerStorageForTest2and4Players.addTower();
        assertEquals(7,towerStorageForTest2and4Players.getNumberOfElements());
    }

    /**
     * we are testing if the number of towers in the tower storage is correctly returned
     */
    @Test
    void getNumberOfTowers3Players() {
        assertEquals(8,towerStorageForTest2and4Players.getNumberOfElements());
    }

}
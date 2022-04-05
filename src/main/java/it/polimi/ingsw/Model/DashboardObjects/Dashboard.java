package it.polimi.ingsw.Model.DashboardObjects;

import it.polimi.ingsw.Model.Enumeration.GameMode;
import it.polimi.ingsw.Model.Enumeration.TowerColors;

public class Dashboard {

    /**
     * this is the identification number of the dashboard
     */
    private final int idDashboard;
    /**
     * this is the entrance of the dashboard
     */
    private final Entrance entrance;
    /**
     * this is the dining room of the dashboard
     */
    private final DiningRoom diningRoom;
    /**
     * this is the tower storage of the dashboard
     */
    private final TowerStorage towerStorage;

    /**
     * Dashboard constructor: here are set the maximum dimension of each depending on the number of players and the game mode
     */
    public Dashboard(int numOfPlayers, int idDashboard, GameMode gm) {

        int maxEntranceStudents;
        int maxEntranceStudentsRemovable;
        int maxStorageTowers;
        TowerColors towerColor = TowerColors.NOCOLOR;

        this.idDashboard = idDashboard;

        if (numOfPlayers == 3) {
            maxEntranceStudents = 9;
            maxEntranceStudentsRemovable = 4;
            maxStorageTowers = 6;
        }
        else {
            maxEntranceStudents = 7;
            maxEntranceStudentsRemovable = 3;
            maxStorageTowers = 8;
        }

        this.entrance = new Entrance(maxEntranceStudents, maxEntranceStudentsRemovable);
        this.diningRoom = new DiningRoom();
        if((idDashboard==2||idDashboard==3)&&numOfPlayers==4)
            maxStorageTowers=0;
        this.towerStorage = new TowerStorage(maxStorageTowers, towerColor);
        //the process of building the tower storage mey be simplified
        towerStorage.setTowerColor(towerStorage.getTowerColor().getColor(idDashboard));
    }

    public int getIdDashboard() {
        return idDashboard;
    }

    public Entrance getEntrance() {
        return entrance;
    }

    public DiningRoom getDiningRoom() {
        return diningRoom;
    }

    public TowerStorage getTowerStorage() {
        return towerStorage;
    }
}

package it.polimi.ingsw.Model.DashboardObjects;

import it.polimi.ingsw.Model.Enumeration.TowerColors;

public class Dashboard {

    /**
     * this is the identification number of the dashboard
     */
    private final int dashboardID;
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
    public Dashboard(int numOfPlayers, int dashboardID) {

        int maxEntranceStudents;
        int maxStorageTowers;

        this.dashboardID = dashboardID;
        TowerColors towerColor = TowerColors.getColor(dashboardID);

        if (numOfPlayers == 3) {
            maxEntranceStudents = 9;
            maxStorageTowers = 6;
        }
        else {
            maxEntranceStudents = 7;
            maxStorageTowers = 8;
        }

        this.entrance = new Entrance(maxEntranceStudents);
        this.diningRoom = new DiningRoom();
        if((dashboardID ==2|| dashboardID ==3)&&numOfPlayers==4) {
            maxStorageTowers = 0;
            towerColor = TowerColors.getColor(3);
        }
        this.towerStorage = new TowerStorage(maxStorageTowers, towerColor);
    }

    /**
     * Getter method
     * @return the dashboard id
     */
    public int getDashboardID() { return dashboardID; }

    /**
     * Getter method
     * @return the entrance of the dashboard
     */
    public Entrance getEntrance() { return entrance; }

    /**
     * Getter method
     * @return the dining room of the dashboard
     */
    public DiningRoom getDiningRoom() { return diningRoom; }

    /**
     * Getter method
     * @return the tower storage of the dashboard
     */
    public TowerStorage getTowerStorage() { return towerStorage; }

}

package it.polimi.ingsw.Model.DashboardObjects;

import it.polimi.ingsw.Model.Enumeration.TowerColors;

/**
 * Dashboard of the player.
 */
public class Dashboard {

    /**
     * Identification number of the dashboard.
     */
    private final int dashboardID;
    /**
     * Entrance of the dashboard.
     */
    private final Entrance entrance;
    /**
     * Dining room of the dashboard.
     */
    private final DiningRoom diningRoom;
    /**
     * Tower storage of the dashboard.
     */
    private final TowerStorage towerStorage;

    /**
     * Dashboard constructor:
     * here are set the maximum values of each variable depending on the number of players and the game mode.
     * @param numOfPlayers number of players that are playing the game.
     * @param dashboardID ID of the dashboard.
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
     * Getter method for the ID of the dashboard.
     * @return the dashboard ID.
     */
    public int getDashboardID() { return dashboardID; }

    /**
     * Getter method for the entrance.
     * @return the entrance of the dashboard.
     */
    public Entrance getEntrance() { return entrance; }

    /**
     * Getter method for the dining room.
     * @return the dining room of the dashboard.
     */
    public DiningRoom getDiningRoom() { return diningRoom; }

    /**
     * Getter method for the tower storage.
     * @return the tower storage of the dashboard.
     */
    public TowerStorage getTowerStorage() { return towerStorage; }

}

package it.polimi.ingsw.Model;

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

            switch (idDashboard) {
                case 0 :
                    towerColor = TowerColors.WHITE;
                    break;
                case 1 :
                    towerColor = TowerColors.BLACK;
                    break;
                case 2 :
                    towerColor = TowerColors.GREY;
                    break;
            }

        }
        else {
            maxEntranceStudents = 7;
            maxEntranceStudentsRemovable = 3;
            maxStorageTowers = 8;

            switch (idDashboard) {
                case 0 :
                    towerColor = TowerColors.WHITE;
                    break;
                case 1 :
                    towerColor = TowerColors.BLACK;
                    break;
                case 2 :
                case 3 :
                    towerColor = TowerColors.NOCOLOR;
                    maxStorageTowers = 0;
                    break;
            }

        }

        this.entrance = new Entrance(maxEntranceStudents, maxEntranceStudentsRemovable);
        this.towerStorage = new TowerStorage(maxStorageTowers, towerColor);

        if (gm == GameMode.EXPERT)
            this.diningRoom = new ExpertDiningRoom();
        else
            this.diningRoom = new DiningRoom();
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

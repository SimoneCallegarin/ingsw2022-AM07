package it.polimi.ingsw.Model;

public class Dashboard {
    private final int idDashboard;
    private final Entrance entrance;
    private final DiningRoom diningRoom;
    private final TowerStorage towerStorage;

    public Dashboard(int numOfPlayers, int idDashboard, GameMode gm) {

        int maxEntranceStudents;
        int maxEntranceStudentsRemovable;
        int maxStorageTowers;
        TowerColors towerColor;

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

        switch (idDashboard) {
            case 1 :
                towerColor = TowerColors.WHITE;
                break;
            case 2 :
                towerColor = TowerColors.BLACK;
                break;
            case 3 :
                towerColor = TowerColors.GREY;
                break;
            default:
                towerColor = TowerColors.NOCOLOR;
        }

        this.entrance = new Entrance(maxEntranceStudents, maxEntranceStudentsRemovable);
        this.towerStorage = new TowerStorage(maxStorageTowers, towerColor);

        if (gm == GameMode.EXPERT)
            this.diningRoom = new ExpertDiningRoom();
        else this.diningRoom = new DiningRoom();
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

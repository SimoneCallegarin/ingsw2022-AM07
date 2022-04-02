package it.polimi.ingsw.Model;

public class Dashboard {
    //Not yet implemented, used only to do proper tests of other classes
    private final int idDashboard;
    private Entrance entrance;
    private DiningRoom diningRoom;
    private final TowerStorage towerStorage;

    public Dashboard(int idDashboard, TowerStorage towerStorage) {
        this.idDashboard = idDashboard;
        this.towerStorage = towerStorage;
    }



}

package it.polimi.ingsw.Model.DashboardObjects;

import it.polimi.ingsw.Model.DashboardObjects.Dashboard;
import it.polimi.ingsw.Model.DashboardObjects.DiningRoom;
import it.polimi.ingsw.Model.DashboardObjects.Entrance;
import it.polimi.ingsw.Model.DashboardObjects.TowerStorage;
import it.polimi.ingsw.Model.Enumeration.GameMode;
import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Model.Enumeration.TowerColors;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DashboardTest {

    /**
     * this is a basic creation test: dashboard constructors correctly instantiates every dashboard place for 2 players in base game mode
     */
    @Test
    public void basicDashboardCreation() {
        Dashboard d = new Dashboard(2, 1, GameMode.BASE);
        assertEquals(1, d.getIdDashboard());
        Entrance e = d.getEntrance();
        assertEquals(0, e.getNumberOfElements());
        e.addStudent(RealmColors.YELLOW);
        e.addStudent(RealmColors.YELLOW);
        e.addStudent(RealmColors.YELLOW);
        e.addStudent(RealmColors.YELLOW);
        e.addStudent(RealmColors.YELLOW);
        e.addStudent(RealmColors.YELLOW);
        e.addStudent(RealmColors.YELLOW);
        assertEquals(7, e.getNumberOfElements());
        e.addStudent(RealmColors.YELLOW);
        assertEquals(7, e.getNumberOfElements());
        DiningRoom dr = d.getDiningRoom();
        assertEquals(0, dr.getNumberOfElements());
        dr.addStudent(RealmColors.YELLOW);
        dr.addStudent(RealmColors.YELLOW);
        dr.addStudent(RealmColors.YELLOW);
        dr.addStudent(RealmColors.YELLOW);
        dr.addStudent(RealmColors.YELLOW);
        dr.addStudent(RealmColors.YELLOW);
        dr.addStudent(RealmColors.YELLOW);
        dr.addStudent(RealmColors.YELLOW);
        dr.addStudent(RealmColors.YELLOW);
        dr.addStudent(RealmColors.YELLOW);
        assertEquals(10, dr.getNumberOfElements());
        dr.addStudent(RealmColors.YELLOW);
        assertEquals(10, dr.getNumberOfElements());
        TowerStorage ts = d.getTowerStorage();
        assertEquals(TowerColors.BLACK, ts.getTowerColor());
        assertEquals(8, ts.getNumberOfElements());
        ts.addTower();
        assertEquals(8, ts.getNumberOfElements());
        ts.removeTower();
        ts.removeTower();
        ts.removeTower();
        ts.removeTower();
        ts.removeTower();
        ts.removeTower();
        ts.removeTower();
        ts.removeTower();
        assertEquals(0, ts.getNumberOfElements());
        ts.removeTower();
        assertEquals(0, ts.getNumberOfElements());
    }


}
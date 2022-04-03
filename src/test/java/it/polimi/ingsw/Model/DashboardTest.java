package it.polimi.ingsw.Model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DashboardTest {

    /**
     * this is a basic creation test: dashboard constructors correctly instantiates every dashboard place for 2 players in base game mode
     */
    @Test
    public void basicDashboardCreation() {
        Dashboard d = new Dashboard(2, 0, GameMode.BASE);
        assertEquals(0, d.getIdDashboard());
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
        assertEquals(TowerColors.WHITE, ts.getTowerColor());
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
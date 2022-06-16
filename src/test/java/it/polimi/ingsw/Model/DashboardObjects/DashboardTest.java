package it.polimi.ingsw.Model.DashboardObjects;

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
        Dashboard d = new Dashboard(2, 1);
        assertEquals(1, d.getDashboardID());
        Entrance e = d.getEntrance();
        assertEquals(0, e.getNumberOfStudents());
        e.addStudent(RealmColors.YELLOW);
        e.addStudent(RealmColors.YELLOW);
        e.addStudent(RealmColors.YELLOW);
        e.addStudent(RealmColors.YELLOW);
        e.addStudent(RealmColors.YELLOW);
        e.addStudent(RealmColors.YELLOW);
        e.addStudent(RealmColors.YELLOW);
        assertEquals(7, e.getNumberOfStudents());
        e.addStudent(RealmColors.YELLOW);
        assertEquals(7, e.getNumberOfStudents());
        DiningRoom dr = d.getDiningRoom();
        assertEquals(0, dr.getNumberOfStudents());
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
        assertEquals(10, dr.getNumberOfStudents());
        dr.addStudent(RealmColors.YELLOW);
        assertEquals(10, dr.getNumberOfStudents());
        TowerStorage ts = d.getTowerStorage();
        assertEquals(TowerColors.BLACK, ts.getTowerColor());
        assertEquals(8, ts.getNumberOfTowers());
        ts.addTower();
        assertEquals(8, ts.getNumberOfTowers());
        ts.removeTower();
        ts.removeTower();
        ts.removeTower();
        ts.removeTower();
        ts.removeTower();
        ts.removeTower();
        ts.removeTower();
        ts.removeTower();
        assertEquals(0, ts.getNumberOfTowers());
        ts.removeTower();
        assertEquals(0, ts.getNumberOfTowers());
    }

}
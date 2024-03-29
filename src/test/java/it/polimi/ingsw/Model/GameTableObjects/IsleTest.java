package it.polimi.ingsw.Model.GameTableObjects;

import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Model.Enumeration.Squads;
import it.polimi.ingsw.Model.Enumeration.TowerColors;
import it.polimi.ingsw.Model.Player.Player;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class IsleTest {

    Isle isleTest=new Isle(0);

    /**
     * testing if the addStudent method correctly adds one student to the hashmap
     */
    @Test
    void addStudent() {
        for(RealmColors color: RealmColors.values()){
        isleTest.addStudent(color);
        assertEquals(isleTest.getStudentsByColor(color),1);
        }
    }

    /**
     * testing if the removeStudent method correctly removes a student from the Isle and if it doesn't subtract
     * one student when there aren't any
     */
    @Test
    void removeStudent() {
        for (RealmColors colors: RealmColors.values()) {
            isleTest.addStudent(colors);
            isleTest.removeStudent(colors);
            assertEquals(isleTest.getStudentsByColor(colors),0);
            isleTest.removeStudent(colors);
            assertEquals(isleTest.getStudentsByColor(colors),0);
        }
    }

    /**
     * testing if this methods return the correct students number
     */
    @Test
    void getStudentsByColor() {

        for(RealmColors colors: RealmColors.values()){
        isleTest.addStudent(colors);
        isleTest.addStudent(colors);
        assertEquals(isleTest.getStudentsByColor(colors),2);}
    }

    /**
     * testing if this method correctly updates the boolean value of the denyCard attribute
     */
    @Test
    void removeDenyCard() {
        isleTest.addDenyCard();
        assertEquals(1,isleTest.getDenyCards());
        isleTest.removeDenyCard();
        assertEquals(0,isleTest.getDenyCards());
    }

    /**
     * testing if getInfluence correctly return the influence value relative to one player if there's only one student,
     * if there's also the tower, and if there's nothing relative to the player
     */
    @Test
    void getInfluences() {

        ArrayList<Player> players = new ArrayList<>();

        Player p1 = new Player("filo", 2, 0, Squads.NO_SQUAD);
        Player p2 = new Player("calle", 2, 1, Squads.NO_SQUAD);

        players.add(p1);
        players.add(p2);

        isleTest.addStudent(RealmColors.RED);
        p1.getDashboard().getDiningRoom().addProfessor(RealmColors.RED);
        assertEquals(isleTest.getInfluences(players).get(0),1);

        isleTest.setTower(p1.getDashboard().getTowerStorage().getTowerColor());
        assertEquals(isleTest.getInfluences(players).get(0),2);

        isleTest.setTower(TowerColors.NOCOLOR);
        isleTest.removeStudent(RealmColors.RED);
        assertEquals(isleTest.getInfluences(players).get(0),0);

    }

    @Test
    void getDenyCards(){ assertEquals(0,isleTest.getDenyCards()); }

}
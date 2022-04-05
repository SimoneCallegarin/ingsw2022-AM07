package it.polimi.ingsw.Model;

import org.junit.jupiter.api.Test;

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
        assertEquals(isleTest.students.get(colors),2);}
    }

    /**
     * testing if this method correctly updates the boolean value of the denyCard attribute
     */
    @Test
    void removeDenyCard() {
        isleTest.addDenyCard();
        isleTest.removeDenyCard();
        assertFalse(isleTest.getDenyCard());
        isleTest.removeDenyCard();
        assertFalse(isleTest.getDenyCard());
    }

    /**
     * testing if getInfluence correctly return the influence value relative to one player if there's only one student,
     * if there's also the tower, and if there's nothing relative to the player
     */
    @Test
    void getInfluence() {
        Player p=new Player("filobuda",Squads.SQUAD1,Mages.MAGE1,new Dashboard(2,0,GameMode.BASE),GameMode.BASE);


        isleTest.addStudent(RealmColors.RED);
        p.dashboard.getDiningRoom().addProfessor(RealmColors.RED);
        assertEquals(isleTest.getInfluence(p),1);

        isleTest.setTower( p.dashboard.getTowerStorage().getTowerColor());
        assertEquals(isleTest.getInfluence(p),2);

        isleTest.setTower(TowerColors.NOCOLOR);
        isleTest.removeStudent(RealmColors.RED);
        assertEquals(isleTest.getInfluence(p),0);

    }
}
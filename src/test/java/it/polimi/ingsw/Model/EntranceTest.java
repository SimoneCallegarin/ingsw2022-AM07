package it.polimi.ingsw.Model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EntranceTest {

    /**
     * we are testing if the method getnumOfElements works properly on normal conditions
     */
    @Test
    public void getNumBasic() {
        Entrance e = new Entrance(2);
        e.addStudent(RealmColors.YELLOW);
        e.addStudent(RealmColors.YELLOW);
        e.addStudent(RealmColors.BLUE);
        e.addStudent(RealmColors.RED);
        e.addStudent(RealmColors.RED);
        e.addStudent(RealmColors.RED);
        e.addStudent(RealmColors.RED);
        assertEquals(7, e.getNumberOfElements());
    }

    /**
     * we are testing if the value in the students' hashmap is correctly updated (+1)
     */
    @Test
    public void addStudentBasic() {
        Entrance e = new Entrance(2);
        e.addStudent(RealmColors.YELLOW);
        assertEquals(1, e.getStudentsByColor(RealmColors.YELLOW));
    }

    /**
     * we are testing if trying to add a student when entrance is full leads to errors
     */
    @Test
    public void addStudentFull() {
        Entrance e = new Entrance(2);
        e.addStudent(RealmColors.YELLOW);
        e.addStudent(RealmColors.YELLOW);
        e.addStudent(RealmColors.BLUE);
        e.addStudent(RealmColors.RED);
        e.addStudent(RealmColors.RED);
        e.addStudent(RealmColors.RED);
        e.addStudent(RealmColors.RED);
        assertTrue(e.isFull());
        e.addStudent(RealmColors.PINK);
        assertEquals(7, e.getNumberOfElements());
    }

    /**
     * we are testing if the value in the students' hashmap is correctly updated (-1)
     */
    @Test
    public void removeStudent() {
        Entrance e = new Entrance(2);
        e.addStudent(RealmColors.YELLOW);
        e.addStudent(RealmColors.YELLOW);
        e.addStudent(RealmColors.YELLOW);
        e.addStudent(RealmColors.YELLOW);
        e.addStudent(RealmColors.YELLOW);
        e.addStudent(RealmColors.YELLOW);
        assertEquals(6, e.getNumberOfElements());
        e.removeStudent(RealmColors.YELLOW);
        assertEquals(5, e.getNumberOfElements());
    }

    /**
     * we are testing if trying to remove a student when all possible students have been removed from the entrance leads to errors
     */
    @Test
    public void removeStudentUnder() {
        Entrance e = new Entrance(2);
        e.addStudent(RealmColors.YELLOW);
        e.addStudent(RealmColors.YELLOW);
        e.addStudent(RealmColors.BLUE);
        e.addStudent(RealmColors.RED);
        assertEquals(4, e.getNumberOfElements());
        e.removeStudent(RealmColors.YELLOW);
        assertEquals(4, e.getNumberOfElements());
    }

    /**
     * we are testing if the output is correct (entrance is full when numOfElements = maxStudents)
     */
    @Test
    public void isFull() {
        Entrance e = new Entrance(2);
        e.addStudent(RealmColors.YELLOW);
        e.addStudent(RealmColors.YELLOW);
        e.addStudent(RealmColors.BLUE);
        e.addStudent(RealmColors.RED);
        e.addStudent(RealmColors.RED);
        e.addStudent(RealmColors.RED);
        assertFalse(e.isFull());
        e.addStudent(RealmColors.RED);
        assertTrue(e.isFull());
    }
}
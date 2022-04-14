package it.polimi.ingsw.Model.GameTableObjects;

import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Model.GameTableObjects.Bag;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class BagTest {

    Bag bagForTest = new Bag();

    /**
     * we are testing if the number of students in the Hashmap is correctly returned
     */
    @Test
    void getNumOfElements() {
        bagForTest.fillSetupBag();
        assertEquals(10, bagForTest.getNumberOfStudents());
        bagForTest.fillBag();
        assertEquals(120, bagForTest.getNumberOfStudents());
    }

    /**
     * we are testing if the value in the students' hashmap is correctly updated (+1 each time)
     */
    @Test
    void addStudent() {
        bagForTest.fillBag();
        bagForTest.removeStudent(RealmColors.YELLOW);
        bagForTest.removeStudent(RealmColors.YELLOW);
        bagForTest.addStudent(RealmColors.YELLOW);
        assertEquals(119, bagForTest.getNumberOfStudents());
    }

    /**
     * we are testing if the value in the students' hashmap is correctly updated (-1 each time)
     */
    @Test
    void removeStudent() {
        bagForTest.fillBag();
        bagForTest.removeStudent(RealmColors.YELLOW);
        assertEquals(119, bagForTest.getNumberOfStudents());
        bagForTest.removeStudent(RealmColors.RED);
        assertEquals(118, bagForTest.getNumberOfStudents());
    }

    /**
     * we are testing if the getter works properly
     */
    @Test
    public void getStudentsByColor() {
        bagForTest.fillSetupBag();
        assertEquals(2, bagForTest.getStudentsByColor(RealmColors.YELLOW));
        bagForTest.fillBag();
        assertEquals(24, bagForTest.getStudentsByColor(RealmColors.YELLOW));
    }

    /**
     * we are testing if the probability of a student to be drawn
     * is the same for each color when there are all the students
     *
     * this test acts like an extraction with reintegration
     */
    @Test
    void drawEqually() {
        bagForTest.fillBag();
        HashMap<RealmColors,Integer> studentsEqually = new HashMap<>();

        for (RealmColors c : RealmColors.values())
            studentsEqually.put(c,0);

        int studentsOfColor;
        RealmColors color;

        for (int i = 0; i < 100000; i++){
            color = bagForTest.draw();
            studentsOfColor = studentsEqually.get(color) + 1;
            studentsEqually.put(color, studentsOfColor);
            bagForTest.addStudent(color);
        }

        boolean checkInBetween = 19000 < studentsEqually.get(RealmColors.YELLOW) & studentsEqually.get(RealmColors.YELLOW) < 21000;
        assertTrue(checkInBetween);
        checkInBetween = 19000 < studentsEqually.get(RealmColors.PINK) & studentsEqually.get(RealmColors.PINK) < 21000;
        assertTrue(checkInBetween);
        checkInBetween = 19000 < studentsEqually.get(RealmColors.BLUE) & studentsEqually.get(RealmColors.BLUE) < 21000;
        assertTrue(checkInBetween);
        checkInBetween = 19000 < studentsEqually.get(RealmColors.RED) & studentsEqually.get(RealmColors.RED) < 21000;
        assertTrue(checkInBetween);
        checkInBetween = 19000 < studentsEqually.get(RealmColors.GREEN) & studentsEqually.get(RealmColors.GREEN) < 21000;
        assertTrue(checkInBetween);

    }

    /**
     * we are testing if when extracting 130 students the number of students remaining is equal to 0
     */
    @Test
    void drawWeighted() {
        bagForTest.fillBag();
        HashMap<RealmColors,Integer> students = new HashMap<>();

        for (RealmColors c : RealmColors.values())
            students.put(c,0);

        int studentsOfColor;
        RealmColors color;

        for (int i = 0; i < 120; i++){
            color = bagForTest.draw();
            studentsOfColor = students.get(color) + 1;
            students.put(color, studentsOfColor);
        }

        assertEquals(0, bagForTest.getNumberOfStudents());
    }

}
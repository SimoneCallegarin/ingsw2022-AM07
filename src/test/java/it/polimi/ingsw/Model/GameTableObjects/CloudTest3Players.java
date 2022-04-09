package it.polimi.ingsw.Model.GameTableObjects;

import it.polimi.ingsw.Model.Enumeration.RealmColors;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CloudTest3Players {

    //when game will be implemented it will be possible to better differentiate the two tests of the cloud

    /**
     * we are testing a cloud with a side of a 3 players match
     */
    Cloud cloudForTest = new Cloud(2, 3);

    /**
     * we are testing if the number of students on the cloud is correctly returned
     */
    @Test
    void getNumberOfElements() {
        assertEquals(0, cloudForTest.getNumberOfStudents());
        cloudForTest.addStudent(RealmColors.YELLOW);
        cloudForTest.addStudent(RealmColors.PINK);
        cloudForTest.addStudent(RealmColors.RED);
        cloudForTest.addStudent(RealmColors.PINK);
        assertEquals(4, cloudForTest.getNumberOfStudents());
        cloudForTest.addStudent(RealmColors.RED);
        assertEquals(4, cloudForTest.getNumberOfStudents());
    }

    /**
     * we are testing if the number of students of a certain color on the cloud is correctly returned
     */
    @Test
    void getNumberOfStudentsOfColor(){
        cloudForTest.addStudent(RealmColors.BLUE);
        cloudForTest.addStudent(RealmColors.BLUE);
        cloudForTest.addStudent(RealmColors.PINK);
        assertEquals(2,cloudForTest.getNumberOfStudentsOfColor(RealmColors.BLUE));
        assertEquals(1,cloudForTest.getNumberOfStudentsOfColor(RealmColors.PINK));
        assertEquals(0,cloudForTest.getNumberOfStudentsOfColor(RealmColors.YELLOW));
    }

    /**
     * we are testing if the students are correctly added to the cloud
     */
    @Test
    void addStudent() {
        assertEquals(0, cloudForTest.getNumberOfStudents());
        cloudForTest.addStudent(RealmColors.YELLOW);
        assertEquals(1, cloudForTest.getNumberOfStudents());
    }

    /**
     * we are testing if the students are correctly removed from the cloud
     */
    @Test
    void removeStudent() {
        cloudForTest.addStudent(RealmColors.YELLOW);
        cloudForTest.addStudent(RealmColors.YELLOW);
        cloudForTest.removeStudent(RealmColors.YELLOW);
        assertEquals(1,cloudForTest.getNumberOfStudentsOfColor(RealmColors.YELLOW));
    }

    /**
     * we are testing if the getter works properly
     */
    @Test
    public void getStudentsByColor() {
        assertEquals(0, cloudForTest.getStudentsByColor(RealmColors.YELLOW));
    }

    /**
     * we are testing if the cloud is empty when there aren't any students on the cloud
     */
    @Test
    void isEmpty() {
        cloudForTest.addStudent(RealmColors.YELLOW);
        assertFalse(cloudForTest.isEmpty());
        cloudForTest.removeStudent(RealmColors.YELLOW);
        assertTrue(cloudForTest.isEmpty());
    }

}
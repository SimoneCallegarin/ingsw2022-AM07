package it.polimi.ingsw.Model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiningRoomTest {

    /**
     * we are testing if class constructor sets every field to 0
     */
    @Test
    public void constructor() {
        DiningRoom dr = new DiningRoom();
        assertEquals(0, dr.getStudentsByColor(RealmColors.YELLOW));
        assertEquals(0, dr.getStudentsByColor(RealmColors.PINK));
        assertEquals(0, dr.getStudentsByColor(RealmColors.BLUE));
        assertEquals(0, dr.getStudentsByColor(RealmColors.RED));
        assertEquals(0, dr.getStudentsByColor(RealmColors.GREEN));
    }

    /**
     * we are testing if the method getnumOfElements works properly on normal conditions
     */
    @Test
    public void getNumBasic() {
        DiningRoom dr = new DiningRoom();
        dr.addStudent(RealmColors.YELLOW);
        dr.addStudent(RealmColors.YELLOW);
        dr.addStudent(RealmColors.BLUE);
        dr.addStudent(RealmColors.RED);
        dr.addStudent(RealmColors.RED);
        dr.addStudent(RealmColors.RED);
        dr.addProfessor(RealmColors.RED);
        assertEquals(7, dr.getNumOfElements());
    }

    /**
     * we are testing if the value in the students' hashmap is correctly updated (+1)
     */
    @Test
    public void addStudentBasic() {
        DiningRoom dr = new DiningRoom();
        dr.addStudent(RealmColors.YELLOW);
        assertEquals(1, dr.getStudentsByColor(RealmColors.YELLOW));
    }

    /**
     * we are testing if the value in the students' hashmap is correctly updated (-1)
     */
    @Test
    public void removeStudentBasic() {
        DiningRoom dr = new DiningRoom();
        dr.addStudent(RealmColors.YELLOW);
        dr.addStudent(RealmColors.YELLOW);
        dr.addStudent(RealmColors.YELLOW);
        assertEquals(3, dr.getStudentsByColor(RealmColors.YELLOW));
        dr.removeStudent(RealmColors.YELLOW);
        assertEquals(2, dr.getStudentsByColor(RealmColors.YELLOW));
    }

    /**
     * we are testing if removing a student where there are none doesn't change anything
     */
    @Test
    public void removeStudentInstantly() {
        DiningRoom dr = new DiningRoom();
        dr.removeStudent(RealmColors.YELLOW);
        assertEquals(0, dr.getStudentsByColor(RealmColors.YELLOW));
    }


    /**
     * we are testing if the value in the professors' hashmap is correctly updated (it becomes 1)
     */
    @Test
    public void addProfessorBasic() {
        DiningRoom dr = new DiningRoom();
        dr.addProfessor(RealmColors.YELLOW);
        assertEquals(1, dr.getProfessorByColor(RealmColors.YELLOW));
    }

    /**
     * we are testing if the value in the professors' hashmap is correctly updated (it becomes 0)
     */
    @Test
    public void removeProfessorBasic() {
        DiningRoom dr = new DiningRoom();
        dr.addProfessor(RealmColors.YELLOW);
        assertEquals(1, dr.getProfessorByColor(RealmColors.YELLOW));
        dr.removeProfessor(RealmColors.YELLOW);
        assertEquals(0, dr.getProfessorByColor(RealmColors.YELLOW));
    }

    /**
     * we are testing if the getter works properly
     */
    @Test
    public void getStudentsByColor() {
        DiningRoom dr = new DiningRoom();
        assertEquals(0, dr.getStudentsByColor(RealmColors.YELLOW));
    }

    /**
     * we are testing if the getter works properly
     */
    @Test
    public void getProfessorByColor() {
        DiningRoom dr = new DiningRoom();
        assertEquals(0, dr.getProfessorByColor(RealmColors.YELLOW));
    }
}
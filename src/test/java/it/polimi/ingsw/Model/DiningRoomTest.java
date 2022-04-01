package it.polimi.ingsw.Model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiningRoomTest {

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
    void removeStudentBasic() {
        DiningRoom dr = new DiningRoom();
        dr.addStudent(RealmColors.YELLOW);
        dr.addStudent(RealmColors.YELLOW);
        dr.addStudent(RealmColors.YELLOW);
        assertEquals(3, dr.getStudentsByColor(RealmColors.YELLOW));
        dr.removeStudent(RealmColors.YELLOW);
        assertEquals(2, dr.getStudentsByColor(RealmColors.YELLOW));
    }

    /**
     * we are testing if the value in the professors' hashmap is correctly updated (it becomes 1)
     */
    @Test
    void addProfessorBasic() {
        DiningRoom dr = new DiningRoom();
        dr.addProfessor(RealmColors.YELLOW);
        assertEquals(1, dr.getProfessorByColor(RealmColors.YELLOW));
    }

    /**
     * we are testing if the value in the professors' hashmap is correctly updated (it becomes 0)
     */
    @Test
    void removeProfessorBasic() {
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
    void getStudentsByColor() {
        DiningRoom dr = new DiningRoom();
        assertEquals(0, dr.getStudentsByColor(RealmColors.YELLOW));
    }

    /**
     * we are testing if the getter works properly
     */
    @Test
    void getProfessorByColor() {
        DiningRoom dr = new DiningRoom();
        assertEquals(0, dr.getProfessorByColor(RealmColors.YELLOW));
    }
}
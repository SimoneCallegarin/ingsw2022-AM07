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
        dr.addStudent(Colors.YELLOW);
        assertEquals(1, dr.getStudentsByColor(Colors.YELLOW));
    }

    /**
     * we are testing if the value in the students' hashmap is correctly updated (-1)
     */
    @Test
    void removeStudentBasic() {
        DiningRoom dr = new DiningRoom();
        dr.addStudent(Colors.YELLOW);
        dr.addStudent(Colors.YELLOW);
        dr.addStudent(Colors.YELLOW);
        assertEquals(3, dr.getStudentsByColor(Colors.YELLOW));
        dr.removeStudent(Colors.YELLOW);
        assertEquals(2, dr.getStudentsByColor(Colors.YELLOW));
    }

    /**
     * we are testing if the value in the professors' hashmap is correctly updated (it becomes 1)
     */
    @Test
    void addProfessorBasic() {
        DiningRoom dr = new DiningRoom();
        dr.addProfessor(Colors.YELLOW);
        assertEquals(1, dr.getProfessorByColor(Colors.YELLOW));
    }

    /**
     * we are testing if the value in the professors' hashmap is correctly updated (it becomes 0)
     */
    @Test
    void removeProfessorBasic() {
        DiningRoom dr = new DiningRoom();
        dr.addProfessor(Colors.YELLOW);
        assertEquals(1, dr.getProfessorByColor(Colors.YELLOW));
        dr.removeProfessor(Colors.YELLOW);
        assertEquals(0, dr.getProfessorByColor(Colors.YELLOW));
    }

    /**
     * we are testing if the getter works properly
     */
    @Test
    void getStudentsByColor() {
        DiningRoom dr = new DiningRoom();
        assertEquals(0, dr.getStudentsByColor(Colors.YELLOW));
    }

    /**
     * we are testing if the getter works properly
     */
    @Test
    void getProfessorByColor() {
        DiningRoom dr = new DiningRoom();
        assertEquals(0, dr.getProfessorByColor(Colors.YELLOW));
    }
}
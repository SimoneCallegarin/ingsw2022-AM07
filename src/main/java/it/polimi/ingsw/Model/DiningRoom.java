package it.polimi.ingsw.Model;

import java.util.HashMap;

public class DiningRoom implements StudentManager, ProfessorManager{

    /**
     * this is the students container
     */
    private final HashMap<Colors,Integer> students;
    /**
     * this is the professors container
     */
    private final HashMap<Colors,Integer> professors;
    /**
     * this is a value that defines how the dining room is made
     */
    private static final int maxStudentsPerColor = 10;

    /**
     * DiningRoom constructor: hashmaps initialization (every field is set to 0)
     */
    public DiningRoom() {
        this.students = new HashMap<>();
        this.professors = new HashMap<>();

        for (Colors c : Colors.values()) {
            students.put(c, 0);
            professors.put(c, 0);
        }
    }

    /**
     * this method updates the students' hashmap incrementing by 1 the value specified by color
     * @param color is the key of the value we want to update in the students' hashmap
     */
    @Override
    public void addStudent(Colors color) {
        int temp;
        temp = students.get(color);
        temp++;
        students.put(color, temp);
    }

    /**
     * this method updates the students' hashmap decrementing by 1 the value specified by color
     * @param color is the key of the value we want to update in the students' hashmap
     */
    @Override
    public void removeStudent(Colors color) {
        int temp;
        temp = students.get(color);
        temp--;
        students.put(color, temp);
    }

    /**
     * this method adds the professor of a certain color to the dining room of the player who owns it
     * @param color is the key of the value we want to set to 1 in the professors' hashmap
     */
    @Override
    public void addProfessor(Colors color) {
        professors.put(color, 1);
    }

    /**
     * this method removes the professor of a certain color from the dining room of the player who doesn't own it anymore
     * @param color is the key of the value we want to set to 0 in the professors' hashmap
     */
    @Override
    public void removeProfessor(Colors color) {
        professors.put(color, 0);
    }

    /**
     * a getter method to receive a certain value contained in the students' hashmap
     * @param color is the key of the value we want to get
     * @return the value we want
     */
    public int getStudentsByColor(Colors color) {
        return students.get(color);
    }

    /**
     * a getter method to receive a certain value contained in the professors' hashmap
     * @param color is the key of the value we want to get
     * @return the value we want
     */
    public int getProfessorByColor(Colors color) {
        return professors.get(color);
    }

}

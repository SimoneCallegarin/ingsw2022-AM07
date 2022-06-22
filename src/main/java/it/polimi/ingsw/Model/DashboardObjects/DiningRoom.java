package it.polimi.ingsw.Model.DashboardObjects;

import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Model.Interface.StudentManager;

import java.util.HashMap;

/**
 * Dining room of the dashboard of the player, it contains students.
 */
public class DiningRoom implements StudentManager {

    /**
     * Students container.
     */
    private final HashMap<RealmColors,Integer> students;
    /**
     * Professors container.
     */
    private final HashMap<RealmColors,Integer> professors;
    /**
     * Limit of students in the dining room per color.
     */
    private static final int maxStudentsPerColor = 10;

    /**
     * DiningRoom constructor:
     * hashmaps initialization (every field is set to 0).
     */
    public DiningRoom() {
        this.students = new HashMap<>();
        this.professors = new HashMap<>();
        for (RealmColors rc : RealmColors.values()) {
            students.put(rc, 0);
            professors.put(rc, 0);
        }
    }

    /**
     * Updates the students' hashmap incrementing by 1 the value specified by the color.
     * @param color is the key of the value we want to update in the students' hashmap.
     */
    @Override
    public void addStudent(RealmColors color) {
        int temp;
        temp = students.get(color);
        if (temp < maxStudentsPerColor)
            temp += 1;
        students.put(color,temp);
    }

    /**
     * Updates the students' hashmap decrementing by 1 the value specified by the color.
     * @param color is the key of the value we want to update in the students' hashmap.
     */
    @Override
    public void removeStudent(RealmColors color) {
        int temp;
        temp = students.get(color);
        if (temp > 0)
            temp--;
        students.put(color, temp);
    }

    /**
     * Adds the professor of a certain color to the dining room of the player who owns it.
     * @param color is the key of the value we want to set to 1 in the professors' hashmap.
     */
    public void addProfessor(RealmColors color) { professors.put(color, 1); }

    /**
     * Removes the professor of a certain color from the dining room of the player who doesn't own it anymore.
     * @param color is the key of the value we want to set to 0 in the professors' hashmap.
     */
    public void removeProfessor(RealmColors color) { professors.put(color, 0); }

    /**
     * Getter method for the students of a certain color in the dining room.
     * @param color is the key of the value we want to get.
     * @return the number of students in the dining room of that color.
     */
    @Override
    public int getStudentsByColor(RealmColors color) { return students.get(color); }

    /**
     * Getter method for a professor.
     * @param color is the key of the value we want to get.
     * @return 1 if the professor of that color is present in the dining room, else 0.
     */
    public int getProfessorByColor(RealmColors color) { return professors.get(color); }

    /**
     * Getter method for the student HashMap.
     * @return the student HashMap.
     */
    @Override
    public HashMap<RealmColors, Integer> getStudentsHashMap() { return students; }

    /**
     * Getter method for the professors HashMap.
     * @return the professor HashMap.
     */
    public HashMap<RealmColors, Integer> getProfessors() { return professors; }

    /**
     * Getter method for the total number of students in the dining room.
     * @return the number of students actually in the dining room.
     */
    @Override
    public int getNumberOfStudents() {
        int totalNumberOfStudents = 0;
        for (RealmColors rc : RealmColors.values()){
            totalNumberOfStudents = totalNumberOfStudents + students.get(rc);
        }
        return totalNumberOfStudents;
    }

    /**
     * Getter method for the total number of professors in the dining room.
     * @return the number of professors actually in the dining room.
     */
    public int getNumberOfProfessors() {
        int totalNumberOfProfessors = 0;
        for (RealmColors rc : RealmColors.values()){
            totalNumberOfProfessors = totalNumberOfProfessors + professors.get(rc);
        }
        return totalNumberOfProfessors;
    }

}

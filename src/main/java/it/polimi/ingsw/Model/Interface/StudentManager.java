package it.polimi.ingsw.Model.Interface;

import it.polimi.ingsw.Model.Enumeration.RealmColors;

import java.util.HashMap;

/**
 * Interface for objects of the game that have students on them.
 */
public interface StudentManager {

    /**
     * Updates the students' hashmap incrementing by 1 the value specified by the color.
     * @param color is the key of the value we want to update in the students' hashmap.
     */
    void addStudent(RealmColors color);

    /**
     * Updates the students' hashmap decrementing by 1 the value specified by the color
     * @param color is the key of the value we want to update in the students' hashmap.
     */
    void removeStudent(RealmColors color);

    /**
     * Getter method to receive the quantity of students of a certain color in the students' hashmap.
     * @param color is the key of the value we want to get.
     * @return the number of students of that color on the student manager.
     */
    int getStudentsByColor(RealmColors color);

    /**
     * Getter method for the number of students in the student manager.
     * @return the number of students actually in the student manager.
     */
    int getNumberOfStudents();

    /**
     * Getter method for the students HashMap.
     * @return the students HashMap.
     */
    HashMap<RealmColors, Integer> getStudentsHashMap();

}

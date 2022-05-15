package it.polimi.ingsw.Model.Interface;

import it.polimi.ingsw.Model.Enumeration.RealmColors;

import java.util.HashMap;

/**
 * interface for Places that needs to implement these three methods
 */
public interface StudentManager {

    /**
     * this method updates the students' hashmap incrementing by 1 the value specified by color
     * @param color is the key of the value we want to update in the students' hashmap
     */
    void addStudent(RealmColors color);

    /**
     * this method updates the students' hashmap decrementing by 1 the value specified by color
     * @param color is the key of the value we want to update in the students' hashmap
     */
    void removeStudent(RealmColors color);

    /**
     * a getter method to receive a certain value contained in the students' hashmap
     * @param color is the key of the value we want to get
     * @return the value we want
     */
    int getStudentsByColor(RealmColors color);

    /**
     * this method when called gives the number of students in the student manager
     * @return the number of students actually in the bag
     */
    int getNumberOfStudents();

    /**
     * getter for returning the students HashMap
     * @return the student HashMap
     */
    public HashMap<RealmColors, Integer> getStudents();

}

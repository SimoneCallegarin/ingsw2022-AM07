package it.polimi.ingsw.Model.DashboardObjects;

import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Model.Interface.StudentManager;

import java.util.HashMap;

/**
 * Entrance of the dashboard of the player, it contains students.
 */
public class Entrance implements StudentManager {

    /**
     * Students container.
     */
    private final HashMap<RealmColors,Integer> students;
    /**
     * Defines how many students can be in the entrance at the same time.
     */
    private final int maxStudents;

    /**
     * Entrance constructor:
     * hashmaps initialization (every field is set to 0) and maxNumber attribute initialization.
     * @param maxStudents is used to choose which value has to be assigned to maxStudents.
     */
    public Entrance(int maxStudents) {
        this.maxStudents = maxStudents;
        this.students = new HashMap<>();
        for (RealmColors rc : RealmColors.values())
            students.put(rc, 0);
    }

    /**
     * Updates the students' hashmap incrementing by 1 the value specified by the color.
     * @param color is the key of the value we want to update in the students' hashmap.
     */
    @Override
    public void addStudent(RealmColors color) {
        int temp;
        temp = students.get(color);
        if (getNumberOfStudents() < maxStudents)
            temp++;
        students.put(color, temp);
    }

    /**
     * Updates the students' hashmap decrementing by 1 the value specified by the color.
     * @param color is the key of the value we want to update in the students' hashmap.
     */
    @Override
    public void removeStudent(RealmColors color) {
        int temp;
        temp = students.get(color);
        if (temp > 0)       //ADD a check that are not removed more than 3/4 students per turn for each player
            temp--;
        students.put(color, temp);
    }

    /**
     * Checks if the entrance is filled with students.
     * @return true if the entrance is filled with students, else false.
     */
    public boolean isFull() { return getNumberOfStudents() == maxStudents; }

    /**
     * Getter method for the maximum quantity of students in the entrance.
     * @return the number of maximum students in the entrance.
     */
    public int getMaxStudents() { return maxStudents; }

    /**
     * Getter method for the students of a certain color in the entrance.
     * @param color is the key of the value we want to get.
     * @return the number of students of that color in the entrance.
     */
    @Override
    public int getStudentsByColor(RealmColors color) { return students.get(color); }

    /**
     * Getter method for the student HashMap.
     * @return the student HashMap.
     */
    @Override
    public HashMap<RealmColors, Integer> getStudentsHashMap() { return students; }

    /**
     * Getter method for the number of students in the entrance.
     * @return the number of students actually in the entrance.
     */
    @Override
    public int getNumberOfStudents() {
        int totalNumberOfStudents = 0;
        for (RealmColors rc : RealmColors.values()){
            totalNumberOfStudents = totalNumberOfStudents + students.get(rc);
        }
        return totalNumberOfStudents;
    }

}

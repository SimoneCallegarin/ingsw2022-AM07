package it.polimi.ingsw.Model;

import java.util.HashMap;

public class Entrance implements Place, StudentManager {

    /**
     * this is the students container
     */
    private final HashMap<RealmColors,Integer> students;
    /**
     * this is a value that defines how many students can be in the entrance at the same time
     */
    private final int maxStudents;
    /**
     * this is a value that defines how many students can be removed from the entrance during a turn
     */
    private final int maxStudentsRemovable;

    /**
     * Entrance constructor: hashmaps initialization (every field is set to 0); maxNumber and maxNumberRemovable attributes initialization
     * @param maxStudents is used to choose which value has to be assigned to maxStudents
     * @param maxStudentsRemovable is used to choose which value has to be assigned to maxStudentsRemovable
     */
    public Entrance(int maxStudents, int maxStudentsRemovable) {
        this.students = new HashMap<>();

        for (RealmColors rc : RealmColors.values()) {
            students.put(rc, 0);
        }

        this.maxStudents = maxStudents;
        this.maxStudentsRemovable = maxStudentsRemovable;

    }

    /**
     * this method when called gives the number of students in the entrance
     * @return the number of students actually in the entrance
     */
    @Override
    public int getNumberOfElements() {
        int totalNumberOfStudents = 0;
        for (RealmColors rc : RealmColors.values()){
            totalNumberOfStudents = totalNumberOfStudents + students.get(rc);
        }
        return totalNumberOfStudents;
    }

    /**
     * this method updates the students' hashmap incrementing by 1 the value specified by color
     * @param color is the key of the value we want to update in the students' hashmap
     */
    @Override
    public void addStudent(RealmColors color) {
        int temp;
        temp = students.get(color);
        if (getNumberOfElements() < maxStudents)
            temp++;
        students.put(color, temp);
    }

    /**
     * this method updates the students' hashmap decrementing by 1 the value specified by color
     * @param color is the key of the value we want to update in the students' hashmap
     */
    @Override
    public void removeStudent(RealmColors color) {
        int temp;
        temp = students.get(color);
        if (temp > 0 && getNumberOfElements() > (maxStudents-maxStudentsRemovable))
            temp--;
        students.put(color, temp);
    }

    /**
     * a getter method to receive a certain value contained in the students' hashmap
     * @param color is the key of the value we want to get
     * @return the value we want
     */
    @Override
    public int getStudentsByColor(RealmColors color) {
        return students.get(color);
    }

    /**
     * this method checks if the entrance is filled with students
     * @return is the boolean that says if the entrance is full or not
     */
    public boolean isFull() {
        return getNumberOfElements() == maxStudents;
    }
}

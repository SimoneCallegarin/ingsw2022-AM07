package it.polimi.ingsw.Model.GameTableObjects;

import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Model.Interface.StudentManager;

import java.util.HashMap;

/**
 * Bag of the game table, it contains students.
 */
public class Bag implements StudentManager {

    /**
     * Students container.
     */
    private final HashMap<RealmColors,Integer> students;

    /**
     * Constructor of the bag, we put 130 students at the start of the game.
     */
    public Bag() { this.students = new HashMap<>(); }

    /**
     * It fills the bag with 10 students (2 per color), that will be put on the isle.
     */
    public void fillSetupBag() {
        for (RealmColors c : RealmColors.values())
            students.put(c, 2);
    }

    /**
     * It fills the bag with the remaining students.
     */
    public void fillBag() {
        for (RealmColors c : RealmColors.values())
            students.put(c, 24);
    }

    /**
     * Extract the students for the bag randomly, basing on how many students per color there are.
     * @return the color of the extracted student.
     */
    public RealmColors draw(){

        int studentsSequence = 0;   //studentsSequence permits checking of which color the random students extracted is

        int randomStudent = (int) (Math.random() * (getNumberOfStudents())+1);

        for (RealmColors c : RealmColors.values()) {
            studentsSequence = students.get(c) + studentsSequence;

            if(randomStudent <= studentsSequence){
                removeStudent(c);
                return c;
            }

        }
        return null;
    }

    /**
     * Updates the students' hashmap incrementing by 1 the value specified by the color.
     * @param color is the key of the value we want to update in the students' hashmap.
     */
    @Override
    public void addStudent(RealmColors color) {
        int temp;
        temp = students.get(color);
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
        temp--;
        students.put(color, temp);
    }

    /**
     * Getter method for the number of students in the bag.
     * @return the number of students actually in the bag.
     */
    @Override
    public int getNumberOfStudents() {
        int totalNumberOfStudents = 0;
        for (RealmColors c : RealmColors.values()){
            totalNumberOfStudents = totalNumberOfStudents + students.get(c);
        }
        return totalNumberOfStudents;
    }

    /**
     * Getter method for the hashmap of the students.
     * @return the hashmap of the students.
     */
    @Override
    public HashMap<RealmColors, Integer> getStudentsHashMap() { return students; }

    /**
     * Getter method to receive a certain value contained in the students' hashmap.
     * @param color is the key of the value we want to get.
     * @return the number of students of that color.
     */
    @Override
    public int getStudentsByColor(RealmColors color) { return students.get(color); }

}

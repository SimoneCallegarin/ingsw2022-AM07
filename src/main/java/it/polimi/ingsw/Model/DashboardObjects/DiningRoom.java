package it.polimi.ingsw.Model.DashboardObjects;

import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Model.Interface.ProfessorManager;
import it.polimi.ingsw.Model.Interface.StudentManager;

import java.util.HashMap;

public class DiningRoom implements StudentManager, ProfessorManager {

    /**
     * this is the students container
     */
    final HashMap<RealmColors,Integer> students;
    /**
     * this is the professors container
     */
    final HashMap<RealmColors,Integer> professors;
    /**
     * this is a value that defines how the dining room is made
     */
    static final int maxStudentsPerColor = 10;

    boolean giveMoney = false;

    /**
     * DiningRoom constructor: hashmaps initialization (every field is set to 0)
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
     * this method when called gives the number of students in the dining room
     * @return the number of students actually in the dining room
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
     * this method when called gives the number of professors in the dining room
     * @return the number of professors actually in the dining room
     */
    @Override
    public int getNumberOfProfessors() {
        int totalNumberOfProfessors = 0;
        for (RealmColors rc : RealmColors.values()){
            totalNumberOfProfessors = totalNumberOfProfessors + professors.get(rc);
        }
        return totalNumberOfProfessors;
    }

    /**
     * this method updates the students' hashmap incrementing by 1 the value specified by color
     * @param color is the key of the value we want to update in the students' hashmap
     */
    @Override
    public void addStudent(RealmColors color) {
        int temp;
        temp = students.get(color);
        if (temp < maxStudentsPerColor) {
            if (temp%3 == 0)
                giveMoney = true;  //ANCORA DA IMPLEMENTARE
            temp += 1;
        }
        students.put(color, temp);
    }

    /**
     * this method updates the students' hashmap decrementing by 1 the value specified by color
     * @param color is the key of the value we want to update in the students' hashmap
     * @return the color of the removed student
     */
    @Override
    public RealmColors removeStudent(RealmColors color) {
        int temp;
        temp = students.get(color);
        if (temp > 0)
            temp--;
        students.put(color, temp);
        return color;
    }

    /**
     * this method adds the professor of a certain color to the dining room of the player who owns it
     * @param color is the key of the value we want to set to 1 in the professors' hashmap
     */
    @Override
    public void addProfessor(RealmColors color) {
        professors.put(color, 1);
    }

    /**
     * this method removes the professor of a certain color from the dining room of the player who doesn't own it anymore
     * @param color is the key of the value we want to set to 0 in the professors' hashmap
     */
    @Override
    public void removeProfessor(RealmColors color) {
        professors.put(color, 0);
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
     * a getter method to receive a certain value contained in the professors' hashmap
     * @param color is the key of the value we want to get
     * @return the value we want
     */
    @Override
    public int getProfessorByColor(RealmColors color) {
        return professors.get(color);
    }

}

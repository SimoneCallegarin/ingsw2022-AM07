package it.polimi.ingsw.Model;

import java.util.HashMap;

public class Bag implements Place, StudentManager{

    /**
     * this is the students container
     */
    private final HashMap<RealmColors,Integer> students;

    public Bag() {
        this.students = new HashMap<>();

        for (RealmColors c : RealmColors.values()) {
            students.put(c, 26);
        }

    }

    /**
     * this method when called gives the number of students in the bag
     * @return the number of students actually in the bag
     */
    @Override
    public int getnumOfElements() {
        int totalNumberOfStudents = 0;
        for (RealmColors c : RealmColors.values()){
            totalNumberOfStudents = totalNumberOfStudents + students.get(c);
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
        temp--;
        students.put(color, temp);
    }

    /**
     * this method extract the students for the bag randomly, basing on how many students per color there are
     * @return the color of the extracted student
     */
    public RealmColors draw(){

        int studentsSequence = 0;   //studentsSequence permits checking of which color the random students extracted is

        int randomStudent = (int) (Math.random() * (getnumOfElements())+1);

        for (RealmColors c : RealmColors.values()) {
            studentsSequence = students.get(c) + studentsSequence;

            if(randomStudent <= studentsSequence){
                removeStudent(c);
                return c;
            }

        }
        return null;
    }

}

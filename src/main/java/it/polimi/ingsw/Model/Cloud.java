package it.polimi.ingsw.Model;

import java.util.HashMap;

public class Cloud implements Place,StudentManager{

    private final int idCloud;
    private final CloudSide side;
    /**
     * this is the students container
     */
    private final HashMap<RealmColors,Integer> students;

    public Cloud(int idCloud, CloudSide side) {
        this.idCloud = idCloud;
        this.side = side;
        this.students = new HashMap<>();

        for (RealmColors c : RealmColors.values()) {
            students.put(c, 0);
        }
    }

    /**
     * this method when called gives the number of students in the bag
     * @return the number of students actually in the bag
     */
    @Override
    public int getNumberOfElements() {
        int totalNumberOfStudents = 0;
        for (RealmColors c : RealmColors.values()){
            totalNumberOfStudents = totalNumberOfStudents + students.get(c);
        }
        return totalNumberOfStudents;
    }

    /**
     * this method when called gives the number of students in the bag of a precise color
     * @param  colors is the color of the students we want to know the number
     * @return the number of students actually in the bag of a certain color
     */
    public int getNumberOfStudentsOfColor(RealmColors colors) {
        return students.get(colors);
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
     * a getter method to receive a certain value contained in the students' hashmap
     * @param color is the key of the value we want to get
     * @return the value we want
     */
    @Override
    public int getStudentsByColor(RealmColors color) {
        return students.get(color);
    }

    //the following method might be not very useful, in the future we may decide to remove it
    /**
     * this method permits knowing if the cloud is actually empty
     */
    public boolean isEmpty(){
        return getNumberOfElements() == 0;
    }

}

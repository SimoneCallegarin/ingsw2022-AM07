package it.polimi.ingsw.Model.GameTableObjects;

import it.polimi.ingsw.Model.Enumeration.CloudSide;
import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Model.Interface.StudentManager;

import java.util.HashMap;

public class Cloud implements StudentManager {

    private final int idCloud;
    private final CloudSide side;
    private final int maxCloudsStudents;
    /**
     * this is the students container
     */
    private final HashMap<RealmColors,Integer> students;

    public Cloud(int idCloud,CloudSide side) {
        this.idCloud = idCloud;
        this.side = side;
        this.students = new HashMap<>();

        for (RealmColors c : RealmColors.values()) {
            students.put(c, 0);
        }

       if(side == CloudSide.SIDE_2_AND_4_PLAYERS)
           this.maxCloudsStudents = 3;
       else
           this.maxCloudsStudents = 4;

    }

    /**
     * this method when called gives the number of students in the bag
     * @return the number of students actually in the bag
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
     * this method when called gives the number of students in the bag of a precise color
     * @param  colors is the color of the students we want to know the number
     * @return the number of students actually in the bag of a certain color
     */
    public int getNumberOfStudentsOfColor(RealmColors colors) {
        return students.get(colors);
    }   // refuso

    /**
     * this method updates the students' hashmap incrementing by 1 the value specified by color
     * @param color is the key of the value we want to update in the students' hashmap
     */
    @Override
    public void addStudent(RealmColors color) {
        int temp;
        temp = students.get(color);
        temp++;
        if (getNumberOfStudents()<maxCloudsStudents)
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
        if (getNumberOfStudents()>0)
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

    public int getIdCloud(){ return idCloud; }

    //the following method might be not very useful, in the future we may decide to remove it
    /**
     * this method permits knowing if the cloud is actually empty
     */
    public boolean isEmpty(){
        return getNumberOfStudents() == 0;
    }

}

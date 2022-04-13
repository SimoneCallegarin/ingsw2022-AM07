package it.polimi.ingsw.Model.GameTableObjects;

import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Model.Interface.StudentManager;

import java.util.HashMap;

public class Cloud implements StudentManager {

    /**
     * this is the id of the cloud, it is decided in the GameTable according to the number of players
     */
    private final int idCloud;
    /**
     * this is the maximum number of students that can be placed on the cloud
     */
    private final int maxCloudsStudents;
    /**
     * this is the students container
     */
    private final HashMap<RealmColors,Integer> students;

    /**
     * Cloud constructor
     * @param idCloud id of the cloud
     * @param numberOfPlayers the number of players that are going to play the game
     */
    public Cloud(int idCloud, int numberOfPlayers) {
        this.idCloud = idCloud;
        this.students = new HashMap<>();

        for (RealmColors c : RealmColors.values()) {
            students.put(c, 0);
        }

       if(numberOfPlayers == 3)
           this.maxCloudsStudents = 4;
       else
           this.maxCloudsStudents = 3;

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

    /**
     * this method when called gives the number of students in the cloud
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
     * a getter method that gives the id of the cloud
     * @return the id of the cloud
     */
    public int getIdCloud(){ return idCloud; }

    /**
     * this method permits knowing if the cloud is actually empty
     */
    public boolean isEmpty(){
        return getNumberOfStudents() == 0;
    }

}

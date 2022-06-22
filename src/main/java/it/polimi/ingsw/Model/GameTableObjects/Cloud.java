package it.polimi.ingsw.Model.GameTableObjects;

import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Model.Interface.StudentManager;

import java.util.HashMap;

/**
 * Cloud of the game table, it contains students.
 */
public class Cloud implements StudentManager {

    /**
     * ID of the cloud, it is decided in the GameTable according to the number of players.
     */
    private final int cloudID;
    /**
     * Maximum number of students that can be placed on the cloud.
     */
    private final int maxCloudsStudents;
    /**
     * Students container.
     */
    private final HashMap<RealmColors,Integer> students;

    /**
     * Cloud constructor.
     * @param cloudID ID of the cloud.
     * @param numberOfPlayers the number of players that are going to play the game.
     */
    public Cloud(int cloudID, int numberOfPlayers) {
        this.cloudID = cloudID;
        this.students = new HashMap<>();

        for (RealmColors c : RealmColors.values())
            students.put(c, 0);

       if(numberOfPlayers == 3)
           this.maxCloudsStudents = 4;
       else
           this.maxCloudsStudents = 3;
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
        if (getNumberOfStudents()<maxCloudsStudents)
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
        if (getNumberOfStudents()>0)
            students.put(color, temp);
    }

    /**
     * Getter method for the ID of the cloud.
     * @return the ID of the cloud.
     */
    public int getCloudID(){ return cloudID; }

    /**
     * Getter method that permits knowing if the cloud is actually empty.
     * @return true if the cloud is empty, else false.
     */
    public boolean isEmpty(){ return getNumberOfStudents() == 0; }

    /**
     * Getter method for the quantity of students of a certain color.
     * @param color is the key of the value we want to get.
     * @return the number of students of that color.
     */
    @Override
    public int getStudentsByColor(RealmColors color) { return students.get(color); }

    /**
     * Getter method for the number of students on the cloud.
     * @return the number of students actually on the cloud.
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
     * Getter method for the students' hashmap.
     * @return the hashmap of the students on the cloud.
     */
    @Override
    public HashMap<RealmColors, Integer> getStudentsHashMap() { return students; }

}

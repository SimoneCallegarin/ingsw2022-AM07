package it.polimi.ingsw.Model;

/**
 * interface for Places that needs to implement these three methods
 */
public interface StudentManager {
    public void addStudent(RealmColors color);
    public void removeStudent(RealmColors color);
    public int getStudentsByColor(RealmColors color);
}

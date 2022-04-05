package it.polimi.ingsw.Model.Interface;

import it.polimi.ingsw.Model.Enumeration.RealmColors;

/**
 * interface for Places that needs to implement these three methods
 */
public interface StudentManager {
    void addStudent(RealmColors color);
    void removeStudent(RealmColors color);
    int getStudentsByColor(RealmColors color);
}

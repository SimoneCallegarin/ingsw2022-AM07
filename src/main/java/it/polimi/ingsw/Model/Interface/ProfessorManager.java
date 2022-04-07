package it.polimi.ingsw.Model.Interface;

import it.polimi.ingsw.Model.Enumeration.RealmColors;

/**
 * interface for Places that needs to implement these three methods
 */
public interface ProfessorManager {
    void addProfessor(RealmColors color);
    void removeProfessor(RealmColors color);
    int getNumberOfProfessors();
    int getProfessorByColor(RealmColors color);
}

package it.polimi.ingsw.Model;

/**
 * interface for Places that needs to implement these three methods
 */
public interface ProfessorManager {
    public void addProfessor(RealmColors color);
    public void removeProfessor(RealmColors color);
    public int getProfessorByColor(RealmColors color);
}

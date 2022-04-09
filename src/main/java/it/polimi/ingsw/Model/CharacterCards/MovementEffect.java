package it.polimi.ingsw.Model.CharacterCards;

import it.polimi.ingsw.Model.Enumeration.Movable;
import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Model.Interface.StudentManager;

public class MovementEffect implements AtomicEffect {

    /**
     * Effect that permits to move students from a StudentsManager to another
     * or to to move a denyCard from a DenyCardsManager to another
     * @param times the number of times this exact movement has to be done
     * @param from the StudentsManager/DenyCardsManager where is removed the student/denyCard
     * @param to the StudentsManager/DenyCardsManager where is added the student/denyCard
     * @param movable indicates if it is a student or a denyCard that has to be moved
     * @param color the color of the student that has to be moved
     */
    @Override
    public void effect(int times, StudentManager from, StudentManager to, Movable movable, RealmColors color) {
        for (int i = 0; i < times; i++) {
            if(movable.equals(Movable.STUDENT)){
                to.addStudent(color);
                from.removeStudent(color);
            }
            //if (movable.equals(Movable.DENYCARD)){
            //    to.addDenyCard();
            //   from.removeDenyCard();
            //}

        }
    }
}

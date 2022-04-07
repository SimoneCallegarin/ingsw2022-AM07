package it.polimi.ingsw.Model.CharacterCards;

import it.polimi.ingsw.Model.Enumeration.Movable;
import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Model.Interface.StudentManager;

public class MovementEffect implements AtomicEffect {

    @Override
    public void effect(int times, StudentManager from, StudentManager to, Movable movable, RealmColors color) {
        for (int i = 0; i < times; i++) {
            to.addStudent(from.removeStudent(color));
        }
    }
}

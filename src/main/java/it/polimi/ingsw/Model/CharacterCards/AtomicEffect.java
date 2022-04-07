package it.polimi.ingsw.Model.CharacterCards;

import it.polimi.ingsw.Model.Enumeration.Movable;
import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Model.Interface.StudentManager;

public interface AtomicEffect {

    void effect(int times, StudentManager from, StudentManager to, Movable movable, RealmColors color);

}

package it.polimi.ingsw.Model.CharacterCards;

import it.polimi.ingsw.Model.Enumeration.Movable;
import it.polimi.ingsw.Model.GameTableObjects.Bag;

public abstract class Effect {
    public void effect() {

    }

    public abstract void effect(Movable object, CharacterCard characterCard, Bag bag);

}

package it.polimi.ingsw.Model.CharacterCards;

import it.polimi.ingsw.Model.Enumeration.Movable;
import it.polimi.ingsw.Model.GameTableObjects.Bag;

public class SetupEffect extends  Effect{
    @Override
    public void effect(Movable object, CharacterCard characterCard, Bag bag) {
        super.effect();
        if(object.equals(Movable.STUDENT))
            characterCard.addStudent(bag.draw());
        if ((object.equals(Movable.DENYCARD)))
            characterCard.addDenyCard();
    }
}

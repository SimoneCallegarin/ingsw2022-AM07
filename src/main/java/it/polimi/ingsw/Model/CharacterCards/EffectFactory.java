package it.polimi.ingsw.Model.CharacterCards;

import it.polimi.ingsw.Model.Enumeration.Movable;
import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Model.Interface.StudentManager;

import java.util.ArrayList;

public class EffectFactory {

    public EffectFactory(){}

    /*public ArrayList<AtomicEffect> getEffect (CharacterCardsName card){
        ArrayList<AtomicEffect> effect = null;

        StudentManager from = null;
        StudentManager to = null;
        RealmColors color = RealmColors.YELLOW;

        switch (card){
            case MONK:

                CharacterCard characterCard = new CharacterCard(CharacterCardsName.MONK);

                AtomicEffect setup = new SetupEffect();
                setup.effect(4, null,null,Movable.STUDENT,color);

                effect.add(setup);

                AtomicEffect move = new MovementEffect();

                move.effect(1, from, to, Movable.STUDENT,color);

                effect.add(move);

                break;
            case FARMER:
                break;
            case HERALD:
                break;
            case MAGICAL_LETTER_CARRIER:
                break;
            case GRANDMA_HERBS:
                break;
            case CENTAUR:
                break;
            case JESTER:
                break;
            case KNIGHT:
                break;
            case FUNGIST:
                break;
            case MINSTREL:
                break;
            case SPOILED_PRINCESS:
                break;
            case THIEF:
                break;
        }
        return effect;
    }*/

}

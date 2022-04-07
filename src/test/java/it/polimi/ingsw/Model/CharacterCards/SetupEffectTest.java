package it.polimi.ingsw.Model.CharacterCards;

import it.polimi.ingsw.Model.Enumeration.Movable;
import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Model.GameTableObjects.Bag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SetupEffectTest {

    /**
     * we are testing if a setup effect does what it is supposed to do when called
     */
    @Test
    void effectSetupForStudents() {
        CharacterCard characterCardTestForStudents = new CharacterCard(CharacterCardsName.MONK,1);
        ArrayList<AtomicEffect> effects = new ArrayList<>();
        AtomicEffect setupEffectStudents = new SetupEffect();
        Bag bag =  new Bag();
        setupEffectStudents.effect(4,bag , characterCardTestForStudents, Movable.STUDENT, RealmColors.RED);   //The colors have to be randomized, in order to accomplish this we have to chance the draw method in bag and add a RANDOM_COLOR in RealColor enum
        assertEquals(126,bag.getNumberOfStudents());
        assertEquals(4, characterCardTestForStudents.getNumberOfStudents());
        effects.add(setupEffectStudents);
        characterCardTestForStudents.setEffect(effects);
        assertEquals(effects,characterCardTestForStudents.getEffect());
    }

}
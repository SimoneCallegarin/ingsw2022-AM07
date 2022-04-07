package it.polimi.ingsw.Model.CharacterCards;

import it.polimi.ingsw.Model.Enumeration.Movable;
import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Model.GameTableObjects.Bag;
import it.polimi.ingsw.Model.GameTableObjects.IsleManager;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MovementEffectTest {

    /**
     * we are testing if a movement effect does what it is supposed to do when given two students manager in input
     */
    @Test
    void effect() {
        CharacterCard characterCardTestForStudents = new CharacterCard(CharacterCardsName.MONK,1);
        ArrayList<AtomicEffect> effects = new ArrayList<>();
        AtomicEffect movementEffectStudents1 = new MovementEffect();
        AtomicEffect movementEffectStudents2 = new MovementEffect();
        IsleManager isleManager = new IsleManager();
        Bag bag = new Bag();
        characterCardTestForStudents.addStudent(RealmColors.RED);
        movementEffectStudents1.effect(1,characterCardTestForStudents,isleManager.getIsle(0),Movable.STUDENT,RealmColors.RED);
        assertEquals(0,characterCardTestForStudents.getNumberOfStudents());
        assertEquals(1,isleManager.getIsle(0).getNumberOfStudents());
        movementEffectStudents2.effect(1,bag,characterCardTestForStudents,Movable.STUDENT,RealmColors.RED);
        assertEquals(1,characterCardTestForStudents.getNumberOfStudents());
        assertEquals(129,bag.getNumberOfStudents());
        effects.add(movementEffectStudents1);
        effects.add(movementEffectStudents2);
        characterCardTestForStudents.setEffect(effects);
        assertEquals(effects,characterCardTestForStudents.getEffect());
    }
}
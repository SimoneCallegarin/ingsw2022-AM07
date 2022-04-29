package it.polimi.ingsw.Model.CharacterCards;

import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Model.GameTableObjects.Bag;
import it.polimi.ingsw.Model.GameTableObjects.IsleManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MovementEffectTest {

    /**
     * we are testing if a movement effect does what it is supposed to do when given two students manager in input
     */
    @Test
    void effect() {
        CharacterCard characterCardTestForStudents = new CharacterCard(CharacterCardsName.MONK);
        StudentMovementEffect movementEffectStudents1 = new StudentMovementEffect();
        StudentMovementEffect movementEffectStudents2 = new StudentMovementEffect();
        IsleManager isleManager = new IsleManager();
        Bag bag = new Bag();
        bag.fillBag();
        characterCardTestForStudents.addStudent(RealmColors.YELLOW);
        movementEffectStudents1.effect(characterCardTestForStudents,isleManager.getIsle(0), ColorsForEffects.SELECT, RealmColors.YELLOW, null);
        assertEquals(0,characterCardTestForStudents.getNumberOfStudents());
        assertEquals(1,isleManager.getIsle(0).getNumberOfStudents());
        movementEffectStudents2.effect(bag,characterCardTestForStudents, ColorsForEffects.RANDOM, RealmColors.YELLOW, null);
        assertEquals(1,characterCardTestForStudents.getNumberOfStudents());
        assertEquals(119,bag.getNumberOfStudents());
    }
}
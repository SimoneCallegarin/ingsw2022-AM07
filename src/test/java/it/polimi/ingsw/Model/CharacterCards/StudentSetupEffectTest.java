package it.polimi.ingsw.Model.CharacterCards;

import it.polimi.ingsw.Model.Enumeration.GameMode;
import it.polimi.ingsw.Model.Enumeration.Squads;
import it.polimi.ingsw.Model.GameTableObjects.Bag;
import it.polimi.ingsw.Model.Player.Player;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentSetupEffectTest {

    /**
     * we are testing if a setup effect does what it is supposed to do when called
     */
    @Test
    void effectSetupForStudents() {
        CharacterCard characterCardTestForStudents = new CharacterCard(CharacterCardsName.MONK);
        Player player = new Player("simone", 4, 0, Squads.SQUAD1, GameMode.EXPERT);

        StudentSetupEffect studentSetupEffectStudents = new StudentSetupEffect();
        Bag bag =  new Bag();
        bag.fillBag();
        studentSetupEffectStudents.effect(bag , characterCardTestForStudents, ColorsForEffects.RANDOM, player);
        studentSetupEffectStudents.effect(bag , characterCardTestForStudents, ColorsForEffects.RANDOM, player);
        studentSetupEffectStudents.effect(bag , characterCardTestForStudents, ColorsForEffects.RANDOM, player);
        studentSetupEffectStudents.effect(bag , characterCardTestForStudents, ColorsForEffects.RANDOM, player);
        assertEquals(116,bag.getNumberOfStudents());
        assertEquals(4, characterCardTestForStudents.getNumberOfStudents());
    }

}
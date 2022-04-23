package it.polimi.ingsw.Model.CharacterCards;

import it.polimi.ingsw.Model.Interface.StudentManager;

public class StudentSetupEffect {

    /**
     * effect that permits to set up all the students that a card need to have at the start of the game
     * it uses the StudentMovementEffect to put the students on the character card
     * @param from will always be set to the bag
     * @param to will always be set to the character card
     * @param color will always be set to RANDOM
     */
    public void effect(StudentManager from, StudentManager to, ColorsForEffects color) {
        StudentMovementEffect studentMovementEffect = new StudentMovementEffect();
        studentMovementEffect.effect(from, to, color, null, null);
    }

}
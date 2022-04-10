package it.polimi.ingsw.Model.CharacterCards;

import it.polimi.ingsw.Model.Interface.StudentManager;
import it.polimi.ingsw.Model.Player.Player;

public class StudentSetupEffect {

    /**
     * effect that permits to set up all the students that a card need to have at the start of the game
     * it uses the StudentMovementEffect to put the students on the character card
     * @param times the number of students that have to be put on the character card
     * @param from will always be set to the bag
     * @param to will always be set to the character card
     * @param color will always be set to RANDOM
     */
    public void effect(int times, StudentManager from, StudentManager to, ColorsForEffects color, Player player) {
            StudentMovementEffect studentMovementEffect = new StudentMovementEffect();
            studentMovementEffect.effect(times, from, to, color, player);
    }

}
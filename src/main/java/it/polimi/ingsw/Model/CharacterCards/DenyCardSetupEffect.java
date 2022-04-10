package it.polimi.ingsw.Model.CharacterCards;

import it.polimi.ingsw.Model.Interface.DenyCardManager;

public class DenyCardSetupEffect {

    /**
     * effect that permits to set up all deny cards that a card need to have at the start of the game
     * it uses the DenyCardMovementEffect to put deny cards on the character card
     * @param times the number of deny cards that have to be put on the character card
     * @param from will not be used, always set to null
     * @param to will always be set to the character card
     * @param color will always be set to NONE
     */
    public void effect(int times, DenyCardManager from, DenyCardManager to, ColorsForEffects color) {
        DenyCardMovementEffect denyCardMovementEffect = new DenyCardMovementEffect();
        denyCardMovementEffect.effect(times,null,to,color);
    }
}

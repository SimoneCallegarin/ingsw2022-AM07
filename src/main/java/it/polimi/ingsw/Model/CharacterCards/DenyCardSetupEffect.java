package it.polimi.ingsw.Model.CharacterCards;

import it.polimi.ingsw.Model.Interface.DenyCardManager;

public class DenyCardSetupEffect {

    /**
     * effect that permits to set up all deny cards that a card need to have at the start of the game
     * it uses the DenyCardMovementEffect to put deny cards on the character card
     * @param from will not be used, always set to null
     * @param to will always be set to the character card
     */
    public void effect(DenyCardManager from, DenyCardManager to) {
        DenyCardMovementEffect denyCardMovementEffect = new DenyCardMovementEffect();
        denyCardMovementEffect.effect(from,to);
    }
}

package it.polimi.ingsw.Model.CharacterCards;

import it.polimi.ingsw.Model.Interface.DenyCardManager;

public class DenyCardMovementEffect {

    /**
     * Effect that permits to move a denyCard from a DenyCardsManager to another
     * @param from the DenyCardsManager where is removed the denyCard
     * @param to the DenyCardsManager where is added the denyCard
     * @param color will be set to NONE every time we use this effect
     */
    public void effect(DenyCardManager from, DenyCardManager to, ColorsForEffects color) {
            to.addDenyCard();
            from.removeDenyCard();
    }
}

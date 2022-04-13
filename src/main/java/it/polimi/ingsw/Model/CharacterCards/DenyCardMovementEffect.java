package it.polimi.ingsw.Model.CharacterCards;

import it.polimi.ingsw.Model.Interface.DenyCardManager;

public class DenyCardMovementEffect {

    /**
     * Effect that permits to move a denyCard from a DenyCardsManager to another
     * @param times the number of times this exact movement has to be done
     * @param from the DenyCardsManager where is removed the denyCard
     * @param to the DenyCardsManager where is added the denyCard
     * @param color will be set to NONE every time we use this effect
     */
    public void effect(int times, DenyCardManager from, DenyCardManager to, ColorsForEffects color) {
        for (int i = 0; i < times; i++) {
            to.addDenyCard();
            from.removeDenyCard();
        }
    }
}

package it.polimi.ingsw.Model.CharacterCards;

import it.polimi.ingsw.Model.Interface.DenyCardManager;

/**
 * Handle the movement of the deny card from a DenyCardsManager to another.
 */
class DenyCardMovementEffect {

    /**
     * Effect that permits to move a denyCard from a DenyCardsManager to another.
     * @param from the DenyCardsManager where is removed the denyCard.
     * @param to the DenyCardsManager where is added the denyCard.
     */
    public void effect(DenyCardManager from, DenyCardManager to) {
            to.addDenyCard();
            from.removeDenyCard();
    }
}

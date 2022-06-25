package it.polimi.ingsw.Model.CharacterCards;

import it.polimi.ingsw.Model.GameTableObjects.GameTable;

/**
 * The factory that permits to set up the character cards that need to be set up at the start of the game.
 */
public class EffectSetupFactory {

    /**
     * Prepares a playable character card that needs a setup.
     * @param gameTable the game table that contains the playable character cards.
     * @param characterCard the character card that needs the setup.
     */
    public void getEffect(GameTable gameTable, CharacterCard characterCard){

        StudentMovementEffect studentSetupEffect = new StudentMovementEffect();
        DenyCardMovementEffect denyCardSetupEffect = new DenyCardMovementEffect();

        switch (characterCard.getCharacterCardName()) {
            case MONK, SPOILED_PRINCESS -> {
                for(int i=0; i<4; i++)
                    studentSetupEffect.effect(gameTable.getBag(), characterCard, ColorsForEffects.RANDOM, null,null);
            }
            case GRANDMA_HERBS -> {
                for(int i=0; i<4; i++)
                    denyCardSetupEffect.effect(gameTable, characterCard);
            }
            case JESTER -> {
                for(int i=0; i<6; i++)
                    studentSetupEffect.effect(gameTable.getBag(), characterCard, ColorsForEffects.RANDOM, null, null);
            }
        }
    }

}

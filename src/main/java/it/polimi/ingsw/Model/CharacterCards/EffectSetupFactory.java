package it.polimi.ingsw.Model.CharacterCards;

import it.polimi.ingsw.Model.GameTableObjects.GameTable;
import it.polimi.ingsw.Model.Player.Player;

/**
 * this is the factory that permits to set up the character cards that need to be set up at the start of the game
 */
public class EffectSetupFactory {

        public EffectSetupFactory(){}

        public void getEffect (Player player, GameTable gameTable, CharacterCard characterCard){

            StudentSetupEffect studentSetupEffect = new StudentSetupEffect();
            DenyCardSetupEffect denyCardSetupEffect = new DenyCardSetupEffect();

            switch (characterCard.getCharacterCardName()) {
                case MONK, SPOILED_PRINCESS -> studentSetupEffect.effect(4, gameTable.getBag(), characterCard, ColorsForEffects.RANDOM, player);
                case GRANDMA_HERBS -> denyCardSetupEffect.effect(4, gameTable, characterCard, ColorsForEffects.NONE);
                case JESTER -> studentSetupEffect.effect(6, gameTable.getBag(), characterCard, ColorsForEffects.RANDOM, player);
            }
        }

}

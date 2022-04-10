package it.polimi.ingsw.Model.CharacterCards;

import it.polimi.ingsw.Model.GameTableObjects.Bag;
import it.polimi.ingsw.Model.GameTableObjects.GameTable;
import it.polimi.ingsw.Model.Player.Player;

import java.util.List;

public class EffectFactory {

    public EffectFactory(){}

    public void getEffect (Player player, GameTable gameTable, CharacterCard characterCard){

        List effect = null;
        StudentMovementEffect studentMovementEffect = new StudentMovementEffect();
        DenyCardMovementEffect denyCardMovementEffect = new DenyCardMovementEffect();
        StudentSetupEffect studentSetupEffect = new StudentSetupEffect();
        DenyCardSetupEffect denyCardSetupEffect = new DenyCardSetupEffect();

        switch (characterCard.getCharacterCardName()){
            case MONK:

                studentSetupEffect.effect(4,gameTable.getBag(),characterCard,ColorsForEffects.RANDOM, player);
                effect.add(studentSetupEffect);
                studentMovementEffect.effect(1,characterCard,gameTable.getIsleManager().getIsle(player.selectIsleId(0)),ColorsForEffects.SELECT,player);
                effect.add(studentMovementEffect);
                studentMovementEffect.effect(1,gameTable.getBag(),characterCard,ColorsForEffects.RANDOM,player);
                effect.add(studentMovementEffect);

                break;
            case FARMER:
                break;
            case HERALD:
                break;
            case MAGICAL_LETTER_CARRIER:
                break;
            case GRANDMA_HERBS:
                break;
            case CENTAUR:
                break;
            case JESTER:
                break;
            case KNIGHT:
                break;
            case FUNGIST:
                break;
            case MINSTREL:
                break;
            case SPOILED_PRINCESS:
                break;
            case THIEF:
                break;
        }
    }
}

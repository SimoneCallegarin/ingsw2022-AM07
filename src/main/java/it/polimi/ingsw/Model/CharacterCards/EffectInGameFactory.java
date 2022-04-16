package it.polimi.ingsw.Model.CharacterCards;

import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Model.GameTableObjects.GameTable;
import it.polimi.ingsw.Model.Player.Player;

import java.util.ArrayList;

/**
 * this is the factory that permits to activate character cards effect that takes place during the game
 */
public class EffectInGameFactory {

    public EffectInGameFactory(){}

    public void getEffect (CharacterCard characterCard, ArrayList<Player> players, GameTable gameTable, Player player, ColorsForEffects color, RealmColors colorFrom, RealmColors colorTo, int value){

        StudentMovementEffect studentMovementEffect = new StudentMovementEffect();
        DenyCardMovementEffect denyCardMovementEffect = new DenyCardMovementEffect();

        switch (characterCard.getCharacterCardName()){
            case MONK:
                studentMovementEffect.effect(characterCard,gameTable.getIsleManager().getIsle(player.selectIsleId(value)),ColorsForEffects.SELECT,colorFrom, null, player);
                studentMovementEffect.effect(gameTable.getBag(),characterCard,ColorsForEffects.RANDOM,null, null, player);
                break;

            case FARMER:
                //Recalculate
                break;

            case HERALD:
                //Recalculate
                break;

            case MAGICAL_LETTER_CARRIER:
                //Recalculate
                break;
            case GRANDMA_HERBS:
                denyCardMovementEffect.effect(characterCard,gameTable.getIsleManager().getIsle(value),ColorsForEffects.NONE);
                break;

            case CENTAUR:
                //Recalculate
                break;

            case JESTER:
                studentMovementEffect.effect(characterCard,player.getDashboard().getEntrance(),ColorsForEffects.SELECT,colorFrom, colorTo, player);
                break;

            case KNIGHT:
                //Recalculate
                break;

            case FUNGIST:
                //Recalculate
                break;

            case MINSTREL:
                studentMovementEffect.effect(player.getDashboard().getDiningRoom(),player.getDashboard().getEntrance(),ColorsForEffects.SELECT,colorFrom, colorTo, player);
                break;

            case SPOILED_PRINCESS:
                studentMovementEffect.effect(characterCard,player.getDashboard().getDiningRoom(),ColorsForEffects.SELECT,colorFrom, null, player);
                studentMovementEffect.effect(gameTable.getBag(),characterCard,ColorsForEffects.RANDOM,null,null, player);
                break;

            case THIEF:
                for (Player item : players)
                    for (int j = 0; j < 3; j++) {
                        if (item.getDashboard().getDiningRoom().getStudentsByColor(RealmColors.YELLOW) > 0)
                            studentMovementEffect.effect(item.getDashboard().getDiningRoom(), gameTable.getBag(), ColorsForEffects.SELECT, colorFrom, null, player);
                    }
                break;

        }
    }
}

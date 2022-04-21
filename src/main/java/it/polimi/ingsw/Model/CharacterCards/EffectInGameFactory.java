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

    /**
     * this method will permit activating part of the effect that characterise a character card
     * @param characterCard the character card played
     * @param players the list of players playing the game
     * @param gameTable the game table where the game takes place
     * @param player the player that played the character card
     * @param color the type of color it will be used
     *      NONE: refers to deny cards and for any recalculate effect that doesn't need a color because it doesn't pick students
     *      RANDOM: refers to an extraction of students from the bag that is randomized and doesn't need to know a color first
     *      SELECT: refers to the fact that when calling an effect, it needs to know from the user what is the color of the students it needs to move
     * @param colorFrom it's the color of the student that have to be moved from a certain student manager
     *                  it will be set to null when it isn't required
     * @param colorTo it's the color of the student that have to be moved to a certain student manager
     *                it will be set to null when it isn't required
     * @param value it represents many values
     */
    public void getEffect (CharacterCard characterCard, ArrayList<Player> players, GameTable gameTable, Player player, ColorsForEffects color, RealmColors colorFrom, RealmColors colorTo, int value){

        StudentMovementEffect studentMovementEffect = new StudentMovementEffect();
        DenyCardMovementEffect denyCardMovementEffect = new DenyCardMovementEffect();

        switch (characterCard.getCharacterCardName()){
            case MONK:
                studentMovementEffect.effect(characterCard,gameTable.getIsleManager().getIsle(player.selectIsleId(value)),ColorsForEffects.SELECT,colorFrom, null, player);
                studentMovementEffect.effect(gameTable.getBag(),characterCard,ColorsForEffects.RANDOM,null, null, player);
                break;

            case FARMER:
                for(Player p : players){
                    for(RealmColors c : RealmColors.values()){
                        if(p.getDashboard().getDiningRoom().getProfessorByColor(c)==1)
                            if(player.getDashboard().getDiningRoom().getStudentsByColor(c)==p.getDashboard().getDiningRoom().getStudentsByColor(c)){
                                p.getDashboard().getDiningRoom().removeStudent(c);
                                player.getDashboard().getDiningRoom().addProfessor(c);
                            }
                    }
                }
                break;

            case HERALD:
                //Recalculate
                break;

            case MAGICAL_LETTER_CARRIER:
                player.getDiscardPile().increaseMnMovement();
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

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

    public void getEffect (CharacterCard characterCard, ArrayList<Player> players, GameTable gameTable, Player player){

        StudentMovementEffect studentMovementEffect = new StudentMovementEffect();
        DenyCardMovementEffect denyCardMovementEffect = new DenyCardMovementEffect();

        int times;

        switch (characterCard.getCharacterCardName()){
            case MONK:
                studentMovementEffect.effect(1,characterCard,gameTable.getIsleManager().getIsle(player.selectIsleId(0)),ColorsForEffects.SELECT,player);
                studentMovementEffect.effect(1,gameTable.getBag(),characterCard,ColorsForEffects.RANDOM,player);
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
                denyCardMovementEffect.effect(1,characterCard,gameTable.getIsleManager().getIsle(0),ColorsForEffects.NONE);
                break;

            case CENTAUR:
                //Recalculate
                break;

            case JESTER:
                times = player.selectNumberOfStudentsToMove(characterCard,3);       //3 will be substituted with the view and the controller when implemented
                for(int i=0; i<times; i++){
                    studentMovementEffect.effect(1,player.getDashboard().getEntrance(),characterCard,ColorsForEffects.SELECT,player);
                    studentMovementEffect.effect(1,characterCard,player.getDashboard().getEntrance(),ColorsForEffects.SELECT,player);
                }
                break;

            case KNIGHT:
                //Recalculate
                break;

            case FUNGIST:
                //Recalculate
                break;

            case MINSTREL:
                times = player.selectNumberOfStudentsToMove(characterCard,2);       //3 will be substituted with the view and the controller when implemented
                studentMovementEffect.effect(times,player.getDashboard().getDiningRoom(),player.getDashboard().getEntrance(),ColorsForEffects.SELECT,player);
                studentMovementEffect.effect(times,player.getDashboard().getEntrance(),player.getDashboard().getDiningRoom(),ColorsForEffects.SELECT,player);
                break;

            case SPOILED_PRINCESS:
                studentMovementEffect.effect(1,characterCard,player.getDashboard().getDiningRoom(),ColorsForEffects.SELECT,player);
                studentMovementEffect.effect(1,gameTable.getBag(),characterCard,ColorsForEffects.RANDOM,player);
                break;

            case THIEF:
                RealmColors color = player.selectColor(gameTable.getBag(), RealmColors.YELLOW); //va generalizzato con un nuovo metodo!
                for(int i=0; i<players.size(); i++)
                    for (int j=0; j<3; j++){
                        if(players.get(i).getDashboard().getDiningRoom().getStudentsByColor(color)>0)
                            studentMovementEffect.effect(1,players.get(i).getDashboard().getDiningRoom(),gameTable.getBag(),ColorsForEffects.SELECT,player);
                    }
                break;

        }
    }
}

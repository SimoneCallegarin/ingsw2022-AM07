package it.polimi.ingsw.Model.CharacterCards;

import it.polimi.ingsw.Model.Enumeration.GameMode;
import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.Player.Player;

/**
 * this is the factory that permits to activate character cards effect that takes place during the game
 */
public class EffectInGameFactory {

    public EffectInGameFactory(){}

    /**
     * this method will permit activating part of the effect that characterise a character card
     * @param characterCard the character card played
     * @param game reference to all the objects that compose the game
     * @param player the player that played the character card
     * @param colorFrom it's the color of the student that have to be moved from a certain student manager
*                  it will be set to null when it isn't required
     * @param colorTo it's the color of the student that have to be moved to a certain student manager
*                it will be set to null when it isn't required
     * @param value it represents many values
     */
    public void getEffect(CharacterCard characterCard, Game game, Player player, RealmColors colorFrom, RealmColors colorTo, int value){

        StudentMovementEffect studentMovementEffect = new StudentMovementEffect();
        DenyCardMovementEffect denyCardMovementEffect = new DenyCardMovementEffect();

        switch (characterCard.getCharacterCardName()){
            case MONK:
                studentMovementEffect.effect(characterCard,game.getGameTable().getIsleManager().getIsle(player.selectIsleId(value)),ColorsForEffects.SELECT,colorFrom, null);
                studentMovementEffect.effect(game.getGameTable().getBag(),characterCard,ColorsForEffects.RANDOM,null, null);
                break;

            case FARMER:
                for(Player p : game.getPlayers()){
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
                game.checkUpdateInfluence(value);
                game.checkEndGame();
                break;

            case MAGICAL_LETTER_CARRIER:
                player.getDiscardPile().increaseMnMovement();
                break;

            case GRANDMA_HERBS:
                denyCardMovementEffect.effect(characterCard,game.getGameTable().getIsleManager().getIsle(value));
                break;

            case CENTAUR:
                game.getGameTable().getIsleManager().getIsle(value).setCentaur(true);
                break;

            case JESTER:
                studentMovementEffect.effect(characterCard,player.getDashboard().getEntrance(),ColorsForEffects.SELECT,colorFrom, colorTo);
                break;

            case KNIGHT:
                player.setKnight(true);
                break;

            case FUNGIST:
                // yet to be implemented
                break;

            case MINSTREL:
                studentMovementEffect.effect(player.getDashboard().getDiningRoom(),player.getDashboard().getEntrance(),ColorsForEffects.SELECT,colorFrom, colorTo);
                if (game.getGameMode() == GameMode.EXPERT && player.getDashboard().getDiningRoom().getStudentsByColor(colorTo)%3 == 0){
                    game.getGameTable().studentInMoneyPosition();
                    player.gainMoney();
                }
                break;

            case SPOILED_PRINCESS:
                studentMovementEffect.effect(characterCard,player.getDashboard().getDiningRoom(),ColorsForEffects.SELECT,colorFrom, null);
                studentMovementEffect.effect(game.getGameTable().getBag(),characterCard,ColorsForEffects.RANDOM,null,null);
                if (game.getGameMode() == GameMode.EXPERT && player.getDashboard().getDiningRoom().getStudentsByColor(colorFrom)%3 == 0){
                    game.getGameTable().studentInMoneyPosition();
                    player.gainMoney();
                }
                break;

            case THIEF:
                for (Player p : game.getPlayers())
                    for (int j = 0; j < 3; j++) {
                        if (p.getDashboard().getDiningRoom().getStudentsByColor(RealmColors.YELLOW) > 0)
                            studentMovementEffect.effect(p.getDashboard().getDiningRoom(), game.getGameTable().getBag(), ColorsForEffects.SELECT, colorFrom, null);
                    }
                break;

        }
    }
}
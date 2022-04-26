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
     * @param value1 it's the color of the student that has to be moved from a certain student manager
     *               it will be set to null when it isn't required
     * @param value2 it can be the color of the student that has to be moved to a certain student manager
     *               it can also be to the index of a certain isle
     *               it will be set to null when it isn't required
     */
    public void getEffect(CharacterCard characterCard, Game game, Player player, int value1, int value2){

        StudentMovementEffect studentMovementEffect = new StudentMovementEffect();
        DenyCardMovementEffect denyCardMovementEffect = new DenyCardMovementEffect();

        switch (characterCard.getCharacterCardName()){
            case MONK:
                studentMovementEffect.effect(characterCard,game.getGameTable().getIsleManager().getIsle(player.selectIsleId(value2)),ColorsForEffects.SELECT,RealmColors.getColor(value1), RealmColors.getColor(value2));
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
                game.checkUpdateInfluence(value2);
                game.checkEndGame();
                break;

            case MAGICAL_LETTER_CARRIER:
                player.getDiscardPile().increaseMnMovement();
                break;

            case GRANDMA_HERBS:
                denyCardMovementEffect.effect(characterCard,game.getGameTable().getIsleManager().getIsle(value2));
                break;

            case CENTAUR:
                game.getGameTable().getIsleManager().getIsle(value2).setCentaur(true);
                break;

            case JESTER:
                studentMovementEffect.effect(characterCard,player.getDashboard().getEntrance(),ColorsForEffects.SELECT,RealmColors.getColor(value1), RealmColors.getColor(value2));
                break;

            case KNIGHT:
                player.setKnight(true);
                break;

            case FUNGIST:
                // yet to be implemented
                break;

            case MINSTREL:
                studentMovementEffect.effect(player.getDashboard().getDiningRoom(),player.getDashboard().getEntrance(),ColorsForEffects.SELECT,RealmColors.getColor(value1), RealmColors.getColor(value2));
                if (game.getGameMode() == GameMode.EXPERT && player.getDashboard().getDiningRoom().getStudentsByColor(RealmColors.getColor(value2))%3 == 0){
                    game.getGameTable().studentInMoneyPosition();
                    player.gainMoney();
                }
                break;

            case SPOILED_PRINCESS:
                studentMovementEffect.effect(characterCard,player.getDashboard().getDiningRoom(),ColorsForEffects.SELECT,RealmColors.getColor(value1), null);
                studentMovementEffect.effect(game.getGameTable().getBag(),characterCard,ColorsForEffects.RANDOM,null,null);
                if (game.getGameMode() == GameMode.EXPERT && player.getDashboard().getDiningRoom().getStudentsByColor(RealmColors.getColor(value1))%3 == 0){
                    game.getGameTable().studentInMoneyPosition();
                    player.gainMoney();
                }
                break;

            case THIEF:
                for (Player p : game.getPlayers())
                    for (int j = 0; j < 3; j++) {
                        if (p.getDashboard().getDiningRoom().getStudentsByColor(RealmColors.getColor(value1)) > 0)
                            studentMovementEffect.effect(p.getDashboard().getDiningRoom(), game.getGameTable().getBag(), ColorsForEffects.SELECT, RealmColors.getColor(value1), null);
                    }
                break;

        }
    }
}
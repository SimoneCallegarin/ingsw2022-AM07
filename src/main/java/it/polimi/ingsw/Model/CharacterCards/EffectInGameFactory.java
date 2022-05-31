package it.polimi.ingsw.Model.CharacterCards;

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
     * @param value1 it can be the color of the student that has to be moved from a certain student manager
     *               it can also be to the index of a certain isle
     *               it will be set to -1 when it isn't required
     * @param value2 it's the color of the student that has to be moved to a certain student manager
     *               it will be set to -1 when it isn't required
     */
    public void getEffect(CharacterCard characterCard, Game game, Player player, int value1, int value2){

        StudentMovementEffect studentMovementEffect = new StudentMovementEffect();
        DenyCardMovementEffect denyCardMovementEffect = new DenyCardMovementEffect();

        RealmColors color1 = RealmColors.getColor(value1);
        RealmColors color2 = RealmColors.getColor(value2);

        /*
         KNIGHT and CENTAUR character cards are directly managed by the class ISLE,
         in the method that get changed by their activation
         ISLE -> getInfluence();
        */
        switch (characterCard.getCharacterCardName()){
            case MONK:
                studentMovementEffect.effect(characterCard,game.getGameTable().getIsleManager().getIsle(player.selectIsleId(value2)),ColorsForEffects.SELECT,color1,color2);
                studentMovementEffect.effect(game.getGameTable().getBag(),characterCard,ColorsForEffects.RANDOM,color1, color2);
                game.setActionPhase(game.getLastActionPhase());
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
                game.setActionPhase(game.getLastActionPhase());
                break;

            case HERALD:
                game.checkUpdateInfluence(value2);      // put this in another class.
                //game.checkEndGame();
                game.setActionPhase(game.getLastActionPhase());
                break;

            case MAGICAL_LETTER_CARRIER:
                player.getDiscardPile().increaseMnMovement();
                game.setActionPhase(game.getLastActionPhase());
                break;

            case GRANDMA_HERBS:
                denyCardMovementEffect.effect(characterCard,game.getGameTable().getIsleManager().getIsle(value1));
                game.setActionPhase(game.getLastActionPhase());
                break;

            case JESTER:
                studentMovementEffect.effect(characterCard,player.getDashboard().getEntrance(),ColorsForEffects.SELECT,color1, color2);
                game.increaseAtomicEffectCounter();
                if (game.getAtomicEffectCounter() == 3)
                    game.setActionPhase(game.getLastActionPhase());
                break;

            case FUNGIST:
                game.setColorForFungist(value1);
                game.setActionPhase(game.getLastActionPhase());
                break;

            case MINSTREL:
                studentMovementEffect.effect(player.getDashboard().getDiningRoom(),player.getDashboard().getEntrance(),ColorsForEffects.SELECT,color1,color2);
                // checking if the student is added in third, sixth or ninth position of the dining room
                if (game.isGameMode() && player.getDashboard().getDiningRoom().getStudentsByColor(color2)%3 == 0){
                    game.getGameTable().studentInMoneyPosition();
                    player.gainMoney();
                    game.notifyObserver(obs->obs.onMoneyUpdate(player.getDashboard().getIdDashboard(), player.getMoney(), game.getGameTable().getGeneralMoneyReserve()));
                }
                game.checkUpdateProfessor(player.getDashboard().getIdDashboard(), color1);
                game.checkUpdateProfessor(player.getDashboard().getIdDashboard(), color2);
                game.increaseAtomicEffectCounter();
                if (game.getAtomicEffectCounter() == 2)
                    game.setActionPhase(game.getLastActionPhase());
                break;

            case SPOILED_PRINCESS:
                studentMovementEffect.effect(characterCard,player.getDashboard().getDiningRoom(),ColorsForEffects.SELECT,color1, color2);
                studentMovementEffect.effect(game.getGameTable().getBag(),characterCard,ColorsForEffects.RANDOM,color1,color2);
                // checking if the student is added in third, sixth or ninth position of the dining room
                if (game.isGameMode() && player.getDashboard().getDiningRoom().getStudentsByColor(color1)%3 == 0){
                    game.getGameTable().studentInMoneyPosition();
                    player.gainMoney();
                    game.notifyObserver(obs->obs.onMoneyUpdate(player.getDashboard().getIdDashboard(), player.getMoney(), game.getGameTable().getGeneralMoneyReserve()));
                }
                game.checkUpdateProfessor(player.getDashboard().getIdDashboard(), color1);
                game.setActionPhase(game.getLastActionPhase());
                break;

            case THIEF:
                for (Player p : game.getPlayers())
                    for (int j = 0; j < 3; j++) {
                        if (p.getDashboard().getDiningRoom().getStudentsByColor(color1) > 0)
                            studentMovementEffect.effect(p.getDashboard().getDiningRoom(), game.getGameTable().getBag(), ColorsForEffects.SELECT, color1, color2);
                    }
                game.checkUpdateProfessor(player.getDashboard().getIdDashboard(), color1);
                game.setActionPhase(game.getLastActionPhase());
                break;
            case KNIGHT, CENTAUR:
                game.setActionPhase(game.getLastActionPhase());
                break;

        }
    }
}
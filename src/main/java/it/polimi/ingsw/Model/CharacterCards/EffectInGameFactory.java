package it.polimi.ingsw.Model.CharacterCards;

import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.Player.Player;

/**
 * Factory that permits to activate character cards effect that takes place during the game.
 */
public class EffectInGameFactory {

    /**
     * Permits to activate a part of the effect that characterises a certain character card.
     * @param characterCard the character card played.
     * @param game reference to all the objects that compose the game.
     * @param player the player that played the character card.
     * @param value1 it can be the color of the student that has to be moved from a certain student manager.
     *               It can also be to the index of a certain isle.
     *               It will be set to -1 when it isn't required.
     * @param value2 it's the color of the student that has to be moved to a certain student manager.
     *               It will be set to -1 when it isn't required.
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
        switch (characterCard.getCharacterCardName()) {
            case MONK -> {
                studentMovementEffect.effect(characterCard, game.getGameTable().getIsleManager().getIsle(value2), ColorsForEffects.SELECT, color1, null);
                studentMovementEffect.effect(game.getGameTable().getBag(), characterCard, ColorsForEffects.RANDOM, color1, color2);
                game.setActionPhase(game.getLastActionPhase());
            }
            case FARMER -> {
                for (Player p : game.getPlayers()) {
                    for (RealmColors c : RealmColors.values()) {
                        if (p.getDashboard().getDiningRoom().getProfessorByColor(c) == 1)
                            if (player.getDashboard().getDiningRoom().getStudentsByColor(c) == p.getDashboard().getDiningRoom().getStudentsByColor(c)) {
                                p.getDashboard().getDiningRoom().removeProfessor(c);
                                player.getDashboard().getDiningRoom().addProfessor(c);
                            }
                    }
                }
                game.setActionPhase(game.getLastActionPhase());
            }
            case HERALD -> {
                if (game.getGameTable().getIsleManager().getIsle(value1).getDenyCards() == 0)
                    game.checkUpdateInfluence(value1);
                else {
                    game.getGameTable().getIsleManager().getIsle(value1).removeDenyCard();
                    int grandmaIndex = 0;
                    for (int i=0; i<3; i++)
                        if(game.getGameTable().getCharacterCard(i).getCharacterCardName() == CharacterCardsName.GRANDMA_HERBS){
                            grandmaIndex = i;
                            break;
                        }
                    game.getGameTable().getCharacterCard(grandmaIndex).addDenyCard();
                    int finalGrandmaIndex = grandmaIndex;
                    game.notifyObserver(obs->obs.onDenyCard(finalGrandmaIndex, game.getGameTable().getCharacterCard(finalGrandmaIndex).getCost(), game.getGameTable().getCharacterCard(finalGrandmaIndex).getDenyCards(), game.getGameTable().getCharacterCard(finalGrandmaIndex).getStudentsHashMap(), value1, game.getGameTable().getIsleManager().getIsle(value1).getDenyCards()));
                }
                game.checkEndGame();
                game.setActionPhase(game.getLastActionPhase());
            }
            case MAGICAL_LETTER_CARRIER -> {
                player.getDiscardPile().increaseMnMovement();
                game.setActionPhase(game.getLastActionPhase());
            }
            case GRANDMA_HERBS -> {
                denyCardMovementEffect.effect(characterCard, game.getGameTable().getIsleManager().getIsle(value1));
                game.setActionPhase(game.getLastActionPhase());
            }
            case JESTER -> {
                studentMovementEffect.effect(characterCard, player.getDashboard().getEntrance(), ColorsForEffects.SELECT, color1, color2);
                game.increaseAtomicEffectCounter();
                if (game.getAtomicEffectCounter() == 3)
                    game.setActionPhase(game.getLastActionPhase());
            }
            case FUNGIST -> {
                game.setColorForFungist(value1);
                game.setActionPhase(game.getLastActionPhase());
            }
            case MINSTREL -> {
                studentMovementEffect.effect(player.getDashboard().getDiningRoom(), player.getDashboard().getEntrance(), ColorsForEffects.SELECT, color1, color2);
                game.checkStudentInMoneyPosition(player.getDashboard().getDashboardID(),color2);
                game.checkUpdateProfessor(player.getDashboard().getDashboardID(), color1);
                game.checkUpdateProfessor(player.getDashboard().getDashboardID(), color2);
                game.increaseAtomicEffectCounter();
                if (game.getAtomicEffectCounter() == 2)
                    game.setActionPhase(game.getLastActionPhase());
            }
            case SPOILED_PRINCESS -> {
                studentMovementEffect.effect(characterCard, player.getDashboard().getDiningRoom(), ColorsForEffects.SELECT, color1, color2);
                studentMovementEffect.effect(game.getGameTable().getBag(), characterCard, ColorsForEffects.RANDOM, color1, color2);
                game.checkStudentInMoneyPosition(player.getDashboard().getDashboardID(),color1);
                game.checkUpdateProfessor(player.getDashboard().getDashboardID(), color1);
                game.setActionPhase(game.getLastActionPhase());
            }
            case THIEF -> {
                for (Player p : game.getPlayers())
                    for (int j = 0; j < 3; j++) {
                        if (p.getDashboard().getDiningRoom().getStudentsByColor(color1) > 0)
                            studentMovementEffect.effect(p.getDashboard().getDiningRoom(), game.getGameTable().getBag(), ColorsForEffects.SELECT, color1, color2);
                    }
                game.checkUpdateProfessor(player.getDashboard().getDashboardID(), color1);
                game.setActionPhase(game.getLastActionPhase());
            }
            case KNIGHT, CENTAUR -> game.setActionPhase(game.getLastActionPhase());
        }
    }
}
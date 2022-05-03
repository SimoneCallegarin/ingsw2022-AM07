package it.polimi.ingsw.Model.CharacterCards;

import it.polimi.ingsw.Model.Enumeration.*;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Network.Messages.MessageType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * We are testing if each character card behaves correctly
 */
class GamePlaySomeCharacterCardsTest {

    Game game = new Game();

    /**
     * We are testing the behaviour of the MONK
     */
    @Test
    void play_MONK_CharacterCard() {
        game.addFirstPlayer("simone", true, 2);
        game.addAnotherPlayer("giacomo");
        game.getGameTable().setCharacterCards(CharacterCardsName.MONK);
        game.setGamePhase(GamePhases.ACTION_PHASE);
        game.currentActivePlayer = game.getPlayerByIndex(0).getOrder();
        // students:
        //  130 in the bag
        //  -10 in isle
        //  -7*2 in the entrances
        //  -3*2 in the clouds
        //  -4 in MONK
        //-------------------------
        //  = 96
        assertEquals(96,game.getGameTable().getBag().getNumberOfStudents());
        // 4 students randomly extracted from the bag now put on the character card
        assertEquals(4,game.getGameTable().getCharacterCard(0).getNumberOfStudents());

        //we add a yellow student in the character card (5 students on it now)
        game.getGameTable().getCharacterCard(0).addStudent(RealmColors.PINK);
        //we keep trace on how many students there are at the start of the game in the isle 0 (because there can be 1 or none)
        int studentsOnIsle0 = game.getGameTable().getIsleManager().getIsle(10).getNumberOfStudents();
        //we play the character card we want
        game.playCharacterCard(0,0);
        //and then the controller will activate the atomic effect of each card for the number of times needed
        game.activateAtomicEffect(0,0,1,10);

        // students on the isle 10
        assertEquals(studentsOnIsle0+1, game.getGameTable().getIsleManager().getIsle(10).getNumberOfStudents());
        // 96 students - 1 student extracted from the bag and put on the character card
        assertEquals(95,game.getGameTable().getBag().getNumberOfStudents());
        // 5 student on the character card (4 randomly extracted during set up + 1 extracted in game)
        assertEquals(5,game.getGameTable().getCharacterCard(0).getNumberOfStudents());
    }

    /**
     * We are testing the behaviour of the GRANDMA_HERBS
     */
    @Test
    void play_FARMER_CharacterCard(){
        game.addFirstPlayer("simone", true, 3);
        game.addAnotherPlayer("giacomo");
        game.addAnotherPlayer("filippo");
        game.getGameTable().setCharacterCards(CharacterCardsName.FARMER);

        game.getPlayerByIndex(0).getDashboard().getDiningRoom().addStudent(RealmColors.YELLOW);
        game.checkUpdateProfessor(0,RealmColors.YELLOW);
        game.getPlayerByIndex(0).getDashboard().getDiningRoom().addStudent(RealmColors.GREEN);
        game.checkUpdateProfessor(0,RealmColors.GREEN);
        game.getPlayerByIndex(0).getDashboard().getDiningRoom().addStudent(RealmColors.BLUE);
        game.checkUpdateProfessor(0,RealmColors.BLUE);
        assertEquals(1,game.getPlayerByIndex(0).getDashboard().getDiningRoom().getProfessorByColor(RealmColors.YELLOW));
        assertEquals(1,game.getPlayerByIndex(0).getDashboard().getDiningRoom().getProfessorByColor(RealmColors.GREEN));
        assertEquals(1,game.getPlayerByIndex(0).getDashboard().getDiningRoom().getProfessorByColor(RealmColors.BLUE));

        game.getPlayerByIndex(1).getDashboard().getDiningRoom().addStudent(RealmColors.RED);
        game.checkUpdateProfessor(1,RealmColors.RED);
        game.getPlayerByIndex(1).getDashboard().getDiningRoom().addStudent(RealmColors.RED);
        game.checkUpdateProfessor(1,RealmColors.RED);
        assertEquals(1,game.getPlayerByIndex(1).getDashboard().getDiningRoom().getProfessorByColor(RealmColors.RED));

        game.getPlayerByIndex(2).getDashboard().getDiningRoom().addStudent(RealmColors.PINK);
        game.checkUpdateProfessor(2,RealmColors.PINK);
        game.getPlayerByIndex(2).getDashboard().getDiningRoom().addStudent(RealmColors.YELLOW);
        game.checkUpdateProfessor(2,RealmColors.YELLOW);
        game.getPlayerByIndex(2).getDashboard().getDiningRoom().addStudent(RealmColors.RED);
        game.checkUpdateProfessor(2,RealmColors.RED);
        assertEquals(1,game.getPlayerByIndex(2).getDashboard().getDiningRoom().getProfessorByColor(RealmColors.PINK));

        //Students:
        //  simone: 1 YELLOW, 1 GREEN, 1 BLUE
        //  giacomo: 2 RED
        //  filippo: 1 PINK, 1 YELLOW, 1 RED

        game.setGamePhase(GamePhases.ACTION_PHASE);
        game.currentActivePlayer = game.getPlayerByIndex(2).getOrder();
        game.getPlayerByIndex(2).gainMoney();

        game.playCharacterCard(2,0);

        game.activateAtomicEffect(2,0,0,0);

        assertEquals(1,game.getPlayerByIndex(2).getDashboard().getDiningRoom().getProfessorByColor(RealmColors.YELLOW));
        assertEquals(1,game.getPlayerByIndex(2).getDashboard().getDiningRoom().getProfessorByColor(RealmColors.PINK));
        assertEquals(0,game.getPlayerByIndex(2).getDashboard().getDiningRoom().getProfessorByColor(RealmColors.RED));
        assertEquals(0,game.getPlayerByIndex(2).getDashboard().getDiningRoom().getProfessorByColor(RealmColors.BLUE));
        assertEquals(0,game.getPlayerByIndex(2).getDashboard().getDiningRoom().getProfessorByColor(RealmColors.GREEN));
    }

    /**
     * We are testing the behaviour of the HERALD
     */
    @Test
    void play_HERALD_CharacterCard(){
        game.addFirstPlayer("simone", true, 2);
        game.addAnotherPlayer("giacomo");
        game.getGameTable().setCharacterCards(CharacterCardsName.HERALD);

        //Player 0: 1 YELLOW student, 1 YELLOW professor
        game.getGameTable().getIsleManager().getIsle(8).addStudent(RealmColors.YELLOW);
        game.checkUpdateProfessor(0,RealmColors.YELLOW);
        //Player 1: 1 RED student, 1 RED professor
        game.getGameTable().getIsleManager().getIsle(8).addStudent(RealmColors.RED);
        game.checkUpdateProfessor(1,RealmColors.RED);

        while (game.getGameTable().getIsleManager().getIsle(8).getNumberOfStudents()!=0){
            for(RealmColors c : RealmColors.values()){
                if(game.getGameTable().getIsleManager().getIsle(8).getStudentsByColor(c)!=0)
                    game.getGameTable().getIsleManager().getIsle(8).removeStudent(c);
            }
        }
        //Isle 8: 1 YELLOW student
        game.getGameTable().getIsleManager().getIsle(8).addStudent(RealmColors.YELLOW);

        //Isle 8:
        //player 0 influence = 1
        //player 1 influence = 0
        game.checkUpdateInfluence(8);
        //Isle 8:
        //player 0 influence = 1 + 1(tower)
        //player 1 influence = 0
        assertEquals(TowerColors.WHITE,game.getGameTable().getIsleManager().getIsle(8).getTowersColor());

        //Isle 8: 3 RED students, 1 YELLOW student
        game.getGameTable().getIsleManager().getIsle(8).addStudent(RealmColors.RED);
        game.getGameTable().getIsleManager().getIsle(8).addStudent(RealmColors.RED);
        game.getGameTable().getIsleManager().getIsle(8).addStudent(RealmColors.RED);
        //Isle 8:
        //player 0 influence = 2
        //player 1 influence = 3

        game.setGamePhase(GamePhases.ACTION_PHASE);
        game.currentActivePlayer = game.getPlayerByIndex(1).getOrder();
        game.getPlayerByIndex(1).gainMoney();
        game.getPlayerByIndex(1).gainMoney();

        game.playCharacterCard(1,0);
        game.activateAtomicEffect(1,0,0,8);

        assertEquals(TowerColors.BLACK,game.getGameTable().getIsleManager().getIsle(8).getTowersColor());
    }

        /**
         * We are testing the behaviour of the MAGICAL_LETTER_CARRIER
         */
    @Test
    void play_MAGICAL_LETTER_CARRIER_CharacterCard(){
        game.addFirstPlayer("simone", true, 2);
        game.addAnotherPlayer("giacomo");
        game.getGameTable().setCharacterCards(CharacterCardsName.MAGICAL_LETTER_CARRIER);

        //setting the correct game phase
        game.setGamePhase(GamePhases.PLANNING_PHASE);
        game.planningPhase = PlanningPhases.ASSISTANT_CARD_PHASE;
        game.currentActivePlayer = game.getPlayerByIndex(0).getOrder();
        game.playAssistantCard(0,1);

        assertEquals(1,game.getPlayerByIndex(0).getDiscardPile().getMnMovement());

        game.setGamePhase(GamePhases.ACTION_PHASE);
        game.currentActivePlayer = game.getPlayerByIndex(0).getOrder();

        game.playCharacterCard(0,0);

        game.activateAtomicEffect(0,0,0,0);

        assertEquals(3,game.getPlayerByIndex(0).getDiscardPile().getMnMovement());
    }

    /**
     * We are testing the behaviour of the GRANDMA_HERBS
     */
    @Test
    void play_GRANDMA_HERBS_CharacterCard(){
        game.addFirstPlayer("simone", true, 2);
        game.addAnotherPlayer("giacomo");
        game.getGameTable().setCharacterCards(CharacterCardsName.GRANDMA_HERBS);
        game.setGamePhase(GamePhases.ACTION_PHASE);
        game.currentActivePlayer = game.getPlayerByIndex(0).getOrder();
        game.getPlayerByIndex(0).gainMoney();

        // 4 deny cards on the character card
        assertEquals(4,game.getGameTable().getCharacterCard(0).getDenyCards());
        // 0 deny cards on the isle 11 at the start of the game
        assertEquals(0,game.getGameTable().getIsleManager().getIsle(11).getDenyCards());


        //we play the character card we want
        game.playCharacterCard(0,0);
        //and then the controller will activate the atomic effect of each card for the number of times needed
        game.activateAtomicEffect(0,0,11,0);

        // 3 deny cards on the character card, one is removed and then put on the isle 11
        assertEquals(3,game.getGameTable().getCharacterCard(0).getDenyCards());
        // 1 deny card on the isle 11
        assertEquals(1,game.getGameTable().getIsleManager().getIsle(11).getDenyCards());

        game.setGamePhase(GamePhases.PLANNING_PHASE);
        game.planningPhase = PlanningPhases.ASSISTANT_CARD_PHASE;
        game.currentActivePlayer = game.getPlayerByIndex(0).getOrder();

        game.playAssistantCard(0,5);

        game.setGamePhase(GamePhases.ACTION_PHASE);
        game.actionPhase = ActionPhases.MOVE_MOTHER_NATURE;
        game.currentActivePlayer = game.getPlayerByIndex(0).getOrder();

        game.moveMotherNature(0,game.getGameTable().getIsleManager().getIsleWithMotherNatureIndex()+2);

        assertEquals(0,game.getGameTable().getIsleManager().getIsle(game.getGameTable().getIsleManager().getIsleWithMotherNatureIndex()).getDenyCards());
    }

    /**
     * We are testing the behaviour of the CENTAUR in a draw condition between two players
     */
    @Test
    void play_CENTAUR_Draw_CharacterCard(){
        game.addFirstPlayer("simone", true, 2);
        game.addAnotherPlayer("giacomo");

        game.getGameTable().setCharacterCards(CharacterCardsName.CENTAUR);

        //Player 0: 1 YELLOW student, 1 YELLOW professor
        game.getGameTable().getIsleManager().getIsle(8).addStudent(RealmColors.YELLOW);
        game.checkUpdateProfessor(0,RealmColors.YELLOW);
        //Player 1: 1 RED student, 1 RED professor
        game.getGameTable().getIsleManager().getIsle(8).addStudent(RealmColors.RED);
        game.checkUpdateProfessor(1,RealmColors.RED);

        while (game.getGameTable().getIsleManager().getIsle(8).getNumberOfStudents()!=0){
            for(RealmColors c : RealmColors.values()){
                if(game.getGameTable().getIsleManager().getIsle(8).getStudentsByColor(c)!=0)
                    game.getGameTable().getIsleManager().getIsle(8).removeStudent(c);
            }
        }
        //Isle 8: 1 YELLOW student
        game.getGameTable().getIsleManager().getIsle(8).addStudent(RealmColors.YELLOW);

        //Isle 8:
        //player 0 influence = 1
        //player 1 influence = 0
        game.checkUpdateInfluence(8);
        //Isle 8:
        //player 0 influence = 1 + 1(tower)
        //player 1 influence = 0
        assertEquals(TowerColors.WHITE,game.getGameTable().getIsleManager().getIsle(8).getTowersColor());

        //Isle 8: 1 RED student
        game.getGameTable().getIsleManager().getIsle(8).addStudent(RealmColors.RED);
        //Isle 8:
        //player 0 influence = 2
        //player 1 influence = 1

        game.setGamePhase(GamePhases.ACTION_PHASE);
        game.currentActivePlayer = game.getPlayerByIndex(1).getOrder();
        game.getPlayerByIndex(1).gainMoney();

        game.playCharacterCard(1,0);
        game.activateAtomicEffect(1,0,0,8);
        //Isle 8:
        //player 0 influence = 2
        //player 1 influence = 1
        // it's a DRAW
        assertEquals(TowerColors.WHITE,game.getGameTable().getIsleManager().getIsle(8).getTowersColor());

    }

    /**
     * We are testing the behaviour of the CENTAUR in a condition where between two players
     * the second conquers the isle using the centaur character card
     */
    @Test
    void play_CENTAUR_Win_CharacterCard(){
        game.addFirstPlayer("simone", true, 2);
        game.addAnotherPlayer("giacomo");

        game.getGameTable().setCharacterCards(CharacterCardsName.CENTAUR);

        //Player 0: 1 YELLOW student, 1 YELLOW professor
        game.getGameTable().getIsleManager().getIsle(8).addStudent(RealmColors.YELLOW);
        game.checkUpdateProfessor(0,RealmColors.YELLOW);
        //Player 1: 1 RED student, 1 RED professor
        game.getGameTable().getIsleManager().getIsle(8).addStudent(RealmColors.RED);
        game.checkUpdateProfessor(1,RealmColors.RED);

        while (game.getGameTable().getIsleManager().getIsle(8).getNumberOfStudents()!=0){
            for(RealmColors c : RealmColors.values()){
                if(game.getGameTable().getIsleManager().getIsle(8).getStudentsByColor(c)!=0)
                    game.getGameTable().getIsleManager().getIsle(8).removeStudent(c);
            }
        }
        //Isle 8: 1 YELLOW student
        game.getGameTable().getIsleManager().getIsle(8).addStudent(RealmColors.YELLOW);

        //Isle 8:
        //player 0 influence = 1
        //player 1 influence = 0
        game.checkUpdateInfluence(8);
        //Isle 8:
        //player 0 influence = 1 + 1(tower)
        //player 1 influence = 0
        assertEquals(TowerColors.WHITE,game.getGameTable().getIsleManager().getIsle(8).getTowersColor());

        //Isle 8: 1 RED student
        game.getGameTable().getIsleManager().getIsle(8).addStudent(RealmColors.RED);
        game.getGameTable().getIsleManager().getIsle(8).addStudent(RealmColors.RED);
        game.getGameTable().getIsleManager().getIsle(8).addStudent(RealmColors.RED);
        //Isle 8:
        //player 0 influence = 2
        //player 1 influence = 3
        game.setGamePhase(GamePhases.ACTION_PHASE);
        game.currentActivePlayer = game.getPlayerByIndex(1).getOrder();
        game.getPlayerByIndex(1).gainMoney();
        game.getPlayerByIndex(1).gainMoney();

        game.playCharacterCard(1,0);
        game.activateAtomicEffect(1,0,0,8);
        //Isle 8:
        //player 0 influence = 2
        //player 1 influence = 3
        game.checkUpdateInfluence(8);
        assertEquals(TowerColors.BLACK,game.getGameTable().getIsleManager().getIsle(8).getTowersColor());
    }

    /**
     * We are testing the behaviour of the JESTER
     */
    @Test
    void play_JESTER_CharacterCard(){             //check better the colors number and with more students!
        game.addFirstPlayer("simone", true, 2);
        game.addAnotherPlayer("giacomo");

        game.getGameTable().setCharacterCards(CharacterCardsName.JESTER);
        game.setGamePhase(GamePhases.ACTION_PHASE);
        game.currentActivePlayer = game.getPlayerByIndex(0).getOrder();

        int counter=0;

        // removing all 7 students from entrance
        for (RealmColors rc : RealmColors.values()) {
            int studentsOfSpecifiedColor = game.getPlayerByIndex(0).getDashboard().getEntrance().getStudentsByColor(rc);
            for (int i = 0; i < studentsOfSpecifiedColor; i++) {
                game.getPlayerByIndex(0).getDashboard().getEntrance().removeStudent(rc);
                counter++;
                if (counter == 7)
                    break;
            }
            if (counter == 7)
                break;
        }

        assertEquals(94, game.getGameTable().getBag().getNumberOfStudents());
        assertEquals(6, game.getGameTable().getCharacterCard(0).getNumberOfStudents());

        game.getGameTable().getCharacterCard(0).addStudent(RealmColors.BLUE);
        game.getGameTable().getCharacterCard(0).addStudent(RealmColors.YELLOW);
        game.getGameTable().getCharacterCard(0).addStudent(RealmColors.YELLOW);
        game.getPlayerByIndex(0).getDashboard().getEntrance().addStudent(RealmColors.GREEN);
        game.getPlayerByIndex(0).getDashboard().getEntrance().addStudent(RealmColors.PINK);
        game.getPlayerByIndex(0).getDashboard().getEntrance().addStudent(RealmColors.YELLOW);

        int numberOfBlueInCharacterCard = game.getGameTable().getCharacterCard(0).getStudentsByColor(RealmColors.BLUE);

        assertEquals(3, game.getPlayerByIndex(0).getDashboard().getEntrance().getNumberOfStudents());

        game.playCharacterCard(0,0);

        // the number of times this method is called is decided by the player, and it's defined by a controller invocation of this method
        game.activateAtomicEffect(0,0,2,4);
        game.activateAtomicEffect(0,0,1,0);

        assertEquals(3, game.getPlayerByIndex(0).getDashboard().getEntrance().getNumberOfStudents());
        assertEquals(9, game.getGameTable().getCharacterCard(0).getNumberOfStudents());

        assertEquals(numberOfBlueInCharacterCard-1,game.getGameTable().getCharacterCard(0).getStudentsByColor(RealmColors.BLUE));
    }

    /**
     * We are testing the behaviour of the KNIGHT
     */
    @Test
    void play_KNIGHT_CharacterCard() {
        game.addFirstPlayer("simone", true, 2);
        game.addAnotherPlayer("giacomo");

        game.getGameTable().setCharacterCards(CharacterCardsName.KNIGHT);
        game.getPlayerByIndex(0).gainMoney();

        game.getPlayerByIndex(1).getDashboard().getDiningRoom().addProfessor(RealmColors.YELLOW);

        //removing all students from isle 8:
        while (game.getGameTable().getIsleManager().getIsle(8).getNumberOfStudents()!=0){
            for(RealmColors c : RealmColors.values()){
                if(game.getGameTable().getIsleManager().getIsle(8).getStudentsByColor(c)!=0)
                    game.getGameTable().getIsleManager().getIsle(8).removeStudent(c);
            }
        }
        //Isle 8: 1 YELLOW student
        game.getGameTable().getIsleManager().getIsle(8).addStudent(RealmColors.YELLOW);
        //player 0 influence on isle 8 = 0
        //player 1 influence on isle 8 = 1
        assertEquals(0,game.getGameTable().getIsleManager().getIsle(8).getInfluence(game.getPlayerByIndex(0)));

        game.setGamePhase(GamePhases.ACTION_PHASE);
        game.currentActivePlayer = game.getPlayerByIndex(0).getOrder();

        game.playCharacterCard(0,0);
        //player 0 influence on isle 8 = 2
        //player 1 influence on isle 8 = 1
        game.activateAtomicEffect(0,0,0,0);
        assertEquals(2,game.getGameTable().getIsleManager().getIsle(8).getInfluence(game.getPlayerByIndex(0)));
        assertEquals(1,game.getGameTable().getIsleManager().getIsle(8).getInfluence(game.getPlayerByIndex(1)));

        game.checkUpdateInfluence(8);

        assertEquals(TowerColors.WHITE, game.getGameTable().getIsleManager().getIsle(8).getTowersColor());
        assertEquals(CharacterCardsName.KNIGHT,game.getPlayerByIndex(0).getCharacterCardPlayed());

    }

    /**
     * We are testing the behaviour of the FUNGIST
     */
    @Test
    void play_FUNGIST_CharacterCard() {
        game.addFirstPlayer("simone", true, 2);
        game.addAnotherPlayer("giacomo");

        game.getGameTable().setCharacterCards(CharacterCardsName.FUNGIST);
        game.getPlayerByIndex(1).gainMoney();
        game.getPlayerByIndex(1).gainMoney();

        game.getPlayerByIndex(0).getDashboard().getDiningRoom().addStudent(RealmColors.YELLOW);
        game.getPlayerByIndex(1).getDashboard().getDiningRoom().addStudent(RealmColors.RED);
        game.checkUpdateProfessor(0,RealmColors.YELLOW);
        game.checkUpdateProfessor(1,RealmColors.RED);

        //removing all students from isle 8:
        while (game.getGameTable().getIsleManager().getIsle(8).getNumberOfStudents()!=0){
            for(RealmColors c : RealmColors.values()){
                if(game.getGameTable().getIsleManager().getIsle(8).getStudentsByColor(c)!=0)
                    game.getGameTable().getIsleManager().getIsle(8).removeStudent(c);
            }
        }
        //Isle 8: 2 YELLOW student and 1 RED student
        game.getGameTable().getIsleManager().getIsle(8).addStudent(RealmColors.YELLOW);
        game.getGameTable().getIsleManager().getIsle(8).addStudent(RealmColors.YELLOW);
        game.getGameTable().getIsleManager().getIsle(8).addStudent(RealmColors.RED);
        // player 0 influence on isle 8 = 2
        // player 1 influence on isle 8 = 1
        assertEquals(2,game.getGameTable().getIsleManager().getIsle(8).getInfluence(game.getPlayerByIndex(0)));
        assertEquals(1,game.getGameTable().getIsleManager().getIsle(8).getInfluence(game.getPlayerByIndex(1)));

        game.setGamePhase(GamePhases.PLANNING_PHASE);
        game.planningPhase=PlanningPhases.ASSISTANT_CARD_PHASE;
        game.currentActivePlayer = game.getPlayerByIndex(0).getOrder();
        game.playAssistantCard(1,9);
        game.currentActivePlayer = game.getPlayerByIndex(1).getOrder();
        game.playAssistantCard(1,5);

        game.setGamePhase(GamePhases.ACTION_PHASE);
        game.actionPhase = ActionPhases.MOVE_MOTHER_NATURE;
        game.currentActivePlayer = game.getPlayerByIndex(1).getOrder();
        for(int i=0; i<12; i++)
            game.getGameTable().getIsleManager().getIsle(i).setMotherNature(false);
        game.getGameTable().getIsleManager().getIsle(7).setMotherNature(true);
        game.getGameTable().getIsleManager().setIsleWithMotherNatureIndex(7);
        assertTrue(game.getGameTable().getIsleManager().getIsle(7).getMotherNature());

        game.playCharacterCard(1,0);
        game.activateAtomicEffect(1,0,0,0);
        // player 0 influence on isle 8 = 0
        // player 1 influence on isle 8 = 1
        // value1 = 0 = YELLOW
        game.moveMotherNature(1,8);
        assertTrue(game.getGameTable().getIsleManager().getIsle(8).getMotherNature());
        assertEquals(TowerColors.BLACK, game.getGameTable().getIsleManager().getIsle(8).getTowersColor());

    }

    /**
     * We are testing the behaviour of the MINSTREL
     */
    @Test
    void play_MINSTREL_CharacterCard() {
        game.addFirstPlayer("simone", true, 2);
        game.addAnotherPlayer("giacomo");

        game.getGameTable().setCharacterCards(CharacterCardsName.MINSTREL);
        game.setGamePhase(GamePhases.ACTION_PHASE);
        game.currentActivePlayer = game.getPlayerByIndex(0).getOrder();

        int counter=0;

        // removing all 7 students from entrance
        for (RealmColors rc : RealmColors.values()) {
            int studentsOfSpecifiedColor = game.getPlayerByIndex(0).getDashboard().getEntrance().getStudentsByColor(rc);
            for (int i = 0; i < studentsOfSpecifiedColor; i++) {
                game.getPlayerByIndex(0).getDashboard().getEntrance().removeStudent(rc);
                counter++;
                if (counter == 7)
                    break;
            }
            if (counter == 7)
                break;
        }

        assertEquals(0, game.getPlayerByIndex(0).getDashboard().getEntrance().getNumberOfStudents());
        assertEquals(0, game.getPlayerByIndex(0).getDashboard().getDiningRoom().getNumberOfStudents());

        game.getPlayerByIndex(0).getDashboard().getDiningRoom().addStudent(RealmColors.BLUE);
        game.getPlayerByIndex(0).getDashboard().getDiningRoom().addStudent(RealmColors.RED);
        game.getPlayerByIndex(0).getDashboard().getDiningRoom().addStudent(RealmColors.YELLOW);
        game.getPlayerByIndex(0).getDashboard().getDiningRoom().addStudent(RealmColors.YELLOW);
        game.getPlayerByIndex(0).getDashboard().getDiningRoom().addStudent(RealmColors.YELLOW);
        game.getPlayerByIndex(0).getDashboard().getDiningRoom().addStudent(RealmColors.YELLOW);
        game.getPlayerByIndex(0).getDashboard().getDiningRoom().addStudent(RealmColors.YELLOW);

        game.getPlayerByIndex(0).getDashboard().getEntrance().addStudent(RealmColors.GREEN);
        game.getPlayerByIndex(0).getDashboard().getEntrance().addStudent(RealmColors.PINK);
        game.getPlayerByIndex(0).getDashboard().getEntrance().addStudent(RealmColors.PINK);
        game.getPlayerByIndex(0).getDashboard().getEntrance().addStudent(RealmColors.YELLOW);
        game.getPlayerByIndex(0).getDashboard().getEntrance().addStudent(RealmColors.YELLOW);
        game.getPlayerByIndex(0).getDashboard().getEntrance().addStudent(RealmColors.YELLOW);
        game.getPlayerByIndex(0).getDashboard().getEntrance().addStudent(RealmColors.YELLOW);

        assertEquals(7, game.getPlayerByIndex(0).getDashboard().getEntrance().getNumberOfStudents());
        assertEquals(7, game.getPlayerByIndex(0).getDashboard().getDiningRoom().getNumberOfStudents());

        game.playCharacterCard(0,0);

        game.activateAtomicEffect(0,0,2, 4);
        game.activateAtomicEffect(0,0,3, 1);
        game.activateAtomicEffect(0,0,0, 1);

        assertEquals(7, game.getPlayerByIndex(0).getDashboard().getEntrance().getNumberOfStudents());
        assertEquals(7, game.getPlayerByIndex(0).getDashboard().getDiningRoom().getNumberOfStudents());
    }

    /**
     * We are testing the behaviour of the SPOILED_PRINCESS
     */
    @Test
    void play_SPOILED_PRINCESS_CharacterCard() {
        game.addFirstPlayer("simone", true, 2);
        game.addAnotherPlayer("giacomo");

        game.getGameTable().setCharacterCards(CharacterCardsName.SPOILED_PRINCESS);
        game.setGamePhase(GamePhases.ACTION_PHASE);
        game.currentActivePlayer = game.getPlayerByIndex(0).getOrder();
        game.getPlayerByIndex(0).gainMoney();

        //testing if SPOILED_PRINCESS character card is correctly set up
        assertEquals(96, game.getGameTable().getBag().getNumberOfStudents());
        assertEquals(4, game.getGameTable().getCharacterCard(0).getNumberOfStudents());

        game.getGameTable().getCharacterCard(0).addStudent(RealmColors.BLUE);

        //played the SPOILED_PRINCESS character card:
        game.playCharacterCard(0, 0);
        //and then the controller will activate the atomic effect of each card for the number of times needed
        game.activateAtomicEffect(0,0,2,-1);

        assertEquals(95, game.getGameTable().getBag().getNumberOfStudents());
        assertEquals(1, game.getPlayerByIndex(0).getDashboard().getDiningRoom().getNumberOfStudents());
        assertEquals(5, game.getGameTable().getCharacterCard(0).getNumberOfStudents());
    }

    /**
     * We are testing the behaviour of the THIEF
     */
    @Test
    void play_THIEF_CharacterCard() {
        game.addFirstPlayer("simone", true, 2);
        game.addAnotherPlayer("giacomo");

        game.getGameTable().setCharacterCards(CharacterCardsName.THIEF);
        game.setGamePhase(GamePhases.ACTION_PHASE);
        game.currentActivePlayer = game.getPlayerByIndex(0).getOrder();
        game.getPlayerByIndex(0).gainMoney();
        game.getPlayerByIndex(0).gainMoney();

        // add 4 yellow students in the dining room of player 0
        game.getPlayerByIndex(0).getDashboard().getDiningRoom().addStudent(RealmColors.YELLOW);
        game.getPlayerByIndex(0).getDashboard().getDiningRoom().addStudent(RealmColors.YELLOW);
        game.getPlayerByIndex(0).getDashboard().getDiningRoom().addStudent(RealmColors.YELLOW);
        game.getPlayerByIndex(0).getDashboard().getDiningRoom().addStudent(RealmColors.YELLOW);
        assertEquals(4,game.getPlayerByIndex(0).getDashboard().getDiningRoom().getNumberOfStudents());

        // add 2 yellow students in the dining room of player 1
        game.getPlayerByIndex(1).getDashboard().getDiningRoom().addStudent(RealmColors.YELLOW);
        game.getPlayerByIndex(1).getDashboard().getDiningRoom().addStudent(RealmColors.YELLOW);
        assertEquals(2,game.getPlayerByIndex(1).getDashboard().getDiningRoom().getNumberOfStudents());

        //played the THIEF character card:
        game.playCharacterCard(0, 0);
        //and then the controller will activate the atomic effect of each card for the number of times needed
        game.activateAtomicEffect(0,0,0,-1);

        // 100 students in the bag
        // + 3 yellow students removed from the dining room of player 0
        // + 2 yellow students removed from the dining room of player 1
        //---------------------------------------------------------------
        // = 105 students in the bag
        assertEquals(105, game.getGameTable().getBag().getNumberOfStudents());
        // 1 student remained in the dining room of player 0
        assertEquals(1,game.getPlayerByIndex(0).getDashboard().getDiningRoom().getNumberOfStudents());
        // 0 student remained in the dining room of player 1
        assertEquals(0,game.getPlayerByIndex(1).getDashboard().getDiningRoom().getNumberOfStudents());
    }

}
package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.CharacterCards.CharacterCardsName;
import it.polimi.ingsw.Model.Enumeration.GameMode;
import it.polimi.ingsw.Model.Enumeration.RealmColors;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * We are testing if each character card behaves correctly
 */
class GamePlaySomeCharacterCards {

    /**
     * We are testing the behaviour of the MONK
     */
    @Test
    void playMONK_CharacterCard() {
        Game game = new Game();
        game.addFirstPlayer("simone", GameMode.EXPERT, 2);
        game.addAnotherPlayer("giacomo");

        int studentsInTheBag = game.getGameTable().getBag().getNumberOfStudents();

        game.getGameTable().setCharacterCards(0,CharacterCardsName.MONK);

        //testing if MONK character card is correctly set up
        assertEquals(studentsInTheBag-4, game.getGameTable().getBag().getNumberOfStudents());
        assertEquals(4, game.getGameTable().getCharacterCard(0).getNumberOfStudents());

        int studentsOnIsle = game.getGameTable().getIsleManager().getIsle(0).getNumberOfStudents();

        game.getGameTable().getCharacterCard(0).addStudent(RealmColors.YELLOW);   //this will be removed when the implementation is fully completed

        //played the MONK character card:
        game.playCharacterCard(0, game.getGameTable().getCharacterCard(0));
        assertEquals(studentsInTheBag-5, game.getGameTable().getBag().getNumberOfStudents());
        assertEquals(studentsOnIsle+1, game.getGameTable().getIsleManager().getIsle(0).getNumberOfStudents());
        assertEquals(5, game.getGameTable().getCharacterCard(0).getNumberOfStudents());

    }

    /**
     * We are testing the behaviour of the GRANDMA_HERBS
     */
    @Test
    public void playGRANDMA_HERBS_CharacterCard(){
        Game game = new Game();
        game.addFirstPlayer("simone", GameMode.EXPERT, 2);
        game.addAnotherPlayer("giacomo");

        game.getGameTable().setCharacterCards(0,CharacterCardsName.GRANDMA_HERBS);

        assertEquals(4,game.getGameTable().getCharacterCard(0).getDenyCards());

        game.playCharacterCard(0,game.getGameTable().getCharacterCard(0));

        assertEquals(3,game.getGameTable().getCharacterCard(0).getDenyCards());
        assertEquals(1,game.getGameTable().getIsleManager().getIsle(0).getDenyCards());

    }

    /**
     * We are testing the behaviour of the JESTER
     */
    @Test
    public void playJESTER_CharacterCard(){
        Game game = new Game();
        game.addFirstPlayer("simone", GameMode.EXPERT, 2);
        game.addAnotherPlayer("giacomo");

        int studentsInTheBag = game.getGameTable().getBag().getNumberOfStudents();

        int counter=0;

        // removing 3 students from entrance
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

        game.getGameTable().setCharacterCards(0,CharacterCardsName.JESTER);

        assertEquals(studentsInTheBag-6, game.getGameTable().getBag().getNumberOfStudents());
        assertEquals(6, game.getGameTable().getCharacterCard(0).getNumberOfStudents());

        game.getGameTable().getCharacterCard(0).addStudent(RealmColors.YELLOW);   //this will be removed when the implementation is fully completed
        game.getGameTable().getCharacterCard(0).addStudent(RealmColors.YELLOW);   //this will be removed when the implementation is fully completed
        game.getGameTable().getCharacterCard(0).addStudent(RealmColors.YELLOW);   //this will be removed when the implementation is fully completed
        game.getPlayerByIndex(0).getDashboard().getEntrance().addStudent(RealmColors.YELLOW);   //this will be removed when the implementation is fully completed
        game.getPlayerByIndex(0).getDashboard().getEntrance().addStudent(RealmColors.YELLOW);   //this will be removed when the implementation is fully completed
        game.getPlayerByIndex(0).getDashboard().getEntrance().addStudent(RealmColors.YELLOW);   //this will be removed when the implementation is fully completed

        assertEquals(3, game.getPlayerByIndex(0).getDashboard().getEntrance().getNumberOfStudents());

        game.playCharacterCard(0,game.getGameTable().getCharacterCard(0));

        assertEquals(3, game.getPlayerByIndex(0).getDashboard().getEntrance().getNumberOfStudents());
        assertEquals(9, game.getGameTable().getCharacterCard(0).getNumberOfStudents());

    }

    /**
     * We are testing the behaviour of the MINSTREL
     */
    @Test
    public void playMINSTREL_CharacterCard(){
        Game game = new Game();
        game.addFirstPlayer("simone", GameMode.EXPERT, 2);
        game.addAnotherPlayer("giacomo");

        game.getGameTable().setCharacterCards(0,CharacterCardsName.MINSTREL);

        int counter=0;

        // removing 3 students from entrance
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

        game.getPlayerByIndex(0).getDashboard().getEntrance().addStudent(RealmColors.YELLOW);   //this will be removed when the implementation is fully completed
        game.getPlayerByIndex(0).getDashboard().getEntrance().addStudent(RealmColors.YELLOW);   //this will be removed when the implementation is fully completed
        game.getPlayerByIndex(0).getDashboard().getEntrance().addStudent(RealmColors.YELLOW);   //this will be removed when the implementation is fully completed
        game.getPlayerByIndex(0).getDashboard().getDiningRoom().addStudent(RealmColors.YELLOW);   //this will be removed when the implementation is fully completed
        game.getPlayerByIndex(0).getDashboard().getDiningRoom().addStudent(RealmColors.YELLOW);   //this will be removed when the implementation is fully completed
        game.getPlayerByIndex(0).getDashboard().getDiningRoom().addStudent(RealmColors.YELLOW);   //this will be removed when the implementation is fully completed

        assertEquals(3, game.getPlayerByIndex(0).getDashboard().getEntrance().getNumberOfStudents());
        assertEquals(3, game.getPlayerByIndex(0).getDashboard().getDiningRoom().getNumberOfStudents());

        game.playCharacterCard(0,game.getGameTable().getCharacterCard(0));

        assertEquals(3, game.getPlayerByIndex(0).getDashboard().getEntrance().getNumberOfStudents());
        assertEquals(3, game.getPlayerByIndex(0).getDashboard().getDiningRoom().getNumberOfStudents());

    }

    /**
     * We are testing the behaviour of the SPOILED_PRINCESS
     */
    @Test
    void playSPOILED_PRINCESS_CharacterCard() {
        Game game = new Game();
        game.addFirstPlayer("simone", GameMode.EXPERT, 2);
        game.addAnotherPlayer("giacomo");

        int studentsInTheBag = game.getGameTable().getBag().getNumberOfStudents();

        game.getGameTable().setCharacterCards(0,CharacterCardsName.SPOILED_PRINCESS);

        //testing if SPOILED_PRINCESS character card is correctly set up
        assertEquals(studentsInTheBag-4, game.getGameTable().getBag().getNumberOfStudents());
        assertEquals(4, game.getGameTable().getCharacterCard(0).getNumberOfStudents());

        game.getGameTable().getCharacterCard(0).addStudent(RealmColors.YELLOW);   //this will be removed when the implementation is fully completed

        //played the SPOILED_PRINCESS character card:
        game.playCharacterCard(0, game.getGameTable().getCharacterCard(0));
        assertEquals(studentsInTheBag-5, game.getGameTable().getBag().getNumberOfStudents());
        assertEquals(1, game.getPlayerByIndex(0).getDashboard().getDiningRoom().getNumberOfStudents());
        assertEquals(5, game.getGameTable().getCharacterCard(0).getNumberOfStudents());

    }

    /**
     * We are testing the behaviour of the THIEF
     */
    @Test
    void playTHIEF_CharacterCard() {
        Game game = new Game();
        game.addFirstPlayer("simone", GameMode.EXPERT, 2);
        game.addAnotherPlayer("giacomo");

        game.getPlayerByIndex(0).getDashboard().getDiningRoom().addStudent(RealmColors.YELLOW);
        game.getPlayerByIndex(0).getDashboard().getDiningRoom().addStudent(RealmColors.YELLOW);
        game.getPlayerByIndex(0).getDashboard().getDiningRoom().addStudent(RealmColors.YELLOW);
        game.getPlayerByIndex(0).getDashboard().getDiningRoom().addStudent(RealmColors.YELLOW);
        assertEquals(4,game.getPlayerByIndex(0).getDashboard().getDiningRoom().getNumberOfStudents());

        game.getPlayerByIndex(1).getDashboard().getDiningRoom().addStudent(RealmColors.YELLOW);
        game.getPlayerByIndex(1).getDashboard().getDiningRoom().addStudent(RealmColors.YELLOW);
        assertEquals(2,game.getPlayerByIndex(1).getDashboard().getDiningRoom().getNumberOfStudents());

        int studentsInTheBag = game.getGameTable().getBag().getNumberOfStudents();
        game.getGameTable().setCharacterCards(0,CharacterCardsName.THIEF);

        //played the THIEF character card:
        game.playCharacterCard(0, game.getGameTable().getCharacterCard(0));
        assertEquals(studentsInTheBag+5, game.getGameTable().getBag().getNumberOfStudents());
        assertEquals(1,game.getPlayerByIndex(0).getDashboard().getDiningRoom().getNumberOfStudents());
        assertEquals(0,game.getPlayerByIndex(1).getDashboard().getDiningRoom().getNumberOfStudents());

    }

}
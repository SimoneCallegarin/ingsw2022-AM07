package it.polimi.ingsw.Model.GamePhases;

import it.polimi.ingsw.Model.Enumeration.*;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.Player.AssistantCard;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActionPhase {

    /**
     * we are testing if the first player to move students during the first action phase gains all the professors he deserves
     */
    @Test
    public void diningRoomFirstMovements2Players() {
        int counter = 0;
        Game game = new Game();
        AssistantCard cardPlayed1 = new AssistantCard(4, 2, false);
        AssistantCard cardPlayed2 = new AssistantCard(3, 2, false);
        game.addFirstPlayer("jack", GameMode.BASE, 2);
        game.addAnotherPlayer("calle");
        if (game.firstPlayerIndex == 0 && game.gamePhase == GamePhases.PLANNING_PHASE) {
            game.playAssistantCard(0, cardPlayed1);
            game.playAssistantCard(1, cardPlayed2);
        }
        if (game.firstPlayerIndex == 1 && game.gamePhase == GamePhases.PLANNING_PHASE) {
            game.playAssistantCard(1, cardPlayed2);
            game.playAssistantCard(0, cardPlayed1);
        }
        //for (RealmColors rc : RealmColors.values())
        //    System.out.println("Number of " + rc.toString() + " students in player one entrance: " + game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getEntrance().getStudentsByColor(rc));
        for (RealmColors rc : RealmColors.values()) {
            int studentsOfSpecifiedColor = game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getEntrance().getStudentsByColor(rc);
            for (int i = 0; i < studentsOfSpecifiedColor; i++) {
                game.moveStudentInDiningRoom(game.firstPlayerIndex, rc);
                counter++;
                if (counter == 3)
                    break;
            }
            if (counter == 3)
                break;
        }
        assertEquals(ActionPhases.MOVE_MOTHER_NATURE, game.actionPhase);
        assertEquals(4, game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getEntrance().getNumberOfStudents());
        //System.out.println("");
        //for (RealmColors rc : RealmColors.values())
        //    System.out.println("Number of " + rc.toString() + " students in player one entrance: " + game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getEntrance().getStudentsByColor(rc));
        //System.out.println("");
        //for (RealmColors rc : RealmColors.values())
        //    System.out.println("Number of " + rc.toString() + " students in player one dining room: " + game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getDiningRoom().getStudentsByColor(rc));
        for (RealmColors rc : RealmColors.values()) {
            if (game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getDiningRoom().getStudentsByColor(rc) > 0)
                assertEquals(1, game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getDiningRoom().getProfessorByColor(rc));
            else
                assertEquals(0, game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getDiningRoom().getProfessorByColor(rc));
        }
    }

    /**
     * we are testing if nothing changes even if there could be a draw situation (no professors gained)
     */
    @Test
    public void diningRoomMovementsPossibleDraw2Players() {
        int counter = 0;
        Game game = new Game();
        AssistantCard cardPlayed1 = new AssistantCard(4, 2, false);
        AssistantCard cardPlayed2 = new AssistantCard(3, 2, false);
        game.addFirstPlayer("jack", GameMode.BASE, 2);
        game.addAnotherPlayer("calle");
        if (game.firstPlayerIndex == 0 && game.gamePhase == GamePhases.PLANNING_PHASE) {
            game.playAssistantCard(0, cardPlayed1);
            game.playAssistantCard(1, cardPlayed2);
        }
        if (game.firstPlayerIndex == 1 && game.gamePhase == GamePhases.PLANNING_PHASE) {
            game.playAssistantCard(1, cardPlayed2);
            game.playAssistantCard(0, cardPlayed1);
        }
        game.getPlayerByIndex(0).getDashboard().getDiningRoom().addStudent(RealmColors.YELLOW);
        game.getPlayerByIndex(0).getDashboard().getDiningRoom().addStudent(RealmColors.PINK);
        game.getPlayerByIndex(0).getDashboard().getDiningRoom().addStudent(RealmColors.BLUE);
        game.getPlayerByIndex(0).getDashboard().getDiningRoom().addProfessor(RealmColors.YELLOW);
        game.getPlayerByIndex(0).getDashboard().getDiningRoom().addProfessor(RealmColors.PINK);
        game.getPlayerByIndex(0).getDashboard().getDiningRoom().addProfessor(RealmColors.BLUE);
        game.getGameTable().removeProfessor(RealmColors.YELLOW);
        game.getGameTable().removeProfessor(RealmColors.PINK);
        game.getGameTable().removeProfessor(RealmColors.BLUE);
        for (RealmColors rc : RealmColors.values()) {
            int studentsOfSpecifiedColor = game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getEntrance().getStudentsByColor(rc);
            for (int i = 0; i < studentsOfSpecifiedColor; i++) {
                game.moveStudentInDiningRoom(game.firstPlayerIndex, rc);
                counter++;
                if (counter == 3)
                    break;
            }
            if (counter == 3)
                break;
        }
        assertEquals(ActionPhases.MOVE_MOTHER_NATURE, game.actionPhase);
        assertEquals(4, game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getEntrance().getNumberOfStudents());
        //for (RealmColors rc : RealmColors.values())
        //    System.out.println("Number of " + rc.toString() + " students in player one dining room: " + game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getDiningRoom().getStudentsByColor(rc));
        for (RealmColors rc : RealmColors.values()) {
            if (game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getDiningRoom().getStudentsByColor(rc) > game.getPlayerByIndex(0).getDashboard().getDiningRoom().getStudentsByColor(rc))
                assertEquals(1, game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getDiningRoom().getProfessorByColor(rc));
            else
                assertEquals(0, game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getDiningRoom().getProfessorByColor(rc));
        }
        //System.out.println("");
        //for (RealmColors rc : RealmColors.values())
         //   System.out.println("Is " + rc.toString() + " professor in player one dining room? " + game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getDiningRoom().getProfessorByColor(rc));
    }

    /**
     * we are testing if a combination of the two moveStudent methods works properly
     */
    @Test
    public void firstMovements() {
        int counter = 0;
        Game game = new Game();
        AssistantCard cardPlayed1 = new AssistantCard(4, 2, false);
        AssistantCard cardPlayed2 = new AssistantCard(3, 2, false);
        game.addFirstPlayer("jack", GameMode.BASE, 2);
        game.addAnotherPlayer("calle");
        if (game.firstPlayerIndex == 0 && game.gamePhase == GamePhases.PLANNING_PHASE) {
            game.playAssistantCard(0, cardPlayed1);
            game.playAssistantCard(1, cardPlayed2);
        }
        if (game.firstPlayerIndex == 1 && game.gamePhase == GamePhases.PLANNING_PHASE) {
            game.playAssistantCard(1, cardPlayed2);
            game.playAssistantCard(0, cardPlayed1);
        }
        for (RealmColors rc : RealmColors.values()) {
            int studentsOfSpecifiedColor = game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getEntrance().getStudentsByColor(rc);
            for (int i = 0; i < studentsOfSpecifiedColor; i++) {
                if (counter == 1)
                    game.moveStudentInIsle(game.firstPlayerIndex, game.getGameTable().getIsleManager().getIsleOppositeToMotherNatureIndex(), rc);
                else
                    game.moveStudentInDiningRoom(game.firstPlayerIndex, rc);
                counter++;
                if (counter == 3)
                    break;
            }
            if (counter == 3)
                break;
        }
        assertEquals(ActionPhases.MOVE_MOTHER_NATURE, game.actionPhase);
        assertEquals(4, game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getEntrance().getNumberOfStudents());
        for (RealmColors rc : RealmColors.values()) {
            if (game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getDiningRoom().getStudentsByColor(rc) > 0)
                assertEquals(1, game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getDiningRoom().getProfessorByColor(rc));
            else
                assertEquals(0, game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getDiningRoom().getProfessorByColor(rc));
        }
        assertEquals(1, game.getGameTable().getIsleManager().getIsle(game.getGameTable().getIsleManager().getIsleOppositeToMotherNatureIndex()).getNumberOfStudents());
    }
}
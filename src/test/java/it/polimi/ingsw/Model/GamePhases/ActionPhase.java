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
        game.moveStudentInDiningRoom(game.firstPlayerIndex, RealmColors.YELLOW);
        game.moveStudentInDiningRoom(game.firstPlayerIndex, RealmColors.PINK);
        game.moveStudentInDiningRoom(game.firstPlayerIndex, RealmColors.RED);
        assertEquals(ActionPhases.MOVE_MOTHER_NATURE, game.actionPhase);
        // assertEquals(4, game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getEntrance().getNumberOfStudents());
        assertEquals(1, game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getDiningRoom().getProfessorByColor(RealmColors.YELLOW));
        assertEquals(1, game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getDiningRoom().getProfessorByColor(RealmColors.PINK));
        assertEquals(1, game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getDiningRoom().getProfessorByColor(RealmColors.RED));
        assertEquals(0, game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getDiningRoom().getProfessorByColor(RealmColors.BLUE));
        assertEquals(0, game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getDiningRoom().getProfessorByColor(RealmColors.GREEN));
    }

    /**
     * we are testing if nothing changes in a draw situation (no professors gained)
     */
    @Test
    public void diningRoomMovementsDraw2Players() {
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
        game.getPlayerByIndex(0).getDashboard().getDiningRoom().addStudent(RealmColors.YELLOW);
        game.getPlayerByIndex(0).getDashboard().getDiningRoom().addStudent(RealmColors.RED);
        game.getPlayerByIndex(0).getDashboard().getDiningRoom().addProfessor(RealmColors.YELLOW);
        game.getPlayerByIndex(0).getDashboard().getDiningRoom().addProfessor(RealmColors.RED);
        game.moveStudentInDiningRoom(game.firstPlayerIndex, RealmColors.YELLOW);
        game.moveStudentInDiningRoom(game.firstPlayerIndex, RealmColors.YELLOW);
        game.moveStudentInDiningRoom(game.firstPlayerIndex, RealmColors.PINK);
        assertEquals(ActionPhases.MOVE_MOTHER_NATURE, game.actionPhase);
        // assertEquals(4, game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getEntrance().getNumberOfStudents());
        assertEquals(0, game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getDiningRoom().getProfessorByColor(RealmColors.YELLOW));
        assertEquals(1, game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getDiningRoom().getProfessorByColor(RealmColors.PINK));
        assertEquals(0, game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getDiningRoom().getProfessorByColor(RealmColors.RED));
        assertEquals(0, game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getDiningRoom().getProfessorByColor(RealmColors.BLUE));
        assertEquals(0, game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getDiningRoom().getProfessorByColor(RealmColors.GREEN));
    }

    /**
     * we are testing if a combination of the two moveStudent methods works properly
     */
    @Test
    public void firstMovements() {
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
        game.moveStudentInDiningRoom(game.firstPlayerIndex, RealmColors.YELLOW);
        game.moveStudentInIsle(game.firstPlayerIndex, 5, RealmColors.RED);
        game.moveStudentInIsle(game.firstPlayerIndex, 4, RealmColors.YELLOW);
        assertEquals(ActionPhases.MOVE_MOTHER_NATURE, game.actionPhase);
        // assertEquals(4, game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getEntrance().getNumberOfStudents());
        assertEquals(1, game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getDiningRoom().getProfessorByColor(RealmColors.YELLOW));
        assertEquals(0, game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getDiningRoom().getProfessorByColor(RealmColors.PINK));
        assertEquals(0, game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getDiningRoom().getProfessorByColor(RealmColors.RED));
        assertEquals(0, game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getDiningRoom().getProfessorByColor(RealmColors.BLUE));
        assertEquals(0, game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getDiningRoom().getProfessorByColor(RealmColors.GREEN));
        assertEquals(1, game.getGameTable().getIsleManager().getIsle(5).getNumberOfStudents());
        assertEquals(1, game.getGameTable().getIsleManager().getIsle(4).getNumberOfStudents());
    }
}
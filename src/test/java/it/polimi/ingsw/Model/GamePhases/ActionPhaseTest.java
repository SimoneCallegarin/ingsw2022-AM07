package it.polimi.ingsw.Model.GamePhases;

import it.polimi.ingsw.Model.Enumeration.*;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Model.Player.AssistantCard;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActionPhaseTest {

    /**
     * it tests if the first player to move students during the first action phase gains all the professors he deserves
     */
    @Test
    public void diningRoomFirstMovements2Players() {
        int counter = 0;
        Game game = new Game();
        AssistantCard cardPlayed1 = new AssistantCard(4, 2);
        AssistantCard cardPlayed2 = new AssistantCard(3, 2);
        game.addFirstPlayer("jack", GameMode.BASE, 2);
        game.addAnotherPlayer("calle");
        if (game.firstPlayerIndex == 0 && game.gamePhase == GamePhases.PLANNING_PHASE) {
            game.playAssistantCard(0, 4);
            game.playAssistantCard(1, 3);
        }
        if (game.firstPlayerIndex == 1 && game.gamePhase == GamePhases.PLANNING_PHASE) {
            game.playAssistantCard(1, 3);
            game.playAssistantCard(0, 4);
        }
        /*for (RealmColors rc : RealmColors.values())
            System.out.println("Number of " + rc.toString() + " students in player one entrance: " + game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getEntrance().getStudentsByColor(rc));*/
        // moving 3 students
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
        // checking if we skipped to the next stage
        assertEquals(ActionPhases.MOVE_MOTHER_NATURE, game.actionPhase);
        // checking if the player has 4 students in his entrance
        assertEquals(4, game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getEntrance().getNumberOfStudents());
        /*System.out.println("");
        for (RealmColors rc : RealmColors.values())
            System.out.println("Number of " + rc.toString() + " students in player one entrance: " + game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getEntrance().getStudentsByColor(rc));
        System.out.println("");
        for (RealmColors rc : RealmColors.values())
            System.out.println("Number of " + rc.toString() + " students in player one dining room: " + game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getDiningRoom().getStudentsByColor(rc));*/
        // checking if professors are gained
        for (RealmColors rc : RealmColors.values()) {
            if (game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getDiningRoom().getStudentsByColor(rc) > 0)
                assertEquals(1, game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getDiningRoom().getProfessorByColor(rc));
            else
                assertEquals(0, game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getDiningRoom().getProfessorByColor(rc));
        }
    }

    /**
     * it tests if nothing changes even if there could be a draw situation (no professors gained)
     */
    @Test
    public void diningRoomMovementsPossibleDraw2Players() {
        int counter = 0;
        Game game = new Game();
        AssistantCard cardPlayed1 = new AssistantCard(4, 2);
        AssistantCard cardPlayed2 = new AssistantCard(3, 2);
        game.addFirstPlayer("jack", GameMode.BASE, 2);
        game.addAnotherPlayer("calle");
        if (game.firstPlayerIndex == 0 && game.gamePhase == GamePhases.PLANNING_PHASE) {
            game.playAssistantCard(0, 4);
            game.playAssistantCard(1, 3);
        }
        if (game.firstPlayerIndex == 1 && game.gamePhase == GamePhases.PLANNING_PHASE) {
            game.playAssistantCard(1, 3);
            game.playAssistantCard(0, 4);
        }
        // manually adding students and professors to the second player
        game.getPlayerByIndex(0).getDashboard().getDiningRoom().addStudent(RealmColors.YELLOW);
        game.getPlayerByIndex(0).getDashboard().getDiningRoom().addStudent(RealmColors.PINK);
        game.getPlayerByIndex(0).getDashboard().getDiningRoom().addStudent(RealmColors.BLUE);
        game.getPlayerByIndex(0).getDashboard().getDiningRoom().addProfessor(RealmColors.YELLOW);
        game.getPlayerByIndex(0).getDashboard().getDiningRoom().addProfessor(RealmColors.PINK);
        game.getPlayerByIndex(0).getDashboard().getDiningRoom().addProfessor(RealmColors.BLUE);
        game.getGameTable().removeProfessor(RealmColors.YELLOW);
        game.getGameTable().removeProfessor(RealmColors.PINK);
        game.getGameTable().removeProfessor(RealmColors.BLUE);
        // moving 3 students of the first player
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
        /*for (RealmColors rc : RealmColors.values())
            System.out.println("Number of " + rc.toString() + " students in player one dining room: " + game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getDiningRoom().getStudentsByColor(rc));*/
        // checking if certain professors are gained or not (comparison is made with the number of students of the other player)
        for (RealmColors rc : RealmColors.values()) {
            if (game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getDiningRoom().getStudentsByColor(rc) > game.getPlayerByIndex(0).getDashboard().getDiningRoom().getStudentsByColor(rc))
                assertEquals(1, game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getDiningRoom().getProfessorByColor(rc));
            else
                assertEquals(0, game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getDiningRoom().getProfessorByColor(rc));
        }
        /*System.out.println("");
        for (RealmColors rc : RealmColors.values())
            System.out.println("Is " + rc.toString() + " professor in player one dining room? " + game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getDiningRoom().getProfessorByColor(rc));*/
    }

    /**
     * it tests if a combination of the two moveStudent methods works properly
     */
    @Test
    public void firstStudentsMovements() {
        int counter = 0;
        Game game = new Game();
        AssistantCard cardPlayed1 = new AssistantCard(4, 2);
        AssistantCard cardPlayed2 = new AssistantCard(3, 2);
        game.addFirstPlayer("jack", GameMode.BASE, 2);
        game.addAnotherPlayer("calle");
        if (game.firstPlayerIndex == 0 && game.gamePhase == GamePhases.PLANNING_PHASE) {
            game.playAssistantCard(0, 4);
            game.playAssistantCard(1, 3);
        }
        if (game.firstPlayerIndex == 1 && game.gamePhase == GamePhases.PLANNING_PHASE) {
            game.playAssistantCard(1, 3);
            game.playAssistantCard(0, 4);
        }
        // moving 3 students (both in the dining room and on an isle)
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
        // checking the professors update...
        for (RealmColors rc : RealmColors.values()) {
            if (game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getDiningRoom().getStudentsByColor(rc) > 0)
                assertEquals(1, game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getDiningRoom().getProfessorByColor(rc));
            else
                assertEquals(0, game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getDiningRoom().getProfessorByColor(rc));
        }
        // ...and the number of students on the chosen isle
        assertEquals(1, game.getGameTable().getIsleManager().getIsle(game.getGameTable().getIsleManager().getIsleOppositeToMotherNatureIndex()).getNumberOfStudents());
    }

    /**
     * it tests if the mother nature movement is correct and a tower is put on the chosen isle
     */
    @Test
    public void firstMotherNatureMovementWithConquest() {
        int nextIsleIndex = 0;
        Game game = new Game();
        AssistantCard cardPlayed1 = new AssistantCard(4, 2);
        AssistantCard cardPlayed2 = new AssistantCard(3, 2);
        game.addFirstPlayer("jack", GameMode.BASE, 2);
        game.addAnotherPlayer("calle");
        if (game.firstPlayerIndex == 0 && game.gamePhase == GamePhases.PLANNING_PHASE) {
            game.playAssistantCard(0, 4);
            game.playAssistantCard(1, 3);
        }
        if (game.firstPlayerIndex == 1 && game.gamePhase == GamePhases.PLANNING_PHASE) {
            game.playAssistantCard(1, 3);
            game.playAssistantCard(0, 4);
        }
        // skipping first movements phase by manually adding students and professors to player one dining room
        game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getDiningRoom().addStudent(RealmColors.YELLOW);
        game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getDiningRoom().addStudent(RealmColors.PINK);
        game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getDiningRoom().addProfessor(RealmColors.YELLOW);
        assertEquals(1, game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getDiningRoom().getNumberOfProfessors());
        game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getDiningRoom().addProfessor(RealmColors.PINK);
        assertEquals(2, game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getDiningRoom().getNumberOfProfessors());
        // calculating index of the next isle (we want to move only by one)
        if (game.getGameTable().getIsleManager().getIsleWithMotherNatureIndex() != 11)
            nextIsleIndex = game.getGameTable().getIsleManager().getIsleWithMotherNatureIndex()+1;
        // putting a yellow student on the chosen isle in order to be sure player one has the highest influence on it
        game.getGameTable().getIsleManager().getIsle(nextIsleIndex).addStudent(RealmColors.YELLOW);
        // manually setting the correct actionPhase stage
        game.actionPhase = ActionPhases.MOVE_MOTHER_NATURE;
        // moving mother nature
        game.moveMotherNature(game.firstPlayerIndex, nextIsleIndex);
        // checking if the movement has been done correctly
        assertEquals(nextIsleIndex, game.getGameTable().getIsleManager().getIsleWithMotherNatureIndex());
        // due to the fact that player one has the highest influence on the isle, we are checking if one tower has been removed from player one dining room...
        assertEquals(7, game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getTowerStorage().getNumberOfTowers());
        // ...and tower color on the isle has been updated with the tower color of player one
        assertEquals(game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getTowerStorage().getTowerColor(), game.getGameTable().getIsleManager().getIsle(nextIsleIndex).getTowersColor());
        // checking if we moved on the next stage of actionPhase
        assertEquals(ActionPhases.CHOOSE_CLOUD, game.actionPhase);
    }

    /**
     * it tests if nothing happens when somebody tries to move mother nature more than the mnMovement number indicated on the assistant card he played
     */
    @Test
    public void wrongMotherNatureMovements() {
        Game game = new Game();
        AssistantCard cardPlayed1 = new AssistantCard(4, 2);
        AssistantCard cardPlayed2 = new AssistantCard(3, 2);
        game.addFirstPlayer("jack", GameMode.BASE, 2);
        game.addAnotherPlayer("calle");
        if (game.firstPlayerIndex == 0 && game.gamePhase == GamePhases.PLANNING_PHASE) {
            game.playAssistantCard(0, 4);
            game.playAssistantCard(1, 3);
        }
        if (game.firstPlayerIndex == 1 && game.gamePhase == GamePhases.PLANNING_PHASE) {
            game.playAssistantCard(1, 3);
            game.playAssistantCard(0, 4);
        }
        // setting externally where mother nature is (isle 5)
        game.getGameTable().getIsleManager().getIsle(game.getGameTable().getIsleManager().getIsleWithMotherNatureIndex()).setMotherNature(false);
        game.getGameTable().getIsleManager().getIsle(5).setMotherNature(true);
        game.getGameTable().getIsleManager().setIsleWithMotherNatureIndex(5);
        // manually setting the correct actionPhase stage
        game.actionPhase = ActionPhases.MOVE_MOTHER_NATURE;
        // moving mother nature
        game.moveMotherNature(game.firstPlayerIndex, 8);
        // checking if no movement has been done (because first player can only move by 2)...
        assertEquals(5, game.getGameTable().getIsleManager().getIsleWithMotherNatureIndex());
        // ...and we didn't move on the next actionPhase's stage
        assertEquals(ActionPhases.MOVE_MOTHER_NATURE, game.actionPhase);

        // setting externally where mother nature is (isle 10)
        game.getGameTable().getIsleManager().getIsle(5).setMotherNature(false);
        game.getGameTable().getIsleManager().getIsle(10).setMotherNature(true);
        game.getGameTable().getIsleManager().setIsleWithMotherNatureIndex(10);
        // manually setting the correct actionPhase stage
        game.actionPhase = ActionPhases.MOVE_MOTHER_NATURE;
        // moving mother nature
        game.moveMotherNature(game.firstPlayerIndex, 1);
        // checking if no movement has been done (because first player can only move by 2)...
        assertEquals(10, game.getGameTable().getIsleManager().getIsleWithMotherNatureIndex());
        // ...and we didn't move on the next actionPhase's stage
        assertEquals(ActionPhases.MOVE_MOTHER_NATURE, game.actionPhase);

        // moving mother nature
        game.moveMotherNature(game.firstPlayerIndex, 10);
        // checking if no movement has been done (because first player can only move by 2)...
        assertEquals(10, game.getGameTable().getIsleManager().getIsleWithMotherNatureIndex());
        assertEquals(TowerColors.NOCOLOR, game.getGameTable().getIsleManager().getIsle(10).getTowersColor());
        // ...and we didn't move on the next actionPhase's stage
        assertEquals(ActionPhases.MOVE_MOTHER_NATURE, game.actionPhase);
    }

    /**
     * it tests if landing on isles with different influences for player one leads to correct towers updates
     */
    @Test
    public void variousMotherNatureMovementsWithAndWithoutConquest() {
        Game game = new Game();
        AssistantCard cardPlayed1 = new AssistantCard(4, 2);
        AssistantCard cardPlayed2 = new AssistantCard(3, 2);
        game.addFirstPlayer("jack", GameMode.BASE, 2);
        game.addAnotherPlayer("calle");
        if (game.firstPlayerIndex == 0 && game.gamePhase == GamePhases.PLANNING_PHASE) {
            game.playAssistantCard(0, 4);
            game.playAssistantCard(1, 3);
        }
        if (game.firstPlayerIndex == 1 && game.gamePhase == GamePhases.PLANNING_PHASE) {
            game.playAssistantCard(1, 3);
            game.playAssistantCard(0, 4);
        }
        // skipping first movements phase by manually adding students and professors to players dining rooms
        game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getDiningRoom().addStudent(RealmColors.YELLOW);
        game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getDiningRoom().addProfessor(RealmColors.YELLOW);
        assertEquals(1, game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getDiningRoom().getNumberOfProfessors());
        game.getPlayerByIndex(0).getDashboard().getDiningRoom().addStudent(RealmColors.BLUE);
        game.getPlayerByIndex(0).getDashboard().getDiningRoom().addProfessor(RealmColors.BLUE);
        assertEquals(1, game.getPlayerByIndex(0).getDashboard().getDiningRoom().getNumberOfProfessors());
        // setting externally where mother nature is (isle 5)
        game.getGameTable().getIsleManager().getIsle(game.getGameTable().getIsleManager().getIsleWithMotherNatureIndex()).setMotherNature(false);
        game.getGameTable().getIsleManager().getIsle(5).setMotherNature(true);
        game.getGameTable().getIsleManager().setIsleWithMotherNatureIndex(5);
        // putting students and towers in the isles of interest
        game.getGameTable().getIsleManager().getIsle(6).removeStudent(RealmColors.YELLOW);
        game.getGameTable().getIsleManager().getIsle(6).addStudent(RealmColors.YELLOW);
        game.getGameTable().getIsleManager().getIsle(6).removeStudent(RealmColors.BLUE);
        game.getGameTable().getIsleManager().getIsle(6).addStudent(RealmColors.BLUE);
        game.getGameTable().getIsleManager().getIsle(6).setTower(TowerColors.WHITE);
        game.getPlayerByIndex(0).getDashboard().getTowerStorage().removeTower();
        game.getGameTable().getIsleManager().getIsle(8).removeStudent(RealmColors.YELLOW);
        game.getGameTable().getIsleManager().getIsle(8).addStudent(RealmColors.YELLOW);
        game.getGameTable().getIsleManager().getIsle(8).addStudent(RealmColors.YELLOW);
        game.getGameTable().getIsleManager().getIsle(8).removeStudent(RealmColors.BLUE);
        game.getGameTable().getIsleManager().getIsle(8).addStudent(RealmColors.BLUE);
        game.getGameTable().getIsleManager().getIsle(8).setTower(TowerColors.WHITE);
        game.getPlayerByIndex(0).getDashboard().getTowerStorage().removeTower();
        game.getGameTable().getIsleManager().getIsle(10).removeStudent(RealmColors.YELLOW);
        game.getGameTable().getIsleManager().getIsle(10).addStudent(RealmColors.YELLOW);
        game.getGameTable().getIsleManager().getIsle(10).addStudent(RealmColors.YELLOW);
        game.getGameTable().getIsleManager().getIsle(10).addStudent(RealmColors.YELLOW);
        game.getGameTable().getIsleManager().getIsle(10).removeStudent(RealmColors.BLUE);
        game.getGameTable().getIsleManager().getIsle(10).addStudent(RealmColors.BLUE);
        game.getGameTable().getIsleManager().getIsle(10).setTower(TowerColors.WHITE);
        game.getPlayerByIndex(0).getDashboard().getTowerStorage().removeTower();
        // manually setting the correct actionPhase stage
        game.actionPhase = ActionPhases.MOVE_MOTHER_NATURE;
        // moving mother nature
        game.moveMotherNature(game.firstPlayerIndex, 6);
        // checking if the movement has been done correctly
        assertEquals(6, game.getGameTable().getIsleManager().getIsleWithMotherNatureIndex());
        // checking if the other player still has 5 towers on its dashboard
        assertEquals(5, game.getPlayerByIndex(0).getDashboard().getTowerStorage().getNumberOfTowers());
        // due to the fact that player one has not the highest influence on the isle (1<2), we are checking if no tower has been removed from player one dining room...
        assertEquals(8, game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getTowerStorage().getNumberOfTowers());
        // ...and tower color remained the same as before
        assertEquals(TowerColors.WHITE, game.getGameTable().getIsleManager().getIsle(6).getTowersColor());
        // checking if we moved on the next stage of actionPhase
        assertEquals(ActionPhases.CHOOSE_CLOUD, game.actionPhase);

        // manually setting the correct actionPhase stage
        game.actionPhase = ActionPhases.MOVE_MOTHER_NATURE;
        // moving mother nature
        game.moveMotherNature(game.firstPlayerIndex, 8);
        // checking if the movement has been done correctly
        assertEquals(8, game.getGameTable().getIsleManager().getIsleWithMotherNatureIndex());
        // checking if the other player still has 5 towers on its dashboard
        assertEquals(5, game.getPlayerByIndex(0).getDashboard().getTowerStorage().getNumberOfTowers());
        // due to the fact that player one has not the highest influence on the isle (2=2), we are checking if no tower has been removed from player one dining room...
        assertEquals(8, game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getTowerStorage().getNumberOfTowers());
        // ...and tower color remained the same as before
        assertEquals(TowerColors.WHITE, game.getGameTable().getIsleManager().getIsle(8).getTowersColor());
        // checking if we moved on the next stage of actionPhase
        assertEquals(ActionPhases.CHOOSE_CLOUD, game.actionPhase);

        // manually setting the correct actionPhase stage
        game.actionPhase = ActionPhases.MOVE_MOTHER_NATURE;
        // moving mother nature
        game.moveMotherNature(game.firstPlayerIndex, 10);
        // checking if the movement has been done correctly
        assertEquals(10, game.getGameTable().getIsleManager().getIsleWithMotherNatureIndex());
        // checking if the other player now has 6 towers on its dashboard
        assertEquals(6, game.getPlayerByIndex(0).getDashboard().getTowerStorage().getNumberOfTowers());
        // due to the fact that player one has the highest influence on the isle (3>2), we are checking if one tower has been removed from player one dining room...
        assertEquals(7, game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getTowerStorage().getNumberOfTowers());
        // ...and tower color on the isle has been updated with the tower color of player one
        assertEquals(TowerColors.BLACK, game.getGameTable().getIsleManager().getIsle(10).getTowersColor());
        // checking if we moved on the next stage of actionPhase
        assertEquals(ActionPhases.CHOOSE_CLOUD, game.actionPhase);
    }

    /**
     * it tests if various MN movements are acceptable
     */
    @Test
    public void variousPossibleMotherNatureMovements() {
        Game game = new Game();
        AssistantCard cardPlayed1 = new AssistantCard(4, 2);
        AssistantCard cardPlayed2 = new AssistantCard(3, 12);
        game.addFirstPlayer("jack", GameMode.BASE, 2);
        game.addAnotherPlayer("calle");
        if (game.firstPlayerIndex == 0 && game.gamePhase == GamePhases.PLANNING_PHASE) {
            game.playAssistantCard(0, 4);
            game.playAssistantCard(1, 3);
        }
        if (game.firstPlayerIndex == 1 && game.gamePhase == GamePhases.PLANNING_PHASE) {
            game.playAssistantCard(1, 3);
            game.playAssistantCard(0, 4);
        }
        // setting externally where mother nature is (isle 9)
        game.getGameTable().getIsleManager().getIsle(game.getGameTable().getIsleManager().getIsleWithMotherNatureIndex()).setMotherNature(false);
        game.getGameTable().getIsleManager().getIsle(9).setMotherNature(true);
        game.getGameTable().getIsleManager().setIsleWithMotherNatureIndex(9);
        // manually setting the correct actionPhase stage
        game.actionPhase = ActionPhases.MOVE_MOTHER_NATURE;
        // moving mother nature
        game.moveMotherNature(game.firstPlayerIndex, 11);
        // checking if the movement has been done correctly
        assertEquals(11, game.getGameTable().getIsleManager().getIsleWithMotherNatureIndex());
        // checking if we moved on the next stage of actionPhase
        assertEquals(ActionPhases.CHOOSE_CLOUD, game.actionPhase);

        // manually setting the correct actionPhase stage
        game.actionPhase = ActionPhases.MOVE_MOTHER_NATURE;
        // moving mother nature
        game.moveMotherNature(game.firstPlayerIndex, 0);
        // checking if the movement has been done correctly
        assertEquals(0, game.getGameTable().getIsleManager().getIsleWithMotherNatureIndex());
        // checking if we moved on the next stage of actionPhase
        assertEquals(ActionPhases.CHOOSE_CLOUD, game.actionPhase);

        // manually setting the correct actionPhase stage
        //game.actionPhase = ActionPhases.MOVE_MOTHER_NATURE;
        // moving mother nature
        //game.moveMotherNature(game.firstPlayerIndex, 0);
        // checking if the movement has been done correctly
        //assertEquals(0, game.getGameTable().getIsleManager().getIsleWithMotherNatureIndex());
        // checking if we moved on the next stage of actionPhase
        //assertEquals(ActionPhases.CHOOSE_CLOUD, game.actionPhase);
    }

    /**
     * it tests if the influence is correctly calculated during a 4 players match and if the consequences are the ones we expected
     */
    @Test
    public void motherNatureMovement4Players() {
        Game game = new Game();
        AssistantCard cardPlayed1 = new AssistantCard(6, 3);
        AssistantCard cardPlayed2 = new AssistantCard(5, 3);
        AssistantCard cardPlayed3 = new AssistantCard(3, 2);
        AssistantCard cardPlayed4 = new AssistantCard(4, 2);
        game.addFirstPlayer("jack", GameMode.BASE, 4);
        game.addAnotherPlayer("calle");
        game.addAnotherPlayer("filo");
        game.addAnotherPlayer("bob");
        if (game.firstPlayerIndex == 0 && game.gamePhase == GamePhases.PLANNING_PHASE) {
            game.playAssistantCard(0, 6);
            game.playAssistantCard(1, 5);
            game.playAssistantCard(2, 3);
            game.playAssistantCard(3, 4);
            assertEquals(GamePhases.ACTION_PHASE, game.gamePhase);
        }
        if (game.firstPlayerIndex == 1 && game.gamePhase == GamePhases.PLANNING_PHASE) {
            game.playAssistantCard(1, 5);
            game.playAssistantCard(2, 3);
            game.playAssistantCard(3, 4);
            game.playAssistantCard(0, 6);
            assertEquals(GamePhases.ACTION_PHASE, game.gamePhase);
        }
        if (game.firstPlayerIndex == 2 && game.gamePhase == GamePhases.PLANNING_PHASE) {
            game.playAssistantCard(2, 3);
            game.playAssistantCard(3, 4);
            game.playAssistantCard(0, 6);
            game.playAssistantCard(1, 5);
            assertEquals(GamePhases.ACTION_PHASE, game.gamePhase);
        }
        if (game.firstPlayerIndex == 3 && game.gamePhase == GamePhases.PLANNING_PHASE) {
            game.playAssistantCard(3, 4);
            game.playAssistantCard(0, 6);
            game.playAssistantCard(1, 5);
            game.playAssistantCard(2, 3);
            assertEquals(GamePhases.ACTION_PHASE, game.gamePhase);
        }
        assertEquals(2, game.firstPlayerIndex);
        // skipping first movements phase by manually adding students and professors to players dining rooms
        game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getDiningRoom().addStudent(RealmColors.YELLOW);
        game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getDiningRoom().addProfessor(RealmColors.YELLOW);
        assertEquals(1, game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getDiningRoom().getNumberOfProfessors());
        game.getPlayerByIndex(0).getDashboard().getDiningRoom().addStudent(RealmColors.BLUE);
        game.getPlayerByIndex(0).getDashboard().getDiningRoom().addProfessor(RealmColors.BLUE);
        assertEquals(1, game.getPlayerByIndex(0).getDashboard().getDiningRoom().getNumberOfProfessors());
        game.getPlayerByIndex(1).getDashboard().getDiningRoom().addStudent(RealmColors.PINK);
        game.getPlayerByIndex(1).getDashboard().getDiningRoom().addProfessor(RealmColors.PINK);
        assertEquals(1, game.getPlayerByIndex(1).getDashboard().getDiningRoom().getNumberOfProfessors());
        game.getPlayerByIndex(3).getDashboard().getDiningRoom().addStudent(RealmColors.RED);
        game.getPlayerByIndex(3).getDashboard().getDiningRoom().addProfessor(RealmColors.RED);
        assertEquals(1, game.getPlayerByIndex(3).getDashboard().getDiningRoom().getNumberOfProfessors());
        // setting externally where mother nature is (isle 5)
        game.getGameTable().getIsleManager().getIsle(game.getGameTable().getIsleManager().getIsleWithMotherNatureIndex()).setMotherNature(false);
        game.getGameTable().getIsleManager().getIsle(5).setMotherNature(true);
        game.getGameTable().getIsleManager().setIsleWithMotherNatureIndex(5);
        // putting students and towers in the isles of interest
        game.getGameTable().getIsleManager().getIsle(6).removeStudent(RealmColors.YELLOW);
        game.getGameTable().getIsleManager().getIsle(6).addStudent(RealmColors.YELLOW);
        game.getGameTable().getIsleManager().getIsle(6).removeStudent(RealmColors.PINK);
        game.getGameTable().getIsleManager().getIsle(6).addStudent(RealmColors.PINK);
        game.getGameTable().getIsleManager().getIsle(6).removeStudent(RealmColors.BLUE);
        game.getGameTable().getIsleManager().getIsle(6).addStudent(RealmColors.BLUE);
        game.getGameTable().getIsleManager().getIsle(6).removeStudent(RealmColors.RED);
        game.getGameTable().getIsleManager().getIsle(6).addStudent(RealmColors.RED);
        game.getGameTable().getIsleManager().getIsle(6).setTower(TowerColors.BLACK);
        game.getPlayerByIndex(1).getDashboard().getTowerStorage().removeTower();
        game.getGameTable().getIsleManager().getIsle(8).removeStudent(RealmColors.YELLOW);
        game.getGameTable().getIsleManager().getIsle(8).addStudent(RealmColors.YELLOW);
        game.getGameTable().getIsleManager().getIsle(8).addStudent(RealmColors.YELLOW);
        game.getGameTable().getIsleManager().getIsle(8).removeStudent(RealmColors.PINK);
        game.getGameTable().getIsleManager().getIsle(8).addStudent(RealmColors.PINK);
        game.getGameTable().getIsleManager().getIsle(8).removeStudent(RealmColors.BLUE);
        game.getGameTable().getIsleManager().getIsle(8).addStudent(RealmColors.BLUE);
        game.getGameTable().getIsleManager().getIsle(8).removeStudent(RealmColors.RED);
        game.getGameTable().getIsleManager().getIsle(8).addStudent(RealmColors.RED);
        game.getGameTable().getIsleManager().getIsle(8).setTower(TowerColors.BLACK);
        game.getPlayerByIndex(1).getDashboard().getTowerStorage().removeTower();
        game.getGameTable().getIsleManager().getIsle(10).removeStudent(RealmColors.YELLOW);
        game.getGameTable().getIsleManager().getIsle(10).addStudent(RealmColors.YELLOW);
        game.getGameTable().getIsleManager().getIsle(10).addStudent(RealmColors.YELLOW);
        game.getGameTable().getIsleManager().getIsle(10).removeStudent(RealmColors.PINK);
        game.getGameTable().getIsleManager().getIsle(10).addStudent(RealmColors.PINK);
        game.getGameTable().getIsleManager().getIsle(10).removeStudent(RealmColors.BLUE);
        game.getGameTable().getIsleManager().getIsle(10).addStudent(RealmColors.BLUE);
        game.getGameTable().getIsleManager().getIsle(10).addStudent(RealmColors.BLUE);
        game.getGameTable().getIsleManager().getIsle(10).removeStudent(RealmColors.RED);
        game.getGameTable().getIsleManager().getIsle(10).addStudent(RealmColors.RED);
        game.getGameTable().getIsleManager().getIsle(10).setTower(TowerColors.BLACK);
        game.getPlayerByIndex(1).getDashboard().getTowerStorage().removeTower();
        // initial tower storages check
        assertEquals(8, game.getPlayerByIndex(0).getDashboard().getTowerStorage().getNumberOfTowers());
        assertEquals(TowerColors.WHITE, game.getPlayerByIndex(0).getDashboard().getTowerStorage().getTowerColor());
        assertEquals(5, game.getPlayerByIndex(1).getDashboard().getTowerStorage().getNumberOfTowers());
        assertEquals(TowerColors.BLACK, game.getPlayerByIndex(1).getDashboard().getTowerStorage().getTowerColor());
        assertEquals(0, game.getPlayerByIndex(2).getDashboard().getTowerStorage().getNumberOfTowers());
        assertEquals(TowerColors.NOCOLOR, game.getPlayerByIndex(2).getDashboard().getTowerStorage().getTowerColor());
        assertEquals(0, game.getPlayerByIndex(3).getDashboard().getTowerStorage().getNumberOfTowers());
        assertEquals(TowerColors.NOCOLOR, game.getPlayerByIndex(3).getDashboard().getTowerStorage().getTowerColor());
        // manually setting the correct actionPhase stage
        game.actionPhase = ActionPhases.MOVE_MOTHER_NATURE;
        // moving mother nature
        game.moveMotherNature(game.firstPlayerIndex, 6);
        // checking if the movement has been done correctly
        assertEquals(6, game.getGameTable().getIsleManager().getIsleWithMotherNatureIndex());
        // checking if the captain of squad2 still has 5 towers on its dashboard
        assertEquals(5, game.getPlayerByIndex(1).getDashboard().getTowerStorage().getNumberOfTowers());
        // due to the fact that squad1 has not the highest influence on the isle (2<3), we are checking if no tower has been removed from squad1's captain dining room...
        assertEquals(8, game.getPlayerByIndex(0).getDashboard().getTowerStorage().getNumberOfTowers());
        // ...and tower color remained the same as before
        assertEquals(TowerColors.BLACK, game.getGameTable().getIsleManager().getIsle(6).getTowersColor());
        // checking if we moved on the next stage of actionPhase
        assertEquals(ActionPhases.CHOOSE_CLOUD, game.actionPhase);

        // manually setting the correct actionPhase stage
        game.actionPhase = ActionPhases.MOVE_MOTHER_NATURE;
        // moving mother nature
        game.moveMotherNature(game.firstPlayerIndex, 8);
        // checking if the movement has been done correctly
        assertEquals(8, game.getGameTable().getIsleManager().getIsleWithMotherNatureIndex());
        // checking if the captain of squad2 still has 5 towers on its dashboard
        assertEquals(5, game.getPlayerByIndex(1).getDashboard().getTowerStorage().getNumberOfTowers());
        // due to the fact that squad1 has not the highest influence on the isle (3=3), we are checking if no tower has been removed from squad1's captain dining room...
        assertEquals(8, game.getPlayerByIndex(0).getDashboard().getTowerStorage().getNumberOfTowers());
        // ...and tower color remained the same as before
        assertEquals(TowerColors.BLACK, game.getGameTable().getIsleManager().getIsle(8).getTowersColor());
        // checking if we moved on the next stage of actionPhase
        assertEquals(ActionPhases.CHOOSE_CLOUD, game.actionPhase);

        // manually setting the correct actionPhase stage
        game.actionPhase = ActionPhases.MOVE_MOTHER_NATURE;
        // checking if the influence of each player is correct
        assertEquals(2, game.getGameTable().getIsleManager().getIsle(10).getInfluence(game.getPlayerByIndex(0)));
        assertEquals(2, game.getGameTable().getIsleManager().getIsle(10).getInfluence(game.getPlayerByIndex(1)));
        assertEquals(2, game.getGameTable().getIsleManager().getIsle(10).getInfluence(game.getPlayerByIndex(2)));
        assertEquals(1, game.getGameTable().getIsleManager().getIsle(10).getInfluence(game.getPlayerByIndex(3)));
        // moving mother nature
        game.moveMotherNature(game.firstPlayerIndex, 10);
        // checking if the movement has been done correctly
        assertEquals(10, game.getGameTable().getIsleManager().getIsleWithMotherNatureIndex());
        // checking if the captain of squad2 now has 6 towers on its dashboard
        assertEquals(6, game.getPlayerByIndex(1).getDashboard().getTowerStorage().getNumberOfTowers());
        // due to the fact that squad1 has the highest influence on the isle (4>3), we are checking if one tower has been removed from squad1's captain dining room...
        assertEquals(7, game.getPlayerByIndex(0).getDashboard().getTowerStorage().getNumberOfTowers());
        // ...and tower color on the isle has been updated with the tower color of squad1's captain
        assertEquals(TowerColors.WHITE, game.getGameTable().getIsleManager().getIsle(10).getTowersColor());
        // checking if we moved on the next stage of actionPhase
        assertEquals(ActionPhases.CHOOSE_CLOUD, game.actionPhase);
    }

    /**
     * it tests if isles are unified correctly in different situations
     */
    @Test
    public void checkUnifyIsle() {
        Game game = new Game();
        AssistantCard cardPlayed1 = new AssistantCard(4, 2);
        AssistantCard cardPlayed2 = new AssistantCard(3, 2);
        game.addFirstPlayer("jack", GameMode.BASE, 2);
        game.addAnotherPlayer("calle");
        if (game.firstPlayerIndex == 0 && game.gamePhase == GamePhases.PLANNING_PHASE) {
            game.playAssistantCard(0, 4);
            game.playAssistantCard(1, 3);
        }
        if (game.firstPlayerIndex == 1 && game.gamePhase == GamePhases.PLANNING_PHASE) {
            game.playAssistantCard(1, 3);
            game.playAssistantCard(0, 4);
        }
        // skipping first movements phase by manually adding students and professors to first player's dining room
        game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getDiningRoom().addStudent(RealmColors.YELLOW);
        game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getDiningRoom().addProfessor(RealmColors.YELLOW);
        assertEquals(1, game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getDiningRoom().getNumberOfProfessors());
        // setting externally where mother nature is (isle 1)
        game.getGameTable().getIsleManager().getIsle(game.getGameTable().getIsleManager().getIsleWithMotherNatureIndex()).setMotherNature(false);
        game.getGameTable().getIsleManager().getIsle(1).setMotherNature(true);
        game.getGameTable().getIsleManager().setIsleWithMotherNatureIndex(1);
        // putting students and towers on isles of interest
        game.getGameTable().getIsleManager().getIsle(2).removeStudent(RealmColors.YELLOW);
        game.getGameTable().getIsleManager().getIsle(2).addStudent(RealmColors.YELLOW);
        game.getGameTable().getIsleManager().getIsle(3).setTower(TowerColors.BLACK);
        game.getGameTable().getIsleManager().getIsle(4).setTower(TowerColors.WHITE);
        // manually setting the correct actionPhase stage
        game.actionPhase = ActionPhases.MOVE_MOTHER_NATURE;
        // moving mother nature
        game.moveMotherNature(game.firstPlayerIndex, 2);
        // checking if isles 2 and 3 have been unified correctly
        assertEquals(11, game.getGameTable().getIsleManager().getIsles().size());
        assertEquals(2, game.getGameTable().getIsleManager().getIsle(2).getNumOfIsles());
        assertEquals(2, game.getGameTable().getIsleManager().getIsleWithMotherNatureIndex());
        assertEquals(TowerColors.BLACK, game.getGameTable().getIsleManager().getIsle(2).getTowersColor());
        assertEquals(TowerColors.WHITE, game.getGameTable().getIsleManager().getIsle(3).getTowersColor());

        // putting students and towers on isles of interest
        game.getGameTable().getIsleManager().getIsle(3).removeStudent(RealmColors.YELLOW);
        game.getGameTable().getIsleManager().getIsle(3).addStudent(RealmColors.YELLOW);
        game.getGameTable().getIsleManager().getIsle(3).addStudent(RealmColors.YELLOW);
        game.getGameTable().getIsleManager().getIsle(4).setTower(TowerColors.BLACK);
        // manually setting the correct actionPhase stage
        game.actionPhase = ActionPhases.MOVE_MOTHER_NATURE;
        // moving mother nature
        game.moveMotherNature(game.firstPlayerIndex, 3);
        // checking if isles 2, 3 and 4 have been unified correctly
        assertEquals(9, game.getGameTable().getIsleManager().getIsles().size());
        assertEquals(4, game.getGameTable().getIsleManager().getIsle(2).getNumOfIsles());
        assertEquals(2, game.getGameTable().getIsleManager().getIsleWithMotherNatureIndex());
        assertEquals(TowerColors.BLACK, game.getGameTable().getIsleManager().getIsle(2).getTowersColor());

        // putting students and towers on isles of interest
        game.getGameTable().getIsleManager().getIsle(3).removeStudent(RealmColors.YELLOW);
        game.getGameTable().getIsleManager().getIsle(3).addStudent(RealmColors.YELLOW);
        game.getGameTable().getIsleManager().getIsle(3).setTower(TowerColors.WHITE);
        // manually setting the correct actionPhase stage
        game.actionPhase = ActionPhases.MOVE_MOTHER_NATURE;
        // moving mother nature
        game.moveMotherNature(game.firstPlayerIndex, 3);
        // checking if no isles have been unified
        assertEquals(9, game.getGameTable().getIsleManager().getIsles().size());
        assertEquals(4, game.getGameTable().getIsleManager().getIsle(2).getNumOfIsles());
        assertEquals(1, game.getGameTable().getIsleManager().getIsle(3).getNumOfIsles());
        assertEquals(3, game.getGameTable().getIsleManager().getIsleWithMotherNatureIndex());
        assertEquals(TowerColors.WHITE, game.getGameTable().getIsleManager().getIsle(3).getTowersColor());

        // setting externally where mother nature is (isle 7)
        game.getGameTable().getIsleManager().getIsle(game.getGameTable().getIsleManager().getIsleWithMotherNatureIndex()).setMotherNature(false);
        game.getGameTable().getIsleManager().getIsle(7).setMotherNature(true);
        game.getGameTable().getIsleManager().setIsleWithMotherNatureIndex(7);
        // putting students and towers on isles of interest
        game.getGameTable().getIsleManager().getIsle(8).removeStudent(RealmColors.YELLOW);
        game.getGameTable().getIsleManager().getIsle(8).addStudent(RealmColors.YELLOW);
        game.getGameTable().getIsleManager().getIsle(0).setTower(TowerColors.BLACK);
        // manually setting the correct actionPhase stage
        game.actionPhase = ActionPhases.MOVE_MOTHER_NATURE;
        // moving mother nature
        game.moveMotherNature(game.firstPlayerIndex, 8);
        // checking if isles 8 and 0 have been unified correctly
        assertEquals(8, game.getGameTable().getIsleManager().getIsles().size());
        assertEquals(2, game.getGameTable().getIsleManager().getIsle(7).getNumOfIsles());
        assertEquals(7, game.getGameTable().getIsleManager().getIsleWithMotherNatureIndex());
        assertEquals(TowerColors.BLACK, game.getGameTable().getIsleManager().getIsle(7).getTowersColor());
        assertEquals(TowerColors.NOCOLOR, game.getGameTable().getIsleManager().getIsle(0).getTowersColor());

        // putting students and towers on isles of interest
        game.getGameTable().getIsleManager().getIsle(0).removeStudent(RealmColors.YELLOW);
        game.getGameTable().getIsleManager().getIsle(0).addStudent(RealmColors.YELLOW);
        // manually setting the correct actionPhase stage
        game.actionPhase = ActionPhases.MOVE_MOTHER_NATURE;
        // moving mother nature
        game.moveMotherNature(game.firstPlayerIndex, 0);
        // checking if isles 7, 0 and 1 have been unified correctly
        assertEquals(6, game.getGameTable().getIsleManager().getIsles().size());
        assertEquals(7, game.getGameTable().getIsleManager().getIsle(5).getNumOfIsles());
        assertEquals(5, game.getGameTable().getIsleManager().getIsleWithMotherNatureIndex());
        assertEquals(TowerColors.BLACK, game.getGameTable().getIsleManager().getIsle(5).getTowersColor());
        assertEquals(TowerColors.WHITE, game.getGameTable().getIsleManager().getIsle(0).getTowersColor());
    }

    /**
     * it tests if students are correctly picked up from a cloud when it is not empty
     */
    @Test
    public void pickStudentsFromCloud() {
        int counter = 0;
        Game game = new Game();
        AssistantCard cardPlayed1 = new AssistantCard(4, 2);
        AssistantCard cardPlayed2 = new AssistantCard(3, 2);
        game.addFirstPlayer("jack", GameMode.BASE, 2);
        game.addAnotherPlayer("calle");
        if (game.firstPlayerIndex == 0 && game.gamePhase == GamePhases.PLANNING_PHASE) {
            game.playAssistantCard(0, 4);
            game.playAssistantCard(1, 3);
        }
        if (game.firstPlayerIndex == 1 && game.gamePhase == GamePhases.PLANNING_PHASE) {
            game.playAssistantCard(1, 3);
            game.playAssistantCard(0, 4);
        }
        // removing 3 students from entrance
        for (RealmColors rc : RealmColors.values()) {
            int studentsOfSpecifiedColor = game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getEntrance().getStudentsByColor(rc);
            for (int i = 0; i < studentsOfSpecifiedColor; i++) {
                game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getEntrance().removeStudent(rc);
                counter++;
                if (counter == 3)
                    break;
            }
            if (counter == 3)
                break;
        }
        // manually setting the correct actionPhase stage
        game.actionPhase = ActionPhases.CHOOSE_CLOUD;
        // choosing a cloud
        game.pickStudentsFromCloud(game.firstPlayerIndex, 0);
        // verifying if the first player has 7 students in its entrance now and if the chosen cloud is empty
        assertEquals(7, game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getEntrance().getNumberOfStudents());
        assertTrue(game.getGameTable().getCloud(0).isEmpty());
        // checking if we moved onto the next stage
        assertEquals(ActionPhases.MOVE_STUDENTS, game.actionPhase);
        assertEquals(CurrentOrder.SECOND_PLAYER, game.currentActivePlayer);

        counter = 0;
        // removing 3 students from entrance
        for (RealmColors rc : RealmColors.values()) {
            int studentsOfSpecifiedColor = game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getEntrance().getStudentsByColor(rc);
            for (int i = 0; i < studentsOfSpecifiedColor; i++) {
                game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getEntrance().removeStudent(rc);
                counter++;
                if (counter == 3)
                    break;
            }
            if (counter == 3)
                break;
        }
        // manually setting the correct actionPhase stage
        game.actionPhase = ActionPhases.CHOOSE_CLOUD;
        game.currentActivePlayer = CurrentOrder.FIRST_PLAYER;
        // choosing a cloud
        game.pickStudentsFromCloud(game.firstPlayerIndex, 0);
        // verifying if the first player has 4 students in its entrance now and if the chosen cloud is still empty
        assertEquals(4, game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getEntrance().getNumberOfStudents());
        assertTrue(game.getGameTable().getCloud(0).isEmpty());
        // checking if we didn't move onto the next stage
        assertEquals(ActionPhases.CHOOSE_CLOUD, game.actionPhase);
        assertEquals(CurrentOrder.FIRST_PLAYER, game.currentActivePlayer);
    }

    /**
     * it tests if after an action phase comes a planning phase with the correct order of players
     */
    @Test
    public void endActionPhase() {
        int counter = 0;
        Game game = new Game();
        AssistantCard cardPlayed1 = new AssistantCard(4, 2);
        AssistantCard cardPlayed2 = new AssistantCard(3, 2);
        game.addFirstPlayer("jack", GameMode.BASE, 2);
        game.addAnotherPlayer("calle");
        if (game.firstPlayerIndex == 0 && game.gamePhase == GamePhases.PLANNING_PHASE) {
            game.playAssistantCard(0, 4);
            game.playAssistantCard(1, 3);
        }
        if (game.firstPlayerIndex == 1 && game.gamePhase == GamePhases.PLANNING_PHASE) {
            game.playAssistantCard(1, 3);
            game.playAssistantCard(0, 4);
        }
        // removing 3 students from first player's entrance
        for (RealmColors rc : RealmColors.values()) {
            int studentsOfSpecifiedColor = game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getEntrance().getStudentsByColor(rc);
            for (int i = 0; i < studentsOfSpecifiedColor; i++) {
                game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getEntrance().removeStudent(rc);
                counter++;
                if (counter == 3)
                    break;
            }
            if (counter == 3)
                break;
        }
        // manually setting the correct actionPhase stage
        game.actionPhase = ActionPhases.CHOOSE_CLOUD;
        // choosing a cloud
        game.pickStudentsFromCloud(game.firstPlayerIndex, 0);
        // verifying if the first player has 7 students in its entrance now and if the chosen cloud is empty
        assertEquals(7, game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getEntrance().getNumberOfStudents());
        assertTrue(game.getGameTable().getCloud(0).isEmpty());
        // checking if we moved onto the next stage
        assertEquals(ActionPhases.MOVE_STUDENTS, game.actionPhase);
        assertEquals(CurrentOrder.SECOND_PLAYER, game.currentActivePlayer);

        counter = 0;
        // removing 3 students from second player's entrance
        for (RealmColors rc : RealmColors.values()) {
            int studentsOfSpecifiedColor = game.getPlayerByIndex(0).getDashboard().getEntrance().getStudentsByColor(rc);
            for (int i = 0; i < studentsOfSpecifiedColor; i++) {
                game.getPlayerByIndex(0).getDashboard().getEntrance().removeStudent(rc);
                counter++;
                if (counter == 3)
                    break;
            }
            if (counter == 3)
                break;
        }
        // manually setting the correct actionPhase stage
        game.actionPhase = ActionPhases.CHOOSE_CLOUD;
        // choosing a cloud
        game.pickStudentsFromCloud(0, 1);
        // verifying if the second player has 7 students in its entrance now
        assertEquals(7, game.getPlayerByIndex(0).getDashboard().getEntrance().getNumberOfStudents());
        // checking if we moved onto the next stage
        assertNull(game.getPlayerByIndex(0).getDiscardPile());
        assertNull(game.getPlayerByIndex(1).getDiscardPile());
        assertFalse(game.getGameTable().getCloud(0).isEmpty());
        assertFalse(game.getGameTable().getCloud(1).isEmpty());
        assertEquals(ActionPhases.MOVE_STUDENTS, game.actionPhase);
        assertEquals(GamePhases.PLANNING_PHASE, game.gamePhase);
        assertEquals(PlanningPhases.ASSISTANT_CARD_PHASE, game.planningPhase);
        assertEquals(CurrentOrder.FIRST_PLAYER, game.currentActivePlayer);
        assertEquals(CurrentOrder.FIRST_PLAYER, game.getPlayerByIndex(game.firstPlayerIndex).getOrder());
    }

    /**
     * it tests if a game ends properly when a player has placed his last tower on an isle
     */
    @Test
    public void endGame2PlayersNoMoreTowers() {
        Game game = new Game();
        AssistantCard cardPlayed1 = new AssistantCard(4, 2);
        AssistantCard cardPlayed2 = new AssistantCard(3, 2);
        game.addFirstPlayer("jack", GameMode.BASE, 2);
        game.addAnotherPlayer("calle");
        if (game.firstPlayerIndex == 0 && game.gamePhase == GamePhases.PLANNING_PHASE) {
            game.playAssistantCard(0, 4);
            game.playAssistantCard(1, 3);
        }
        if (game.firstPlayerIndex == 1 && game.gamePhase == GamePhases.PLANNING_PHASE) {
            game.playAssistantCard(1, 3);
            game.playAssistantCard(0, 4);
        }
        // manually removing 7 towers from first player's tower storage
        game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getTowerStorage().removeTower();
        game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getTowerStorage().removeTower();
        game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getTowerStorage().removeTower();
        game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getTowerStorage().removeTower();
        game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getTowerStorage().removeTower();
        game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getTowerStorage().removeTower();
        game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getTowerStorage().removeTower();
        // skipping first movements phase by manually adding students and professors to first player's dining room
        game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getDiningRoom().addStudent(RealmColors.YELLOW);
        game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getDiningRoom().addProfessor(RealmColors.YELLOW);
        assertEquals(1, game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getDiningRoom().getNumberOfProfessors());
        // setting externally where mother nature is (isle 1)
        game.getGameTable().getIsleManager().getIsle(game.getGameTable().getIsleManager().getIsleWithMotherNatureIndex()).setMotherNature(false);
        game.getGameTable().getIsleManager().getIsle(1).setMotherNature(true);
        game.getGameTable().getIsleManager().setIsleWithMotherNatureIndex(1);
        // putting students and towers on isles of interest
        game.getGameTable().getIsleManager().getIsle(2).removeStudent(RealmColors.YELLOW);
        game.getGameTable().getIsleManager().getIsle(2).addStudent(RealmColors.YELLOW);
        // manually setting the correct actionPhase stage
        game.actionPhase = ActionPhases.MOVE_MOTHER_NATURE;
        // moving mother nature
        game.moveMotherNature(game.firstPlayerIndex, 2);
        // checking if game is ended and if the winner is calle
        assertTrue(game.isGameEnded());
        assertFalse(game.isGameEndedInADraw());
        assertEquals("calle", game.getWinner());
    }

    /**
     * it tests if a game ends properly when there are 3 groups of isles on the game table and a player has more towers than the other
     */
    @Test
    public void endGame2Players3IslesNoDraw() {
        Game game = new Game();
        AssistantCard cardPlayed1 = new AssistantCard(4, 2);
        AssistantCard cardPlayed2 = new AssistantCard(3, 2);
        game.addFirstPlayer("jack", GameMode.BASE, 2);
        game.addAnotherPlayer("calle");
        if (game.firstPlayerIndex == 0 && game.gamePhase == GamePhases.PLANNING_PHASE) {
            game.playAssistantCard(0, 4);
            game.playAssistantCard(1, 3);
        }
        if (game.firstPlayerIndex == 1 && game.gamePhase == GamePhases.PLANNING_PHASE) {
            game.playAssistantCard(1, 3);
            game.playAssistantCard(0, 4);
        }
        // manually removing 5 towers from players' tower storages
        game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getTowerStorage().removeTower();
        game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getTowerStorage().removeTower();
        game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getTowerStorage().removeTower();
        game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getTowerStorage().removeTower();
        game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getTowerStorage().removeTower();
        game.getPlayerByIndex(0).getDashboard().getTowerStorage().removeTower();
        game.getPlayerByIndex(0).getDashboard().getTowerStorage().removeTower();
        game.getPlayerByIndex(0).getDashboard().getTowerStorage().removeTower();
        game.getPlayerByIndex(0).getDashboard().getTowerStorage().removeTower();
        game.getPlayerByIndex(0).getDashboard().getTowerStorage().removeTower();
        // skipping first movements phase by manually adding students and professors to first player's dining room
        game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getDiningRoom().addStudent(RealmColors.YELLOW);
        game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getDiningRoom().addProfessor(RealmColors.YELLOW);
        assertEquals(1, game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getDiningRoom().getNumberOfProfessors());
        // unifying some isles...
        game.getGameTable().getIsleManager().unifyIsle(0, 1);
        game.getGameTable().getIsleManager().unifyIsle(0, 1);
        game.getGameTable().getIsleManager().unifyIsle(0, 1);
        game.getGameTable().getIsleManager().unifyIsle(0, 1);
        game.getGameTable().getIsleManager().unifyIsle(2, 3);
        game.getGameTable().getIsleManager().unifyIsle(2, 3);
        game.getGameTable().getIsleManager().unifyIsle(2, 3);
        game.getGameTable().getIsleManager().unifyIsle(2, 3);
        assertEquals(4, game.getGameTable().getIsleManager().getIsles().size());
        // setting tower colors...
        game.getGameTable().getIsleManager().getIsle(0).setTower(TowerColors.BLACK);
        game.getGameTable().getIsleManager().getIsle(2).setTower(TowerColors.WHITE);
        // setting externally where mother nature is (isle 0)
        game.getGameTable().getIsleManager().getIsle(game.getGameTable().getIsleManager().getIsleWithMotherNatureIndex()).setMotherNature(false);
        game.getGameTable().getIsleManager().getIsle(0).setMotherNature(true);
        game.getGameTable().getIsleManager().setIsleWithMotherNatureIndex(0);
        // putting students and towers on isles of interest
        game.getGameTable().getIsleManager().getIsle(1).removeStudent(RealmColors.YELLOW);
        game.getGameTable().getIsleManager().getIsle(1).addStudent(RealmColors.YELLOW);
        // manually setting the correct actionPhase stage
        game.actionPhase = ActionPhases.MOVE_MOTHER_NATURE;
        // moving mother nature
        game.moveMotherNature(game.firstPlayerIndex, 1);
        // checking union between isles 0 and 1
        assertEquals(3, game.getGameTable().getIsleManager().getIsles().size());
        assertEquals(6, game.getGameTable().getIsleManager().getIsle(0).getNumOfIsles());
        assertEquals(2, game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getTowerStorage().getNumberOfTowers());
        // checking if game is ended and if the winner is calle
        assertTrue(game.isGameEnded());
        assertFalse(game.isGameEndedInADraw());
        assertEquals("calle", game.getWinner());
    }

    /**
     * it tests if a game ends properly when there are 3 groups of isles on the game table and a player has the same amount of towers of the other but more professors
     * (it can happen only during a 3 Players match)
     */
    @Test
    public void endGame3Players3IslesWithTowersDraw() {
        Game game = new Game();
        AssistantCard cardPlayed1 = new AssistantCard(4, 2);
        AssistantCard cardPlayed2 = new AssistantCard(3, 2);
        AssistantCard cardPlayed3 = new AssistantCard(5, 3);
        game.addFirstPlayer("jack", GameMode.BASE, 3);
        game.addAnotherPlayer("calle");
        game.addAnotherPlayer("filo");
        if (game.firstPlayerIndex == 0 && game.gamePhase == GamePhases.PLANNING_PHASE) {
            game.playAssistantCard(0, 4);
            game.playAssistantCard(1, 3);
            game.playAssistantCard(2, 5);
        }
        if (game.firstPlayerIndex == 1 && game.gamePhase == GamePhases.PLANNING_PHASE) {
            game.playAssistantCard(1, 3);
            game.playAssistantCard(2, 5);
            game.playAssistantCard(0, 4);
        }
        if (game.firstPlayerIndex == 2 && game.gamePhase == GamePhases.PLANNING_PHASE) {
            game.playAssistantCard(2, 5);
            game.playAssistantCard(0, 4);
            game.playAssistantCard(1, 3);
        }
        // manually removing 3 or 4 towers from players' tower storages
        game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getTowerStorage().removeTower();
        game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getTowerStorage().removeTower();
        game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getTowerStorage().removeTower();
        game.getPlayerByIndex(0).getDashboard().getTowerStorage().removeTower();
        game.getPlayerByIndex(0).getDashboard().getTowerStorage().removeTower();
        game.getPlayerByIndex(0).getDashboard().getTowerStorage().removeTower();
        game.getPlayerByIndex(0).getDashboard().getTowerStorage().removeTower();
        game.getPlayerByIndex(2).getDashboard().getTowerStorage().removeTower();
        game.getPlayerByIndex(2).getDashboard().getTowerStorage().removeTower();
        game.getPlayerByIndex(2).getDashboard().getTowerStorage().removeTower();
        game.getPlayerByIndex(2).getDashboard().getTowerStorage().removeTower();
        // skipping first movements phase by manually adding students and professors to players' dining rooms
        game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getDiningRoom().addStudent(RealmColors.YELLOW);
        game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getDiningRoom().addProfessor(RealmColors.YELLOW);
        game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getDiningRoom().addStudent(RealmColors.RED);
        game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getDiningRoom().addProfessor(RealmColors.RED);
        game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getDiningRoom().addStudent(RealmColors.GREEN);
        game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getDiningRoom().addProfessor(RealmColors.GREEN);
        assertEquals(3, game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getDiningRoom().getNumberOfProfessors());
        game.getPlayerByIndex(0).getDashboard().getDiningRoom().addStudent(RealmColors.PINK);
        game.getPlayerByIndex(0).getDashboard().getDiningRoom().addProfessor(RealmColors.PINK);
        assertEquals(1, game.getPlayerByIndex(0).getDashboard().getDiningRoom().getNumberOfProfessors());
        game.getPlayerByIndex(2).getDashboard().getDiningRoom().addStudent(RealmColors.BLUE);
        game.getPlayerByIndex(2).getDashboard().getDiningRoom().addProfessor(RealmColors.BLUE);
        assertEquals(1, game.getPlayerByIndex(2).getDashboard().getDiningRoom().getNumberOfProfessors());
        // unifying some isles...
        game.getGameTable().getIsleManager().unifyIsle(0, 1);
        game.getGameTable().getIsleManager().unifyIsle(0, 1);
        game.getGameTable().getIsleManager().unifyIsle(0, 1);
        game.getGameTable().getIsleManager().unifyIsle(1, 2);
        game.getGameTable().getIsleManager().unifyIsle(1, 2);
        game.getGameTable().getIsleManager().unifyIsle(1, 2);
        game.getGameTable().getIsleManager().unifyIsle(2, 3);
        game.getGameTable().getIsleManager().unifyIsle(2, 3);
        assertEquals(4, game.getGameTable().getIsleManager().getIsles().size());
        // setting tower colors...
        game.getGameTable().getIsleManager().getIsle(0).setTower(TowerColors.WHITE);
        game.getGameTable().getIsleManager().getIsle(1).setTower(TowerColors.GREY);
        game.getGameTable().getIsleManager().getIsle(2).setTower(TowerColors.BLACK);
        // setting externally where mother nature is (isle 2)
        game.getGameTable().getIsleManager().getIsle(game.getGameTable().getIsleManager().getIsleWithMotherNatureIndex()).setMotherNature(false);
        game.getGameTable().getIsleManager().getIsle(2).setMotherNature(true);
        game.getGameTable().getIsleManager().setIsleWithMotherNatureIndex(2);
        // putting students and towers on isle of interest
        game.getGameTable().getIsleManager().getIsle(3).removeStudent(RealmColors.YELLOW);
        game.getGameTable().getIsleManager().getIsle(3).addStudent(RealmColors.YELLOW);
        game.getGameTable().getIsleManager().getIsle(3).addStudent(RealmColors.YELLOW);
        assertEquals(2, game.getGameTable().getIsleManager().getIsle(3).getStudentsByColor(RealmColors.YELLOW));
        // manually setting the correct actionPhase stage
        game.actionPhase = ActionPhases.MOVE_MOTHER_NATURE;
        // moving mother nature
        game.moveMotherNature(game.firstPlayerIndex, 3);
        // checking union between isles 2 and 3
        assertEquals(3, game.getGameTable().getIsleManager().getIsles().size());
        assertEquals(4, game.getGameTable().getIsleManager().getIsle(2).getNumOfIsles());
        assertEquals(2, game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getTowerStorage().getNumberOfTowers());
        // checking if game is ended and if the winner is calle
        assertTrue(game.isGameEnded());
        assertFalse(game.isGameEndedInADraw());
        assertEquals("calle", game.getWinner());
    }

    /**
     * it tests if a 3 Players game concluded with 3 groups of isles ends in a draw
     * (it can be possible only when the two players with more towers has the same amount of towers and professors)
     */
    @Test
    public void game3IslesEndedInADraw() {
        Game game = new Game();
        AssistantCard cardPlayed1 = new AssistantCard(4, 2);
        AssistantCard cardPlayed2 = new AssistantCard(3, 2);
        AssistantCard cardPlayed3 = new AssistantCard(5, 3);
        game.addFirstPlayer("jack", GameMode.BASE, 3);
        game.addAnotherPlayer("calle");
        game.addAnotherPlayer("filo");
        if (game.firstPlayerIndex == 0 && game.gamePhase == GamePhases.PLANNING_PHASE) {
            game.playAssistantCard(0, 4);
            game.playAssistantCard(1, 3);
            game.playAssistantCard(2, 5);
        }
        if (game.firstPlayerIndex == 1 && game.gamePhase == GamePhases.PLANNING_PHASE) {
            game.playAssistantCard(1, 3);
            game.playAssistantCard(2, 5);
            game.playAssistantCard(0, 4);
        }
        if (game.firstPlayerIndex == 2 && game.gamePhase == GamePhases.PLANNING_PHASE) {
            game.playAssistantCard(2, 5);
            game.playAssistantCard(0, 4);
            game.playAssistantCard(1, 3);
        }
        // manually removing 3 or 4 towers from players' tower storages
        game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getTowerStorage().removeTower();
        game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getTowerStorage().removeTower();
        game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getTowerStorage().removeTower();
        game.getPlayerByIndex(0).getDashboard().getTowerStorage().removeTower();
        game.getPlayerByIndex(0).getDashboard().getTowerStorage().removeTower();
        game.getPlayerByIndex(0).getDashboard().getTowerStorage().removeTower();
        game.getPlayerByIndex(0).getDashboard().getTowerStorage().removeTower();
        game.getPlayerByIndex(2).getDashboard().getTowerStorage().removeTower();
        game.getPlayerByIndex(2).getDashboard().getTowerStorage().removeTower();
        game.getPlayerByIndex(2).getDashboard().getTowerStorage().removeTower();
        game.getPlayerByIndex(2).getDashboard().getTowerStorage().removeTower();
        // skipping first movements phase by manually adding students and professors to players' dining rooms
        game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getDiningRoom().addStudent(RealmColors.YELLOW);
        game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getDiningRoom().addProfessor(RealmColors.YELLOW);
        game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getDiningRoom().addStudent(RealmColors.RED);
        game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getDiningRoom().addProfessor(RealmColors.RED);
        assertEquals(2, game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getDiningRoom().getNumberOfProfessors());
        game.getPlayerByIndex(0).getDashboard().getDiningRoom().addStudent(RealmColors.PINK);
        game.getPlayerByIndex(0).getDashboard().getDiningRoom().addProfessor(RealmColors.PINK);
        game.getPlayerByIndex(0).getDashboard().getDiningRoom().addStudent(RealmColors.GREEN);
        game.getPlayerByIndex(0).getDashboard().getDiningRoom().addProfessor(RealmColors.GREEN);
        assertEquals(2, game.getPlayerByIndex(0).getDashboard().getDiningRoom().getNumberOfProfessors());
        game.getPlayerByIndex(2).getDashboard().getDiningRoom().addStudent(RealmColors.BLUE);
        game.getPlayerByIndex(2).getDashboard().getDiningRoom().addProfessor(RealmColors.BLUE);
        assertEquals(1, game.getPlayerByIndex(2).getDashboard().getDiningRoom().getNumberOfProfessors());
        // unifying some isles...
        game.getGameTable().getIsleManager().unifyIsle(0, 1);
        game.getGameTable().getIsleManager().unifyIsle(0, 1);
        game.getGameTable().getIsleManager().unifyIsle(0, 1);
        game.getGameTable().getIsleManager().unifyIsle(1, 2);
        game.getGameTable().getIsleManager().unifyIsle(1, 2);
        game.getGameTable().getIsleManager().unifyIsle(1, 2);
        game.getGameTable().getIsleManager().unifyIsle(2, 3);
        game.getGameTable().getIsleManager().unifyIsle(2, 3);
        assertEquals(4, game.getGameTable().getIsleManager().getIsles().size());
        // setting tower colors...
        game.getGameTable().getIsleManager().getIsle(0).setTower(TowerColors.WHITE);
        game.getGameTable().getIsleManager().getIsle(1).setTower(TowerColors.GREY);
        game.getGameTable().getIsleManager().getIsle(2).setTower(TowerColors.BLACK);
        // setting externally where mother nature is (isle 2)
        game.getGameTable().getIsleManager().getIsle(game.getGameTable().getIsleManager().getIsleWithMotherNatureIndex()).setMotherNature(false);
        game.getGameTable().getIsleManager().getIsle(2).setMotherNature(true);
        game.getGameTable().getIsleManager().setIsleWithMotherNatureIndex(2);
        // putting students and towers on isle of interest
        game.getGameTable().getIsleManager().getIsle(3).removeStudent(RealmColors.YELLOW);
        game.getGameTable().getIsleManager().getIsle(3).addStudent(RealmColors.YELLOW);
        game.getGameTable().getIsleManager().getIsle(3).addStudent(RealmColors.YELLOW);
        assertEquals(2, game.getGameTable().getIsleManager().getIsle(3).getStudentsByColor(RealmColors.YELLOW));
        // manually setting the correct actionPhase stage
        game.actionPhase = ActionPhases.MOVE_MOTHER_NATURE;
        // moving mother nature
        game.moveMotherNature(game.firstPlayerIndex, 3);
        // checking union between isles 2 and 3
        assertEquals(3, game.getGameTable().getIsleManager().getIsles().size());
        assertEquals(4, game.getGameTable().getIsleManager().getIsle(2).getNumOfIsles());
        assertEquals(2, game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getTowerStorage().getNumberOfTowers());
        // checking if game is ended in a draw
        assertTrue(game.isGameEnded());
        assertTrue(game.isGameEndedInADraw());
    }

    /**
     * it tests if a game ends properly when a squad has placed his last tower on an isle
     */
    @Test
    public void endGame4PlayersNoMoreTowers() {
        Game game = new Game();
        AssistantCard cardPlayed1 = new AssistantCard(6, 3);
        AssistantCard cardPlayed2 = new AssistantCard(5, 3);
        AssistantCard cardPlayed3 = new AssistantCard(3, 2);
        AssistantCard cardPlayed4 = new AssistantCard(4, 2);
        game.addFirstPlayer("jack", GameMode.BASE, 4);
        game.addAnotherPlayer("calle");
        game.addAnotherPlayer("filo");
        game.addAnotherPlayer("bob");
        if (game.firstPlayerIndex == 0 && game.gamePhase == GamePhases.PLANNING_PHASE) {
            game.playAssistantCard(0, 6);
            game.playAssistantCard(1, 5);
            game.playAssistantCard(2, 3);
            game.playAssistantCard(3, 4);
            assertEquals(GamePhases.ACTION_PHASE, game.gamePhase);
        }
        if (game.firstPlayerIndex == 1 && game.gamePhase == GamePhases.PLANNING_PHASE) {
            game.playAssistantCard(1, 5);
            game.playAssistantCard(2, 3);
            game.playAssistantCard(3, 4);
            game.playAssistantCard(0, 6);
            assertEquals(GamePhases.ACTION_PHASE, game.gamePhase);
        }
        if (game.firstPlayerIndex == 2 && game.gamePhase == GamePhases.PLANNING_PHASE) {
            game.playAssistantCard(2, 3);
            game.playAssistantCard(3, 4);
            game.playAssistantCard(0, 6);
            game.playAssistantCard(1, 5);
            assertEquals(GamePhases.ACTION_PHASE, game.gamePhase);
        }
        if (game.firstPlayerIndex == 3 && game.gamePhase == GamePhases.PLANNING_PHASE) {
            game.playAssistantCard(3, 4);
            game.playAssistantCard(0, 6);
            game.playAssistantCard(1, 5);
            game.playAssistantCard(2, 3);
            assertEquals(GamePhases.ACTION_PHASE, game.gamePhase);
        }
        assertEquals(2, game.firstPlayerIndex);
        // manually removing 7 towers from squad1 captain's tower storage
        game.getPlayerByIndex(0).getDashboard().getTowerStorage().removeTower();
        game.getPlayerByIndex(0).getDashboard().getTowerStorage().removeTower();
        game.getPlayerByIndex(0).getDashboard().getTowerStorage().removeTower();
        game.getPlayerByIndex(0).getDashboard().getTowerStorage().removeTower();
        game.getPlayerByIndex(0).getDashboard().getTowerStorage().removeTower();
        game.getPlayerByIndex(0).getDashboard().getTowerStorage().removeTower();
        game.getPlayerByIndex(0).getDashboard().getTowerStorage().removeTower();
        // skipping first movements phase by manually adding students and professors to first player's dining room
        game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getDiningRoom().addStudent(RealmColors.YELLOW);
        game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getDiningRoom().addProfessor(RealmColors.YELLOW);
        assertEquals(1, game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getDiningRoom().getNumberOfProfessors());
        // setting externally where mother nature is (isle 1)
        game.getGameTable().getIsleManager().getIsle(game.getGameTable().getIsleManager().getIsleWithMotherNatureIndex()).setMotherNature(false);
        game.getGameTable().getIsleManager().getIsle(1).setMotherNature(true);
        game.getGameTable().getIsleManager().setIsleWithMotherNatureIndex(1);
        // putting students and towers on isles of interest
        game.getGameTable().getIsleManager().getIsle(2).removeStudent(RealmColors.YELLOW);
        game.getGameTable().getIsleManager().getIsle(2).addStudent(RealmColors.YELLOW);
        // manually setting the correct actionPhase stage
        game.actionPhase = ActionPhases.MOVE_MOTHER_NATURE;
        // moving mother nature
        game.moveMotherNature(game.firstPlayerIndex, 2);
        // checking if squad1 captain's has 0 towers in is tower storage
        assertEquals(0, game.getPlayerByIndex(0).getDashboard().getTowerStorage().getNumberOfTowers());
        // checking if game is ended and if the winner is SQUAD1
        assertTrue(game.isGameEnded());
        assertFalse(game.isGameEndedInADraw());
        assertEquals("SQUAD1", game.getWinner());
    }

    /**
     * it tests if a game ends properly when there are 3 groups of isles on the game table and a squad has more towers than the other
     */
    @Test
    public void endGame4Players3IslesNoDraw() {
        Game game = new Game();
        AssistantCard cardPlayed1 = new AssistantCard(6, 3);
        AssistantCard cardPlayed2 = new AssistantCard(5, 3);
        AssistantCard cardPlayed3 = new AssistantCard(3, 2);
        AssistantCard cardPlayed4 = new AssistantCard(4, 2);
        game.addFirstPlayer("jack", GameMode.BASE, 4);
        game.addAnotherPlayer("calle");
        game.addAnotherPlayer("filo");
        game.addAnotherPlayer("bob");
        if (game.firstPlayerIndex == 0 && game.gamePhase == GamePhases.PLANNING_PHASE) {
            game.playAssistantCard(0, 6);
            game.playAssistantCard(1, 5);
            game.playAssistantCard(2, 3);
            game.playAssistantCard(3, 4);
            assertEquals(GamePhases.ACTION_PHASE, game.gamePhase);
        }
        if (game.firstPlayerIndex == 1 && game.gamePhase == GamePhases.PLANNING_PHASE) {
            game.playAssistantCard(1, 5);
            game.playAssistantCard(2, 3);
            game.playAssistantCard(3, 4);
            game.playAssistantCard(0, 6);
            assertEquals(GamePhases.ACTION_PHASE, game.gamePhase);
        }
        if (game.firstPlayerIndex == 2 && game.gamePhase == GamePhases.PLANNING_PHASE) {
            game.playAssistantCard(2, 3);
            game.playAssistantCard(3, 4);
            game.playAssistantCard(0, 6);
            game.playAssistantCard(1, 5);
            assertEquals(GamePhases.ACTION_PHASE, game.gamePhase);
        }
        if (game.firstPlayerIndex == 3 && game.gamePhase == GamePhases.PLANNING_PHASE) {
            game.playAssistantCard(3, 4);
            game.playAssistantCard(0, 6);
            game.playAssistantCard(1, 5);
            game.playAssistantCard(2, 3);
            assertEquals(GamePhases.ACTION_PHASE, game.gamePhase);
        }
        assertEquals(2, game.firstPlayerIndex);
        // manually removing 5 towers from captains' tower storages
        game.getPlayerByIndex(0).getDashboard().getTowerStorage().removeTower();
        game.getPlayerByIndex(0).getDashboard().getTowerStorage().removeTower();
        game.getPlayerByIndex(0).getDashboard().getTowerStorage().removeTower();
        game.getPlayerByIndex(0).getDashboard().getTowerStorage().removeTower();
        game.getPlayerByIndex(0).getDashboard().getTowerStorage().removeTower();
        game.getPlayerByIndex(1).getDashboard().getTowerStorage().removeTower();
        game.getPlayerByIndex(1).getDashboard().getTowerStorage().removeTower();
        game.getPlayerByIndex(1).getDashboard().getTowerStorage().removeTower();
        game.getPlayerByIndex(1).getDashboard().getTowerStorage().removeTower();
        game.getPlayerByIndex(1).getDashboard().getTowerStorage().removeTower();
        // skipping first movements phase by manually adding students and professors to first player's dining room
        game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getDiningRoom().addStudent(RealmColors.YELLOW);
        game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getDiningRoom().addProfessor(RealmColors.YELLOW);
        assertEquals(1, game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getDiningRoom().getNumberOfProfessors());
        // unifying some isles...
        game.getGameTable().getIsleManager().unifyIsle(0, 1);
        game.getGameTable().getIsleManager().unifyIsle(0, 1);
        game.getGameTable().getIsleManager().unifyIsle(0, 1);
        game.getGameTable().getIsleManager().unifyIsle(0, 1);
        game.getGameTable().getIsleManager().unifyIsle(2, 3);
        game.getGameTable().getIsleManager().unifyIsle(2, 3);
        game.getGameTable().getIsleManager().unifyIsle(2, 3);
        game.getGameTable().getIsleManager().unifyIsle(2, 3);
        assertEquals(4, game.getGameTable().getIsleManager().getIsles().size());
        // setting tower colors...
        game.getGameTable().getIsleManager().getIsle(0).setTower(TowerColors.WHITE);
        game.getGameTable().getIsleManager().getIsle(2).setTower(TowerColors.BLACK);
        // setting externally where mother nature is (isle 0)
        game.getGameTable().getIsleManager().getIsle(game.getGameTable().getIsleManager().getIsleWithMotherNatureIndex()).setMotherNature(false);
        game.getGameTable().getIsleManager().getIsle(0).setMotherNature(true);
        game.getGameTable().getIsleManager().setIsleWithMotherNatureIndex(0);
        // putting students and towers on isles of interest
        game.getGameTable().getIsleManager().getIsle(1).removeStudent(RealmColors.YELLOW);
        game.getGameTable().getIsleManager().getIsle(1).addStudent(RealmColors.YELLOW);
        // manually setting the correct actionPhase stage
        game.actionPhase = ActionPhases.MOVE_MOTHER_NATURE;
        // moving mother nature
        game.moveMotherNature(game.firstPlayerIndex, 1);
        // checking union between isles 0 and 1
        assertEquals(3, game.getGameTable().getIsleManager().getIsles().size());
        assertEquals(6, game.getGameTable().getIsleManager().getIsle(0).getNumOfIsles());
        assertEquals(2, game.getPlayerByIndex(0).getDashboard().getTowerStorage().getNumberOfTowers());
        // checking if game is ended and if the winner is SQUAD1
        assertTrue(game.isGameEnded());
        assertFalse(game.isGameEndedInADraw());
        assertEquals("SQUAD1", game.getWinner());
    }

    @Test
    public void lastRoundNoMoreStudents() {
        Game game = new Game();
        game.addFirstPlayer("jack", GameMode.BASE, 2);
        game.addAnotherPlayer("calle");
        if (game.firstPlayerIndex == 0 && game.gamePhase == GamePhases.PLANNING_PHASE) {
            game.playAssistantCard(0, 4);
            game.playAssistantCard(1, 3);
        }
        if (game.firstPlayerIndex == 1 && game.gamePhase == GamePhases.PLANNING_PHASE) {
            game.playAssistantCard(1, 3);
            game.playAssistantCard(0, 4);
        }
        // leaving 5 students in the bag
        for (int i = 0; i < 95; i++)
            game.getGameTable().getBag().draw();
        assertEquals(5, game.getGameTable().getBag().getNumberOfStudents());
        // skipping to the phase of interest
        game.actionPhase = ActionPhases.CHOOSE_CLOUD;
        game.pickStudentsFromCloud(game.firstPlayerIndex, 0);
        game.actionPhase = ActionPhases.CHOOSE_CLOUD;
        game.pickStudentsFromCloud(0, 1);
        // checking if we are in the last round and the clouds are empty
        assertTrue(game.isLastRound());
        assertTrue(game.getGameTable().getCloud(0).isEmpty());
        assertTrue(game.getGameTable().getCloud(1).isEmpty());
        assertEquals(PlanningPhases.ASSISTANT_CARD_PHASE, game.planningPhase);
    }

    @Test
    public void endGameNoMoreStudentsDraw() {
        Game game = new Game();
        game.addFirstPlayer("jack", GameMode.BASE, 2);
        game.addAnotherPlayer("calle");
        if (game.firstPlayerIndex == 0 && game.gamePhase == GamePhases.PLANNING_PHASE) {
            game.playAssistantCard(0, 4);
            game.playAssistantCard(1, 3);
        }
        if (game.firstPlayerIndex == 1 && game.gamePhase == GamePhases.PLANNING_PHASE) {
            game.playAssistantCard(1, 3);
            game.playAssistantCard(0, 4);
        }
        // leaving 5 students in the bag
        for (int i = 0; i < 95; i++)
            game.getGameTable().getBag().draw();
        assertEquals(5, game.getGameTable().getBag().getNumberOfStudents());
        // skipping to the phase of interest
        game.actionPhase = ActionPhases.CHOOSE_CLOUD;
        game.pickStudentsFromCloud(game.firstPlayerIndex, 0);
        game.actionPhase = ActionPhases.CHOOSE_CLOUD;
        game.pickStudentsFromCloud(0, 1);
        // checking if we are in the last round and the clouds are empty
        assertTrue(game.isLastRound());
        assertTrue(game.getGameTable().getCloud(0).isEmpty());
        assertTrue(game.getGameTable().getCloud(1).isEmpty());
        assertEquals(PlanningPhases.ASSISTANT_CARD_PHASE, game.planningPhase);
        // executing last round... (skipping move student phases)
        game.playAssistantCard(1, 1);
        game.playAssistantCard(0, 2);
        game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getDiningRoom().addStudent(RealmColors.YELLOW);
        game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getDiningRoom().addProfessor(RealmColors.YELLOW);
        assertEquals(1, game.getPlayerByIndex(game.firstPlayerIndex).getDashboard().getDiningRoom().getNumberOfProfessors());
        game.getPlayerByIndex(0).getDashboard().getDiningRoom().addStudent(RealmColors.BLUE);
        game.getPlayerByIndex(0).getDashboard().getDiningRoom().addProfessor(RealmColors.BLUE);
        assertEquals(1, game.getPlayerByIndex(0).getDashboard().getDiningRoom().getNumberOfProfessors());

        game.getGameTable().getIsleManager().getIsle(game.getGameTable().getIsleManager().getIsleWithMotherNatureIndex()).setMotherNature(false);
        game.getGameTable().getIsleManager().getIsle(5).setMotherNature(true);
        game.getGameTable().getIsleManager().setIsleWithMotherNatureIndex(5);

        game.getGameTable().getIsleManager().getIsle(6).removeStudent(RealmColors.YELLOW);
        game.getGameTable().getIsleManager().getIsle(6).addStudent(RealmColors.YELLOW);
        game.getGameTable().getIsleManager().getIsle(6).addStudent(RealmColors.YELLOW);
        game.getGameTable().getIsleManager().getIsle(7).removeStudent(RealmColors.BLUE);
        game.getGameTable().getIsleManager().getIsle(7).addStudent(RealmColors.BLUE);
        game.getGameTable().getIsleManager().getIsle(7).addStudent(RealmColors.BLUE);

        game.actionPhase = ActionPhases.MOVE_MOTHER_NATURE;
        game.moveMotherNature(game.firstPlayerIndex, 6);
        game.actionPhase = ActionPhases.MOVE_MOTHER_NATURE;
        game.moveMotherNature(0, 7);
        // checking if the game ended properly
        assertTrue(game.isGameEnded());
        assertTrue(game.isGameEndedInADraw());
    }

    @Test
    public void lastRoundNoMoreAssistCard() {
        Game game = new Game();
        game.addFirstPlayer("jack", GameMode.BASE, 2);
        game.addAnotherPlayer("calle");

        // leaving 1 assistant card to the player of index 1
        game.getPlayerByIndex(1).mageDeck.subList(0, 9).clear();

        if (game.firstPlayerIndex == 0 && game.gamePhase == GamePhases.PLANNING_PHASE) {
            game.playAssistantCard(0, 4);
            game.playAssistantCard(1, 10);
        }
        if (game.firstPlayerIndex == 1 && game.gamePhase == GamePhases.PLANNING_PHASE) {
            game.playAssistantCard(1, 10);
            game.playAssistantCard(0, 4);
        }
        // checking if we are in the last round due to the fact that the first player has just played his last assistant card
        assertTrue(game.isLastRound());
        assertTrue(game.getPlayerByIndex(1).isMageDeckEmpty());
    }

    @Test
    public void endGameNoMoreAssistCard() {
        Game game = new Game();
        game.addFirstPlayer("jack", GameMode.BASE, 2);
        game.addAnotherPlayer("calle");

        // leaving 1 assistant card to the player of index 1
        game.getPlayerByIndex(1).mageDeck.subList(0, 9).clear();

        if (game.firstPlayerIndex == 0 && game.gamePhase == GamePhases.PLANNING_PHASE) {
            game.playAssistantCard(0, 4);
            game.playAssistantCard(1, 10);
        }
        if (game.firstPlayerIndex == 1 && game.gamePhase == GamePhases.PLANNING_PHASE) {
            game.playAssistantCard(1, 10);
            game.playAssistantCard(0, 4);
        }
        // executing last round... (skipping move student phases)
        game.getPlayerByIndex(0).getDashboard().getDiningRoom().addStudent(RealmColors.YELLOW);
        game.getPlayerByIndex(0).getDashboard().getDiningRoom().addProfessor(RealmColors.YELLOW);
        assertEquals(1, game.getPlayerByIndex(0).getDashboard().getDiningRoom().getNumberOfProfessors());

        game.getGameTable().getIsleManager().getIsle(game.getGameTable().getIsleManager().getIsleWithMotherNatureIndex()).setMotherNature(false);
        game.getGameTable().getIsleManager().getIsle(5).setMotherNature(true);
        game.getGameTable().getIsleManager().setIsleWithMotherNatureIndex(5);

        game.getGameTable().getIsleManager().getIsle(6).removeStudent(RealmColors.YELLOW);
        game.getGameTable().getIsleManager().getIsle(6).addStudent(RealmColors.YELLOW);
        game.getGameTable().getIsleManager().getIsle(6).addStudent(RealmColors.YELLOW);
        game.getGameTable().getIsleManager().getIsle(7).removeStudent(RealmColors.BLUE);
        game.getGameTable().getIsleManager().getIsle(7).addStudent(RealmColors.BLUE);
        game.getGameTable().getIsleManager().getIsle(7).addStudent(RealmColors.BLUE);

        game.actionPhase = ActionPhases.MOVE_MOTHER_NATURE;
        game.moveMotherNature(0, 6);
        game.actionPhase = ActionPhases.MOVE_MOTHER_NATURE;
        game.moveMotherNature(1, 7);
        // checking if the game ended properly
        assertTrue(game.isGameEnded());
        assertFalse(game.isGameEndedInADraw());
        assertEquals("jack", game.getWinner());
    }
}
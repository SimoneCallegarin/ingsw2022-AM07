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
    public void firstStudentsMovements() {
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

    /**
     * we are testing if the mother nature movement is correct and a tower is put on the chosen isle
     */
    @Test
    public void firstMotherNatureMovementWithConquest() {
        int nextIsleIndex = 0;
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
        AssistantCard cardPlayed1 = new AssistantCard(4, 2, false);
        AssistantCard cardPlayed2 = new AssistantCard(3, 12, false);
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
        game.actionPhase = ActionPhases.MOVE_MOTHER_NATURE;
        // moving mother nature
        game.moveMotherNature(game.firstPlayerIndex, 0);
        // checking if the movement has been done correctly
        assertEquals(0, game.getGameTable().getIsleManager().getIsleWithMotherNatureIndex());
        // checking if we moved on the next stage of actionPhase
        assertEquals(ActionPhases.CHOOSE_CLOUD, game.actionPhase);
    }

    /**
     * it tests if the influence is correctly calculated during a 4 players match and if the consequences are the ones we expected
     */
    @Test
    public void motherNatureMovement4Players() {
        Game game = new Game();
        AssistantCard cardPlayed1 = new AssistantCard(6, 3, false);
        AssistantCard cardPlayed2 = new AssistantCard(5, 3, false);
        AssistantCard cardPlayed3 = new AssistantCard(3, 2, false);
        AssistantCard cardPlayed4 = new AssistantCard(4, 2, false);
        game.addFirstPlayer("jack", GameMode.BASE, 4);
        game.addAnotherPlayer("calle");
        game.addAnotherPlayer("filo");
        game.addAnotherPlayer("bob");
        if (game.firstPlayerIndex == 0 && game.gamePhase == GamePhases.PLANNING_PHASE) {
            game.playAssistantCard(0, cardPlayed1);
            game.playAssistantCard(1, cardPlayed2);
            game.playAssistantCard(2, cardPlayed3);
            game.playAssistantCard(3, cardPlayed4);
            assertEquals(GamePhases.ACTION_PHASE, game.gamePhase);
        }
        if (game.firstPlayerIndex == 1 && game.gamePhase == GamePhases.PLANNING_PHASE) {
            game.playAssistantCard(1, cardPlayed2);
            game.playAssistantCard(2, cardPlayed3);
            game.playAssistantCard(3, cardPlayed4);
            game.playAssistantCard(0, cardPlayed1);
            assertEquals(GamePhases.ACTION_PHASE, game.gamePhase);
        }
        if (game.firstPlayerIndex == 2 && game.gamePhase == GamePhases.PLANNING_PHASE) {
            game.playAssistantCard(2, cardPlayed3);
            game.playAssistantCard(3, cardPlayed4);
            game.playAssistantCard(0, cardPlayed1);
            game.playAssistantCard(1, cardPlayed2);
            assertEquals(GamePhases.ACTION_PHASE, game.gamePhase);
        }
        if (game.firstPlayerIndex == 3 && game.gamePhase == GamePhases.PLANNING_PHASE) {
            game.playAssistantCard(3, cardPlayed4);
            game.playAssistantCard(0, cardPlayed1);
            game.playAssistantCard(1, cardPlayed2);
            game.playAssistantCard(2, cardPlayed3);
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
}
package it.polimi.ingsw.View.GUI.DashboardPanels;

import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.View.GUI.Buttons.ProfessorButton;
import it.polimi.ingsw.View.GUI.EmptyPanel;

import javax.swing.*;
import java.awt.*;

public class ProfessorPanel extends JPanel {
    Game game;
    int playerID;

    public ProfessorPanel(Game game,int playerID) {
        this.game = game;
        this.playerID=playerID;
        GridLayout gridLayout = new GridLayout(1, 5);
        gridLayout.setHgap(-30);
        setLayout(gridLayout);
        InitializeProfessors(playerID);
    }

    private void InitializeProfessors(int playerID) {
        if (game.getPlayerByIndex(playerID).getDashboard().getDiningRoom().getProfessorByColor(RealmColors.GREEN) != 0) {
            add(new ProfessorButton(RealmColors.GREEN));
        } else {
            add(new EmptyPanel());
        }
        if (game.getPlayerByIndex(playerID).getDashboard().getDiningRoom().getProfessorByColor(RealmColors.RED) != 0) {
            add(new ProfessorButton(RealmColors.RED));
        } else {
            add(new EmptyPanel());
        }
        if (game.getPlayerByIndex(playerID).getDashboard().getDiningRoom().getProfessorByColor(RealmColors.YELLOW) != 0) {
            add(new ProfessorButton(RealmColors.YELLOW));
        } else {
            add(new EmptyPanel());
        }
        if (game.getPlayerByIndex(playerID).getDashboard().getDiningRoom().getProfessorByColor(RealmColors.PINK) != 0) {
            add(new ProfessorButton(RealmColors.PINK));
        } else {
            add(new EmptyPanel());
        }
        if (game.getPlayerByIndex(playerID).getDashboard().getDiningRoom().getProfessorByColor(RealmColors.BLUE) != 0) {
            add(new ProfessorButton(RealmColors.BLUE));
        } else {
            add(new EmptyPanel());
        }
    }
}
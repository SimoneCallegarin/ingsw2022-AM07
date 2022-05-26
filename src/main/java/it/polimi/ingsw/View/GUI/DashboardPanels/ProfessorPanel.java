package it.polimi.ingsw.View.GUI.DashboardPanels;

import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.View.GUI.Buttons.ProfessorButton;
import it.polimi.ingsw.View.GUI.EmptyPanel;

import javax.swing.*;
import java.awt.*;

public class ProfessorPanel extends JPanel {
    Game game;

    public ProfessorPanel(Game game) {
        this.game = game;
        GridLayout gridLayout = new GridLayout(1, 5);
        gridLayout.setHgap(-30);
        setLayout(gridLayout);
        InitializeProfessors();
    }

    private void InitializeProfessors() {
        if (game.getPlayerByIndex(0).getDashboard().getDiningRoom().getProfessorByColor(RealmColors.GREEN) != 0) {
            add(new ProfessorButton(RealmColors.GREEN));
        } else {
            add(new EmptyPanel());
        }
        if (game.getPlayerByIndex(0).getDashboard().getDiningRoom().getProfessorByColor(RealmColors.RED) != 0) {
            add(new ProfessorButton(RealmColors.RED));
        } else {
            add(new EmptyPanel());
        }
        if (game.getPlayerByIndex(0).getDashboard().getDiningRoom().getProfessorByColor(RealmColors.YELLOW) != 0) {
            add(new ProfessorButton(RealmColors.YELLOW));
        } else {
            add(new EmptyPanel());
        }
        if (game.getPlayerByIndex(0).getDashboard().getDiningRoom().getProfessorByColor(RealmColors.PINK) != 0) {
            add(new ProfessorButton(RealmColors.PINK));
        } else {
            add(new EmptyPanel());
        }
        if (game.getPlayerByIndex(0).getDashboard().getDiningRoom().getProfessorByColor(RealmColors.BLUE) != 0) {
            add(new ProfessorButton(RealmColors.BLUE));
        } else {
            add(new EmptyPanel());
        }
    }
}
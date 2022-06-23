package it.polimi.ingsw.View.GUI.DashboardPanels;

import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.View.GUI.Buttons.ProfessorButton;
import it.polimi.ingsw.View.GUI.EmptyPanel;
import it.polimi.ingsw.View.StorageOfModelInformation.ModelStorage;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class ProfessorPanel extends JPanel {

    int playerID;
    ArrayList<BufferedImage> professors;

    public ProfessorPanel(ModelStorage storage, int playerID, ArrayList<BufferedImage> professors) {

        this.playerID=playerID;
        GridLayout gridLayout = new GridLayout(1, 5);
        gridLayout.setHgap(-30);
        setLayout(gridLayout);
        this.professors = professors;
        InitializeProfessors(playerID,storage);
    }

    private void InitializeProfessors(int playerID,ModelStorage storage) {
        if (storage.getDashboard(playerID).getDiningProfessors(RealmColors.GREEN) != 0) {
            add(new ProfessorButton(RealmColors.GREEN, professors));
        } else {
            add(new EmptyPanel());
        }
        if (storage.getDashboard(playerID).getDiningProfessors(RealmColors.RED) != 0) {
            add(new ProfessorButton(RealmColors.RED, professors));
        } else {
            add(new EmptyPanel());
        }
        if (storage.getDashboard(playerID).getDiningProfessors(RealmColors.YELLOW) != 0) {
            add(new ProfessorButton(RealmColors.YELLOW, professors));
        } else {
            add(new EmptyPanel());
        }
        if (storage.getDashboard(playerID).getDiningProfessors(RealmColors.PINK)!= 0) {
            add(new ProfessorButton(RealmColors.PINK, professors));
        } else {
            add(new EmptyPanel());
        }
        if (storage.getDashboard(playerID).getDiningProfessors(RealmColors.BLUE) != 0) {
            add(new ProfessorButton(RealmColors.BLUE, professors));
        } else {
            add(new EmptyPanel());
        }
        this.validate();
        this.repaint();
    }

    public void resetProfessors() {
        this.removeAll();
        this.validate();
        this.repaint();
    }
}
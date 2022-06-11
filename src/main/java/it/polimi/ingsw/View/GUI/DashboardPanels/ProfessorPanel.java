package it.polimi.ingsw.View.GUI.DashboardPanels;

import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.View.GUI.Buttons.ProfessorButton;
import it.polimi.ingsw.View.GUI.EmptyPanel;
import it.polimi.ingsw.View.StorageOfModelInformation.ModelStorage;

import javax.swing.*;
import java.awt.*;

public class ProfessorPanel extends JPanel {

    int playerID;

    public ProfessorPanel(ModelStorage storage, int playerID) {

        this.playerID=playerID;
        GridLayout gridLayout = new GridLayout(1, 5);
        gridLayout.setHgap(-30);
        setLayout(gridLayout);
        InitializeProfessors(playerID,storage);
    }

    private void InitializeProfessors(int playerID,ModelStorage storage) {
        if (storage.getDashboard(playerID).getDiningProfessors(RealmColors.GREEN) != 0) {
            add(new ProfessorButton(RealmColors.GREEN));
        } else {
            add(new EmptyPanel());
        }
        if (storage.getDashboard(playerID).getDiningProfessors(RealmColors.RED) != 0) {
            add(new ProfessorButton(RealmColors.RED));
        } else {
            add(new EmptyPanel());
        }
        if (storage.getDashboard(playerID).getDiningProfessors(RealmColors.YELLOW) != 0) {
            add(new ProfessorButton(RealmColors.YELLOW));
        } else {
            add(new EmptyPanel());
        }
        if (storage.getDashboard(playerID).getDiningProfessors(RealmColors.PINK)!= 0) {
            add(new ProfessorButton(RealmColors.PINK));
        } else {
            add(new EmptyPanel());
        }
        if (storage.getDashboard(playerID).getDiningProfessors(RealmColors.BLUE) != 0) {
            add(new ProfessorButton(RealmColors.BLUE));
        } else {
            add(new EmptyPanel());
        }
        this.validate();
        this.repaint();
    }

    public void resetProfessors() {
        for(int i=0;i<this.getComponentCount();i++){
            this.remove(i);
        }
    }
}
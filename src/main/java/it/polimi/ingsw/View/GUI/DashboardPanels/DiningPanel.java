package it.polimi.ingsw.View.GUI.DashboardPanels;

import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Model.Game;

import javax.swing.*;
import java.awt.*;

public class DiningPanel extends JPanel {
    GridBagConstraints c;
    Game game;

    public DiningPanel(Game game) {
        setLayout(new GridBagLayout());
        c = new GridBagConstraints();
        this.game = game;
        setOpaque(false);
        setBorder(BorderFactory.createLineBorder(Color.black));

        InitializeDining();

    }

    private void InitializeDining() {
        ProfessorPanel professorPanel=new ProfessorPanel(game);
        professorPanel.setBorder(BorderFactory.createLineBorder(Color.white));
        professorPanel.setOpaque(false);
        DiningStudentsPanel studentsPanel=new DiningStudentsPanel(game);
        studentsPanel.setBorder(BorderFactory.createLineBorder(Color.white));
        studentsPanel.setOpaque(false);
        c.fill=GridBagConstraints.BOTH;
        c.gridx=0;
        c.gridy=0;
        c.weighty=0.15;
        c.weightx=1;
        c.ipady=-12;
        add(professorPanel,c);
        c.gridy=1;
        c.weighty=1;
        c.weightx=1;
        c.ipady=-25;
        add(studentsPanel,c);
    }

}
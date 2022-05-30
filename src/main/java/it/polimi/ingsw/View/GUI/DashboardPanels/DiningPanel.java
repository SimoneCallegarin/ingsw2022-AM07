package it.polimi.ingsw.View.GUI.DashboardPanels;

import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.View.GUI.EventListeners.DiningListener;

import javax.swing.*;
import java.awt.*;

public class DiningPanel extends JPanel {
    GridBagConstraints c;
    Game game;
    int playerID;
    ProfessorPanel professorPanel;
    DiningStudentsPanel studentsPanel;


    public DiningPanel(Game game,int playerID) {
        setLayout(new GridBagLayout());
        c = new GridBagConstraints();
        this.game = game;
        this.playerID=playerID;
        setOpaque(false);
        setBorder(BorderFactory.createLineBorder(Color.black));

        InitializeDining(playerID);

    }

    /**
     * this method initialize the dining room according to the model state
     * @param playerID the player ID used to identify the dining room
     */
    private void InitializeDining(int playerID) {
        professorPanel=new ProfessorPanel(game,playerID);
        professorPanel.setBorder(BorderFactory.createLineBorder(Color.white));
        professorPanel.setOpaque(false);
        studentsPanel=new DiningStudentsPanel(game,playerID);
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

    /**
     * this method is called by the Color Listener after one student button press
     * 
     */
    public void setCLickable(){
        addMouseListener(new DiningListener(this));
    }

    public void removeCLickable(){
        removeMouseListener(this.getMouseListeners()[0]);
    }

    public ProfessorPanel getProfessorPanel() {
        return professorPanel;
    }

    public DiningStudentsPanel getStudentsPanel() {
        return studentsPanel;
    }
}
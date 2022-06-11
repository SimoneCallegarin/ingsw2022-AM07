package it.polimi.ingsw.View.GUI.DashboardPanels;

import it.polimi.ingsw.Observer.ViewObserver;
import it.polimi.ingsw.View.GUI.EventListeners.DiningListener;
import it.polimi.ingsw.View.StorageOfModelInformation.ModelStorage;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class DiningPanel extends JPanel {
    GridBagConstraints c;

    int playerID;
    ProfessorPanel professorPanel;
    DiningStudentsPanel studentsPanel;


    public DiningPanel(ModelStorage storage, int playerID) {
        setLayout(new GridBagLayout());
        c = new GridBagConstraints();

        this.playerID=playerID;
        setOpaque(false);
        setBorder(BorderFactory.createLineBorder(Color.black));

        initializeProfessorPanel(playerID,storage);
        initializeStudentDining(playerID,storage);

    }

    /**
     * this method initialize the dining room according to the model state
     * @param playerID the player ID used to identify the dining room
     */
    public void initializeStudentDining(int playerID, ModelStorage storage) {
        studentsPanel=new DiningStudentsPanel(storage,playerID);
        studentsPanel.setBorder(BorderFactory.createLineBorder(Color.white));
        studentsPanel.setOpaque(false);
        c.fill=GridBagConstraints.BOTH;
        c.gridy=1;
        c.weighty=1;
        c.weightx=1;
        c.ipady=-25;
        add(studentsPanel,c);
        this.validate();
        this.repaint();
    }

    public void initializeProfessorPanel(int playerID, ModelStorage storage){
        professorPanel=new ProfessorPanel(storage,playerID);
        professorPanel.setBorder(BorderFactory.createLineBorder(Color.white));
        professorPanel.setOpaque(false);
        c.fill=GridBagConstraints.BOTH;
        c.gridx=0;
        c.gridy=0;
        c.weighty=0.15;
        c.weightx=1;
        c.ipady=-12;
        add(professorPanel,c);
        this.validate();
        this.repaint();
    }

    /**
     * this method is called by the Color Listener after one student button press
     * @param observers the observers list which are notified by the listener
     */
    public void setCLickable(ArrayList<ViewObserver> observers){
        EntrancePanel pairedEntrance=((DashboardPanel)this.getParent()).getEntrance();
        addMouseListener(new DiningListener(this,pairedEntrance,observers));
    }

    /**
     * this method removes the mouse listeners from the dining in order to avoid any wrong timed click from the player
     */
    public void removeCLickable(){
        removeMouseListener(this.getMouseListeners()[0]);
    }

    public void resetStudentDining(){
        this.getStudentsPanel().resetStudents();
    }

    public ProfessorPanel getProfessorPanel() {
        return professorPanel;
    }

    public DiningStudentsPanel getStudentsPanel() {
        return studentsPanel;
    }
}
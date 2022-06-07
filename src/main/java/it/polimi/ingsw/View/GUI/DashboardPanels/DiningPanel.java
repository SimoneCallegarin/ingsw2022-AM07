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

        InitializeDining(playerID,storage);

    }

    /**
     * this method initialize the dining room according to the model state
     * @param playerID the player ID used to identify the dining room
     */
    public void InitializeDining(int playerID,ModelStorage storage) {
        professorPanel=new ProfessorPanel(storage,playerID);
        professorPanel.setBorder(BorderFactory.createLineBorder(Color.white));
        professorPanel.setOpaque(false);
        studentsPanel=new DiningStudentsPanel(storage,playerID);
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
     * @param observers
     */
    public void setCLickable(ArrayList<ViewObserver> observers){
        addMouseListener(new DiningListener(this,observers));
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
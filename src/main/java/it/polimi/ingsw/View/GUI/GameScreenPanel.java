package it.polimi.ingsw.View.GUI;



import it.polimi.ingsw.Observer.ViewObserver;
import it.polimi.ingsw.View.GUI.DashboardPanels.DashboardPanel;
import it.polimi.ingsw.View.GUI.IslesPanels.TableCenterPanel;
import it.polimi.ingsw.View.StorageOfModelInformation.ModelStorage;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class GameScreenPanel extends JPanel {

    private final ModelStorage storage;

    JPanel textContainerPanel;

    JLabel textLabel;

    JPanel dashboardContainerPanel1;

    JPanel dashboardContainerPanel2;

    TableCenterPanel tableCenterPanel;

    ArrayList<DashboardPanel> dashboardPanels;
    ArrayList<JPanel> dashboardContainers;

    ArrayList<ViewObserver> viewObservers;

    GridBagConstraints gamescreenConstraints;


    /**
     * Create the game screen panel used to contain all the game panels
     *
     * @param layout the LayoutManager to use
     * @param usernamePlaying the username of the current player using the GUI to pass to the tableCenter to highlight which is his dashboard
     */
    public GameScreenPanel(LayoutManager layout, ModelStorage storage, int frameWidth, int frameHeight, String usernamePlaying,ArrayList<ViewObserver> viewObservers ) {
        super(layout);
        this.storage=storage;
        setPreferredSize(new Dimension(frameWidth,frameHeight));
        this.dashboardPanels=new ArrayList<>();
        this.dashboardContainers=new ArrayList<>();
        this.viewObservers=viewObservers;
        textContainerPanel = new JPanel(new GridLayout(1,1));
        //textContainerPanel.setBackground(Color.WHITE);
        dashboardContainerPanel1=new JPanel(new GridLayout(2,1));
        dashboardContainerPanel1.setBackground(Color.CYAN);
        dashboardContainers.add(dashboardContainerPanel1);
        dashboardContainerPanel2=new JPanel(new GridLayout(2,1));
        dashboardContainerPanel2.setBackground(Color.CYAN);
        dashboardContainers.add(dashboardContainerPanel2);

        gamescreenConstraints=new GridBagConstraints();

        gamescreenConstraints.gridy=0;
        gamescreenConstraints.weighty=0.01;
        gamescreenConstraints.weightx=1;
        gamescreenConstraints.gridx=1;
        add(textContainerPanel,gamescreenConstraints);

        //first i divide the gamescreen in 3 columns and 1 row and i made the isleManagerPanel take the most space in the center
        gamescreenConstraints.gridy=1;
        gamescreenConstraints.fill=GridBagConstraints.BOTH;

        gamescreenConstraints.gridx=1;
        tableCenterPanel=new TableCenterPanel(storage,usernamePlaying,viewObservers);
        add(tableCenterPanel,gamescreenConstraints);

        //then I add the two dashboard container Panel on the left and on the right, and I set the weights in order to correctly size the dashboard
        gamescreenConstraints.weightx=0.05;
        gamescreenConstraints.weighty=0.2;
        gamescreenConstraints.gridx=0;
        add(dashboardContainerPanel1,gamescreenConstraints);

        gamescreenConstraints.gridx=2;
        add(dashboardContainerPanel2,gamescreenConstraints);

        for(int i=0;i<storage.getNumberOfPlayers();i++){
            DashboardPanel dashboardPanel=new DashboardPanel(storage,i,viewObservers,tableCenterPanel);
            dashboardContainers.get(i%2).add(dashboardPanel);//the containers are used to place the panels in the screen
            dashboardPanels.add(dashboardPanel);//this arrayList is used only to store them and access them in a more efficient way
        }

        textLabel = new JLabel("                                                                                                                                          ");
        textContainerPanel.add(textLabel);
        gamescreenConstraints.gridy=0;
    }

    /**
     * this method set the dashboard entrance clickable in order to select a student to move
     * @param playerID the playerID used to identify which dashboard set movable
     */
    public void setClickableStudents(int playerID) {
        dashboardPanels.get(playerID).getEntrance().setClickable();
    }

    public void setMessage(String message) {
        //textContainerPanel.remove(gamescreenConstraints.gridy);
        textLabel.setText(message);
        //textContainerPanel.add(textLabel);
        //textContainerPanel.validate();
    }


    public void updateEntrance(int playerID){
        dashboardPanels.get(playerID).getEntrance().resetEntrance();
        dashboardPanels.get(playerID).getEntrance().initializeEntrance(storage);
    }

    public void updateStudentDinings(int playerID){
        dashboardPanels.get(playerID).getDining().resetStudentDining();
        dashboardPanels.get(playerID).getDining().initializeStudentDining(storage);
    }

    public void updateProfessors(int playerID){
        dashboardPanels.get(playerID).getDining().resetStudentDining();
        dashboardPanels.get(playerID).getDining().initializeProfessorPanel(storage);
    }

    public void updateTowerStorages(int playerID){
        dashboardPanels.get(playerID).getTowerStorage().resetTowerStorage();
        dashboardPanels.get(playerID).getTowerStorage().initializeTowerStorage(storage);
    }
}

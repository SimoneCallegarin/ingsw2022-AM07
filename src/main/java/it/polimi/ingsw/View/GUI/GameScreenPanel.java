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

    JPanel dashboardContainerPanel1;

    JPanel dashboardContainerPanel2;

    TableCenterPanel tableCenterPanel;

    DashboardPanel dashboard1;
    DashboardPanel dashboard2;
    DashboardPanel dashboard3;
    DashboardPanel dashboard4;

    /**
     * Create a new buffered JPanel with the specified layout manager
     *
     * @param layout the LayoutManager to use
     * @param usernamePlaying the username of the current player using the GUI to pass to the tableCenter to highlight which is his dashboard
     */
    public GameScreenPanel(LayoutManager layout, ModelStorage storage, int frameWidth, int frameHeight, String usernamePlaying) {
        super(layout);
        this.storage=storage;
        setPreferredSize(new Dimension(frameWidth,frameHeight));
        dashboardContainerPanel1=new JPanel(new GridLayout(2,1));
        dashboardContainerPanel1.setBackground(Color.CYAN);
        dashboardContainerPanel2=new JPanel(new GridLayout(2,1));
        dashboardContainerPanel2.setBackground(Color.CYAN);


        GridBagConstraints gamescreenConstraints=new GridBagConstraints();
        //first i divide the gamescreen in 3 columns and 1 row and i made the isleManagerPanel take the most space in the center
        gamescreenConstraints.gridy=0;
        gamescreenConstraints.weighty=1;
        gamescreenConstraints.weightx=1;
        gamescreenConstraints.fill=GridBagConstraints.BOTH;

        gamescreenConstraints.gridx=1;
        tableCenterPanel=new TableCenterPanel(storage,usernamePlaying);
        add(tableCenterPanel,gamescreenConstraints);

        //then i add the two dashboard container Panel on the left and on the right and i set the weights in order to correctly size the dashboard
        gamescreenConstraints.weightx=0.05;
        gamescreenConstraints.weighty=0.2;
        gamescreenConstraints.gridx=0;
        add(dashboardContainerPanel1,gamescreenConstraints);

        gamescreenConstraints.gridx=2;
        add(dashboardContainerPanel2,gamescreenConstraints);

        switch (storage.getNumberOfPlayers()){
            case 2->{
                dashboard1=new DashboardPanel(storage,0);
                dashboard2=new DashboardPanel(storage,1);
                dashboardContainerPanel1.add(dashboard1);
                dashboardContainerPanel2.add(dashboard2);
            }
            case 3->{
                dashboard1=new DashboardPanel(storage,0);
                dashboard2=new DashboardPanel(storage,1);
                dashboard3=new DashboardPanel(storage,2);
                dashboardContainerPanel1.add(dashboard1);
                dashboardContainerPanel1.add(dashboard3);
                dashboardContainerPanel2.add(dashboard2);
            }
            case 4->{
                dashboard1=new DashboardPanel(storage,0);
                dashboard2=new DashboardPanel(storage,1);
                dashboard3=new DashboardPanel(storage,2);
                dashboard4=new DashboardPanel(storage,3);
                dashboardContainerPanel1.add(dashboard1);
                dashboardContainerPanel1.add(dashboard3);
                dashboardContainerPanel2.add(dashboard2);
                dashboardContainerPanel2.add(dashboard4);
            }
        }
    }

    /**
     * this method set the dashboard entrance clickable in order to select a student to move
     * @param playerID the playerID used to identify which dashboard set movable
     * @param viewObserverList the observer list to pass to the entrance listener
     */
    public void setClickableStudents(int playerID, ArrayList<ViewObserver> viewObserverList) {
        switch (playerID){
            case 0->{
                dashboard1.getEntrance().setClickable(viewObserverList);
            }
            case 1->{
                dashboard2.getEntrance().setClickable(viewObserverList);
            }
            case 2->{
                dashboard3.getEntrance().setClickable(viewObserverList);
            }
            case 3->{
                dashboard4.getEntrance().setClickable(viewObserverList);
            }
        }
    }

    public void setClickableCharacters(){

    }

    public void updateEntrance(int playerID){
        switch(playerID){
            case 0->{
                dashboard1.getEntrance().resetEntrance();
                dashboard1.getEntrance().initializeEntrance(playerID,storage);
            }
            case 1->{
                dashboard2.getEntrance().resetEntrance();
                dashboard2.getEntrance().initializeEntrance(playerID,storage);
            }
            case 2->{
                dashboard3.getEntrance().resetEntrance();
                dashboard3.getEntrance().initializeEntrance(playerID,storage);
            }
            case 3->{
                dashboard4.getEntrance().resetEntrance();
                dashboard4.getEntrance().initializeEntrance(playerID,storage);
            }
        }
    }

    public void updateStudentDinings(int playerID){
        switch (playerID){
            case 0->{
                dashboard1.getDining().resetStudentDining();
                dashboard1.getDining().initializeStudentDining(playerID,storage);
            }
            case 1->{
                dashboard2.getDining().resetStudentDining();
                dashboard2.getDining().initializeStudentDining(playerID,storage);
            }
            case 2->{
                dashboard3.getDining().resetStudentDining();
                dashboard3.getDining().initializeStudentDining(playerID,storage);
            }
            case 3->{
                dashboard4.getDining().resetStudentDining();
                dashboard4.getDining().initializeStudentDining(playerID,storage);
            }
        }
    }

    public void updateProfessors(){

    }

    public void updateTowerStorages(){

    }
}

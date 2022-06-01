package it.polimi.ingsw.View.GUI;

import it.polimi.ingsw.Model.Game;

import it.polimi.ingsw.View.GUI.DashboardPanels.DashboardPanel;
import it.polimi.ingsw.View.GUI.IslesPanels.TableCenterPanel;
import it.polimi.ingsw.View.StorageOfModelInformation.ModelStorage;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameScreenPanel extends JPanel {

    private ModelStorage storage;

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
     */
    public GameScreenPanel(LayoutManager layout, ModelStorage storage, int frameWidth, int frameHeight) {
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
        tableCenterPanel=new TableCenterPanel(storage);
        add(tableCenterPanel,gamescreenConstraints);

        //then i add the two dashboard container Panel on the left and on the right and i set the weights in order to correctly size the dashboard
        gamescreenConstraints.weightx=0.05;
        gamescreenConstraints.weighty=0.2;
        gamescreenConstraints.gridx=0;
        add(dashboardContainerPanel1,gamescreenConstraints);

        gamescreenConstraints.gridx=2;
        add(dashboardContainerPanel2,gamescreenConstraints);



        //then i add the 4 dashbpoard to the containers: first one in the top-left, second top-right, third bottom-left, fourth bottom-right
       /* for(int i=0;i<game.getNumberOfPlayers();i++){
           if(i%2==0){
               dashboardContainerPanel1.add(new DashboardPanel(game,i));
           }else{
               dashboardContainerPanel2.add(new DashboardPanel(game,i));
           }
        }

        */
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
        /*
        dashboardContainerPanel1.add(new DashboardPanel(game));
        dashboardContainerPanel2.add(new DashboardPanel(game));

        dashboardContainerPanel1.add(new DashboardPanel(game));
        dashboardContainerPanel2.add(new DashboardPanel(game));
        */

    }

    /**
     * this method set the dashboard entrance clickable in order to select a student to move
     * @param playerID the playerID used to identify which dashboard set movable
     */
    public void setClickableStudents(int playerID) {
        switch (playerID){
            case 0->{
                dashboard1.getEntrance().setClickable();
            }
            case 1->{
                dashboard2.getEntrance().setClickable();
            }
            case 2->{
                dashboard3.getEntrance().setClickable();
            }
            case 3->{
                dashboard4.getEntrance().setClickable();
            }
        }
    }

    public void setClickableCharacters(){

     }

}

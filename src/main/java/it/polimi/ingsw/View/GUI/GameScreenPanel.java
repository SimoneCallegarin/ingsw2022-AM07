package it.polimi.ingsw.View.GUI;

import it.polimi.ingsw.Model.Game;

import it.polimi.ingsw.View.GUI.DashboardPanels.DashboardPanel;
import it.polimi.ingsw.View.GUI.IslesPanels.IsleManagerPanel;
import it.polimi.ingsw.View.StorageOfModelInformation.ModelStorage;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameScreenPanel extends JPanel {
    Game game;
    private  final int DASHBOARD_WIDTH = 250;
    private  final int DASHBOARD_HEIGHT = 800;
    GridBagConstraints dashboardConstraints;

    private Graphics g;

    private ModelStorage storage;

    private void setGraphics(Graphics g) { this.g = g; }

    public void setStorage(ModelStorage storage) { this.storage = storage; }

    public ModelStorage getStorage() { return storage; }

    /**
     * Create a new buffered JPanel with the specified layout manager
     *
     * @param layout the LayoutManager to use
     */
    public GameScreenPanel(LayoutManager layout, Game game, int frameWidth, int frameHeight) {
        super(layout);
        this.game=game;
        setPreferredSize(new Dimension(frameWidth,frameHeight));
        ArrayList<JPanel> dashboardContainers=new ArrayList<>();
        JPanel dashboardContainerPanel1=new JPanel(new GridLayout(2,1));
        dashboardContainerPanel1.setBackground(Color.CYAN);
        JPanel dashboardContainerPanel2=new JPanel(new GridLayout(2,1));
        dashboardContainerPanel2.setBackground(Color.CYAN);
        dashboardContainers.add(dashboardContainerPanel1);
        dashboardContainers.add(dashboardContainerPanel2);

        GridBagConstraints gamescreenConstraints=new GridBagConstraints();
        //first i divide the gamescreen in 3 columns and 1 row and i made the isleManagerPanel take the most space in the center
        gamescreenConstraints.gridy=0;
        gamescreenConstraints.weighty=1;
        gamescreenConstraints.weightx=1;
        gamescreenConstraints.fill=GridBagConstraints.BOTH;

        gamescreenConstraints.gridx=1;
        add(new IsleManagerPanel(game),gamescreenConstraints);

        //then i add the two dashboard container Panel on the left and on the right and i set the weights in order to correctly size the dashboard
        gamescreenConstraints.weightx=0.05;
        gamescreenConstraints.weighty=0.2;
        gamescreenConstraints.gridx=0;
        add(dashboardContainerPanel1,gamescreenConstraints);

        gamescreenConstraints.gridx=2;
        add(dashboardContainerPanel2,gamescreenConstraints);



        //then i add the 4 dashbpoard to the containers: first one in the top-left, second top-right, third bottom-left, fourth bottom-right
        for(int i=0;i<game.getNumberOfPlayers();i++){
           if(i%2==0){
               dashboardContainers.get(0).add(new DashboardPanel(game,i));
           }else{
               dashboardContainers.get(1).add(new DashboardPanel(game,i));
           }
        }
        /*
        dashboardContainerPanel1.add(new DashboardPanel(game));
        dashboardContainerPanel2.add(new DashboardPanel(game));

        dashboardContainerPanel1.add(new DashboardPanel(game));
        dashboardContainerPanel2.add(new DashboardPanel(game));
        */

    }

}

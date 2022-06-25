package it.polimi.ingsw.View.GUI.DashboardPanels;

import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Observer.ViewObserver;
import it.polimi.ingsw.View.GUI.IslesPanels.TableCenterPanel;
import it.polimi.ingsw.View.StorageOfModelInformation.ModelStorage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * the panel that paints the dashboard images and contains the panel for the dining, the tower storage and the entrance
 */
public class DashboardPanel extends JPanel {

    ModelStorage storage;
    TowerStoragePanel towerStorage;
    GridBagConstraints towerStorageConstraints;

    DiningPanel dining;
    GridBagConstraints diningConstraints;

    EntrancePanel entrance;
    GridBagConstraints entranceConstraints;

    int playerID;

    BufferedImage dashboard;

    /**
     * the constructor of DashboardPanel
     * @param storage the model storage used to retrieve information about the game state
     * @param playerID the player id used to access the model information
     * @param viewObservers the list of observer to pass to the listeners
     * @param tableCenterPanel the table center panel to pass to the listeners in order for them to call removeIsleClickable
     * @param students array list of buffered images to print students button
     * @param checkedStudents array list of buffered images to print checked students button
     * @param towers array list of buffered images to print towers
     */
    public DashboardPanel(ModelStorage storage, int playerID, ArrayList<ViewObserver> viewObservers, TableCenterPanel tableCenterPanel, ArrayList<BufferedImage> students, ArrayList<BufferedImage> checkedStudents, ArrayList<BufferedImage> towers, BufferedImage dashboard) {
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createLineBorder(Color.black));

        this.storage=storage;
        this.playerID=playerID;
        this.towerStorage=new TowerStoragePanel(storage, playerID, towers);
        this.dining=new DiningPanel(storage,playerID, students);
        this.entrance=new EntrancePanel(storage,playerID,viewObservers,tableCenterPanel,this, students, checkedStudents);
        towerStorageConstraints =new GridBagConstraints();
        diningConstraints= new GridBagConstraints();
        entranceConstraints= new GridBagConstraints();
        this.dashboard = dashboard;

        initializeDashboard();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(dashboard,0,0,getWidth(),getHeight(),null);
    }

    public void initializeDashboard(){

        towerStorageConstraints.fill=GridBagConstraints.BOTH;
        towerStorageConstraints.gridy=0;
        towerStorageConstraints.weightx=1;
        towerStorageConstraints.weighty=0.35;
        towerStorageConstraints.ipady=-90;
        add(towerStorage, towerStorageConstraints);

        diningConstraints.fill=GridBagConstraints.BOTH;
        diningConstraints.gridy=1;
        diningConstraints.weightx=1;
        diningConstraints.weighty=1;
        add(dining,diningConstraints);

        entranceConstraints.fill=GridBagConstraints.BOTH;
        entranceConstraints.gridy=2;
        entranceConstraints.weightx=1;
        entranceConstraints.weighty=0.25;
        entranceConstraints.ipady=-50;
        add(entrance,entranceConstraints);
    }


    public TowerStoragePanel getTowerStorage() {
        return towerStorage;
    }

    public DiningPanel getDining() { return dining; }

    public EntrancePanel getEntrance() {
        return entrance;
    }
}

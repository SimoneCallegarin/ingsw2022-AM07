package it.polimi.ingsw.View.GUI.DashboardPanels;


import it.polimi.ingsw.Observer.ViewObserver;
import it.polimi.ingsw.View.GUI.IslesPanels.TableCenterPanel;
import it.polimi.ingsw.View.StorageOfModelInformation.ModelStorage;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static it.polimi.ingsw.View.GUI.GUIConstants.*;

/**
 * The panel that paints the dashboard images and contains the panel for the dining, the tower storage and the entrance.
 */
public class DashboardPanel extends JPanel {

    /**
     * TowerStoragePanel contained in this panel.
     */
    private final TowerStoragePanel towerStorage;
    /**
     * Constraints used to place the TowerStoragePanel in this panel.
     */
    private GridBagConstraints towerStorageConstraints;
    /**
     * DiningPanel contained in this panel.
     */
    private final DiningPanel dining;
    /**
     * Constraints used to place the DiningPanel in this panel.
     */
    private GridBagConstraints diningConstraints;
    /**
     * EntrancePanel contained in this panel.
     */
    private final EntrancePanel entrance;
    /**
     * Constraints used to place the EntrancePanel in this panel.
     */
    private GridBagConstraints entranceConstraints;
    /**
     * Image used to paint the background of this panel.
     */
    private final BufferedImage dashboard;

    /**
     * Constructor of DashboardPanel.
     * @param storage The model storage used to retrieve information about the game state.
     * @param playerID The player ID used to access the model information.
     * @param viewObservers Array List of observer to pass to the listeners.
     * @param tableCenterPanel The table center panel to pass to the listeners in order for them to call removeIsleClickable.
     * @param students Array List of buffered images to print students button.
     * @param checkedStudents Array list of buffered images to print checked students button.
     * @param towers Array List of buffered images to print towers.
     */
    public DashboardPanel(ModelStorage storage, int playerID, ArrayList<ViewObserver> viewObservers, TableCenterPanel tableCenterPanel, ArrayList<BufferedImage> students, ArrayList<BufferedImage> checkedStudents, ArrayList<BufferedImage> towers, BufferedImage dashboard) {
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createLineBorder(Color.black));

        this.towerStorage = new TowerStoragePanel(storage, playerID, towers);
        this.dining = new DiningPanel(storage,playerID, students, checkedStudents);
        this.entrance = new EntrancePanel(storage,playerID,viewObservers,tableCenterPanel,this, students, checkedStudents);
        this.towerStorageConstraints = new GridBagConstraints();
        this.diningConstraints = new GridBagConstraints();
        this.entranceConstraints = new GridBagConstraints();
        this.dashboard = dashboard;

        initializeDashboard();
    }

    /**
     * Adds the tower storage, dining room and entrance panels in this panel with their constraints.
     */
    public void initializeDashboard(){
        towerStorageConstraints = setConstraints(towerStorageWeightY,towerStorageGridY, towerStorageIpadY);
        add(towerStorage, towerStorageConstraints);

        diningConstraints = setConstraints(diningRoomWeightY,diningRoomGridY,diningRoomIpadY);
        add(dining,diningConstraints);

        entranceConstraints = setConstraints(entranceWeightY,entranceGridY,entranceIpadY);
        add(entrance,entranceConstraints);
    }

    /**
     * Sets the constraints for the dashboard panel.
     * @param weightY the weightx of the GridBagConstraints.
     * @param gridY the gridy of the GridBagConstraints.
     * @param ipadY the ipdady of the GridBagConstraints.
     * @return the constraints with the correct value passed.
     */
    private GridBagConstraints setConstraints(double weightY, int gridY, int ipadY) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = dashboardWeightX;
        constraints.gridx = dashboardGridX;
        constraints.weighty = weightY;
        constraints.gridy = gridY;
        constraints.ipady = ipadY;
        return constraints;
    }

    /**
     * Paints the dashboard.
     * @param g allows an application to draw onto components that are realized on various devices.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(dashboard,0,0,getWidth(),getHeight(),null);
    }

    /**
     * Getter method for the tower storage.
     * @return the tower storage.
     */
    public TowerStoragePanel getTowerStorage() { return towerStorage; }

    /**
     * Getter method for the dining room.
     * @return the dining room.
     */
    public DiningPanel getDining() { return dining; }

    /**
     * Getter method for the entrance.
     * @return the entrance.
     */
    public EntrancePanel getEntrance() { return entrance; }
}

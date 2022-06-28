package it.polimi.ingsw.View.GUI.DashboardPanels;


import it.polimi.ingsw.Observer.ViewObserver;
import it.polimi.ingsw.View.GUI.IslesPanels.TableCenterPanel;
import it.polimi.ingsw.View.StorageOfModelInformation.ModelStorage;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * The panel that paints the dashboard images and contains the panel for the dining, the tower storage and the entrance
 */
public class DashboardPanel extends JPanel {
    /**
     * TowerStoragePanel contained in this panel
     */
    private final TowerStoragePanel towerStorage;
    /**
     * Constraints used to place the TowerStoragePanel in this panel
     */
    private final GridBagConstraints towerStorageConstraints;
    /**
     * DiningPanel contained in this panel
     */
    private final DiningPanel dining;
    /**
     * Constraints used to place the DiningPanel in this panel
     */
    private final GridBagConstraints diningConstraints;
    /**
     * EntrancePanel contained in this panel
     */
    private final EntrancePanel entrance;
    /**
     * Constraints used to place the EntrancePanel in this panel
     */
    private final GridBagConstraints entranceConstraints;
    /**
     * Image used to paint the background of this panel
     */
    private final BufferedImage dashboard;

    /**
     * the constructor of DashboardPanel
     * @param storage The model storage used to retrieve information about the game state
     * @param playerID The player id used to access the model information
     * @param viewObservers The list of observer to pass to the listeners
     * @param tableCenterPanel The table center panel to pass to the listeners in order for them to call removeIsleClickable
     * @param students Array list of buffered images to print students button
     * @param checkedStudents Array list of buffered images to print checked students button
     * @param towers Array list of buffered images to print towers
     */
    public DashboardPanel(ModelStorage storage, int playerID, ArrayList<ViewObserver> viewObservers, TableCenterPanel tableCenterPanel, ArrayList<BufferedImage> students, ArrayList<BufferedImage> checkedStudents, ArrayList<BufferedImage> towers, BufferedImage dashboard) {
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createLineBorder(Color.black));

        this.towerStorage=new TowerStoragePanel(storage, playerID, towers);
        this.dining=new DiningPanel(storage,playerID, students, checkedStudents);
        this.entrance=new EntrancePanel(storage,playerID,viewObservers,tableCenterPanel,this, students, checkedStudents);
        this.towerStorageConstraints =new GridBagConstraints();
        this.diningConstraints= new GridBagConstraints();
        this.entranceConstraints= new GridBagConstraints();
        this.dashboard = dashboard;

        initializeDashboard();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(dashboard,0,0,getWidth(),getHeight(),null);
    }

    /**
     * This method add the tower storage, dining room and entrance panels in this panel
     */
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

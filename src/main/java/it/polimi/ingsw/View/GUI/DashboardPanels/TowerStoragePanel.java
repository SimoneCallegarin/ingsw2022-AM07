package it.polimi.ingsw.View.GUI.DashboardPanels;


import it.polimi.ingsw.Model.Enumeration.TowerColors;
import it.polimi.ingsw.View.GUI.Buttons.TowerButton;
import it.polimi.ingsw.View.StorageOfModelInformation.ModelStorage;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Panel representing the tower storage part in the dashboard
 */
public class TowerStoragePanel extends JPanel {
    /**
     * Model storage used to retrieve game state information and draw the tower storage accordingly
     */
    private final ModelStorage storage;
    /**
     * Constraints used by the layout manager
     */
    private final GridBagConstraints constraints;
    /**
     * Player ID associated with this tower storage
     */
    private final int playerID;
    /**
     * Array list to store towers images
     */
    private final ArrayList<BufferedImage> towers;

    /**
     * Constructor of TowerStoragePanel
     * @param storage Model storage used to retrieve game state information and draw the tower storage accordingly
     * @param playerID Player ID associated with this tower storage
     * @param towers Array list to store towers images
     */
    public TowerStoragePanel(ModelStorage storage, int playerID, ArrayList<BufferedImage> towers) {
        setLayout(new GridBagLayout());
        this.storage=storage;
        this.playerID=playerID;
        setOpaque(false);
        constraints =new GridBagConstraints();
        setBorder(BorderFactory.createLineBorder(Color.black));

        this.towers = towers;

        initializeTowerStorage();

    }

    public void initializeTowerStorage(){
        TowerColors color=storage.getDashboard(playerID).getTowerColor();
        constraints.insets=new Insets(5,5,5,5);
        constraints.gridy=0;
        constraints.gridx=0;

        for(int numTower=storage.getDashboard(playerID).getNumOfTowers();numTower>0;numTower--){
            add(new TowerButton(color, towers), constraints);
            if(constraints.gridx==3){
                constraints.gridy++;
                constraints.gridx=-1;
            }
            constraints.gridx++;
        }

        this.validate();
        this.repaint();
    }

    public void resetTowerStorage() {
        this.removeAll();
        this.validate();
        this.repaint();
    }
}

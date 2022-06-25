package it.polimi.ingsw.View.GUI.DashboardPanels;

import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Model.Enumeration.TowerColors;
import it.polimi.ingsw.View.GUI.Buttons.TowerButton;
import it.polimi.ingsw.View.StorageOfModelInformation.ModelStorage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class TowerStoragePanel extends JPanel {
    /**
     * model storage used to retrieve game state information and draw the tower storage accordingly
     */
    ModelStorage storage;
    /**
     * constraints used by the layout manager
     */
    GridBagConstraints c;
    /**
     * player ID associated with this tower storage
     */
    int playerID;
    /**
     * array list to store towers images
     */
    ArrayList<BufferedImage> towers;

    public TowerStoragePanel(ModelStorage storage, int playerID, ArrayList<BufferedImage> towers) {
        setLayout(new GridBagLayout());
        this.storage=storage;
        this.storage=storage;
        this.playerID=playerID;
        setOpaque(false);
        c=new GridBagConstraints();
        setBorder(BorderFactory.createLineBorder(Color.black));

        this.towers = towers;

        initializeTowerStorage();

    }

    public void initializeTowerStorage(){
        TowerColors color=storage.getDashboard(playerID).getTowerColor();
        c.insets=new Insets(5,5,5,5);
        c.gridy=0;
        c.gridx=0;

        for(int numTower=storage.getDashboard(playerID).getNumOfTowers();numTower>0;numTower--){
            add(new TowerButton(color, towers),c);
            if(c.gridx==3){
                c.gridy++;
                c.gridx=-1;
            }
            c.gridx++;
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

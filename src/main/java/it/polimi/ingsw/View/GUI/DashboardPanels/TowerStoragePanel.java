package it.polimi.ingsw.View.GUI.DashboardPanels;

import it.polimi.ingsw.Model.Enumeration.TowerColors;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.View.GUI.Buttons.TowerButton;
import it.polimi.ingsw.View.StorageOfModelInformation.ModelStorage;

import javax.swing.*;
import java.awt.*;

public class TowerStoragePanel extends JPanel {

    GridBagConstraints c;
    int playerID;

    public TowerStoragePanel(ModelStorage storage, int playerID) {
        setLayout(new GridBagLayout());

        this.playerID=playerID;
        setOpaque(false);
        c=new GridBagConstraints();
        setBorder(BorderFactory.createLineBorder(Color.black));
        InitializeTowerStorage(playerID,storage);

    }

    private void InitializeTowerStorage(int playerID,ModelStorage storage){
        TowerColors color=storage.getDashboard(playerID).getTowerColor();
        c.insets=new Insets(5,5,5,5);
        c.gridy=0;
        c.gridx=0;

        for(int numTower=storage.getDashboard(playerID).getNumOfTowers();numTower>0;numTower--){
            add(new TowerButton(color),c);
            if(c.gridx==3){
                c.gridy++;
                c.gridx=-1;
            }
            c.gridx++;
        }


    }
}

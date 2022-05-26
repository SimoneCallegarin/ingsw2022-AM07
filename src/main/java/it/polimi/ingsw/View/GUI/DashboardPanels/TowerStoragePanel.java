package it.polimi.ingsw.View.GUI.DashboardPanels;

import it.polimi.ingsw.Model.Enumeration.TowerColors;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.View.GUI.Buttons.TowerButton;

import javax.swing.*;
import java.awt.*;

public class TowerStoragePanel extends JPanel {
    Game game;
    GridBagConstraints c;

    public TowerStoragePanel(Game game) {
        setLayout(new GridBagLayout());
        this.game=game;
        setOpaque(false);
        c=new GridBagConstraints();
        setBorder(BorderFactory.createLineBorder(Color.black));
        InitializeTowerStorage();

    }

    private void InitializeTowerStorage(){
        TowerColors color=game.getPlayerByIndex(0).getDashboard().getTowerStorage().getTowerColor();
        c.insets=new Insets(5,5,5,5);
        c.gridy=0;
        c.gridx=0;

        for(int numTower=game.getPlayerByIndex(0).getDashboard().getTowerStorage().getNumberOfTowers();numTower>0;numTower--){
            add(new TowerButton(color),c);
            if(c.gridx==3){
                c.gridy++;
                c.gridx=-1;
            }
            c.gridx++;
        }


    }
}

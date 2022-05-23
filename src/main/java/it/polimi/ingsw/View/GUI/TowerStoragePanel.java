package it.polimi.ingsw.View.GUI;

import it.polimi.ingsw.Model.Enumeration.TowerColors;
import it.polimi.ingsw.Model.Game;

import javax.swing.*;
import java.awt.*;

public class TowerStoragePanel extends JPanel {
    Game game;
    GridBagConstraints c;

    public TowerStoragePanel(LayoutManager layout, Game game,int width, int height) {
        super(layout);
        this.game=game;
        setOpaque(false);
        c=new GridBagConstraints();
        setBorder(BorderFactory.createLineBorder(Color.black));
        setSize(width,height);
        InitializeTowerStorage();
    }

    private void InitializeTowerStorage(){

        int row=0;
        int column=0;
        TowerColors color=game.getPlayerByIndex(0).getDashboard().getTowerStorage().getTowerColor();
        for(int i=0;i<game.getPlayerByIndex(0).getDashboard().getTowerStorage().getNumberOfTowers();i++){
            c.gridy=row;
            c.gridx=column;
            add( new TowerButton(color),c);
            if(column==3){
                row++;
                column=-1;
            }
            column++;
        }
    }
}

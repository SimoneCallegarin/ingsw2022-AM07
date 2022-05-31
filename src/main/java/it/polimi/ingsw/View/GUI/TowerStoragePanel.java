package it.polimi.ingsw.View.GUI;

import it.polimi.ingsw.Model.Enumeration.TowerColors;
import it.polimi.ingsw.Model.Game;

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
        add(new TowerButton(TowerColors.BLACK));

    }
}

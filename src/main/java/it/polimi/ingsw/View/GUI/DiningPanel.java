package it.polimi.ingsw.View.GUI;

import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Model.Game;

import javax.swing.*;
import java.awt.*;

public class DiningPanel extends JPanel {
    GridBagConstraints c;
    Game game;

    public DiningPanel(Game game) {
        setLayout(new GridBagLayout());
        c = new GridBagConstraints();
        this.game = game;
        setOpaque(false);
        setBorder(BorderFactory.createLineBorder(Color.black));

        InitializeDining();

    }

    private void InitializeDining() {
    }

}
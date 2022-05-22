package it.polimi.ingsw.View.GUI;

import it.polimi.ingsw.Model.Game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class GameScreenPanel extends JPanel {
    Game game;

    public GameScreenPanel(LayoutManager layout, Game game) {
        super(layout);
        setBackground(Color.CYAN);
        this.game=game;
        printDashboard();

    }

    private void printDashboard(){
        for(int i=0;i<game.getNumberOfPlayers();i++){
            add(new DashboardPanel(new GridBagLayout(),game));
        }
    }
}

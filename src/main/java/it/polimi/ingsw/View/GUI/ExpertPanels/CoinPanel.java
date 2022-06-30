package it.polimi.ingsw.View.GUI.ExpertPanels;

import it.polimi.ingsw.View.GUI.ImagesLoader;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**
 * Panel representing a coin, used when placed on a used character card
 */
public class CoinPanel extends JPanel {
    /**
     * Constructor of CoinPanel
     */
    public CoinPanel() {
        setOpaque(false);
        setPreferredSize(new Dimension(40,40));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        BufferedImage img= ImagesLoader.coinsImageLoader(this.getClass().getClassLoader());
        g.drawImage(img,0,0,getWidth(),getHeight(),null);
    }
}

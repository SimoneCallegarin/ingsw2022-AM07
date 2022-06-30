package it.polimi.ingsw.View.GUI.ExpertPanels;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import static it.polimi.ingsw.View.GUI.GUIConstants.*;
import static it.polimi.ingsw.View.GUI.ImagesLoader.coinImageLoader;

/**
 * Panel representing a coin, used when placed on a used character card
 */
public class CoinPanel extends JPanel {

    /**
     * Image of the coin.
     */
    private final BufferedImage coinImage;

    /**
     * Constructor of CoinPanel.
     */
    public CoinPanel() {
        setOpaque(false);
        setPreferredSize(coinDimension);
        coinImage = coinImageLoader();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(coinImage,coinPositionX,coinPositionY,getWidth(),getHeight(),null);
    }
}

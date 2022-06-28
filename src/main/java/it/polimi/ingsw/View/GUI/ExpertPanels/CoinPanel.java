package it.polimi.ingsw.View.GUI.ExpertPanels;

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
        ClassLoader cl=this.getClass().getClassLoader();
        InputStream url=cl.getResourceAsStream("GameTable/Moneta_base.png");
        BufferedImage img=null;
        try{
            assert url!=null;
            img= ImageIO.read(url);
        }catch(IOException e){
            e.printStackTrace();
        }
        g.drawImage(img,0,0,getWidth(),getHeight(),null);
    }
}

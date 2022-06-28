package it.polimi.ingsw.View.GUI.IslesPanels;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**
 * Panel representing the Deny card game object placed on isle or on Grandma herbs character card
 */
public class DenyCardPanel extends JPanel {
    /**
     * Constructor of DenyCardPanel
     */
    public DenyCardPanel() {
        setOpaque(true);
        setPreferredSize(new Dimension(25,25));
        setBackground(Color.CYAN);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ClassLoader cl=this.getClass().getClassLoader();
        InputStream url=cl.getResourceAsStream("GameTable/Isles/deny_island_icon.png");
        BufferedImage img=null;
        try{
            img= ImageIO.read(url);
        }catch(IOException e){
            e.printStackTrace();
        }
        g.drawImage(img,0,0,getWidth(),getHeight(),null);
    }
}

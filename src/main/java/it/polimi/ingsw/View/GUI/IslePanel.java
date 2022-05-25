package it.polimi.ingsw.View.GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class IslePanel extends JPanel {

    public IslePanel() {
        setOpaque(false);
        setBorder(BorderFactory.createLineBorder(Color.black));
        setMinimumSize(new Dimension(100,100));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ClassLoader cl=this.getClass().getClassLoader();
        InputStream url=cl.getResourceAsStream("island1.png");
        BufferedImage img=null;
        try{
            img= ImageIO.read(url);
        }catch(IOException e){
            e.printStackTrace();
        }
        g.drawImage(img,0,0,getWidth(),getHeight(),null);
    }
}

package it.polimi.ingsw.View.GUI;

import it.polimi.ingsw.Model.Enumeration.Mages;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class MagePanel extends JPanel {
    int mage;

    public MagePanel(int i) {
        this.mage=i;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ClassLoader cl=this.getClass().getClassLoader();
        InputStream url=null;
        switch (mage){
            case 0 -> url=cl.getResourceAsStream("GameTable/Assistant_Cards/retro/MYSTICAL_WIZARD.png");
            case 1 -> url=cl.getResourceAsStream("GameTable/Assistant_Cards/retro/WEALTHY_KING.png");
            case 2 -> url=cl.getResourceAsStream("GameTable/Assistant_Cards/retro/CLEVER_WITCH.png");
            case 3 -> url=cl.getResourceAsStream("GameTable/Assistant_Cards/retro/ANCIENT_SAGE.png");
        }
        BufferedImage img= null;
        try {
            if (url != null)
                img = ImageIO.read(url);
        }catch (IOException e) {
            e.printStackTrace();
        }
        g.drawImage(img,0,0,getWidth(),getHeight(),null);
    }
}

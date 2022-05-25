package it.polimi.ingsw.View.GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**
 * the background of the first screen to appear to the user where he will have to submit his preferences
 */
public class InitialBackgroundPanel extends JPanel {

    public InitialBackgroundPanel(LayoutManager layout, int width, int height) {
        super(layout);
        setBackground(Color.blue);
        //adding borders
        add(Box.createRigidArea(new Dimension(width/3,height)),BorderLayout.WEST);
        add(Box.createRigidArea(new Dimension(width/3,height)),BorderLayout.EAST);
        add(Box.createRigidArea(new Dimension(width,height/3)),BorderLayout.NORTH);
        add(Box.createRigidArea(new Dimension(width,height/3)),BorderLayout.SOUTH);
    }

    @Override
    protected void paintComponent(Graphics g) {
        printImage(g,"eriantys background.jpg",0,0,getWidth(),getHeight());
        printImage(g,"LOGO CRANIO CREATIONS_bianco.png",1550,800,140,200);

    }

    private void printImage(Graphics g, String image, int posX, int posY, int width, int height) {
        ClassLoader cl = this.getClass().getClassLoader();
        InputStream url = cl.getResourceAsStream(image);
        BufferedImage img= null;
        try {
            if (url != null)
                img = ImageIO.read(url);
        }catch (IOException e) {
            e.printStackTrace();
        }
        g.drawImage(img, posX, posY, width, height, null);
    }

}

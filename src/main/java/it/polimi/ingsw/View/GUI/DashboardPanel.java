package it.polimi.ingsw.View.GUI;

import it.polimi.ingsw.Model.Game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class DashboardPanel extends JPanel {
    Game game;
    GridBagConstraints c;
    ClassLoader cl=this.getClass().getClassLoader();


    public DashboardPanel(LayoutManager layout, Game game) {
        super(layout);
        this.game=game;
        c=new GridBagConstraints();

        initializeDashboard();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        InputStream url = cl.getResourceAsStream("PLANCIA GIOCO V.png");
        BufferedImage img= null;
        try {
            img = ImageIO.read(url);
        }catch (IOException e) {
            e.printStackTrace();
        }

        g.drawImage(img,0,0,getWidth(),getHeight(),null);
    }

    private void initializeDashboard(){
        c.gridx=0;
        c.gridy=0;
        add(new EntrancePanel(new GridBagLayout(),game),c);
        c.gridy=1;
        add(new DiningPanel(new GridBagLayout(),game),c);
        //add DiningPanel
    }

}

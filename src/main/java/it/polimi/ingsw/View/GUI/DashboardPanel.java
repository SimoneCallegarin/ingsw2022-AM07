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


    public DashboardPanel( Game game,int width,int height) {
        setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
        this.game=game;
        c=new GridBagConstraints();
        setSize(width,height);
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
        int entranceHeight;
        c.fill=GridBagConstraints.HORIZONTAL;
        c.gridx=0;
        c.gridy=2;
        add(new EntrancePanel(new GridBagLayout(),game, getWidth(),100),c);
        c.gridy=1;
        add(new DiningPanel(new GridBagLayout(),game,getWidth(),600),c);
        c.gridy=0;
        add(new TowerStoragePanel((new GridBagLayout()),game, getWidth(),100),c);
    }

}

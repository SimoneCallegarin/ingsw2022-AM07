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
    GridBagConstraints entranceConstraints;
    GridBagConstraints diningConstraints;
    GridBagConstraints towerStorageConstraints;

    ClassLoader cl=this.getClass().getClassLoader();


    public DashboardPanel( Game game) {
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createLineBorder(Color.black));
        this.game=game;
        entranceConstraints =new GridBagConstraints();
        diningConstraints= new GridBagConstraints();
        towerStorageConstraints= new GridBagConstraints();


        initializeDashboard();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        InputStream url = cl.getResourceAsStream("Dashboard/Complete.png");
        BufferedImage img= null;
        try {
            if (url != null)
                img = ImageIO.read(url);
        }catch (IOException e) {
            e.printStackTrace();
        }
        g.drawImage(img,0,0,getWidth(),getHeight(),null);
    }

    private void initializeDashboard(){
        entranceConstraints.fill=GridBagConstraints.BOTH;
        entranceConstraints.gridy=0;
        entranceConstraints.weightx=1;
        entranceConstraints.weighty=0.35;
        add(new TowerStoragePanel(game),entranceConstraints);

        diningConstraints.fill=GridBagConstraints.BOTH;
        diningConstraints.gridy=1;
        diningConstraints.weightx=1;
        diningConstraints.weighty=1;
        add(new DiningPanel(game),diningConstraints);

        towerStorageConstraints.fill=GridBagConstraints.BOTH;
        towerStorageConstraints.gridy=2;
        towerStorageConstraints.weightx=1;
        towerStorageConstraints.weighty=0.25;
        add(new EntrancePanel(game),towerStorageConstraints);

    }

}

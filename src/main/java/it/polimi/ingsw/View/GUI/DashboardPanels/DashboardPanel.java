package it.polimi.ingsw.View.GUI.DashboardPanels;

import it.polimi.ingsw.Model.Game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class DashboardPanel extends JPanel {
    Game game;
    TowerStoragePanel towerStorage;
    GridBagConstraints towerStorageConstraints;

    DiningPanel dining;
    GridBagConstraints diningConstraints;

    EntrancePanel entrance;
    GridBagConstraints entranceConstraints;
    int playerID;

    ClassLoader cl=this.getClass().getClassLoader();


    public DashboardPanel( Game game, int playerID) {
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createLineBorder(Color.black));
        this.game=game;
        this.playerID=playerID;
        this.towerStorage=new TowerStoragePanel(game, playerID);
        this.dining=new DiningPanel(game,playerID);
        this.entrance=new EntrancePanel(game,playerID);
        towerStorageConstraints =new GridBagConstraints();
        diningConstraints= new GridBagConstraints();
        entranceConstraints= new GridBagConstraints();

        initializeDashboard();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        InputStream url = cl.getResourceAsStream("Dashboard/Semi_empty.png");
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
        towerStorageConstraints.fill=GridBagConstraints.BOTH;
        towerStorageConstraints.gridy=0;
        towerStorageConstraints.weightx=1;
        towerStorageConstraints.weighty=0.35;
        towerStorageConstraints.ipady=-90;
        add(towerStorage, towerStorageConstraints);

        diningConstraints.fill=GridBagConstraints.BOTH;
        diningConstraints.gridy=1;
        diningConstraints.weightx=1;
        diningConstraints.weighty=1;
        add(dining,diningConstraints);

        entranceConstraints.fill=GridBagConstraints.BOTH;
        entranceConstraints.gridy=2;
        entranceConstraints.weightx=1;
        entranceConstraints.weighty=0.25;
        entranceConstraints.ipady=-50;
        add(entrance,entranceConstraints);
    }


    public TowerStoragePanel getTowerStorage() {
        return towerStorage;
    }

    public DiningPanel getDining() {
        return dining;
    }

    public EntrancePanel getEntrance() {
        return entrance;
    }
}

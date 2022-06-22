package it.polimi.ingsw.View.GUI.DashboardPanels;

import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Observer.ViewObserver;
import it.polimi.ingsw.View.GUI.IslesPanels.TableCenterPanel;
import it.polimi.ingsw.View.StorageOfModelInformation.ModelStorage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class DashboardPanel extends JPanel {

    ModelStorage storage;
    TowerStoragePanel towerStorage;
    GridBagConstraints towerStorageConstraints;

    DiningPanel dining;
    GridBagConstraints diningConstraints;

    EntrancePanel entrance;
    GridBagConstraints entranceConstraints;

    int playerID;

    ClassLoader cl=this.getClass().getClassLoader();




    public DashboardPanel(ModelStorage storage, int playerID, ArrayList<ViewObserver> viewObservers, TableCenterPanel tableCenterPanel) {
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createLineBorder(Color.black));

        this.storage=storage;
        this.playerID=playerID;
        this.towerStorage=new TowerStoragePanel(storage, playerID);
        this.dining=new DiningPanel(storage,playerID);
        this.entrance=new EntrancePanel(storage,playerID,viewObservers,tableCenterPanel,this);
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

    public void initializeDashboard(){

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
        entrance.initializeEntrance(storage);
    }


    public TowerStoragePanel getTowerStorage() {
        return towerStorage;
    }

    public DiningPanel getDining() { return dining; }

    public EntrancePanel getEntrance() {
        return entrance;
    }
}

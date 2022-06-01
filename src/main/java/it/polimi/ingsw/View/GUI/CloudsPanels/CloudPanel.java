package it.polimi.ingsw.View.GUI.CloudsPanels;

import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.View.GUI.Buttons.StudentButton;
import it.polimi.ingsw.View.StorageOfModelInformation.ModelStorage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class CloudPanel extends JPanel {
    ClassLoader cl;
    ModelStorage storage;
    GridBagConstraints constraints;

    public CloudPanel(ModelStorage storage,int cloudID) {
        cl=this.getClass().getClassLoader();
        this.storage=storage;
        setLayout(new GridBagLayout());
        constraints =new GridBagConstraints();
        setBackground(Color.CYAN);
        setOpaque(false);
        InitializeCloud(cloudID);
    }

    @Override
    protected void paintComponent(Graphics g) {
        InputStream url = cl.getResourceAsStream("GameTable/Clouds/cloud_card.png");
        BufferedImage img= null;
        try {
            if (url != null)
                img = ImageIO.read(url);
        }catch (IOException e) {
            e.printStackTrace();
        }
        g.drawImage(img,0,0,getWidth(),getHeight(),null);
    }

    private void InitializeCloud(int cloudID){
        constraints.gridx=0;
        constraints.gridy=0;

        for(RealmColors color:RealmColors.values()){
            for(int i=0;i<storage.getGameTable().getCloud(cloudID).getStudentsByColor(color);i++){
                add(new StudentButton(color), constraints);
                if(constraints.gridx==3){
                    constraints.gridx=-1;
                    constraints.gridy++;
                }
                constraints.gridx++;
            }
        }

    }
}

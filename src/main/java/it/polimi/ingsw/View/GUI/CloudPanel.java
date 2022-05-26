package it.polimi.ingsw.View.GUI;

import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.View.GUI.Buttons.StudentButton;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class CloudPanel extends JPanel {
    ClassLoader cl;
    Game game;
    GridBagConstraints constraints;

    public CloudPanel(Game game) {
        cl=this.getClass().getClassLoader();
        this.game=game;
        setLayout(new GridBagLayout());
        constraints =new GridBagConstraints();
        setBackground(Color.CYAN);
        setOpaque(false);
        InitializeCloud();
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

    private void InitializeCloud(){
        constraints.gridx=0;
        constraints.gridy=0;

        for(RealmColors color:RealmColors.values()){
            for(int i=0;i<game.getGameTable().getCloud(0).getStudentsByColor(color);i++){
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

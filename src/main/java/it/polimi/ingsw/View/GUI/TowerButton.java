package it.polimi.ingsw.View.GUI;

import it.polimi.ingsw.Model.Enumeration.TowerColors;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class TowerButton extends JButton {

    ClassLoader cl=this.getClass().getClassLoader();

    public TowerButton(TowerColors color) {
        setBorder(BorderFactory.createEmptyBorder());
        setContentAreaFilled(false);
        printTower(color);
    }

    private void printTower(TowerColors color){
        InputStream url=null;
        switch (color){
            case BLACK -> url=cl.getResourceAsStream("Dashboard/Tower_storage/black_tower.png");
            case WHITE -> url=cl.getResourceAsStream("Dashboard/Tower_storage/white_tower.png");
            case GREY -> url=cl.getResourceAsStream("Dashboard/Tower_storage/grey_tower.png");
        }
        BufferedImage img=null;
        try {
            assert url != null;
            img= ImageIO.read(url);
        }catch (IOException e){
            e.printStackTrace();
        }
        ImageIcon towerImg=new ImageIcon(img);
        setIcon(towerImg);
    }
}

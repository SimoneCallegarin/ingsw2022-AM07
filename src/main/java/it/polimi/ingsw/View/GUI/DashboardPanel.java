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
    ClassLoader cl = this.getClass().getClassLoader();
    InputStream redStudentUrl;
    InputStream yellowStudentUrl;
    InputStream greenStudentUrl;
    InputStream pinkStudentUrl;
    InputStream blueStudentUrl;
    BufferedImage redStudentImg;
    BufferedImage yellowStudentImg;
    BufferedImage greenStudentImg;
    BufferedImage pinkStudentImg;
    BufferedImage blueStudentImg;

    public DashboardPanel(LayoutManager layout, Game game) {
        super(layout);
        this.game=game;
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
        redStudentUrl=cl.getResourceAsStream("student_red.png");
        yellowStudentUrl=cl.getResourceAsStream("student_yellow.png");
        greenStudentUrl=cl.getResourceAsStream("student_green.png");
        pinkStudentUrl=cl.getResourceAsStream("student_pink.png");
        blueStudentUrl=cl.getResourceAsStream("student_blue.png");
    }
}

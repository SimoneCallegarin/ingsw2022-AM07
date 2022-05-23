package it.polimi.ingsw.View.GUI;

import it.polimi.ingsw.Model.Game;

import it.polimi.ingsw.View.StorageOfModelInformation.ModelStorage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class GameScreenPanel extends JPanel {
    Game game;
    private  final int DASHBOARD_WIDTH = 250;
    private  final int DASHBOARD_HEIGHT = 800;

    private Graphics g;

    private ModelStorage storage;

    private void setGraphics(Graphics g) { this.g = g; }

    public void setStorage(ModelStorage storage) { this.storage = storage; }

    public ModelStorage getStorage() { return storage; }

    /**
     * Create a new buffered JPanel with the specified layout manager
     *
     * @param layout the LayoutManager to use
     */
    public GameScreenPanel(LayoutManager layout, Game game) {
        super(layout);
        setBackground(Color.CYAN);
        this.game=game;
        for(int i=0; i<4;i++){
            add(new DashboardPanel(game,DASHBOARD_WIDTH,DASHBOARD_HEIGHT));                       // 4 -> storage.getNumberOfPlayers();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        setGraphics(g);

            //printDashboards(i);

    }

    /*
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
    */

    private void printDashboard(int posX, int posY) {
        ClassLoader cl = this.getClass().getClassLoader();
        InputStream url = cl.getResourceAsStream("PLANCIA GIOCO V.png");
        BufferedImage img= null;
        try {
            if (url != null)
                img = ImageIO.read(url);
        }catch (IOException e) {
            e.printStackTrace();
        }
        g.drawImage(img, posX, posY, DASHBOARD_WIDTH, DASHBOARD_HEIGHT,null);
    }

    public void printDashboards(int playerID) {
        int posX;
        int posY;

        if(playerID==0||playerID==2)
            posX = 0;
        else
            posX = getWidth()-DASHBOARD_WIDTH;

        if(playerID==0||playerID==1)
            posY = 0;
        else
            posY = DASHBOARD_HEIGHT+10;

        printDashboard(posX,posY);

    }
}

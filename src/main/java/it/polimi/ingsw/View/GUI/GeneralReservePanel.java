package it.polimi.ingsw.View.GUI;

import it.polimi.ingsw.View.StorageOfModelInformation.ModelStorage;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Panel representing the general reserve of money, placed in the center of the screen
 */
public class GeneralReservePanel extends JPanel {
    /**
     * ModelStorage reference to retrieve the general reserve state
     */
    private final ModelStorage storage;

    /**
     * Constructor of GeneralReservePanel
     * @param storage ModelStorage reference to retrieve the general reserve state
     */
    public GeneralReservePanel(ModelStorage storage) {
        setOpaque(false);
        setBackground(Color.CYAN);
        this.storage=storage;
        initializeGeneralReserve();
    }

    /**
     * Draws and updates the generalReservePanel according to storage information
     */
    public void initializeGeneralReserve(){
        this.removeAll();
        JLabel moneyLabel = new JLabel();
        moneyLabel.setOpaque(false);
        moneyLabel.setText("General reserve:"+storage.getGameTable().getGeneralMoneyReserve());
        moneyLabel.setFont(new Font("Dialog.bold", Font.PLAIN,11));
        add(moneyLabel);
        this.repaint();
        this.validate();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        BufferedImage img=ImagesLoader.coinsImageLoader(this.getClass().getClassLoader());
        g.drawImage(img,0,0,getWidth(),getHeight(),null);
    }

}

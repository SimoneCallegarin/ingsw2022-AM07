package it.polimi.ingsw.View.GUI;

import it.polimi.ingsw.Model.Game;

import javax.swing.*;
import java.awt.*;

public class CloudsContainerPanel extends JPanel {
    Game game;
    public CloudsContainerPanel(Game game) {
        this.game=game;
        setLayout(new GridBagLayout());
        GridBagConstraints mainConstraints=new GridBagConstraints();

        //i create the grid for the clouds
        JPanel cloudContainer=new JPanel(new GridLayout(1,game.getGameTable().getClouds().size()));
        cloudContainer.setBackground(Color.CYAN);
        for(int i=0;i<game.getGameTable().getClouds().size();i++){
            cloudContainer.add(new CloudPanel(game,i));
        }

        mainConstraints.fill=GridBagConstraints.BOTH;
        mainConstraints.weighty=1;
        mainConstraints.weightx=1;
        mainConstraints.gridx=0;
        mainConstraints.gridy=0;
        add(cloudContainer,mainConstraints);

        //this will be the space for the character cards
        mainConstraints.gridy=1;
        JPanel bluPanel=new JPanel();
        bluPanel.setBackground(Color.CYAN);
        add(bluPanel,mainConstraints);
    }
}

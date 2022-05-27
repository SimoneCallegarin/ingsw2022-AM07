package it.polimi.ingsw.View.GUI.IslesPanels;

import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.View.GUI.CloudsContainerPanel;

import javax.swing.*;
import java.awt.*;

/**
 * the panel use to contain the isles
 */
public class IsleManagerPanel extends JPanel {
    Game game;

    public IsleManagerPanel(Game game) {
        this.game=game;
        setBackground(Color.CYAN);
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createLineBorder(Color.black));

        GridBagConstraints c=new GridBagConstraints();
        GridBagConstraints centerConstraints=new GridBagConstraints();

        //I create three columns, the first and the third are simple 4x1 grids with an isle in every cell
        //the second one is a 3x1 panel with a GridBagLayout with the first and third row filled with the remaining isles, while
        //the center row will be used to take the clouds and the character cards
        JPanel isleContainerDx=new JPanel(new GridLayout(4,1));
        isleContainerDx.setBackground(Color.CYAN);
        JPanel isleContainerCenter=new JPanel(new GridBagLayout());
        isleContainerCenter.setBackground(Color.CYAN);
        JPanel isleContainerSx=new JPanel(new GridLayout(4,1));
        isleContainerSx.setBackground(Color.CYAN);

        c.fill=GridBagConstraints.BOTH;
        c.weighty=0.5;
        c.weightx=0.5;
        c.gridy=0;
        c.gridx=0;
        add(isleContainerSx,c);
        isleContainerSx.add(new IslePanel(game,0));
        isleContainerSx.add(new IslePanel(game,1));
        isleContainerSx.add(new IslePanel(game,2));
        isleContainerSx.add(new IslePanel(game,3));

        c.gridx=2;
        c.weighty=0.5;
        c.weightx=0.5;
        add(isleContainerDx,c);
        isleContainerDx.add(new IslePanel(game,4));
        isleContainerDx.add(new IslePanel(game,5));
        isleContainerDx.add(new IslePanel(game,6));
        isleContainerDx.add(new IslePanel(game,7));

        c.gridx=1;
        c.weighty=1;
        c.weightx=1;
        add(isleContainerCenter,c);
            JPanel firstIsleContainer1x2=new JPanel(new GridLayout(1,2));
            firstIsleContainer1x2.setBackground(Color.CYAN);
            JPanel secondIsleContainer1x2=new JPanel(new GridLayout(1,2));
            secondIsleContainer1x2.setBackground(Color.CYAN);
            firstIsleContainer1x2.add(new IslePanel(game,8));
            firstIsleContainer1x2.add(new IslePanel(game,9));
            secondIsleContainer1x2.add(new IslePanel(game,10));
            secondIsleContainer1x2.add(new IslePanel(game,11));

            centerConstraints.fill=GridBagConstraints.BOTH;
            centerConstraints.gridx=0;
            centerConstraints.gridy=0;
            centerConstraints.weightx=0.5;
            centerConstraints.weighty=0.5;

            isleContainerCenter.add(firstIsleContainer1x2,centerConstraints);

            centerConstraints.fill=GridBagConstraints.BOTH;
            centerConstraints.gridy=1;
            centerConstraints.weighty=1;
            centerConstraints.weightx=1;
            CloudsContainerPanel cloudsContainerPanel=new CloudsContainerPanel(game);
            cloudsContainerPanel.setBackground(Color.CYAN);
            isleContainerCenter.add(cloudsContainerPanel,centerConstraints);

            centerConstraints.fill=GridBagConstraints.BOTH;
            centerConstraints.gridy=2;
            centerConstraints.weightx=0.5;
            centerConstraints.weighty=0.5;
            isleContainerCenter.add(secondIsleContainer1x2,centerConstraints);

    }

}

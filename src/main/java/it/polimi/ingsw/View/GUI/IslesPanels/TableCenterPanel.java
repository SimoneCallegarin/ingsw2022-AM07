package it.polimi.ingsw.View.GUI.IslesPanels;

import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.View.GUI.AssistantCardPanel;
import it.polimi.ingsw.View.GUI.CloudsContainerPanel;
import it.polimi.ingsw.View.GUI.MagePanel;

import javax.swing.*;
import java.awt.*;

/**
 * the panel use to contain the isles, the clouds and the character cards
 */
public class TableCenterPanel extends JPanel {
    Game game;

    /**
     * the panel containing the leftmost three isles;
     */
    JPanel isleContainerSx;
    /**
     * the panel container the rightmost three isles
     */
    JPanel isleContainerDx;
    /**
     * the center panel containing two rows of isles and a center row containing the clouds and the character cards
     */
    JPanel isleContainerCenter;

    /**
     * the panel containing the discard pile or the mage for the first and third player (displayed on the left)
     */
    JPanel assistantContainerSx;
    /**
     * the panel containing the discard pile or the mage for the second and the fourth player(displayed on the right)
     */
    JPanel assistantContainerDx;

    /**
     * one of the two isle panel container in the isleContainerCenter
     */
    JPanel firstIsleContainer1x2;

    /**
     * one of the two isle panel container in the isleContainerCenter
     */
    JPanel secondIsleContainer1x2;

    public TableCenterPanel(Game game) {
        this.game=game;
        setBackground(Color.CYAN);
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createLineBorder(Color.black));

        GridBagConstraints c=new GridBagConstraints();
        GridBagConstraints centerConstraints=new GridBagConstraints();

        //I create five columns, the first and the fifth one will contain the discard pile of each player,
        //the second and the fourth one will contain two 4x1 grids containing four isles
        //the third one will contain a another 3x1 panel with two 1x2 grids on the top and on the bottom, while in the
        //center cell there are the clouds and the character cards
        isleContainerDx=new JPanel(new GridLayout(4,1));
        isleContainerDx.setBackground(Color.CYAN);
        isleContainerCenter=new JPanel(new GridBagLayout());
        isleContainerCenter.setBackground(Color.CYAN);
        isleContainerSx=new JPanel(new GridLayout(4,1));
        isleContainerSx.setBackground(Color.CYAN);

        GridLayout gridLayout=new GridLayout(2,1);//layout for the assistant card containers
        gridLayout.setVgap(550);
        assistantContainerSx=new JPanel(gridLayout);
        assistantContainerSx.setBackground(Color.CYAN);
        assistantContainerDx=new JPanel(gridLayout);
        assistantContainerDx.setBackground(Color.CYAN);

        for(int i=0;i<game.getNumberOfPlayers();i++){
            if(i%2==0){
                assistantContainerSx.add(new MagePanel(game.getPlayerByIndex(i).getMage()));
            }else{
                assistantContainerDx.add(new MagePanel(game.getPlayerByIndex(i).getMage()));
            }
        }

        c.fill=GridBagConstraints.BOTH;
        c.gridx=0;
        c.gridy=0;
        c.weighty=1;
        c.weightx=0.2;
        add(assistantContainerSx,c);

        c.gridx=4;
        add(assistantContainerDx,c);

        c.weighty=0.5;
        c.weightx=0.5;
        c.gridx=1;
        add(isleContainerSx,c);
        isleContainerSx.add(new IslePanel(game,0));
        isleContainerSx.add(new IslePanel(game,1));
        isleContainerSx.add(new IslePanel(game,2));
        isleContainerSx.add(new IslePanel(game,3));

        c.gridx=3;
        c.weighty=0.5;
        c.weightx=0.5;
        add(isleContainerDx,c);
        isleContainerDx.add(new IslePanel(game,4));
        isleContainerDx.add(new IslePanel(game,5));
        isleContainerDx.add(new IslePanel(game,6));
        isleContainerDx.add(new IslePanel(game,7));

        c.gridx=2;
        c.weighty=1;
        c.weightx=1;
        add(isleContainerCenter,c);
            firstIsleContainer1x2=new JPanel(new GridLayout(1,2));
            firstIsleContainer1x2.setBackground(Color.CYAN);
            secondIsleContainer1x2=new JPanel(new GridLayout(1,2));
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

    public void UpdateAssistCard(int playerID, int turnOrder){
        switch(playerID){
            case 0->{
                assistantContainerSx.remove(0);
                assistantContainerSx.add(new AssistantCardPanel(turnOrder),0);
                assistantContainerSx.validate();//if you change a component that's already been displayed you need to validate it to display the changes
            }
            case 1->{
                assistantContainerDx.remove(0);
                assistantContainerDx.add(new AssistantCardPanel(turnOrder),0);
                assistantContainerDx.validate();
            }
            case 2->{
                assistantContainerSx.remove(1);
                assistantContainerSx.add(new AssistantCardPanel(turnOrder),1);
                assistantContainerSx.validate();
            }
            case 3->{
                assistantContainerDx.remove(1);
                assistantContainerDx.add(new AssistantCardPanel(turnOrder),1);
                assistantContainerDx.validate();
            }
        }
    }

}

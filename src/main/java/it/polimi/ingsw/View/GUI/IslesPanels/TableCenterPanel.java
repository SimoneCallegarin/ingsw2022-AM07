package it.polimi.ingsw.View.GUI.IslesPanels;

import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.View.GUI.AssistantCardPanel;
import it.polimi.ingsw.View.GUI.CharacterPanel;
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
     * the panel containing the discard pile (or the mage) and the coins for the first and third player (displayed on the left)
     */
    JPanel assistantAndMoneyContainerSx;
    /**
     * the panel containing the discard pile (or the mage) and the coins for the second and the fourth player(displayed on the right)
     */
    JPanel assistantAndMoneyContainerDx;
    /**
     * one of the two isle panel container in the isleContainerCenter
     */
    JPanel firstIsleContainer1x2;
    /**
     * one of the two isle panel container in the isleContainerCenter
     */
    JPanel secondIsleContainer1x2;
    /**
     * panel that contains the character cards
     */
    JPanel charactersContainer;

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

        GridLayout gridLayout=new GridLayout(2,1);//layout for the assistant card and money containers
        gridLayout.setVgap(220);
        assistantAndMoneyContainerSx =new JPanel(gridLayout);
        assistantAndMoneyContainerSx.setBackground(Color.CYAN);
        assistantAndMoneyContainerDx =new JPanel(gridLayout);
        assistantAndMoneyContainerDx.setBackground(Color.CYAN);

        JPanel assistantAndMoneyPanel1=new JPanel(new GridBagLayout());
        assistantAndMoneyPanel1.setBackground(Color.CYAN);
        JPanel assistantAndMoneyPanel2=new JPanel(new GridBagLayout());
        assistantAndMoneyPanel2.setBackground(Color.CYAN);
        JPanel assistantAndMoneyPanel3=new JPanel(new GridBagLayout());
        assistantAndMoneyPanel3.setBackground(Color.CYAN);
        JPanel assistantAndMoneyPanel4=new JPanel(new GridBagLayout());
        assistantAndMoneyPanel4.setBackground(Color.CYAN);
        GridBagConstraints assistantAndMoneyConstraints=new GridBagConstraints();

        assistantAndMoneyConstraints.gridy=0;
        assistantAndMoneyConstraints.gridx=0;
        assistantAndMoneyConstraints.fill=GridBagConstraints.BOTH;
        assistantAndMoneyConstraints.weightx=1;
        assistantAndMoneyConstraints.weighty=1;

        assistantAndMoneyContainerSx.add(assistantAndMoneyPanel1);
        assistantAndMoneyContainerSx.add(assistantAndMoneyPanel3);
        assistantAndMoneyContainerDx.add(assistantAndMoneyPanel2);
        assistantAndMoneyContainerDx.add(assistantAndMoneyPanel4);
        int idxSx=0;
        int idxDx=0;
        for(int i=0;i<game.getNumberOfPlayers();i++){
            if(i%2==0){
                assistantAndMoneyConstraints.weighty=0.1;
                ((JPanel) assistantAndMoneyContainerSx.getComponent(idxSx)).add(new JLabel("Username:"+game.getPlayerByIndex(i).getNickname()),assistantAndMoneyConstraints);
                if(game.getNumberOfPlayers()==4){
                    assistantAndMoneyConstraints.gridy++;
                    assistantAndMoneyConstraints.weighty=0.1;
                    ((JPanel) assistantAndMoneyContainerSx.getComponent(idxSx)).add(new JLabel("Squad: "+game.getPlayerByIndex(i).getSquad()),assistantAndMoneyConstraints);
                }
                assistantAndMoneyConstraints.weighty=1;
                assistantAndMoneyConstraints.gridy++;
                ((JPanel) assistantAndMoneyContainerSx.getComponent(idxSx)).add(new MagePanel(game.getPlayerByIndex(i).getMage()),assistantAndMoneyConstraints);
                assistantAndMoneyConstraints.gridy=0;
                idxSx++;
            }else{
                assistantAndMoneyConstraints.weighty=0.1;
                ((JPanel) assistantAndMoneyContainerDx.getComponent(idxDx)).add(new JLabel("Username:"+game.getPlayerByIndex(i).getNickname()),assistantAndMoneyConstraints);
                if(game.getNumberOfPlayers()==4){
                    assistantAndMoneyConstraints.gridy++;
                    assistantAndMoneyConstraints.weighty=0.1;
                    ((JPanel) assistantAndMoneyContainerDx.getComponent(idxDx)).add(new JLabel("Squad: "+game.getPlayerByIndex(i).getSquad()),assistantAndMoneyConstraints);
                }
                assistantAndMoneyConstraints.weighty=1;
                assistantAndMoneyConstraints.gridy++;
                ((JPanel) assistantAndMoneyContainerDx.getComponent(idxDx)).add(new MagePanel(game.getPlayerByIndex(i).getMage()),assistantAndMoneyConstraints);
                assistantAndMoneyConstraints.gridy=0;
                idxDx++;
            }

        }
        //if the gamemode is expert we need to show also the coins
        if(game.isGameMode()) {
            idxSx=0;
            idxDx=0;
            if(game.getNumberOfPlayers()==4){
                assistantAndMoneyConstraints.gridy=3;
            }else{
                assistantAndMoneyConstraints.gridy=2;
            }
            assistantAndMoneyConstraints.weighty=0.1;
            for (int i = 0; i < game.getNumberOfPlayers(); i++) {
                if(i%2==0){
                    ((JPanel) assistantAndMoneyContainerSx.getComponent(idxSx)).add(new JLabel("Coins:"+game.getPlayerByIndex(i).getMoney()),assistantAndMoneyConstraints);
                    idxSx++;
                }else{
                    ((JPanel) assistantAndMoneyContainerDx.getComponent(idxDx)).add(new JLabel("Coins:"+game.getPlayerByIndex(i).getMoney()),assistantAndMoneyConstraints);
                    idxDx++;
                }
            }
        }
        c.fill=GridBagConstraints.BOTH;
        c.gridx=0;
        c.gridy=0;
        c.weighty=1;
        c.weightx=0.2;
        add(assistantAndMoneyContainerSx,c);

        c.gridx=4;
        add(assistantAndMoneyContainerDx,c);

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
        c.weightx=0.5;
        add(isleContainerCenter,c);
            firstIsleContainer1x2=new JPanel(new GridLayout(1,2));
            firstIsleContainer1x2.setBackground(Color.CYAN);
            secondIsleContainer1x2=new JPanel(new GridLayout(1,2));
            secondIsleContainer1x2.setBackground(Color.CYAN);
            charactersContainer=new JPanel(new GridLayout(1,3));
            charactersContainer.setBackground(Color.CYAN);

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
            centerConstraints.weightx=1;
            centerConstraints.weighty=1;
            charactersContainer.add(new CharacterPanel(game.getGameTable().getCharacterCard(0).getCharacterCardName()));
            charactersContainer.add(new CharacterPanel(game.getGameTable().getCharacterCard(1).getCharacterCardName()));
            charactersContainer.add(new CharacterPanel(game.getGameTable().getCharacterCard(2).getCharacterCardName()));
            isleContainerCenter.add(charactersContainer,centerConstraints);

            centerConstraints.fill=GridBagConstraints.BOTH;
            centerConstraints.gridy=3;
            centerConstraints.weightx=0.5;
            centerConstraints.weighty=0.5;
            isleContainerCenter.add(secondIsleContainer1x2,centerConstraints);

    }

    public void UpdateAssistCard(int playerID, int turnOrder){
        switch(playerID){
            case 0->{
                assistantAndMoneyContainerSx.remove(0);
                assistantAndMoneyContainerSx.add(new AssistantCardPanel(turnOrder),0);
                assistantAndMoneyContainerSx.validate();//if you change a component that's already been displayed you need to validate it to display the changes
            }
            case 1->{
                assistantAndMoneyContainerDx.remove(0);
                assistantAndMoneyContainerDx.add(new AssistantCardPanel(turnOrder),0);
                assistantAndMoneyContainerDx.validate();
            }
            case 2->{
                assistantAndMoneyContainerSx.remove(1);
                assistantAndMoneyContainerSx.add(new AssistantCardPanel(turnOrder),1);
                assistantAndMoneyContainerSx.validate();
            }
            case 3->{
                assistantAndMoneyContainerDx.remove(1);
                assistantAndMoneyContainerDx.add(new AssistantCardPanel(turnOrder),1);
                assistantAndMoneyContainerDx.validate();
            }
        }
    }

}

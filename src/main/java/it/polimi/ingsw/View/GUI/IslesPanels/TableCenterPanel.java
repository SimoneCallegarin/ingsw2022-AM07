package it.polimi.ingsw.View.GUI.IslesPanels;

import it.polimi.ingsw.Observer.ViewObserver;
import it.polimi.ingsw.View.GUI.AssistantCardPanel;
import it.polimi.ingsw.View.GUI.CharacterPanel;
import it.polimi.ingsw.View.GUI.CloudsPanels.CloudsContainerPanel;
import it.polimi.ingsw.View.GUI.DashboardPanels.DiningPanel;
import it.polimi.ingsw.View.GUI.DashboardPanels.EntrancePanel;
import it.polimi.ingsw.View.GUI.EventListeners.CharacterCardListener;
import it.polimi.ingsw.View.GUI.EventListeners.IsleListener;
import it.polimi.ingsw.View.GUI.MagePanel;
import it.polimi.ingsw.View.StorageOfModelInformation.ModelStorage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/**
 * the panel use to contain the isles, the clouds and the character cards
 */
public class TableCenterPanel extends JPanel {

    ModelStorage storage;
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
    /**
     * panel containing the discard pile, the coins and the username (and squad) of the first player
     */
    JPanel assistantAndMoneyPanel1;
    /**
     * panel containing the discard pile, the coins and the username (and squad) of the second player
     */
    JPanel assistantAndMoneyPanel2;
    /**
     * panel containing the discard pile, the coins and the username (and squad) of the third player
     */
    JPanel assistantAndMoneyPanel3;
    /**
     * panel containing the discard pile, the coins and the username (and squad) of the fourth player
     */
    JPanel assistantAndMoneyPanel4;
    /**
     * constraints used to add the assistant card and the labels for money and username (and squad) in the assistantAndMoneyPanels
     */
    GridBagConstraints assistantAndMoneyConstraints;
    /**
     * username of the player using the GUI
     */
    String usernamePlaying;
    /**
     * this array contains all the panels representing the isles
     */
    ArrayList<IslePanel> islesPanels;
    /**
     * this array list contains all the panel representing the assistant card, the money, the username and the squad for each player
     */
    ArrayList<JPanel> assistantAndMoneyPanelList;

    public TableCenterPanel(ModelStorage storage, String usernamePlaying) {
        this.storage=storage;
        this.usernamePlaying=usernamePlaying;
        this.islesPanels=new ArrayList<>();
        this.assistantAndMoneyPanelList=new ArrayList<>();
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

        assistantAndMoneyPanel1=new JPanel(new GridBagLayout());
        assistantAndMoneyPanel1.setBackground(Color.CYAN);
        assistantAndMoneyPanelList.add(assistantAndMoneyPanel1);
        assistantAndMoneyPanel2=new JPanel(new GridBagLayout());
        assistantAndMoneyPanel2.setBackground(Color.CYAN);
        assistantAndMoneyPanelList.add(assistantAndMoneyPanel2);
        assistantAndMoneyPanel3=new JPanel(new GridBagLayout());
        assistantAndMoneyPanel3.setBackground(Color.CYAN);
        assistantAndMoneyPanelList.add(assistantAndMoneyPanel3);
        assistantAndMoneyPanel4=new JPanel(new GridBagLayout());
        assistantAndMoneyPanel4.setBackground(Color.CYAN);
        assistantAndMoneyPanelList.add(assistantAndMoneyPanel4);
        assistantAndMoneyConstraints=new GridBagConstraints();

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
        for(int i=0;i<storage.getNumberOfPlayers();i++){
            String username=""+storage.getDashboard(i).getNickname();

            JPanel usernamePanel=new JPanel();
            usernamePanel.setLayout(new BorderLayout());
            usernamePanel.setOpaque(false);

            JLabel usernameLabel=new JLabel(username);
            usernamePanel.add(usernameLabel,BorderLayout.CENTER);

            if(storage.getDashboard(i).getNickname().equals(this.usernamePlaying)){
                usernamePanel.add(new JLabel("(you)"),BorderLayout.PAGE_END);
            }else{
                usernamePanel.add(new JLabel("     "),BorderLayout.PAGE_END);//to make all the panel occupy the same space
            }

            assistantAndMoneyConstraints.weighty=0.1;
            if(i%2==0){
                ((JPanel) assistantAndMoneyContainerSx.getComponent(idxSx)).add(usernamePanel,assistantAndMoneyConstraints);
                if(storage.getNumberOfPlayers()==4){
                    assistantAndMoneyConstraints.gridy++;
                    assistantAndMoneyConstraints.weighty=0.1;
                    ((JPanel) assistantAndMoneyContainerSx.getComponent(idxSx)).add(new JLabel("Squad: "+storage.getDashboard(i).getTeam()),assistantAndMoneyConstraints);
                }
                assistantAndMoneyConstraints.weighty=1;
                assistantAndMoneyConstraints.gridy++;
                ((JPanel) assistantAndMoneyContainerSx.getComponent(idxSx)).add(new MagePanel(i),assistantAndMoneyConstraints);
                assistantAndMoneyConstraints.gridy=0;
                idxSx++;
            }else{
                ((JPanel) assistantAndMoneyContainerDx.getComponent(idxDx)).add(usernamePanel,assistantAndMoneyConstraints);
                if(storage.getNumberOfPlayers()==4){
                    assistantAndMoneyConstraints.gridy++;
                    assistantAndMoneyConstraints.weighty=0.1;
                    ((JPanel) assistantAndMoneyContainerDx.getComponent(idxDx)).add(new JLabel("Squad: "+storage.getDashboard(i).getTeam()),assistantAndMoneyConstraints);
                }
                assistantAndMoneyConstraints.weighty=1;
                assistantAndMoneyConstraints.gridy++;
                ((JPanel) assistantAndMoneyContainerDx.getComponent(idxDx)).add(new MagePanel(i),assistantAndMoneyConstraints);
                assistantAndMoneyConstraints.gridy=0;
                idxDx++;
            }

        }
        //if the gamemode is expert we need to show also the coins
        if(storage.isGameMode()) {
            idxSx=0;
            idxDx=0;
            if(storage.getNumberOfPlayers()==4){
                assistantAndMoneyConstraints.gridy=3;
            }else{
                assistantAndMoneyConstraints.gridy=2;
            }
            assistantAndMoneyConstraints.weighty=0.1;
            for (int i = 0; i < storage.getNumberOfPlayers(); i++) {
                if(i%2==0){
                    ((JPanel) assistantAndMoneyContainerSx.getComponent(idxSx)).add(new JLabel("Coins:"+storage.getDashboard(i).getMoney()),assistantAndMoneyConstraints);
                    idxSx++;
                }else{
                    ((JPanel) assistantAndMoneyContainerDx.getComponent(idxDx)).add(new JLabel("Coins:"+storage.getDashboard(i).getMoney()),assistantAndMoneyConstraints);
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

        //initialize the isles
        for(int i=0;i<12;i++){
            islesPanels.add(new IslePanel(storage,i));
        }

        c.weighty=0.5;
        c.weightx=0.5;
        c.gridx=1;
        add(isleContainerSx,c);
        isleContainerSx.add(islesPanels.get(0));
        isleContainerSx.add(islesPanels.get(11));
        isleContainerSx.add(islesPanels.get(10));
        isleContainerSx.add(islesPanels.get(9));

        c.gridx=3;
        c.weighty=0.5;
        c.weightx=0.5;
        add(isleContainerDx,c);
        isleContainerDx.add(islesPanels.get(3));
        isleContainerDx.add(islesPanels.get(4));
        isleContainerDx.add(islesPanels.get(5));
        isleContainerDx.add(islesPanels.get(6));

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

            firstIsleContainer1x2.add(islesPanels.get(1));
            firstIsleContainer1x2.add(islesPanels.get(2));
            secondIsleContainer1x2.add(islesPanels.get(8));
            secondIsleContainer1x2.add(islesPanels.get(7));

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
            CloudsContainerPanel cloudsContainerPanel=new CloudsContainerPanel(storage);
            cloudsContainerPanel.setBackground(Color.CYAN);
            isleContainerCenter.add(cloudsContainerPanel,centerConstraints);

            if(storage.isGameMode()) {
                centerConstraints.fill = GridBagConstraints.BOTH;
                centerConstraints.gridy = 2;
                centerConstraints.weightx = 1;
                centerConstraints.weighty = 1;
                charactersContainer.add(new CharacterPanel(storage.getGameTable().getCharacterCard(0).getCharacterCardName()));
                charactersContainer.add(new CharacterPanel(storage.getGameTable().getCharacterCard(1).getCharacterCardName()));
                charactersContainer.add(new CharacterPanel(storage.getGameTable().getCharacterCard(2).getCharacterCardName()));
                isleContainerCenter.add(charactersContainer, centerConstraints);
            }

            centerConstraints.fill=GridBagConstraints.BOTH;
            centerConstraints.gridy++;
            centerConstraints.weightx=0.5;
            centerConstraints.weighty=0.5;
            isleContainerCenter.add(secondIsleContainer1x2,centerConstraints);

    }


    public void updateAssistCard(int playerID){
        assistantAndMoneyConstraints.gridx=0;
        if(storage.getNumberOfPlayers()==4){
            assistantAndMoneyConstraints.gridy=2;
        }else{
            assistantAndMoneyConstraints.gridy=1;
        }
        assistantAndMoneyConstraints.weighty=1;
        assistantAndMoneyConstraints.fill=GridBagConstraints.BOTH;
        assistantAndMoneyPanelList.get(playerID).remove(assistantAndMoneyConstraints.gridy);
        assistantAndMoneyPanelList.get(playerID).add(new AssistantCardPanel(storage.getDashboard(playerID).getDiscardPileTurnOrder()), assistantAndMoneyConstraints);
        assistantAndMoneyPanelList.get(playerID).validate();//if you change a component that's already been displayed you need to validate it to display the changes
    }

    public void updateCoins(int playerID){
        assistantAndMoneyConstraints.gridx=0;
        if(storage.getNumberOfPlayers()==4){
            assistantAndMoneyConstraints.gridy=3;
        }else{
            assistantAndMoneyConstraints.gridy=2;
        }
        assistantAndMoneyConstraints.weighty=0.1;
        assistantAndMoneyConstraints.fill=GridBagConstraints.BOTH;
        assistantAndMoneyPanelList.get(playerID).remove(assistantAndMoneyConstraints.gridy);
        assistantAndMoneyPanelList.get(playerID).add(new JLabel("Coins:"+storage.getDashboard(playerID).getMoney()),assistantAndMoneyConstraints);
        assistantAndMoneyPanelList.get(playerID).validate();
    }

    public void updateIsle(int isleID){
        islesPanels.get(isleID).resetIsle();
        islesPanels.get(isleID).initializeIsle();
    }

    public void updateIsleLayout(){
        for (int i=0;i<islesPanels.size();i++){
            islesPanels.get(i).resetIsle();
            islesPanels.get(i).initializeIsle();
        }
    }

    public void setIslesClickable(ArrayList<ViewObserver> viewObserverList, EntrancePanel entrance, DiningPanel dining){
        for(int i=0;i<islesPanels.size();i++){
            islesPanels.get(i).addMouseListener(new IsleListener(this,viewObserverList,entrance,islesPanels.get(i).getIsleID(),dining));
        }
    }

    public void setMNClickable(ArrayList<ViewObserver> viewObserverList){
        for(int i=0;i<islesPanels.size();i++){
            if(!islesPanels.get(i).motherNature){
                islesPanels.get(i).setClickableForMN(viewObserverList);
            }
        }

    }


    public void removeIslesClickable(){
        for(int i=0;i<islesPanels.size();i++){
            islesPanels.get(i).removeMouseListener(islesPanels.get(i).getMouseListeners()[0]);
        }
    }

    public void setClickableCharacters(ArrayList<ViewObserver> viewObserverList) {
        for(int i=0;i<3;i++){
            charactersContainer.getComponent(i).addMouseListener(new CharacterCardListener(viewObserverList,i,this));
        }
    }

    public void removeCharactersClickable() {
        for(int i=0;i<3;i++){
            MouseListener mouseListener=charactersContainer.getComponent(i).getMouseListeners()[0];
            charactersContainer.getComponent(i).removeMouseListener(mouseListener);
        }
    }
}

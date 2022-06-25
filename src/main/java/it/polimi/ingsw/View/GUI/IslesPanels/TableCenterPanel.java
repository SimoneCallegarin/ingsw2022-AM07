package it.polimi.ingsw.View.GUI.IslesPanels;

import it.polimi.ingsw.Observer.ViewObserver;
import it.polimi.ingsw.View.GUI.AssistantCardPanel;
import it.polimi.ingsw.View.GUI.CharacterPanel;
import it.polimi.ingsw.View.GUI.CloudsPanels.CloudsContainerPanel;
import it.polimi.ingsw.View.GUI.DashboardPanels.DiningPanel;
import it.polimi.ingsw.View.GUI.DashboardPanels.EntrancePanel;
import it.polimi.ingsw.View.GUI.EventListeners.CharacterCardListener;
import it.polimi.ingsw.View.GUI.EventListeners.IsleListener;

import it.polimi.ingsw.View.GUI.EventListeners.MNListener;
import it.polimi.ingsw.View.GUI.GameScreenPanel;
import it.polimi.ingsw.View.StorageOfModelInformation.ModelStorage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
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
     * the panel containing the clouds panels
     */
    CloudsContainerPanel cloudsContainerPanel;
    /**
     * constraints used to add the assistant card and the labels for money and username (and squad) in the assistantAndMoneyPanels
     */
    GridBagConstraints assistantAndMoneyConstraints;
    /**
     * username of the player using the GUI
     */
    String usernamePlaying;

    Color nicknameColor;
    /**
     * this array contains all the panels representing the isles
     */
    ArrayList<IslePanel> islesPanels;
    /**
     * this array list stores all the panel representing the assistant card, the money, the username and the squad for each player
     */
    ArrayList<JPanel> assistantAndMoneyPanelList;
    /**
     * ArrayList storing the containers for the assistantAndMoneyPanels
     */
    ArrayList<JPanel> assistantAndMoneyContainers;
    /**
     * arrayList to store the labels for showing the available coins for each player
     */
    ArrayList<JLabel> coinsLabel;
    /**
     * arraylist to store the assistant card panels currently on screen
     */
    ArrayList<AssistantCardPanel> assistantCardPanels;
    /**
     * constraints for the rightmost isle container
     */
    GridBagConstraints icDxConstraints;
    /**
     * constraints for the leftmost isle container
     */
    GridBagConstraints icSxConstraints;
    /**
     * constraints for the first 1x2 isle container (the one on top)
     */
    GridBagConstraints first1x2constraints;
    /**
     * constraints for the second 1x2 isle container (the one on bottom)
     */
    GridBagConstraints second1x2constraints;
    /**
     * array list of panel added on top of isle. The listeners will be added to this array list element
     * in order to make the isles clickable on every point of their panel even if they are full of students,towers etc.
     */
    ArrayList<JPanel> clickablePanels;
    /**
     * array list to store the layered panel added to the isle container. Layered panel are used to permit to add on top of the isle panel
     * a clickable panel
     */
    ArrayList<JLayeredPane> isleLayeredPanels;
    /**
     * array list to store the character cards panel
     */
    ArrayList<CharacterPanel> characterPanels;
    /**
     * gameScreenPanel reference
     */
    GameScreenPanel gsp;
    /**
     * classloader to load images from resource folder
     */
    ClassLoader cl;
    /**
     * array list to store isle images
     */
    ArrayList<BufferedImage> isles;
    /**
     * array list to store students images
     */
    ArrayList<BufferedImage> students;
    /**
     * array list to store towers images
     */
    ArrayList<BufferedImage> towers;

    public TableCenterPanel(ModelStorage storage, String usernamePlaying, Color nicknameColor, ArrayList<ViewObserver> viewObservers, ArrayList<BufferedImage> students, ArrayList<BufferedImage> towers, GameScreenPanel gsp) {
        this.storage=storage;
        this.usernamePlaying=usernamePlaying;
        this.islesPanels=new ArrayList<>();
        this.assistantAndMoneyPanelList=new ArrayList<>();
        this.assistantAndMoneyContainers=new ArrayList<>();
        this.assistantCardPanels=new ArrayList<>();
        this.characterPanels = new ArrayList<>();
        this.gsp = gsp;

        nicknameColor = nicknameColor;
        this.clickablePanels=new ArrayList<>();
        this.isleLayeredPanels=new ArrayList<>();

        setBackground(Color.CYAN);
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createLineBorder(Color.black));

        GridBagConstraints c=new GridBagConstraints();
        GridBagConstraints centerConstraints=new GridBagConstraints();

        //I create five columns, the first and the fifth one will contain the discard pile of each player,
        //the second and the fourth one will contain two 4x1 grids containing four isles
        //the third one will contain another 3x1 panel with two 1x2 grids on the top and on the bottom, while in the
        //center cell there are the clouds and the character cards
        isleContainerDx=new JPanel(new GridBagLayout());
        isleContainerDx.setBackground(Color.CYAN);
        icDxConstraints=new GridBagConstraints();
        isleContainerCenter=new JPanel(new GridBagLayout());
        isleContainerCenter.setBackground(Color.CYAN);
        isleContainerSx=new JPanel(new GridBagLayout());
        icSxConstraints=new GridBagConstraints();
        isleContainerSx.setBackground(Color.CYAN);

        GridLayout gridLayout=new GridLayout(2,1);//layout for the assistant card and money containers
        gridLayout.setVgap(220);
        assistantAndMoneyContainerSx =new JPanel(gridLayout);
        assistantAndMoneyContainerSx.setBackground(Color.CYAN);
        assistantAndMoneyContainerDx =new JPanel(gridLayout);
        assistantAndMoneyContainerDx.setBackground(Color.CYAN);
        assistantAndMoneyContainers.add(assistantAndMoneyContainerSx);
        assistantAndMoneyContainers.add(assistantAndMoneyContainerDx);

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

        isles = new ArrayList<>();
        this.students = students;
        this.towers = towers;

        cl = this.getClass().getClassLoader();
        InputStream url;

        for (int i = 1; i < 4; i++) {
            BufferedImage img = null;
            url = cl.getResourceAsStream("GameTable/Isles/island"+i+".png");
            try {
                assert url != null;
                img = ImageIO.read(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            isles.add(img);
        }

        int idxSx=0;
        int idxDx=0;
        for(int i=0;i<storage.getNumberOfPlayers();i++){
            String username=""+storage.getDashboard(i).getNickname();

            JPanel usernamePanel=new JPanel();
            usernamePanel.setLayout(new BorderLayout());
            usernamePanel.setOpaque(false);

            JLabel usernameLabel=new JLabel(username);
            if(storage.getDashboard(i).getNickname().equals(this.usernamePlaying))
                usernameLabel.setForeground(nicknameColor);
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
                AssistantCardPanel magePanel=new AssistantCardPanel(i,true);
                ((JPanel) assistantAndMoneyContainerSx.getComponent(idxSx)).add(magePanel,assistantAndMoneyConstraints);
                assistantCardPanels.add(magePanel);
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
                AssistantCardPanel magePanel=new AssistantCardPanel(i,true);
                ((JPanel) assistantAndMoneyContainerDx.getComponent(idxDx)).add(magePanel,assistantAndMoneyConstraints);
                assistantCardPanels.add(magePanel);
                assistantAndMoneyConstraints.gridy=0;
                idxDx++;
            }

        }

        //if the gamemode is expert we need to show also the coins
        if(storage.isGameMode()) {
            idxSx=0;
            idxDx=0;
            this.coinsLabel=new ArrayList<>();
            if(storage.getNumberOfPlayers()==4){
                assistantAndMoneyConstraints.gridy=3;
            }else{
                assistantAndMoneyConstraints.gridy=2;
            }
            assistantAndMoneyConstraints.weighty=0.1;
            for (int i = 0; i < storage.getNumberOfPlayers(); i++) {
                coinsLabel.add(new JLabel("Coins:"+storage.getDashboard(i).getMoney()));
                if(i%2==0){
                    ((JPanel) assistantAndMoneyContainerSx.getComponent(idxSx)).add(coinsLabel.get(i),assistantAndMoneyConstraints);
                    idxSx++;
                }else{
                    ((JPanel) assistantAndMoneyContainerDx.getComponent(idxDx)).add(coinsLabel.get(i),assistantAndMoneyConstraints);
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
            JLayeredPane isleLayeredPane=new JLayeredPane();
            JPanel clickablePanel=new JPanel();
            IslePanel islePanel=new IslePanel(storage,i, isles, students, towers);

            clickablePanel.setOpaque(false);
            clickablePanel.setBackground(Color.YELLOW);
            clickablePanel.setBounds(0,0,200,200);
            islePanel.setBounds(0,0,200,200);

            isleLayeredPane.add(clickablePanel,Integer.valueOf(1));
            isleLayeredPane.add(islePanel,Integer.valueOf(0));

            islesPanels.add(islePanel);
            clickablePanels.add(clickablePanel);
            isleLayeredPanels.add(isleLayeredPane);
        }

        c.weighty=0.5;
        c.weightx=0.4;
        c.gridx=1;
        add(isleContainerSx,c);
        icSxConstraints.gridx=0;
        icSxConstraints.gridy=0;
        icSxConstraints.weightx=1;
        icSxConstraints.weighty=1;
        icSxConstraints.fill=GridBagConstraints.BOTH;
        isleContainerSx.add(isleLayeredPanels.get(0),icSxConstraints);

        icSxConstraints.gridy++;
        isleContainerSx.add(isleLayeredPanels.get(11),icSxConstraints);

        icSxConstraints.gridy++;
        isleContainerSx.add(isleLayeredPanels.get(10),icSxConstraints);

        icSxConstraints.gridy++;
        isleContainerSx.add(isleLayeredPanels.get(9),icSxConstraints);


        c.gridx=3;
        c.weighty=0.5;
        c.weightx=0.4;
        add(isleContainerDx,c);
        icDxConstraints.gridx=0;
        icDxConstraints.gridy=0;
        icDxConstraints.weighty=1;
        icDxConstraints.weightx=1;
        icDxConstraints.fill=GridBagConstraints.BOTH;
        isleContainerDx.add(isleLayeredPanels.get(3),icDxConstraints);

        icDxConstraints.gridy++;
        isleContainerDx.add(isleLayeredPanels.get(4),icDxConstraints);

        icDxConstraints.gridy++;
        isleContainerDx.add(isleLayeredPanels.get(5),icDxConstraints);

        icDxConstraints.gridy++;
        isleContainerDx.add(isleLayeredPanels.get(6),icDxConstraints);


        c.gridx=2;
        c.weighty=1;
        c.weightx=0.5;
        add(isleContainerCenter,c);
            firstIsleContainer1x2=new JPanel(new GridBagLayout());
            firstIsleContainer1x2.setBackground(Color.CYAN);
            first1x2constraints=new GridBagConstraints();
            secondIsleContainer1x2=new JPanel(new GridBagLayout());
            secondIsleContainer1x2.setBackground(Color.CYAN);
            second1x2constraints=new GridBagConstraints();

            first1x2constraints.gridy=0;
            first1x2constraints.gridx=0;
            first1x2constraints.weightx=1;
            first1x2constraints.weighty=1;
            first1x2constraints.fill=GridBagConstraints.BOTH;
            firstIsleContainer1x2.add(isleLayeredPanels.get(1),first1x2constraints);

            first1x2constraints.gridx++;
            firstIsleContainer1x2.add(isleLayeredPanels.get(2),first1x2constraints);


            second1x2constraints.gridy=0;
            second1x2constraints.gridx=0;
            second1x2constraints.weightx=1;
            second1x2constraints.weighty=1;
            second1x2constraints.fill=GridBagConstraints.BOTH;
            secondIsleContainer1x2.add(isleLayeredPanels.get(8),second1x2constraints);

            second1x2constraints.gridx++;
            secondIsleContainer1x2.add(isleLayeredPanels.get(7),second1x2constraints);


            centerConstraints.fill=GridBagConstraints.BOTH;
            centerConstraints.gridx=0;
            centerConstraints.gridy=0;
            centerConstraints.weightx=0.5;
            centerConstraints.weighty=0.95;
            isleContainerCenter.add(firstIsleContainer1x2,centerConstraints);

            centerConstraints.fill=GridBagConstraints.BOTH;
            centerConstraints.gridy=1;
            centerConstraints.weighty=1;
            centerConstraints.weightx=1;
            cloudsContainerPanel=new CloudsContainerPanel(storage,viewObservers,this, students);
            cloudsContainerPanel.setBackground(Color.CYAN);
            isleContainerCenter.add(cloudsContainerPanel,centerConstraints);

            if(storage.isGameMode()) {
                charactersContainer=new JPanel(new GridLayout(1,3));
                charactersContainer.setBackground(Color.CYAN);
                centerConstraints.fill = GridBagConstraints.BOTH;
                centerConstraints.gridy = 2;
                centerConstraints.weightx = 1;
                centerConstraints.weighty = 1;
                for (int i = 0; i < 3; i++)
                    characterPanels.add(new CharacterPanel(storage.getGameTable().getCharacterCard(i).getCharacterCardName(), i));
                charactersContainer.add(characterPanels.get(0));
                charactersContainer.add(characterPanels.get(1));
                charactersContainer.add(characterPanels.get(2));
                isleContainerCenter.add(charactersContainer, centerConstraints);
            }

            centerConstraints.fill=GridBagConstraints.BOTH;
            centerConstraints.gridy++;
            centerConstraints.weightx=0.5;
            centerConstraints.weighty=0.95;
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
        //remove assistant/mage panel from the container in the screen and from the data structure
        assistantAndMoneyPanelList.get(playerID).remove(assistantCardPanels.get(playerID));
        assistantCardPanels.remove(assistantCardPanels.get(playerID));
        //create a new AssistantCardPanel according to the storage info and add it to the container and to the data structure
        AssistantCardPanel assistantCardPanel=new AssistantCardPanel(storage.getDashboard(playerID).getDiscardPileTurnOrder(),false);
        assistantAndMoneyPanelList.get(playerID).add(assistantCardPanel, assistantAndMoneyConstraints);
        assistantCardPanels.add(playerID,assistantCardPanel);

        assistantAndMoneyPanelList.get(playerID).validate();//if you change a component that's already been displayed you need to validate it to display the changes
        assistantAndMoneyPanelList.get(playerID).repaint();
    }

    public void updateCloud(int cloudID) {
        cloudsContainerPanel.updateCloudPanels(cloudID);
    }

    public void updateFillClouds(){
        for(int i=0;i<storage.getGameTable().getClouds().size();i++){
            cloudsContainerPanel.updateCloudPanels(i);
        }
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

        assistantAndMoneyPanelList.get(playerID).remove(coinsLabel.get(playerID));
        coinsLabel.get(playerID).setText("Coins:"+storage.getDashboard(playerID).getMoney());
        assistantAndMoneyPanelList.get(playerID).add(coinsLabel.get(playerID),assistantAndMoneyConstraints);
        assistantAndMoneyPanelList.get(playerID).validate();
        assistantAndMoneyPanelList.get(playerID).repaint();
    }

    public void updateIsle(int isleID){
        islesPanels.get(isleID).resetIsle();
        islesPanels.get(isleID).initializeIsle();
    }

    public void updateIsleLayout(){
        int lastIsleID=isleLayeredPanels.size()-1;
        JLayeredPane lastIsle=isleLayeredPanels.get(lastIsleID);
        JPanel emptyPanel=new JPanel();
        emptyPanel.setOpaque(false);
        emptyPanel.setBackground(Color.CYAN);

        if(lastIsleID==0 || (lastIsleID>=9 && lastIsleID<=11)){
            isleContainerSx.remove(lastIsle);
            icSxConstraints.gridx=0;
            if(lastIsleID==11) icSxConstraints.gridy=1;
            if(lastIsleID==10) icSxConstraints.gridy=2;
            if(lastIsleID==9) icSxConstraints.gridy=3;

            isleContainerSx.add(emptyPanel,icSxConstraints);
        }
        if(lastIsleID==1 || lastIsleID==2){
            firstIsleContainer1x2.remove(lastIsle);
            first1x2constraints.gridy=0;
            first1x2constraints.gridx=lastIsleID-1;
            firstIsleContainer1x2.add(emptyPanel,first1x2constraints);
        }
        if(lastIsleID>=3 && lastIsleID<=6){
            isleContainerDx.remove(lastIsle);
            icDxConstraints.gridx=0;
            icDxConstraints.gridy=(lastIsleID+1)%4;
            isleContainerDx.add(emptyPanel,icDxConstraints);
        }
        if(lastIsleID==7 || lastIsleID==8){
            secondIsleContainer1x2.remove(lastIsle);
            second1x2constraints.gridy=0;
            second1x2constraints.gridx=lastIsleID%2;
            secondIsleContainer1x2.add(emptyPanel,second1x2constraints);
        }
        isleLayeredPanels.remove(lastIsle);
        islesPanels.remove(islesPanels.get(lastIsleID));
        clickablePanels.remove(clickablePanels.get(lastIsleID));
    }

    public void setIslesClickable(ArrayList<ViewObserver> viewObserverList, EntrancePanel entrance, DiningPanel dining){
        for (int i=0;i<islesPanels.size();i++) {
            clickablePanels.get(i).addMouseListener(new IsleListener(this, viewObserverList, entrance, i, dining));
        }
    }

    public void setMNClickable(ArrayList<ViewObserver> viewObserverList){
        for (int i=0;i<islesPanels.size();i++) {
            clickablePanels.get(i).addMouseListener(new MNListener(viewObserverList,i,this));
        }
    }

    public void setCloudsClickable(){
        cloudsContainerPanel.setCloudsClickable();
    }

    public void removeCloudsClickable() {
        cloudsContainerPanel.removeCloudsClickable();
    }

    public void removeIslesClickable(){
        for (JPanel clickablePanel : clickablePanels) {
            for (int j = 0; j < clickablePanel.getMouseListeners().length; j++) {
                clickablePanel.removeMouseListener(clickablePanel.getMouseListeners()[j]);
            }
        }
    }

    public void setClickableCharacters(ArrayList<ViewObserver> viewObserverList, int playerID) {
        for(CharacterPanel characterPanel : characterPanels){
            characterPanel.addMouseListener(new CharacterCardListener(viewObserverList,characterPanel.getCharacterIndex(), playerID, this , gsp));
        }
    }

    public void removeCharactersClickable() {
        for(CharacterPanel characterPanel : characterPanels){
            for (int j = 0; j < characterPanel.getMouseListeners().length; j++) {
                characterPanel.removeMouseListener(characterPanel.getMouseListeners()[j]);
            }
        }
    }



}

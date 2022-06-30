package it.polimi.ingsw.View.GUI.IslesPanels;

import it.polimi.ingsw.Observer.ViewObserver;
import it.polimi.ingsw.View.GUI.AssistantCardPanel;
import it.polimi.ingsw.View.GUI.ExpertPanels.CharacterPanel;
import it.polimi.ingsw.View.GUI.CloudsPanels.CloudsContainerPanel;
import it.polimi.ingsw.View.GUI.DashboardPanels.DashboardPanel;
import it.polimi.ingsw.View.GUI.DashboardPanels.DiningPanel;
import it.polimi.ingsw.View.GUI.DashboardPanels.EntrancePanel;
import it.polimi.ingsw.View.GUI.EventListeners.CharacterCardListener;
import it.polimi.ingsw.View.GUI.EventListeners.EffectListener;
import it.polimi.ingsw.View.GUI.EventListeners.IsleListener;
import it.polimi.ingsw.View.GUI.EventListeners.MNListener;
import it.polimi.ingsw.View.GUI.GameScreenPanel;
import it.polimi.ingsw.View.GUI.GeneralReservePanel;
import it.polimi.ingsw.View.GUI.ImagesLoader;
import it.polimi.ingsw.View.StorageOfModelInformation.ModelStorage;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * the panel use to contain the isles, the clouds and the character cards
 */
public class TableCenterPanel extends JPanel {
    /**
     * ModelStorage reference used to retrieve game state information from
     */
    private final ModelStorage storage;
    /**
     * The panel containing the leftmost three isles;
     */
    private final JPanel isleContainerSx;
    /**
     * The panel container the rightmost three isles
     */
    private final JPanel isleContainerDx;
    /**
     * One of the two isle panel container in the isleContainerCenter
     */
    private final JPanel firstIsleContainer1x2;
    /**
     * One of the two isle panel container in the isleContainerCenter
     */
    private final JPanel secondIsleContainer1x2;
    /**
     * The panel containing the clouds panels
     */
    private final CloudsContainerPanel cloudsContainerPanel;
    /**
     * Constraints used to add the assistant card and the labels for money and username (and squad) in the assistantAndMoneyPanels
     */
    private final GridBagConstraints assistantAndMoneyConstraints;
    /**
     * This array contains all the panels representing the isles
     */
    private final ArrayList<IslePanel> islesPanels;
    /**
     * This array list stores all the panel representing the assistant card, the money, the username and the squad for each player
     */
    private final ArrayList<JPanel> assistantAndMoneyPanelList;
    /**
     * ArrayList to store the labels for showing the available coins for each player
     */
    private ArrayList<JLabel> coinsLabel;
    /**
     * Arraylist to store the assistant card panels currently on screen
     */
    private final ArrayList<AssistantCardPanel> assistantCardPanels;
    /**
     * Constraints for the rightmost isle container
     */
    private final GridBagConstraints icDxConstraints;
    /**
     * Constraints for the leftmost isle container
     */
    private final GridBagConstraints icSxConstraints;
    /**
     * Constraints for the first 1x2 isle container (the one on top)
     */
    private final GridBagConstraints first1x2constraints;
    /**
     * Constraints for the second 1x2 isle container (the one on bottom)
     */
    private final GridBagConstraints second1x2constraints;
    /**
     * Array list of panel added on top of isle. The listeners will be added to this array list element
     * in order to make the isles clickable on every point of their panel even if they are full of students,towers etc.
     */
    private final ArrayList<JPanel> clickablePanels;
    /**
     * Array list to store the layered panel added to the isle container. Layered panel are used to permit to add on top of the isle panel
     * a clickable panel
     */
    private final ArrayList<JLayeredPane> isleLayeredPanels;
    /**
     * Array list to store the character cards panel
     */
    private final ArrayList<CharacterPanel> characterPanels;
    /**
     * GameScreenPanel reference
     */
    private final GameScreenPanel gsp;
    /**
     * Panel representing the general money reserve
     */
    private final GeneralReservePanel generalReserve;

    /**
     * Constructor of TableCenterPanel
     * @param frameWidth frame width
     * @param frameHeight frame height
     * @param storage ModelStorage reference
     * @param usernamePlaying username of the player using the GUI
     * @param nicknameColor color of usernamePlaying
     * @param viewObservers array list of viewObservers attached to the view components
     * @param students array list of students images
     * @param towers array list of towers images
     * @param checkedStudents array list of checked students
     * @param gsp gameScreenPanel reference
     */
    public TableCenterPanel(int frameWidth,int frameHeight,ModelStorage storage, String usernamePlaying, Color nicknameColor,
                            ArrayList<ViewObserver> viewObservers, ArrayList<BufferedImage> students, ArrayList<BufferedImage> towers, ArrayList<BufferedImage> checkedStudents,
                            GameScreenPanel gsp) {
        this.storage=storage;

        //username of the player using the GUI
        this.islesPanels=new ArrayList<>();
        this.assistantAndMoneyPanelList=new ArrayList<>();
        this.assistantCardPanels=new ArrayList<>();
        this.characterPanels = new ArrayList<>();
        this.gsp = gsp;
        this.clickablePanels=new ArrayList<>();
        this.isleLayeredPanels=new ArrayList<>();
        this.generalReserve=new GeneralReservePanel(storage);

        setBackground(Color.CYAN);
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createLineBorder(Color.black));

        GridBagConstraints c=new GridBagConstraints();
        GridBagConstraints centerConstraints=new GridBagConstraints();

        //I create five columns, the first and the fifth one will contain the discard pile of each player,
        //the second and the fourth one will contain two 4x1 grids containing four islesImages
        //the third one will contain another 3x1 panel with two 1x2 grids on the top and on the bottom, while in the
        //center cell there are the clouds and the character cards
        isleContainerDx=new JPanel(new GridBagLayout());
        isleContainerDx.setBackground(Color.CYAN);
        icDxConstraints=new GridBagConstraints();

        //The center panel containing two rows of islesImages and a center row containing the clouds and the character cards
        JPanel isleContainerCenter = new JPanel(new GridBagLayout());
        isleContainerCenter.setBackground(Color.CYAN);
        isleContainerSx=new JPanel(new GridBagLayout());
        icSxConstraints=new GridBagConstraints();
        isleContainerSx.setBackground(Color.CYAN);

        GridLayout gridLayout=new GridLayout(2,1);//layout for the assistant card and money containers
        gridLayout.setVgap(220);

        // The panel containing the discard pile (or the mage) and the coins for the first and third player (displayed on the left)
        JPanel assistantAndMoneyContainerSx = new JPanel(gridLayout);
        assistantAndMoneyContainerSx.setBackground(Color.CYAN);

        //The panel containing the discard pile (or the mage) and the coins for the second and the fourth player(displayed on the right)
        JPanel assistantAndMoneyContainerDx = new JPanel(gridLayout);
        assistantAndMoneyContainerDx.setBackground(Color.CYAN);

        //Panel containing the discard pile, the coins and the username (and squad) of the first player
        JPanel assistantAndMoneyPanel1 = new JPanel(new GridBagLayout());
        assistantAndMoneyPanel1.setBackground(Color.CYAN);
        assistantAndMoneyPanelList.add(assistantAndMoneyPanel1);


        //Panel containing the discard pile, the coins and the username (and squad) of the second player
        JPanel assistantAndMoneyPanel2 = new JPanel(new GridBagLayout());
        assistantAndMoneyPanel2.setBackground(Color.CYAN);
        assistantAndMoneyPanelList.add(assistantAndMoneyPanel2);

        //panel containing the discard pile, the coins and the username (and squad) of the third player
        JPanel assistantAndMoneyPanel3 = new JPanel(new GridBagLayout());
        assistantAndMoneyPanel3.setBackground(Color.CYAN);
        assistantAndMoneyPanelList.add(assistantAndMoneyPanel3);

        //panel containing the discard pile, the coins and the username (and squad) of the fourth player
        JPanel assistantAndMoneyPanel4 = new JPanel(new GridBagLayout());
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

        //array list to store isle images
        ArrayList<BufferedImage> islesImages = new ArrayList<>();

        for (int i = 1; i < 4; i++) {
            BufferedImage img= ImagesLoader.isleImageLoader(this.getClass().getClassLoader(),i);
            islesImages.add(img);
        }

        int idxSx=0;
        int idxDx=0;
        for(int i=0;i<storage.getNumberOfPlayers();i++){
            String username=""+storage.getDashboard(i).getNickname();

            JPanel usernamePanel=new JPanel();
            usernamePanel.setLayout(new BorderLayout());
            usernamePanel.setOpaque(false);

            JLabel usernameLabel=new JLabel(username);
            if(storage.getDashboard(i).getNickname().equals(usernamePlaying))
                usernameLabel.setForeground(nicknameColor);
            usernamePanel.add(usernameLabel,BorderLayout.CENTER);

            if(storage.getDashboard(i).getNickname().equals(usernamePlaying)){
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
        if(storage.isExpertMode()) {
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

        //initialize the islesImages
        for(int i=0;i<12;i++){
            JLayeredPane isleLayeredPane=new JLayeredPane();
            JPanel clickablePanel=new JPanel();
            IslePanel islePanel=new IslePanel(storage,i, islesImages, students, towers);

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
            centerConstraints.weighty=0.6;
            isleContainerCenter.add(firstIsleContainer1x2,centerConstraints);

            JPanel centerContainer=new JPanel(new GridBagLayout());
            centerContainer.setBackground(Color.CYAN);
            GridBagConstraints constraints=new GridBagConstraints();

            cloudsContainerPanel=new CloudsContainerPanel(storage,viewObservers,this, students);
            cloudsContainerPanel.setBackground(Color.CYAN);
            constraints.fill=GridBagConstraints.BOTH;
            constraints.gridx=0;
            constraints.gridy=0;
            constraints.weightx=1;
            constraints.weighty=0.8;
            centerContainer.add(cloudsContainerPanel,constraints);

            if(storage.isExpertMode()) {
                constraints.gridy++;
                constraints.weighty=0.3;
                constraints.weightx=0;
                constraints.fill=GridBagConstraints.VERTICAL;
                centerContainer.add(generalReserve,constraints);
                //panel that contains the character cards
                constraints.gridy++;
                constraints.fill=GridBagConstraints.BOTH;
                constraints.weightx=1;
                constraints.weighty=1;
                JPanel charactersContainer = new JPanel(new GridLayout(1, 3));
                charactersContainer.setBackground(Color.CYAN);
                for (int i = 0; i < 3; i++)
                    characterPanels.add(new CharacterPanel(storage,storage.getGameTable().getCharacterCard(i).getCharacterCardName(),
                            i,students,checkedStudents, this, viewObservers));
                charactersContainer.add(characterPanels.get(0));
                charactersContainer.add(characterPanels.get(1));
                charactersContainer.add(characterPanels.get(2));
                centerContainer.add(charactersContainer,constraints);
            }else{
                constraints.gridy++;
                constraints.fill=GridBagConstraints.BOTH;
                constraints.weightx=1;
                constraints.weighty=1;
                JPanel emptyPanel=new JPanel();
                emptyPanel.setOpaque(false);
                emptyPanel.setBackground(Color.CYAN);
                centerContainer.add(emptyPanel,constraints);
            }
            centerConstraints.fill=GridBagConstraints.BOTH;
            centerConstraints.gridy=1;
            centerConstraints.weighty=1;
            centerConstraints.weightx=1;
            isleContainerCenter.add(centerContainer,centerConstraints);

            centerConstraints.fill=GridBagConstraints.BOTH;
            centerConstraints.gridy++;
            centerConstraints.weightx=0.5;
            centerConstraints.weighty=0.55;
            isleContainerCenter.add(secondIsleContainer1x2,centerConstraints);

        firstIsleContainer1x2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        secondIsleContainer1x2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        cloudsContainerPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        //set sizes of islesImages and clickablepanels
        int x=0;
        int y=0;
        int width= (int) Math.round(frameWidth/(7.5));
        int height= frameHeight/4;
        for(int i=0;i<islesPanels.size();i++){
            islesPanels.get(i).setBounds(x,y,width,height);
            clickablePanels.get(i).setBounds(x,y,width,height);
            islesPanels.get(i).repaint();
            clickablePanels.get(i).repaint();
        }
    }

    public void updateGeneralReserve(){
        generalReserve.initializeGeneralReserve();
    }

    /**
     * Updates the player discard pile with the last assistant card played by that player
     * @param playerID the player id used to identify which player discard pile update
     */
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

        assistantAndMoneyPanelList.get(playerID).validate();
        assistantAndMoneyPanelList.get(playerID).repaint();
    }

    /**
     * Updates cloud panel on player choice
     * @param cloudID the id of the cloud to update
     */
    public void updateCloud(int cloudID) {
        cloudsContainerPanel.updateCloudPanels(cloudID);
    }

    /**
     * Update cloud panels on their filling
     */
    public void updateFillClouds(){
        for(int i=0;i<storage.getGameTable().getClouds().size();i++){
            cloudsContainerPanel.updateCloudPanels(i);
        }
    }

    /**
     * Updates the player available coins
     * @param playerID the player id used to identify which coin reserve update
     */
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

    /**
     * Update isle panel
     * @param isleID isle id used to identify which isle update
     */
    public void updateIsle(int isleID){
        islesPanels.get(isleID).resetIsle();
        islesPanels.get(isleID).initializeIsle();
    }

    /**
     * Update isle panels once one or more isle are unified. It eliminates the last isle (starting from the one below the top-left angle)
     *  and updates the remaining
     */
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

    /**
     * Adds mouse listener to the isle panels in order to register user click on them. This allows the player to move students on them.
     * @param viewObserverList view observers list passed to the listeners
     * @param entrance EntrancePanel reference passed to the listeners
     * @param dining DiningPanel reference passed to the listeners
     */
    public void setIslesClickable(ArrayList<ViewObserver> viewObserverList, EntrancePanel entrance, DiningPanel dining){
        for (int i=0;i<islesPanels.size();i++) {
            clickablePanels.get(i).addMouseListener(new IsleListener(this, viewObserverList, entrance, i, dining));
        }
    }
    /**
     *Adds mouse listener to the isle panels in order to register user click on them. This allows the player to move MN on one of them.
     * @param viewObserverList the viewObserver list passed to the listeners
     */
    public void setMNClickable(ArrayList<ViewObserver> viewObserverList){
        for (int i=0;i<islesPanels.size();i++) {
            clickablePanels.get(i).addMouseListener(new MNListener(viewObserverList,i,this));
        }
    }

    /**
     * Adds a MouseListener to every isle in order to send an ACTIVATE_ATOMIC_EFFECT message to the server when clicked.
     * @param viewObserverList the list of observers that have to send the message to the server
     */
    public void setIslesClickableForEffect(ArrayList<ViewObserver> viewObserverList){
        for (int i=0;i<islesPanels.size();i++) {
            clickablePanels.get(i).addMouseListener(new EffectListener(viewObserverList,i,this));
        }

    }

    /**
     * Adds mouse listeners to the clouds in order to let the player pick students from one of them
     */
    public void setCloudsClickable(){
        cloudsContainerPanel.setCloudsClickable();
    }

    /**
     * Removes mouse listeners from the clouds
     */
    public void removeClickableClouds() {
        cloudsContainerPanel.removeCloudsClickable();
    }

    /**
     * Removes mouse listeners from the isles
     */
    public void removeClickableIsles(){
        for (JPanel clickablePanel : clickablePanels) {
            for (int j = 0; j < clickablePanel.getMouseListeners().length; j++) {
                clickablePanel.removeMouseListener(clickablePanel.getMouseListeners()[j]);
            }
        }
    }

    /**
     * Adds a MouseListener to every Character Card in order to send an PLAY_CHARACTER_CARD message to the server when clicked.
     * @param viewObserverList the list of observers that have to send the message to the server
     * @param playerID the ID of the player that clicked on the card
     */
    public void setCharactersClickable(ArrayList<ViewObserver> viewObserverList, int playerID) {
        for(CharacterPanel characterPanel : characterPanels){
            characterPanel.addMouseListener(new CharacterCardListener(viewObserverList,characterPanel.getCharacterIndex(), playerID, this , gsp));
        }
    }

    /**
     * Removes the MouseListeners of the Character Cards.
     */
    public void removeClickableCharacters() {
        for(CharacterPanel characterPanel : characterPanels){
            for (int j = 0; j < characterPanel.getMouseListeners().length; j++) {
                characterPanel.removeMouseListener(characterPanel.getMouseListeners()[j]);
            }
        }
    }

    public ArrayList<CharacterPanel> getCharacterPanels() {
        return characterPanels;
    }

    /**
     * Sets the students on the chosen Character Card clickable.
     * @param character the panel of the chosen Character Card
     * @param dashboard the dashboard associated with the one who played the Character Card
     */
    public void setCharacterStudentsClickable(CharacterPanel character, DashboardPanel dashboard) {
        character.setStudentsClickable(dashboard);
    }

    /**
     * Removes the MouseListeners of the students on the Character Cards.
     */
    public void removeClickableCharacterStudents() {
        for(CharacterPanel characterPanel : characterPanels){
            characterPanel.removeStudentsClickable();
        }
    }

}

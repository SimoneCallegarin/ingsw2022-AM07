package it.polimi.ingsw.View.GUI;

import it.polimi.ingsw.Model.CharacterCards.CharacterCardsName;
import it.polimi.ingsw.Observer.Subjects.ViewSubject;
import it.polimi.ingsw.View.GUI.Buttons.AssistantCardButton;
import it.polimi.ingsw.View.StorageOfModelInformation.ModelChanges;
import it.polimi.ingsw.View.StorageOfModelInformation.ModelStorage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicInteger;


public class GUIDrawer extends ViewSubject {

    ModelStorage modelStorage;

    private final String frameTitle="Eriantys Game";

    /**
     * the frame containing all the GUI
     */
    JFrame f=new JFrame(frameTitle);


    /**
     * the panel for the user inputs
     */
    JPanel userInputPanel =new JPanel(new CardLayout());
    /**
     * user preferences layout to change between username insertion and game preferences choice
     */
    CardLayout userCl=(CardLayout) userInputPanel.getLayout();
    /**
     * the general panel manager switches between the screen where the user will submit his server and game preferences
     * and the screen where the actual game will take place
     */
    JPanel generalPanelManager=new JPanel(new CardLayout());
    /**
     * the generalPanelManager layout to show different panels on events happening
     */
    CardLayout cl=(CardLayout)generalPanelManager.getLayout();
    /**
     * Text field for username input
     */
    JTextField usernameInputText =new JTextField();
    /**
     * Text field for gamemode input
     */
    JTextField gameModeInputText =new JTextField();
    /**
     * Text field for number of players input
     */
    JTextField numberPlayersInput=new JTextField();
    /**
     * on this button press the player will join a lobby and wait for the game to start
     */
    JButton startGame=new JButton("Start game");
    /**
     * panel where the actual game will take place
     */
    GameScreenPanel gameScreenPanel;
    /**
     * the username of the player currently using the GUI
     */
    String usernamePlaying;
    /**
     * boolean variable used to execute only one time the GameScreenPanel creation
     */
    boolean gameStart=true;
    /**
     * the class containing all the information about the changes occurred in the ModelStorage
     */
    ModelChanges modelChanges;

    private boolean turnStarted = false;

    /**
     * this method initialize the first screen when you open the app where you'll be asked the username, the game mode and the
     * number of Players. It uses a frame whose content pane is a General Panel Manager which will switch between the Initial Background panel
     * where the user will input his game preferences, and the proper game screen where the actual game will take place.
     * The InitialBackground class contains a userInputPanel which will switch between the username form and the game preferences form.
     */
    public void screeInitialization(){
        //screen frame initialization
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setExtendedState(JFrame.MAXIMIZED_BOTH);
        f.setVisible(true);
        final int screenDimensionX = f.getWidth();
        final int screenDimensionY = f.getHeight();
        //set my initial content pane where I add my user input manager
        InitialBackgroundPanel initialBackgroundPanel =new InitialBackgroundPanel(new BorderLayout(),screenDimensionX,screenDimensionY
        );
        //add it to the general manager
        generalPanelManager.add(initialBackgroundPanel,"User Input");

        initialBackgroundPanel.add(userInputPanel,BorderLayout.CENTER);

        //add my general panel manager as my content pane to switch between user input screen and the actual game
        f.setContentPane(generalPanelManager);

        //setting correct dimensions for text fields
        usernameInputText.setMaximumSize(new Dimension(700,25));
        gameModeInputText.setMaximumSize(new Dimension(700,25));
        numberPlayersInput.setMaximumSize(new Dimension(700,25));
        //ask the username
        showUsernameForm();
    }

    /**
     * this method shows on screen the input form for the username. It initializes the usernameForm panel and adds it to the
     * user input panel. The usernameForm panel contains a submit button which checks if the input is empty (in this case it shows
     * an error message and makes the user reinsert the input). If the input is acceptable it notifies the clientController who forward the
     * information to the server.
     */
    public void showUsernameForm(){
        //switch in the initial background panel
        cl.show(generalPanelManager,"User Input");
        //initialize the username form
        JPanel usernameForm=new JPanel();
        usernameForm.setLayout(new BoxLayout(usernameForm,BoxLayout.PAGE_AXIS));
        usernameForm.add(new JLabel("Insert username"));
        usernameForm.add(usernameInputText);
        JButton submitUsername=new JButton("Submit");
        submitUsername.addActionListener(e -> {
           if(usernameInputText.getText().equals("")){
               JOptionPane.showMessageDialog(userInputPanel,"Invalid input","Error",JOptionPane.ERROR_MESSAGE);
               return;
           }
           usernamePlaying=usernameInputText.getText();
           notifyObserver(obs->obs.onUsername(usernameInputText.getText()));
        });
        usernameForm.add(submitUsername);
        userInputPanel.add(usernameForm,"Username form");
    }

    /**
     * this method shows on screen the input form for the game mode and the number of players. It initializes the gamePreferences panel
     * and adds it to the user input panel. The gamePreferences panel contains a start game button which checks if the inputs are acceptable
     * and, if that's the case, notifies the ClientController. Once the ClientController is notified this method calls showLoadingScreen.
     */
    public void showGamePreferencesForm(){
        JPanel gamePreferences=new JPanel();
        gamePreferences.setLayout(new BoxLayout(gamePreferences,BoxLayout.PAGE_AXIS));
        userInputPanel.add(gamePreferences,"Game preferences form");
        gamePreferences.add(new JLabel("Input the number of players[2,3,4]"));
        gamePreferences.add(numberPlayersInput);
        gamePreferences.add(new JLabel("Input the gamemode[Base or Expert]"));
        gamePreferences.add(gameModeInputText);
        startGame.addActionListener(e -> {
            //add try catch
            int numPlayers=Integer.parseInt(numberPlayersInput.getText());
            if (numPlayers!=2 && numPlayers!=3 && numPlayers!=4) {
                JOptionPane.showMessageDialog(userInputPanel, "Invalid number of players input", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!gameModeInputText.getText().equals("Expert") && !gameModeInputText.getText().equals("Base")) {
                JOptionPane.showMessageDialog(userInputPanel, "Invalid gamemode input", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            notifyObserver(obs -> obs.onGamePreferences(Integer.parseInt(numberPlayersInput.getText()), gameModeInputText.getText().equals("Expert")));
            showLoadingScreen();
        });
        gamePreferences.add(startGame);
        //make it show
        userCl.show(userInputPanel,"Game preferences form");

    }

    /**
     * this method shows a loading screen while waiting for other players to join and loading the game
     */
    public void showLoadingScreen(){

        //need to update the loading screen graphics
        generalPanelManager.add(new LoadingScreen(),"Loading Screen");
        cl.show(generalPanelManager,"Loading Screen");
    }

    public void createGameScreen(){
        //initialize the game screen and add it to the generalPanelManager
        gameScreenPanel=new GameScreenPanel(new GridBagLayout(),modelStorage,f.getWidth(),f.getHeight(),usernamePlaying,getViewObserverList());
        generalPanelManager.add(gameScreenPanel,"Game Screen");
    }

    /**
     * this method uses the general panel manager card layout to switch from the InitialBackground panel to the GameScreen panel
     */
    public void showGameScreen(){
        //switch to the actual game screen
        cl.show(generalPanelManager,"Game Screen");
    }

    /**
     * this method opens a JOptionPane to select the assistant card. Each assistant card displayed is a button which on press updates
     * the relative player discard pile
     * @param playerID the playerID used to decide which character card display and which discard pile update
     */
    public int ShowAssistantCardForm(int playerID) {
        AtomicInteger turnOrder = new AtomicInteger();
        turnOrder.set(0);
        JPanel buttonContainer = new JPanel();
        JButton[] buttons = new JButton[modelStorage.getDashboard(playerID).getAssistantCardsMNMovement().size()];
        AtomicInteger lastPressedButton = new AtomicInteger();
        for (int i = 0; i < modelStorage.getDashboard(playerID).getAssistantCardsMNMovement().size(); i++) {
            buttons[i] = (new AssistantCardButton(modelStorage.getDashboard(playerID).getAssistantCardsTurnOrder().get(i)));
            //idk what the hell is going on with these variables
            int finalI = modelStorage.getDashboard(playerID).getAssistantCardsTurnOrder().get(i);
            int finalI1 = i;
            buttons[i].addActionListener(e -> {
                ClassLoader cl1=this.getClass().getClassLoader();
                InputStream url1=cl1.getResourceAsStream("GameTable/Assistant_cards/2x/" + finalI + "chk.png");
                BufferedImage img1= null;
                try {
                    if (url1 != null)
                        img1 = ImageIO.read(url1);
                }catch (IOException ex) {
                    ex.printStackTrace();
                }
                buttons[finalI1].setIcon(new ImageIcon(img1));

                if (turnOrder.get() != 0 && turnOrder.get() != finalI) {
                    ClassLoader cl2=this.getClass().getClassLoader();
                    InputStream url2=cl2.getResourceAsStream("GameTable/Assistant_cards/2x/" + turnOrder.get() + ".png");
                    BufferedImage img2= null;
                    try {
                        if (url2 != null)
                            img2 = ImageIO.read(url2);
                    }catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    buttons[lastPressedButton.get()].setIcon(new ImageIcon(img2));
                }
                //gameScreenPanel.tableCenterPanel.updateAllAssistCard();
                lastPressedButton.set(finalI1);
                turnOrder.set(finalI);

            });
            buttonContainer.add(buttons[i]);
        }
        JScrollPane scrollPane = new JScrollPane(buttonContainer);
        scrollPane.setPreferredSize(new Dimension(600, 400));
        JOptionPane.showMessageDialog(f, scrollPane, "Your deck", JOptionPane.PLAIN_MESSAGE);
        return turnOrder.get();
    }

    /**
     * this method create a JOptionPane to inform the player about the available moves in that game phase and then set clickable the entrance of the
     * player dashboard and/or the character cards
     * @param expertMode the game mode boolean used to decide whether to set the character cards clickable
     */
    public void showMoveOptions(boolean expertMode){
        if (!turnStarted) {
            turnStarted = true;
            if (expertMode)
                gameScreenPanel.setClickableCharacters(modelChanges.getPlayerID());
            String message = "It's your turn!";
            JOptionPane.showMessageDialog(f, message, "Action Phase", JOptionPane.PLAIN_MESSAGE);
        }

        gameScreenPanel.setClickableStudents(modelChanges.getPlayerID());
    }

    public void showMNMovement(boolean expertMode){
        /*String message="Now you can move mother nature by clicking on the island where you want to move her";
        JOptionPane.showMessageDialog(f,message,"Move mother nature",JOptionPane.PLAIN_MESSAGE);*/

        gameScreenPanel.tableCenterPanel.setMNClickable(getViewObserverList());
    }

    public void showCloudChoice(boolean expertMode){
        /*String message="Now choose a cloud to pick students from by clicking on it";
        JOptionPane.showMessageDialog(f,message,"Choose cloud",JOptionPane.PLAIN_MESSAGE);*/

        gameScreenPanel.tableCenterPanel.setCloudsClickable();

        turnStarted = false;
    }

    public void showEffectActivationChoice(CharacterCardsName cc) {
        StringBuilder message = new StringBuilder("You activated the " + cc.toString() + "!");
        switch (cc) {
            case FARMER -> {
                message.append("\nDuring this turn, you take control of any number of Professors even if you have the same number of Students as the player who currently controls them.");
                JOptionPane.showMessageDialog(f,message,"Character Card activated",JOptionPane.PLAIN_MESSAGE);
                notifyObserver(obs -> obs.onAtomicEffect(-1));
            }
            case MAGICAL_LETTER_CARRIER -> {
                message.append("\nYou may move Mother Nature up to 2 additional Islands than is indicated by the Assistant card you've played.");
                JOptionPane.showMessageDialog(f,message,"Character Card activated",JOptionPane.PLAIN_MESSAGE);
                notifyObserver(obs -> obs.onAtomicEffect(-1));
            }
            case CENTAUR -> {
                message.append("\nWhen resolving a Conquering on an Island, Towers do not count towards influence.");
                JOptionPane.showMessageDialog(f,message,"Character Card activated",JOptionPane.PLAIN_MESSAGE);
                notifyObserver(obs -> obs.onAtomicEffect(-1));
            }
            case KNIGHT -> {
                message.append("\nDuring the influence calculation this turn, you count as having 2 more influence.");
                JOptionPane.showMessageDialog(f,message,"Character Card activated",JOptionPane.PLAIN_MESSAGE);
                notifyObserver(obs -> obs.onAtomicEffect(-1));
            }

        }
    }


    /**
     * this method update the gamescreen panel according to the changes occurred in the modelStorage. The GuiDrawer reads the changes arraylist
     * in the modelStorage to correctly know which component to update.
     */
    public void updateGameScreenPanel(){

        for(int i=0;i<modelChanges.getToUpdate().size();i++){
            switch(modelChanges.getToUpdate().get(i)){
                case FILLCLOUD_CHANGED -> {
                    if(gameStart) {
                        createGameScreen();
                        showGameScreen();
                        gameStart=false;
                    }
                    else {
                        gameScreenPanel.tableCenterPanel.updateFillClouds();
                    }

                }

                case CLOUD_CHANGED -> gameScreenPanel.tableCenterPanel.updateCloud(modelChanges.getCloudID());

                case DISCARDPILE_CHANGED -> gameScreenPanel.tableCenterPanel.updateAssistCard(modelChanges.getPlayingID());

                case ENTRANCE_CHANGED -> gameScreenPanel.updateEntrance(modelChanges.getPlayingID());

                case STUDENTDINING_CHANGED -> gameScreenPanel.updateStudentDinings(modelChanges.getPlayingID());

                case PROFDINING_CHANGED -> gameScreenPanel.updateProfessors(modelChanges.getPlayingID());

                case COINS_CHANGED -> gameScreenPanel.tableCenterPanel.updateCoins(modelChanges.getPlayingID());

                case TOWERSTORAGE_CHANGED -> gameScreenPanel.updateTowerStorages(modelChanges.getPlayingID());

                case ISLE_CHANGED -> gameScreenPanel.tableCenterPanel.updateIsle(modelChanges.getIsleID());

                case ISLELAYOUT_CHANGED -> gameScreenPanel.tableCenterPanel.updateIsleLayout();

                //missing case character card and cloud changes
            }
        }
        modelChanges.getToUpdate().clear();
    }

    public void showServiceMessage(String message) {
        gameScreenPanel.setMessage(message);
    }

    public void showKOMessage(String serviceMessage){
        if (serviceMessage.equals("You don't have enough money to activate this card, please select another one"))
            gameScreenPanel.setClickableCharacters(modelChanges.getPlayerID());
        JOptionPane.showMessageDialog(f,serviceMessage,"Service message",JOptionPane.PLAIN_MESSAGE);
    }

    public ModelStorage getModelStorage() {
        return modelStorage;
    }

    public void setModelStorage(ModelStorage modelStorage) {
        this.modelStorage = modelStorage;
        modelChanges=modelStorage.getModelChanges();
    }
}

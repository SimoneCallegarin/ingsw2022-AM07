package it.polimi.ingsw.View.GUI;

import it.polimi.ingsw.Model.CharacterCards.CharacterCardsName;
import it.polimi.ingsw.Observer.Subjects.ViewSubject;
import it.polimi.ingsw.Observer.ViewObserver;
import it.polimi.ingsw.View.GUI.Buttons.AssistantCardButton;
import it.polimi.ingsw.View.GUI.Buttons.ColorChoiceButton;
import it.polimi.ingsw.View.GUI.Buttons.ExchangeChoiceButton;
import it.polimi.ingsw.View.StorageOfModelInformation.ModelChanges;
import it.polimi.ingsw.View.StorageOfModelInformation.ModelStorage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicInteger;


public class GUIDrawer extends ViewSubject {

    ModelStorage modelStorage;

    JTextField nicknameField;
    Font italicFont;
    JLabel nicknameDisplay;
    JPanel mainMenu;

    private final String frameTitle = "Eriantys Game";
    /**
     * Frame containing the GUI itself.
     */
    JFrame f = new JFrame(frameTitle);
    /**
     * Panel for the user inputs.
     */
    JPanel userInputPanel = new JPanel(new CardLayout());
    /**
     * User preferences layout to change between username insertion and game preferences choice.
     */
    CardLayout userCl = (CardLayout) userInputPanel.getLayout();
    /**
     * General panel manager switches between the screen where the user will submit his server and game preferences
     * and the screen where the actual game will take place.
     */
    JPanel generalPanelManager = new JPanel(new CardLayout());
    /**
     * GeneralPanelManager layout to show different panels on events happening.
     */
    CardLayout cl = (CardLayout) generalPanelManager.getLayout();
    /**
     * on this button press the player will join a lobby and wait for the game to start
     */
    JButton startGame = new JButton("Start game");

    LoadingScreen loadingScreen;
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
    boolean gameStart = true;
    /**
     * the class containing all the information about the changes occurred in the ModelStorage
     */
    ModelChanges modelChanges;

    /**
     * boolean used to know if it is necessary to let appear a JOptionPanel of start turn or not
     */
    private boolean turnStarted = false;

    /**
     * this method initialize the first screen when you open the app where you'll be asked the username, the game mode and the
     * number of Players. It uses a frame whose content pane is a General Panel Manager which will switch between the Initial Background panel
     * where the user will input his game preferences, and the proper game screen where the actual game will take place.
     * The InitialBackground class contains a userInputPanel which will switch between the username form and the game preferences form.
     */
    public void screeInitialization() {
        //screen frame initialization
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setExtendedState(JFrame.MAXIMIZED_BOTH);
        f.setVisible(true);
        final int screenDimensionX = f.getWidth();
        final int screenDimensionY = f.getHeight();
        //set the initial content pane where it will be added the user input manager.
        InitialBackgroundPanel initialBackgroundPanel = new InitialBackgroundPanel(new BorderLayout(), screenDimensionX, screenDimensionY);
        //add it to the general manager.
        generalPanelManager.add(initialBackgroundPanel, "User Input");

        initialBackgroundPanel.add(userInputPanel, BorderLayout.CENTER);

        //add the general panel manager as the content pane to switch between user input screen and the actual game.
        f.setContentPane(generalPanelManager);

        //ask the username
        showUsernameForm();
    }

    /**
     * Shows on screen the input form for the username.
     * It initializes the usernameForm panel and adds it to the user input panel.
     * The usernameForm panel contains a check button that checks if the input is valid (between 2 and 20 characters).
     * Valid nicknames are written on the right side of the panel with green text, otherwise they will be displayed as red.
     * It also contains a submit button that notifies the ClientController with valid nicknames when pressed.
     * If the input is acceptable it notifies the ClientController who forward the information to the server.
     * The server answers the ClientHandler if the nickname has been already taken by another player or not.
     * In the first case then it will be notified on screen by an error message, otherwise the game will soon begin.
     */
    public void showUsernameForm() {

        mainMenu = new JPanel();

        mainMenu.setLayout(new BoxLayout(mainMenu, BoxLayout.LINE_AXIS));

        JPanel leftHalf = new JPanel(new GridLayout(3, 1)) {
            //Don't allow the nickname field to stretch vertically.
            public Dimension getMaximumSize() {
                Dimension pref = getPreferredSize();
                return new Dimension(Integer.MAX_VALUE, pref.height);
            }
        };

        JLabel nicknameLabel = new JLabel("Insert username:");
        nicknameLabel.setHorizontalAlignment(JLabel.LEFT);
        nicknameField = new JTextField();

        leftHalf.add(nicknameLabel, 0);
        leftHalf.add(nicknameField, 1);

        JPanel buttons = new JPanel(new GridLayout(1, 2));
        JButton check = new JButton("Check");
        JButton submit = new JButton("Submit");
        check.addActionListener(this::updateDisplay);
        submit.addActionListener(e -> {
            if (nicknameDisplay.getForeground() != Color.GREEN)
                JOptionPane.showMessageDialog(userInputPanel, "Check the nickname first", "Error", JOptionPane.ERROR_MESSAGE);
            else {
                usernamePlaying = nicknameField.getText();
                notifyObserver(obs -> obs.onUsername(usernamePlaying));
            }
        });
        buttons.add(check);
        buttons.add(submit);

        leftHalf.add(buttons, 2);

        leftHalf.setLayout(new BoxLayout(leftHalf, BoxLayout.PAGE_AXIS));

        mainMenu.add(leftHalf);
        mainMenu.add(createNicknameDisplay());

        userInputPanel.add(mainMenu);
    }

    /**
     * Displays on screen on the right side of the main menu panel the nickname chosen and checked,
     * that will be green when valid, else red.
     *
     * @return the right panel of the main menu.
     */
    private JComponent createNicknameDisplay() {
        JPanel panel = new JPanel(new BorderLayout());
        nicknameDisplay = new JLabel();
        nicknameDisplay.setHorizontalAlignment(JLabel.CENTER);
        italicFont = nicknameDisplay.getFont().deriveFont(Font.ITALIC, 16.0f);

        panel.add(new JSeparator(JSeparator.VERTICAL), BorderLayout.LINE_START);
        panel.add(nicknameDisplay, BorderLayout.CENTER);
        panel.setPreferredSize(new Dimension(200, 150));

        return panel;
    }

    /**
     * Updates the right panel of the main menu when the check button is pressed.
     *
     * @param e the action event of the check button pressed.
     */
    public void updateDisplay(ActionEvent e) {
        nicknameDisplay.setText(nicknameField.getText());
        nicknameDisplay.setForeground(Color.GREEN);
        if (nicknameDisplay.getText().length() < 2 || nicknameDisplay.getText().length() >= 20)
            nicknameDisplay.setForeground(Color.RED);
        nicknameDisplay.setFont(italicFont);

    }

    /**
     * Shows on screen the possible choice for the game mode and the number of players.
     * It initializes the gamePreferences panel and adds it to the user input panel.
     * The gamePreferences panel contains a start game button that notifies the ClientController.
     * Once the ClientController is notified this method calls showLoadingScreen.
     */
    public void showGamePreferencesForm() {
        JPanel gamePreferences = new JPanel(new GridLayout(5, 3));

        gamePreferences.add(new JLabel("Select number of players:"), 0);

        JPanel numberOfPlayers = new JPanel(new GridLayout(1, 3));
        JRadioButton twoPlayers = new JRadioButton("2");
        twoPlayers.setMnemonic(KeyEvent.VK_2);
        twoPlayers.setActionCommand("2");
        twoPlayers.setSelected(true);
        JRadioButton threePlayers = new JRadioButton("3");
        threePlayers.setMnemonic(KeyEvent.VK_3);
        threePlayers.setActionCommand("3");
        JRadioButton fourPlayers = new JRadioButton("4");
        fourPlayers.setMnemonic(KeyEvent.VK_4);
        fourPlayers.setActionCommand("4");
        numberOfPlayers.add(twoPlayers);
        numberOfPlayers.add(threePlayers);
        numberOfPlayers.add(fourPlayers);
        gamePreferences.add(numberOfPlayers, 1);

        gamePreferences.add(new JLabel("Select game mode:"), 2);

        JPanel gameMode = new JPanel(new GridLayout(1, 2));
        JRadioButton baseMode = new JRadioButton("Base");
        baseMode.setMnemonic(KeyEvent.VK_B);
        baseMode.setActionCommand("Base");
        baseMode.setSelected(true);
        JRadioButton expertMode = new JRadioButton("Expert");
        expertMode.setMnemonic(KeyEvent.VK_B);
        expertMode.setActionCommand("Expert");
        expertMode.setSelected(true);
        gameMode.add(baseMode, 0);
        gameMode.add(expertMode, 1);
        ButtonGroup numOfPlayersButtons = new ButtonGroup();
        ButtonGroup gameModeButtons = new ButtonGroup();
        numOfPlayersButtons.add(twoPlayers);
        numOfPlayersButtons.add(threePlayers);
        numOfPlayersButtons.add(fourPlayers);
        gameModeButtons.add(baseMode);
        gameModeButtons.add(expertMode);

        gamePreferences.add(gameMode, 3);

        startGame.addActionListener(e -> {
            //add try catch
            int numPlayers = Integer.parseInt(numOfPlayersButtons.getSelection().getActionCommand());
            boolean gameModeChosen;
            gameModeChosen = gameModeButtons.getSelection().getActionCommand().equals("Expert");

            notifyObserver(obs -> obs.onGamePreferences(numPlayers, gameModeChosen));
            showLoadingScreen();
        });

        gamePreferences.add(startGame, 4);

        userInputPanel.add(gamePreferences, "Game preferences form");
        userCl.show(userInputPanel, "Game preferences form");
    }

    /**
     * this method shows a loading screen while waiting for other players to join and loading the game
     */
    public void showLoadingScreen() {
        loadingScreen = new LoadingScreen(usernamePlaying);
        //need to update the loading screen graphics
        generalPanelManager.add(loadingScreen, "Loading Screen");
        cl.show(generalPanelManager, "Loading Screen");

    }

    public void createGameScreen() {
        //initialize the game screen and add it to the generalPanelManager
        gameScreenPanel = new GameScreenPanel(new GridBagLayout(), modelStorage, f.getWidth(), f.getHeight(), usernamePlaying, loadingScreen.getNicknameColor(), getViewObserverList());
        generalPanelManager.add(gameScreenPanel, "Game Screen");
    }

    /**
     * this method uses the general panel manager card layout to switch from the InitialBackground panel to the GameScreen panel
     */
    public void showGameScreen() {
        //switch to the actual game screen
        cl.show(generalPanelManager, "Game Screen");
    }

    /**
     * this method opens a JOptionPane to select the assistant card. Each assistant card displayed is a button which on press updates
     * the relative player discard pile
     *
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
            int finalI = modelStorage.getDashboard(playerID).getAssistantCardsTurnOrder().get(i);
            int finalI1 = i;
            buttons[i].addActionListener(e -> {
                ClassLoader cl1 = this.getClass().getClassLoader();
                InputStream url1 = cl1.getResourceAsStream("GameTable/Assistant_cards/2x/" + finalI + "chk.png");
                BufferedImage img1 = null;
                try {
                    if (url1 != null)
                        img1 = ImageIO.read(url1);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                if (img1 != null) {
                    buttons[finalI1].setIcon(new ImageIcon(img1));
                }

                if (turnOrder.get() != 0 && turnOrder.get() != finalI) {
                    ClassLoader cl2 = this.getClass().getClassLoader();
                    InputStream url2 = cl2.getResourceAsStream("GameTable/Assistant_cards/2x/" + turnOrder.get() + ".png");
                    BufferedImage img2 = null;
                    try {
                        if (url2 != null)
                            img2 = ImageIO.read(url2);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    if (img2 != null) {
                        buttons[lastPressedButton.get()].setIcon(new ImageIcon(img2));
                    }
                }
                //gameScreenPanel.tableCenterPanel.updateAllAssistCard();
                lastPressedButton.set(finalI1);
                turnOrder.set(finalI);

            });
            buttonContainer.add(buttons[i]);
        }
        JScrollPane scrollPane = new JScrollPane(buttonContainer);
        scrollPane.setPreferredSize(new Dimension(1000, 564));
        JOptionPane.showMessageDialog(f, scrollPane, "Your deck", JOptionPane.PLAIN_MESSAGE);
        return turnOrder.get();
    }

    /**
     * this method create a JOptionPane to inform the player about the available moves in that game phase and then set clickable the entrance of the
     * player dashboard and/or the character cards
     *
     * @param expertMode the game mode boolean used to decide whether to set the character cards clickable
     */
    public void showMoveOptions(boolean expertMode) {
        if (!turnStarted) {
            turnStarted = true;
            if (expertMode)
                gameScreenPanel.setCharactersClickable(modelChanges.getPlayerID());
            String message = "It's your turn!";
            JOptionPane.showMessageDialog(f, message, "Action Phase", JOptionPane.PLAIN_MESSAGE);
        }

        gameScreenPanel.setEntranceStudentsClickable(modelChanges.getPlayerID());
    }

    public void showMNMovement() {
        /*String message="Now you can move mother nature by clicking on the island where you want to move her";
        JOptionPane.showMessageDialog(f,message,"Move mother nature",JOptionPane.PLAIN_MESSAGE);*/

        gameScreenPanel.getTableCenterPanel().setMNClickable(getViewObserverList());
    }

    public void showCloudChoice() {
        /*String message="Now choose a cloud to pick students from by clicking on it";
        JOptionPane.showMessageDialog(f,message,"Choose cloud",JOptionPane.PLAIN_MESSAGE);*/

        gameScreenPanel.getTableCenterPanel().setCloudsClickable();

        turnStarted = false;
    }

    /**
     * Informs the user about the Character Card he played and activates various listeners according to the Character Card.
     * @param cc is the name of the Character Card played
     */
    public void showEffectActivationChoice(CharacterCardsName cc) {
        StringBuilder message = new StringBuilder("You activated the " + cc.toString() + "!");
        switch (cc) {
            case FARMER -> {
                message.append("\nDuring this turn, you take control of any number of Professors even if you have the same number of Students as the player who currently controls them.");
                JOptionPane.showMessageDialog(f, message, "Character Card activated", JOptionPane.PLAIN_MESSAGE);
                notifyObserver(obs -> obs.onAtomicEffect(-1));
            }
            case MAGICAL_LETTER_CARRIER -> {
                message.append("\nYou may move Mother Nature up to 2 additional Islands than is indicated by the Assistant card you've played.");
                JOptionPane.showMessageDialog(f, message, "Character Card activated", JOptionPane.PLAIN_MESSAGE);
                notifyObserver(obs -> obs.onAtomicEffect(-1));
            }
            case CENTAUR -> {
                message.append("\nWhen resolving a Conquering on an Island, Towers do not count towards influence.");
                JOptionPane.showMessageDialog(f, message, "Character Card activated", JOptionPane.PLAIN_MESSAGE);
                notifyObserver(obs -> obs.onAtomicEffect(-1));
            }
            case KNIGHT -> {
                message.append("\nDuring the influence calculation this turn, you count as having 2 more influence.");
                JOptionPane.showMessageDialog(f, message, "Character Card activated", JOptionPane.PLAIN_MESSAGE);
                notifyObserver(obs -> obs.onAtomicEffect(-1));
            }
            case HERALD -> {
                message.append("\nChoose an Island and resolve the Island as if Mother Nature had ended her movement there.\nMother Nature will still move and the Island where she ends her movement will also be resolved.");
                JOptionPane.showMessageDialog(f,message,"Character Card activated",JOptionPane.PLAIN_MESSAGE);
                gameScreenPanel.getTableCenterPanel().setIslesClickableForEffect(getViewObserverList());
            }
            case GRANDMA_HERBS -> {
                message.append("\nPlace a NoEntry tile on an Island of your choice.\nThe first time Mother Nature ends her movement there, put the No Entry tile back onto this card\n(DO NOT calculate influence on that Island, or place any Towers).");
                JOptionPane.showMessageDialog(f,message,"Character Card activated",JOptionPane.PLAIN_MESSAGE);
                gameScreenPanel.getTableCenterPanel().setIslesClickableForEffect(getViewObserverList());
            }
            case FUNGIST -> {
                message.append("\nChoose a color of Student: during the influence calculation this turn, that color adds no influence.");
                JOptionPane.showMessageDialog(f, message, "Character Card activated", JOptionPane.PLAIN_MESSAGE);
                //showColorForm("Which color you don't want to consider for influence calculation in this turn?");
                int chosenColor = showColorForm("Which color you don't want to consider for influence calculation in this turn?");
                notifyObserver(obs -> obs.onAtomicEffect(chosenColor));
            }
            case THIEF -> {
                message.append("\nChoose a type of Student: every player (including yourself) must return 3 Students of that type from their Dining Room to the bag.\nIf any player has fewer than 3 Students of that type, return as many Students as they have.");
                JOptionPane.showMessageDialog(f, message, "Character Card activated", JOptionPane.PLAIN_MESSAGE);
                //showColorForm("Which color you want to be removed from Dining Rooms?");
                int chosenColor = showColorForm("Which color you want to be removed from Dining Rooms?");
                notifyObserver(obs -> obs.onAtomicEffect(chosenColor));
            }
            case SPOILED_PRINCESS -> {
                message.append("\nTake 1 Student from this card and place it in your Dining Room. Then, draw a new Student from the Bag and place it on this card.");
                JOptionPane.showMessageDialog(f, message, "Character Card activated", JOptionPane.PLAIN_MESSAGE);
                CharacterPanel spoiledPrincessPanel = null;
                for (CharacterPanel character : gameScreenPanel.getTableCenterPanel().getCharacterPanels()) {
                    if (character.getCharacterName().equals(CharacterCardsName.SPOILED_PRINCESS.toString())) {
                        spoiledPrincessPanel = character;
                        break;
                    }
                }
                gameScreenPanel.getTableCenterPanel().setCharacterStudentsClickable(spoiledPrincessPanel, gameScreenPanel.getDashboardPanel(modelChanges.getPlayerID()));
            }
            case MONK -> {
                message.append("\nTake 1 Student from this card and place it on an Island of your choice. Then, draw a new Student from the Bag and place it on this card.");
                JOptionPane.showMessageDialog(f, message, "Character Card activated", JOptionPane.PLAIN_MESSAGE);
                CharacterPanel monkPanel = null;
                for (CharacterPanel character : gameScreenPanel.getTableCenterPanel().getCharacterPanels()) {
                    if (character.getCharacterName().equals(CharacterCardsName.MONK.toString())) {
                        monkPanel = character;
                        break;
                    }
                }
                gameScreenPanel.getTableCenterPanel().setCharacterStudentsClickable(monkPanel, gameScreenPanel.getDashboardPanel(modelChanges.getPlayerID()));
            }
            case JESTER -> {
                message.append("\nYou may take up to 3 Students from this card and replace them with the same number of Students from your Entrance.");
                JOptionPane.showMessageDialog(f,message,"Character Card activated",JOptionPane.PLAIN_MESSAGE);
                if (showExchangeForm())
                    notifyObserver(ViewObserver::onEndCharacterPhase);
                else {
                    CharacterPanel jesterPanel = null;
                    for (CharacterPanel character : gameScreenPanel.getTableCenterPanel().getCharacterPanels()) {
                        if (character.getCharacterName().equals(CharacterCardsName.JESTER.toString())) {
                            jesterPanel = character;
                            break;
                        }
                    }
                    gameScreenPanel.getTableCenterPanel().setCharacterStudentsClickable(jesterPanel, gameScreenPanel.getDashboardPanel(modelChanges.getPlayerID()));
                }
            }
            case MINSTREL -> {
                message.append("\nNow you can exchange up to 2 students between your dining and your entrance");
                JOptionPane.showMessageDialog(f,message,"Character Card activated",JOptionPane.PLAIN_MESSAGE);
                if (showExchangeForm())
                    notifyObserver(ViewObserver::onEndCharacterPhase);
                else
                    gameScreenPanel.setDiningStudentsClickable(modelChanges.getPlayingID());

            }
        }
    }


    /**
     * this method update the gamescreen panel according to the changes occurred in the modelStorage. The GuiDrawer reads the changes arraylist
     * in the modelStorage to correctly know which component to update.
     */
    public void updateGameScreenPanel() {
        for (int i = 0; i < modelChanges.getToUpdate().size(); i++) {
            switch (modelChanges.getToUpdate().get(i)) {
                case FILLCLOUD_CHANGED -> {
                    if (gameStart) {
                        createGameScreen();
                        showGameScreen();
                        gameStart=false;
                    }
                    else {
                        gameScreenPanel.getTableCenterPanel().updateFillClouds();
                    }

                }

                case CLOUD_CHANGED -> gameScreenPanel.getTableCenterPanel().updateCloud(modelChanges.getCloudID());

                case DISCARDPILE_CHANGED -> gameScreenPanel.getTableCenterPanel().updateAssistCard(modelChanges.getPlayingID());

                case ENTRANCE_CHANGED -> gameScreenPanel.updateEntrance(modelChanges.getPlayingID());

                case STUDENTDINING_CHANGED -> gameScreenPanel.updateStudentDinings(modelChanges.getPlayingID());

                case PROFDINING_CHANGED -> gameScreenPanel.updateProfessors();

                case COINS_CHANGED -> gameScreenPanel.getTableCenterPanel().updateCoins(modelChanges.getPlayingID());

                case TOWERSTORAGE_CHANGED -> gameScreenPanel.updateTowerStorages();

                case ISLE_CHANGED -> gameScreenPanel.getTableCenterPanel().updateIsle(modelChanges.getIsleID());

                case ISLELAYOUT_CHANGED -> {
                    if (modelChanges.isLayoutChanged()) {

                        for(int j=0;j<modelChanges.getIslesToRemove();j++) {
                            gameScreenPanel.getTableCenterPanel().updateIsleLayout();
                        }
                    }
                    for(int j=0;j<modelStorage.getGameTable().getIsles().size();j++) {
                        gameScreenPanel.getTableCenterPanel().updateIsle(j);
                    }
                    modelChanges.setLayoutChanged(false);
                }
                case CHARACTERCARD_CHANGED -> {
                    for(CharacterPanel characterPanel:gameScreenPanel.getTableCenterPanel().getCharacterPanels()){
                        characterPanel.resetCharacter();
                        characterPanel.initializeCharacter();
                    }
                }
            }
        }
        modelChanges.getToUpdate().clear();
    }

    /**
     * Prints a message at the top of the screen which informs the user about the phase that is being played.
     * @param message the text to display
     */
    public void showServiceMessage(String message) {
        gameScreenPanel.setMessage(message);
    }

    /**
     * Pops up a JOptionPane which tells the user that he has to repeat the last action.
     * @param serviceMessage the KO to display
     */
    public void showKOMessage(String serviceMessage) {
        if (serviceMessage.equals("You don't have enough money to activate this card, please select another one") || serviceMessage.equals("This cloud is empty! Please select another one"))
            gameScreenPanel.setCharactersClickable(modelChanges.getPlayerID());
        JOptionPane.showMessageDialog(f,serviceMessage,"Service message",JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * Opens a JOptionPanel to select a color. It is used for FUNGIST and THIEF Character Cards.
     * @param colorQuestion the question to ask
     */
    private int showColorForm(String colorQuestion) {
        AtomicInteger colorChoice = new AtomicInteger();
        colorChoice.set(-1);
        JPanel buttonContainer = new JPanel();
        JButton[] buttons = new JButton[5];
        for (int i = 0; i < 5; i++) {
            buttons[i] = (new ColorChoiceButton(i));
            int finalI = i;
            buttons[i].addActionListener(e -> {
                ClassLoader cl1 = this.getClass().getClassLoader();
                InputStream url1;
                switch (finalI) {
                    case 0 -> url1 = cl1.getResourceAsStream("Dashboard/Students/YellowChk.png");
                    case 1 -> url1 = cl1.getResourceAsStream("Dashboard/Students/PinkChk.png");
                    case 2 -> url1 = cl1.getResourceAsStream("Dashboard/Students/BlueChk.png");
                    case 3 -> url1 = cl1.getResourceAsStream("Dashboard/Students/RedChk.png");
                    case 4 -> url1 = cl1.getResourceAsStream("Dashboard/Students/GreenChk.png");
                    default -> url1 = cl1.getResourceAsStream("Dashboard/Circles.png");
                }
                BufferedImage img1 = null;
                try {
                    if (url1 != null)
                        img1 = ImageIO.read(url1);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                if (img1 != null) {
                    buttons[finalI].setIcon(new ImageIcon(img1));
                }

                if (colorChoice.get() != -1 && colorChoice.get() != finalI) {
                    ClassLoader cl2 = this.getClass().getClassLoader();
                    InputStream url2;
                    switch (colorChoice.get()) {
                        case 0 -> url2 = cl2.getResourceAsStream("Dashboard/Students/Yellow.png");
                        case 1 -> url2 = cl2.getResourceAsStream("Dashboard/Students/Pink.png");
                        case 2 -> url2 = cl2.getResourceAsStream("Dashboard/Students/Blue.png");
                        case 3 -> url2 = cl2.getResourceAsStream("Dashboard/Students/Red.png");
                        case 4 -> url2 = cl2.getResourceAsStream("Dashboard/Students/Green.png");
                        default -> url2 = cl2.getResourceAsStream("Dashboard/Circles.png");
                    }
                    BufferedImage img2 = null;
                    try {
                        if (url2 != null)
                            img2 = ImageIO.read(url2);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    if (img2 != null) {
                        buttons[colorChoice.get()].setIcon(new ImageIcon(img2));
                    }
                }

                colorChoice.set(finalI);

            });
            buttonContainer.add(buttons[i]);
        }
        JScrollPane scrollPane = new JScrollPane(buttonContainer);
        scrollPane.setPreferredSize(new Dimension(500, 40));
        JOptionPane.showMessageDialog(f, scrollPane, colorQuestion, JOptionPane.PLAIN_MESSAGE);
        return colorChoice.get();
    }

    /**
     * Opens a JOptionPanel to select if continuing to use the effect of JESTER/MINSTREL Character Card or not.
     * @return the answer of the user
     */
    private boolean showExchangeForm() {
        AtomicInteger exchangeChoice = new AtomicInteger();
        exchangeChoice.set(-1);
        JPanel buttonContainer = new JPanel();
        JButton[] buttons = new JButton[2];
        for (int i = 0; i < 2; i++) {
            buttons[i] = (new ExchangeChoiceButton(i));
            int finalI = i;
            buttons[i].addActionListener(e -> {
                ClassLoader cl1 = this.getClass().getClassLoader();
                InputStream url1;
                if (finalI == 0) {
                    url1 = cl1.getResourceAsStream("Raw/CheckChk.png");
                } else {
                    url1 = cl1.getResourceAsStream("Raw/RedCrossChk.png");
                }
                BufferedImage img1 = null;
                try {
                    if (url1 != null)
                        img1 = ImageIO.read(url1);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                if (img1 != null) {
                    buttons[finalI].setIcon(new ImageIcon(img1));
                }

                if (exchangeChoice.get() != -1 && exchangeChoice.get() != finalI) {
                    ClassLoader cl2 = this.getClass().getClassLoader();
                    InputStream url2;
                    if (exchangeChoice.get() == 0) {
                        url2 = cl2.getResourceAsStream("Raw/Check.png");
                    } else {
                        url2 = cl2.getResourceAsStream("Raw/RedCross.png");
                    }
                    BufferedImage img2 = null;
                    try {
                        if (url2 != null)
                            img2 = ImageIO.read(url2);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    if (img2 != null) {
                        buttons[exchangeChoice.get()].setIcon(new ImageIcon(img2));
                    }
                }

                exchangeChoice.set(finalI);

            });
            buttonContainer.add(buttons[i]);
        }
        JScrollPane scrollPane = new JScrollPane(buttonContainer);
        scrollPane.setPreferredSize(new Dimension(440, 220));
        JOptionPane.showMessageDialog(f, scrollPane, "Do you want to exchange students?", JOptionPane.PLAIN_MESSAGE);
        return exchangeChoice.get() != 0;
    }

    public void showErrorMessage(String serviceMessage) {
        JOptionPane.showMessageDialog(f, serviceMessage, "Disconnection occurred", JOptionPane.ERROR_MESSAGE);
    }

    public ModelStorage getModelStorage() {
        return modelStorage;
    }

    public void setModelStorage(ModelStorage modelStorage) {
        this.modelStorage = modelStorage;
        modelChanges = modelStorage.getModelChanges();
    }

    public void showWinner(String winner) {
        String message=winner+" won the game!";
        JOptionPane.showMessageDialog(f,message,"Winner",JOptionPane.PLAIN_MESSAGE);
        f.dispose();
    }
}
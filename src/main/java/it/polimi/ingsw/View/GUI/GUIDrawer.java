package it.polimi.ingsw.View.GUI;

import it.polimi.ingsw.Model.CharacterCards.CharacterCardsName;
import it.polimi.ingsw.Observer.Subjects.ViewSubject;
import it.polimi.ingsw.Observer.ViewObserver;
import it.polimi.ingsw.View.GUI.Buttons.AssistantCardButton;
import it.polimi.ingsw.View.GUI.Buttons.ColorChoiceButton;
import it.polimi.ingsw.View.GUI.Buttons.ExchangeChoiceButton;
import it.polimi.ingsw.View.GUI.ExpertPanels.CharacterPanel;
import it.polimi.ingsw.View.StorageOfModelInformation.ModelChanges;
import it.polimi.ingsw.View.StorageOfModelInformation.ModelStorage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicInteger;

import static it.polimi.ingsw.View.GUI.GUIConstants.*;
import static it.polimi.ingsw.View.GUI.ImagesLoader.*;

/**
 * Class called upon GUI creation that start the GUI construction chain.
 * It's also responsible for showing forms to the user and managing the update logic.
 */
public class GUIDrawer extends ViewSubject {

    /**
     * Model Storage reference used to retrieve game state information.
     */
    private ModelStorage modelStorage;
    /**
     * Contains all the information about the changes occurred in the ModelStorage.
     */
    private ModelChanges modelChanges;
    /**
     * String placed in the frame to identify the application
     */
    private final String frameTitle = gameTitle;
    /**
     * Frame containing the GUI itself.
     */
    private final JFrame f = new JFrame(frameTitle);
    // Nickname selection:
    /**
     * Panel for the user inputs.
     */
    private final JPanel userInputPanel = new JPanel(new CardLayout());
    /**
     * JTextField used to insert nickname choice.
     */
    private JTextField nicknameField;
    /**
     * JLabel used to print the chosen nickname.
     */
    private JLabel nicknameDisplay;
    // User's game preferences selection:
    /**
     * User preferences layout to change between username insertion and game preferences choice.
     */
    private final CardLayout userCl = (CardLayout) userInputPanel.getLayout();
    /**
     * On this button press the player will join a lobby and wait for the game to start.
     */
    private final JButton startGame = new JButton("Start game");
    /**
     * Loading screen shown while waiting for the game to load/other player to join.
     */
    private WaitingRoom waitingRoom;
    // Actual game screen:
    /**
     * panel where the actual game will take place
     */
    private GameScreenPanel gameScreenPanel;
    /**
     * Boolean variable used to execute only one time the GameScreenPanel creation.
     */
    private boolean gameStart = true;
    /**
     * General panel manager switches between the screen where the user will submit his game preferences
     * and the screen where the actual game will take place.
     */
    private final JPanel generalPanelManager = new JPanel(new CardLayout());
    /**
     * GeneralPanelManager layout to show different panels on events happening.
     */
    private final CardLayout cl = (CardLayout) generalPanelManager.getLayout();
    /**
     * The username of the player currently using this GUI.
     */
    private String usernamePlaying;
    /**
     * Used to know if it's necessary to let appear a JOptionPanel of start turn or not.
     */
    private boolean turnStarted = false;

    /**
     * Initializes the first screen when you open the app where you'll be asked the username,
     * the game mode and the number of Players.
     * It uses a frame whose content pane is a General Panel Manager
     * which will switch between the Initial Background panel
     * where the user will input his game preferences,
     * and the proper game screen where the actual game will take place.
     * The InitialBackground class contains a userInputPanel which will switch
     * between the username form and the game preferences form.
     */
    public void screeInitialization() {
        // Screen frame initialization:
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setExtendedState(JFrame.MAXIMIZED_BOTH);
        f.setVisible(true);
        setScreenDimensionX(f.getWidth());
        setScreenDimensionY(f.getHeight());
        // Set the initial content pane where it will be added the user input manager.
        InitialBackgroundPanel initialBackgroundPanel = new InitialBackgroundPanel(new BorderLayout());
        // Add it to the general manager.
        initialBackgroundPanel.add(userInputPanel, BorderLayout.CENTER);
        generalPanelManager.add(initialBackgroundPanel, "User Input");

        // Add the general panel manager as the content pane to switch between user input screen and the actual game.
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
        JPanel mainMenu = new JPanel();
        mainMenu.setLayout(new BoxLayout(mainMenu, BoxLayout.LINE_AXIS));

        JPanel leftHalf = new JPanel(new GridLayout(3, 1)) {
            // Don't allow the nickname field to stretch vertically.
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
        JButton check = new JButton("Check Length");
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
     * @return the right panel of the main menu.
     */
    private JComponent createNicknameDisplay() {
        JPanel nicknameRightSide = new JPanel(new BorderLayout());
        nicknameDisplay = new JLabel();
        nicknameDisplay.setHorizontalAlignment(JLabel.CENTER);

        nicknameRightSide.add(new JSeparator(JSeparator.VERTICAL), BorderLayout.LINE_START);
        nicknameRightSide.add(nicknameDisplay, BorderLayout.CENTER);
        nicknameRightSide.setPreferredSize(nicknameDisplayDimension);

        return nicknameRightSide;
    }

    /**
     * Updates the right panel of the main menu when the check button is pressed.
     * @param e the action event of the check button pressed.
     */
    public void updateDisplay(ActionEvent e) {
        nicknameDisplay.setText(nicknameField.getText());
        nicknameDisplay.setForeground(Color.GREEN);
        if (nicknameDisplay.getText().length() < 2 || nicknameDisplay.getText().length() >= 11)
            nicknameDisplay.setForeground(Color.RED);
        nicknameDisplay.setFont(new Font(SansSerif, Font.ITALIC, 24));
    }

    /**
     * Shows on screen the possible choice for the game mode and the number of players.
     * It initializes the gamePreferences panel and adds it to the user input panel.
     * The gamePreferences panel contains a start game button that notifies the ClientController.
     * Once the ClientController is notified this method calls showLoadingScreen.
     */
    public void showGamePreferencesForm() {
        JPanel gamePreferences = new JPanel(new GridLayout(5, 3));

        gamePreferences.add(new JLabel("Select number of players:"));
        JPanel numberOfPlayers = new JPanel(new GridLayout(1, 3));
        JRadioButton twoPlayers = createChoiceButton("2",KeyEvent.VK_2,"2");
        JRadioButton threePlayers = createChoiceButton("3",KeyEvent.VK_3,"3");
        JRadioButton fourPlayers = createChoiceButton("4",KeyEvent.VK_4,"4");
        ButtonGroup numOfPlayersButtons = new ButtonGroup();
        numOfPlayersButtons.add(twoPlayers);
        numOfPlayersButtons.add(threePlayers);
        numOfPlayersButtons.add(fourPlayers);
        numberOfPlayers.add(twoPlayers);
        numberOfPlayers.add(threePlayers);
        numberOfPlayers.add(fourPlayers);
        gamePreferences.add(numberOfPlayers);

        gamePreferences.add(new JLabel("Select game mode:"));
        JPanel gameMode = new JPanel(new GridLayout(1, 2));
        JRadioButton baseMode = createChoiceButton("Base",KeyEvent.VK_B,"Base");
        JRadioButton expertMode = createChoiceButton("Expert",KeyEvent.VK_E,"Expert");
        ButtonGroup gameModeButtons = new ButtonGroup();
        gameModeButtons.add(baseMode);
        gameModeButtons.add(expertMode);
        gameMode.add(baseMode);
        gameMode.add(expertMode);
        gamePreferences.add(gameMode);

        startGame.addActionListener(e -> {
            int numPlayers = Integer.parseInt(numOfPlayersButtons.getSelection().getActionCommand());
            boolean gameModeChosen;
            gameModeChosen = gameModeButtons.getSelection().getActionCommand().equals("Expert");

            notifyObserver(obs -> obs.onGamePreferences(numPlayers, gameModeChosen));
            showWaitingRoom();
        });

        gamePreferences.add(startGame);

        userInputPanel.add(gamePreferences, "Game preferences form");
        userCl.show(userInputPanel, "Game preferences form");
    }

    /**
     * Creates the button used in the game preferences selection.
     * @param text text of the button.
     * @param key KeyEvent associated to the buttons.
     * @param actionCommand action command of the buttons.
     * @return the button with the desired attributes.
     */
    private JRadioButton createChoiceButton(String text, int key, String actionCommand) {
        JRadioButton choiceButton = new JRadioButton(text);
        choiceButton.setMnemonic(key);
        choiceButton.setActionCommand(actionCommand);
        if(text.equals("2") || text.equals("Base"))
            choiceButton.setSelected(true);
        return choiceButton;
    }

    /**
     * Shows a waiting room on screen while waiting for other players to join and start the game.
     */
    public void showWaitingRoom() {
        waitingRoom = new WaitingRoom(usernamePlaying);
        //need to update the waiting room graphics
        generalPanelManager.add(waitingRoom, "Waiting room");
        cl.show(generalPanelManager, "Waiting room");

    }

    /**
     * Initializes the game screen and add it to the generalPanelManager.
     */
    public void createGameScreen() {
        gameScreenPanel = new GameScreenPanel(new GridBagLayout(), modelStorage, screenDimensionX, screenDimensionY, usernamePlaying, waitingRoom.getNicknameColor(), getViewObserverList());
        generalPanelManager.add(gameScreenPanel, "Game Screen");
    }

    /**
     * Uses the general panel manager card layout to switch from the InitialBackground panel to the GameScreen panel.
     */
    public void showGameScreen() {
        addLogoutButton();
        // Switch to the actual game screen
        cl.show(generalPanelManager, "Game Screen");
    }

    /**
     * Opens a JOptionPane to select the assistant card.
     * Each assistant card displayed is a button which on press updates the relative player discard pile.
     * @param playerID the playerID used to decide which character card display and which discard pile update.
     */
    public int ShowAssistantCardsForm(int playerID) {
        AtomicInteger turnOrder = new AtomicInteger();
        turnOrder.set(0);
        JPanel buttonContainer = assistantCardsButtons(playerID,turnOrder);
        JScrollPane scrollPane = new JScrollPane(buttonContainer);
        scrollPane.setPreferredSize(assistantCardsDimension);
        JOptionPane.showMessageDialog(f, scrollPane, "Your deck", JOptionPane.PLAIN_MESSAGE);
        return turnOrder.get();
    }

    /**
     * Associates to each assistant card displayed a button that permits to update
     * the relative player discard pile when pressed.
     * @param playerID the playerID used to decide which character card display and which discard pile update.
     * @param turnOrder turn order of the assistant card.
     * @return the assistant cards in form of buttons.
     */
    private JPanel assistantCardsButtons(int playerID, AtomicInteger turnOrder) {
        JPanel buttonContainer = new JPanel();
        JButton[] buttons = new JButton[modelStorage.getDashboard(playerID).getAssistantCardsMNMovement().size()];
        AtomicInteger lastPressedButton = new AtomicInteger();
        for (int i = 0; i < modelStorage.getDashboard(playerID).getAssistantCardsMNMovement().size(); i++) {
            buttons[i] = (new AssistantCardButton(modelStorage.getDashboard(playerID).getAssistantCardsTurnOrder().get(i)));
            int finalI = modelStorage.getDashboard(playerID).getAssistantCardsTurnOrder().get(i);
            int finalI1 = i;
            buttons[i].addActionListener(e -> {
                InputStream url1 = this.getClass().getClassLoader().getResourceAsStream("GameTable/Assistant_cards/2x/" + finalI + "chk.png");
                loadImages(buttons, finalI1, url1);
                if (turnOrder.get() != 0 && turnOrder.get() != finalI) {
                    InputStream url2 = this.getClass().getClassLoader().getResourceAsStream("GameTable/Assistant_cards/2x/" + turnOrder.get() + ".png");
                    loadImagesOnLastPressedButton(buttons, lastPressedButton, url2);
                }
                lastPressedButton.set(finalI1);
                turnOrder.set(finalI);
            });
            buttonContainer.add(buttons[i]);
        }
        return buttonContainer;
    }

    /**
     * Creates a JOptionPane to inform the player about the available moves in that game phase
     * and then set clickable the entrance of the player dashboard and/or the character cards.
     * @param expertMode the game mode boolean used to decide whether to set the character cards clickable.
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

    /**
     * Permits moving mother nature when it's possible (ACTION_PHASE.MOVE_MOTHER_NATURE).
     */
    public void showMNMovement() { gameScreenPanel.getTableCenterPanel().setMNClickable(getViewObserverList()); }

    /**
     * Permits picking students from a cloud when it's possible (ACTION_PHASE.CHOOSE_CLOUD)
     */
    public void showCloudChoice() {
        gameScreenPanel.getTableCenterPanel().setCloudsClickable();
        turnStarted = false;
    }

    /**
     * Informs the user about the Character Card he played and activates various listeners according to the Character Card.
     * @param cc is the name of the Character Card played.
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
                int chosenColor = showColorForm("Which color you don't want to consider for influence calculation in this turn?");
                notifyObserver(obs -> obs.onAtomicEffect(chosenColor));
            }
            case THIEF -> {
                message.append("\nChoose a type of Student: every player (including yourself) must return 3 Students of that type from their Dining Room to the bag.\nIf any player has fewer than 3 Students of that type, return as many Students as they have.");
                JOptionPane.showMessageDialog(f, message, "Character Card activated", JOptionPane.PLAIN_MESSAGE);
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
                if (spoiledPrincessPanel != null)
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
                if (monkPanel != null)
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
                    if (jesterPanel != null)
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
     * Updates the game screen panel according to the changes occurred in the modelStorage.
     * The GuiDrawer reads the changes Array List in the modelStorage in order to know which component to update.
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
                    else
                        gameScreenPanel.getTableCenterPanel().updateFillClouds();
                }

                case CLOUD_CHANGED -> gameScreenPanel.getTableCenterPanel().updateCloud(modelChanges.getCloudID());

                case DISCARDPILE_CHANGED -> gameScreenPanel.getTableCenterPanel().updateAssistCard(modelChanges.getPlayingID());

                case ENTRANCE_CHANGED -> gameScreenPanel.updateEntrance(modelChanges.getPlayingID());

                case STUDENTDINING_CHANGED -> gameScreenPanel.updateStudentDinings();

                case PROFDINING_CHANGED -> gameScreenPanel.updateProfessors();

                case COINS_CHANGED -> gameScreenPanel.getTableCenterPanel().updateCoins(modelChanges.getPlayingID());

                case TOWERSTORAGE_CHANGED -> gameScreenPanel.updateTowerStorages();

                case ISLE_CHANGED -> gameScreenPanel.getTableCenterPanel().updateIsle(modelChanges.getIsleID());

                case ISLELAYOUT_CHANGED -> {
                    if (modelChanges.isLayoutChanged())
                        for(int j=0;j<modelChanges.getIslesToRemove();j++)
                            gameScreenPanel.getTableCenterPanel().updateIsleLayout();
                    for(int j=0;j<modelStorage.getGameTable().getIsles().size();j++)
                        gameScreenPanel.getTableCenterPanel().updateIsle(j);
                    modelChanges.setLayoutChanged(false);
                }
                case CHARACTERCARD_CHANGED -> {
                    for(CharacterPanel characterPanel:gameScreenPanel.getTableCenterPanel().getCharacterPanels()){
                        characterPanel.resetCharacter();
                        characterPanel.initializeCharacter();
                    }
                }
                case GNRLRESERVE_CHANGED -> gameScreenPanel.getTableCenterPanel().updateGeneralReserve();
            }
        }
        modelChanges.getToUpdate().clear();
    }

    /**
     * Prints a message at the top of the screen which informs the user about the phase that is being played.
     * @param message the text to display.
     */
    public void showServiceMessage(String message) { gameScreenPanel.setMessage(message); }

    /**
     * Adds a logout button at the top of the screen.
     * When pressed it will open a confirmation option pane.
     */
    public void addLogoutButton() {
        JButton logout = new JButton();
        logout.setText("LOGOUT");
        logout.setBackground(Color.RED);
        logout.setPreferredSize(logoutButtonDimension);
        logout.addActionListener(e -> logoutConfirmation());
        gameScreenPanel.getTextContainerPanel().add(logout,BorderLayout.LINE_END);
    }

    /**
     * Asks the player if he is sure to log out.
     * If the answer is yes then it will notify the ClientController that starts the logout process,
     * else the player will be able to continue the game.
     */
    public void logoutConfirmation() {
        JButton yes = new JButton();
        JButton no = new JButton();
        yes.setText("Yes");
        no.setText("No");
        yes.addActionListener(e -> notifyObserver(ViewObserver::onLogout));
        no.addActionListener(e -> {});
        Object[] options = { yes, "No" };
        JOptionPane.showOptionDialog(null,"Are you sure you want to log out?", "Logout Request",
                                        JOptionPane.DEFAULT_OPTION,JOptionPane.WARNING_MESSAGE,null,options,options[0]);
    }

    /**
     * Pops up a JOptionPane which tells the user that he has to repeat the last action.
     * @param serviceMessage the KO to display
     */
    public void showKOMessage(String serviceMessage) {
        if (reactivateCharacters(serviceMessage))
            gameScreenPanel.setCharactersClickable(modelChanges.getPlayerID());
        JOptionPane.showMessageDialog(f,serviceMessage,"Service message",JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * Checks if it is necessary to set Character Cards clickable again after a KO.
     * @param serviceMessage the message to check
     * @return true if the KO message has been received immediately after a listener set Character Cards as non-clickable.
     */
    private boolean reactivateCharacters(String serviceMessage) {
        return serviceMessage.equals("You don't have enough money to activate this card, please select another one") ||
               serviceMessage.equals("This cloud is empty! Please select another one") ||
               serviceMessage.equals("There's no deny cards left on GRANDMA_HERBS, you can't activate this card") ||
               serviceMessage.equals("You don't have students in your dining room, you can't activate this card");
    }

    /**
     * Opens a JOptionPanel to select a color. It is used for FUNGIST and THIEF Character Cards.
     * @param colorQuestion the question to ask.
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
                checkedStudentsImagesLoaderInt(finalI,buttons);
                if (colorChoice.get()!=-1 && colorChoice.get()!=finalI)
                    studentsImagesLoaderInt(colorChoice,buttons);
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
                loadImages(buttons, finalI, url1);

                if (exchangeChoice.get() != -1 && exchangeChoice.get() != finalI) {
                    ClassLoader cl2 = this.getClass().getClassLoader();
                    InputStream url2;
                    if (exchangeChoice.get() == 0) {
                        url2 = cl2.getResourceAsStream("Raw/Check.png");
                    } else {
                        url2 = cl2.getResourceAsStream("Raw/RedCross.png");
                    }
                    loadImagesOnLastPressedButton(buttons, exchangeChoice, url2);
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

    /**
     * Shows on screen the notification that an error has occurred and that the player will be disconnected.
     * @param serviceMessage contains the reason why the disconnection occurred.
     */
    public void showErrorMessage(String serviceMessage) { JOptionPane.showMessageDialog(f, serviceMessage, "Disconnection occurred", JOptionPane.ERROR_MESSAGE); }

    public ModelStorage getModelStorage() {
        return modelStorage;
    }

    public void setModelStorage(ModelStorage modelStorage) {
        this.modelStorage = modelStorage;
        modelChanges = modelStorage.getModelChanges();
    }

    /**
     * This method shows on screen a dialog window notifying who has won the game
     * @param winner the String representing the name of the winning player
     */
    public void showWinner(String winner) {
        String message=winner+" won the game!";
        JOptionPane.showMessageDialog(f,message,"Winner",JOptionPane.PLAIN_MESSAGE);
        f.dispose();
        System.exit(1);
    }

    public JPanel getUserInputPanel() {
        return userInputPanel;
    }
}
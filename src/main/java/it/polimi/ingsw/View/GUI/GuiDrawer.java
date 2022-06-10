package it.polimi.ingsw.View.GUI;

import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Observer.ViewSubject;
import it.polimi.ingsw.View.GUI.Buttons.AssistantCardButton;
import it.polimi.ingsw.View.StorageOfModelInformation.ModelStorage;

import javax.swing.*;
import java.awt.*;


public class GuiDrawer extends ViewSubject {

    ModelStorage modelStorage;
    Game game;

    //create the window
    private final String frameTitle="Eriantys Game";
    private final String submitButton="Submit";
    /**
     * the frame containing all the GUI
     */
    JFrame f=new JFrame(frameTitle);
    /**
     * the panel for the user inputs
     */
    JPanel serverPreferences=new JPanel();
    /**
     * the panel for the user inputs
     */
    JPanel userPreferences=new JPanel();
    /**
     * the user inputs panel container, it switches between the two on successful submit button press
     */
    JPanel userInputPanelManager=new JPanel(new CardLayout());
    /**
     * the general panel manager switches between the screen where the user will submit his server and game preferences
     * and the screen where the actual game will take place
     */
    JPanel generalPanelManager=new JPanel(new CardLayout());
    /**
     * Text field for server IP input
     */
    JTextField serverIPInputText=new JTextField();
    /**
     * Text field for server Port input
     */
    JTextField serverPortInputText=new JTextField();
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
     * submit button used to enter the text choices made by the player
     */
    JButton submit=new JButton(submitButton);
    /**
     * on this button press the player will join a lobby and wait for the game to start
     */
    JButton startGame=new JButton("Start game");
    /**
     * panel where the actual game will take place
     */
    GameScreenPanel gameScreenPanel;

    public GuiDrawer(Game game) throws HeadlessException {
        //to change
        this.game=game;

        //screen frame initialization
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setExtendedState(JFrame.MAXIMIZED_BOTH);
        f.setVisible(true);
        final int screenDimensionX = f.getWidth();
        final int screenDimensionY = f.getHeight();
        //set my initial content pane where I add my user input manager
        InitialBackgroundPanel contentPane =new InitialBackgroundPanel(new BorderLayout(),screenDimensionX,screenDimensionY
        );
        contentPane.add(userInputPanelManager,BorderLayout.CENTER);
        //add it to the general manager
        generalPanelManager.add(contentPane,"User Input");
        //add my general panel manager as my content pane to switch between user input screen and the actual game
        f.setContentPane(generalPanelManager);

        //setting correct dimensions for text fields
        serverIPInputText.setMaximumSize(new Dimension(700,25));
        serverPortInputText.setMaximumSize(new Dimension(700,25));
        usernameInputText.setMaximumSize(new Dimension(700,25));
        gameModeInputText.setMaximumSize(new Dimension(700,25));
        numberPlayersInput.setMaximumSize(new Dimension(700,25));
        //initialize the container
        userInputPanelManager.add(serverPreferences,"Server parameters choice");
        userInputPanelManager.add(userPreferences,"Game preferences choice");

        ShowServerPreferencesForm();


    }

    public void ShowServerPreferencesForm(){

        //create the area where it wll be shown the title and the server input forms
        serverPreferences.setLayout(new BoxLayout(serverPreferences,BoxLayout.PAGE_AXIS));
        String serverPreferencesTitle = "WELCOME TO ERIANTYS";
        serverPreferences.add(new JLabel(serverPreferencesTitle));
        //an invisible separator
        serverPreferences.add(Box.createRigidArea(new Dimension(0,10)));
        //add the server form
        serverPreferences.add(new JLabel("Insert server ip"));
        serverPreferences.add(serverIPInputText);
        serverPreferences.add(new JLabel("Insert server port"));
        serverPreferences.add(serverPortInputText);
        submit.addActionListener(e -> {//on button press

            String serverIp = serverIPInputText.getText();
            String serverPort = serverPortInputText.getText();
            //checking for empty input, commented for debug
            /*
            if (serverIp.equals("") || serverPort.equals("")) {
                    JOptionPane.showMessageDialog(serverPreferences, "Invalid input", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
            }

             */
            //need to notify the observer
            CardLayout cl=(CardLayout) userInputPanelManager.getLayout();
            cl.show(userInputPanelManager,"Game preferences choice");
        });
        serverPreferences.add(submit);

        ShowUserPreferencesForm();

    }

    public void ShowUserPreferencesForm(){
        //same general layout for the user preferences form
        userPreferences.setLayout(new BoxLayout(userPreferences,BoxLayout.PAGE_AXIS));
        String userPreferencesTitle = "Connected to server, input your game preferences";
        userPreferences.add(new JLabel(userPreferencesTitle));

        userPreferences.add(Box.createRigidArea(new Dimension(0,10)));

        userPreferences.add(new JLabel("Insert username"));
        userPreferences.add(usernameInputText);
        userPreferences.add(new JLabel("Insert game mode"));
        userPreferences.add(gameModeInputText);
        userPreferences.add(new JLabel("Insert number of players"));
        userPreferences.add(numberPlayersInput);
        startGame.addActionListener(e -> {
            //check for empty input, commented for faster debug
            /*
            if(gameModeInputText.getText().equals("") || usernameInputText.getText().equals("") || numberPlayersInput.getText().equals("")){
                JOptionPane.showMessageDialog(serverPreferences, "Invalid input", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
             */
            boolean gamemode;
            gamemode= gameModeInputText.getText().equals("Expert");
            notifyObserver(obs->obs.onUsername(usernameInputText.getText()));
            notifyObserver(obs->obs.onGamePreferences(Integer.parseInt(numberPlayersInput.getText()),gamemode));
            //change window
            ShowGameScreen();     // it will be called by the client handler1
        });
        userPreferences.add(startGame);
    }

    public void ShowGameScreen(){
        gameScreenPanel=new GameScreenPanel(new GridBagLayout(),game,f.getWidth(),f.getHeight());
        generalPanelManager.add(gameScreenPanel,"Game Screen");
        //switch to the actual game screen
        CardLayout cl=(CardLayout) generalPanelManager.getLayout();
        cl.show(generalPanelManager,"Game Screen");
    }

    public int ShowAssistantCardForm(int playerID) {
        JPanel buttonContainer = new JPanel();
        JButton[] buttons = new JButton[game.getPlayerByIndex(playerID).getMageDeck().size()];
        int finalI1 = 0;
        for (int i = 0; i < game.getPlayerByIndex(playerID).getMageDeck().size(); i++) {
            buttons[i] = (new AssistantCardButton(game.getPlayerByIndex(playerID).getMageDeck().get(i).getTurnOrder()));
            //idk what the hell is going on with these variables
            int finalI = i;
            finalI1 = i;
            int finalI2 = finalI1;
            buttons[i].addActionListener(e -> {
                gameScreenPanel.tableCenterPanel.UpdateAssistCard(playerID, game.getPlayerByIndex(playerID).getMageDeck().get(finalI2).getTurnOrder());
            });
            buttonContainer.add(buttons[i]);
        }
        JScrollPane scrollPane = new JScrollPane(buttonContainer);
        scrollPane.setPreferredSize(new Dimension(1000, 600));
        JOptionPane.showMessageDialog(f, scrollPane, "Play Assistant Card", JOptionPane.PLAIN_MESSAGE);
        return game.getPlayerByIndex(playerID).getMageDeck().get(finalI1).getTurnOrder();
    }

    public void SetMovableOptions(boolean gamemode, int playerID){
        JOptionPane.showMessageDialog(f,"Now you can move a student by clicking on the entrance of your dashboard\n" +
                "and then clicking on the isle where you want to move the student or your dining room");
        gameScreenPanel.setClickableStudents(playerID);
        if(gamemode){
            gameScreenPanel.setClickableCharacters();
            JOptionPane.showMessageDialog(f,"Since you are playing in Expert mode,\n you can also click on a character card to" +
                    "activate his effect, given you have enough coins");
        }
    }

    public ModelStorage getModelStorage() {
        return modelStorage;
    }

    public void setModelStorage(ModelStorage modelStorage) {
        this.modelStorage = modelStorage;
    }
}

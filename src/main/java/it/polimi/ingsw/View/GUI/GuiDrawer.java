package it.polimi.ingsw.View.GUI;

import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Observer.ViewSubject;
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
    JTextField usernameInputText =new JTextField();
    JTextField gameModeInputText =new JTextField();
    JTextField numberPlayersInput=new JTextField();
    /**
     * submit button used to enter the text choices made by the player
     */
    JButton submit=new JButton(submitButton);
    /**
     * on this button press the player will join a lobby and wait for the game to start
     */
    JButton startGame=new JButton("Start game");

    public GuiDrawer(Game game) throws HeadlessException {
        //to change
        this.game=game;

        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setExtendedState(JFrame.MAXIMIZED_BOTH);
        f.setVisible(true);
        //set my initial content pane where I add my user input manager
        InitialBackgroundPanel contentPane =new InitialBackgroundPanel(new BorderLayout());
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

        DrawUserSettingsForm();

        // f.pack();

    }

    private void DrawUserSettingsForm(){

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
        submit.addActionListener(e -> {
            String serverIp= serverIPInputText.getText();
            String serverPort= serverPortInputText.getText();
            System.out.println(serverIp+" "+serverPort);
            //notify the observer
            CardLayout cl=(CardLayout) userInputPanelManager.getLayout();
            cl.show(userInputPanelManager,"Game preferences choice");
        });
        serverPreferences.add(submit);

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
            //notifyObserver
            boolean gamemode;
            gamemode= gameModeInputText.getText().equals("Expert");
            notifyObserver(obs->obs.onUsername(usernameInputText.getText()));
            notifyObserver(obs->obs.onGamePreferences(Integer.parseInt(numberPlayersInput.getText()),gamemode));
            //change window

            GameScreenDrawer();     // it will be called by the client handler1
        });
        userPreferences.add(startGame);


    }

    private void GameScreenDrawer(){
        GameScreenPanel gameScreenPanel=new GameScreenPanel(new GridLayout(2,2,400,400),game);
        generalPanelManager.add(gameScreenPanel,"Game Screen");
        //switch to the actual game screen
        CardLayout cl=(CardLayout) generalPanelManager.getLayout();
        cl.show(generalPanelManager,"Game Screen");
    }

    public ModelStorage getModelStorage() {
        return modelStorage;
    }

    public void setModelStorage(ModelStorage modelStorage) {
        this.modelStorage = modelStorage;
    }
}

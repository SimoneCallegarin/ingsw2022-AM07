package it.polimi.ingsw.View.GUI;

import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.Observer.ViewSubject;
import it.polimi.ingsw.View.GUI.Buttons.AssistantCardButton;
import it.polimi.ingsw.View.StorageOfModelInformation.ModelStorage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


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
     * this method initialize the firt screen when you open the app where you'll be asked the username, the game mode and the
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

    public void ShowGameScreen(){
        gameScreenPanel=new GameScreenPanel(new GridBagLayout(),modelStorage,f.getWidth(),f.getHeight());
        generalPanelManager.add(gameScreenPanel,"Game Screen");
        //switch to the actual game screen
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

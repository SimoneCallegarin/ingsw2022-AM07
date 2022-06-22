package it.polimi.ingsw.View.GUI;

import it.polimi.ingsw.Observer.Subjects.ViewSubject;
import it.polimi.ingsw.View.GUI.Buttons.AssistantCardButton;
import it.polimi.ingsw.View.StorageOfModelInformation.ModelChanges;
import it.polimi.ingsw.View.StorageOfModelInformation.ModelStorage;

import javax.swing.*;
import java.awt.*;
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


    ModelChanges modelChanges;

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
        JPanel buttonContainer = new JPanel();
        JButton[] buttons = new JButton[modelStorage.getDashboard(playerID).getAssistantCardsMNMovement().size()];
        for (int i = 0; i < modelStorage.getDashboard(playerID).getAssistantCardsMNMovement().size(); i++) {
            buttons[i] = (new AssistantCardButton(modelStorage.getDashboard(playerID).getAssistantCardsTurnOrder().get(i)));
            //idk what the hell is going on with these variables
            int finalI = i+1;
            buttons[i].addActionListener(e -> {
                //gameScreenPanel.tableCenterPanel.updateAllAssistCard();
                turnOrder.set(finalI);

            });
            buttonContainer.add(buttons[i]);
        }
        JScrollPane scrollPane = new JScrollPane(buttonContainer);
        scrollPane.setPreferredSize(new Dimension(600, 400));
        JOptionPane.showMessageDialog(f, scrollPane, "Play Assistant Card", JOptionPane.PLAIN_MESSAGE);
        return turnOrder.get();
    }

    /**
     * this method create a JOptionPane to inform the player about the available moves in that game phase and then set clickable the entrance of the
     * player dashboard and/or the character cards
     * @param expertMode the game mode boolean used to decide whether to set the character cards clickable
     */
    public void showMoveOptions(boolean expertMode){
        StringBuilder message=new StringBuilder("Now you can move a student from your entrance to your dining room or to an isle of your preference" +
                " by clicking on a student in your entrance and then on the isle/dining room.");
        if(expertMode){
            message.append("\nSince you are playing on Expert Mode you can also click on a character card to activate its effect");
            gameScreenPanel.tableCenterPanel.setClickableCharacters(getViewObserverList());
        }
        JOptionPane.showMessageDialog(f,message,"Choose your move",JOptionPane.PLAIN_MESSAGE);

        gameScreenPanel.setClickableStudents(modelChanges.getPlayerID(),getViewObserverList());
    }

    public void showMNMovement(){
        String message="Now you can move mother nature by clicking on the island where you want to move her";
        JOptionPane.showMessageDialog(f,message,"Move mother nature",JOptionPane.PLAIN_MESSAGE);

        gameScreenPanel.tableCenterPanel.setMNClickable(getViewObserverList());
    }


    /**
     * this method update the gamescreen panel according to the changes occurred in the modelStorage. The GuiDrawer reads the changes arraylist
     * in the modelStorage to correctly know which component to update.
     */
    public void updateGameScreenPanel(){
        boolean gameStart=true;

        for(int i=0;i<modelChanges.getToUpdate().size();i++){
            switch(modelChanges.getToUpdate().get(i)){
                case CLOUDS_CHANGED -> {
                    if(gameStart) {
                        createGameScreen();
                        showGameScreen();
                        gameStart=false;
                    }
                    else {
                        //update with cloud update
                    }

                }
                case DISCARDPILE_CHANGED ->{
                    SwingWorker<Void,Void> swingWorker= new SwingWorker<>() {
                        @Override
                        protected Void doInBackground() {
                            gameScreenPanel.tableCenterPanel.updateAssistCard(modelChanges.getPlayingID());
                            return null;
                        }
                    };
                    swingWorker.execute();
                }
                //gameScreenPanel.tableCenterPanel.updateAssistCard(modelChanges.getPlayingID());
                case ENTRANCE_CHANGED ->{
                    SwingWorker<Void,Void> swingWorker= new SwingWorker<>() {
                        @Override
                        protected Void doInBackground() {
                            gameScreenPanel.updateEntrance(modelChanges.getPlayingID());
                            return null;
                        }
                    };
                    swingWorker.execute();
                }
                //gameScreenPanel.updateEntrance(modelChanges.getPlayingID());
                case STUDENTDINING_CHANGED ->{
                    SwingWorker<Void,Void> swingWorker= new SwingWorker<>() {
                        @Override
                        protected Void doInBackground() {
                            gameScreenPanel.updateStudentDinings(modelChanges.getPlayingID());
                            return null;
                        }
                    };
                    swingWorker.execute();
                }
                //gameScreenPanel.updateStudentDinings(modelChanges.getPlayingID());
                case PROFDINING_CHANGED ->{
                    SwingWorker<Void,Void> swingWorker= new SwingWorker<>() {
                        @Override
                        protected Void doInBackground() {
                            gameScreenPanel.updateProfessors(modelChanges.getPlayingID());
                            return null;
                        }
                    };
                    swingWorker.execute();
                }
                //gameScreenPanel.updateProfessors(modelChanges.getPlayingID());
                case COINS_CHANGED ->{
                    SwingWorker<Void,Void> swingWorker= new SwingWorker<>() {
                        @Override
                        protected Void doInBackground() {
                            gameScreenPanel.tableCenterPanel.updateCoins(modelChanges.getPlayingID());
                            return null;
                        }
                    };
                    swingWorker.execute();
                }
                //gameScreenPanel.tableCenterPanel.updateCoins();
                case TOWERSTORAGE_CHANGED ->{
                    SwingWorker<Void,Void> swingWorker= new SwingWorker<>() {
                        @Override
                        protected Void doInBackground() {
                            gameScreenPanel.updateTowerStorages(modelChanges.getPlayingID());
                            return null;
                        }
                    };
                    swingWorker.execute();
                }
                //gameScreenPanel.updateTowerStorages(modelChanges.getPlayingID());
                case ISLE_CHANGED ->{
                    SwingWorker<Void,Void> swingWorker= new SwingWorker<>() {
                        @Override
                        protected Void doInBackground() {
                            gameScreenPanel.tableCenterPanel.updateIsle(modelChanges.getIsleID());
                            return null;
                        }
                    };
                    swingWorker.execute();
                }
                //gameScreenPanel.tableCenterPanel.updateIsle(modelChanges.getIsleIndex());
                case ISLELAYOUT_CHANGED ->{
                    SwingWorker<Void,Void> swingWorker= new SwingWorker<>() {
                        @Override
                        protected Void doInBackground() {
                            gameScreenPanel.tableCenterPanel.updateIsleLayout();
                            return null;
                        }
                    };
                    swingWorker.execute();
                }
                //gameScreenPanel.tableCenterPanel.updateIsleLayout();
                //missing case character card and cloud changes
            }
        }
        modelChanges.getToUpdate().clear();
    }

    public void showServiceMessage(String serviceMessage){
        JOptionPane.showMessageDialog(f,serviceMessage,"Service information",JOptionPane.PLAIN_MESSAGE);
    }

    public ModelStorage getModelStorage() {
        return modelStorage;
    }

    public void setModelStorage(ModelStorage modelStorage) {
        this.modelStorage = modelStorage;
        modelChanges=modelStorage.getModelChanges();
    }
}

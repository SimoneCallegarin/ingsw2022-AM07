package it.polimi.ingsw.View.GUI;

import it.polimi.ingsw.Observer.ViewSubject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;


public class GuiDrawer extends ViewSubject {
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
    JPanel userInputContainer=new JPanel(new CardLayout());
    /**
     * Text field for server IP input
     */
    JTextField serverIPInputText=new JTextField();
    /**
     * Text field for server Port input
     */
    JTextField serverPortInputText=new JTextField();

    JTextField usernameInput=new JTextField();
    JTextField gamemodeInput=new JTextField();
    JTextField numberPlayersInput=new JTextField();

    /**
     * submit button used to enter the text choices made by the player
     */
    JButton submit=new JButton(submitButton);
    /**
     * on this button press the player will join a lobby and wait for the game to start
     */
    JButton startGame=new JButton("Start game");

    public GuiDrawer() throws HeadlessException {

        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(1920,1080);
        f.setVisible(true);

        //set my content pane where I'll add my components
        JPanel contentPane=new JPanel();
        contentPane.setBackground(Color.blue);
        contentPane.setLayout(new BorderLayout());
        //aligning the inputPane in the center
        contentPane.add(Box.createRigidArea(new Dimension(600,300)),BorderLayout.WEST);
        contentPane.add(Box.createRigidArea(new Dimension(600,300)),BorderLayout.EAST);
        contentPane.add(Box.createRigidArea(new Dimension(500,340)),BorderLayout.NORTH);
        contentPane.add(Box.createRigidArea(new Dimension(500,340)),BorderLayout.SOUTH);

        f.setContentPane(contentPane);

        //setting correct dimensions for text fields
        serverIPInputText.setMaximumSize(new Dimension(700,25));
        serverPortInputText.setMaximumSize(new Dimension(700,25));
        usernameInput.setMaximumSize(new Dimension(700,25));
        gamemodeInput.setMaximumSize(new Dimension(700,25));
        numberPlayersInput.setMaximumSize(new Dimension(700,25));

        //initialize the container
        userInputContainer.add(serverPreferences,"Server parameters choice");
        userInputContainer.add(userPreferences,"Game preferences choice");


        DrawServerSettingsForm();

    }

    private void DrawServerSettingsForm(){

        //create the area where i'll show the title and the server input forms
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
            CardLayout cl=(CardLayout) userInputContainer.getLayout();
            cl.show(userInputContainer,"Game preferences choice");
        });
        serverPreferences.add(submit);

        userPreferences.setLayout(new BoxLayout(userPreferences,BoxLayout.PAGE_AXIS));
        String userPreferencesTitle = "Connected to server, input your game preferences";
        userPreferences.add(new JLabel(userPreferencesTitle));

        userPreferences.add(Box.createRigidArea(new Dimension(0,10)));

        userPreferences.add(new JLabel("Insert username"));
        userPreferences.add(usernameInput);
        userPreferences.add(new JLabel("Insert gamemode"));
        userPreferences.add(gamemodeInput);
        userPreferences.add(new JLabel("Insert number of players"));
        userPreferences.add(numberPlayersInput);
        startGame.addActionListener(e -> {
            //notifyObserver
            boolean gamemode;
            if(gamemodeInput.getText().equals("Expert")){
                gamemode=true;
            }else{gamemode=false;}

            notifyObserver(obs->obs.onUsername(usernameInput.getText()));
            notifyObserver(obs->obs.onGamePreferences(Integer.parseInt(numberPlayersInput.getText()),gamemode));
            //change window

        });
        userPreferences.add(startGame);


        //putting all together
        f.getContentPane().add(userInputContainer,BorderLayout.CENTER);
    }


}

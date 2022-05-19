package it.polimi.ingsw.View.GUI;

import it.polimi.ingsw.Observer.ViewSubject;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GuiDrawer extends ViewSubject implements MouseListener {
    //create the window
    private final String frameTitle="Eriantys Game";
    private final String inputPanelTitle="WELCOME TO ERIANTYS";
    private final String submitButton="Submit";

    JFrame f=new JFrame(frameTitle);
    JPanel inputPanel=new JPanel();
    JTextField serverIPInput=new JTextField();
    JTextField serverPortInput=new JTextField();
    JTextField usernameInput=new JTextField();
    JTextField gamemodeInput=new JTextField();
    JTextField numberPlayersInput=new JTextField();

    JButton submit=new JButton(submitButton);

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
        contentPane.add(Box.createRigidArea(new Dimension(500,380)),BorderLayout.SOUTH);

        f.setContentPane(contentPane);

        DrawServerSettingsForm();

    }

    private void DrawServerSettingsForm(){
        //create the area where i'll show the title and the input forms

        inputPanel.setLayout(new BoxLayout(inputPanel,BoxLayout.PAGE_AXIS));

        inputPanel.add(new JLabel(inputPanelTitle));
        //an invisible separator
        inputPanel.add(Box.createRigidArea(new Dimension(0,10)));
        //add the form
        inputPanel.add(new JLabel("Insert server ip"));
        inputPanel.add(serverIPInput);
        inputPanel.add(new JLabel("Insert server port"));
        inputPanel.add(serverPortInput);
        submit.addMouseListener(this);
        inputPanel.add(submit);

        //putting all together
        f.getContentPane().add(inputPanel,BorderLayout.CENTER);
    }

    private void DrawUserPreferencesForm(){
        //f.getContentPane().removeAll();
        //inputPanel.setLayout(new BoxLayout(inputPanel,BoxLayout.PAGE_AXIS));


        inputPanel.add(new JLabel("Game joined"));
        inputPanel.add(Box.createRigidArea(new Dimension(0,10)));
        inputPanel.add(new JLabel("Insert username"));
        inputPanel.add(usernameInput);
        inputPanel.add(new JLabel("Insert gamemode[Expert/Base]"));
        inputPanel.add(gamemodeInput);
        inputPanel.add(new JLabel("Insert number of players[2,3,4]"));
        inputPanel.add(numberPlayersInput);
        submit.addMouseListener(this);
        inputPanel.add(submit);

        f.getContentPane().add(inputPanel,BorderLayout.CENTER);


    }


    @Override
    public void mouseClicked(MouseEvent e) {
       String serverIp= serverIPInput.getText();
       String serverPort= serverPortInput.getText();
       System.out.println(serverIp+" "+serverPort);
       DrawUserPreferencesForm();
       //notify the observer


    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}

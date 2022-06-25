package it.polimi.ingsw.View.GUI;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.*;

/**
 * Loading screen that correspond to the waiting room where players wait for the game to start.
 */
public class LoadingScreen extends JPanel {

    /**
     * Color chooser for the nickname color.
     */
    private final JColorChooser colorChooser;
    /**
     * Label containing the nickname previously selected by the player.
     */
    private final JLabel nickname;
    /**
     * Color selected by the player for his nickname.
     */
    private Color selectedColor;

    /**
     * LoadingScreen constructor.
     * @param yourNickname the nickname of the player that is playing on this GUI
     */
    public LoadingScreen(String yourNickname) {
        super(new BorderLayout());

        JPanel bannerPanel = new JPanel(new BorderLayout());
        JLabel waitingRoom = new JLabel("Welcome in the waiting room, the game will start soon...", JLabel.CENTER);
        waitingRoom.setForeground(Color.BLACK);
        waitingRoom.setOpaque(true);
        waitingRoom.setFont(new Font("SansSerif", Font.BOLD, 24));
        waitingRoom.setPreferredSize(new Dimension(100, 65));
        bannerPanel.add(waitingRoom, BorderLayout.CENTER);

        JPanel previewPanel = new JPanel(new BorderLayout());
        nickname = new JLabel(yourNickname,JLabel.CENTER);
        nickname.setForeground(Color.BLACK);
        nickname.setBackground(Color.CYAN);
        nickname.setOpaque(true);
        nickname.setFont(new Font("SansSerif", Font.BOLD, 24));
        nickname.setPreferredSize(new Dimension(420, 65));
        previewPanel.add(nickname, BorderLayout.CENTER);

        colorChooser = new JColorChooser(nickname.getForeground());
        colorChooser.getSelectionModel().addChangeListener(this::stateChanged);
        colorChooser.setPreviewPanel(previewPanel);
        colorChooser.getSelectionModel().addChangeListener(this::stateChanged);
        colorChooser.setBorder(BorderFactory.createTitledBorder("Choose Text Color for your nickname"));

        add(waitingRoom, BorderLayout.CENTER);
        add(colorChooser, BorderLayout.PAGE_END);
    }

    /**
     * Permits to change the color of the nickname with the color chosen in the ColorChooser.
     * @param e the event happening (color selection).
     */
    public void stateChanged(ChangeEvent e) {
        Color newColor = colorChooser.getColor();
        nickname.setForeground(newColor);
        selectedColor = newColor;
    }

    /**
     * Getter method for the color of the nickname.
     * @return the color of the nickname chosen.
     */
    public Color getNicknameColor() { return selectedColor;}

}

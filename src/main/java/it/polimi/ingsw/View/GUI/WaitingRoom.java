package it.polimi.ingsw.View.GUI;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.*;

import static it.polimi.ingsw.View.GUI.GUIConstants.*;

/**
 * Loading screen that correspond to the waiting room where players wait for the game to start.
 */
class WaitingRoom extends JPanel {

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
    public WaitingRoom(String yourNickname) {
        super(new BorderLayout());

        JLabel waitingRoom = waitingRoomBanner();
        nickname = waitingRoomNickname(yourNickname);
        colorChooser = waitingRoomColorChooser();

        add(waitingRoom, BorderLayout.CENTER);
        add(colorChooser, BorderLayout.PAGE_END);
    }

    /**
     * Creates the banner containing the waiting room message.
     * @return the label of the waiting room message.
     */
    private JLabel waitingRoomBanner() {
        JLabel waitingRoom = new JLabel("Welcome in the waiting room, the game will start soon...", JLabel.CENTER);
        waitingRoom.setForeground(Color.BLACK);
        waitingRoom.setOpaque(true);
        waitingRoom.setFont(waitingRoomFont);
        waitingRoom.setPreferredSize(waitingRoomDimension);
        return waitingRoom;
    }

    /**
     * Creates the label for the nickname used in the waiting room.
     * @param yourNickname the nickname that was chosen by the player.
     * @return the label containing the nickname chosen by the player but colored.
     */
    private JLabel waitingRoomNickname(String yourNickname) {
        JLabel nickname = new JLabel(yourNickname,JLabel.CENTER);
        nickname.setForeground(Color.BLACK);
        nickname.setBackground(Color.CYAN);
        nickname.setOpaque(true);
        nickname.setFont(waitingRoomNicknameFont);
        nickname.setPreferredSize(waitingRoomNicknameDimension);
        return nickname;
    }

    /**
     * Creates the color chooser used in the waiting room to permit a player that is waiting to join a match
     * to choose a color for his nickname that will be displayed in game.
     * @return the color chooser.
     */
    private JColorChooser waitingRoomColorChooser() {
        JColorChooser colorChooser = new JColorChooser(nickname.getForeground());
        JPanel previewPanel = new JPanel(new BorderLayout());
        previewPanel.add(nickname, BorderLayout.CENTER);
        colorChooser.getSelectionModel().addChangeListener(this::stateChanged);
        colorChooser.setPreviewPanel(previewPanel);
        colorChooser.setBorder(BorderFactory.createTitledBorder("Choose Text Color for your nickname"));
        return colorChooser;
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

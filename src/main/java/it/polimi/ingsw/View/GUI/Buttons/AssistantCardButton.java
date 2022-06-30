package it.polimi.ingsw.View.GUI.Buttons;

import javax.swing.*;

import static it.polimi.ingsw.View.GUI.ImagesLoader.assistantCardImageLoader;

/**
 * Assistant Card button used in the showAssistantCardForm method in the GuiDrawer
 * to let the user choose the assistant card to play.
 */
public class AssistantCardButton extends JButton {

    /**
     * Constructor of AssistantCardButton.
     * @param turnOrder card turn order, used to identify it.
     */
    public AssistantCardButton(int turnOrder) {
        setBorder(BorderFactory.createEmptyBorder());
        setContentAreaFilled(false);
        setIcon(new ImageIcon(assistantCardImageLoader(turnOrder)));
    }

}

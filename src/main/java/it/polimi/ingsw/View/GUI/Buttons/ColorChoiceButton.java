package it.polimi.ingsw.View.GUI.Buttons;

import javax.swing.*;

import static it.polimi.ingsw.View.GUI.ImagesLoader.colorChoiceImageLoader;

/**
 * Button placed in the "choose color" form from the FUNGIST and THIEF effect activation.
 */
public class ColorChoiceButton extends JButton {

    /**
     * Constructor of ColorChoiceButton.
     * @param color integer representing the color of the button.
     */
    public ColorChoiceButton(int color) {
        setBorder(BorderFactory.createEmptyBorder());
        setContentAreaFilled(false);
        setIcon(new ImageIcon(colorChoiceImageLoader(color)));
    }

}

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
        ClassLoader cl = this.getClass().getClassLoader();
        setIcon(new ImageIcon(colorChoiceImageLoader(color,cl)));
    }

}

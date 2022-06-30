package it.polimi.ingsw.View.GUI.Buttons;

import javax.swing.*;

import static it.polimi.ingsw.View.GUI.ImagesLoader.motherNatureImageLoader;

/**
 * Button representing Mother nature.
 */
public class MNButton extends JButton {

    /**
     * Constructor of MNButton.
     */
    public MNButton() {
        setBorder(BorderFactory.createEmptyBorder());
        setContentAreaFilled(false);
        ClassLoader cl = this.getClass().getClassLoader();
        setIcon(new ImageIcon(motherNatureImageLoader(cl)));
    }

}

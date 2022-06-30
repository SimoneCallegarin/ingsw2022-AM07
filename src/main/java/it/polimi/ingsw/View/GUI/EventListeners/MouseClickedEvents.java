package it.polimi.ingsw.View.GUI.EventListeners;

import it.polimi.ingsw.View.GUI.Buttons.StudentButton;

/**
 * Class used to handle the event when a mouse is clicked.
 */
public class MouseClickedEvents {

    /**
     * Returns the color of a student picked from a student button that has been pressed.
     * @param buttonPressed the student button pressed.
     * @return the color of the student button that has been pressed.
     */
    protected static int studentButtonPressed(StudentButton buttonPressed) {
        int colorPressed = -1;
        switch (buttonPressed.getColor()){
            case YELLOW -> colorPressed = 0;
            case PINK -> colorPressed = 1;
            case BLUE -> colorPressed = 2;
            case RED -> colorPressed = 3;
            case GREEN -> colorPressed = 4;
        }
        return colorPressed;
    }

}

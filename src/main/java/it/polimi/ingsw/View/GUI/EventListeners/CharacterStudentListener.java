package it.polimi.ingsw.View.GUI.EventListeners;

import it.polimi.ingsw.Observer.Subjects.ViewSubject;
import it.polimi.ingsw.Observer.ViewObserver;
import it.polimi.ingsw.View.GUI.Buttons.StudentButton;
import it.polimi.ingsw.View.GUI.DashboardPanels.DiningPanel;
import it.polimi.ingsw.View.GUI.DashboardPanels.EntrancePanel;
import it.polimi.ingsw.View.GUI.IslesPanels.TableCenterPanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/**
 * MouseListener added to Character student buttons to listen to a mouse click and notify the view observers of the button color selected
 */
public class CharacterStudentListener extends ViewSubject implements MouseListener {
    /**
     * Entrance panel reference used add listener to the entrance if the played character card is "JESTER"
     */
    private final EntrancePanel entrance;
    /**
     * TableCenterPanel panel reference used add listener to the isles if the played character card is "MONK"
     */
    private final TableCenterPanel tableCenter;
    /**
     * String representing the name of the character played by the user
     */
    private final String character;

    /**
     * Constructor of CharacterStudentListener
     * @param viewObserverList Array list of ViewObservers used to attach to this listener the GUI observers
     * @param tableCenter TableCenterPanel panel reference used add listener to the isles if the played character card is "MONK"
     * @param entrance Entrance panel reference used add listener to the entrance if the played character card is "JESTER"
     * @param character String representing the name of the character played by the user
     */
    public CharacterStudentListener(ArrayList<ViewObserver> viewObserverList, TableCenterPanel tableCenter, EntrancePanel entrance, String character) {
        this.entrance = entrance;
        this.tableCenter=tableCenter;
        this.character = character;
        addAllObservers(viewObserverList);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        StudentButton buttonPressed=(StudentButton) e.getSource();
        int colorPressed=-1;
        switch (buttonPressed.getColor()){
            case YELLOW -> colorPressed=0;
            case PINK -> colorPressed=1;
            case BLUE -> colorPressed=2;
            case RED -> colorPressed=3;
            case GREEN -> colorPressed=4;
        }
        int finalColorPressed = colorPressed;
        buttonPressed.printClick(buttonPressed.getColor());
        notifyObserver(obs->obs.onColorChoice(finalColorPressed));

        switch (character) {
            case "MONK" -> tableCenter.setIslesClickableForEffect(getViewObserverList());
            case "JESTER" -> entrance.setStudentsClickableForEffect();
        }

        tableCenter.removeClickableCharacterStudents();

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

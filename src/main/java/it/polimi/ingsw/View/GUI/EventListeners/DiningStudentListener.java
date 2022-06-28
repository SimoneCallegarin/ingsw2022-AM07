package it.polimi.ingsw.View.GUI.EventListeners;

import it.polimi.ingsw.Observer.Subjects.ViewSubject;
import it.polimi.ingsw.Observer.ViewObserver;
import it.polimi.ingsw.View.GUI.Buttons.StudentButton;

import it.polimi.ingsw.View.GUI.DashboardPanels.DiningStudentsPanel;
import it.polimi.ingsw.View.GUI.DashboardPanels.EntrancePanel;


import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/**
 * Class added to dining student buttons to listen to a mouse click and notify the view observers of the button color selected
 */
public class DiningStudentListener extends ViewSubject implements MouseListener {
    /**
     * EntrancePanel reference used to set the students clickable with the effect listener
     */
    private final EntrancePanel entrance;
    /**
     * DiningStudentsPanel reference used to remove the listeners from its students
     */
    private final DiningStudentsPanel diningStudentsRoom;

    /**
     * Constructor of DiningStudentsPanel
     * @param entrance EntrancePanel reference used to set the students clickable with the effect listener
     * @param viewObserverList Array list of View Observers used to attach to this listener the GUI observers
     * @param diningStudentsRoom DiningStudentsPanel reference used to remove the listeners from its students
     */
    public DiningStudentListener(EntrancePanel entrance, ArrayList<ViewObserver> viewObserverList, DiningStudentsPanel diningStudentsRoom) {
        this.entrance = entrance;
        this.diningStudentsRoom = diningStudentsRoom;
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

        entrance.setStudentsClickableForEffect();
        diningStudentsRoom.removeClickableStudents();

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


package it.polimi.ingsw.View.GUI.EventListeners;

import it.polimi.ingsw.Model.CharacterCards.CharacterCardsName;
import it.polimi.ingsw.Observer.Subjects.ViewSubject;
import it.polimi.ingsw.Observer.ViewObserver;
import it.polimi.ingsw.View.GUI.Buttons.StudentButton;
import it.polimi.ingsw.View.GUI.DashboardPanels.DashboardPanel;
import it.polimi.ingsw.View.GUI.DashboardPanels.DiningStudentsPanel;
import it.polimi.ingsw.View.GUI.DashboardPanels.EntrancePanel;
import it.polimi.ingsw.View.GUI.IslesPanels.TableCenterPanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/**
 * Class added to dining student buttons to listen to a mouse click and notify the view observers of the button color selected
 */
public class DiningStudentListener extends ViewSubject implements MouseListener {

    EntrancePanel entrance;
    DiningStudentsPanel dsp;
    TableCenterPanel tableCenter;
    ArrayList<ViewObserver> observers;

    public DiningStudentListener(EntrancePanel entrance, ArrayList<ViewObserver> viewObserverList,
                                 TableCenterPanel tableCenter, DiningStudentsPanel dsp) {
        this.entrance = entrance;
        this.dsp = dsp;
        this.tableCenter=tableCenter;
        observers=viewObserverList;
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
        dsp.removeClickableStudents();

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


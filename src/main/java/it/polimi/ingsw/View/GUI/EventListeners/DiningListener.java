package it.polimi.ingsw.View.GUI.EventListeners;

import it.polimi.ingsw.Observer.ViewObserver;
import it.polimi.ingsw.Observer.Subjects.ViewSubject;
import it.polimi.ingsw.View.GUI.DashboardPanels.DiningPanel;
import it.polimi.ingsw.View.GUI.DashboardPanels.EntrancePanel;
import it.polimi.ingsw.View.GUI.IslesPanels.TableCenterPanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class DiningListener extends ViewSubject implements MouseListener  {
    DiningPanel dining;
    EntrancePanel entrance;
    TableCenterPanel tableCenterPanel;

    public DiningListener(DiningPanel dining, EntrancePanel entrance, ArrayList<ViewObserver> observersList, TableCenterPanel tableCenterPanel) {
        this.dining = dining;
        this.entrance=entrance;
        this.tableCenterPanel=tableCenterPanel;
        addAllObservers(observersList);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        notifyObserver(ViewObserver::onStudentMovement_toDining);
        dining.removeClickable();
        entrance.removeStudentsClickable();
        tableCenterPanel.removeIslesClickable();
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

package it.polimi.ingsw.View.GUI.EventListeners;

import it.polimi.ingsw.Observer.ViewObserver;
import it.polimi.ingsw.Observer.Subjects.ViewSubject;
import it.polimi.ingsw.View.GUI.DashboardPanels.DashboardPanel;
import it.polimi.ingsw.View.GUI.DashboardPanels.DiningPanel;
import it.polimi.ingsw.View.GUI.DashboardPanels.EntrancePanel;
import it.polimi.ingsw.View.GUI.IslesPanels.TableCenterPanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class IsleListener extends ViewSubject implements MouseListener {

    TableCenterPanel tableCenterPanel;
    EntrancePanel entrance;
    DiningPanel dining;
    int idListened;

    public IsleListener(TableCenterPanel tableCenterPanel, ArrayList<ViewObserver> viewObserverList, EntrancePanel entrance, int idListened, DiningPanel dining) {
        this.tableCenterPanel=tableCenterPanel;
        addAllObservers(viewObserverList);
        this.entrance=entrance;
        this.idListened=idListened;
        this.dining=dining;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        notifyObserver(obs->obs.onStudentMovement_toIsle(idListened));
        tableCenterPanel.removeIslesClickable();
        DashboardPanel dashboardPanel=(DashboardPanel)entrance.getParent();
        dashboardPanel.getDining().removeCLickable();
        entrance.removeClickable();
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

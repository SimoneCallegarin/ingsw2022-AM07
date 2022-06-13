package it.polimi.ingsw.View.GUI.EventListeners;

import it.polimi.ingsw.Observer.ViewObserver;
import it.polimi.ingsw.Observer.ViewSubject;
import it.polimi.ingsw.View.GUI.Buttons.StudentButton;
import it.polimi.ingsw.View.GUI.DashboardPanels.DashboardPanel;
import it.polimi.ingsw.View.GUI.DashboardPanels.EntrancePanel;
import it.polimi.ingsw.View.GUI.IslesPanels.TableCenterPanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/**
 * this class is added to student buttons to listen to a mouse click and notify the view observers of the button color selected
 */
public class EntranceListener extends ViewSubject implements MouseListener  {
    /**
     * this boolean is used to set the dining and the isles clickable only one time and avoid adding multiple listeners to it and sending
     * multiple studentToDining/studentToIsle messages to the server
     */
    static boolean setClickable;
    EntrancePanel entrance;
    TableCenterPanel tableCenter;
    DashboardPanel dashboardListened;
    ArrayList<ViewObserver> observers;

    public EntranceListener(DashboardPanel dashboardListened, ArrayList<ViewObserver> viewObserverList, TableCenterPanel tableCenter, EntrancePanel entrance) {
        this.entrance=entrance;
        this.dashboardListened = dashboardListened;
        this.tableCenter=tableCenter;
        observers=viewObserverList;
        addAllObservers(viewObserverList);
        setClickable =false;
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
        System.out.println(finalColorPressed);
        notifyObserver(obs->obs.onColorChoice(finalColorPressed));
        if(!setClickable) {
            dashboardListened.getDining().setCLickable(observers,tableCenter);//so after at least one student button press the dining room is set clickable
            tableCenter.setIslesClickable(getViewObserverList(),entrance,dashboardListened.getDining());
            setClickable =true;
        }
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

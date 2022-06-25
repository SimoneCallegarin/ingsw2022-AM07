package it.polimi.ingsw.View.GUI.EventListeners;

import it.polimi.ingsw.Model.CharacterCards.CharacterCardsName;
import it.polimi.ingsw.Observer.Subjects.ViewSubject;
import it.polimi.ingsw.Observer.ViewObserver;
import it.polimi.ingsw.View.GUI.Buttons.StudentButton;
import it.polimi.ingsw.View.GUI.DashboardPanels.DashboardPanel;
import it.polimi.ingsw.View.GUI.DashboardPanels.EntrancePanel;
import it.polimi.ingsw.View.GUI.IslesPanels.TableCenterPanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class DiningStudentListener extends ViewSubject implements MouseListener {

    EntrancePanel entrance;
    TableCenterPanel tableCenter;
    DashboardPanel dashboardListened;
    ArrayList<ViewObserver> observers;

    public DiningStudentListener(DashboardPanel dashboardListened, ArrayList<ViewObserver> viewObserverList,
                                 TableCenterPanel tableCenter, EntrancePanel entrance) {
        this.entrance = entrance;
        this.dashboardListened = dashboardListened;
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

        //dashboardListened.getDining().setClickable(observers,tableCenter);

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


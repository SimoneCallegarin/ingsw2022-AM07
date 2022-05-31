package it.polimi.ingsw.View.GUI.EventListeners;

import it.polimi.ingsw.Observer.ViewSubject;
import it.polimi.ingsw.View.GUI.Buttons.StudentButton;
import it.polimi.ingsw.View.GUI.DashboardPanels.DashboardPanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * this class is added to student buttons to listen to a mouse click and notify the view observers of the button color selected
 */
public class EntranceListener extends ViewSubject implements MouseListener  {

    DashboardPanel dashboardListened;

    public EntranceListener(DashboardPanel dashboardListened) {
        this.dashboardListened = dashboardListened;
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
        dashboardListened.getDining().setCLickable();//so after at least one student button press the dining room is set clickable

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

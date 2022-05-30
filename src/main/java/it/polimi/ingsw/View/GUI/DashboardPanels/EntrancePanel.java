package it.polimi.ingsw.View.GUI.DashboardPanels;

import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.View.GUI.Buttons.StudentButton;
import it.polimi.ingsw.View.GUI.EventListeners.ColorListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * this panel is inserted in the Dashboard panel and represents the dashboard entrance. It contains students button.
 * On entering the right phase the setClickable method is called and a ColorListener is added to notify the view observers
 * of the color of the button that has been pressed.
 * When it's not the right phase to move a student, or it's not the player turn the listener are removed so any wrong click it's not counted
 */

public class EntrancePanel extends JPanel{
    GridBagConstraints c;
    Game game;
    int playerID;
    RealmColors colorSelected;


    public EntrancePanel(Game game, int playerID) {
        setLayout(new GridBagLayout());
        c=new GridBagConstraints();
        this.game=game;
        setOpaque(false);
        setBorder(BorderFactory.createLineBorder(Color.black) );
        InitializeEntrance(playerID);

    }

    /**
     * this method initialize the students in the entrance according to the model information
     * @param playerID the player ID used to identify the entrance Dashboard
     */
    private void InitializeEntrance(int playerID){
        c.gridx=0;
        c.gridy=0;
        c.insets=new Insets(0,9,3,7);
        for(RealmColors color:RealmColors.values()){
            for(int i=0;i<game.getPlayerByIndex(playerID).getDashboard().getEntrance().getStudentsByColor(color);i++){
                add(new StudentButton(color),c);
                if(c.gridx==4){
                    c.gridy=1;
                    c.gridx=0;//it has to start from the second cell in the second row
                }
                c.gridx++;
            }
        }
    }

    /**
     * this method is called when the player can move the students in order to listen to clicks on a student
     */
    public void setClickable(){
        for(int i=0;i<this.getComponentCount();i++){
            this.getComponent(i).addMouseListener(new ColorListener((DashboardPanel) this.getParent()));
        }
    }

    /**
     * this method is used to remove the listener from the buttons
     */
    public void removeClickable(){
        for(int i=0;i<this.getComponentCount();i++){
            this.getComponent(i).removeMouseListener(this.getComponent(i).getMouseListeners()[0]);
        }
    }

}

package it.polimi.ingsw.View.GUI.DashboardPanels;

import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.View.GUI.Buttons.StudentButton;

import javax.swing.*;
import java.awt.*;


public class EntrancePanel extends JPanel {
    GridBagConstraints c;
    Game game;

    public EntrancePanel( Game game) {
        setLayout(new GridBagLayout());
        c=new GridBagConstraints();
        this.game=game;
        setOpaque(false);
        setBorder(BorderFactory.createLineBorder(Color.black) );
        InitializeEntrance();

    }

    private void InitializeEntrance(){
        c.gridx=0;
        c.gridy=0;
        c.insets=new Insets(0,9,3,7);
        for(RealmColors color:RealmColors.values()){
            for(int i=0;i<game.getPlayerByIndex(0).getDashboard().getEntrance().getStudentsByColor(color);i++){
                add(new StudentButton(color),c);
                if(c.gridx==4){
                    c.gridy=1;
                    c.gridx=0;//it has to start from the second cell in the second row
                }
                c.gridx++;
            }
        }
    }
}

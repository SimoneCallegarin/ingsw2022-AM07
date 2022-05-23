package it.polimi.ingsw.View.GUI;

import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Model.Game;

import javax.swing.*;
import java.awt.*;


public class EntrancePanel extends JPanel {
    GridBagConstraints c;
    Game game;

    public EntrancePanel(LayoutManager layout,Game game) {
        super(layout);
        c=new GridBagConstraints();
        this.game=game;
        setOpaque(false);
        setBorder(BorderFactory.createLineBorder(Color.black) );
        //loading the images from resource folder
        InitializeEntrance();
    }

    private void InitializeEntrance(){
        int row=0;
        int column=0;
        c.insets=new Insets(10,7,10,7);


        for (RealmColors color:game.getPlayerByIndex(0).getDashboard().getEntrance().getStudents().keySet()) {
            for (int i = 0; i < game.getPlayerByIndex(0).getDashboard().getEntrance().getStudentsByColor(color);i++){
                c.gridx=column;
                c.gridy=row;
                add(new StudentButton(color),c);
                if(column==4){
                    row++;
                    column=1;
                }else {
                    column++;
                }
            }
        }

    }
}

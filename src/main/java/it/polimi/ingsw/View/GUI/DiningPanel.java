package it.polimi.ingsw.View.GUI;

import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Model.Game;

import javax.swing.*;
import java.awt.*;

public class DiningPanel extends JPanel {
    GridBagConstraints c;
    Game game;

    public DiningPanel(LayoutManager layout, Game game) {
        super(layout);
        c=new GridBagConstraints();
        this.game=game;
        setOpaque(false);
        setBorder(BorderFactory.createLineBorder(Color.black) );
        InitializeDining();

    }

    private void InitializeDining() {
        int row=0;
        int column=0;
        for (RealmColors color : game.getPlayerByIndex(0).getDashboard().getDiningRoom().getStudents().keySet()) {
            for (int i = 0; i < game.getPlayerByIndex(0).getDashboard().getDiningRoom().getStudentsByColor(color); i++) {
                c.gridx=column;
                c.gridy=row;
                add(new StudentButton(color));
                row++;
            }
            row=0;
            column++;
        }
    }
}

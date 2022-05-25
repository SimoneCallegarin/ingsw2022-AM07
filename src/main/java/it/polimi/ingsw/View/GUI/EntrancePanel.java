package it.polimi.ingsw.View.GUI;

import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Model.Game;

import javax.swing.*;
import javax.swing.border.Border;
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

    }
}

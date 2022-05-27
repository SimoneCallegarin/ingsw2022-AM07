package it.polimi.ingsw.View.GUI.DashboardPanels;

import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.View.GUI.Buttons.StudentButton;

import javax.swing.*;
import java.awt.*;

public class DiningStudentsPanel extends JPanel {
    Game game;
    int playerID;

    public DiningStudentsPanel(Game game, int playerID) {
        this.game = game;
        this.playerID=playerID;
        GridLayout gridLayout=new GridLayout(1,5);
        gridLayout.setHgap(-35);
        setLayout(gridLayout);
        InitializeDiningStudents(playerID);
    }
    private void InitializeDiningStudents(int playerID){
        GridLayout gridLayout=new GridLayout(10,1);
        gridLayout.setVgap(-13);
        JPanel greenLane=new JPanel(gridLayout);
        greenLane.setOpaque(false);
        JPanel redLane=new JPanel(gridLayout);
        redLane.setOpaque(false);
        JPanel yellowLane=new JPanel(gridLayout);
        yellowLane.setOpaque(false);
        JPanel pinkLane=new JPanel(gridLayout);
        pinkLane.setOpaque(false);
        JPanel blueLane=new JPanel(gridLayout);
        blueLane.setOpaque(false);



        for(RealmColors color:RealmColors.values()){
            for(int i=0;i<10;i++){
                JPanel emptyPanel=new JPanel();
                emptyPanel.setOpaque(false);
                emptyPanel.setBorder(BorderFactory.createLineBorder(Color.black));
                if(game.getPlayerByIndex(playerID).getDashboard().getDiningRoom().getStudentsByColor(color)-(i+1)>=0) {
                    switch (color) {
                        case YELLOW -> yellowLane.add(new StudentButton(color));
                        case BLUE -> blueLane.add(new StudentButton(color));
                        case RED -> redLane.add(new StudentButton(color));
                        case PINK -> pinkLane.add(new StudentButton(color));
                        case GREEN -> greenLane.add(new StudentButton(color));
                    }
                }

            }
        }

        add(greenLane);
        add(redLane);
        add(yellowLane);
        add(pinkLane);
        add(blueLane);

    }
}

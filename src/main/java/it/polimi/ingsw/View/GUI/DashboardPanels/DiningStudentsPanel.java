package it.polimi.ingsw.View.GUI.DashboardPanels;

import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.View.GUI.Buttons.StudentButton;
import it.polimi.ingsw.View.StorageOfModelInformation.ModelStorage;

import javax.swing.*;
import java.awt.*;

public class DiningStudentsPanel extends JPanel {

    int playerID;
    private JPanel greenLane;
    private JPanel redLane;
    private JPanel yellowLane;
    private JPanel pinkLane;
    private JPanel blueLane;

    public DiningStudentsPanel(ModelStorage storage, int playerID) {
        this.playerID=playerID;
        GridLayout gridLayout=new GridLayout(1,5);
        gridLayout.setHgap(-35);
        setLayout(gridLayout);
        InitializeDiningStudents(playerID,storage);
    }
    private void InitializeDiningStudents(int playerID,ModelStorage storage){
        GridLayout gridLayout=new GridLayout(10,1);
        gridLayout.setVgap(-13);
        greenLane=new JPanel(gridLayout);
        greenLane.setOpaque(false);
        redLane=new JPanel(gridLayout);
        redLane.setOpaque(false);
        yellowLane=new JPanel(gridLayout);
        yellowLane.setOpaque(false);
        pinkLane=new JPanel(gridLayout);
        pinkLane.setOpaque(false);
        blueLane=new JPanel(gridLayout);
        blueLane.setOpaque(false);



        for(RealmColors color:RealmColors.values()){
            for(int i=0;i<10;i++){
                JPanel emptyPanel=new JPanel();
                emptyPanel.setOpaque(false);
                emptyPanel.setBorder(BorderFactory.createLineBorder(Color.black));
                if(storage.getDashboard(playerID).getDiningStudents(color)-(i+1)>=0) {
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

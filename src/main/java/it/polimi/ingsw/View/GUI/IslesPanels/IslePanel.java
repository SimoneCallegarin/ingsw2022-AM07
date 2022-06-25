package it.polimi.ingsw.View.GUI.IslesPanels;

import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Model.Enumeration.TowerColors;
import it.polimi.ingsw.View.GUI.Buttons.MNButton;
import it.polimi.ingsw.View.GUI.Buttons.StudentButton;
import it.polimi.ingsw.View.GUI.Buttons.TowerButton;
import it.polimi.ingsw.View.GUI.EventListeners.EffectListener;
import it.polimi.ingsw.View.GUI.EventListeners.MNListener;
import it.polimi.ingsw.View.StorageOfModelInformation.ModelStorage;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class IslePanel extends JPanel {

    ModelStorage storage;
    GridBagConstraints constraints;
    int isleID;
    int imageID;
    boolean motherNature=false;
    JPanel clickablePanel;
    DenyCardPanel denyCardPanel;

    ArrayList<BufferedImage> isles;
    ArrayList<BufferedImage> students;
    ArrayList<BufferedImage> towers;

    public IslePanel(ModelStorage storage,int isleID, ArrayList<BufferedImage> isles, ArrayList<BufferedImage> students, ArrayList<BufferedImage> towers) {
        this.storage=storage;
        this.isleID=isleID;
        this.imageID=isleID%3+1;
        this.clickablePanel=new JPanel();
        setLayout(new GridBagLayout());
        constraints=new GridBagConstraints();
        setOpaque(false);

        this.isles = isles;
        this.students = students;
        this.towers = towers;
        initializeIsle();
        setPreferredSize(this.getSize());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        BufferedImage img = isles.get(isleID%3);
        g.drawImage(img,0,0,getWidth(),getHeight(),null);
    }

    public void initializeIsle(){
        JPanel studentContainer=new JPanel(new GridBagLayout());
        studentContainer.setOpaque(false);
        JPanel towerContainer=new JPanel(new GridBagLayout());
        towerContainer.setOpaque(false);

        GridBagConstraints studentConstraints=new GridBagConstraints();
        GridBagConstraints towerConstraints=new GridBagConstraints();

        constraints.gridx=0;
        constraints.gridy=0;

        if(storage.getGameTable().getIsle(isleID).motherNatureIsPresent()){
            add(new MNButton(),constraints);
            constraints.gridy++;
            motherNature=true;
        }

        for(int i=0;i<storage.getGameTable().getIsle(isleID).getDenyCardsNumber();i++) {
            denyCardPanel = new DenyCardPanel();
            denyCardPanel.setPreferredSize(new Dimension(25, 25));
            add(denyCardPanel, constraints);
            constraints.gridy++;
        }

        studentConstraints.gridx=0;
        studentConstraints.gridy=0;
        for(RealmColors color:RealmColors.values()) {
            for (int i = 0; i < storage.getGameTable().getIsle(isleID).getStudentsByColor(color); i++) {
                studentContainer.add(new StudentButton(color, students),studentConstraints);
                if(studentConstraints.gridx==3){
                    studentConstraints.gridx=-1;
                    studentConstraints.gridy++;
                }
                studentConstraints.gridx++;
            }
        }
        add(studentContainer,constraints);

        constraints.gridy++;

        towerConstraints.gridx=0;
        towerConstraints.gridy=0;

        if(storage.getGameTable().getIsle(isleID).getTowerColor()!= TowerColors.NOCOLOR) {
            for (int i = 0; i < storage.getGameTable().getIsle(isleID).getTowerNumber(); i++) {
                towerContainer.add(new TowerButton(storage.getGameTable().getIsle(isleID).getTowerColor(), towers),towerConstraints);
                if(towerConstraints.gridx==3){
                    towerConstraints.gridx=-1;
                    towerConstraints.gridy++;
                }
                towerConstraints.gridx++;
            }
            add(towerContainer,constraints);
        }

        this.validate();
        this.repaint();
    }

    public int getIsleID() {
        return isleID;
    }

    public void resetIsle(){
        this.removeAll();
        this.validate();
        this.repaint();
    }

}

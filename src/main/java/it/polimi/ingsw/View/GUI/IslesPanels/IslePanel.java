package it.polimi.ingsw.View.GUI.IslesPanels;

import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Model.Enumeration.TowerColors;
import it.polimi.ingsw.Model.Game;
import it.polimi.ingsw.View.GUI.Buttons.MNButton;
import it.polimi.ingsw.View.GUI.Buttons.StudentButton;
import it.polimi.ingsw.View.GUI.Buttons.TowerButton;
import it.polimi.ingsw.View.GUI.EventListeners.IsleListener;
import it.polimi.ingsw.View.StorageOfModelInformation.ModelStorage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class IslePanel extends JPanel {

    ModelStorage storage;
    GridBagConstraints constraints;
    int isleID;
    DenyCardPanel denyCard;

    public IslePanel(ModelStorage storage,int isleID) {
        this.storage=storage;
        this.isleID=isleID;
        setLayout(new GridBagLayout());
        constraints=new GridBagConstraints();
        setOpaque(false);
        setBorder(BorderFactory.createLineBorder(Color.black));

        InitializeIsle(isleID);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ClassLoader cl=this.getClass().getClassLoader();
        InputStream url=cl.getResourceAsStream("GameTable/Isles/island1.png");
        BufferedImage img=null;
        try{
            img= ImageIO.read(url);
        }catch(IOException e){
            e.printStackTrace();
        }
        g.drawImage(img,0,0,getWidth(),getHeight(),null);
    }

    public void InitializeIsle(int isleID){
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
        }

        studentConstraints.gridx=0;
        studentConstraints.gridy=0;
        for(RealmColors color:RealmColors.values()) {
            for (int i = 0; i < storage.getGameTable().getIsle(isleID).getStudentsByColor(color); i++) {
                studentContainer.add(new StudentButton(color),studentConstraints);
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
                towerContainer.add(new TowerButton(storage.getGameTable().getIsle(isleID).getTowerColor()),towerConstraints);
                if(towerConstraints.gridx==3){
                    towerConstraints.gridx=-1;
                    towerConstraints.gridy++;
                }
                towerConstraints.gridx++;
            }
            add(towerContainer,constraints);
        }
    }

    public void setClickable(){
        addMouseListener(new IsleListener(this));
    }

    public void removeClickable(){
        removeMouseListener(this.getMouseListeners()[0]);
    }

    public int getIsleID() {
        return isleID;
    }

    public void addDenyCard(){
      denyCard=new DenyCardPanel();
      constraints.gridy++;
      constraints.ipady=30;
      constraints.ipadx=30;
      add(denyCard,constraints);
      this.validate();
    }

    public void removeDenyCard(){
        remove(denyCard);
        this.revalidate();
    }
}

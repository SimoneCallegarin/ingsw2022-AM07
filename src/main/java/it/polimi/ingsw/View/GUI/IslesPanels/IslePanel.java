package it.polimi.ingsw.View.GUI.IslesPanels;

import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Model.Enumeration.TowerColors;
import it.polimi.ingsw.View.GUI.Buttons.MNButton;
import it.polimi.ingsw.View.GUI.Buttons.StudentButton;
import it.polimi.ingsw.View.GUI.Buttons.TowerButton;
import it.polimi.ingsw.View.StorageOfModelInformation.ModelStorage;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Panel representing an isle on the game board
 */
public class IslePanel extends JPanel {
    /**
     * ModelStorage reference used to retrieve model information
     */
    private final ModelStorage storage;
    /**
     * Constraints used to organize the game objects (students,towers,Mother nature, deny card) in the isle panel
     */
    private final GridBagConstraints constraints;
    /**
     * Id of the isle represented by this panel
     */
    private final int isleID;
    /**
     * Array list used to retrieve the pngs of the isles
     */
    private final ArrayList<BufferedImage> isles;
    /**
     * Array list used to retrieve the pngs of the students
     */
    private final ArrayList<BufferedImage> students;
    /**
     * Array list used to retrieve the pngs of the towers
     */
    private final ArrayList<BufferedImage> towers;

    /**
     * Isle panel constructor
     * @param storage the ModelStorage reference
     * @param isleID the isle Id of the isle this panel represents
     * @param isles Array list used to retrieve the pngs of the isles
     * @param students Array list used to retrieve the pngs of the students
     * @param towers Array list used to retrieve the pngs of the towers
     */
    public IslePanel(ModelStorage storage,int isleID, ArrayList<BufferedImage> isles, ArrayList<BufferedImage> students, ArrayList<BufferedImage> towers) {
        this.storage=storage;
        this.isleID=isleID;
        this.isles = isles;
        this.students = students;
        this.towers = towers;

        setLayout(new GridBagLayout());
        constraints=new GridBagConstraints();
        setOpaque(false);

        initializeIsle();
        setPreferredSize(this.getSize());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        BufferedImage img = isles.get(isleID%3);
        g.drawImage(img,0,0,getWidth(),getHeight(),null);
    }

    /**
     * this method initialize the isle according to the model state retrieved from the ModelStorage reference. It's called once in the panel constructor
     * and everytime there's need to update the graphics on screen (but only once the isle panel has been reset by the resetIsle method).
     */
    public void initializeIsle(){
        JPanel studentContainer=new JPanel(new GridBagLayout());
        studentContainer.setOpaque(false);
        JPanel tower_MN_Deny_Container=new JPanel(new GridBagLayout());
        tower_MN_Deny_Container.setOpaque(false);

        GridBagConstraints studentConstraints=new GridBagConstraints();
        GridBagConstraints tower_MN_Deny_Constraints=new GridBagConstraints();

        constraints.gridx=0;
        constraints.gridy=0;

        tower_MN_Deny_Constraints.gridx=0;
        tower_MN_Deny_Constraints.gridy=0;
        TowerColors towerColor=storage.getGameTable().getIsle(isleID).getTowerColor();

        if(storage.getGameTable().getIsle(isleID).motherNatureIsPresent()){
            tower_MN_Deny_Container.add(new MNButton(),tower_MN_Deny_Constraints);
            tower_MN_Deny_Constraints.gridx++;
        }

        for(int i=0;i<storage.getGameTable().getIsle(isleID).getDenyCardsNumber();i++) {

            //Deny card panel used to draw the deny card if it's present on this isle
            DenyCardPanel denyCardPanel = new DenyCardPanel();
            denyCardPanel.setPreferredSize(new Dimension(25, 25));
            tower_MN_Deny_Container.add(denyCardPanel, tower_MN_Deny_Constraints);
            tower_MN_Deny_Constraints.gridx++;
        }

        if(towerColor!= TowerColors.NOCOLOR) {
            int towerNumber=storage.getGameTable().getIsle(isleID).getTowerNumber();
            if(towerNumber>=4){
                JPanel labelPanel=new JPanel();
                labelPanel.setPreferredSize(new Dimension(25,25));
                labelPanel.setOpaque(false);

                JLabel numberLabel=new JLabel();
                numberLabel.setOpaque(false);
                labelPanel.add(numberLabel);
                numberLabel.setText(String.valueOf(towerNumber));

                TowerButton towerButton=new TowerButton(towerColor, towers);
                tower_MN_Deny_Container.add(towerButton,tower_MN_Deny_Constraints);
                tower_MN_Deny_Constraints.gridx++;
                tower_MN_Deny_Container.add(labelPanel,tower_MN_Deny_Constraints);
            }else {
                for (int i = 0; i < towerNumber; i++) {
                    tower_MN_Deny_Container.add(new TowerButton(towerColor, towers), tower_MN_Deny_Constraints);
                    if (tower_MN_Deny_Constraints.gridx == 3) {
                        tower_MN_Deny_Constraints.gridx = -1;
                        tower_MN_Deny_Constraints.gridy++;
                    }
                    tower_MN_Deny_Constraints.gridx++;
                }
            }
        }
        add(tower_MN_Deny_Container,constraints);

        constraints.gridy++;

        studentConstraints.gridx=0;
        studentConstraints.gridy=0;

        for(RealmColors color:RealmColors.values()) {
            int studentsNumber=storage.getGameTable().getIsle(isleID).getStudentsByColor(color);
            if(studentsNumber>=5){
                JPanel labelPanel=new JPanel();
                labelPanel.setPreferredSize(new Dimension(25,25));
                labelPanel.setOpaque(false);

                JLabel numberLabel=new JLabel();
                numberLabel.setOpaque(false);
                labelPanel.add(numberLabel);
                numberLabel.setText(String.valueOf(studentsNumber));
                StudentButton studentButton=new StudentButton(color,students);
                studentContainer.add(studentButton,studentConstraints);
                studentConstraints.gridx++;
                studentContainer.add(labelPanel,studentConstraints);
            }else {
                for (int i = 0; i < studentsNumber; i++) {
                    StudentButton studentButton=new StudentButton(color,students);

                    studentContainer.add(studentButton, studentConstraints);
                    if (studentConstraints.gridx == 3) {
                        studentConstraints.gridx = -1;
                        studentConstraints.gridy++;
                    }
                    studentConstraints.gridx++;
                }
            }
            studentConstraints.gridx=0;
            studentConstraints.gridy++;
        }

        add(studentContainer,constraints);

        constraints.gridy++;

        this.validate();
        this.repaint();
    }

    /**
     * Getter of the isle ID represented by this isle
     * @return the isle ID represented by this isle
     */
    public int getIsleID() {
        return isleID;
    }

    /**
     * This method is called everytime the isle panel is updated before the initializeIsle method in order to arrange the game objects
     * with an empty panel
     */
    public void resetIsle(){
        this.removeAll();
        this.validate();
        this.repaint();
    }

}

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

import static it.polimi.ingsw.View.GUI.GUIConstants.denyCardDimension;
import static it.polimi.ingsw.View.GUI.GUIConstants.isleDimension;

/**
 * Panel representing an isle on the game board.
 */
public class IslePanel extends JPanel {

    /**
     * ModelStorage reference used to retrieve model information.
     */
    private final ModelStorage storage;
    /**
     * Constraints used to organize the game objects (students,towers,Mother nature, deny card) in the isle panel.
     */
    private final GridBagConstraints constraints;
    /**
     * Id of the isle represented by this panel.
     */
    private final int isleID;
    /**
     * Array list used to retrieve the pngs of the isles.
     */
    private final ArrayList<BufferedImage> isles;
    /**
     * Array list used to retrieve the pngs of the students.
     */
    private final ArrayList<BufferedImage> students;
    /**
     * Array list used to retrieve the pngs of the towers.
     */
    private final ArrayList<BufferedImage> towers;
    /**
     * The container of towers, mother nature and deny card of the isle.
     */
    private JPanel tower_MN_Deny_Container;
    /**
     * Constraints used for the towers, mother nature and the deny card.
     */
    private GridBagConstraints tower_MN_Deny_Constraints;
    /**
     * The students on the isle.
     */
    private JPanel studentContainer;
    /**
     * Constraints used for the students.
     */
    private GridBagConstraints studentConstraints;

    /**
     * Isle panel constructor.
     * @param storage the ModelStorage reference.
     * @param isleID the isle ID of the isle this panel represents.
     * @param isles Array list used to retrieve the pngs of the isles.
     * @param students Array list used to retrieve the pngs of the students.
     * @param towers Array list used to retrieve the pngs of the towers.
     */
    public IslePanel(ModelStorage storage,int isleID, ArrayList<BufferedImage> isles, ArrayList<BufferedImage> students, ArrayList<BufferedImage> towers) {
        this.storage=storage;
        this.isleID=isleID;
        this.isles = isles;
        this.students = students;
        this.towers = towers;

        setLayout(new GridBagLayout());
        constraints = new GridBagConstraints();
        setOpaque(false);

        initializeIsle();
        setPreferredSize(this.getSize());
    }

    /**
     * Paints the isles.
     * @param g allows an application to draw onto components that are realized on various devices.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        BufferedImage img = isles.get(isleID%3);
        g.drawImage(img,0,0,getWidth(),getHeight(),null);
    }

    /**
     * Initializes the isle according to the model state retrieved from the ModelStorage reference.
     * It's called once in the panel constructor and everytime there's need to update the graphics on screen
     * (but only once the isle panel has been reset by the resetIsle method).
     */
    public void initializeIsle(){
        tower_MN_Deny_Container = new JPanel(new GridBagLayout());
        tower_MN_Deny_Constraints = new GridBagConstraints();
        studentContainer = new JPanel(new GridBagLayout());
        studentConstraints = new GridBagConstraints();

        studentContainer.setOpaque(false);
        tower_MN_Deny_Container.setOpaque(false);

        constraints.gridx = 0;
        constraints.gridy = 0;

        tower_MN_Deny_Constraints.gridx = 0;
        tower_MN_Deny_Constraints.gridy = 0;

        drawMotherNature();

        drawDenyCards();

        drawTowers();

        add(tower_MN_Deny_Container,constraints);

        constraints.gridy++;

        studentConstraints.gridx=0;
        studentConstraints.gridy=0;

        drawStudents();

        add(studentContainer,constraints);

        constraints.gridy++;

        this.validate();
        this.repaint();
    }

    /**
     * When there are more than 5 students of the same color/towers on the isle,
     * they are substituted by a label containing their quantity.
     * @param towersOrStudentsNumber quantity of students/towers.
     * @return the label containing their quantity.
     */
    private JPanel studentsLabel(int towersOrStudentsNumber) {
        JPanel labelPanel = new JPanel();
        labelPanel.setPreferredSize(isleDimension);
        labelPanel.setOpaque(false);
        JLabel numberLabel = new JLabel();
        numberLabel.setOpaque(false);
        numberLabel.setText(String.valueOf(towersOrStudentsNumber));
        labelPanel.add(numberLabel);
        return labelPanel;
    }

    /**
     * Draws mother nature on the isle (if present).
     */
    private void drawMotherNature() {
        if(storage.getGameTable().getIsle(isleID).motherNatureIsPresent()){
            tower_MN_Deny_Container.add(new MNButton(),tower_MN_Deny_Constraints);
            tower_MN_Deny_Constraints.gridx++;
        }
    }

    /**
     * Draws the deny card on the isle (if there's one).
     */
    private void drawDenyCards() {
        if (storage.getGameTable().getIsle(isleID).getDenyCardsNumber() != 0) {
            DenyCardPanel denyCardPanel = new DenyCardPanel();
            denyCardPanel.setPreferredSize(denyCardDimension);
            tower_MN_Deny_Container.add(denyCardPanel,tower_MN_Deny_Constraints);
            tower_MN_Deny_Constraints.gridx++;
        }
    }

    /**
     * Draws the towers on the isle (if there are).
     */
    private void drawTowers() {
        TowerColors towerColor = storage.getGameTable().getIsle(isleID).getTowerColor();
        int towerNumber = storage.getGameTable().getIsle(isleID).getTowerNumber();
        if(towerColor != TowerColors.NOCOLOR) {
            if(towerNumber>=4) {
                JPanel labelPanel = studentsLabel(towerNumber);
                TowerButton towerButton = new TowerButton(towerColor, towers);
                tower_MN_Deny_Container.add(towerButton,tower_MN_Deny_Constraints);
                tower_MN_Deny_Constraints.gridx++;
                tower_MN_Deny_Container.add(labelPanel,tower_MN_Deny_Constraints);
            } else {
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
    }

    /**
     * Draws the students on the isle (if present).
     */
    private void drawStudents() {
        for(RealmColors color:RealmColors.values()) {
            int studentsNumber=storage.getGameTable().getIsle(isleID).getStudentsByColor(color);
            if(studentsNumber>=5){
                JPanel labelPanel = studentsLabel(studentsNumber);
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
    }

    /**
     * Getter of the isle ID represented by this isle
     * @return the isle ID represented by this isle
     */
    public int getIsleID() { return isleID; }

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

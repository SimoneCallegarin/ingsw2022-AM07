package it.polimi.ingsw.View.GUI;

import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Model.Enumeration.TowerColors;
import it.polimi.ingsw.Observer.ViewObserver;
import it.polimi.ingsw.View.GUI.Buttons.StudentButton;
import it.polimi.ingsw.View.GUI.Buttons.TowerButton;
import it.polimi.ingsw.View.GUI.DashboardPanels.DashboardPanel;
import it.polimi.ingsw.View.GUI.DashboardPanels.DiningStudentsPanel;
import it.polimi.ingsw.View.GUI.IslesPanels.TableCenterPanel;
import it.polimi.ingsw.View.StorageOfModelInformation.ModelStorage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Panel in which all the game takes place, it contains all the other panels
 */
public class GameScreenPanel extends JPanel {
    /**
     * ModelStorage reference used to retrieve game state information and pass it to the components created in this class
     */
    private final ModelStorage storage;
    /**
     * Array list used to store images of the students
     */
    ArrayList<BufferedImage> students;
    /**
     * Array list used to store images of checked students
     */
    ArrayList<BufferedImage> checkedStudents;
    /**
     * Array list used to store images of the towers
     */
    ArrayList<BufferedImage> towers;
    /**
     * Dashboard image
     */
    BufferedImage dashboard;
    /**
     * Panel placed on top of the screen used to contain the textLabel and the logout button
     */
    JPanel textContainerPanel;
    /**
     * Label used to show the current action occurring in game
     */
    JLabel textLabel;
    /**
     * Panel containing the first and third player dashboard
     */
    JPanel dashboardContainerPanel1;
    /**
     * Panel containing the first and third player dashboard
     */
    JPanel dashboardContainerPanel2;
    /**
     * TableCenterPanel reference
     */
    private final TableCenterPanel tableCenterPanel;
    /**
     * Array list used to store the player dashboard panels
     */
    ArrayList<DashboardPanel> dashboardPanels;
    /**
     * Array list used to store the player dashboard container panels
     */
    ArrayList<JPanel> dashboardContainers;
    /**
     * Array list of observer attached to the view components
     */
    ArrayList<ViewObserver> viewObservers;
    /**
     * Constraints for this component layout manager
     */
    GridBagConstraints gamescreenConstraints;


    /**
     * Create the game screen panel used to contain all the game panels
     * @param layout the LayoutManager to use
     * @param usernamePlaying the username of the current player using the GUI to pass to the tableCenter to highlight which is his dashboard
     * @param storage ModelStorage reference
     * @param frameWidth frame width
     * @param frameHeight frame height
     * @param nicknameColor color of the user nickname (chosen in the loading screen)
     * @param viewObservers array list of viewObservers attached to view components
     */
    public GameScreenPanel(LayoutManager layout, ModelStorage storage, int frameWidth, int frameHeight, String usernamePlaying, Color nicknameColor, ArrayList<ViewObserver> viewObservers ) {
        super(layout);
        this.storage = storage;
        setPreferredSize(new Dimension(frameWidth,frameHeight));
        this.dashboardPanels = new ArrayList<>();
        this.dashboardContainers = new ArrayList<>();
        this.viewObservers = viewObservers;
        textContainerPanel = new JPanel(new BorderLayout());

        dashboardContainerPanel1 = new JPanel(new GridLayout(2,1));
        dashboardContainerPanel1.setBackground(Color.CYAN);
        dashboardContainers.add(dashboardContainerPanel1);
        dashboardContainerPanel2 = new JPanel(new GridLayout(2,1));
        dashboardContainerPanel2.setBackground(Color.CYAN);
        dashboardContainers.add(dashboardContainerPanel2);

        students = new ArrayList<>();
        checkedStudents = new ArrayList<>();
        towers = new ArrayList<>();

        StudentButton sb = new StudentButton();
        TowerButton tb = new TowerButton();
        ClassLoader studentClassLoader = sb.getClass().getClassLoader();
        ClassLoader towerClassLoader = tb.getClass().getClassLoader();
        ClassLoader cl = this.getClass().getClassLoader();
        InputStream url;

        for (RealmColors rc : RealmColors.values()) {
            BufferedImage img = null;
            switch(rc) {
                case YELLOW -> url = studentClassLoader.getResourceAsStream("Dashboard/Students/Yellow.png");
                case BLUE -> url = studentClassLoader.getResourceAsStream("Dashboard/Students/Blue.png");
                case RED -> url = studentClassLoader.getResourceAsStream("Dashboard/Students/Red.png");
                case PINK -> url = studentClassLoader.getResourceAsStream("Dashboard/Students/Pink.png");
                case GREEN -> url = studentClassLoader.getResourceAsStream("Dashboard/Students/Green.png");
                default -> url = studentClassLoader.getResourceAsStream("Dashboard/Circles.png");
            }
            try {
                assert url != null;
                img = ImageIO.read(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            students.add(img);
        }

        for (RealmColors rc : RealmColors.values()) {
            BufferedImage img = null;
            switch(rc) {
                case YELLOW ->url=studentClassLoader.getResourceAsStream("Dashboard/Students/YellowChk.png");
                case BLUE -> url=studentClassLoader.getResourceAsStream("Dashboard/Students/BlueChk.png");
                case RED -> url=studentClassLoader.getResourceAsStream("Dashboard/Students/RedChk.png");
                case PINK -> url=studentClassLoader.getResourceAsStream("Dashboard/Students/PinkChk.png");
                case GREEN -> url=studentClassLoader.getResourceAsStream("Dashboard/Students/GreenChk.png");
                default -> url=studentClassLoader.getResourceAsStream("Dashboard/Circles.png");
            }
            try {
                assert url != null;
                img = ImageIO.read(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            checkedStudents.add(img);
        }

        for (TowerColors tc : TowerColors.values()) {
            BufferedImage img = null;
            switch (tc) {
                case BLACK -> url = towerClassLoader.getResourceAsStream("Dashboard/Tower_storage/black_tower.png");
                case WHITE -> url = towerClassLoader.getResourceAsStream("Dashboard/Tower_storage/white_tower.png");
                case GREY -> url = towerClassLoader.getResourceAsStream("Dashboard/Tower_storage/grey_tower.png");
                default -> url = towerClassLoader.getResourceAsStream("Dashboard/Circles.png");
            }
            try {
                assert url != null;
                img = ImageIO.read(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            towers.add(img);
        }

        url = cl.getResourceAsStream("Dashboard/Semi_empty.png");
        try {
            if (url != null)
                dashboard = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        gamescreenConstraints=new GridBagConstraints();

        gamescreenConstraints.gridy=0;
        gamescreenConstraints.weighty=0.01;
        gamescreenConstraints.weightx=1;
        gamescreenConstraints.gridx=1;
        add(textContainerPanel,gamescreenConstraints);

        gamescreenConstraints.gridy=1;
        gamescreenConstraints.fill=GridBagConstraints.BOTH;

        gamescreenConstraints.gridx=1;

        tableCenterPanel=new TableCenterPanel(frameWidth,frameHeight,storage,usernamePlaying,nicknameColor,viewObservers, students, towers,checkedStudents, this);
        add(tableCenterPanel,gamescreenConstraints);

        // Then I add the two dashboard container Panel on the left and on the right, and I set the weights in order to correctly size the dashboard.
        gamescreenConstraints.weightx=0.05;
        gamescreenConstraints.weighty=0.2;
        gamescreenConstraints.gridx=0;
        add(dashboardContainerPanel1,gamescreenConstraints);

        gamescreenConstraints.gridx=2;
        add(dashboardContainerPanel2,gamescreenConstraints);

        for(int i=0;i<storage.getNumberOfPlayers();i++){
            DashboardPanel dashboardPanel=new DashboardPanel(storage,i,viewObservers,tableCenterPanel, students, checkedStudents, towers, dashboard);
            dashboardContainers.get(i%2).add(dashboardPanel);   //the containers are used to place the panels in the screen
            dashboardPanels.add(dashboardPanel);                //this arrayList is used only to store them and access them in a more efficient way
        }

        textLabel = new JLabel("");
        textLabel.setPreferredSize(new Dimension(1000,10));
        textContainerPanel.add(textLabel);
        gamescreenConstraints.gridy=0;
    }

    /**
     * Sets the dashboard entrance clickable in order to select a student to move.
     * @param playerID the playerID used to identify which dashboard set movable.
     */
    public void setEntranceStudentsClickable(int playerID) {
        dashboardPanels.get(playerID).getEntrance().setStudentsClickable();
    }

    /**
     * Sets the character cards clickable in order for the player to activate the effect.
     * @param playerID the player id used by the listener to remove the listener from the player dashboard.
     */
    public void setCharactersClickable(int playerID) {
        tableCenterPanel.setCharactersClickable(viewObservers, playerID);
    }

    /**
     * Sets the students of the dining room clickable.
     * @param playerID ID associated with the dining room.
     */
    public void setDiningStudentsClickable(int playerID){
        DiningStudentsPanel diningStudentsPanel=dashboardPanels.get(playerID).getDining().getStudentsPanel();
        diningStudentsPanel.setStudentsClickableForEffect(viewObservers,tableCenterPanel);
    }

    /**
     * getter for the dashboard panel associated with the player id
     * @param playerID player id used to retrieve a dashboard from the array list
     * @return the dashboard panel
     */
    public DashboardPanel getDashboardPanel(int playerID) { return dashboardPanels.get(playerID); }

    /**
     * getter for the TableCenterPanel reference
     * @return the TableCenterPanel reference
     */
    public TableCenterPanel getTableCenterPanel() { return tableCenterPanel; }

    /**
     * method to set the message on the label on top of the game screen
     * @param message the text string to update the label with
     */
    public void setMessage(String message) { textLabel.setText(message); }

    /**
     * Removes the listeners from the dashboard entrance and dining
     * @param playerID the player id used to identify which dashboard remove the listeners from
     */
    public void removeClickableDashboard(int playerID) {
        dashboardPanels.get(playerID).getEntrance().removeStudentsClickable();
        dashboardPanels.get(playerID).getDining().removeClickable();
    }

    /**
     * Method used to update the entrance associated with the specified player ID
     * @param playerID the player ID used to access the dashboard that contains the entrance to update
     */
    public void updateEntrance(int playerID){
        dashboardPanels.get(playerID).getEntrance().resetEntrance();
        dashboardPanels.get(playerID).getEntrance().initializeEntrance(storage);
    }

    /**
     * Updates the dashboard DiningStudentPanel
     * @param playerID player id used to identify which dining to update
     */
    public void updateStudentDinings(int playerID){
        dashboardPanels.get(playerID).getDining().resetStudentDining();
        dashboardPanels.get(playerID).getDining().initializeStudentDining(storage);
    }

    /**
     * Updates all the dashboard ProfessorPanels
     */
    public void updateProfessors(){
        for(DashboardPanel dashboardPanel:dashboardPanels) {
            dashboardPanel.getDining().resetProfessorDining();
            dashboardPanel.getDining().initializeProfessorPanel(storage);
        }
    }

    /**
     * Updates all the TowerStorage panels
     */
    public void updateTowerStorages(){
        for(DashboardPanel dashboardPanel: dashboardPanels){
            dashboardPanel.getTowerStorage().resetTowerStorage();
            dashboardPanel.getTowerStorage().initializeTowerStorage();
        }

    }
}

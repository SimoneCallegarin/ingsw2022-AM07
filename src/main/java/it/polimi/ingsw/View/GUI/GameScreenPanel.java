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

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static it.polimi.ingsw.View.GUI.GUIConstants.currentActionGSP;
import static it.polimi.ingsw.View.GUI.ImagesLoader.*;

/**
 * Panel in which all the game takes place, it contains all the other panels.
 */
public class GameScreenPanel extends JPanel {

    /**
     * Panel placed on top of the screen used to contain the textLabel and the logout button.
     */
    private final JPanel textContainerPanel;
    /**
     * Label used to show the current action occurring in game.
     */
    private final JLabel currentAction;
    /**
     * TableCenterPanel reference.
     */
    private final TableCenterPanel tableCenterPanel;
    /**
     * Array list used to store the player dashboard panels.
     */
    private final ArrayList<DashboardPanel> dashboardPanels;
    /**
     * Array list of observer attached to the view components.
     */
    private final ArrayList<ViewObserver> viewObservers;
    /**
     * GridBagConstraints used in the game screen panel.
     */
    private final GridBagConstraints gameScreenConstraints;

    /**
     * Create the game screen panel used to contain all the game panels.
     * @param layout the LayoutManager to use.
     * @param usernamePlaying the username of the current player using the GUI to pass to the tableCenter to highlight which is his dashboard.
     * @param storage ModelStorage reference.
     * @param frameWidth frame width.
     * @param frameHeight frame height.
     * @param nicknameColor color of the user nickname (chosen in the loading screen).
     * @param viewObservers array list of viewObservers attached to view components.
     */
    public GameScreenPanel(LayoutManager layout, ModelStorage storage, int frameWidth, int frameHeight, String usernamePlaying, Color nicknameColor, ArrayList<ViewObserver> viewObservers) {
        super(layout);

        this.textContainerPanel = new JPanel(new BorderLayout());
        this.dashboardPanels = new ArrayList<>();
        this.viewObservers = viewObservers;

        setPreferredSize(new Dimension(frameWidth,frameHeight));

        // Set the dashboards:
        ArrayList<JPanel> dashboardContainers = prepareDashboardsPanels();
        BufferedImage dashboardImage = dashboardImageLoader(this.getClass().getClassLoader());

        ArrayList<BufferedImage> students = prepareStudents();                  // Array list used to store images of the students.
        ArrayList<BufferedImage> checkedStudents = prepareCheckedStudents();    // Array list used to store images of checked students.
        ArrayList<BufferedImage> towers = prepareTowers();                      // Array list used to store images of the towers.

        gameScreenConstraints = new GridBagConstraints();

        // Set game screen constraints for the textContainerPanel.
        setConstraints(1,1,0.01,0);
        add(textContainerPanel,gameScreenConstraints);
        // Set game screen constraints for the tableCenterPanel.
        setConstraints(1,1,0.01,1);
        gameScreenConstraints.fill = GridBagConstraints.BOTH;
        tableCenterPanel = new TableCenterPanel(storage,usernamePlaying,nicknameColor,viewObservers, students, towers, checkedStudents, this);
        add(tableCenterPanel,gameScreenConstraints);

        // Add the two dashboard container Panel and set the weights in order to correctly size them.
        // First two dashboards (first and third):
        setConstraints(0.05,0,0.2,1);
        add(dashboardContainers.get(0), gameScreenConstraints);
        // Second two dashboards (second and fourth):
        setConstraints(0.05,2,0.2,1);
        add(dashboardContainers.get(1), gameScreenConstraints);

        for(int i=0; i<storage.getNumberOfPlayers(); i++){
            DashboardPanel dashboardPanel = new DashboardPanel(storage,i,viewObservers,tableCenterPanel,students,checkedStudents,towers,dashboardImage);
            dashboardContainers.get(i%2).add(dashboardPanel);   // the containers are used to place the panels in the screen.
            dashboardPanels.add(dashboardPanel);                // this arrayList is used only to store them and access them in a more efficient way.
        }

        // Set the textLabel:
        currentAction = new JLabel("");
        currentAction.setPreferredSize(currentActionGSP);
        textContainerPanel.add(currentAction);
    }

    /**
     * Sets the GridBagConstraints.
     * @param weightX weightx of the GridBagConstraints.
     * @param gridX gridx of the GridBagConstraints.
     * @param weightY weighty of the GridBagConstraints.
     * @param gridY gridy of the GridBagConstraints.
     */
    private void setConstraints( double weightX, int gridX, double weightY, int gridY) {
        gameScreenConstraints.weightx = weightX;
        gameScreenConstraints.gridx = gridX;
        gameScreenConstraints.weighty = weightY;
        gameScreenConstraints.gridy = gridY;
    }

    /**
     * Prepares dashboards panels.
     * @return the Array list used to store the player dashboard container panel..
     */
    private ArrayList<JPanel> prepareDashboardsPanels() {
        ArrayList<JPanel> dashboardContainers = new ArrayList<>();
        // Panel containing the first and third player dashboard.
        JPanel dashboardContainerPanel1 = new JPanel(new GridLayout(2, 1));
        dashboardContainerPanel1.setBackground(Color.CYAN);
        dashboardContainers.add(dashboardContainerPanel1);
        // Panel containing the second and fourth player dashboard
        JPanel dashboardContainerPanel2 = new JPanel(new GridLayout(2, 1));
        dashboardContainerPanel2.setBackground(Color.CYAN);
        dashboardContainers.add(dashboardContainerPanel2);
        return dashboardContainers;
    }

    /**
     * Prepares the students.
     * @return the Array list used to store images of the students.
     */
    private ArrayList<BufferedImage> prepareStudents() {
        // Loading Images of the students buttons:
        ArrayList<BufferedImage> students = new ArrayList<>();
        for (RealmColors rc : RealmColors.values())
            students.add(studentsImagesLoader(rc,StudentButton.class.getClassLoader()));
        return students;
    }

    /**
     * Prepares the checked students.
     * @return the Array list used to store images of the checked students.
     */
    private ArrayList<BufferedImage> prepareCheckedStudents() {
        // Loading Images of the students buttons:
        ArrayList<BufferedImage> checkedStudents = new ArrayList<>();
        for (RealmColors rc : RealmColors.values())
            checkedStudents.add(checkedStudentsImagesLoader(rc,StudentButton.class.getClassLoader()));
        return checkedStudents;
    }

    /**
     * Prepares the towers.
     * @return the Array list used to store images of the towers.
     */
    private ArrayList<BufferedImage> prepareTowers() {
        // Loading Images on the towers buttons:
        ArrayList<BufferedImage> towers = new ArrayList<>();
        // Loading towers images:
        for (TowerColors tc : TowerColors.values())
            towers.add(towersImagesLoader(tc,TowerButton.class.getClassLoader()));
        return towers;
    }

    /**
     * Removes the listeners from the dashboard entrance and dining room.
     * @param playerID the player ID used to identify which dashboard remove the listeners from.
     */
    public void removeClickableDashboard(int playerID) {
        dashboardPanels.get(playerID).getEntrance().removeStudentsClickable();
        dashboardPanels.get(playerID).getDining().removeClickable();
    }

    /**
     * Updates the entrance associated with the specified player ID.
     * @param playerID the player ID used to access the dashboard that contains the entrance to update.
     */
    public void updateEntrance(int playerID){
        dashboardPanels.get(playerID).getEntrance().resetEntrance();
        dashboardPanels.get(playerID).getEntrance().initializeEntrance();
    }

    /**
     * Updates the dashboard DiningStudentPanel.
     */
    public void updateStudentDinings(){
        for(DashboardPanel dashboardPanel:dashboardPanels){
            dashboardPanel.getDining().resetStudentDining();
            dashboardPanel.getDining().initializeStudentDining();
        }
    }

    /**
     * Updates all the dashboard ProfessorPanels.
     */
    public void updateProfessors(){
        for(DashboardPanel dashboardPanel:dashboardPanels) {
            dashboardPanel.getDining().resetProfessorDining();
            dashboardPanel.getDining().initializeProfessorPanel();
        }
    }

    /**
     * Updates all the TowerStorage panels.
     */
    public void updateTowerStorages(){
        for(DashboardPanel dashboardPanel: dashboardPanels){
            dashboardPanel.getTowerStorage().resetTowerStorage();
            dashboardPanel.getTowerStorage().initializeTowerStorage();
        }
    }

    /**
     * Sets the dashboard entrance clickable in order to select a student to move.
     * @param playerID the playerID used to identify which dashboard set movable.
     */
    public void setEntranceStudentsClickable(int playerID) { dashboardPanels.get(playerID).getEntrance().setStudentsClickable(); }

    /**
     * Sets the character cards clickable in order for the player to activate the effect.
     * @param playerID the player id used by the listener to remove the listener from the player dashboard.
     */
    public void setCharactersClickable(int playerID) { tableCenterPanel.setCharactersClickable(viewObservers, playerID); }

    /**
     * Sets the students of the dining room clickable.
     * @param playerID player ID associated to the dining room.
     */
    public void setDiningStudentsClickable(int playerID){
        DiningStudentsPanel diningStudentsPanel=dashboardPanels.get(playerID).getDining().getStudentsPanel();
        diningStudentsPanel.setStudentsClickableForEffect(viewObservers);
    }

    /**
     * Setter method for the message on the label on top of the game screen.
     * @param message the text string to update the label with.
     */
    public void setMessage(String message) { currentAction.setText(message); }

    /**
     * Getter method for the dashboard panel associated to the player ID.
     * @param playerID player ID used to retrieve a dashboard from the array list.
     * @return the dashboard panel associated with that player ID.
     */
    public DashboardPanel getDashboardPanel(int playerID) { return dashboardPanels.get(playerID); }

    /**
     * Getter method for the TableCenterPanel reference.
     * @return the TableCenterPanel reference.
     */
    public TableCenterPanel getTableCenterPanel() { return tableCenterPanel; }

    /**
     * Getter method for the panel placed on top of the screen that contains the textLabel and the logout button.
     * @return the panel placed on top of the screen that contains the textLabel and the logout button.
     */
    public JPanel getTextContainerPanel() { return textContainerPanel; }
}

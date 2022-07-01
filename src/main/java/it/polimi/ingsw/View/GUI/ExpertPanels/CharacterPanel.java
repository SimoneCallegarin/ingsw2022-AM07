package it.polimi.ingsw.View.GUI.ExpertPanels;

import it.polimi.ingsw.Model.CharacterCards.CharacterCardsName;
import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Observer.ViewObserver;
import it.polimi.ingsw.View.GUI.Buttons.StudentButton;
import it.polimi.ingsw.View.GUI.DashboardPanels.DashboardPanel;
import it.polimi.ingsw.View.GUI.EventListeners.CharacterStudentListener;
import it.polimi.ingsw.View.GUI.EventListeners.EffectListener;
import it.polimi.ingsw.View.GUI.IslesPanels.DenyCardPanel;
import it.polimi.ingsw.View.GUI.IslesPanels.TableCenterPanel;
import it.polimi.ingsw.View.StorageOfModelInformation.ModelStorage;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static it.polimi.ingsw.View.GUI.GUIConstants.*;
import static it.polimi.ingsw.View.GUI.ImagesLoader.characterCardImageLoader;

/**
 * This panel represents a character card on the game table.
 */
public class CharacterPanel extends JPanel {

    /**
     * The String representing the character name.
     */
    private final String character;
    /**
     * The index of the character in the game table.
     */
    private final int characterIndex;
    /**
     * The image associated to this character card.
     */
    private final BufferedImage characterCardImage;
    /**
     * Constraints for this panel layout.
     */
    private final GridBagConstraints constraints;
    /**
     * Model storage reference used to retrieve information about the character card state
     * (students/deny cards etc. on it).
     */
    private final ModelStorage storage;
    /**
     * Array list to store the students images.
     */
    private final ArrayList<BufferedImage> students;
    /**
     * Array list to store the checked students images.
     */
    private final ArrayList<BufferedImage> checkedStudents;
    /**
     * Array list to store the student buttons placed on the character card.
     */
    private final ArrayList<StudentButton> studentButtons;
    /**
     * Array list of EffectListeners to attach to the game objects available on the card.
     */
    private final ArrayList<EffectListener> effectListeners;
    /**
     * Array list of CharacterStudentListener to attach to this character panel.
     */
    private final ArrayList<CharacterStudentListener> characterStudentListeners;
    /**
     * TableCenterPanel reference passed to the listener.
     */
    private final TableCenterPanel tcp;
    /**
     * Array list of viewObservers attached to the view components.
     */
    private final ArrayList<ViewObserver> viewObservers;

    /**
     * Constructor fo CharacterPanel.
     * @param storage Model storage reference used to retrieve information about the character card state
     *                (students/deny cards etc. on it).
     * @param character The String representing the character name.
     * @param characterIndex The index of the character in the game table.
     * @param students Array list to store the students images.
     * @param checkedStudents Array list to store the checked students images.
     * @param tcp TableCenterPanel reference passed to the listener.
     * @param viewObservers Array list of viewObservers attached to the view components.
     */
    public CharacterPanel(ModelStorage storage, String character, int characterIndex,
                          ArrayList<BufferedImage> students, ArrayList<BufferedImage> checkedStudents,
                          TableCenterPanel tcp, ArrayList<ViewObserver> viewObservers) {

        this.character = character;
        this.characterIndex = characterIndex;
        this.characterCardImage = characterCardImageLoader(character);
        this.checkedStudents = checkedStudents;
        this.students = students;
        this.studentButtons = new ArrayList<>();
        this.effectListeners = new ArrayList<>();
        this.characterStudentListeners = new ArrayList<>();
        this.tcp = tcp;
        this.viewObservers = viewObservers;
        this.constraints = new GridBagConstraints();
        this.storage = storage;

        setBorder(BorderFactory.createEmptyBorder());
        setLayout(new GridBagLayout());
        setPreferredSize(this.getSize());
        setOpaque(false);

        initializeCharacter();
    }

    /**
     * Paints the character card on screen.
     * @param g allows an application to draw onto components that are realized on various devices.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(characterCardImage,characterCardPositionX,characterCardPositionY,getWidth(),getHeight(),null);
    }

    /**
     * This method place game objects panel on this card if they are present.
     * Game objects are present on the card only if the card name is
     * MONK, JESTER, SPOILED_PRINCESS or GRANDMA_HERBS.
     * This method is called after the resetCharacter method
     * in order to always place the components on an empty panel.
     */
    public void initializeCharacter(){
        constraints.gridx = characterCardGridX;
        constraints.gridy = characterCardGridY;
        JPanel objectsPanel=new JPanel(new GridBagLayout());
        objectsPanel.setOpaque(false);
        GridBagConstraints objectsConstraints=new GridBagConstraints();
        objectsConstraints.gridy=characterCardGridX;
        objectsConstraints.gridx=characterCardGridY;
        switch (character){
            case "MONK", "JESTER","SPOILED_PRINCESS" ->{
                for(RealmColors color: RealmColors.values()){
                    for(int i=0; i<storage.getGameTable().getCharacterCard(characterIndex).getStudentsByColor(color); i++){
                        StudentButton studentButton = new StudentButton(color,students,checkedStudents);
                        objectsPanel.add(studentButton,objectsConstraints);
                        studentButtons.add(studentButton);
                        objectsConstraints.gridx++;
                    }
                }
                add(objectsPanel,constraints);
            }
            case "GRANDMA_HERBS"->{
                for(int i=0; i<storage.getGameTable().getCharacterCard(characterIndex).getDenyCardsOnCharacterCard();  i++){
                    objectsPanel.add(new DenyCardPanel(),objectsConstraints);
                    objectsConstraints.gridx++;
                }
                add(objectsPanel,constraints);
            }
        }
        JPanel coinsPanel=new JPanel(new GridBagLayout());
        coinsPanel.setOpaque(false);
        constraints.gridy++;
        add(coinsPanel,constraints);
        if(storage.getGameTable().getCharacterCard(characterIndex).used()) {
            coinsPanel.add(new CoinPanel());
        }
        this.validate();
        this.repaint();

    }

    /**
     * Adds a MouseListener to every student of the Character Card in order to send
     * an ACTIVATE_ATOMIC_EFFECT or a COLOR_VALUE message to the server, depending on the character.
     * @param dashboard the dashboard associated with the one who played the Character Card.
     */
    public void setStudentsClickable(DashboardPanel dashboard) {
        if (character.equals(CharacterCardsName.SPOILED_PRINCESS.toString())) {
            for (StudentButton studentButton : studentButtons) {
                EffectListener el = new EffectListener(viewObservers, -1, tcp, dashboard.getEntrance());
                effectListeners.add(el);
                studentButton.addMouseListener(el);
            }
        }
        else if (character.equals(CharacterCardsName.MONK.toString()) || character.equals(CharacterCardsName.JESTER.toString())) {
            for (StudentButton studentButton : studentButtons) {
                CharacterStudentListener ccl = new CharacterStudentListener(viewObservers, tcp, dashboard.getEntrance(), character);
                characterStudentListeners.add(ccl);
                studentButton.addMouseListener(ccl);
            }
        }
    }

    /**
     * Removes the MouseListeners of the students on the Character Card.
     */
    public void removeStudentsClickable() {
        if ((character.equals(CharacterCardsName.SPOILED_PRINCESS.toString())) && !effectListeners.isEmpty()) {
            for (int i = 0; i < studentButtons.size(); i++)
                studentButtons.get(i).removeMouseListener(effectListeners.get(i));
        }
        else if ((character.equals(CharacterCardsName.MONK.toString()) || character.equals(CharacterCardsName.JESTER.toString())) && !characterStudentListeners.isEmpty()) {
            for (int i = 0; i < studentButtons.size(); i++)
                studentButtons.get(i).removeMouseListener(characterStudentListeners.get(i));
        }
    }

    /**
     * This method removes all the components from the panel and clear the student buttons array list. Used to let the initializeCharacter method
     * place components on an empty panel
     */
    public void resetCharacter(){
        this.removeAll();
        studentButtons.clear();
        this.validate();
        this.repaint();
    }

    /**
     * Getter method for the index of the character card.
     * @return the index of this character card.
     */
    public int getCharacterIndex() { return characterIndex; }

    /**
     * Getter method for the character card name.
     * @return the name of this character card.
     */
    public String getCharacterName() { return character; }

}

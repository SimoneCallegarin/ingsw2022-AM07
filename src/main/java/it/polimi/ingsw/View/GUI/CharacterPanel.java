package it.polimi.ingsw.View.GUI;

import it.polimi.ingsw.Model.CharacterCards.CharacterCardsName;
import it.polimi.ingsw.Model.DashboardObjects.Entrance;
import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Observer.ViewObserver;
import it.polimi.ingsw.View.GUI.Buttons.StudentButton;
import it.polimi.ingsw.View.GUI.DashboardPanels.DashboardPanel;
import it.polimi.ingsw.View.GUI.DashboardPanels.DiningPanel;
import it.polimi.ingsw.View.GUI.DashboardPanels.EntrancePanel;
import it.polimi.ingsw.View.GUI.EventListeners.CharacterCardListener;
import it.polimi.ingsw.View.GUI.EventListeners.CharacterStudentListener;
import it.polimi.ingsw.View.GUI.EventListeners.EffectListener;
import it.polimi.ingsw.View.GUI.EventListeners.EntranceStudentListener;
import it.polimi.ingsw.View.GUI.IslesPanels.DenyCardPanel;
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
 * this panel represents a character card on the game table
 */
public class CharacterPanel extends JPanel {
    /**
     * the String representing the character name
     */
    String character;
    /**
     * the index of the character in the game table
     */
    int characterIndex;
    /**
     * constraints for this panel layout
     */
    GridBagConstraints constraints;
    /**
     * model storage reference used to retrieve information about the character card state (students/deny cards etc. on it)
     */
    ModelStorage storage;
    /**
     * array list to store the students images
     */
    ArrayList<BufferedImage> students;
    /**
     * array list to store the checked students images
     */
    ArrayList<BufferedImage> checkedStudents;
    /**
     * array list to store the student buttons placed on the character card
     */
    ArrayList<StudentButton> studentButtons;

    ArrayList<EffectListener> effectListeners;
    ArrayList<CharacterStudentListener> characterStudentListeners;

    TableCenterPanel tcp;
    ArrayList<ViewObserver> viewObservers;

    public CharacterPanel(ModelStorage storage, String character, int characterIndex,
                          ArrayList<BufferedImage> students, ArrayList<BufferedImage> checkedStudents,
                          TableCenterPanel tcp, ArrayList<ViewObserver> viewObservers) {

        this.studentButtons=new ArrayList<>();
        this.effectListeners = new ArrayList<>();
        this.characterStudentListeners = new ArrayList<>();
        this.tcp = tcp;
        this.viewObservers = viewObservers;
        this.character=character;
        this.characterIndex = characterIndex;
        this.constraints=new GridBagConstraints();
        this.storage=storage;
        this.students=students;
        this.checkedStudents=checkedStudents;

        setBorder(BorderFactory.createEmptyBorder());
        setLayout(new GridBagLayout());
        setPreferredSize(this.getSize());
        setOpaque(false);

        initializeCharacter();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ClassLoader cl=this.getClass().getClassLoader();
        InputStream url=null;
        switch (character){
            case "MONK"->url=cl.getResourceAsStream("Character_cards/MONK.jpg");
            case "FARMER"->url=cl.getResourceAsStream("Character_cards/FARMER.jpg");
            case "HERALD"->url=cl.getResourceAsStream("Character_cards/HERALD.jpg");
            case "MAGICAL_LETTER_CARRIER"->url=cl.getResourceAsStream("Character_cards/MAGICAL_LETTER_CARRIER.jpg");
            case "GRANDMA_HERBS"->url=cl.getResourceAsStream("Character_cards/GRANDMA_HERBS.jpg");
            case "CENTAUR"->url=cl.getResourceAsStream("Character_cards/CENTAUR.jpg");
            case "JESTER"->url=cl.getResourceAsStream("Character_cards/JESTER.jpg");
            case "KNIGHT"->url=cl.getResourceAsStream("Character_cards/KNIGHT.jpg");
            case "FUNGIST"->url=cl.getResourceAsStream("Character_cards/FUNGIST.jpg");
            case "MINSTREL"->url=cl.getResourceAsStream("Character_cards/MINSTREL.jpg");
            case "SPOILED_PRINCESS"->url=cl.getResourceAsStream("Character_cards/SPOILED_PRINCESS.jpg");
            case "THIEF"->url=cl.getResourceAsStream("Character_cards/THIEF.jpg");
        }
        BufferedImage img=null;
        try{
            assert url != null;
            img= ImageIO.read(url);
        }catch(IOException e){
            e.printStackTrace();
        }
        g.drawImage(img,0,0,getWidth(),getHeight(),null);
    }

    public void initializeCharacter(){
        constraints.gridx=0;
        constraints.gridy=0;
        switch (character){
            case "MONK", "JESTER","SPOILED_PRINCESS" ->{
                for(RealmColors color: RealmColors.values()){
                    for(int i=0;i<storage.getGameTable().getCharacterCard(characterIndex).getStudentsByColor(color);i++){
                        StudentButton studentButton=new StudentButton(color,students,checkedStudents);
                        add(studentButton,constraints);
                        studentButtons.add(studentButton);
                        constraints.gridx++;
                    }
                }
            }
            case "GRANDMA_HERBS"->{
                for(int i=0;i<storage.getGameTable().getCharacterCard(characterIndex).getDenyCardsOnCharacterCard();i++){
                    add(new DenyCardPanel(),constraints);
                    constraints.gridx++;
                }
            }

        }
        if(storage.getGameTable().getCharacterCard(characterIndex).used()) {
            constraints.gridx=0;
            constraints.gridy++;
            constraints.gridy++;
            add(new CoinPanel(),constraints);
        }

    }

    /**
     * Adds a MouseListener to every student of the Character Card in order to send an ACTIVATE_ATOMIC_EFFECT or a COLOR_VALUE message to the server, depending on the character.
     * @param dashboard the dashboard associated with the one who played the Character Card
     */
    public void setStudentsClickable(DashboardPanel dashboard) {
        if (character.equals(CharacterCardsName.SPOILED_PRINCESS.toString())) {
            for (StudentButton studentButton : studentButtons) {
                EffectListener el = new EffectListener(viewObservers, -1, tcp, dashboard.getEntrance());
                effectListeners.add(el);
                studentButton.addMouseListener(new EffectListener(viewObservers, -1, tcp, dashboard.getEntrance()));
            }
        }
        else if (character.equals(CharacterCardsName.MONK.toString()) || character.equals(CharacterCardsName.JESTER.toString())) {
            for (StudentButton studentButton : studentButtons) {
                CharacterStudentListener ccl = new CharacterStudentListener(viewObservers, tcp, dashboard.getEntrance(), character);
                characterStudentListeners.add(ccl);
                studentButton.addMouseListener(new CharacterStudentListener(viewObservers, tcp, dashboard.getEntrance(), character));
            }
        }
    }

    /**
     * Removes the MouseListeners of the students on the Character Card.
     */
    public void removeStudentsClickable() {
        if ((character.equals(CharacterCardsName.SPOILED_PRINCESS.toString())) && !effectListeners.isEmpty()) {
            for (int i = 0; i < studentButtons.size(); i++) {
                studentButtons.get(i).removeMouseListener(effectListeners.get(i));
            }
        }
        else if ((character.equals(CharacterCardsName.MONK.toString()) || character.equals(CharacterCardsName.JESTER.toString())) && !characterStudentListeners.isEmpty()) {
            for (int i = 0; i < studentButtons.size(); i++) {
                studentButtons.get(i).removeMouseListener(characterStudentListeners.get(i));
            }
        }
    }

    public void resetCharacter(){
        this.removeAll();
        studentButtons.clear();
        this.validate();
        this.repaint();
    }

    public int getCharacterIndex() { return characterIndex; }

    public String getCharacterName() { return character; }

}

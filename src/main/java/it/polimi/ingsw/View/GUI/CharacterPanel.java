package it.polimi.ingsw.View.GUI;

import it.polimi.ingsw.Model.CharacterCards.CharacterCardsName;
import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.View.GUI.Buttons.StudentButton;
import it.polimi.ingsw.View.GUI.IslesPanels.DenyCardPanel;
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

    public CharacterPanel(ModelStorage storage, String character, int characterIndex,
                          ArrayList<BufferedImage> students, ArrayList<BufferedImage> checkedStudents) {

        this.studentButtons=new ArrayList<>();
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

    public void resetCharacter(){
        this.removeAll();
        studentButtons.clear();
        this.validate();
        this.repaint();
    }

    public int getCharacterIndex() { return characterIndex; }

}

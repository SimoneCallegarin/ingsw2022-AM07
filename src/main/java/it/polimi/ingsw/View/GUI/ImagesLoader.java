package it.polimi.ingsw.View.GUI;

import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Model.Enumeration.TowerColors;
import it.polimi.ingsw.View.GUI.Buttons.*;
import it.polimi.ingsw.View.GUI.CloudsPanels.CloudPanel;
import it.polimi.ingsw.View.GUI.DashboardPanels.DiningPanel;
import it.polimi.ingsw.View.GUI.ExpertPanels.CharacterPanel;
import it.polimi.ingsw.View.GUI.ExpertPanels.CoinPanel;
import it.polimi.ingsw.View.GUI.IslesPanels.DenyCardPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Permits loading images that will be stored in order to use them in game.
 */
public class ImagesLoader {

    /**
     * Loads images on buttons.
     * @param buttons where to put the images.
     * @param finalI1 the index of the image to load.
     * @param url the path of the image.
     */
    protected static void loadImages(JButton[] buttons, int finalI1, InputStream url) {
        BufferedImage img = null;
        try {
            if (url != null)
                img = ImageIO.read(url);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        if (img != null)
            buttons[finalI1].setIcon(new ImageIcon(img));
    }

    /**
     * Loads images on the last button that the player pressed.
     * @param buttons where to put the image.
     * @param lastPressedButton last button pressed by the player.
     * @param url path of the image.
     */
    protected static void loadImagesOnLastPressedButton(JButton[] buttons, AtomicInteger lastPressedButton, InputStream url) {
        BufferedImage img = null;
        try {
            if (url != null)
                img = ImageIO.read(url);
        } catch (IOException ex) {
            System.err.println("Failed to load the image at path: " + url);
        }
        if (img != null)
            buttons[lastPressedButton.get()].setIcon(new ImageIcon(img));
    }

    /**
     * Loads the background image.
     * @return the background image.
     */
    protected static BufferedImage backgroundImageLoader() {
        InputStream url = InitialBackgroundPanel.class.getClassLoader().getResourceAsStream("Background.png");
        BufferedImage img= null;
        try {
            if (url != null)
                img = ImageIO.read(url);
        }catch (IOException e) {
            System.err.println("Failed to load the image at path: " + url);
        }
        return img;
    }

    /**
     * Loads a student image.
     * @param rc color of the student we want the image.
     * @return the image of the student of the chosen color.
     */
    public static BufferedImage studentsImagesLoader(RealmColors rc) {
        InputStream url;
        BufferedImage img = null;
        ClassLoader studentClassLoader = StudentButton.class.getClassLoader();
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
            System.err.println("Failed to load the image at path: " + url);
        }
        return img;
    }

    /**
     * Loads the students images using the index of the color.
     * @param colorIndex index associated to the students chosen.
     * @param buttons buttons to add the images with.
     */
    public static void studentsImagesLoaderInt(AtomicInteger colorIndex, JButton[] buttons) {
        ClassLoader cl = GUIDrawer.class.getClassLoader();
        InputStream url;
        switch (colorIndex.get()) {
            case 0 -> url = cl.getResourceAsStream("Dashboard/Students/Yellow.png");
            case 1 -> url = cl.getResourceAsStream("Dashboard/Students/Pink.png");
            case 2 -> url = cl.getResourceAsStream("Dashboard/Students/Blue.png");
            case 3 -> url = cl.getResourceAsStream("Dashboard/Students/Red.png");
            case 4 -> url = cl.getResourceAsStream("Dashboard/Students/Green.png");
            default -> url = cl.getResourceAsStream("Dashboard/Circles.png");
        }
        loadImagesOnLastPressedButton(buttons, colorIndex, url);
    }

    /**
     * Loads a checked student image.
     * @param rc color of the student we want the image.
     * @return the image of the checked student of the chosen color.
     */
    public static BufferedImage checkedStudentsImagesLoader(RealmColors rc) {
        InputStream url;
        BufferedImage img = null;
        ClassLoader studentClassLoader = StudentButton.class.getClassLoader();
        switch(rc) {
            case YELLOW -> url=studentClassLoader.getResourceAsStream("Dashboard/Students/YellowChk.png");
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
            System.err.println("Failed to load the image at path: " + url);
        }
        return img;
    }

    /**
     * Loads the checked students images using the index of the color.
     * @param colorIndex index associated to the students chosen.
     * @param buttons buttons to add the images with.
     */
    public static void checkedStudentsImagesLoaderInt(int colorIndex, JButton[] buttons) {
        ClassLoader cl = GUIDrawer.class.getClassLoader();
        InputStream url;
        switch (colorIndex) {
            case 0 -> url = cl.getResourceAsStream("Dashboard/Students/YellowChk.png");
            case 1 -> url = cl.getResourceAsStream("Dashboard/Students/PinkChk.png");
            case 2 -> url = cl.getResourceAsStream("Dashboard/Students/BlueChk.png");
            case 3 -> url = cl.getResourceAsStream("Dashboard/Students/RedChk.png");
            case 4 -> url = cl.getResourceAsStream("Dashboard/Students/GreenChk.png");
            default -> url = cl.getResourceAsStream("Dashboard/Circles.png");
        }
        loadImages(buttons, colorIndex, url);
    }

    /**
     * Loads a professor image.
     * @param rc the color of the professor.
     * @return the image of the professor of the given color.
     */
    public static BufferedImage professorImageLoader(RealmColors rc) {
        InputStream url;
        BufferedImage img = null;
        ClassLoader cl = DiningPanel.class.getClassLoader();
        switch(rc) {
            case YELLOW ->url = cl.getResourceAsStream("Dashboard/Professors/Yellow.png");
            case BLUE -> url = cl.getResourceAsStream("Dashboard/Professors/Blue.png");
            case RED -> url = cl.getResourceAsStream("Dashboard/Professors/Red.png");
            case PINK -> url = cl.getResourceAsStream("Dashboard/Professors/Pink.png");
            case GREEN -> url = cl.getResourceAsStream("Dashboard/Professors/Green.png");
            default -> url = cl.getResourceAsStream("Dashboard/Circles.png");
        }
        try {
            assert url != null;
            img = ImageIO.read(url);
        } catch (IOException e) {
            System.err.println("Failed to load the image at path: " + url);
        }
        return img;
    }

    /**
     * Loads a tower image.
     * @param tc color of the tower we want the image.
     * @return the image of the tower of the chosen color.
     */
    public static BufferedImage towersImagesLoader(TowerColors tc) {
        InputStream url;
        BufferedImage img = null;
        ClassLoader towerClassLoader = TowerButton.class.getClassLoader();
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
            System.err.println("Failed to load the image at path: " + url);
        }
        return img;
    }

    /**
     * Loads the image of the dashboard.
     * @return the image of the dashboard.
     */
    public static BufferedImage dashboardImageLoader() {
        InputStream url;
        BufferedImage img = null;
        url = GameScreenPanel.class.getClassLoader().getResourceAsStream("Dashboard/Semi_empty.png");
        try {
            if (url != null)
                img = ImageIO.read(url);
        } catch (IOException e) {
            System.err.println("Failed to load the image at path: " + url);
        }
        return img;
    }

    /**
     * Loads the Mother nature image and sets it as the icon for the button.
     * @return the image of mother nature.
     */
    public static BufferedImage motherNatureImageLoader(){
        InputStream url;
        url = MNButton.class.getClassLoader().getResourceAsStream("GameTable/mother_nature.png");
        BufferedImage img = null;
        try{
            assert url != null;
            img= ImageIO.read(url);
        }catch (IOException e){
            System.err.println("Failed to load the image at path: " + url);
        }
        return img;
    }

    /**
     * If showMage attribute is false, this method load and pass the Assistant card image
     * to the override paintComponent method in order to draw it on the panel.
     * The image is identified by the index of the card.
     * If the showMage attribute is true, this method load and pass the Mage image
     * according to the turnOrder passed to the constructor and set as attribute.
     * @param turnOrder the card turnOrder used to identify which image to load.
     * @param showMage when true then load the mage image, else the assistant card one.
     * @return the image loaded.
     */
    protected static BufferedImage assistantCardAndMageImageLoader(int turnOrder, Boolean showMage) {
        InputStream url = null;
        ClassLoader cl = AssistantCardPanel.class.getClassLoader();
        if(!showMage)
            url = cl.getResourceAsStream("GameTable/Assistant_cards/2x/" + turnOrder + ".png");
        else
            switch (turnOrder){
                case 0 -> url=cl.getResourceAsStream("GameTable/Assistant_cards/retro/MYSTICAL_WIZARD.png");
                case 1 -> url=cl.getResourceAsStream("GameTable/Assistant_cards/retro/WEALTHY_KING.png");
                case 2 -> url=cl.getResourceAsStream("GameTable/Assistant_cards/retro/CLEVER_WITCH.png");
                case 3 -> url=cl.getResourceAsStream("GameTable/Assistant_cards/retro/ANCIENT_SAGE.png");
            }
        BufferedImage img= null;
        try {
            if (url != null)
                img = ImageIO.read(url);
        }catch (IOException e) {
            e.printStackTrace();
        }
        return img;
    }

    /**
     * Loads the Assistant Card image that is identified by the turn order.
     * @param turnOrder the card turn order used to identify the card.
     */
    public static BufferedImage assistantCardImageLoader(int turnOrder){
        InputStream url = AssistantCardButton.class.getClassLoader().getResourceAsStream("GameTable/Assistant_cards/2x/" + turnOrder + ".png");
        BufferedImage img= null;
        try {
            if (url != null)
                img = ImageIO.read(url);
        }catch (IOException e) {
            System.err.println("Failed to load the image at path: " + url);
        }
        return img;
    }

    /**
     * Loads the image (either a check or a red cross).
     * @param exchange integer used to decide which image to set as icon.
     */
    public static BufferedImage exchangeChoiceImageLoader(int exchange) {
        InputStream url = null;
        ClassLoader cl = ExchangeChoiceButton.class.getClassLoader();
        switch(exchange){
            case 0 -> url = cl.getResourceAsStream("Raw/Check.png");
            case 1 -> url = cl.getResourceAsStream("Raw/RedCross.png");
        }
        BufferedImage img = null;
        try {
            if (url != null)
                img = ImageIO.read(url);
        }catch (IOException e) {
            System.err.println("Failed to load the image at path: " + url);
        }
        return img;
    }

    /**
     * Loads a student image based on the color integer.
     * @param color the integer associated to which image load and set as icon.
     */
    public static BufferedImage colorChoiceImageLoader(int color) {
        InputStream url = null;
        ClassLoader cl = ColorChoiceButton.class.getClassLoader();
        switch(color){
            case 0 -> url = cl.getResourceAsStream("Dashboard/Students/Yellow.png");
            case 1 -> url = cl.getResourceAsStream("Dashboard/Students/Pink.png");
            case 2 -> url = cl.getResourceAsStream("Dashboard/Students/Blue.png");
            case 3 -> url = cl.getResourceAsStream("Dashboard/Students/Red.png");
            case 4 -> url = cl.getResourceAsStream("Dashboard/Students/Green.png");
        }
        BufferedImage img = null;
        try {
            if (url != null)
                img = ImageIO.read(url);
        }catch (IOException e) {
            System.err.println("Failed to load the image at path: " + url);
        }
        return img;
    }

    /**
     * Loads the cloud image.
     * @return the cloud image.
     */
    public static BufferedImage cloudImageLoader() {
        InputStream url = CloudPanel.class.getClassLoader().getResourceAsStream("GameTable/Clouds/cloud_card.png");
        BufferedImage img= null;
        try {
            if (url != null)
                img = ImageIO.read(url);
        }catch (IOException e) {
            System.err.println("Failed to load the image at path: " + url);
        }
        return img;
    }

    /**
     * Loads the character card image.
     * @param character the name of the character card to load.
     * @return the image of the chosen character card.
     */
    public static BufferedImage characterCardImageLoader(String character) {
        InputStream url;
        ClassLoader cl = CharacterPanel.class.getClassLoader();
        switch (character){
            case "MONK" -> url = cl.getResourceAsStream("Character_cards/MONK.jpg");
            case "FARMER" -> url = cl.getResourceAsStream("Character_cards/FARMER.jpg");
            case "HERALD" -> url = cl.getResourceAsStream("Character_cards/HERALD.jpg");
            case "MAGICAL_LETTER_CARRIER" -> url = cl.getResourceAsStream("Character_cards/MAGICAL_LETTER_CARRIER.jpg");
            case "GRANDMA_HERBS" -> url = cl.getResourceAsStream("Character_cards/GRANDMA_HERBS.jpg");
            case "CENTAUR" -> url = cl.getResourceAsStream("Character_cards/CENTAUR.jpg");
            case "JESTER" -> url = cl.getResourceAsStream("Character_cards/JESTER.jpg");
            case "KNIGHT" -> url = cl.getResourceAsStream("Character_cards/KNIGHT.jpg");
            case "FUNGIST" -> url = cl.getResourceAsStream("Character_cards/FUNGIST.jpg");
            case "MINSTREL" -> url = cl.getResourceAsStream("Character_cards/MINSTREL.jpg");
            case "SPOILED_PRINCESS" -> url = cl.getResourceAsStream("Character_cards/SPOILED_PRINCESS.jpg");
            case "THIEF" -> url = cl.getResourceAsStream("Character_cards/THIEF.jpg");
            default -> throw new IllegalStateException("Unexpected value: " + character);
        }
        BufferedImage img=null;
        try{
            if (url != null) 
                img= ImageIO.read(url);
        }catch(IOException e){
            System.err.println("Failed to load the image at path: " + url);
        }
        return img;
    }

    /**
     * Loads the image of the coin.
     * @return the image of the money.
     */
    public static BufferedImage coinImageLoader() {
        InputStream url = CoinPanel.class.getClassLoader().getResourceAsStream("GameTable/Moneta_base.png");
        BufferedImage img = null;
        try{
            if (url != null)
                img = ImageIO.read(url);
        }catch(IOException e){
            System.err.println("Failed to load the image at path: " + url);
        }
        return img;
    }

    /**
     * Loads the deny card image.
     * @return the deny card image.
     */
    public static BufferedImage denyCardImageLoader() {
        InputStream url = DenyCardPanel.class.getClassLoader().getResourceAsStream("GameTable/Isles/deny_island_icon.png");
        BufferedImage img = null;
        try{
            if (url != null)
                img= ImageIO.read(url);
        }catch(IOException e){
            System.err.println("Failed to load the image at path: " + url);
        }
        return img;
    }

    /**
     * Loads the cloud image
     * @param cl the class loader used for the coin
     * @return the coin image
     */
    public static BufferedImage coinsImageLoader(ClassLoader cl){
        InputStream url=cl.getResourceAsStream("GameTable/Moneta_base.png");
        BufferedImage img=null;
        try{
            if(url!=null)
                img=ImageIO.read(url);
        }catch (IOException e) {
            e.printStackTrace();
        }
        return img;
    }

    /**
     * Loads the isles images
     * @param cl the class loader used for the isles
     * @param i the index used to identify which image to load
     * @return the isle image
     */
    public static BufferedImage isleImageLoader(ClassLoader cl, int i){
        InputStream url;
        BufferedImage img = null;
        url = cl.getResourceAsStream("GameTable/Isles/island"+i+".png");
        try {
            assert url != null;
            img = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return img;
    }

}

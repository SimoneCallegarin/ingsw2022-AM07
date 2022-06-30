package it.polimi.ingsw.View.GUI;

import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Model.Enumeration.TowerColors;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**
 * Permits loading images that will be stored in order to use them in game.
 */
public class ImagesLoader {

    /**
     * Loads a student image.
     * @param rc color of the student we want the image.
     * @param studentClassLoader class loader used for the students images.
     * @return the image of the student of the chosen color.
     */
    public static BufferedImage studentsImagesLoader(RealmColors rc, ClassLoader studentClassLoader) {
        InputStream url;
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
        return img;
    }

    /**
     * Loads a checked student image.
     * @param rc color of the student we want the image.
     * @param studentClassLoader class loader used for the students images.
     * @return the image of the checked student of the chosen color.
     */
    public static BufferedImage checkedStudentsImagesLoader(RealmColors rc, ClassLoader studentClassLoader) {
        InputStream url;
        BufferedImage img = null;
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
            e.printStackTrace();
        }
        return img;
    }

    /**
     * Loads a professor image.
     * @param rc the color of the professor.
     * @param cl the class loader of the professor.
     * @return the image of the professor of the given color.
     */
    public static BufferedImage professorImageLoader(RealmColors rc, ClassLoader cl) {
        InputStream url;
        BufferedImage img = null;
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
            e.printStackTrace();
        }
        return img;
    }

    /**
     * Loads a tower image.
     * @param tc color of the tower we want the image.
     * @param towerClassLoader class loader used for the towers images.
     * @return the image of the tower of the chosen color.
     */
    public static BufferedImage towersImagesLoader(TowerColors tc, ClassLoader towerClassLoader) {
        InputStream url;
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
        return img;
    }

    /**
     * Loads the image of the dashboard.
     * @param cl class loader used for the dashboard image.
     * @return the image of the dashboard.
     */
    public static BufferedImage dashboardImageLoader(ClassLoader cl) {
        InputStream url;
        BufferedImage img = null;
        url = cl.getResourceAsStream("Dashboard/Semi_empty.png");
        try {
            if (url != null)
                img = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return img;
    }

    /**
     * Loads the Mother nature image and sets it as the icon for the button.
     * @param cl the class loader used for mother nature.
     */
    public static BufferedImage motherNatureImageLoader(ClassLoader cl){
        InputStream url;
        url = cl.getResourceAsStream("GameTable/mother_nature.png");
        BufferedImage img = null;
        try{
            assert url != null;
            img= ImageIO.read(url);
        }catch (IOException e){
            e.printStackTrace();
        }
        return img;
    }

    /**
     * Loads the Assistant Card image that is identified by the turn order.
     * @param turnOrder the card turn order used to identify the card.
     * @param cl the class loader used for the assistant card.
     */
    public static BufferedImage assistantCardImageLoader(int turnOrder, ClassLoader cl){
        InputStream url = cl.getResourceAsStream("GameTable/Assistant_cards/2x/" + turnOrder + ".png");
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
     * Loads the image (either a check or a red cross).
     * @param exchange integer used to decide which image to set as icon.
     * @param cl the class loader used for the exchange choice.
     */
    public static BufferedImage exchangeChoiceImageLoader(int exchange, ClassLoader cl) {
        InputStream url = null;
        switch(exchange){
            case 0 -> url = cl.getResourceAsStream("Raw/Check.png");
            case 1 -> url = cl.getResourceAsStream("Raw/RedCross.png");
        }
        BufferedImage img = null;
        try {
            if (url != null)
                img = ImageIO.read(url);
        }catch (IOException e) {
            e.printStackTrace();
        }
        return img;
    }

    /**
     * Loads a student image based on the color integer.
     * @param color the integer associated to which image load and set as icon.
     * @param cl the class loader used for color choice.
     */
    public static BufferedImage colorChoiceImageLoader(int color, ClassLoader cl) {
        InputStream url = null;
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
            e.printStackTrace();
        }
        return img;
    }

    /**
     * Loads the cloud image.
     * @param cl the class loader used for the cloud.
     * @return the cloud image.
     */
    public static BufferedImage cloudImageLoader(ClassLoader cl) {
        InputStream url = cl.getResourceAsStream("GameTable/Clouds/cloud_card.png");
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

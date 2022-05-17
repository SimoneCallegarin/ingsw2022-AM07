package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.CharacterCards.CharacterCardsName;
import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Model.Enumeration.TowerColors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameTableModelView {

    // We used records for each component of the game table because each time there's a move that changes
    // the game table the UpdateHandler will create a new variable record containing all the updated information,
    // and it will place it in the store removing the previous one.

    public static class CharacterCard {

        String characterCardName;
        int characterCardCost;
        HashMap<RealmColors, Integer> studentsOnCharacterCard;
        int denyCardsOnCharacterCard;

        public CharacterCard(String characterCardName, int characterCardCost, HashMap<RealmColors, Integer> studentsOnCharacterCard, int denyCardsOnCharacterCard) {
            this.characterCardName = characterCardName;
            this.characterCardCost = characterCardCost;
            this.studentsOnCharacterCard = studentsOnCharacterCard;
            this.denyCardsOnCharacterCard = denyCardsOnCharacterCard;
        }

        public String getCharacterCardName() { return characterCardName; }

        public int getCharacterCardCost() { return characterCardCost; }

        public int getStudentsByColor(RealmColors color) { return studentsOnCharacterCard.get(color); }

        public int getDenyCardsOnCharacterCard() { return denyCardsOnCharacterCard; }

    }

    public static class Isle {

        private final HashMap<RealmColors, Integer> studentsOnIsle;
        private final int towerNumber;
        private final TowerColors towerColor;
        private final int denyCardsOnIsle;
        private final boolean motherNatureIsPresent;

        public Isle(HashMap<RealmColors, Integer> studentsOnIsle, boolean motherNatureIsPresent) {
            this.studentsOnIsle = studentsOnIsle;
            this.towerNumber = 0;
            this.towerColor = TowerColors.NOCOLOR;
            this.denyCardsOnIsle = 0;
            this.motherNatureIsPresent = motherNatureIsPresent;
        }

        public int getStudentsByColor(RealmColors color) { return studentsOnIsle.get(color); }

        public int getTowerNumber() { return towerNumber; }

        public TowerColors getTowerColor() { return towerColor; }

        public int getDenyCardsOnIsle() { return denyCardsOnIsle; }

        public boolean isMotherNaturePresent() { return motherNatureIsPresent; }

    }

    public record Cloud(HashMap<RealmColors, Integer> studentsOnCloud) {

        public int getStudentsByColor(RealmColors color) { return studentsOnCloud.get(color); }

    }

    private final ArrayList<CharacterCard> characterCards;
    private final ArrayList<Isle> isles;
    private final ArrayList<Cloud> clouds;
    private int generalMoneyReserve;

    public GameTableModelView(ArrayList<CharacterCard> characterCards, ArrayList<Isle> isles, ArrayList<Cloud> clouds, int generalMoneyReserve) {
        this.characterCards = characterCards;
        this.isles = isles;
        this.clouds = clouds;
        this.generalMoneyReserve = generalMoneyReserve;
    }

    public CharacterCard getCharacterCard(int characterCardIndex) { return characterCards.get(characterCardIndex); }

    public int getCharacterCardStudents(int characterCardIndex) {
        int sum=0;
        for (RealmColors color : RealmColors.values())
            sum = sum + getCharacterCard(characterCardIndex).getStudentsByColor(color);
        return sum;
    }

    public Isle getIsle(int isleID) { return isles.get(isleID); }

    public ArrayList<Isle> getIsles() { return isles; }

    public Cloud getCloud(int cloudID) { return clouds.get(cloudID); }

    public int getGeneralMoneyReserve() { return generalMoneyReserve; }

    // SETTERS:

    public void setCharacterCard(int characterCardIndex, CharacterCard characterCard) {
        this.characterCards.remove(characterCardIndex);
        this.characterCards.add(characterCardIndex,characterCard);
    }

    public void setIsles(int isleID, Isle isle) {
        this.isles.remove(isleID);
        this.isles.add(isleID,isle);
    }

    public void setClouds(int cloudID, Cloud cloud) {
        this.clouds.remove(cloudID);
        this.clouds.add(cloud);
    }

    public void setGeneralMoneyReserve(int generalMoneyReserve) { this.generalMoneyReserve = generalMoneyReserve; }
}

package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.Enumeration.RealmColors;

import java.util.ArrayList;
import java.util.HashMap;

public class GameTableModelView {

    // We used records for each component of the game table because each time there's a move that changes
    // the game table the UpdateHandler will create a new variable record containing all the updated information,
    // and it will place it in the store removing the previous one.

    public record CharacterCard(int characterCardCost,
                                HashMap<RealmColors, Integer> studentsOnCharacterCard,
                                int denyCardsOnCharacterCard) {

        public int getCharacterCardCost() { return characterCardCost; }

        public int getStudentsByColor(RealmColors color) { return studentsOnCharacterCard.get(color); }

        public int getDenyCardsOnCharacterCard() { return denyCardsOnCharacterCard; }

    }

    public record Isle(HashMap<RealmColors, Integer> studentsOnIsle,
                       int denyCardsOnIsle, boolean motherNatureIsPresent) {

        public int getStudentsByColor(RealmColors color) { return studentsOnIsle.get(color); }

        public int getDenyCardsOnIsle() { return denyCardsOnIsle; }

        public boolean isMotherNatureIsPresent() { return motherNatureIsPresent; }

    }

    public record Cloud(HashMap<RealmColors, Integer> studentsOnCloud) {

        public int getStudentsByColor(RealmColors color) { return studentsOnCloud.get(color); }

    }

    private ArrayList<CharacterCard> characterCards;
    private ArrayList<Isle> isles;
    private ArrayList<Cloud> clouds;
    private int generalMoneyReserve;

    public GameTableModelView(ArrayList<CharacterCard> characterCards, ArrayList<Isle> isles, ArrayList<Cloud> clouds, int generalMoneyReserve) {
        this.characterCards = characterCards;
        this.isles = isles;
        this.clouds = clouds;
        this.generalMoneyReserve = generalMoneyReserve;
    }

    public CharacterCard getCharacterCard(int characterCardIndex) { return characterCards.get(characterCardIndex); }

    public Isle getIsle(int isleID) { return isles.get(isleID); }

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

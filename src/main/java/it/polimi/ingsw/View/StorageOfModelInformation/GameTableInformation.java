package it.polimi.ingsw.View.StorageOfModelInformation;

import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Model.Enumeration.TowerColors;

import java.util.ArrayList;
import java.util.HashMap;

public class GameTableInformation {

    // We used records for each component of the game table because each time there's a move that changes
    // the game table the UpdateHandler will create a new variable record containing all the updated information,
    // and it will place it in the store removing the previous one.

    public record CharacterCard(String characterCardName, int characterCardCost,
                                HashMap<RealmColors,Integer> studentsOnCharacterCard,
                                int denyCardsOnCharacterCard,
                                String description) {

        public String getCharacterCardName() { return characterCardName; }

        public int getCharacterCardCost() { return characterCardCost; }

        public int getNumberOfStudents() {
            int sum=0;
            for (RealmColors colors : RealmColors.values())
                sum = sum + studentsOnCharacterCard.get(colors);
            return sum;
        }

        public int getStudentsByColor(RealmColors color) { return studentsOnCharacterCard.get(color); }

        public int getDenyCardsOnCharacterCard() { return denyCardsOnCharacterCard; }

        public String getDescription() { return description; }

    }

    public record Isle(HashMap<RealmColors,Integer> studentsOnIsle, int towerNumber, TowerColors towerColor, int denyCards, boolean motherNatureIsPresent)  {

        public int getStudentsByColor(RealmColors color) { return studentsOnIsle.get(color); }

        public int getTowerNumber() { return towerNumber; }

        public TowerColors getTowerColor() { return towerColor; }

        public int getDenyCardsNumber() { return denyCards; }

        public boolean isMotherNaturePresent() { return motherNatureIsPresent; }

    }

    public record Cloud(HashMap<RealmColors, Integer> studentsOnCloud) {

        public int getStudentsByColor(RealmColors color) { return studentsOnCloud.get(color); }

        public int getStudentsOnCloud() {
            int sum=0;
            for (RealmColors color : RealmColors.values())
                sum = studentsOnCloud.get(color) + sum;
            return sum;
        }

    }

    private final ArrayList<CharacterCard> characterCards;
    private ArrayList<Isle> isles;
    private final ArrayList<Cloud> clouds;
    private int generalMoneyReserve;

    public GameTableInformation(ArrayList<CharacterCard> characterCards, ArrayList<Isle> isles, ArrayList<Cloud> clouds, int generalMoneyReserve) {
        this.characterCards = characterCards;
        this.isles = isles;
        this.clouds = clouds;
        this.generalMoneyReserve = generalMoneyReserve;
    }

    public CharacterCard getCharacterCard(int characterCardIndex) { return characterCards.get(characterCardIndex); }

    public Isle getIsle(int isleID) { return isles.get(isleID); }

    public ArrayList<Isle> getIsles() { return isles; }

    public Cloud getCloud(int cloudID) { return clouds.get(cloudID); }

    public int getNumOfClouds() { return clouds.size(); }

    public int getGeneralMoneyReserve() { return generalMoneyReserve; }

    // SETTERS:

    public void setCharacterCard(int characterCardIndex, CharacterCard characterCard) {
        this.characterCards.remove(characterCardIndex);
        this.characterCards.add(characterCardIndex,characterCard);
    }

    public void setStudentsOnIsle(int isleID, HashMap<RealmColors,Integer> studentsOnIsle) {
        Isle newIsle = new Isle(studentsOnIsle,isles.get(isleID).towerNumber,isles.get(isleID).towerColor,isles.get(isleID).denyCards,isles.get(isleID).motherNatureIsPresent);
        isles.remove(isleID);
        isles.add(isleID,newIsle);
    }

    public void setDenyOnIsle(int isleID, int denyCard) {
        Isle newIsle = new Isle(isles.get(isleID).studentsOnIsle,isles.get(isleID).towerNumber,isles.get(isleID).towerColor,denyCard,isles.get(isleID).motherNatureIsPresent);
        isles.remove(isleID);
        isles.add(isleID,newIsle);
    }

    public void setNewIsle(int isleID, Isle isle) {
        this.isles.remove(isleID);
        this.isles.add(isleID,isle);
    }

    public void setIsles(ArrayList<Isle> isles){ this.isles = isles; }

    public void setCloud(int cloudID, Cloud cloud) {
        this.clouds.remove(cloudID);
        this.clouds.add(cloudID, cloud);
    }

    public void setGeneralMoneyReserve(int generalMoneyReserve) { this.generalMoneyReserve = generalMoneyReserve; }
}

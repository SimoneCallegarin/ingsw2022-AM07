package it.polimi.ingsw.View;

import it.polimi.ingsw.Model.Enumeration.RealmColors;

import java.util.ArrayList;
import java.util.HashMap;

public class GameTableModelView {

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

    public CharacterCard getCharacterCard(int index) { return characterCards.get(index); }

    public Isle getIsle(int index) { return isles.get(index); }

    public Cloud getCloud(int index) { return clouds.get(index); }

    public int getGeneralMoneyReserve() { return generalMoneyReserve; }

    public void setCharacterCards(ArrayList<CharacterCard> CharacterCards) { this.characterCards = CharacterCards; }

    public void setCharacterCard(int index, CharacterCard characterCard) {
        characterCards.remove(index);
        characterCards.add(index,characterCard);
    }

    public void setIsles(ArrayList<Isle> Isles) { this.isles = Isles; }

    public void setClouds(ArrayList<Cloud> Clouds) { this.clouds = Clouds; }

    public void setGeneralMoneyReserve(int generalMoneyReserve) { this.generalMoneyReserve = generalMoneyReserve; }
}

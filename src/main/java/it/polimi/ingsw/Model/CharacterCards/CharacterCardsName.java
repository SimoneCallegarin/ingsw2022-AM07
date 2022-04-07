package it.polimi.ingsw.Model.CharacterCards;

import it.polimi.ingsw.Model.Enumeration.Mages;

public enum CharacterCardsName {
    MONK,
    FARMER,
    HERALD,
    MAGICAL_LETTER_CARRIER,
    GRANDMA_HERBS,
    CENTAUR,
    JESTER,
    KNIGHT,
    FUNGIST,
    MINSTREL,
    SPOILED_PRINCESS,
    THIEF;

    public CharacterCardsName getCharacterCardName(int index){
        return switch (index) {
            case 0 -> MONK;
            case 1 -> FARMER;
            case 2 -> HERALD;
            case 3 -> MAGICAL_LETTER_CARRIER;
            case 4 -> GRANDMA_HERBS;
            case 5 -> CENTAUR;
            case 6 -> JESTER;
            case 7 -> KNIGHT;
            case 8 -> FUNGIST;
            case 9 -> MINSTREL;
            case 10 -> SPOILED_PRINCESS;
            case 11 -> THIEF;
            default -> null;
        };
    }

}

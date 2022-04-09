package it.polimi.ingsw.Model.CharacterCards;

/**
 * The name associated to each character card of the game
 */
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

    /**
     * this method permits to associate a card to a number that
     * is used in the initial extraction of three character cards
     * @param index associated to the character card
     * @return the name of the character card associated to that index
     */
    public static CharacterCardsName getCharacterCardName(int index){
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

    /**
     * this method associate a character card to his relative cost at the start of the game
     * @param characterCardName the name of the character card we want to know the cost
     * @return the cost of that character card
     */
    public int getCharacterCardStartingCost(CharacterCardsName characterCardName){
        return switch (characterCardName) {
            case MONK, MAGICAL_LETTER_CARRIER, JESTER, MINSTREL -> 1;
            case FARMER, GRANDMA_HERBS, KNIGHT, SPOILED_PRINCESS -> 2;
            case HERALD, THIEF, CENTAUR, FUNGIST -> 3;
        };
    }

}

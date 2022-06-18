package it.polimi.ingsw.Model.CharacterCards;

/**
 * The name associated to each character card of the game.
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
     * Associates a card to a number that is used in the initial extraction of three character cards.
     * @param index associated to the character card.
     * @return the name of the character card associated to that index.
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
            default -> throw new IllegalStateException("Unexpected value: " + index);
        };
    }

    /**
     * Associates a character card to his relative cost at the start of the game.
     * @param characterCardName the name of the character card we want to know the cost.
     * @return the cost of that character card.
     */
    public int getCharacterCardStartingCost(CharacterCardsName characterCardName) {
        return switch (characterCardName) {
            case MONK, MAGICAL_LETTER_CARRIER, JESTER, MINSTREL -> 1;
            case FARMER, GRANDMA_HERBS, KNIGHT, SPOILED_PRINCESS -> 2;
            case HERALD, THIEF, CENTAUR, FUNGIST -> 3;
        };
    }

    /**
     * Associates a character card to his relative effect description.
     * @param characterCardName the name of the character card we want to know the effect.
     * @return the description of the effect of a certain character card.
     */
    public String getCharacterCardDescription(CharacterCardsName characterCardName) {
        return switch (characterCardName){
            case MONK                   -> "Take 1 Student from this card and place it on an Island of your choice.\nThen, draw a new Student from the Bag and place it on this card.";
            case FARMER                 -> "During this turn, you take control of any number of Professors\neven if you have the same number of Students as the player who currently controls them.";
            case HERALD                 -> "Choose an Island and resolve the Island as if Mother Nature had ended her movement there.\nMother Nature will still move and the Island where she ends her movement will also be resolved.";
            case MAGICAL_LETTER_CARRIER -> "You may move Mother Nature up to 2 additional Islands\nthan is indicated by the Assistant card you've played.";
            case GRANDMA_HERBS          -> "Place a NoEntry tile on an Island of your choice.\nThe first time Mother Nature ends her movement there, put the No Entry tile back onto this card\n(DO NOT calculate influence on that Island, or place any Towers).";
            case CENTAUR                -> "When resolving a Conquering on an Island, Towers do not count towards influence.";
            case JESTER                 -> "You may take up to 3 Students from this card and\nreplace them with the same number of Students from your Entrance.";
            case KNIGHT                 -> "During the influence calculation this turn, you count as having 2 more influence.";
            case FUNGIST                -> "Choose a color of Student:\nduring the influence calculation this turn, that color adds no influence.";
            case MINSTREL               -> "You may exchange up to 2 Students between your Entrance and your Dining Room.";
            case SPOILED_PRINCESS       -> "Take 1 Student from this card and place it in your Dining Room.\nThen, draw a new Student from the Bag and place it on this card.";
            case THIEF                  -> "Choose a type of Student:\nevery player (including yourself) must return 3 Students of that type from their Dining Room to the bag.\nIf any player has fewer than 3 Students of that type, return as many Students as they have.";
        };
    }

}

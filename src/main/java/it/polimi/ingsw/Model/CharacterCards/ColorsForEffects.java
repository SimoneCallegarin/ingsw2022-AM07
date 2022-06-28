package it.polimi.ingsw.Model.CharacterCards;

/**
 * These are the value that refer to what color use when call an effect.
 * RANDOM: refers to an extraction of students from the bag that is randomized and doesn't need to know a color first.
 * SELECT: refers to the fact that when calling an effect, it needs to know from the user what is the color of the students it needs to move.
 */
enum ColorsForEffects {
    RANDOM,
    SELECT
}

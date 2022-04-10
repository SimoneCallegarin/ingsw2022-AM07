package it.polimi.ingsw.Model.CharacterCards;

/**
 * those are the value that refer to what color use when call an effect
 * NONE: refers to deny cards and for any recalculate effect that doesn't need a color because it doesn't pick students
 * RANDOM: refers to an extraction of students from the bag that is randomized and doesn't need to know a color first
 * SELECT: refers to the fact that when calling an effect, it needs to know from the user what is the color of the students it needs to move
 */
public enum ColorsForEffects {
    NONE, RANDOM, SELECT
}

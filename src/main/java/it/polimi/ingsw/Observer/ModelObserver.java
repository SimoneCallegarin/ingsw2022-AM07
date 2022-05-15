package it.polimi.ingsw.Observer;

import it.polimi.ingsw.Model.Enumeration.CurrentOrder;

public interface ModelObserver {
    //after an assistant card is played the view need to refresh:
    //-the assistant card available
    //-the discard pile
    void onAssistantCard(int idPlayer, int turnOrderPlayed, CurrentOrder currentOrder);

    //after a student is moved to the Dining Room the view need to refresh:
    // -the current player dashboard
    // -the dashboard of the player that eventually loses a professor
    void onStudentMoving_toDining(int idPlayer, int colorIndex);

    //after a student is moved to an Isle the view need to refresh:
    //-the current player dashboard
    //-the isle where the student is moved
    void onStudentMoving_toIsle(int idPlayer, int idIsle, int colorIndex);

    //after a player decides where to move Mother Nature the view need to refresh:
    //-the isle where MN was before the movement
    //-the isle where MN is moved
    //-the isles that eventually unify
    void onMNMovement();

    //after a player decides a cloud the view need to refresh:
    //-the chosen cloud
    //-the current player dashboard entrance
    void onCloudChoice();

    //after a player decides a character card to play the view need to refresh:
    //-the price of the character card
    //-the general reserve
    //-the player money reserve
    //then the model will call other Observer methods to manage the character effect
    void onCharacterCard();


}

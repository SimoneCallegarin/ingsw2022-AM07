package it.polimi.ingsw.Model.Player;

import it.polimi.ingsw.Model.CharacterCards.CharacterCard;
import it.polimi.ingsw.Model.DashboardObjects.Dashboard;
import it.polimi.ingsw.Model.Enumeration.*;
import it.polimi.ingsw.Model.Interface.StudentManager;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represent one single player
 */
public class Player {

    /**
     * This attribute is the nickname of the player
     */
    public String nickname;
    /**
     * This attribute indicates the team of the player in a 4 players game
     */
    public Squads squad;
    /**
     * This attribute represents the name of the mage deck chosen
     */
    public Mages mage;
    /**
     * This attribute is the deck of assistant cards
     */
    public ArrayList<AssistantCard> mageDeck;
    /**
     * This attribute is the card on the top of the discards pile
     */
    public AssistantCard discardPile;
    /**
     * This attribute represents the Dashboard associated with the player
     */
    private final Dashboard dashboard;
    /**
     * This attribute represents the game mode
     */
    public GameMode gameMode;
    /**
     * This attribute represents the number of money of a player
     */
    private int money;
    /**
     * This attribute indicates if a player already played a card in a turn when set true
     */
    private boolean alreadyPlayedACardThisTurn;
    /**
     * This attribute represents the order number in the turn for the player
     */
    private CurrentOrder order;

    /**
     * Constructor
     * @param nickname    valid nickname chosen by the player
     * @param squad       team chosen by the player
     * @param gameMode    game mode of the game where the player have to play, used to add function to the player
     */
    public Player(String nickname, int numOfPlayers, int idDashboard, Squads squad, GameMode gameMode) {
        this.nickname = nickname;
        this.squad = squad;
        this.gameMode = gameMode;

        this.dashboard = new Dashboard(numOfPlayers, idDashboard, gameMode);

        this.mage = Mages.getMage(idDashboard);
        this.mageDeck = new ArrayList<>(10);
        this.discardPile = null;

        /**
         * This method permits the creation of a deck of assistant cards
         * i+1 is equal to the turn order of the assistant card
         * (i/2)+1 is equal to the mother nature possible movement for an assistant card
         * false indicates that the assistant card hasn't been used yet
         */
        for(int i = 0; i < 10; i++) {
            this.mageDeck.add(i, new AssistantCard(i+1, (i/2)+1));
        }

        this.money = 0;
        if (gameMode.equals(GameMode.EXPERT))
            this.money = 1;

        this.alreadyPlayedACardThisTurn = false;

    }

    public Squads getSquad() {return squad;}

    public AssistantCard getDiscardPile() {return discardPile;}

    public void setDiscardPileNull() {
        discardPile = null;
    }

    public AssistantCard getAssistantCardByTurnOrder(int turnOrder) {
        AssistantCard assistantCardFound = null;
        for (AssistantCard ac : mageDeck) {
            if (ac.getTurnOrder() == turnOrder) {
                assistantCardFound = ac;
                break;
            }
        }
        return assistantCardFound;
    }

    /**
     * This method permits the player to select an assistant card to play
     */
    public void playAssistantCard(AssistantCard assistantCard){
        this.discardPile = assistantCard;
        this.mageDeck.remove(assistantCard);
    }

    /**
     * this method permits to set the current player order in the turn
     * @param order of the player this turn
     */
    public void setOrder(CurrentOrder order) {
        this.order = order;
    }

    /**
     * this method gives what is the current player turn order
     * @return the turn order of the player
     */
    public CurrentOrder getOrder() {
        return this.order;
    }

    /**
     * getter method that gives which is the dashboard associated to the current player
     * @return the dashboard of the current player
     */
    public Dashboard getDashboard() {return dashboard;}

    /**
     * getter method that gives the assistant cards of the current player
     * @return the deck of assistant cards of the player
     */
    public List<AssistantCard> getMageDeck() {
        return mageDeck;
    }

    /**
     * getter method that gives the number of money of the player
     * @return number of money of the player
     */
    public int getMoney() {
        return money;
    }

    /**
     * getter method that permits to know if an assistant card has been already used by the player
     * @return true if already used, else false
     */
    public boolean getAlreadyPlayedACardThisTurn() {
        return alreadyPlayedACardThisTurn;
    }

    /**
     * this method increase the number of money of the player by 1
     */
    public void gainMoney(){
        this.money += 1;
    }

    /**
     * this method permits the player to play a character card
     * @param characterCard the selected character card that the player wants to play
     */
    public void playCharacterCard(CharacterCard characterCard){
        if (money>=characterCard.getCost()){
            money = money - characterCard.getCost();
            characterCard.isUsed();
            alreadyPlayedACardThisTurn = true;
        }
    }

    /**
     * this method interfaces with the controller and the view to pick a student color
     * the player choose a student to pick from the student manager where it is and the view gives the color
     * chosen to the controller that sends it to the model
     * @param from the student manager from where choose the color of the student the player wants to move
     * @param color the color of the student the player picked
     * @return the color of the student the player picked
     */
    public RealmColors selectColor(StudentManager from, RealmColors color){     //NEEDS TO BE UPDATED WHEN VIEW AND CONTROLLER WILL BE IMPLEMENTED
        if(from.getStudentsByColor(color)==0)
            return  null;
        else
            return color;
    }

    /**
     * this method interfaces with the controller and the view to pick the number of students the player wants to move
     * the player choose a number of students to pick from the student manager where it is and the view gives this number
     * chosen to the controller that sends it to the model
     * @param from the student manager from where choose the number of the students the player wants to move
     * @param numberOfStudents the number of students the player picked
     * @return the number of students the player picked
     */
    public int selectNumberOfStudentsToMove(StudentManager from, int numberOfStudents){
        if(from.getNumberOfStudents()<numberOfStudents)
            return from.getNumberOfStudents();
        else
            return numberOfStudents;
    }

    /**
     * this method interfaces with the controller and the view to pick the isle chosen by the player
     * @param isleID the ID of the isle chosen by the player
     * @return the ID of the isle chosen by the player
     */
    public int selectIsleId(int isleID){
        return isleID;
    }

}
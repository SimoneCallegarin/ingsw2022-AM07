package it.polimi.ingsw.Model.Player;

import it.polimi.ingsw.Model.CharacterCards.CharacterCard;
import it.polimi.ingsw.Model.CharacterCards.CharacterCardsName;
import it.polimi.ingsw.Model.DashboardObjects.Dashboard;
import it.polimi.ingsw.Model.Enumeration.*;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represent one single player
 */
public class Player {

    /**
     * This attribute is the nickname of the player
     */
    private String nickname;
    /**
     * This attribute indicates the team of the player in a 4 players game
     */
    private final Squads squad;
    /**
     * This attribute represents the name of the mage deck chosen
     */
    private final Mages mage;
    /**
     * This attribute is the deck of assistant cards
     */
    private ArrayList<AssistantCard> mageDeck;
    /**
     * This attribute is the card on the top of the discards pile
     */
    private AssistantCard discardPile;
    /**
     * This attribute represents the Dashboard associated with the player
     */
    private final Dashboard dashboard;
    /**
     * This attribute represents the number of money of a player
     */
    private int money;
    /**
     * This attribute indicates if a player already played a card in a turn when set true
     */
    private boolean alreadyPlayedACardThisTurn = false;
    /**
     * this attribute indicates the name of the character card played this turn
     */
    private CharacterCardsName characterCardPlayed;
    /**
     * This attribute represents the order number in the turn for the player
     */
    private CurrentOrder order;
    /**
     * it saves when the assistant card has been played during last planning phase
     */
    private int cardOrder = 0;

    /**
     * Constructor
     * @param nickname    valid nickname chosen by the player
     * @param squad       team chosen by the player
     */
    public Player(String nickname, int numOfPlayers, int idDashboard, Squads squad) {
        this.nickname = nickname;
        this.squad = squad;

        this.dashboard = new Dashboard(numOfPlayers, idDashboard);

        this.mage = Mages.getMage(idDashboard);
        this.mageDeck = new ArrayList<>(10);
        this.discardPile = new AssistantCard(0,0);

        /*
          This method permits the creation of a deck of assistant cards
          i+1 is equal to the turn order of the assistant card
          (i/2)+1 is equal to the mother nature possible movement for an assistant card
          false indicates that the assistant card hasn't been used yet
         */
        for(int i = 0; i < 10; i++) {
            this.mageDeck.add(i, new AssistantCard(i+1, (i/2)+1));
        }

        this.money = 1;
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

    public boolean isMageDeckEmpty() {
        return mageDeck.isEmpty();
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
        money = money - characterCard.getCost();
        if(!characterCard.isUsed())
            characterCard.setUsed();
        alreadyPlayedACardThisTurn = true;
        characterCardPlayed = characterCard.getCharacterCardName();
    }

    /**
     * this method permits knowing which character card the player played this turn
     * @return the name of the character card played this turn
     */
    public CharacterCardsName getCharacterCardPlayed() {
        if(getAlreadyPlayedACardThisTurn())
            return characterCardPlayed;
        else
            return null;
    }

    /**
     * this method interfaces with the controller and the view to pick the isle chosen by the player
     * @param isleID the ID of the isle chosen by the player
     * @return the ID of the isle chosen by the player
     */
    public int selectIsleId(int isleID){
        return isleID;
    }

    /**
     * Getter method for the nickname.
     * @return the nickname of the player.
     */
    public String getNickname() { return nickname; }

    /**
     * Getter method for the player team.
     * @return the team of the player.
     */
    public Squads getSquad() {return squad;}

    /**
     * Getter method for the discard pile.
     * @return the assistant card on the top of the discard pile.
     */
    public AssistantCard getDiscardPile() {return discardPile;}

    /**
     * Setter method to set the discard pile to null value.
     */
    public void setDiscardPileNull() { discardPile = null; }

    /**
     * Getter method for the player turn order.
     * @return the turn order of the player.
     */
    public CurrentOrder getOrder() {
        return this.order;
    }

    /**
     * Getter method for the assistant card order
     * @return the order of the assistant card
     */
    public int getCardOrder() { return cardOrder; }

    /**
     * Setter method for the assistant card order
     * @param cardOrder of the assistant card
     */
    public void setCardOrder(int cardOrder) { this.cardOrder = cardOrder;}

    /**
     * this method permits to set the current player order in the turn
     * @param order of the player this turn
     */
    public void setOrder(CurrentOrder order) {
        this.order = order;
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
     * set to false the attribute alreadyPlayedACardThisTurn
     */
    public void setNotAlreadyPlayedACardThisTurn() { this.alreadyPlayedACardThisTurn = false; }

}
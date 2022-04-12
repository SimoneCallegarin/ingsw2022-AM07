package it.polimi.ingsw.Model.GameTableObjects;

import it.polimi.ingsw.Model.CharacterCards.CharacterCard;
import it.polimi.ingsw.Model.CharacterCards.CharacterCardsName;
import it.polimi.ingsw.Model.CharacterCards.EffectSetupFactory;
import it.polimi.ingsw.Model.Enumeration.GameMode;
import it.polimi.ingsw.Model.Interface.DenyCardManager;

import java.util.ArrayList;

public class GameTable implements DenyCardManager {

    /**
     * this is the list that contains all the clouds used in the game
     * its dimension is equal to the number of players
     */
    private final ArrayList<Cloud> clouds;
    /**
     * this is the isle manager that will manage the isles
     */
    private final IsleManager isleManager;
    /**
     * this is the bag that will be filled with 130 students (26 per color)
     */
    private final Bag bag;
    /**
     * this is the list of playable character cards (3 for an expert game mode, 0 for the base one)
     */
    private final ArrayList<CharacterCard> characterCards;
    /**
     * used to place them on the character card of the granny herbs
     */
    private int denyCards;
    /**
     * this is the factory that permit the set up of the character cards that need it
     */
    private final EffectSetupFactory effectSetupFactory = new EffectSetupFactory();

    /**
     * this is the general money reserve that is filled with 20 money
     * it is updated when a player plays a character card
     * or when he fill the space of the dining room that gives money
     */
    private final int generalMoneyReserve;

    /**
     * GameTable constructor
     * @param numberOfPlayers that are going to play the game, chosen by the first player that joins the match
     * @param gameMode of the game, chosen by the first player that joins the match
     */
    public GameTable(int numberOfPlayers, GameMode gameMode) {
        this.isleManager = new IsleManager();
        this.bag = new Bag();
        this.clouds = new ArrayList<>(4);
        for (int i = 0; i < numberOfPlayers; i++) {
            Cloud cloud = new Cloud(i, numberOfPlayers);
            clouds.add(cloud);
        }
        this.characterCards = new ArrayList<>(3);
        if (gameMode.equals(GameMode.EXPERT))
            extractAndSetUsableCharacterCards();

        this.denyCards = 4;
        this.generalMoneyReserve = 20;

    }

    /**
     * this method takes care of the character cards
     * it extract 3 random character cards and build them with their cost and effects
     */
    public void extractAndSetUsableCharacterCards() {
        int extraction;
        ArrayList<Integer> extractedNumbers = new ArrayList<>();
        for(int i = 0; i<3; i++) {
            extraction = (int) (Math.random() * 12);
            if (extractedNumbers.contains(extraction))
                i--;
            else {
                characterCards.add(new CharacterCard(CharacterCardsName.getCharacterCardName(extraction)));
                extractedNumbers.add(extraction);
                if(extraction==0||extraction==4||extraction==6||extraction==10)
                    effectSetupFactory.getEffect(null,this,characterCards.get(i));
            }
        }
    }

    /**
     * getter method that gives the cloud of a certain given id
     * @param idCloud the id of the cloud we want to return
     * @return the cloud associated to that id
     */
    public Cloud getCloud(int idCloud) { return clouds.get(idCloud); }

    /**
     * getter method that gives the current isle manager for the game table
     * @return the current isle manager
     */
    public IsleManager getIsleManager() {
        return isleManager;
    }

    /**
     * getter method that gives the current bag for the game table
     * @return the current bag
     */
    public Bag getBag() {
        return bag;
    }

    /**
     * getter method that gives one of the playable character cards in the list
     * @param index of the character card we want
     * @return the playable character card with that index
     */
    public CharacterCard getCharacterCard(int index) {
        return characterCards.get(index);
    }

    /**
     * this method add a deny card to the deny card manager, anyway it isn't really used in the game
     */
    @Override
    public void addDenyCard() { denyCards += 1; }

    /**
     * this method remove a deny card to the deny card manager
     */
    @Override
    public void removeDenyCard(){
        denyCards -= 1;
    }

    /**
     * this method gives the number of deny cards on the deny card manager
     * @return the number of deny cards
     */
    @Override
    public int getDenyCards() { return denyCards; }

    /**
     * method used only for Testing purpose
     * @param index of the character card in the list of the game table
     * @param characterCardsName the name of the character card we want to add
     */
    public void setCharacterCards(int index, CharacterCardsName characterCardsName){
        characterCards.remove(index);
        characterCards.add(index,new CharacterCard(characterCardsName));
        if(characterCardsName.equals(CharacterCardsName.MONK)||characterCardsName.equals(CharacterCardsName.SPOILED_PRINCESS)||characterCardsName.equals(CharacterCardsName.GRANDMA_HERBS)||characterCardsName.equals(CharacterCardsName.JESTER))
            effectSetupFactory.getEffect(null,this,characterCards.get(index));
    }

}

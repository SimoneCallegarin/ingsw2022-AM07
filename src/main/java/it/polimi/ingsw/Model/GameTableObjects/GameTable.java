package it.polimi.ingsw.Model.GameTableObjects;

import it.polimi.ingsw.Model.CharacterCards.CharacterCard;
import it.polimi.ingsw.Model.CharacterCards.CharacterCardsName;
import it.polimi.ingsw.Model.CharacterCards.EffectSetupFactory;
import it.polimi.ingsw.Model.Enumeration.GameMode;
import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Model.Interface.DenyCardManager;


import java.util.ArrayList;
import java.util.HashMap;

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

    private final HashMap<RealmColors,Integer> professors;
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
     * or when he fills the space of the dining room that gives money
     */
    private int generalMoneyReserve;

    /**
     * GameTable constructor
     * @param numberOfPlayers that are going to play the game, chosen by the first player that joins the match
     * @param gameMode of the game, chosen by the first player that joins the match
     */
    public GameTable(int numberOfPlayers, GameMode gameMode) {
        this.isleManager = new IsleManager();
        this.bag = new Bag();

        bag.fillSetupBag();
        for (int i = 0; i < 12; i++) {
            if (isleManager.getIsle(i) != isleManager.getIsle(isleManager.getIsleWithMotherNatureIndex()) && isleManager.getIsle(i) != isleManager.getIsle(isleManager.getIsleOppositeToMotherNatureIndex()))
                isleManager.getIsle(i).addStudent(bag.draw());
        }

        bag.fillBag();

        this.clouds = new ArrayList<>(4);
        for (int i = 0; i < numberOfPlayers; i++) {
            Cloud cloud = new Cloud(i, numberOfPlayers);
            clouds.add(cloud);
        }

        this.professors = new HashMap<>();
        for (RealmColors rc : RealmColors.values()) {
            professors.put(rc, 1);
        }
           
        this.characterCards = new ArrayList<>(3);
        if (gameMode.equals(GameMode.EXPERT))
            extractAndSetUsableCharacterCards();

        this.denyCards = 4;
        this.generalMoneyReserve = 0;
        if(gameMode==GameMode.EXPERT)
            generalMoneyReserve = 20-numberOfPlayers;

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
                    effectSetupFactory.getEffect(this,characterCards.get(i));
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

    public int getNumberOfProfessors() {
        int totalNumberOfProfessors = 0;
        for (RealmColors rc : RealmColors.values()){
            totalNumberOfProfessors = totalNumberOfProfessors + professors.get(rc);
        }
        return totalNumberOfProfessors;
    }

    public void removeProfessor(RealmColors color) {
        professors.put(color, 0);
    }

    /**
     * getter method that gives one of the playable character cards in the list
     * @param index of the character card we want
     * @return the playable character card with that index
     */
    public CharacterCard getCharacterCard(int index) { return characterCards.get(index); }

    /**
     * this method will update the number of money when a character card is played
     * @param index the index of the character card played
     */
    public void characterCardPlayed(int index) {
        if(getCharacterCard(index).isUsed())
            generalMoneyReserve += getCharacterCard(index).getCost();
    }

    /**
     * this method is called when a player
     * places a student in the 3°, 6° or 9° position
     * and gains one money
     */
    public void studentInMoneyPosition() { generalMoneyReserve -= 1; }

    /**
     * getter method to return the general money reserve value
     * @return the number of money still available on the game table
     */
    public int getGeneralMoneyReserve() {
        return generalMoneyReserve;
    }

    /**
     * Adds a deny card to the deny card manager, anyway it isn't really used in the game.
     */
    @Override
    public void addDenyCard() { denyCards += 1; }

    /**
     * this method remove a deny card to the deny card manager
     */
    @Override
    public void removeDenyCard(){ denyCards -= 1; }

    /**
     * this method gives the number of deny cards on the deny card manager
     * @return the number of deny cards
     */
    @Override
    public int getDenyCards() { return denyCards; }

    /**
     * method used only for Testing purpose
     * it deletes all the other character cards created and creates a new character card received in input
     * @param characterCardsName the name of the character card we want to add
     */
    public void setCharacterCards(CharacterCardsName characterCardsName){
        for(int index = 0;index<3;index++){
            switch (getCharacterCard(index).getCharacterCardName()) {
                case MONK, JESTER, SPOILED_PRINCESS -> {
                    for(RealmColors colors : RealmColors.values()) {
                        for(int i=getCharacterCard(index).getStudentsByColor(colors); i>0;i--){
                            getBag().addStudent(colors);
                            getCharacterCard(index).removeStudent(colors);
                        }
                    }
                }
                case GRANDMA_HERBS -> {
                    for(int i=0;i<4;i++) {
                        addDenyCard();
                        getCharacterCard(index).removeDenyCard();
                    }
                }
            }
        }

        characterCards.clear();
        characterCards.add(new CharacterCard(characterCardsName));
        if(characterCardsName.equals(CharacterCardsName.MONK)||characterCardsName.equals(CharacterCardsName.SPOILED_PRINCESS)||characterCardsName.equals(CharacterCardsName.GRANDMA_HERBS)||characterCardsName.equals(CharacterCardsName.JESTER))
            effectSetupFactory.getEffect(this,characterCards.get(0));
    }

    public ArrayList<Cloud> getClouds() {
        return clouds;
    }

    public ArrayList<CharacterCard> getCharacterCards() {
        return characterCards;
    }
}

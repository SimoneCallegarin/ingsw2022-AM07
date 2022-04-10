package it.polimi.ingsw.Model.GameTableObjects;

import it.polimi.ingsw.Model.CharacterCards.CharacterCard;
import it.polimi.ingsw.Model.CharacterCards.Effect;
import it.polimi.ingsw.Model.DashboardObjects.Dashboard;
import it.polimi.ingsw.Model.Enumeration.CloudSide;
import it.polimi.ingsw.Model.Enumeration.GameMode;
import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Model.GameTableObjects.Bag;
import it.polimi.ingsw.Model.GameTableObjects.Cloud;
import it.polimi.ingsw.Model.GameTableObjects.IsleManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameTable {

    private final ArrayList<Cloud> clouds;
    private final IsleManager isleManager;
    private final Bag bag;
    private final HashMap<RealmColors,Integer> professors;
    private final ArrayList<CharacterCard> characterCards;
    private final int generalMoneyReserve = 20;
    private final GameMode gameMode;
    private final int numberOfPlayers;

    public GameTable(int numberOfPlayers, GameMode gameMode) {

        this.gameMode = gameMode;
        this.numberOfPlayers = numberOfPlayers;
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
            buildCloud(i);
        }

        this.professors = new HashMap<>();
        for (RealmColors rc : RealmColors.values()) {
            professors.put(rc, 1);
        }

        this.characterCards = new ArrayList<>(3);
    }

    public void buildCloud(int idCLoud){
        Cloud cloud;
        if (numberOfPlayers==3)
            cloud = new Cloud(idCLoud, CloudSide.SIDE_3_PLAYERS);
        else
            cloud = new Cloud(idCLoud, CloudSide.SIDE_2_AND_4_PLAYERS);
        clouds.add(cloud);
    }

    public void buildCharacterCards(int idCharacterCard){  //Not implemented yet!
        Effect effect = null;
        CharacterCard characterCard = new CharacterCard(idCharacterCard,0,effect);
        characterCards.add(characterCard);
    }

    public Cloud getCloud(int idCloud) { return clouds.get(idCloud); }

    public IsleManager getIsleManager() {
        return isleManager;
    }

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

    public CharacterCard getCharacterCard(int idCharacterCard) { return characterCards.get(idCharacterCard); }
}

package it.polimi.ingsw.Model.GameTableObjects;

import it.polimi.ingsw.Model.CharacterCards.CharacterCard;
import it.polimi.ingsw.Model.CharacterCards.Effect;
import it.polimi.ingsw.Model.DashboardObjects.Dashboard;
import it.polimi.ingsw.Model.Enumeration.CloudSide;
import it.polimi.ingsw.Model.Enumeration.GameMode;
import it.polimi.ingsw.Model.GameTableObjects.Bag;
import it.polimi.ingsw.Model.GameTableObjects.Cloud;
import it.polimi.ingsw.Model.GameTableObjects.IsleManager;

import java.util.ArrayList;

public class GameTable {

    private final ArrayList<Dashboard> dashboards;
    private final ArrayList<Cloud> clouds;
    private final IsleManager isleManager;
    private final Bag bag;
    private final ArrayList<CharacterCard> characterCards;
    private final int generalMoneyReserve = 20;
    private final GameMode gameMode;
    private final int numberOfPlayers;

    public GameTable( int numberOfPlayers, GameMode gameMode) {

        this.gameMode = gameMode;
        this.numberOfPlayers = numberOfPlayers;

        this.dashboards = new ArrayList<>(4);
        this.isleManager = new IsleManager();
        this.bag = new Bag();
        this.clouds = new ArrayList<>(4);
            for (int i = 0; i < numberOfPlayers; i++) {
                buildCloud(i);
            }
        this.characterCards = new ArrayList<>(3);
    }

    public void buildDashboard(int idDashboard){
        Dashboard dashboard = new Dashboard(numberOfPlayers, idDashboard, gameMode);
        dashboards.add(dashboard);
    }

    public void buildCloud(int idCLoud){
        Cloud cloud;
        if (numberOfPlayers==3)
            cloud = new Cloud(idCLoud, CloudSide.SIDE_3_PLAYERS);
        else
            cloud = new Cloud(idCLoud, CloudSide.SIDE_2_AND_4_PLAYERS);
        clouds.add(cloud);
    }

    public void buildCharacterCards(int idCharacterCard){  //Not yet implemented!
        Effect effect = null;
        CharacterCard characterCard = new CharacterCard(idCharacterCard,0);
        characterCards.add(characterCard);
    }

    public Dashboard getDashboard(int idDashboard) { return dashboards.get(idDashboard); }

    public Cloud getCloud(int idCloud) { return clouds.get(idCloud); }

    public IsleManager getIsleManager() {
        return isleManager;
    }

    public Bag getBag() {
        return bag;
    }

    public CharacterCard getCharacterCard(int idCharacterCard) { return characterCards.get(idCharacterCard); }
}

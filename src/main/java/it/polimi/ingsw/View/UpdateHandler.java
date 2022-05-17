package it.polimi.ingsw.View;

import it.polimi.ingsw.Network.Messages.NetworkMessages.GameCreation_UpdateMsg;
import it.polimi.ingsw.Network.Messages.NetworkMessages.NetworkMessage;

import java.util.ArrayList;

public class UpdateHandler {

    StorageOfModel storage;

    public void setupStorage (GameCreation_UpdateMsg message) { // Receive a message containing all the information of the game table.

        ArrayList<PlayerModelView> dashboards = new ArrayList<>();
        for(int i=0; i<storage.getNumberOfPlayers(); i++){
            PlayerModelView dashboard = new PlayerModelView(message.getNicknames().get(i),entranceStudents,message.getNumTower(),towerColor,message.getMoney());
            dashboards.add(i,dashboard);
        }
        ArrayList<GameTableModelView.CharacterCard> characterCards = new ArrayList<>();
        ArrayList<GameTableModelView.Isle> isles = new ArrayList<>();
        ArrayList<GameTableModelView.Cloud> clouds = new ArrayList<>();

        for(int i; i<3; i++){
            GameTableModelView.CharacterCard characterCard = new GameTableModelView.CharacterCard(cost,message.getStudentsOnCharacter(),denycards);
            characterCards.add(i,characterCard);
        }
        for(int i; i<12; i++){
            GameTableModelView.Isle isle = new GameTableModelView.Isle(message.get);
        }
        for(int i; i<message.getNumPlayers(); i++)
            clouds = new GameTableModelView.Cloud();

        GameTableModelView gameTable = new GameTableModelView(characterCards,isles,clouds,message.getGeneralReserve());
        storage = new StorageOfModel(message.getNumPlayers(), gameMode, PlayerModelView[] dashboards, GameTableModelView gameTable);
    }

    public void updateStorage () { // Receive an update message containing the information about what changed.
        // Calls the update methods of the storage.
    }

}
package it.polimi.ingsw.View;

public class TEMPORARYUpdateHandler {

    StorageOfModel storage;

    public void setupStorage () { // Receive a message containing all the information of the game table.
       /* storage = new StorageOfModel(Message.getNumberOfPlayers, PlayerModelView[] dashboards, GameTableModelView gameTable);
        for(int i=0; i<storage.getNumberOfPlayers(); i++){
            PlayerModelView dashboard = new PlayerModelView(nickname,entranceStudents,numOfTowers,towerColor,money);
        }
        GameTableModelView.CharacterCard[] characterCards = new GameTableModelView.CharacterCard[];
        GameTableModelView.Isle[] isles = new GameTableModelView.Isle[];
        GameTableModelView.cloud[] clouds = new GameTableModelView.cloud[];

        for(int i; i<3; i++)
            characterCards = new GameTableModelView.CharacterCard(cost,students,denycards);
        for(int i; i<12; i++)
            isles = new GameTableModelView.Isle();
        for(int i; i<numberOfClouds; i++)
            clouds = new GameTableModelView.Cloud();

        ...
        GameTableModelView gameTable = new GameTableModelView(characterCards,isles,clouds,generalMoneyReserve);
        */
    }

    public void updateStorage () { // Receive an update message containing the information about what changed.
        // Calls the update methods of the storage.
    }

}
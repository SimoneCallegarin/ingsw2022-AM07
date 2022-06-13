package it.polimi.ingsw.View.StorageOfModelInformation;

import java.util.ArrayList;

public class ModelChanges {

    ArrayList<ToUpdate> toUpdate;
    /**
     * the id of the player who is running the App
     */
    int playerID;
    /**
     * the id of the player who made the last move
     */
    int playingID;
    int isleID;
    int cloudID;
    int characterID;

    public ModelChanges() {
        toUpdate=new ArrayList<>();
    }

    public ArrayList<ToUpdate> getToUpdate() {
        return toUpdate;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public void setPlayingID(int playingID) {
        this.playingID = playingID;
    }

    public void setIsleID(int isleID) {
        this.isleID = isleID;
    }

    public void setCloudID(int cloudID) {
        this.cloudID = cloudID;
    }

    public void setCharacterID(int characterID) {
        this.characterID = characterID;
    }

    public int getPlayerID() {
        return playerID;
    }

    public int getPlayingID() {
        return playingID;
    }

    public int getIsleID() {
        return isleID;
    }

    public int getCloudID() {
        return cloudID;
    }

    public int getCharacterID() {
        return characterID;
    }
}

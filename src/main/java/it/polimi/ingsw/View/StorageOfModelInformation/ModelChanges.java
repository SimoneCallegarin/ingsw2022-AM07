package it.polimi.ingsw.View.StorageOfModelInformation;

import java.util.ArrayList;

/**
 * Used to identify what changed in the model after a move.
 */
public class ModelChanges {

    /**
     * List containing all the things that changed.
     */
    private final ArrayList<ToUpdate> toUpdate;
    /**
     * ID of the player who is running the App.
     */
    private int playerID;
    /**
     * ID of the player who made the last move.
     */
    private int playingID;
    /**
     * ID of the isle that changed.
     */
    private int isleID;
    /**
     * ID of the cloud that changed.
     */
    private int cloudID;
    /**
     * ID of the character card that changed.
     */
    private int characterID;
    /**
     * boolean used to know if one or more isles unified
     */
    private boolean layoutChanged = false;
    /**
     * number of isles to remove from the game screen, according to the game rules it can be 1 or 2
     */
    private int islesToRemove;

    /**
     * Constructor of the ModelChanges.
     */
    public ModelChanges() { toUpdate = new ArrayList<>(); }

    // GETTERS:

    public ArrayList<ToUpdate> getToUpdate() { return toUpdate; }

    public int getPlayerID() { return playerID; }

    public int getPlayingID() { return playingID; }

    public int getIsleID() { return isleID; }

    public int getCloudID() { return cloudID; }

    public int getCharacterID() { return characterID; }

    public boolean isLayoutChanged() { return layoutChanged; }

    public int getIslesToRemove() { return islesToRemove; }

    // SETTERS:

    public void setPlayerID(int playerID) { this.playerID = playerID; }

    public void setPlayingID(int playingID) { this.playingID = playingID; }

    public void setIsleID(int isleID) { this.isleID = isleID; }

    public void setCloudID(int cloudID) { this.cloudID = cloudID; }

    public void setCharacterID(int characterID) { this.characterID = characterID; }

    public void setLayoutChanged(boolean layoutChanged) { this.layoutChanged = layoutChanged; }

    public void setIslesToRemove(int islesToRemove) { this.islesToRemove = islesToRemove; }

}

package it.polimi.ingsw.Observer;

public interface ViewObserver {

    public void onUsername(String username);

    public void onGamePreferences(int numPlayers, Boolean gameMode);


}

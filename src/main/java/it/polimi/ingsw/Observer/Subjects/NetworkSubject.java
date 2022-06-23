package it.polimi.ingsw.Observer.Subjects;

import it.polimi.ingsw.Network.Messages.NetworkMessages.NetworkMessage;
import it.polimi.ingsw.Observer.NetworkObserver;

import java.util.ArrayList;

public abstract class NetworkSubject {

    private final ArrayList<NetworkObserver> networkObserverList=new ArrayList<>();

    public void addObserver(NetworkObserver networkObserver){ networkObserverList.add(networkObserver); }

    public void notifyObserver(NetworkMessage message){
        for (NetworkObserver observer : networkObserverList)
            observer.update(message);
    }
}

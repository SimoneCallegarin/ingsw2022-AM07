package it.polimi.ingsw.Observer;

import it.polimi.ingsw.Network.Messages.NetworkMessages.NetworkMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public abstract class NetworkSubject {
    private final List<NetworkObserver> networkObserverList=new ArrayList<>();


    public void addObserver(NetworkObserver networkObserver){
        networkObserverList.add(networkObserver);
    }

    public void removeObserver(NetworkObserver networkObserver){
        networkObserverList.remove(networkObserver);
    }

    public void notifyObserver(NetworkMessage message){
        for (NetworkObserver observer : networkObserverList) {
            observer.update(message);
        }
    }
}

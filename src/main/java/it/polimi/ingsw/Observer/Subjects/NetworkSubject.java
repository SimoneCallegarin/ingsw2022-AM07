package it.polimi.ingsw.Observer.Subjects;

import it.polimi.ingsw.Network.Messages.NetworkMessages.NetworkMessage;
import it.polimi.ingsw.Observer.NetworkObserver;

import java.util.ArrayList;

/**
 * Subject of the Network (ClientListener) observed by the Network Observer (ClientController).
 */
public abstract class NetworkSubject {

    /**
     * List of network observers.
     */
    private final ArrayList<NetworkObserver> networkObserverList=new ArrayList<>();

    /**
     * Adds a network observer to the subject.
     * @param networkObserver that starts to observe the subject.
     */
    public void addObserver(NetworkObserver networkObserver){ networkObserverList.add(networkObserver); }

    /**
     * Notifies the observer with the message received by the subject.
     * @param message received by the subject.
     */
    public void notifyObserver(NetworkMessage message){
        for (NetworkObserver observer : networkObserverList)
            observer.update(message);
    }
}

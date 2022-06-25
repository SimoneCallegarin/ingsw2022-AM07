package it.polimi.ingsw.Observer.Subjects;

import it.polimi.ingsw.Observer.ModelObserver;

import java.util.ArrayList;
import java.util.function.Consumer;

/**
 * Subject of the Model (Game) observed by the Model Observer (VirtualView).
 */
public abstract class ModelSubject {

    /**
     * List of model observers.
     */
    private final ArrayList<ModelObserver> modelObserverList = new ArrayList<>();

    /**
     * Adds a model observer to the subject.
     * @param modelObserver that starts to observe the subject.
     */
    public void addObserver(ModelObserver modelObserver){ modelObserverList.add(modelObserver); }

    /**
     * Notifies the observer with changes of the subject.
     */
    public void notifyObserver(Consumer<ModelObserver> lambda){
        for(ModelObserver modelObserver: modelObserverList)
            lambda.accept(modelObserver);
    }
}

package it.polimi.ingsw.Observer.Subjects;

import it.polimi.ingsw.Observer.ViewObserver;

import java.util.ArrayList;
import java.util.function.Consumer;

/**
 * Subject of the View (CLI or GUI) observed by the View Observer (ClientController).
 */
public abstract class ViewSubject {

    /**
     * List of view observers.
     */
    private final ArrayList<ViewObserver> viewObserverList=new ArrayList<>();

    /**
     * Adds a view observer to the subject.
     * @param viewObserver that starts to observe the subject.
     */
    public void addObserver(ViewObserver viewObserver){ viewObserverList.add(viewObserver); }

    /**
     * Adds a list of view observers to the subject.
     * @param observerList list of view observers that starts to observe the subject.
     */
    public void addAllObservers(ArrayList<ViewObserver> observerList){ viewObserverList.addAll(observerList); }

    /**
     * Notifies the observer with changes of the subject.
     */
    public void notifyObserver(Consumer<ViewObserver> lambda){
        for (ViewObserver observer : viewObserverList)
            lambda.accept(observer);
    }

    /**
     * Getter method for the view observers that observe the subject.
     * @return the list of view observers that observe the view subject.
     */
    public ArrayList<ViewObserver> getViewObserverList() { return viewObserverList; }
}

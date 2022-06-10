package it.polimi.ingsw.Observer;


import java.util.ArrayList;
import java.util.function.Consumer;

public abstract class ViewSubject {

    private final ArrayList<ViewObserver> viewObserverList=new ArrayList<>();

    public void addObserver(ViewObserver viewObserver){ viewObserverList.add(viewObserver); }

    public void addAllObservers(ArrayList<ViewObserver> observerList){viewObserverList.addAll(observerList);}

    public void removeObserver(ViewObserver viewObserver){
        viewObserverList.remove(viewObserver);
    }

    public void notifyObserver(Consumer<ViewObserver> lambda){
        for (ViewObserver observer : viewObserverList) {
            lambda.accept(observer);
        }
    }

    public ArrayList<ViewObserver> getViewObserverList() { return viewObserverList; }
}

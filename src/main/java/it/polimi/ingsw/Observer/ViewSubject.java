package it.polimi.ingsw.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public abstract class ViewSubject {
    private final List<ViewObserver> viewObserverList=new ArrayList<>();


    public void addObserver(ViewObserver viewObserver){
        viewObserverList.add(viewObserver);
    }

    public void removeObserver(ViewObserver viewObserver){
        viewObserverList.remove(viewObserver);
    }

    public void notify(Consumer<ViewObserver> lambda){
        for (ViewObserver observer : viewObserverList) {
            lambda.accept(observer);
        }
    }

}

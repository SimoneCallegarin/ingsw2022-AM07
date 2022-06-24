package it.polimi.ingsw.Observer.Subjects;

import it.polimi.ingsw.Observer.ModelObserver;

import java.util.ArrayList;
import java.util.function.Consumer;

public abstract class ModelSubject {

    private final ArrayList<ModelObserver> modelObserverList=new ArrayList<>();

    public void addObserver(ModelObserver modelObserver){ modelObserverList.add(modelObserver); }

    public void notifyObserver(Consumer<ModelObserver> lambda){
        for(ModelObserver modelObserver: modelObserverList)
            lambda.accept(modelObserver);
    }
}

package it.polimi.ingsw.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public abstract class ModelSubject {
    private final List<ModelObserver> modelObserverList=new ArrayList<>();

    public void addObserver(ModelObserver modelObserver){
        modelObserverList.add(modelObserver);
    }

    public void removeObserver(ModelObserver modelObserver){
        modelObserverList.remove(modelObserver);
    }

    public void notifyObserver(Consumer<ModelObserver> lambda){
        for(ModelObserver modelObserver: modelObserverList){
            lambda.accept(modelObserver);
        }
    }
}

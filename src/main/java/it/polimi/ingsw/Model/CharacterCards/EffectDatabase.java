package it.polimi.ingsw.Model.CharacterCards;

import it.polimi.ingsw.Model.Enumeration.Movable;
import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Model.Interface.Place;

import java.util.ArrayList;
import java.util.HashMap;

public class EffectDatabase {
    public HashMap<CharacterCardsName,HashMap<EffectsName, ArrayList>> database = new HashMap<>();
    public void fillDatabase(){
        HashMap<EffectsName,ArrayList> temp = new HashMap<>();
        Place place = new Place() {
            @Override
            public int getNumberOfElements() {
                return 0;
            }
        };  //NOPE fai enum
        RealmColors color = RealmColors.YELLOW;

        ArrayList listM = new ArrayList<>();
        ArrayList listS = new ArrayList<>();

        //MONK:

        temp.put(EffectsName.MOVEMENT, listM);
        temp.get(EffectsName.MOVEMENT).add(1);
        temp.get(EffectsName.MOVEMENT).add(place);
        temp.get(EffectsName.MOVEMENT).add(place);
        temp.get(EffectsName.MOVEMENT).add(Movable.STUDENT);
        temp.get(EffectsName.MOVEMENT).add(color);
        temp.get(EffectsName.MOVEMENT).add(1);
        temp.get(EffectsName.MOVEMENT).add(place);
        temp.get(EffectsName.MOVEMENT).add(place);
        temp.get(EffectsName.MOVEMENT).add(Movable.STUDENT);

        temp.put(EffectsName.SETUP, listS);
        temp.get(EffectsName.SETUP).add(4);
        temp.get(EffectsName.SETUP).add(Movable.STUDENT);


        database.put(CharacterCardsName.MONK, temp);
    }

    public HashMap getHashMap(){
        return database;
    }

    public HashMap<EffectsName, ArrayList> getHashMapOfEffect(CharacterCard characterCard){
        return database.get(characterCard);
    }

    public HashMap getHashMapEffect(CharacterCard characterCard){
        return (HashMap) database.get(characterCard);
    }

}

package it.polimi.ingsw.Model.CharacterCards;

import it.polimi.ingsw.Model.Enumeration.Movable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class EffectDatabase {

    public HashMap<CharacterCardsName,ArrayList<ArrayList>> database = new HashMap<>();

    public void fillDatabase(){

        ArrayList<ArrayList> temp = new ArrayList<>();
        ArrayList listS;
        ArrayList listM1;
        ArrayList listM2;
        ArrayList listR;

        //MONK:
        listS = new ArrayList( Arrays.asList(EffectsName.SETUP,4,Movable.STUDENT));
        listM1 = new ArrayList( Arrays.asList(EffectsName.MOVEMENT,1,PlaceNames.CHARACTER_CARD,PlaceNames.ISLE,"color"));
        listM2 = new ArrayList( Arrays.asList(EffectsName.MOVEMENT,1,PlaceNames.BAG,PlaceNames.CHARACTER_CARD,Movable.STUDENT,"random"));
        temp.add(listS);
        temp.add(listM1);
        temp.add(listM2);
        database.put(CharacterCardsName.MONK,temp);

        //FARMER:
        listR = new ArrayList(Arrays.asList(EffectsName.RECALCULATE,1,Movable.PROFESSOR)); //Ancora da implementare il refactor
        temp = new ArrayList<>();
        temp.add(listR);
        database.put(CharacterCardsName.FARMER,temp);

        //HERALD:

        //MAGICAL_LETTER_CARRIER:

        //GRANDMA_HERBS:
        listS = new ArrayList( Arrays.asList(EffectsName.SETUP,4,Movable.DENYCARD));
        listM1 = new ArrayList( Arrays.asList(EffectsName.MOVEMENT,1,PlaceNames.CHARACTER_CARD,PlaceNames.ISLE,Movable.DENYCARD,"no_color"));
        temp = new ArrayList<>();
        temp.add(listS);
        temp.add(listM1);
        database.put(CharacterCardsName.GRANDMA_HERBS,temp);

        //CENTAUR:

        //JESTER:
        listS = new ArrayList( Arrays.asList(EffectsName.SETUP,6,Movable.STUDENT));
        listM1 = new ArrayList( Arrays.asList(EffectsName.MOVEMENT,3,PlaceNames.CHARACTER_CARD,PlaceNames.ENTRANCE,Movable.STUDENT,"color"));   //FINO A 3 PERO'!
        temp = new ArrayList<>();
        temp.add(listS);
        temp.add(listM1);
        database.put(CharacterCardsName.JESTER,temp);

    }

    public HashMap getHashMap(){
        return database;
    }

    public EffectsName getSingleEffect(CharacterCardsName characterCardsName, int index) {
        return (EffectsName) database.get(characterCardsName).get(index).get(0);
    }

    public ArrayList getSingleEffectMoves(CharacterCardsName characterCardsName, int index) {
        return database.get(characterCardsName).get(index);
    }

}

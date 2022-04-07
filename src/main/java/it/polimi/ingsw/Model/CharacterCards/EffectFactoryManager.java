package it.polimi.ingsw.Model.CharacterCards;

import java.util.ArrayList;

public class EffectFactoryManager {
    EffectFactory effectFactory;

    public EffectFactoryManager() {
        this.effectFactory = new EffectFactory();
    }

    public ArrayList<AtomicEffect> getEffect(CharacterCardsName characterCard) {
        return effectFactory.getEffect(characterCard);
    }

}

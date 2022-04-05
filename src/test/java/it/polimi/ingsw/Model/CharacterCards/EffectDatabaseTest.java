package it.polimi.ingsw.Model.CharacterCards;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EffectDatabaseTest {

    @Test
    void fillDatabase() {
        EffectDatabase effectDatabase = new EffectDatabase();
        effectDatabase.fillDatabase();
        System.out.print(effectDatabase.database);

    }
}
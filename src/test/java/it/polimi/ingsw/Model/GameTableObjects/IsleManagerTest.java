package it.polimi.ingsw.Model.GameTableObjects;

import it.polimi.ingsw.Model.Enumeration.RealmColors;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IsleManagerTest {

    IsleManager isleManagerTest = new IsleManager();

    /**
     * testing if unifying the isles correctly updates the students value, the number of isles and the list of isles (along with the isleIDs)
     *
     */
    @Test
    void unifyIsle() {

        Isle isletest1 = isleManagerTest.getIsle(0);
        Isle isletest2 = isleManagerTest.getIsle(1);
        Isle isletest3 = isleManagerTest.getIsle(2);
        Isle isletest11= isleManagerTest.getIsle(10);
        Isle isletest12= isleManagerTest.getIsle(11);


        for(RealmColors color:RealmColors.values()) {
            isletest1.addStudent(color);
            isletest1.addStudent(color);
            isletest2.addStudent(color);
        }

        isleManagerTest.unifyIsle(isletest1.getIsleIndex(),isletest2.getIsleIndex());
        for(RealmColors color:RealmColors.values()) assertEquals(isletest1.getStudentsByColor(color),3);
        assertEquals(isletest1.getNumOfIsles(),2);
        assertEquals(isleManagerTest.getIsles().indexOf(isletest1),0);
        assertEquals(isletest1.getIsleIndex(),0);
        assertEquals(isleManagerTest.getIsles().indexOf(isletest3),1);
        assertEquals(isletest3.getIsleIndex(),1);

        isleManagerTest.unifyIsle(isletest1.getIsleIndex(),isletest12.getIsleIndex());
        assertEquals(isletest1.getNumOfIsles(),3);
        assertEquals(isletest1.getIsleIndex(),0);
        assertEquals(isletest11.getIsleIndex(),9);
    }

}
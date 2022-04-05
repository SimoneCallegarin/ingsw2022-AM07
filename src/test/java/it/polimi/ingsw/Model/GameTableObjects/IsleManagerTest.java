package it.polimi.ingsw.Model.GameTableObjects;

import it.polimi.ingsw.Model.Enumeration.RealmColors;
import it.polimi.ingsw.Model.GameTableObjects.Isle;
import it.polimi.ingsw.Model.GameTableObjects.IsleManager;
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
        Isle isletest4 = isleManagerTest.getIsle(3);
        Isle isletest5 = isleManagerTest.getIsle(4);
        Isle isletest6 = isleManagerTest.getIsle(5);
        Isle isletest7 = isleManagerTest.getIsle(6);
        Isle isletest8 = isleManagerTest.getIsle(7);
        Isle isletest9 = isleManagerTest.getIsle(8);
        Isle isletest10= isleManagerTest.getIsle(9);
        Isle isletest11= isleManagerTest.getIsle(10);
        Isle isletest12= isleManagerTest.getIsle(11);


        for(RealmColors color:RealmColors.values()) {
            isletest1.addStudent(color);
            isletest1.addStudent(color);
            isletest2.addStudent(color);
        }

        isleManagerTest.UnifyIsle(isletest1,isletest2);
        for(RealmColors color:RealmColors.values()) assertEquals(isletest1.getStudentsByColor(color),3);
        assertEquals(isletest1.getNumOfIsles(),2);
        assertEquals(isleManagerTest.getIsles().indexOf(isletest1),0);
        assertEquals(isletest1.getIdIsle(),0);
        assertEquals(isleManagerTest.getIsles().indexOf(isletest3),1);
        assertEquals(isletest3.getIdIsle(),1);

        isleManagerTest.UnifyIsle(isletest1,isletest12);
        assertEquals(isletest1.getNumOfIsles(),3);
        assertEquals(isletest1.getIdIsle(),0);
        assertEquals(isletest11.getIdIsle(),9);



    }
}
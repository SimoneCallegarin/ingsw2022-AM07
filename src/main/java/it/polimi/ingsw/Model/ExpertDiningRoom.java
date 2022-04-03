package it.polimi.ingsw.Model;

import java.util.HashMap;

public class ExpertDiningRoom extends DiningRoom {

    public ExpertDiningRoom() {
        super();
    }

    @Override
    public void addStudent(RealmColors color) {
        int temp, prev;

        temp = students.get(color);
        prev = temp;

        if (temp < maxStudentsPerColor)
            temp++;
        students.put(color, temp);

        if (temp < maxStudentsPerColor)
            if ((temp%3 == 0) && (prev == temp--)) {
            //addMoney
        }

    }

}

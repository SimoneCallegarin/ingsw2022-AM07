package it.polimi.ingsw.View.GUI.Buttons;

import it.polimi.ingsw.Model.Enumeration.RealmColors;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class StudentButton extends JButton {

    RealmColors color;
    ArrayList<BufferedImage> students;
    ArrayList<BufferedImage> checkedStudents;

    public StudentButton(RealmColors color, ArrayList<BufferedImage> students, ArrayList<BufferedImage> checkedStudents) {
        this.color=color;
        setBorder(BorderFactory.createEmptyBorder());
        setContentAreaFilled(false);
        printStudent(color);
        this.students = students;
        this.checkedStudents = checkedStudents;
    }

    public StudentButton(RealmColors color, ArrayList<BufferedImage> students) {
        this.color=color;
        setBorder(BorderFactory.createEmptyBorder());
        setContentAreaFilled(false);
        printStudent(color);
        this.students = students;
        this.checkedStudents = null;
    }

    public StudentButton() {
        this.color=null;
        this.students = null;
        this.checkedStudents = null;
    }

    public void printStudent(RealmColors color){
        BufferedImage img = null;
        switch (color) {
            case YELLOW -> img = students.get(0);
            case BLUE -> img = students.get(1);
            case RED -> img = students.get(2);
            case PINK -> img = students.get(3);
            case GREEN -> img = students.get(4);
        }
        setIcon(new ImageIcon(img));
    }

    public void printClick(RealmColors color) {
        BufferedImage img = null;
        switch (color) {
            case YELLOW -> img = checkedStudents.get(0);
            case BLUE -> img = checkedStudents.get(1);
            case RED -> img = checkedStudents.get(2);
            case PINK -> img = checkedStudents.get(3);
            case GREEN -> img = checkedStudents.get(4);
        }
        setIcon(new ImageIcon(img));
    }

    public RealmColors getColor() {
        return color;
    }
}

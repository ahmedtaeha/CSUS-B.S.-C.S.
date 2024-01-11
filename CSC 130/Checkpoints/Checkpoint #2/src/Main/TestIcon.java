package Main;

import javax.swing.*;
import java.awt.*;

public class TestIcon extends JFrame {


    public TestIcon() {
       //add icon to frame
        add(new MovingIcon());
        //set frame properties
        setTitle("Moving Icon");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            new TestIcon().setVisible(true);
        });
        System.out.println("Hello World");
    }
}
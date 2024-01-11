package Main;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class MovingIcon extends JPanel {
    //read image from Art folder
    ImageIcon icon = new ImageIcon("csc130image.png");
    //create JLabel with imageIcon
    JLabel label = new JLabel(icon);
    //create timer
    Timer timer = new Timer(100, this::actionPerformed);

    public MovingIcon() {
        //add label to panel
        add(label);
        //start timer
        timer.start();
        setBackground(java.awt.Color.BLACK);
    }

    private void actionPerformed(ActionEvent e) {
        //move icon
        label.setLocation(label.getX() + 10, label.getY());
        //if icon is out of bounds, reset location
        if (label.getX() > 500) {
            label.setLocation(0, label.getY());
        }
    }
}

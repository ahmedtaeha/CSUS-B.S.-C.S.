package Main;

import java.awt.Color;
import java.awt.Toolkit;
import java.util.LinkedList;
import java.util.Queue;

import Data.Vector2D;
import logic.Control;
import timer.stopWatchX;

public class Main {
    // Fields (Static) below...

    public static Color c = new Color(120, 81, 169);
    public static boolean isImageDrawn = false;
    public static stopWatchX timer = new stopWatchX(250);

    public static Queue<Vector2D> vecs1 = new LinkedList<>();
    public static Queue<Vector2D> vecs2 = new LinkedList<>();

    public static Vector2D currentVec = new Vector2D(-100, -100);

    public static Vector2D vec1 = new Vector2D(100, 100);


    // End Static fields...

    public static void main(String[] args) {
        Control ctrl = new Control();                // Do NOT remove!
        ctrl.gameLoop();// Do NOT remove!

//		Vector2D vect12 = new Vector2D(2,2);
//		vect12.typeSomething();
//		Vector2D.typeSomething();
    }

    /* This is your access to things BEFORE the game loop starts */
    public static void start() {
        // TODO: Code your starting conditions here...NOT DRAW CALLS HERE! (no addSprite or drawString)

        for (int x = -128; x <= 2048; x += 8) {
            vecs1.add(new Vector2D(x, 25));
        }

//		vecs1.add(new Vector2D(100,25));
//		vecs1.add(new Vector2D(85,25));
//		vecs1.add(new Vector2D(64,25));
//		vecs1.add(new Vector2D(78,25));
//
////		vecs2.add(new Vector2D(78,25));
////		vecs2.add(new Vector2D(78,25));


    }

    /* This is your access to the "game loop" (It is a "callback" method from the Control class (do NOT modify that class!))*/
    public static void update(Control ctrl) {
        // TODO: This is where you can code! (Starting code below is just to show you how it works)


        ctrl.addSpriteToFrontBuffer(vec1.getX(), vec1.getY(), "f2");
        //ctrl.addSpriteToFrontBuffer(1100, 550, "f0");						 				// Add a tester sprite to render list by tag (Remove later! Test only!)
        int x  = Toolkit.getDefaultToolkit().getScreenSize().width-200;
        int y = Toolkit.getDefaultToolkit().getScreenSize().height-100;
        ctrl.drawString(x, y, "Ahmed Taeha", c);
        //ctrl.drawString(1120, 900, "Ahmed Taeha", c);        // Test drawing text on screen where you want (Remove later! Test only!)

        if (timer.isTimeUp()) {
            isImageDrawn = !isImageDrawn;
            timer.resetWatch();
        }
        //animate image horizontally smoothly
        if (isImageDrawn) {
            if (vecs1.size() > 0) {
                currentVec = vecs1.remove();
                vecs2.add(currentVec);
                vec1 = currentVec;
            } else {
                while (vecs2.size() > 0) {
                    vecs1.add(vecs2.remove());
                }
            }
        }
    }

}


// Additional Static methods below...(if needed)



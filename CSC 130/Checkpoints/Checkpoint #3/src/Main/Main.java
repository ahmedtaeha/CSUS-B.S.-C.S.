package Main;

import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import Data.Vector2D;
import Data.spriteInfo;
import logic.Control;
import timer.stopWatchX;

public class Main {
    // Fields (Static) below...

    public static Color c = new Color(120, 81, 169);
    public static boolean isImageDrawn = false;
    public static stopWatchX timer = new stopWatchX(Config.REFRESH_RATE);

    public static Queue<Vector2D> vecs1 = new LinkedList<>();
    public static Queue<Vector2D> vecs2 = new LinkedList<>();

    public static Vector2D currentVec = new Vector2D(-100, -100);

    public static Vector2D vec1 = new Vector2D(100, 100);
    public static ArrayList<spriteInfo> sprites = new ArrayList<>();

    public static int currentSpriteIndex = 0;


    // End Static fields...

    public static void main(String[] args) {
        Control ctrl = new Control();                // Do NOT remove!
        ctrl.gameLoop();// Do NOT remove!
    }

    /* This is your access to things BEFORE the game loop starts */
    public static void start() {
        // TODO: Code your starting conditions here...NOT DRAW CALLS HERE! (no addSprite or drawString)

        int tagIndex = 0;
        for (int x = -128; x <= 2048; x += 8) {
            if(tagIndex == 8) {
                tagIndex = 0;
            } else {
                tagIndex++;
            }
            sprites.add(new spriteInfo(new Vector2D(x, 25), "walk"+tagIndex));
            vecs1.add(new Vector2D(x, 25));
        }
    }

    /* This is your access to the "game loop" (It is a "callback" method from the Control class (do NOT modify that class!))*/
    public static void update(Control ctrl) {
        // TODO: This is where you can code! (Starting code below is just to show you how it works)

//		if (isImageDrawn)
        ctrl.addSpriteToFrontBuffer(sprites.get(currentSpriteIndex).getCoords().getX(), sprites.get(currentSpriteIndex).getCoords().getY(), sprites.get(currentSpriteIndex).getTag());
        //ctrl.addSpriteToFrontBuffer(1100, 550, "f0");						 				// Add a tester sprite to render list by tag (Remove later! Test only!)
        ctrl.drawString(Config.SCREENX-200 ,Config.SCREENY-20, "Ahmed Taeha", c);
        //ctrl.drawString(1120, 900, "Ahmed Taeha", c);        // Test drawing text on screen where you want (Remove later! Test only!)

        if (timer.isTimeUp()) {
            isImageDrawn = !isImageDrawn;
            if(currentSpriteIndex == sprites.size()-1) {
                currentSpriteIndex = 0;
            } else {
                currentSpriteIndex++;
            }
            timer.resetWatch();
            //draw sprite what i did



            //	if (timer.isTimeUp() ) {
            // currentVec some vec from q1. Remove it from q1.
            // add current vec to q2 since gotta populate that
            // check if vec1/q1 empty
            // if vecs2 not empty, iterate through vecs 2 and repopulate vec1 again/ while q2 not empty

            // reset time

        }


        //animate image horizontally and smoothly, check config class to edit refresh rate

//        if (isImageDrawn) {
//            if (vecs1.size() > 0) {
//                currentVec = vecs1.remove();
//                vecs2.add(currentVec);
//                vec1 = currentVec;
//            } else {
//                while (vecs2.size() > 0) {
//                    vecs1.add(vecs2.remove());
//                }
//            }
//        }

    }
}
// Additional Static methods below...(if needed)



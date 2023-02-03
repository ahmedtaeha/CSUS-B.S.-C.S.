package Main;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

import Data.Vector2D;
import Data.spriteInfo;
import FileIO.EZFileRead;
import logic.Control;
import timer.stopWatchX;

public class Main {
    // Fields (Static) below...
    public static Color c = new Color(255, 234, 0),d = new Color(255,94,5);
    public static boolean isImageDrawn = false;
    public static char dir = 'd';
    public static stopWatchX timer = new stopWatchX(Config.REFRESH_RATE);
    public static stopWatchX timerFade = new stopWatchX(Config.FADE_SPEED);
    public static stopWatchX timerType = new stopWatchX(Config.TYPE_SPEED);


    public static spriteInfo hero ;
    public static Vector2D oldhero;
    public static spriteInfo ground;
    public static spriteInfo chest;
    public static spriteInfo gold;
    public static ArrayList<spriteInfo> room;
    public static int currentSpriteIndex = 0,currentSpriteIndex2 = 0,currentSpriteIndex3 = 0,currentSpriteIndex4=0;
    //public static HashMap<String, String> textMap = new HashMap<>();
    //list of characters of textMap
    public static HashMap<String, char[]> textMapChar = new HashMap<>();
    public static String currentText = "";
    public static int currentTextCharIndex = 0;
    public static int fade = 200;
    public static int fadeRate = 5;
    public static int fadeMax = 255;
    public static int fadeMin = 0;
    public static spriteInfo lastclick,nowclick;
    public static spriteInfo qmark;

    // End Static fields...

    public static void main(String[] args) {
        Control ctrl = new Control();                // Do NOT remove!
        ctrl.gameLoop();// Do NOT remove!
    }

    public static void loadLinesFromFile() {
        EZFileRead ezr = new EZFileRead("Dialog.txt");
        for (int i = 0; i < ezr.getNumLines(); i++) {
            String raw = ezr.getLine(i);
            StringTokenizer st = new StringTokenizer(raw, "*");
            String tag = st.nextToken();
            System.out.println(tag);
            String message = st.nextToken();
            //textMap.put(tag, message);
            textMapChar.put(tag, message.toCharArray());
            System.out.println(tag + " - " + message);
        }
    }

    /* This is your access to things BEFORE the game loop starts */
    public static void start() {
        // TODO: Code your starting conditions here...NOT DRAW CALLS HERE! (no addSprite or drawString)
        hero = new spriteInfo(new Vector2D(500,500),Config.character_width,Config.character_height,"dwalk"+0);
        oldhero = new Vector2D(500,500);

        ground = new spriteInfo(new Vector2D(0,0),Config.SCREENX,Config.SCREENY,"ground");

        qmark = new spriteInfo(new Vector2D(0,0),0,0,"qmark");

        chest = new spriteInfo(new Vector2D(200,200),Config.chest_width,Config.chest_height,"chest");
        gold = new spriteInfo(new Vector2D(800,800),Config.gold_width,Config.gold_height,"gold");

        room = new ArrayList<>();
        room.add(new spriteInfo(new Vector2D(0,0),Config.wall_width,Config.wall_height,"roomt"));
        room.add(new spriteInfo(new Vector2D(0,Config.SCREENY-100),Config.wall_width,Config.wall_height,"roomb"));
        room.add(new spriteInfo(new Vector2D(Config.SCREENX-100,0),Config.wall_height,Config.wall_width,"rooml"));
        room.add(new spriteInfo(new Vector2D(0,0),Config.wall_height,Config.wall_width,"roomr"));

        loadLinesFromFile();
    }

    /* This is your access to the "game loop" (It is a "callback" method from the Control class (do NOT modify that class!))*/
    public static void update(Control ctrl) {
        // TODO: This is where you can code! (Starting code below is just to show you how it works)
        ctrl.addSpriteToFrontBuffer(0,0,ground.getTag());

        ctrl.addSpriteToFrontBuffer(chest.getCoords().getX(),chest.getCoords().getY(),chest.getTag());

        ctrl.addSpriteToFrontBuffer(gold.getCoords().getX(),gold.getCoords().getY(),gold.getTag());

        ctrl.addSpriteToFrontBuffer(oldhero.getX(),oldhero.getY(), hero.getTag());

        for (spriteInfo x : room)
            ctrl.addSpriteToFrontBuffer(x.getCoords().getX(),x.getCoords().getY(),x.getTag());

        ctrl.drawString(oldhero.getX()+hero.width/2-20,oldhero.getY()+hero.height+20,"Ahmed",d);

        if (lastclick != null) {
            ctrl.drawString(lastclick.getCoords().getX(), lastclick.getCoords().getY(), currentText, new Color(c.getRed(), c.getGreen(), c.getBlue(), fade));

            if (currentTextCharIndex < textMapChar.get(lastclick.getTag()).length) {
                if (timerType.isTimeUp()) {
                    currentText += textMapChar.get(lastclick.getTag())[currentTextCharIndex];
                    currentTextCharIndex++;
                    timerType.resetWatch();
                }
            } else {
                if (fade > fadeMin) {
                    if (timerFade.isTimeUp()) {
                        fade -= fadeRate;
                        timerFade.resetWatch();
                    }
                }else {
                    lastclick = null;

                }
            }
        }else if (nowclick != null)
            ctrl.addSpriteToFrontBuffer(qmark.getCoords().getX(),qmark.getCoords().getY(),qmark.getTag());

        if (timer.isTimeUp()) {
            if(isImageDrawn) {
                switch (dir){
                    case 'u':
                        hero.setTag(dir+"walk"+currentSpriteIndex++);
                        if (currentSpriteIndex == Config.getCharacter_walk_nb) currentSpriteIndex =0;
                        break;
                    case 'd':
                        hero.setTag(dir+"walk"+currentSpriteIndex2++);
                        if (currentSpriteIndex2 == Config.getCharacter_walk_nb) currentSpriteIndex2 =0;
                        break;
                    case 'r':
                        hero.setTag(dir+"walk"+currentSpriteIndex3++);
                        if (currentSpriteIndex3 == Config.getCharacter_walk_nb) currentSpriteIndex3 =0;
                        break;
                    case 'l':
                        hero.setTag(dir+"walk"+currentSpriteIndex4++);
                        if (currentSpriteIndex4 == Config.getCharacter_walk_nb) currentSpriteIndex4 =0;
                        break;
                }
                isImageDrawn = false;
            }else{
                hero.setTag(dir+"walk0");
            }
            timer.resetWatch();
        }
        int k = Config.REFRESH_RATE/Config.movepixel;
        if (oldhero.getX() - hero.getCoords().getX() != 0 )
            if (oldhero.getX() - hero.getCoords().getX() > 0)
                oldhero.adjustX(-k);
        else
            oldhero.adjustX(+k);
        else if (oldhero.getY() - hero.getCoords().getY() != 0)
            if (oldhero.getY() - hero.getCoords().getY() >0)
                oldhero.adjustY(-k);
            else
                oldhero.adjustY(k);
    }
    static public int isCol(){
        nowclick = null;
        if (hero.iscollision(gold))
            nowclick = gold;
        if (hero.iscollision(chest))
            nowclick = chest;
        if (nowclick != null) {
            qmark.getCoords().setX(nowclick.getCoords().getX());
            qmark.getCoords().setY(nowclick.getCoords().getY());
            qmark.getCoords().adjustY(-25);
            qmark.getCoords().adjustX(25);
        }
        for (spriteInfo x : room)
            if (hero.iscollision(x)) {
                int k = 0;
                switch (room.indexOf(x)){
                    case 0:
                        //top
                        k= Math.abs(x.height-hero.getCoords().getY()-Config.movepixel );
                        break;
                    case 1:
                        //bottom
                        k= Math.abs(hero.getCoords().getY() + hero.height-x.getCoords().getY()-Config.movepixel);
                        break;
                    case 2:
                        //right
                        k= Math.abs(hero.width+hero.getCoords().getX() - x.getCoords().getX()-Config.movepixel);
                        break;
                    case 3:
                        //left
                        k= Math.abs(hero.getCoords().getX() - x.width+Config.movepixel);
                        break;
                }
                if (k != Config.movepixel)
                    return k;
            }
        return -1;
    }
    static void startstring() {
        if (nowclick != null) {
            lastclick = nowclick;
            currentTextCharIndex = 0;
            currentText = "";
            fade = fadeMax;
        }
    }
}

// Additional Static methods below...(if needed)



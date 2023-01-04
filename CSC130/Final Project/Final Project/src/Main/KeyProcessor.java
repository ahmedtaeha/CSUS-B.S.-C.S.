/* This will handle the "Hot Key" system. */

package Main;

import logic.Control;
import timer.stopWatchX;

public class KeyProcessor{
	// Static Fields
	private static char last = ' ';			// For debouncing purposes
	private static stopWatchX sw = new stopWatchX(250);
	
	// Static Method(s)
	public static void processKey(char key){
		if(key == ' ')				return;
		// Debounce routine below...
		if(key == last)
			if(sw.isTimeUp() == false){		Main.isImageDrawn = true;
				return;}
		sw.resetWatch();
		Main.isImageDrawn = true;
		/* TODO: You can modify values below here! */
		switch(key){
			case '%':								// ESC key
				System.exit(0);
				break;

			case 'm':
				// For mouse coordinates
				Control.isMouseCoordsDisplayed = !Control.isMouseCoordsDisplayed;
				break;

			case 'w':
				if (last == key)
				check(0,-Config.movepixel);
				Main.dir='u';
				break;
			case 's':
				if (last == key)
				check(0,Config.movepixel);
				Main.dir='d';
				break;
			case 'd':
				if (last == key)
					check(Config.movepixel, 0);
					Main.dir = 'r';
				break;
			case 'a':
				if (last == key)
				check(-Config.movepixel,0);
				Main.dir='l';
				break;
			case '$':
				Main.startstring();
				Main.currentSpriteIndex = 0;
				break;
		}
		last = key;
	}
	static void check(int x,int y){
		Main.hero.getCoords().adjustX(x);
		Main.hero.getCoords().adjustY(y);
		int test = Main.isCol();
		if (test ==0 ){
			Main.hero.getCoords().adjustX(-x);
			Main.hero.getCoords().adjustY(-y);
		}else if(test >0) {
			System.out.println(test);
			if (x !=0)
				Main.hero.getCoords().adjustX(-x + (x/Config.movepixel)*test);
			else
				Main.hero.getCoords().adjustY(-y + (y/Config.movepixel)*test);
		}else{
				Main.oldhero.setX(Main.hero.getCoords().getX()-x);
				Main.oldhero.setY(Main.hero.getCoords().getY()-y);
		}
	}
}
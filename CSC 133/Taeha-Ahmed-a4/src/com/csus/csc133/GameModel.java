package com.csus.csc133;

import java.util.HashMap;
import java.util.Observable;
import java.util.Random;

import com.codename1.ui.CN;
import com.codename1.ui.Dialog;
import com.csus.csc133.GameObjectCollection.Iterator;

public class GameModel extends Observable {
	public static double width = 1024;
	public static double height = 768;
	public static int X = 0;
	public static int Y = 0;
	public static int TIMER_TICK = 400;
	private double gametime;
	private int elapsedTime;	// in milliseconds
	private GameObjectCollection gameObjs;
	
	public GameModel()
	{
		gameObjs = new GameObjectCollection();
		gametime = 0;
		elapsedTime = 0;
	}

	public void init(){
		Random rand = new Random();
		
		gameObjs.addGameObject(StudentPlayer.getInstance());
		gameObjs.addGameObject(new LectureHall("Lecture Hall 1"));
		gameObjs.addGameObject(new LectureHall("Lecture Hall 2"));
		gameObjs.addGameObject(new LectureHall("Lecture Hall 3"));
		gameObjs.addGameObject(new StudentStrategy("randomly"));
		gameObjs.addGameObject(new StudentStrategy("randomly"));

		// 3-6 randomly water dispensers
		for(int i=0;i<(rand.nextInt(4)+3);i++)
		{
			gameObjs.addGameObject(new WaterDispenser());
		}
		// 3-6 randomly restrooms
		for(int i=0;i<(rand.nextInt(4)+3);i++)
		{
			gameObjs.addGameObject(new Restroom());
		}

		for(int i=0;i<(rand.nextInt(4));i++)
		{
			gameObjs.addGameObject(new StudentAngry());
		}


		for(int i=0;i<(rand.nextInt(4));i++)
		{
			gameObjs.addGameObject(new StudentBiking());
		}

		for(int i=0;i<(rand.nextInt(4));i++)
		{
			gameObjs.addGameObject(new StudentCar());
		}

		for(int i=0;i<(rand.nextInt(4));i++)
		{
			gameObjs.addGameObject(new StudentFriendly());
		}

		for(int i=0;i<(rand.nextInt(4));i++)
		{
			gameObjs.addGameObject(new StudentHappy());
		}

		for(int i=0;i<(rand.nextInt(4));i++)
		{
			gameObjs.addGameObject(new StudentRunning());
		}

		for(int i=0;i<(rand.nextInt(4));i++)
		{
			gameObjs.addGameObject(new StudentConfused());
		}

		for(int i=0;i<(rand.nextInt(4));i++)
		{
			gameObjs.addGameObject(new StudentNonstop());
		}
	}
	
	//adjust x,y coordinates of each GameObject according to x,y of
	// ViewMap container
//	public void adjustXY(int mapX, int mapY)
//	{
//		Iterator iterator = gameObjs.getIterator();
//		while(iterator.hasNext())
//		{
//			GameObject obj = iterator.getNext();
//			
//			int x = (int)obj.getX();
//			int y = (int)obj.getY();
//			int size = (int)obj.getSize();
//			
//			x += mapX;
//			y += mapY;
//			
//			if(x >= GameModel.width-size)
//			{
//				x = (int)(x % GameModel.width) +  mapX;
//			}
//			if(y >= GameModel.height-size)
//			{
//				y = (int)(y % GameModel.height) + mapY;
//			}
//			
//			obj.setX(x);
//			obj.setY(y);
//		}
//	}
	
	// public method to call Observable's protected method setChanged
	public void changed()
	{
		this.setChanged();
	}
	
	public double getGametime() {
		return gametime;
	}

	public void setGametime(double gametime) {
		this.gametime = gametime;
	}
	
	public int getElapsedTime()
	{
		return elapsedTime;
	}
	
	public void increaseGameTime()
	{
		if(elapsedTime >= 1000 && elapsedTime % 1000 == 0)
		{
			this.gametime++;
		}
	}

	public GameObjectCollection getGameObjsCollection() {
		return gameObjs;
	}

	public StudentPlayer getPlayer()
	{
		Iterator iterator = gameObjs.getIterator();
		while(iterator.hasNext())
		{
			GameObject obj = iterator.getNext();
			if(obj instanceof StudentPlayer)
			{
				return (StudentPlayer)obj;
			}
		}
		
		return null;
	}

	public LectureHall getLecHall()
	{
		Iterator iterator = gameObjs.getIterator();
		while(iterator.hasNext())
		{
			GameObject obj = iterator.getNext();
			if(obj instanceof LectureHall)
			{
				return (LectureHall)obj;
			}
		}
		
		return null;
	}

	public Restroom getRestroom()
	{
		Iterator iterator = gameObjs.getIterator();
		while(iterator.hasNext())
		{
			GameObject obj = iterator.getNext();
			if(obj instanceof Restroom)
			{
				return (Restroom)obj;
			}
		}
		
		return null;
	}

	public WaterDispenser getWaterDispenser()
	{
		Iterator iterator = gameObjs.getIterator();
		while(iterator.hasNext())
		{
			GameObject obj = iterator.getNext();
			if(obj instanceof WaterDispenser)
			{
				return (WaterDispenser)obj;
			}
		}
		
		return null;
	}

	public void nextFrame()
	{
		elapsedTime += TIMER_TICK;
		Random rand = new Random();
		
		// increase game time		
		this.increaseGameTime();
		// decrease hydration
		if(elapsedTime >= 1000 && elapsedTime % 1000 == 0)
		{
			getPlayer().Hydration -= getPlayer().getSweatingRate();
		}
		
		LectureHall lectureHall = getLecHall();
		// is lecture holding
		if(lectureHall.getLecture().getTime() > 0)
		{
			if(elapsedTime >= 1000 && elapsedTime % 1000 == 0)
			{
				lectureHall.getLecture().decreaseTime();
			}
			
			if(lectureHall.getLecture().getTime() == 0)
			{
				getPlayer().absenceTime++;
			}
		}
		else
		{
			// 10% probability
			if(rand.nextDouble() < 1.0)
			{
				// set new lecture with random lecture time to lecturehall
				lectureHall.setLecture(new Lecture(rand.nextInt(10)));
			}
		}
		
		// check and handle collisions
		HashMap<GameObject, GameObject> collided = new HashMap();
		Iterator iterator = gameObjs.getIterator();
		while(iterator.hasNext())
		{
			GameObject obj1 = iterator.getNext();
			
			Iterator iterator1 = gameObjs.getIterator();
			while(iterator1.hasNext())
			{
				GameObject obj2 = iterator1.getNext();

				double x1 = obj1.translation.getTranslateX() + obj1.getSize()/2;
				double y1 = obj1.translation.getTranslateY() + obj1.getSize()/2;
				double x2 = obj2.translation.getTranslateX() + obj2.getSize()/2;
				double y2 = obj2.translation.getTranslateY() + obj2.getSize()/2;
				double r1 = obj1.getSize()/2;
				double r2 = obj2.getSize()/2;

				if(obj1 == obj2 || collided.containsValue(obj2))
				{
					continue;
				}
				
				if(obj1 instanceof Student && !(obj2 instanceof Student))
				{
					if(isTouched(x1, y1, x2, y2, r1, r2))
					{
						obj2.handleCollide((Student)obj1);
//						collided.put(obj1, obj2);
						this.changed();
						this.notifyObservers(obj1.getClass().getSimpleName()+" collide with " + obj2.getClass().getSimpleName());
					}

				}
				
				if(obj1 instanceof Student && obj2 instanceof Student)
				{
					// if already talking
					if(((Student)obj1).getTimeRemain() > 0 || ((Student)obj2).getTimeRemain() > 0)
					{
						continue;
					}					
					
					if(isTouched(x1, y1, x2, y2, r1, r2))
					{
						obj1.handleCollide((Student)obj2);
						collided.put(obj1, obj2);
						this.changed();
						this.notifyObservers(obj1.getClass().getSimpleName()+" collide with " + obj2.getClass().getSimpleName());
					}
				}
			}
		}
		
		// move all objects accordingly
		iterator = gameObjs.getIterator();
		while(iterator.hasNext())
		{
			iterator.getNext().move(elapsedTime);
		}
		
		// check if game is ended
		if(getPlayer().absenceTime > 5 || getPlayer().waterintake > 5 
				|| getPlayer().Hydration == 0)
		{
//			System.out.println("Gameover. Time: "+ this.gametime);
			this.setChanged();
			this.notifyObservers("Gameover. Time: "+ this.gametime);
			boolean res = Dialog.show("Confirm", "Gameover. Time: "+ this.gametime, "Confirm", null);
			if (res) {
				CN.exitApplication();
			}
		}
		
		this.changed();
		this.notifyObservers();
	}
	
	private boolean isTouched(double x1, double y1, double x2, double y2, double r1, double r2) {
		double d = Math.sqrt(((x1 - x2) * (x1 - x2)) + ((y1 - y2) * (y1 - y2)));

		if (d <= r1 - r2) {
			return true;
		} else if (d <= r2 - r1) {
			return true;
		} else if (d < r1 + r2) {
			return true;
		} else if (d == r1 + r2) {
			return true;
		} else {
			return false;
		}
	}
	
	public void move()
	{
		Iterator iterator = gameObjs.getIterator();
		while(iterator.hasNext())
		{
			GameObject obj = iterator.getNext();
			if(obj instanceof StudentPlayer)
				obj.move(elapsedTime);
		}
	}
	
	public StudentAngry getStudentAngry()
	{
		Iterator iterator = gameObjs.getIterator();
		while(iterator.hasNext())
		{
			GameObject obj = iterator.getNext();
			if(obj instanceof StudentAngry)
			{
				return (StudentAngry)obj;
			}
		}
		
		return null;
	}

	public StudentBiking getStudentBiking()
	{
		Iterator iterator = gameObjs.getIterator();
		while(iterator.hasNext())
		{
			GameObject obj = iterator.getNext();
			if(obj instanceof StudentBiking)
			{
				return (StudentBiking)obj;
			}
		}
		
		return null;
	}

	public StudentCar getStudentCar()
	{
		Iterator iterator = gameObjs.getIterator();
		while(iterator.hasNext())
		{
			GameObject obj = iterator.getNext();
			if(obj instanceof StudentCar)
			{
				return (StudentCar)obj;
			}
		}
		
		return null;
	}

	public StudentConfused getStudentConfused()
	{
		Iterator iterator = gameObjs.getIterator();
		while(iterator.hasNext())
		{
			GameObject obj = iterator.getNext();
			if(obj instanceof StudentConfused)
			{
				return (StudentConfused)obj;
			}
		}
		
		return null;
	}
	
	public StudentFriendly getStudentFriendly()
	{
		Iterator iterator = gameObjs.getIterator();
		while(iterator.hasNext())
		{
			GameObject obj = iterator.getNext();
			if(obj instanceof StudentFriendly)
			{
				return (StudentFriendly)obj;
			}
		}
		
		return null;
	}

	public StudentHappy getStudentHappy()
	{
		Iterator iterator = gameObjs.getIterator();
		while(iterator.hasNext())
		{
			GameObject obj = iterator.getNext();
			if(obj instanceof StudentHappy)
			{
				return (StudentHappy)obj;
			}
		}
		
		return null;
	}

	public StudentNonstop getStudentNonstop()
	{
		Iterator iterator = gameObjs.getIterator();
		while(iterator.hasNext())
		{
			GameObject obj = iterator.getNext();
			if(obj instanceof StudentNonstop)
			{
				return (StudentNonstop)obj;
			}
		}
		
		return null;
	}

	public StudentRunning getStudentRunning()
	{
		Iterator iterator = gameObjs.getIterator();
		while(iterator.hasNext())
		{
			GameObject obj = iterator.getNext();
			if(obj instanceof StudentRunning)
			{
				return (StudentRunning)obj;
			}
		}
		
		return null;
	}

	public StudentStrategy getStudentStrategy(String strategy)
	{
		Iterator iterator = gameObjs.getIterator();
		while(iterator.hasNext())
		{
			GameObject obj = iterator.getNext();
			if(obj instanceof StudentStrategy && ((StudentStrategy)obj).getStrategyName().equals(strategy))
			{
				return (StudentStrategy)obj;
			}
		}
		
		return null;
	}
	
	public void changeStrategy(String strategy)
	{
		Iterator iterator = gameObjs.getIterator();
		while(iterator.hasNext())
		{
			GameObject obj = iterator.getNext();
			if(obj instanceof StudentStrategy)
			{
				((StudentStrategy) obj).setStrategyName(strategy);
				((StudentStrategy) obj).apply();
			}
		}
	}
	
}

package com.csus.csc133;

import java.util.ArrayList;

public class GameObjectCollection {
	private ArrayList<GameObject> gameObjs;
	
	public GameObjectCollection()
	{
		gameObjs = new ArrayList<GameObject>();
	}
	
	public void addGameObject(GameObject obj)
	{
		gameObjs.add(obj);
	}
	
	public Iterator getIterator()
	{
		return new Iterator();
	}

	class Iterator {
		int current;
		
		Iterator()
		{
			if(gameObjs.isEmpty())
			{
				current = -1;
			}
			else
			{
				current = 0;
			}
		}
		
		boolean hasNext()
		{
			return current < gameObjs.size();
		}
		
		GameObject getNext()
		{
			if(current == -1)
			{
				return null;
			}
			
			GameObject obj = gameObjs.get(current);
			current++;
			return obj;
		}
	}
}

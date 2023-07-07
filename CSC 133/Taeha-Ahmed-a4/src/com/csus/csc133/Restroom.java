package com.csus.csc133;

public class Restroom extends Building {
	public Restroom()
	{
		super();
	}

	@Override
	public void move(int elapsedTime) {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleCollide(Student s) {
		// TODO Auto-generated method stub
		
		// clear waterintake
		s.waterintake = 0;
	}

	public String toString()
	{
		return "Restroom, "+super.toString();
	}
}

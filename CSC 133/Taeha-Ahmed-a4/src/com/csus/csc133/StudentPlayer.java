package com.csus.csc133;

public class StudentPlayer extends Student {
	private static StudentPlayer instance = null;
	
	// private constructor to make this class Singleton
	private StudentPlayer()
	{
		super();
		this.speed = 0;
	}
	
	public static StudentPlayer getInstance()
	{
		if(instance == null)
		{
			instance = new StudentPlayer();
		}
		return instance;
	}
	
	public String toString()
	{
		return "StudentPlayer, "+ super.toString()+", absenceTime: " + this.absenceTime+", WaterIntake: " + this.waterintake;
	}

	@Override
	public void move(int elapsedTime) {
		// TODO Auto-generated method stub
		if(this.speed > 0)
		{
			super.move(elapsedTime);
		}
	}

	@Override
	public void handleCollide(Student s) {
		// TODO Auto-generated method stub
		super.handleCollide(s);
	}
	
	public void startMoving(int elapsedTime)
	{
		this.speed = Student.DEFAULT_SPEED;
//		super.move(elapsedTime);
	}
	
	public void stopMoving()
	{
		this.speed = 0;
	}
	
	public void turnLeft()
	{
		this.head -= 5;
	}
	
	public void turnRight()
	{
		this.head += 5;
	}
}

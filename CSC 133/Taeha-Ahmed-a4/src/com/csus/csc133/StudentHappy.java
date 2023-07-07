package com.csus.csc133;

import java.util.Random;

public class StudentHappy extends Student {
	public StudentHappy()
	{
		super();
	}
	
	public String toString()
	{
		return "StudentHappy, "+ super.toString();
	}

	@Override
	public void move(int elapsedTime) {
		// TODO Auto-generated method stub
		
		// at 10% probability increase speed
		
		Random rand = new Random();
		double prob = rand.nextDouble();
		if(prob < 1.0)
		{
			speed += prob * 10;
		}
		
		super.move(elapsedTime);
		
		// set back after move
		speed = Student.DEFAULT_SPEED;
	}

	@Override
	public void handleCollide(Student s) {
		// TODO Auto-generated method stub
		super.handleCollide(s);
	}

}

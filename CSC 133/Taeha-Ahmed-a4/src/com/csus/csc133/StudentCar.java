package com.csus.csc133;

import java.util.Random;

public class StudentCar extends Student {
	public StudentCar()
	{
		super();
		this.speed *= 5;
		this.sweatingRate = 0;
	}
	
	public String toString()
	{
		return "StudentCar, "+ super.toString();
	}

	@Override
	public void move(int elapsedTime) {
		// TODO Auto-generated method stub
		// only move horizontally, i.e. head is 90 or 270
		if(new Random().nextInt(2) == 1)
		{
			head = 270;
		}
		else
		{
			head = 90;
		}
		super.move(elapsedTime);
	}

	@Override
	public void handleCollide(Student s) {
		// TODO Auto-generated method stub
		super.handleCollide(s);
	}
}

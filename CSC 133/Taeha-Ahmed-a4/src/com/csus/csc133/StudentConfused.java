package com.csus.csc133;

import java.util.Random;

public class StudentConfused extends Student {
	public StudentConfused()
	{
		super();
	}
	
	public String toString()
	{
		return "StudentConfused, "+ super.toString();
	}

	@Override
	public void move(int elapsedTime) {
		// TODO Auto-generated method stub
		
		head = 400 * new Random().nextDouble();
		super.move(elapsedTime);
	}

	@Override
	public void handleCollide(Student s) {
		// TODO Auto-generated method stub
		super.handleCollide(s);
	}

}

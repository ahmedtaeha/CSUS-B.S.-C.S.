package com.csus.csc133;

public class StudentAngry extends Student {
	public StudentAngry()
	{
		super();
		talkingTime *= 2;
	}
	
	public String toString()
	{
		return "StudentAngry, "+ super.toString();
	}

	@Override
	public void move(int elapsedTime) {
		// TODO Auto-generated method stub
		super.move(elapsedTime);
	}

	@Override
	public void handleCollide(Student s) {
		// TODO Auto-generated method stub
		super.handleCollide(s);
	}
}

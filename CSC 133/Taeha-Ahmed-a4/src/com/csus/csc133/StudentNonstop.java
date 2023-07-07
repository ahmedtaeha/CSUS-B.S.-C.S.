package com.csus.csc133;

public class StudentNonstop extends Student {
	public StudentNonstop()
	{
		super();
		this.timeRemain = 0;
	}
	
	public String toString()
	{
		return "StudentNonstop, "+ super.toString();
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

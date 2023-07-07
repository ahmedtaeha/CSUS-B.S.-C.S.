package com.csus.csc133;

public class StudentRunning extends Student {
	public StudentRunning()
	{
		super();
		this.sweatingRate *= 2;
	}
	
	public String toString()
	{
		return "StudentRunning, "+ super.toString();
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

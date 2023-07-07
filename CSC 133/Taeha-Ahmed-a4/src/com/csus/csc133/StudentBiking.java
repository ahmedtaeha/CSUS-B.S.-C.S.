package com.csus.csc133;

public class StudentBiking extends Student {
	public StudentBiking()
	{
		super();
		this.speed *= 3;
		this.sweatingRate *= 2;
	}
	
	public String toString()
	{
		return "StudentBiking, "+ super.toString();
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

package com.csus.csc133;

public class StudentFriendly extends Student {
	public StudentFriendly()
	{
		super();
		this.talkingTime *= 0.5;
	}
	
	public String toString()
	{
		return "StudentFriendly, "+ super.toString();
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

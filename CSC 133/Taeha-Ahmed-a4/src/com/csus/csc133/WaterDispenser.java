package com.csus.csc133;

public class WaterDispenser extends Building {
	public WaterDispenser()
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
		s.waterintake += Student.DEFAULT_HYDRATION - s.getHydration();
		s.Hydration = Student.DEFAULT_HYDRATION;
	}

	public String toString()
	{
		return "WaterDispenser, "+super.toString();
	}
}

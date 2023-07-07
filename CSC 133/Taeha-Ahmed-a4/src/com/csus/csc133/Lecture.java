package com.csus.csc133;

public class Lecture {
	private double time;
	
	public Lecture()
	{
		time = 0;
	}
	
	public Lecture(int time)
	{
		this.time = time;
	}

	public double getTime() {
		return time;
	}

	public void setTime(double time) {
		this.time = time;
	}
	
	public void decreaseTime()
	{
		time--;
	}
	
	public String toString()
	{
		return "remaining " + Math.round(time*10.0)/10.0;
	}
}

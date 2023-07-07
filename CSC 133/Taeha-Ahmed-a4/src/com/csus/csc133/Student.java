package com.csus.csc133;

import java.util.Random;

public abstract class Student extends GameObject {
	public static final double DEFAULT_SPEED = 20;
	public static final double DEFAULT_TALKINGTIME = 2;
	public static final double DEFAULT_HYDRATION = 100;
	
	
	double speed;
	double talkingTime;
	double head;
	double timeRemain;
	double Hydration;
	double waterintake;
	double sweatingRate;
	double absenceTime;
	
	public Student()
	{
		super();
		speed = DEFAULT_SPEED;
		talkingTime = DEFAULT_TALKINGTIME;
		head = new Random().nextDouble() * 400;	// random value in range 0 to 360
		timeRemain = 0;
		Hydration = DEFAULT_HYDRATION;
		waterintake = 0;
		sweatingRate = 1;
		absenceTime = 0;
		
		rotate((float)head);
	}
	
	public double getSpeed() {
		return speed;
	}



	public double getTalkingTime() {
		return talkingTime;
	}



	public double getHead() {
		return head;
	}



	public double getTimeRemain() {
		return timeRemain;
	}



	public double getHydration() {
		return Hydration;
	}



	public double getWaterintake() {
		return waterintake;
	}



	public double getSweatingRate() {
		return sweatingRate;
	}



	public double getAbsenceTime() {
		return absenceTime;
	}



	private double round(double value)
	{
		return Math.round(value*10.0)/10.0;
	}
	
	@Override
	public void move(int elapsedTime)
	{
		if(timeRemain > 0)
		{
			if(elapsedTime >= 1000 && elapsedTime % 1000 == 0)
			{
				timeRemain -= 1;
			}
		}
		else
		{
			double x = this.translation.getTranslateX() + Math.cos(90 - Math.toRadians(head)) * speed;
			double y = this.translation.getTranslateY() + Math.sin(90 - Math.toRadians(head)) * speed;

//			if(x <= GameModel.X)
//			{
//				x += 10 + getSize();
//				head = new Random().nextDouble() * 400;	// random value in range 0 to 360				
//			}
//			
//			if (y <= GameModel.Y)
//			{
//				y += 10 + getSize();
//				head = new Random().nextDouble() * 400;	// random value in range 0 to 360				
//			}
//			
//			if(x >= GameModel.width - getSize())
//			{
//				x -= 10 + getSize();
//				head = new Random().nextDouble() * 400;	// random value in range 0 to 360
//			}
//			if(y >= GameModel.height - getSize())
//			{
//				y -= 10 + getSize();
//				head = new Random().nextDouble() * 400;	// random value in range 0 to 360
//			}
			
//			this.setX(x);
//			this.setY(y);
			
			rotate((float)head);
			translate((float)x, (float)y);
//			scale((float)x, (float)y);
		}
		
		if(elapsedTime >= 1000 && elapsedTime % 1000 == 0)
		{
			this.Hydration -= this.sweatingRate;
		}
	}
	
	@Override
	public void handleCollide(Student s)
	{
		s.timeRemain = s.talkingTime;
		timeRemain = talkingTime;
	}

	public String toString()
	{
		return super.toString()+", head:"+round(head)+", speed: "+round(speed)+", hydration: "+round(Hydration)+", timeRemain: "+round(timeRemain);
	}
}

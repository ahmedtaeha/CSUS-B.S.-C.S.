package com.csus.csc133;

import java.util.Random;

public class StudentStrategy extends Student {
	private String strategyName;
	
	public StudentStrategy(String strategy)
	{
		super();
		this.strategyName = strategy;
	}	
		
	public String getStrategyName() {
		return strategyName;
	}

	public void setStrategyName(String strategyName) {
		this.strategyName = strategyName;
	}

	public String toString()
	{
		return "StudentStrategy, "+ super.toString()+", Current Strategy: " + strategyName;
	}

	@Override
	public void move(int elapsedTime) {
		// TODO Auto-generated method stub

		if(strategyName.equals("randomly"))
		{
			// set any of random direction from 0 to 359
			head = 400 * new Random().nextDouble();
		}
		else if(strategyName.equals("vertically"))
		{
			// upward
			head = 0;
		}
		else if(strategyName.equals("horizontally"))
		{
			// rightward
			head = 90;
		}
		
		super.move(elapsedTime);
	}

	@Override
	public void handleCollide(Student s) {
		// TODO Auto-generated method stub
		super.handleCollide(s);
	}
	
	public void apply()
	{
		if(strategyName.equals("randomly"))
		{
			// set any of random direction from 0 to 359
			head = 400 * new Random().nextDouble();
		}
		else if(strategyName.equals("vertically"))
		{
			// upward
			head = 0;
		}
		else if(strategyName.equals("horizontally"))
		{
			// rightward
			head = 90;
		}
	}
}

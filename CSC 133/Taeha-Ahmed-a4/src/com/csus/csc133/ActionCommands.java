package com.csus.csc133;

import com.codename1.charts.models.Point;
import com.codename1.ui.CN;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionEvent.Type;
import com.codename1.ui.layouts.BoxLayout;

public class ActionCommands extends Command {
	private GameModel gm;

	public ActionCommands(String command, GameModel gm) {
		super(command);
		this.gm = gm;
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getEventType() == Type.PointerPressed)
		{
			gm.changed();
			gm.notifyObservers(new Point(e.getX(), e.getY()));
			return;
		}
		
		switch(e.getKeyEvent())
		{
		case 119:
		case 87:
			startMoving();
			break;
		case 115:
		case 83:
			stopMoving();
			break;
		case 97:
		case 65:
			turnLeft();
			break;
		case 100:
		case 68:
			turnRight();
			break;
		}

		if(e.getCommand() == null)
			return;
		
		switch (e.getCommand().toString()) {
		case "Exit":
			exit();
			break;
		case "Move":
			startMoving();
			break;
		case "Stop":
			stopMoving();
			break;
		case "Turn Left":
			turnLeft();
			break;
		case "Turn Right":
			turnRight();
			break;
		case "Restroom":
			collideRestroom();
			break;
		case "Water Dispenser":
			collideWaterdispenser();
			break;
		case "Student":
			selectStudent();
			break;
		case "Next Frame":
			moveToNextFrame();
			break;
		}

	}

	private void exit() {
		boolean res = Dialog.show("Confirm", "Do you really want to exit?", "Yes", "No");
		if (res) {
			CN.exitApplication();
		}
	}
	
	private void startMoving()
	{
		gm.getPlayer().startMoving(gm.getElapsedTime());
		gm.increaseGameTime();
		gm.changed();
		gm.notifyObservers("Student start moving");
	}
	
	private void stopMoving()
	{
		gm.getPlayer().stopMoving();
		gm.changed();
		gm.notifyObservers("Student stopped moving");
	}
	
	private void turnLeft()
	{
		gm.getPlayer().turnLeft();
		gm.changed();
		gm.notifyObservers("Student turned left");
	}
	
	private void turnRight()
	{
		gm.getPlayer().turnRight();
		gm.changed();
		gm.notifyObservers("Student turned right");
	}
	
	private void collideRestroom()
	{
		gm.getRestroom().handleCollide(gm.getPlayer());
		gm.changed();
		gm.notifyObservers("Student collided restroom");
	}

	private void collideWaterdispenser()
	{
		gm.getWaterDispenser().handleCollide(gm.getPlayer());
		gm.changed();
		gm.notifyObservers("Student collided water dispenser");
	}

	private void selectStudent()
	{
		// make an input dialog
		Container container = new Container(new BoxLayout(BoxLayout.Y_AXIS));
		container.setWidth((int) (GameModel.width/2));
		container.add(new Label("Please select student (0-7)."));
		TextField tf = new TextField();
		container.add(tf);
		// create a new command to handle input from input dialog with 
		// overloaded actionPerformed method.		
		Command cmd = new Command("Ok") {
			public void actionPerformed(ActionEvent e)
			{
				try {
					int stdNum = Integer.parseInt(tf.getText());
					
					if(stdNum == 0)
					{
						gm.getStudentAngry().handleCollide(gm.getPlayer());
						gm.changed();
						gm.notifyObservers("Player collided with StudentAngry");
					}
					else if(stdNum == 1)
					{
						gm.getStudentBiking().handleCollide(gm.getPlayer());
						gm.changed();
						gm.notifyObservers("Player collided with StudentBiking");
					}
					else if(stdNum == 2)
					{
						gm.getStudentCar().handleCollide(gm.getPlayer());
						gm.changed();
						gm.notifyObservers("Player collided with StudentCar");
					}
					else if(stdNum == 3)
					{
						gm.getStudentConfused().handleCollide(gm.getPlayer());
						gm.changed();
						gm.notifyObservers("Player collided with StudentConfused");
					}
					else if(stdNum == 4)
					{
						gm.getStudentFriendly().handleCollide(gm.getPlayer());
						gm.changed();
						gm.notifyObservers("Player collided with StudentFriendly");
					}
					else if(stdNum == 5)
					{
						gm.getStudentHappy().handleCollide(gm.getPlayer());
						gm.changed();
						gm.notifyObservers("Player collided with StudentHappy");
					}
					else if(stdNum == 6)
					{
						gm.getStudentNonstop().handleCollide(gm.getPlayer());
						gm.changed();
						gm.notifyObservers("Player collided with StudentNonstop");
					}
					else if(stdNum == 7)
					{
						gm.getStudentRunning().handleCollide(gm.getPlayer());
						gm.changed();
						gm.notifyObservers("Player collided with StudentRunning");
					}
					else
					{
						gm.changed();
						gm.notifyObservers("Invalid input for student selection.");						
					}
				}catch(NumberFormatException ex)
				{
					gm.changed();
					gm.notifyObservers("Invalid input for student selection.");
				}
			}
		};

		Dialog.show("Test", container, new Command[] {cmd, new Command("Cancel")});
	}
	
	private void moveToNextFrame()
	{
		gm.changed();
		gm.notifyObservers("Student moved to next frame");
		gm.nextFrame();
	}
}

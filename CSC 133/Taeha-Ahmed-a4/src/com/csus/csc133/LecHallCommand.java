package com.csus.csc133;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class LecHallCommand extends Command {
	private GameModel gm;

	public LecHallCommand(String command, GameModel gm) {
		super(command);
		
		this.gm = gm;
	}

	public void actionPerformed(ActionEvent e)
	{
		if(e.getCommand().toString().equals("Lecture Hall"))
		{
			collideLectureHall();
		}
	}

	private void collideLectureHall()
	{
		gm.getLecHall().handleCollide(gm.getPlayer());
		gm.changed();
		gm.notifyObservers("Student collided lecture hall");
	}
}

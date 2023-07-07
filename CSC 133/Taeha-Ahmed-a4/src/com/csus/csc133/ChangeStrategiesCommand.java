package com.csus.csc133;

import java.util.Random;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class ChangeStrategiesCommand extends Command {
	private GameModel gm;

	public ChangeStrategiesCommand(String command, GameModel gm) {
		super(command);
		this.gm = gm;
	}


	public void actionPerformed(ActionEvent e) {
		Random rand = new Random();
		
		// change StudentStrategy to any of the following randomly.
		// 1. Randomly, 2. Horizontally, 3. Vertically
		int num = rand.nextInt(3);
		
		if(num == 0)
		{
			gm.changeStrategy("randomly");
			gm.changed();
			gm.notifyObservers("StudentStrategy changed to randomly");
		}
		else if(num == 1)
		{
			gm.changeStrategy("vertically");
			gm.changed();
			gm.notifyObservers("StudentStrategy changed to vertically");
		}
		else if(num == 2)
		{
			gm.changeStrategy("horizontally");
			gm.changed();
			gm.notifyObservers("StudentStrategy changed to horizontally");
		}
	}
}

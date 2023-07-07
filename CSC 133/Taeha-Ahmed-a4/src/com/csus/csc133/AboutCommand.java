package com.csus.csc133;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;

public class AboutCommand extends Command {

	public AboutCommand(String command) {
		super(command);
	}

	public void actionPerformed(ActionEvent e)
	{
		System.out.println("x: " + e.getX()+" y: " + e.getY());
		Dialog.show("A4", "Ahmed Taeha\nCSC 133", "Confirm", null);
	}
}

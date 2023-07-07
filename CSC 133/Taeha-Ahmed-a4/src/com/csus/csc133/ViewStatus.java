package com.csus.csc133;

import java.util.Observable;
import java.util.Observer;

import com.codename1.ui.Container;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;

public class ViewStatus extends Container implements Observer {
	private Label lecName = new Label("No class now");
	private Label lecTimeRem = new Label("0.0");
	private Label gameTime = new Label("0.0");
	private Label absence = new Label("0.0");
	private Label hydration = new Label("0.0");
	private Label waterIntakes = new Label("0.0");
	private Label hold = new Label("0.0");

	
	public ViewStatus()
	{
		this.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
		Label lecHall = new Label("Lecture Hall");
		lecName = new Label("No class now");
		Label lbl1 = new Label("Lecture Time Remaining");
		lecTimeRem = new Label("0.0");
		Label lbl2 = new Label("Game Time");
		gameTime = new Label("0.0");
		Label lbl3 = new Label("Absence");
		absence = new Label("0.0");
		Label lbl4 = new Label("Hydration");
		hydration = new Label("0.0");
		Label lbl5 = new Label("WaterIntakes");
		waterIntakes = new Label("0.0");
		Label lbl6 = new Label("Hold");
		hold = new Label("0.0");

		this.addAll(lecHall, lecName, lbl1, lecTimeRem, lbl2, gameTime,
				lbl3, absence, lbl4, hydration, lbl5, waterIntakes, lbl6, hold);
	}

	private double round(double value)
	{
		return Math.round(value*10.0)/10.0;
	}
	
	@Override
	public void update(Observable observable, Object data) {
		// TODO Auto-generated method stub
		
		GameModel gm = (GameModel) observable;
		LectureHall lecH = gm.getLecHall();
		if(lecH.getLecture().getTime() == 0.0)
		{
			lecName.setText("No class now");
		}
		else
		{
			lecName.setText(String.valueOf(round(lecH.getLecture().getTime())));
		}
		gameTime.setText(String.valueOf(round(gm.getGametime())));
		absence.setText(String.valueOf(round(gm.getPlayer().getAbsenceTime())));
		hydration.setText(String.valueOf(round(gm.getPlayer().getHydration())));
		waterIntakes.setText(String.valueOf(round(gm.getPlayer().getWaterintake())));
		hold.setText(String.valueOf(round(gm.getPlayer().getTimeRemain())));
	}
}

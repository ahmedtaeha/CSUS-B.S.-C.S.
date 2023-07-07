package com.csus.csc133;

import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.UITimer;
import com.csus.csc133.GameObjectCollection.Iterator;

public class SacRun extends Form implements Runnable {
	
	private GameModel gm;
	private ViewMap viewMap;
	
	public SacRun(){
		gm = new GameModel();
					
		A4();
		
		gm.init();
		
//		gm.adjustXY(viewMap.getX() - viewMap.getParent().getAbsoluteX(), viewMap.getY() - viewMap.getParent().getAbsoluteY());
		GameModel.X = viewMap.getX();
		GameModel.Y = viewMap.getY();
		
		gm.changed();
		gm.notifyObservers();
		UITimer.timer(GameModel.TIMER_TICK, true, this);
	}
	
	private void A4() {
		viewMap = new ViewMap();
		ViewMessage viewMessage = new ViewMessage();
		ViewStatus viewStatus = new ViewStatus();
		
		gm.addObserver(viewStatus);
		gm.addObserver(viewMessage);
		gm.addObserver(viewMap);

//		LecHallCommand lecHallCmd = new LecHallCommand("Lecture Hall", gm);
		ChangeStrategiesCommand chngStratCmd = new ChangeStrategiesCommand("Change Strategies", gm);
		AboutCommand aboutCmd = new AboutCommand("About");
		
		this.getToolbar().addMaterialCommandToSideMenu("Change Strategies", ' ', chngStratCmd);
		this.getToolbar().addMaterialCommandToSideMenu("About", ' ', aboutCmd);
		this.getToolbar().addMaterialCommandToSideMenu("Exit", ' ', new ActionCommands("Exit", gm));
//		this.getToolbar().addMaterialCommandToRightBar("Lecture Hall", ' ', lecHallCmd);
		this.getToolbar().addMaterialCommandToRightBar("About", ' ', aboutCmd);
	
		Container btnContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
		
		Button moveBtn = new Button(new ActionCommands("Move", gm));
		btnContainer.add(moveBtn);
		Button stopBtn = new Button(new ActionCommands("Stop", gm));
		btnContainer.add(stopBtn);
		Button turnLeftBtn = new Button(new ActionCommands("Turn Left", gm));
		btnContainer.add(turnLeftBtn);
		Button turnRightBtn = new Button(new ActionCommands("Turn Right", gm));
		btnContainer.add(turnRightBtn);
		Button chngStrategiesBtn = new Button(chngStratCmd);
		btnContainer.add(chngStrategiesBtn);
//		Button lecHallBtn = new Button(lecHallCmd);
//		btnContainer.add(lecHallBtn);
//		Button restroomBtn = new Button(new ActionCommands("Restroom", gm));
//		btnContainer.add(restroomBtn);
//		Button waterDispBtn = new Button(new ActionCommands("Water Dispenser", gm));
//		btnContainer.add(waterDispBtn);
//		Button studentBtn = new Button(new ActionCommands("Student", gm));
//		btnContainer.add(studentBtn);
		Button nextFrameBtn = new Button(new ActionCommands("Next Frame", gm));
		btnContainer.add(nextFrameBtn);
		
		this.setLayout(new BorderLayout());
		this.add(BorderLayout.CENTER, viewMap);
//		this.add(BorderLayout.NORTH, new Label("North"));
		this.add(BorderLayout.SOUTH, viewMessage);
		this.add(BorderLayout.EAST, viewStatus);
		this.add(BorderLayout.WEST, btnContainer);

this.addLongPressListener(aboutCmd);

		this.addKeyListener(119, new ActionCommands("Move", gm));
		this.addKeyListener(87, new ActionCommands("Move", gm));
		this.addKeyListener(65, new ActionCommands("Turn Left", gm));
		this.addKeyListener(97, new ActionCommands("Turn Left", gm));
		this.addKeyListener(68, new ActionCommands("Turn Right", gm));
		this.addKeyListener(100, new ActionCommands("Trun Right", gm));
		this.addKeyListener(115, new ActionCommands("Stop", gm));
		this.addKeyListener(83, new ActionCommands("Stop", gm));
		
		
		this.show();

		GameModel.width = viewMap.getWidth();
		GameModel.height = viewMap.getHeight();
		viewMap.addPointerPressedListener(new ActionCommands("Pointer Pressed", gm));
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		gm.nextFrame();
	}
}

package com.csus.csc133;

import java.util.Random;

import com.codename1.ui.Transform;

public abstract class GameObject {
//	private double x,y;
	private double size;
	Transform rotation, translation, scale ;
	
	public GameObject()
	{
		Random rand = new Random();
		size = 80;
		float x = (float)((GameModel.width - size) * rand.nextDouble()+size);
		float y = (float)((GameModel.height - size) * rand.nextDouble()+size);
		
		rotation = Transform.makeIdentity();
		translation = Transform.makeIdentity();
		scale = Transform.makeIdentity();
		
		translate((float)x, (float)y);
		scale(1,1);
	}
	
	public abstract void move(int elapsedTime);
	
	public abstract void handleCollide(Student s);

	public String toString()
	{
		return "pos: ("+Math.round(translation.getTranslateX()*10.0f)/10.0f+","+Math.round(translation.getTranslateY()*10.0f)/10.0f+")";
	}

//	public double getX() {
//		return x;
//	}
//
//	public void setX(double x) {
//		this.x = x;
//	}
//
//	public double getY() {
//		return y;
//	}
//
//	public void setY(double y) {
//		this.y = y;
//	}

	public double getSize() {
		return size;
	}

	public void setSize(double size) {
		this.size = size;
	}
	
	public void rotate (float deg) {
		rotation.rotate((float)Math.toRadians(deg),0,0);
	}

	public void translate(float tx, float ty) {
		translation.translate (tx, ty);
	}
	
	public void scale (float sx, float sy) {
		scale.scale(sx, sy);
	}
	
}

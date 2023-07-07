package com.csus.csc133;

import com.codename1.ui.Container;
import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;
import com.codename1.ui.plaf.Border;
import com.csus.csc133.GameObjectCollection.Iterator;

import java.util.Observable;
import java.util.Observer;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;


public class ViewMap extends Container implements Observer {
	private GameModel gm;
	private Point mouseLoc;
	private Point prevDragLoc = new Point(-1, -1);

	Transform worldToND, ndToDisplay, theVTM ;
	private float winLeft, winBottom, winRight, winTop, winWidth, winHeight;

    public ViewMap() {
		this.getStyle().setBorder(Border.createLineBorder(4, ColorUtil.rgb(255,0,0)));
		gm = null;
		// default location at  -1,-1 of mouse
		mouseLoc = new Point(-1, -1);
		
		winLeft = 0;
		winBottom = 0;
		winRight = (float)GameModel.width / 2;
		winTop = (float)GameModel.height / 2;
		winWidth = winRight - winLeft;
		winHeight = winTop - winBottom;

    }

	@Override
	public void update(Observable observable, Object data) {
		// TODO Auto-generated method stub
		
		if(data instanceof Point)
		{
			mouseLoc = (Point) data;
		}
		else
		{
			mouseLoc = new Point(-1,-1);
		}
		
		gm = (GameModel)observable;
		
		if(gm.getElapsedTime() >= 1000 && gm.getElapsedTime() % 1000 == 0)
		{
			System.out.println("Time: "+gm.getGametime()+" =========================");
			Iterator iterator = gm.getGameObjsCollection().getIterator();
			while(iterator.hasNext())
			{
				GameObject obj = iterator.getNext();
				System.out.println(obj.toString());
			}
		}
		repaint();
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);

		worldToND = buildWorldForm(winWidth, winHeight, winLeft, winBottom);
		ndToDisplay = buildDisplayForm(this.getWidth(), this.getHeight());
		theVTM = ndToDisplay.copy();
		theVTM.concatenate(worldToND);

		Transform tForm = Transform.makeIdentity();
		g.getTransform(tForm);
		tForm.translate(getAbsoluteX(), getAbsoluteY()); // local origin xform (part 2)
		tForm.concatenate(theVTM); // VTM xform
		tForm.translate(-getAbsoluteX(), -getAbsoluteY()); // local origin xform (part 1)
		g.setTransform(tForm);
		g.resetAffine();
		
		if (gm != null) {
			Iterator iterator = gm.getGameObjsCollection().getIterator();
			while (iterator.hasNext()) {
				GameObject obj = iterator.getNext();
					
				int size = (int)obj.getSize();
				int x = (int)(obj.translation.getTranslateX() + size/2);
				int y = (int)(obj.translation.getTranslateY() + size/2);
				
//				if(x >= GameModel.width-size)
//				{
//					x = (int)(x % GameModel.width) +  getX() - size;
//				}
//				if(y >= GameModel.height-size)
//				{
//					y = (int)(y % GameModel.height) + getY() - size;
//				}
				

				Transform transform = Transform.makeIdentity(); 
				g.getTransform(transform);
				Transform oForm = transform.copy(); 
				transform.translate(getAbsoluteX(), getAbsoluteY());
				transform.translate(obj.translation.getTranslateX(), obj.translation.getTranslateY());
				transform.concatenate(obj.rotation);
				transform.scale(obj.scale.getScaleX(), obj.scale.getScaleY());
				transform.translate(-getAbsoluteX(), -getAbsoluteY()); 
				g.setTransform(transform);

				if(obj instanceof StudentPlayer)
				{
					int xx[] = new int[] {x,x-size/2,x+size/2};
					int yy[] = new int[] {y,y+size,y+size};
					g.setColor(ColorUtil.rgb(255, 0, 0));
					g.fillPolygon(xx, yy, 3);
					if(isInTriangle(mouseLoc, xx[0], yy[0], xx[1], yy[1], xx[2], yy[2]))
					{
						g.setColor(ColorUtil.rgb(255, 0, 0));
						g.drawRoundRect(x-size/2-size/7, y, size+size/4, size+size/4, size+size/4, size+size/4);
					}
					else
					{
						g.setColor(ColorUtil.rgb(242, 242, 242));
						g.drawRoundRect(x-size/2-size/7, y, size+size/4, size+size/4, size+size/4, size+size/4);
					}
				}
				else if(obj instanceof Student)
				{
					int xx[] = new int[] {x,x-size/2,x+size/2};
					int yy[] = new int[] {y,y+size,y+size};
					g.setColor(ColorUtil.rgb(255, 0, 0));
					g.drawPolygon(xx, yy, 3);
					if(isInTriangle(mouseLoc, xx[0], yy[0], xx[1], yy[1], xx[2], yy[2]))
					{
						g.setColor(ColorUtil.rgb(255, 0, 0));
						g.drawRoundRect(x-size/2-size/7, y, size+size/4, size+size/4, size+size/4, size+size/4);
					}
					else
					{
						g.setColor(ColorUtil.rgb(242, 242, 242));
						g.drawRoundRect(x-size/2-size/7, y, size+size/4, size+size/4, size+size/4, size+size/4);
					}
				}
				else if(obj instanceof LectureHall)
				{
					g.setColor(ColorUtil.rgb(0, 0, 255));
					g.fillRect(x, y, size, size);
					g.drawString(((LectureHall) obj).getName(), x, y+size);
					if(isInRectangle(mouseLoc, x, y, x+size, y+size))
					{
						g.setColor(ColorUtil.rgb(255, 0, 0));
						g.drawRoundRect(x, y, size, size, size, size);
					}
					else
					{
						g.setColor(ColorUtil.rgb(0, 0, 255));
						g.drawRoundRect(x, y, size, size, size, size);						
					}
				}
				else if(obj instanceof WaterDispenser)
				{
					g.setColor(ColorUtil.rgb(0, 0, 255));
					g.fillRoundRect(x, y, size, size, size, size);
					if(isInCircle(mouseLoc, x, y, size/2))
					{
						g.setColor(ColorUtil.rgb(255, 0, 0));
						g.drawRoundRect(x, y, size, size, size, size);
					}
					else
					{
						g.setColor(ColorUtil.rgb(0, 0, 255));
						g.drawRoundRect(x, y, size, size, size, size);						
					}
				}
				else if(obj instanceof Restroom)
				{
					g.setColor(ColorUtil.rgb(0, 255, 0));
					g.fillRect(x, y, size, size);
					g.drawRoundRect(x, y, size, size, size, size);
					
					if(isInRectangle(mouseLoc, x, y, x+size, y+size))
					{
						g.setColor(ColorUtil.rgb(255, 0, 0));
						g.drawRoundRect(x, y, size, size, size, size);
					}
					else
					{
						g.setColor(ColorUtil.rgb(0, 255, 0));
						g.drawRoundRect(x, y, size, size, size, size);						
					}
				}
				g.setTransform(oForm);
				g.resetAffine();
			}
		}
		g.resetAffine();
	}
	
	public void pointerPressed(int x, int y)
	{
		x = x - getParent().getAbsoluteX();
		y = y - getParent().getAbsoluteY();
		
		super.pointerPressed(x, y);
	}
	
	private boolean isInCircle(Point p, int x, int y, double r)
	{
		// center of circle
		double h = x+r;
		double k = y+r;
		// find distance from given point
		double dist = Math.sqrt((p.getX() - h)*(p.getX() - h) + ((p.getY() - k)*(p.getY() - k)));
//		System.out.println(dist);
		return dist <= r;
	}
	
	private boolean isInRectangle(Point p, int x1, int y1, int x2, int y2)
	{
		return (p.getX() > x1 && p.getX() < x2 && p.getY() > y1 && p.getY() < y2);
	}
	
	private double area(int x1, int y1, int x2, int y2, int x3, int y3)
	{
		return Math.abs((x1*(y2-y3) + x2*(y3-y1)+ x3*(y1-y2))/2.0);
	}
	
	private boolean isInTriangle(Point p, int x1, int y1, int x2, int y2, int x3, int y3)
	{
		// area of actual triangle
        double a = area (x1, y1, x2, y2, x3, y3);

        // area of triangle with point p
        double a1 = area ((int)p.getX(), (int)p.getY(), x2, y2, x3, y3);
      
        // area of triangle with point p
        double a2 = area (x1, y1, (int)p.getX(), (int)p.getY(), x3, y3);
      
        // area of triangle with point p
        double a3 = area (x1, y1, x2, y2, (int)p.getX(), (int)p.getY());
        
        // if all sums with points are same as a then its inside the triangle
        return (a == a1 + a2 + a3);	
	}
	
	private Transform buildWorldForm(float winWidth, float winHeight, float winLeft, float winBottom)
	{
		Transform tmp = Transform.makeIdentity();
		tmp.scale((1 / winWidth), (1 / winHeight));
		tmp.translate(-winLeft, -winBottom);
		return tmp;
	}
	
	private Transform buildDisplayForm(float displayWidth, float displayHeight)
	{
		Transform tmp = Transform.makeIdentity();
		tmp.translate(0, displayHeight);
		tmp.scale(displayWidth, -displayHeight);
		return tmp;
	}

	public void zoom(float factor) {
		float newWinLeft = winLeft + winWidth * factor;
		float newWinRight = winRight - winWidth * factor;
		float newWinTop = winTop - winHeight * factor;
		float newWinBottom = winBottom + winHeight * factor;
		float newWinHeight = newWinTop - newWinBottom;
		float newWinWidth = newWinRight - newWinLeft;

		if (newWinWidth <= 1000 && newWinHeight <= 1000 && newWinWidth > 0 && newWinHeight > 0)
		{
			winLeft = newWinLeft;
			winRight = newWinRight;
			winTop = newWinTop;
			winBottom = newWinBottom;
			winWidth = newWinWidth;
			winHeight = newWinHeight;
		}
		this.repaint();
	}

	public void panHorizontal(double delta)
	{
		winLeft += delta;
		winRight += delta;
		this.repaint();
	}

	public void panVertical(double delta) {
		winBottom += delta;
		winTop += delta;
		this.repaint();
	}

	@Override
	public boolean pinch(float scale) {
		if (scale < 1.0) {
			zoom(-0.05f);
		} else if (scale > 1.0) {
			zoom(0.05f);
		}
		return true;
	}

	@Override
	public void pointerDragged(int x, int y) {
		if (prevDragLoc.getX() != -1) {
			if (prevDragLoc.getX() < x) {
				panHorizontal(5);
			} else if (prevDragLoc.getX() > x) {
				panHorizontal(-5);
			}
			if (prevDragLoc.getY() < y)
				panVertical(-5);
			else if (prevDragLoc.getY() > y)
				panVertical(5);
		}
		prevDragLoc.setX(x);
		prevDragLoc.setY(y);
	}
}

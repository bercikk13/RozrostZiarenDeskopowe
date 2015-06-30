package com.rozrost;
import java.awt.AWTException;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;


public class Powiekszenie {

	public Powiekszenie() {
		
	}
	private Rectangle skad(Graphics g,int x , int y ,int bok, double razy) {
		Rectangle powiekszenie = new Rectangle(x,y,bok,bok);
		double a=powiekszenie.getCenterX();
		double b=powiekszenie.getCenterY();
		Rectangle re=new  Rectangle((int)(x-a), (int)(y-b), bok, bok);
		g.drawRect((int)(x-a), (int)(y-b), bok, bok);
		return re;
	}
	public void powieksz(Container canvas,Rectangle skadrect,Rectangle r,double ileRazy) {
		Robot robot;
		try {
			robot = new Robot();
			BufferedImage image=robot.createScreenCapture(skadrect);
			canvas.getGraphics().drawRect(skadrect.x, skadrect.y, (int)skadrect.getWidth(), (int)skadrect.getHeight());
	      	canvas.getGraphics().drawImage(image,	r.x	,	r.y	,	(int)r.getWidth()	,(int)r.getHeight()	, 	canvas	);
		} catch (AWTException e) {
			System.err.println(e);
		}
	}	
}

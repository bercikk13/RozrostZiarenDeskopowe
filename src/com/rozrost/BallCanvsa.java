package com.rozrost;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

class BallCanvas extends JPanel 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public void set(Ball b)
	{
      balls=b;
      
	}

   public void paintComponent(Graphics g)
   {
      super.paintComponent(g);
      Graphics2D g2 = (Graphics2D)g;
         if(balls!=null)balls.draw(g2);
   }
   private Ball balls = null ;
}
package com.rozrost;
import java.awt.AWTException;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

class Ball
{
	int tryb;
	static public int los=0;
	Component c;
	public final Color pusty=Color.white; 
	public boolean stan=true;
	static Ziarno[][] plansza;
//	private int wymiar=600;//BounceFrame.suwak.getValue();//100;
	private static Component canvas;
	Robot robot;
	private Color color;
	int algo;

	public Ball(Ziarno[][]plansza,Component c, int tryb,int ilosc) 
	{ 	

		this.c=c;
		this.tryb=tryb;
		try {
			robot = new Robot();
		} catch (AWTException e) {
		// TODO Auto-generated catch block
			e.printStackTrace();
		}
		canvas=c;
		this.plansza=plansza;
	}

   public void draw(Graphics2D g2)
   {

      for (int i = 0; i < plansza.length; i++) {
		for (int j = 0; j < plansza[i].length; j++) {
//			if(plansza[i][j].naGranicy){
//			
//				g2.setColor(Color.black);
//				g2.fill(new Rectangle2D.Double(i+30, j+30, 1, 1));
//			
//			}else{
					g2.setColor(plansza[i][j].color);
					g2.fill(new Rectangle2D.Double(i+30, j+30, 1, 1));
//				}
		}
		
	}
      BufferedImage image=robot.createScreenCapture(new Rectangle(BounceFrame.x+10, 
      			BounceFrame.y+30, BounceFrame.kwadrat, BounceFrame.kwadrat));
      	g2.drawImage(image, 550	, 0, (int)(BounceFrame.kwadrat*BounceFrame.powiekszenie)
      			, (int)(BounceFrame.kwadrat*BounceFrame.powiekszenie), canvas);
   }
   public void move() 
   {
	   int licznik=0;
	   ArrayList<Point> help = new ArrayList<Point>();
	   for(int i = 0; i < plansza.length; i	++)
		    for(int j = 0; j < plansza[i].length; j++) 
		    	if(plansza[i][j].color!=pusty){
		    		help.add(new Point(i,j));
		    	}else
		    	{
		    		licznik++;
		    	}
	   
	   if(licznik>0)
	   		stan=true;
	   	else
	   		stan=false;
	   	
//	   	Collections.shuffle(help);
	   		switch (tryb) {
					case 0:
						  for (Point point : help) 
							plansza=  Neumana((int)point.getX(),(int)point.getY(),BounceFrame.periodyka,false);
						  algo=tryb;
						break;
					case 1:
						for (Point point : help)
							plansza=Moora((int)point.getX(),(int)point.getY(),BounceFrame.periodyka,false);
						algo=tryb;
						break;
					case 2:
						for (Point point : help)
							plansza=rozrostHeksagonalne((int)point.getX(),(int)point.getY(),BounceFrame.periodyka,false);
						algo=tryb;
						break;
					case 3:
						for (Point point : help)
							plansza=rozrostPentagonalne((int)point.getX(),(int)point.getY(),BounceFrame.periodyka,false);
							algo=tryb;
						break;
					}
	 
	
      canvas.repaint();
   }   
   public Ziarno[][] rozrostOgolny(Integer xx,Integer yy,boolean tab[],boolean periodyka,boolean rekrystalizacja) {
 		int x=xx,y=yy;
 		if(los%50000==0&&BounceFrame.ciagleLosowanie.isSelected())
 			{
 				los++;
 				int a,b;
 				Random r = new Random();
 			a=r.nextInt(plansza.length);b=r.nextInt(plansza.length);
 				if(plansza[a][b].color	== pusty	)
 					plansza[a][b].color=new Color(r.nextInt(255),r.nextInt(255),r.nextInt(255));
 			}else
 				los++;
 		if(periodyka)
 		{
 			if(xx-1<0)
 				xx=plansza.length-1;
 			if(yy-1<0)
 				yy=plansza.length-1;
 			if(xx+1>plansza.length-1)
 				xx=-0;
 			if(yy+1>plansza.length-1)
 				yy=0;
 		}
 		
	   if(tab[0])
	   if((xx-1)>=0&&(yy-1)>=0)//lewy gorny
  		if(plansza[xx-1][yy-1].color==pusty||(plansza[xx-1][yy-1].zrekrystalizowana&&rekrystalizacja))
  		{
  			plansza[xx-1][yy-1].color=new Color(plansza[x][y].color.getRGB());
  			plansza[xx-1][yy-1].zrekrystalizowana=true;
  		}
	   
 		if(tab[1])
 		if((yy-1)>=0) //gorny
 	  		if(plansza[x][yy-1].color==pusty||(plansza[x][yy-1].zrekrystalizowana&&rekrystalizacja))
 	  		{
 	  			plansza[x][yy-1].color=new Color(plansza[x][y].color.getRGB());
 	  			plansza[x][yy-1].zrekrystalizowana=true;
 	  		}
 		if(tab[2])
 		if((xx+1)<plansza.length-1&&(yy-1)>=0)//prawy gorny
  	   		if(plansza[xx+1][yy-1].color==pusty||(plansza[xx+1][yy-1].zrekrystalizowana&&rekrystalizacja))
  	   		{
  	   			plansza[xx+1][yy-1].color=new Color(plansza[x][y].color.getRGB());		
  	   			plansza[xx+1][yy-1].zrekrystalizowana=true;
  	   		}
 		
  		
 		if(tab[3])
  		if((xx-1)>=0) //lewy
  	  		if(plansza[xx-1][y].color==pusty||(plansza[xx-1][y].zrekrystalizowana&&rekrystalizacja))
  	  		{
  	  			plansza[xx-1][y].color=new Color(plansza[x][y].color.getRGB());
  	  			plansza[xx-1][y].zrekrystalizowana=true;
  	  		}
 		if(tab[4])
  		if((xx+1)<=plansza.length-1)//prawy
  		if(plansza[xx+1][y].color==pusty||(plansza[xx+1][y].zrekrystalizowana&&rekrystalizacja))
  		{
  			plansza[xx+1][y].color=new Color(plansza[x][y].color.getRGB());		
  			plansza[xx+1][y].zrekrystalizowana=true;
  		}
  	
 		if(tab[5])
  		if((xx-1)>=0&&(yy+1)<=plansza.length-1) //lewy dolny
  	   		if(plansza[xx-1][yy+1].color==pusty||(plansza[xx-1][yy+1].zrekrystalizowana&&rekrystalizacja))
  	   		{

  	   			plansza[xx-1][yy+1].color=new Color(plansza[x][y].color.getRGB());
  	   			plansza[xx-1][yy+1].zrekrystalizowana=true;
  	   		}
 		if(tab[6])
  		if((yy+1)<=plansza.length-1) //dolny
  		if(plansza[x][yy+1].color==pusty||(plansza[x][yy+1].zrekrystalizowana&&rekrystalizacja))
  		{
  			plansza[x][yy+1].color=new Color(plansza[x][y].color.getRGB());
  			plansza[x][yy+1].zrekrystalizowana=true;
  		}
 		if(tab[7])
  		if((xx+1)<=plansza.length-1&&(yy+1)<=plansza.length-1)//prawy dolny
  	   		if(plansza[xx+1][yy+1].color==pusty||(plansza[xx+1][yy+1].zrekrystalizowana&&rekrystalizacja))
  	   		{
  	   			plansza[xx+1][yy+1].color=new Color(plansza[x][y].color.getRGB());		
  	   		plansza[xx+1][yy+1].zrekrystalizowana=true;
  	   		}
		return plansza;
  	
}
   public Ziarno[][] rozrostHeksagonalne(Integer x,Integer y,boolean periodyka,boolean rekrystalizacja) {
 		Random a = new Random();
 		int tryb=a.nextInt(4);
 		boolean []tab=null;
 		switch (tryb) {
		case 0:
			boolean help []={false,false,false,true,true,true,true,true};
			tab=help;
			break;
		case 1:
			boolean help1 []={false,true,true,false,true,false,true,true};
			tab=help1;
			break;
		case 2:
			boolean help3 []={true,true,false,true,false,true,true,false};
			tab=help3;
			break;
		case 3:
			boolean help2 []={true,true,true,true,true,false,false,false};
			tab=help2;
			break;
 		}
 		return rozrostOgolny(x, y, tab, periodyka,rekrystalizacja);
	}
   public Ziarno[][] rozrostPentagonalne(Integer x,Integer y,boolean periodyka,boolean rekrystalizacja) {
		Random a = new Random();
		int tryb=a.nextInt(2);
		boolean []tab = {false,true,true,true,true,true,true,false};
		boolean[] tab2 = {true,true,false,true,true,false,true,true};
		if(tryb==0)
			return rozrostOgolny(x, y, tab, periodyka,rekrystalizacja);
		else
			return rozrostOgolny(x, y, tab2, periodyka,rekrystalizacja);
	}
   public Ziarno[][] Moora(Integer x,Integer y, boolean periodyka,boolean rekrystalizacja) {
  		boolean tab[]={true,true,true,true,true,true,true,true};
	  return  rozrostOgolny(x, y, tab, periodyka,rekrystalizacja);
}
   public Ziarno[][] Neumana(Integer x,Integer y,boolean periodyka,boolean rekrystalizacja) {
	   boolean[] tab = {false,true,false,true,true,false,true,false};
	  return rozrostOgolny(x, y, tab, periodyka,rekrystalizacja);
		
   }
 
   public static Ziarno[][] getPlansza() {
	return plansza;
   }
   public static Component getCanvas() {
	return canvas;
   }
}
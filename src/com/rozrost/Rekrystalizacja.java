package com.rozrost;

import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;


public class Rekrystalizacja implements Runnable {
	double A=86710969050178.5,B=9.41268203527779,Rk=46842668.25,R=0.,Rp=0.,
	t_i=0.001;
	double mull_b=1.4,mull_nb=1.6,t=0.;
	Ziarno [][]plansza;
	int wymiar;
	Color pusty = Color.white;
	int tryb;
	int promien=10;
	File file = new File("file.txt");
	BufferedWriter bw;
	Component c;
	public Rekrystalizacja(Component c,Ziarno [] [] plansza,int tryb) {
		zapisDoPliku();
		this.c=c;
		this.plansza=plansza;
		for (Ziarno[] ziarnos : plansza) {
			for (Ziarno ziarno : ziarnos) {
				ziarno.zrekrystalizowana=false;
			}
		}
		this.tryb=tryb;
		wymiar=plansza.length;
	}
	
	
	private void zapisDoPliku() {
		 
		// if file doesnt exists, then create it
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		FileWriter fw = null;
		try {
			fw = new FileWriter(file.getAbsoluteFile());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bw = new BufferedWriter(fw);
		
		
	}
	public void iteracja(int iter) {
		// TODO Auto-generated method stub
			
			Rp=R=A/B+(double)(1-A/B)*Math.pow(Math.E,(-B*(t-t_i)));
			
		R=A/B+(double)(1-A/B)*Math.pow(Math.E,(-B*t));
		try {
			bw.write((""+t)+"\t"+(""+R)+"\t"+(
					1.9*Math.sqrt(R)*0.257*10*8)+"\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(t==0)
		granica();
		ArrayList<Ziarno> zrekrys = new ArrayList<Ziarno>();
		ArrayList<Ziarno> nieZrekrys = new ArrayList<Ziarno>();
		
		for (int i = 0; i < plansza.length; i++) 
			for (int j = 0; j < plansza.length; j++) 
				if(plansza[i][j].zrekrystalizowana)
					{
						plansza[i][j].point=new Point(i, j);
						zrekrys.add(plansza[i][j]);
					}
				else{
					plansza[i][j].point=new Point(i, j);
					nieZrekrys.add(plansza[i][j]);
				}
		
		
					for (Ziarno ziarno : zrekrys) {
						switch (tryb) {
						case 0:
							  
								plansza=  Neumana(ziarno.point.x,ziarno.point.y,BounceFrame.periodyka,true);
							break;
						case 1:
						
								plansza=Moora(ziarno.point.x,ziarno.point.y,BounceFrame.periodyka,true);
							break;
						case 2:
						
								plansza=rozrostHeksagonalne(ziarno.point.x,ziarno.point.y,BounceFrame.periodyka,true);
							break;
						case 3:
							
								plansza=rozrostPentagonalne(ziarno.point.x,ziarno.point.y,BounceFrame.periodyka,true);
							break;
						}
		 
						rozrostPentagonalne(ziarno.point.x, ziarno.point.y, BounceFrame.periodyka, true);
					}
					Random r = new Random();
					for (Ziarno ziarno : nieZrekrys) {
						
							R=(double)(R-Rp)/(double)(plansza.length*plansza.length);
							if(R<0)
								R*=-1;
							
							if(ziarno.naGranicy)
								ziarno.gestoscDyslokacji+=R*(double)(r.nextInt(40)+60/100.);
							else
								ziarno.gestoscDyslokacji+=R*(double)(r.nextInt(40)/100.);
							if(ziarno.gestoscDyslokacji>Rk)
								{
									//promien dorobic
								
								
									
									ziarno.color = new Color(r.nextInt(255),r.nextInt(255),r.nextInt(255));
									ziarno.zrekrystalizowana=true;
									if(BounceFrame.rekrystalizacjaZPromieniem.isSelected())
									for (int k = ziarno.point.x-Integer.parseInt(BounceFrame.fieldProemien.getText()); k <=
												ziarno.point.x+Integer.parseInt(BounceFrame.fieldProemien.getText()); k++)
											for (int k2 = ziarno.point.y-Integer.parseInt(BounceFrame.fieldProemien.getText()); k2 <=
													ziarno.point.y+Integer.parseInt(BounceFrame.fieldProemien.getText()); k2++) {
												if(k>=0&&k2>=0&&k<plansza.length&&k2<plansza.length)
												{
													
													plansza[k][k2].gestoscDyslokacji=0;
													plansza[k][k2].naGranicy=false;
												}
											}
									
								}
					}
	
		t+=t_i;
	}

	  private void granica() {
			// TODO Auto-generated method stub
			  for (int i =0; i < plansza.length; i++) {
					for (int j = 0; j < plansza[i].length; j++) {
						if(j+1<plansza.length)
						if(plansza[i][j].color.getRGB()!=plansza[i][j+1].color.getRGB())
							plansza[i][j].naGranicy=true;
						if(i+1<plansza.length&&j+1<plansza.length)
						if(plansza[i][j].color.getRGB()!=plansza[i+1][j+1].color.getRGB())
							plansza[i][j].naGranicy=true;
						if(i+1<plansza.length)
						if(plansza[i][j].color.getRGB()!=plansza[i+1][j].color.getRGB())
							plansza[i][j].naGranicy=true;
					}
			  }
		}
	
	private boolean sprawdzSasiada(int x,int y) {
		int xx,yy;
		for (int i = x-1; i <= x+1; i++) {
			for (int j = y-1; j <= y+1; j++) {
			if(i!=x&&j!=y)
			{
				xx=i;yy=j;
				if(xx<0)
	 				xx=plansza.length-1;
	 			if(yy<0)
	 				yy=plansza.length-1;
	 			if(xx>plansza.length-1)
	 				xx=0;
	 			if(yy>plansza.length-1)
	 				yy=0;
	 			if(plansza[xx][yy].zrekrystalizowana==true)
	 			{	
	 				plansza[x][y].zrekrystalizowana=true;
	 				return true;
	 			}
			}
		  }
		}
		return false;
	}
	 public Ziarno[][] rozrostOgolny(Integer xx,Integer yy,boolean tab[],boolean periodyka,boolean rekrystalizacja) {
	 		int x=xx,y=yy;
	 		
	 		if(periodyka)
	 		{
	 			if(xx-1<0)
	 				xx=wymiar-1;
	 			if(yy-1<0)
	 				yy=wymiar-1;
	 			if(xx+1>wymiar-1)
	 				xx=-0;
	 			if(yy+1>wymiar-1)
	 				yy=0;
	 		}
	 		
		   if(tab[0])
		   if((xx-1)>=0&&(yy-1)>=0)//lewy gorny
	  		if(plansza[xx-1][yy-1].color==pusty||(!plansza[xx-1][yy-1].zrekrystalizowana&&rekrystalizacja))
	  		{
	  			plansza[xx-1][yy-1].color=new Color(plansza[x][y].color.getRGB());
	  			plansza[xx-1][yy-1].zrekrystalizowana=true;
	  		}
		   
	 		if(tab[1])
	 		if((yy-1)>=0) //gorny
	 	  		if(plansza[x][yy-1].color==pusty||(!plansza[x][yy-1].zrekrystalizowana&&rekrystalizacja))
	 	  		{
	 	  			plansza[x][yy-1].color=new Color(plansza[x][y].color.getRGB());
	 	  			plansza[x][yy-1].zrekrystalizowana=true;
	 	  		}
	 		if(tab[2])
	 		if((xx+1)<=wymiar-1&&(yy-1)>=0)//prawy gorny
	  	   		if(plansza[xx+1][yy-1].color==pusty||(!plansza[xx+1][yy-1].zrekrystalizowana&&rekrystalizacja))
	  	   		{
	  	   			plansza[xx+1][yy-1].color=new Color(plansza[x][y].color.getRGB());		
	  	   			plansza[xx+1][yy-1].zrekrystalizowana=true;
	  	   		}
	 		
	  		
	 		if(tab[3])
	  		if((xx-1)>=0) //lewy
	  	  		if(plansza[xx-1][y].color==pusty||(!plansza[xx-1][y].zrekrystalizowana&&rekrystalizacja))
	  	  		{
	  	  			plansza[xx-1][y].color=new Color(plansza[x][y].color.getRGB());
	  	  			plansza[xx-1][y].zrekrystalizowana=true;
	  	  		}
	 		if(tab[4])
	  		if((xx+1)<=wymiar-1)//prawy
	  		if(plansza[xx+1][y].color==pusty||(!plansza[xx+1][y].zrekrystalizowana&&rekrystalizacja))
	  		{
	  			plansza[xx+1][y].color=new Color(plansza[x][y].color.getRGB());		
	  			plansza[xx+1][y].zrekrystalizowana=true;
	  		}
	  	
	 		if(tab[5])
	  		if((xx-1)>=0&&(yy+1)<=wymiar-1) //lewy dolny
	  	   		if(plansza[xx-1][yy+1].color==pusty||(!plansza[xx-1][yy+1].zrekrystalizowana&&rekrystalizacja))
	  	   		{

	  	   			plansza[xx-1][yy+1].color=new Color(plansza[x][y].color.getRGB());
	  	   			plansza[xx-1][yy+1].zrekrystalizowana=true;
	  	   		}
	 		if(tab[6])
	  		if((yy+1)<=wymiar-1) //dolny
	  		if(plansza[x][yy+1].color==pusty||(!plansza[x][yy+1].zrekrystalizowana&&rekrystalizacja))
	  		{
	  			plansza[x][yy+1].color=new Color(plansza[x][y].color.getRGB());
	  			plansza[x][yy+1].zrekrystalizowana=true;
	  		}
	 		if(tab[7])
	  		if((xx+1)<=wymiar-1&&(yy+1)<=wymiar-1)//prawy dolny
	  	   		if(plansza[xx+1][yy+1].color==pusty||(!plansza[xx+1][yy+1].zrekrystalizowana&&rekrystalizacja))
	  	   		{
	  	   			plansza[xx+1][yy+1].color=new Color(plansza[x][y].color.getRGB());		
	  	   		plansza[xx+1][yy+1].zrekrystalizowana=true;
	  	   		}
			return plansza;
	  	
	}
	 public Ziarno[][] Moora(Integer x,Integer y, boolean periodyka,boolean rekrystalizacja) {
	  		boolean tab[]={true,true,true,true,true,true,true,true};
		  return  rozrostOgolny(x, y, tab, periodyka,rekrystalizacja);
	}
	   public Ziarno[][] Neumana(Integer x,Integer y,boolean periodyka,boolean rekrystalizacja) {
		   boolean[] tab = {false,true,false,true,true,false,true,false};
		  return rozrostOgolny(x, y, tab, periodyka,rekrystalizacja);
			
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
	public double getR() {
		return R;
	}
	public BufferedWriter getBw() {
		return bw;
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub
		for (int i = 0; i < 50; i++) {
			iteracja(i);
			c.repaint();
			try {
				Thread.sleep(400);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

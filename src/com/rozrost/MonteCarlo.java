package com.rozrost;

import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;

public class MonteCarlo implements Runnable{
	Ziarno [][]plansza;
	Component component;
	ArrayList<Ziarno> przelot;//slozyc bedzie do mieszania i losowego wybierania kazdego ziarna
	
	public MonteCarlo(Ziarno[][]plansza,Component component) {
		this.plansza=plansza;
		this.component=component;
		przelot = new ArrayList<Ziarno>();
		for (int i = 0; i < plansza.length; i++) {
			for (int j = 0; j < plansza.length; j++) {
				plansza[i][j].point.setLocation(i, j);
				przelot.add(plansza[i][j]);
			}
		}
	}
	private int energia(Color symulacja,int x , int y) {
		int licznik=0;
		for (int i = x-1; i <= x+1; i++) {
			for (int j = y-1; j <= y+1; j++) {
				int xx=i,yy=j;
				if(xx==x&&yy==y)
					continue;
				if(xx==-1)
	 				xx=plansza.length-1;
	 			if(yy==-1)
	 				yy=plansza.length-1;
	 			if(xx==plansza.length)
	 				xx=0;
	 			if(yy==plansza.length)
	 				yy=0;
	 			
					if(symulacja==null)	//bez symulacji
					{
						if(plansza[xx][yy].color.getRGB()!=plansza[x][y].color.getRGB())
							licznik++;
					}
					else				//symulacja
						if(plansza[xx][yy].color.getRGB()!=symulacja.getRGB())
							licznik++;
			}
		}		
		return licznik;
	}
	
	private int wybierzMniejszaEnergie(int x , int y) {
		ArrayList<Ziarno> help = new ArrayList<Ziarno>();
		for (int i = x-1; i <= x+1; i++) {
			for (int j = y-1; j <= y+1; j++) {
				int xx=i,yy=j;

 			if(xx==-1)
 				xx=plansza.length-1;
 			if(yy==-1)
 				yy=plansza.length-1;
 			if(xx==plansza.length)
 				xx=0;
 			if(yy==plansza.length)
 				yy=0;
				if(!(xx==x&&yy==y))
					help.add(plansza[xx][yy]);//dodanie do help dookola komorek + periodycznosc
				}
			}
		Collections.shuffle(help);
		for (Ziarno ziarno : help) {
//			System.out.println(energia(null, x, y)+"<--- energia badanego elementu | energia dookola --->"+energia(ziarno.color, x, y));
					if(energia(null, x, y)>=energia(ziarno.color, x, y)){
					if(plansza[x][y].color.getRGB()!=ziarno.color.getRGB())
					{
			//jesli mniejsza  lub rowna jest energia symulacji 
//					od badanej podstawiamy stan symulacji
					plansza[x][y].color=new Color(ziarno.color.getRGB());
					return 1;
				}
			}
		}
		return 0;
	}
	
	boolean iteracja() {
		Collections.shuffle(przelot);
//		granica();
		int licznik=0;
		for (Ziarno ziarno: przelot) {
//			if(ziarno.naGranicy)
				//return 1 jesli zmieniono stan 0 jesli nie
			licznik+=wybierzMniejszaEnergie(ziarno.point.x, ziarno.point.y);
		}
		if(licznik>0)//warunki perwania watku jesli licznik >0 to zostala podmieniona jakas komorka
			return true;
		else return false;
	}
	
	@Override
	public void run() {
		while(iteracja()){
			
			try {
			Thread.sleep(BounceFrame.suwak.getValue());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		component.repaint();
		}
		component.repaint();
	}
	
	private void granica() {
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
}

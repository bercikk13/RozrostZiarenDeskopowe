package com.rozrost;
import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.util.Random;

import javax.print.attribute.standard.Sides;


public class LosowanieZarodkow {
	Ziarno [][]plansza;
//	Component c;
	public LosowanieZarodkow(Component c ,Ziarno[][]plansza,int wymiar) {
//		 		this.c=c;
		 		this.plansza=plansza;
		 				
	}
	public Ziarno[][] wypelnij (Ziarno [][]plansza,int wymiar)
	{

		plansza= new Ziarno[wymiar][wymiar];
		   for (int i = 0; i < wymiar; i++) 
				for (int j = 0; j < wymiar; j++) 
				{
					
					plansza[i][j]= new Ziarno(Color.white, new Point(i,j));
				}
		   this.plansza=plansza;
		   return this.plansza;
	}
	public void losowo(Component c,int ilosc) {
		 Random r = new Random();
		   wyczyscPlansze(c);
		   for (int i = 0; i < ilosc; i++) {
			   plansza[r.nextInt(plansza.length-1)][r.nextInt(plansza.length-1)].color=
					   new Color(r.nextInt(255),r.nextInt(255),r.nextInt(255));
			   c.repaint();
		}
	}
	public int odlegloscPunktuOdZarodka(int x , int y, int a, int b) {
		return (int) Math.sqrt(Math.pow((x-a),2)+Math.pow((y-b),2));
	}
	public void rownomiernie(Component c,int size,int ilosc) {
		wyczyscPlansze(c);
//		this.c=c;
//		plansza=wypelnij(plansza, size);
		Random r = new Random();
		int odstep=(int) (size/Math.sqrt(ilosc));
		for (int i = odstep/2; i < size; i+=odstep) {
			for (int j = odstep/2; j < size;j+= odstep) {
				plansza[i][j].color=					   new Color(r.nextInt(255),r.nextInt(255),r.nextInt(255));
				   c.repaint();

			}
		}
	}
	
	private boolean sprawdzOkolice(int x, int y,int size,int promien) {
		for (int i = x-promien; i < x+promien; i++) {
			for (int j = y-promien; j < y+promien; j++) {
				if(i<0)continue;
				if(j<0)continue;
				if(i>size-1)continue;
				if(j>size-1)continue;
				if(plansza[i][j].color!=Color.white)return false;
			}
		}
		return true;
	}
	public void zPromieniem(Component c,Ziarno[][] plansza,int size,int promien,int ilosc) {
//		plansza=wypelnij(plansza,size);
		wyczyscPlansze(c);
		Random r = new Random();
		for (int i = 0; i < ilosc; i++) {
			int a,b;
			for(int j = 0 ; j <1000;j++)
			{
				
				a=r.nextInt(plansza.length);b=r.nextInt(plansza[a].length);
				if(sprawdzOkolice(a,b,size,promien)){
					plansza[a][b].color=new Color(r.nextInt(255),r.nextInt(255),r.nextInt(255));
					break;
				}
			}
		}
		c.repaint();
	}
	
	public void przezKlikniecie(Component c,int size,int ilosc) {
		
	}
	
	public Ziarno[][] wyczyscPlansze(Component c) {
		for (int k = 0; k < plansza.length; k++) 
			for (int l = 0; l < plansza[k].length; l++) 
			plansza[k][l].color=Color.white;
		c.repaint();
	Ball.plansza=plansza;
		return plansza;
	}
	
}

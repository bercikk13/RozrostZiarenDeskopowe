package com.rozrost;
import java.awt.Color;
import java.awt.Point;
import java.io.IOException;


public class Ziarno {
	public Color color;
	public Point point;
	public double gestoscDyslokacji=0;
	
	public boolean naGranicy=false;
	public boolean zrekrystalizowana=false;
	public Ziarno(Color c, Point p) {
		point=p;
		color=c;
	}
	public Ziarno (Ziarno z)
	{
		this.color=z.color;
		point=z.point;
		gestoscDyslokacji=z.gestoscDyslokacji;
		naGranicy=z.naGranicy;
		zrekrystalizowana=z.zrekrystalizowana;
	}
}
/*
 
 							try {
								bw.write(t+"\t"+R+"\t"+(
										1.9*Math.sqrt(R)*0.257*10*8)+"\n");
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
*/
package com.rozrost;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;


public class BounceFrame extends JFrame {
	private int wymiar=50;
	
	private JFrame fram = this;
	static int x,y;
	static int kwadrat =100;
	static double powiekszenie =7;
	public static boolean periodyka;  
	public static final int WIDTH = 1200;
	public static final int HEIGHT = 650;
   JTextField field;
   private BallCanvas canvas;
   LosowanieZarodkow losowanie;
   Ziarno plansza [][];
   Boolean losowanieCiagle = false;
   static public int speed = 1;
   static public JSlider suwak  ;
   static public JCheckBox ciagleLosowanie;
   int algo;
   Rekrystalizacja rek;
   static JCheckBox rekrystalizacjaZPromieniem  = new JCheckBox("Z promieniem");
   MouseListener mouseListener= new MouseListener() {
	
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mousePressed(MouseEvent arg0) {
		
		Random r = new Random();
		if(arg0.getButton()==1)
		{
			if(arg0.getXOnScreen()+30<wymiar&&arg0.getYOnScreen()<wymiar)
				plansza[arg0.getXOnScreen()-30][arg0.getYOnScreen()-60].color=new Color(r.nextInt(255),r.nextInt(255),r.nextInt(255));
		}else
		{
			x=arg0.getXOnScreen()-30;
			y=arg0.getYOnScreen()-30;
		}
		canvas.repaint();
	}
	
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
};
static public JTextField fieldProemien;
	public BounceFrame()
   {
		addMouseListener(mouseListener);
	       suwak= new JSlider(0, 800);
	       suwak.setMinorTickSpacing(speed);
//	       wymiar=suwak.getValue();
	       
//		losowanie= new LosowanieZarodkow(plansza, wymiar);
		
//		 this.repaint();
      setSize(WIDTH, HEIGHT);
      setTitle("Rozrost Ziaren");
     
      final Container contentPane = getContentPane();
      canvas = new BallCanvas();
      contentPane.add(canvas, BorderLayout.CENTER);
      final JPanel buttonPanel = new JPanel();
       field = new JTextField(5);
      field.setText("500");
      buttonPanel.add(field);
      final JCheckBox checkBox = new JCheckBox("Periodyka");
      String [] w = {"losowo","promien","rownomiernie"};
      final JComboBox<String> b = new JComboBox<>(w);
      fieldProemien=new JTextField("5",4);
      final LosowanieZarodkow losowanie = new LosowanieZarodkow(contentPane,plansza,wymiar);
		 plansza=losowanie.wypelnij(plansza, wymiar);
      buttonPanel.add(fieldProemien);
      
      buttonPanel.add(b);
      buttonPanel .add(rekrystalizacjaZPromieniem);
      ciagleLosowanie= new JCheckBox("Ciagle losowanie");
      buttonPanel.setLayout(new GridLayout( 2,7));
      buttonPanel.add(ciagleLosowanie);
      
       suwak= new JSlider(0, 800);
      suwak.setMinorTickSpacing(speed);
      wymiar=100;//suwak.getValue();
      
      buttonPanel.add(suwak);
      
      checkBox.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(checkBox.isSelected())
				periodyka=true;
			else
				periodyka = false;
			System.out.println(periodyka);
		}
		
	});
      
      buttonPanel.add(checkBox);
      addButton(buttonPanel, "Losuj", new ActionListener() {
  		
  		@Override
  		public void actionPerformed(ActionEvent arg0) {
  			
  			switch (b.getSelectedIndex()) {
			case 0:
				losowanie.losowo(contentPane, Integer.parseInt(field.getText()));
				break;
			case 1:
				losowanie.zPromieniem(contentPane,plansza, wymiar, Integer.parseInt(fieldProemien.getText()), Integer.parseInt(field.getText()));
				break;
			case 2:
				losowanie.rownomiernie(contentPane, wymiar, Integer.parseInt(field.getText()));
				break;
			case 3:
				losowanie.losowo(contentPane, Integer.parseInt(field.getText()));
				break;
			}
  		}
  	});
      addButton(buttonPanel, "Czysc", new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			plansza=losowanie.wypelnij(plansza, wymiar);
			plansza=losowanie.wyczyscPlansze(contentPane);
			contentPane.repaint();
		}
	});
      		
      addButton(buttonPanel, "Neuman",
         new ActionListener()
            {  
               public void actionPerformed(ActionEvent evt)
               {
                  addBall(Thread.NORM_PRIORITY,0);
                  setTitle("Rozrost Ziaren Neumana");
                  fram.setFocusable(true);
                  fram.setAutoRequestFocus(true);
                  algo=0;
               }
            });
      addButton(buttonPanel, "Moor",
    	         new ActionListener()
    	            {  
    	               public void actionPerformed(ActionEvent evt)
    	               {
    	                  addBall(Thread.NORM_PRIORITY,1);
    	                  setTitle("Rozrost Ziaren Moore");
    	                  fram.setFocusable(true);
    	                  algo=1;
    	               }
    	            });
      addButton(buttonPanel, "heksagonalne",
         new ActionListener()
            {  
               public void actionPerformed(ActionEvent evt)
               {
                  addBall(Thread.NORM_PRIORITY + 4,2);
                  setTitle("Rozrost Ziaren Heksagonalne");
                  fram.setFocusable(true);
                  for (Component c : fram.getComponents()) {
					c.setFocusable(false);
				}
                  algo=2;
               }
            });
      addButton(buttonPanel, "pentagonalne",
    	         new ActionListener()
    	            {  
    	               public void actionPerformed(ActionEvent evt)
    	               {
    	                  addBall(Thread.NORM_PRIORITY + 4,3);
    	                  setTitle("Rozrost Ziaren Pentagonalne");
    	                  algo=3;
    	               }
    	            
    	            });

      addButton(buttonPanel, "Close",
         new ActionListener()
            {
               public void actionPerformed(ActionEvent evt)
               {
                  System.exit(0);
               }
            });
      addButton(buttonPanel, "wyczyscGranice",
    	         new ActionListener()
    	            {
    	               public void actionPerformed(ActionEvent evt)
    	               {
    	            	   for (int i = 0; i < plansza.length; i++) {
							for (int j = 0; j < plansza.length; j++) {
								plansza[i][j].naGranicy=false;
								plansza[i][j].zrekrystalizowana=false;
								plansza[i][j].gestoscDyslokacji=0;
							}
						}
    	                  canvas.repaint();
    	                  contentPane.repaint();
    	               }
    	            });
      addButton(buttonPanel, "Repaint",
    	         new ActionListener()
    	            {
    	               public void actionPerformed(ActionEvent evt)
    	               {
    	            	   	repaint();
    	            	   	rek = new Rekrystalizacja(canvas, plansza,algo);
    	            	   }
    	            });
      
      addButton(buttonPanel, "Rekrystalizacja",
    	         new ActionListener()
    	            {
    	               public void actionPerformed(ActionEvent evt)
    	               {
    	            	   
//    	            	   for (int i = 0; i < 200; i++) {
    	            	   
							new Thread(rek).start();
							repaint();
//						}
    	               }
    	            });
      addButton(buttonPanel, "MonteCarlo",
    	         new ActionListener()
    	            {
    	               public void actionPerformed(ActionEvent evt)
    	               {
    	                  MonteCarlo monteCarlo  = new MonteCarlo(plansza,canvas);
    	                  new Thread(monteCarlo).start();
						
    	               }
    	            });
      contentPane.add(buttonPanel, BorderLayout.SOUTH);
   }
	
   public void addButton(Container c, String title,
      ActionListener listener)
   {
      JButton button = new JButton(title);
    
      c.add(button);
      button.addActionListener(listener);
   }
   
   public void addBall(int priority,int tryb)
   {
      Ball b = new Ball(plansza,canvas,tryb,Integer.parseInt(field.getText()));
      canvas.set(b);
      BallThread thread = new BallThread(b);
      thread.setPriority(priority);
      thread.start();
   }
}




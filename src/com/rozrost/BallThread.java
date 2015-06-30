package com.rozrost;
class BallThread extends Thread
{
   /**
      Constructs the thread.
      @aBall the ball to bounce
   */
   public BallThread(Ball aBall) { b = aBall; }

   public void run()
   {
      try
      {
//         for (int i = 1; i <= 100; i++)
         while(b.stan){
            b.move();
            sleep(BounceFrame.suwak.getValue());
         }
      }
      catch (InterruptedException exception)
      {
    	  
      }
   }

   private Ball b;
}


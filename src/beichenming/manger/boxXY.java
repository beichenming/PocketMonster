package beichenming.manger;

import com.badlogic.gdx.physics.box2d.Body;

public class boxXY {
	
   public int boxX;
   public int boxY;
   public boolean haveBody;
   public Body boxBody;
   
   public boxXY()
   {
	   
   }
   public boxXY(int x,int y)
   {
	   boxX = x;
	   boxY = y;
   }
}

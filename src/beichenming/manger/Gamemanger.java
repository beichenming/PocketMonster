package beichenming.manger;

import org.andengine.engine.handler.physics.PhysicsHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.PathModifier;
import org.andengine.entity.modifier.PathModifier.Path;
import org.andengine.entity.modifier.PathModifier.IPathModifierListener;

import android.util.Log;

public class Gamemanger {
	
    public static Sourcemanger gameSourceManager = Sourcemanger.getInstance();
	public static Gamemanger INSTANCE;
	public static int nowScence = 0;
	
	
	public static float mapHeight;
	public static float mapWidth;
	private int direction = 0;                     //�ƶ�����
	
	
	public synchronized static Gamemanger getInstance()
	{
		if(INSTANCE == null){
			INSTANCE = new Gamemanger();
		}
		return INSTANCE;
	}
	
	
	//�����ƶ�����
		public void setHeroMove_control(float arg1,float arg2,long SPEED)
		{
			if (arg2 == 1) {
				// Down
				GameStatus.heroMoveing = true;              //���ô�ʱ������������
				if (direction != 1) {
					gameSourceManager.hero_oneAnimatedSprite.animate(new long[] { SPEED, SPEED, SPEED ,SPEED}, 0,3, true);
					direction = 1;
				}

			} else if (arg2 == -1) {
				// Up
				GameStatus.heroMoveing = true;              //���ô�ʱ������������
				if (direction != 2) {
					gameSourceManager.hero_oneAnimatedSprite.animate(new long[] { SPEED, SPEED, SPEED ,SPEED}, 12,15, true);
					direction = 2;
				}
			} else if (arg1 == -1) {
				// Left
				GameStatus.heroMoveing = true;              //���ô�ʱ������������
				if (direction != 3) {
					gameSourceManager.hero_oneAnimatedSprite.animate(new long[] { SPEED, SPEED, SPEED,SPEED }, 4,7, true);
					direction = 3;
				}
			} else if (arg1 == 1) {
				// Right
				GameStatus.heroMoveing = true;              //���ô�ʱ������������
				if (direction != 4) {
					gameSourceManager.hero_oneAnimatedSprite.animate(new long[] { SPEED, SPEED, SPEED ,SPEED}, 8,11, true);
					direction = 4;
				}
			} else {
				 GameStatus.heroMoveing = false;                                   //���ô�ʱ����������ֹͣ
				if (gameSourceManager.hero_oneAnimatedSprite.isAnimationRunning()) {
					gameSourceManager.hero_oneAnimatedSprite.stopAnimation();
					// direction = 0;
					switch (direction) {
					case 1:
						gameSourceManager.hero_oneAnimatedSprite.setCurrentTileIndex(0);
						break;

                    case 2:
                    	gameSourceManager.hero_oneAnimatedSprite.setCurrentTileIndex(12);
						break;
						
                    case 3:
                    	gameSourceManager.hero_oneAnimatedSprite.setCurrentTileIndex(4);
	                    break;
	                    
                    case 4:
                    	gameSourceManager.hero_oneAnimatedSprite.setCurrentTileIndex(8);
	                    break;
					}
				}
			}
		}
		
       //���������Եķ���(1:Down,4:Up,2:Left 3:Right)
		public int getHerodirection()
		{
			int direction = gameSourceManager.hero_oneAnimatedSprite.getCurrentTileIndex();
			switch (direction) {
			case 0:
				
				return 1;

           case 4:
				
				return 2;
           case 8:
	
	            return 3;

           default:
	
	           return 4;
			}
		}
	  
	
}

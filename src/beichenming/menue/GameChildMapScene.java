package beichenming.menue;

import org.andengine.engine.camera.SmoothCamera;
import org.andengine.entity.Entity;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.LoopEntityModifier.ILoopEntityModifierListener;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;
import org.andengine.entity.sprite.Sprite;
import org.andengine.ui.IGameInterface;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.LoopModifier;

import android.opengl.GLES20;
import beichenming.manger.Gamemanger;
import beichenming.manger.LoadMapManger;
import beichenming.manger.Sourcemanger;
import beichenming.pocketmonster.LoadGame;

public class GameChildMapScene extends MyScene{
	//定义屏幕高度和宽度
		private static final int CAMERA_WIDTH = 480;
		private static final int CAMERA_HEIGHT = 320;
		public static Sourcemanger gameSourceManager = Sourcemanger.getInstance();
	    private static Gamemanger gamemanger = Gamemanger.getInstance();
		protected static final int LAYER_BGROUND = 0;                     //背景图层one
		protected static final int LAYER_MENU = LAYER_BGROUND + 1;        //背景图层two
		private static final int LAYER_MAP = LAYER_MENU + 1;              //背景图层three
	     
		protected GameChildMapScene bcmScene;
		protected SmoothCamera bcmSmoothCamera;
		protected GameLogo bcmGameLogo;
		
		private LoadGame loadGame;
		private int mapIndexNum = 0;                                   // 地图索引序号
		private int mapIndexMax;                                       // 地图数目
		private int mapType,mapIndex;                                  //地图索引和地图数量 
		
	    private Sprite sprite_one;
	    private Sprite sprite_two;
	    private Sprite sprite_three;
	    private Sprite sprite_four;
	     
		
	public GameChildMapScene(GameLogo activity,int maptype) {
		super(activity);
		// TODO Auto-generated constructor stub
		this.bcmScene = this;
		this.bcmSmoothCamera = activity.myCamera;
		this.bcmGameLogo = activity;
		this.mapType = maptype;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		//初始化载入资源
		switch(this.mapType){
		case 0:
           gameSourceManager.loadGameGB_fourTexture_one(engine, context);
           gameSourceManager.loadGameGB_fourSprite_one(engine, context, bcmSmoothCamera);
           this.mapIndexMax = 6;
           break;
		case 1:
		   gameSourceManager.loadGameGB_fourTexture_two(engine, context);
	       gameSourceManager.loadGameGB_fourSprite_two(engine, context, bcmSmoothCamera);
	       this.mapIndexMax = 5;
	       break;
		case 2:
			   gameSourceManager.loadGameGB_fourTexture_three(engine, context);
		       gameSourceManager.loadGameGB_fourSprite_three(engine, context, bcmSmoothCamera);
		       this.mapIndexMax = 4;
		       break;
		case 3:
			   gameSourceManager.loadGameGB_fourTexture_four(engine, context);
		       gameSourceManager.loadGameGB_fourSprite_four(engine, context, bcmSmoothCamera);
		       this.mapIndexMax = 6;
		       break;
		case 4:
			   gameSourceManager.loadGameGB_fourTexture_five(engine, context);
		       gameSourceManager.loadGameGB_fourSprite_five(engine, context, bcmSmoothCamera);
		       this.mapIndexMax = 6;
		       break;
		case 5:
			   gameSourceManager.loadGameGB_fourTexture_six(engine, context);
		       gameSourceManager.loadGameGB_fourSprite_six(engine, context, bcmSmoothCamera);
		       this.mapIndexMax = 6;
		       break;
		case 6:
			   gameSourceManager.loadGameGB_fourTexture_seven(engine, context);
		       gameSourceManager.loadGameGB_fourSprite_seven(engine, context, bcmSmoothCamera);
		       this.mapIndexMax = 6;
		       break;
		case 7:
			   gameSourceManager.loadGameGB_fourTexture_eight(engine, context);
		       gameSourceManager.loadGameGB_fourSprite_eight(engine, context, bcmSmoothCamera);
		       this.mapIndexMax = 3;
		       break;
    
		}
		loadGame = new LoadGame(engine,context);
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub
		this.getChildByIndex(LAYER_BGROUND).attachChild(gameSourceManager.mapShowBgSprite);
		for(int i = 0 ;i < mapIndexMax ;i ++){
			this.getChildByIndex(LAYER_MENU).attachChild(gameSourceManager.childreButtonSprites[i]);
		}
		
	    this.gameSourceManager.childreButtonSprites[0].setX(CAMERA_WIDTH/2 - this.gameSourceManager.childreButtonSprites[0].getWidth()/2);
		this.gameSourceManager.childreButtonSprites[1].setX(CAMERA_WIDTH - this.gameSourceManager.childreButtonSprites[1].getWidth()/3);
		this.gameSourceManager.childreButtonSprites[1].setAlpha(0.5f);
		this.initRegisterEntityModifier();
		this.initSetBtEvent();
	}

	
	@Override
	public void logic(float SecondsElapsed) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void eventDown() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void eventMove() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void eventUp() {
		// TODO Auto-generated method stub
		
	}
	
	
	//按键注册监听
	private void initRegisterEntityModifier()
	{
		for(int i = 0 ;i < mapIndexMax ;i ++){
		  this.registerTouchArea(gameSourceManager.childreButtonSprites[i]);
		}
		
		
		
	}
	
	
	
	
	
	
	
	//按键触发事件
	private void initSetBtEvent()
	{
	     for(int i = 0 ;i < mapIndexMax ; i ++){
	    	 this.gameSourceManager.childreButtonSprites[i].setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(ButtonSprite arg0, float arg1, float arg2) {
					// TODO Auto-generated method stub
					for(int i = 0 ; i < mapIndexMax  ;i ++){
						if(arg0 == gameSourceManager.childreButtonSprites[i]){
							mapIndex = i;
						}
					}
					
			        if((int)arg0.getX() == (int)(CAMERA_WIDTH/2 - arg0.getWidth()/2)){
			        	bcmGameLogo.runOnUiThread(new Runnable() {
							@Override
							public void run() {
								gameSourceManager.touchSound.play();
								GameScene gameScene = new GameScene(activity,mapType,mapIndex);             //载入初始化的第一张地图 
								bcmScene.setChildScene(gameScene,false,true,true);
								loadGame.loadGame(gameScene);
							}
						});
			                                        
			        	return ;
			        }
			       if((int)arg0.getX() == (int)(CAMERA_WIDTH - arg0.getWidth()/3)){
			    	   setMapAnimateRight();
			    	     mapIndexNum++;
			    	   return ;
			       }
			        
			      if((int)arg0.getX() == (int)(-arg0.getWidth()*2/3)){
			    	    setMapAnimateLeft();
			    	    mapIndexNum--;
			    	  return ;
			      }
				}
			});
	     }
		
	}
	
	
	//map左移动画
	private void setMapAnimateLeft(){
	   this.sprite_one = null;
	   this.sprite_two = null;
       this.sprite_three = null;
	   this.sprite_four = null;
	     
	      if(mapIndexNum > 1){
	    	  sprite_one = this.gameSourceManager.childreButtonSprites[mapIndexNum - 2];
	      }
	      if(mapIndexNum > 0){
		    sprite_two = this.gameSourceManager.childreButtonSprites[mapIndexNum - 1];
	      }
	      sprite_three = this.gameSourceManager.childreButtonSprites[mapIndexNum];
	      
	     if(mapIndexNum + 1 < mapIndexMax){
	    	 sprite_four = this.gameSourceManager.childreButtonSprites[mapIndexNum + 1];
	     }
	      
	     if(sprite_two != null){
		   LoopEntityModifier entityModifier = new LoopEntityModifier(new IEntityModifierListener() {
				
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
					// TODO Auto-generated method stub
					 
					sprite_three.registerEntityModifier(new SequenceEntityModifier(
							  new ParallelEntityModifier(
									new MoveXModifier(0.1f, (CAMERA_WIDTH/2 - sprite_three.getWidth()/2),CAMERA_WIDTH - sprite_three.getWidth()/3),
                                   new AlphaModifier(0.5f, 1.0f,0.5f)
	    			)));
					
					if(sprite_one != null){
						sprite_one.registerEntityModifier(new SequenceEntityModifier(
								  new ParallelEntityModifier(
										new MoveXModifier(0.1f, - sprite_one.getWidth(),- sprite_three.getWidth()*2/3),
	                                   new AlphaModifier(0.5f, 0.0f,0.5f)
		    			)));
					}
					
					if(sprite_four != null){
						sprite_four.registerEntityModifier(new SequenceEntityModifier(
								  new ParallelEntityModifier(
										new MoveXModifier(0.1f, (CAMERA_WIDTH - sprite_four.getWidth()/3),CAMERA_WIDTH)
		    			)));
					}
					
				}
				
				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					// TODO Auto-generated method stub

				}
			}, 1, new ILoopEntityModifierListener() {
				
				@Override
				public void onLoopStarted(LoopModifier<IEntity> arg0, int arg1, int arg2) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onLoopFinished(LoopModifier<IEntity> arg0, int arg1, int arg2) {
					// TODO Auto-generated method stub
					
				}
			}, new SequenceEntityModifier(new ParallelEntityModifier(
					new MoveXModifier(0.1f, -sprite_two.getWidth()*2/3, (CAMERA_WIDTH/2 - sprite_two.getWidth()/2)),
					 new AlphaModifier(0.1f, 0.5f,1.0f)
					)));
   	     
		   sprite_two.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);  
		   sprite_two.registerEntityModifier(entityModifier);
	     }

	}
	
	
	//map右移动画
	private void setMapAnimateRight(){
		
		  this.sprite_one = null;
		   this.sprite_two = null;
	       this.sprite_three = null;
		   this.sprite_four = null;
		     
		      if(mapIndexNum > 0){
		    	  sprite_one = this.gameSourceManager.childreButtonSprites[mapIndexNum - 1];
		      }
		      if(mapIndexNum + 1 < mapIndexMax){
			     sprite_two = this.gameSourceManager.childreButtonSprites[mapIndexNum + 1];
		      }
		      sprite_three = this.gameSourceManager.childreButtonSprites[mapIndexNum];
		      
		     if(mapIndexNum + 2 < mapIndexMax){
		    	 sprite_four = this.gameSourceManager.childreButtonSprites[mapIndexNum + 2];
		     }
		      if(sprite_two != null){
			   LoopEntityModifier entityModifier = new LoopEntityModifier(new IEntityModifierListener() {
					
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
						// TODO Auto-generated method stub
						 
						sprite_three.registerEntityModifier(new SequenceEntityModifier(
								  new ParallelEntityModifier(
										new MoveXModifier(0.1f, (CAMERA_WIDTH/2 - sprite_three.getWidth()/2),- sprite_three.getWidth()*2/3),
	                                   new AlphaModifier(0.5f, 1.0f,0.5f)
		    			)));
						
						if(sprite_one != null){
							sprite_one.registerEntityModifier(new SequenceEntityModifier(
									  new ParallelEntityModifier(
											new MoveXModifier(0.1f, - sprite_three.getWidth()*2/3,- sprite_one.getWidth())
			    			)));
						}
						
						if(sprite_four != null){
							sprite_four.registerEntityModifier(new SequenceEntityModifier(
									  new ParallelEntityModifier(
											new MoveXModifier(0.1f, CAMERA_WIDTH,(CAMERA_WIDTH - sprite_four.getWidth()/3)),
		                                   new AlphaModifier(0.5f, 0.0f,0.5f)
			    			)));
						}
						
					}
					
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						// TODO Auto-generated method stub

					}
				}, 1, new ILoopEntityModifierListener() {
					
					@Override
					public void onLoopStarted(LoopModifier<IEntity> arg0, int arg1, int arg2) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onLoopFinished(LoopModifier<IEntity> arg0, int arg1, int arg2) {
						// TODO Auto-generated method stub
						
					}
				}, new SequenceEntityModifier(new ParallelEntityModifier(
						new MoveXModifier(0.1f, CAMERA_WIDTH - sprite_two.getWidth()/3, (CAMERA_WIDTH/2 - sprite_two.getWidth()/2)),
						 new AlphaModifier(0.1f, 0.5f,1.0f)
						)));
	   	 
			   sprite_two.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);  
			   sprite_two.registerEntityModifier(entityModifier);
		      }
			
	}
	
  
}

package beichenming.menue;

import org.andengine.engine.camera.SmoothCamera;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import beichenming.manger.Gamemanger;
import beichenming.manger.Sourcemanger;
import beichenming.pocketmonster.LoadGame;

public class GameMenuScene extends MyScene{
	
	private static final int CAMERA_WIDTH = 480;
	private static final int CAMERA_HEIGHT = 320;
	
    public static Sourcemanger gameSourceManager = Sourcemanger.getInstance();
    private static Gamemanger gamemanger = Gamemanger.getInstance();
    
	protected static final int LAYER_BGROUND = 0;    //±³¾°Í¼²ãone
	protected static final int LAYER_MENU = LAYER_BGROUND + 1;  //±³¾°Í¼²ãtwo
	
	protected Scene bcmScene;
	protected SmoothCamera bcmSmoothCamera; 
	protected Activity bcmActivity;
	private LoadGame loadGame;                  //¼ÓÔØÓÎÏ·
	
	private SharedPreferences sharedPreferences;
	
	
	public GameMenuScene(GameLogo activity) {
		super(activity);
		// TODO Auto-generated constructor stub
		this.bcmScene = this;
		this.bcmSmoothCamera = activity.myCamera;
		this.bcmActivity = activity;
		this.sharedPreferences = activity.getSharedPreferences("loginHero",activity.MODE_PRIVATE);
	}

	
	
	
	
	
	@Override
	public void init() {
		// TODO Auto-generated method stub
		gameSourceManager.loadGameTextures1(engine, bcmActivity, context.getAssets(), engine.getMusicManager(), engine.getSoundManager(), engine.getFontManager(), engine.getTextureManager());
		gameSourceManager.loadGameSprite1(engine,bcmSmoothCamera,context.getAssets());
		loadGame = new LoadGame(engine,context);
		this.setTouchAreaBindingOnActionDownEnabled(true);
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub
		gamemanger.nowScence = 1;
		this.initAttachChild();
		this.initRegisterEntityModifier();
		this.initmenueTouch();
        gameSourceManager.unloadGame1();
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
	
	private void initAttachChild()
	{     
		  this.getChildByIndex(LAYER_BGROUND).attachChild(gameSourceManager.bg_twoSprite);
		  this.getChildByIndex(LAYER_BGROUND).attachChild(gameSourceManager.bg_threeSprite);
		  this.getChildByIndex(LAYER_MENU).attachChild(gameSourceManager.newGameButtonSprite); 
		  this.getChildByIndex(LAYER_MENU).attachChild(gameSourceManager.loadGameButtonSprite); 
		  this.getChildByIndex(LAYER_MENU).attachChild(gameSourceManager.moreGameButtonSprite);    	
		  this.getChildByIndex(LAYER_MENU).attachChild(gameSourceManager.setGameButtonSprite);
		  this.getChildByIndex(LAYER_MENU).attachChild(gameSourceManager.backGameButtonSprite);
		  this.getChildByIndex(LAYER_MENU).attachChild(gameSourceManager.mNewGameText);
		  this.getChildByIndex(LAYER_MENU).attachChild(gameSourceManager.mLoadGameText);
		  this.getChildByIndex(LAYER_MENU).attachChild(gameSourceManager.mGameMoreText);  
		  this.getChildByIndex(LAYER_MENU).attachChild(gameSourceManager.musicButtonSpriteOff);
		  this.getChildByIndex(LAYER_MENU).attachChild(gameSourceManager.musicButtonSpriteOn);
		  this.getChildByIndex(LAYER_MENU).attachChild(gameSourceManager.soundButtonSpriteOff);
		  this.getChildByIndex(LAYER_MENU).attachChild(gameSourceManager.soundButtonSpriteOn);
    	  this.getChildByIndex(LAYER_MENU).attachChild(gameSourceManager.soundText);
    	  this.getChildByIndex(LAYER_MENU).attachChild(gameSourceManager.musicText);  

	}
	
	//Ìí¼Ó³¡¾°×¢²á
		private void initRegisterEntityModifier()
		{
		  this.registerTouchArea(gameSourceManager.newGameButtonSprite);
		  this.registerTouchArea(gameSourceManager.loadGameButtonSprite);
		  this.registerTouchArea(gameSourceManager.moreGameButtonSprite);
		  this.registerTouchArea(gameSourceManager.setGameButtonSprite);
		  this.registerTouchArea(gameSourceManager.backGameButtonSprite);
		  this.registerTouchArea(gameSourceManager.musicButtonSpriteOff);
		  this.registerTouchArea(gameSourceManager.musicButtonSpriteOn);
		  this.registerTouchArea(gameSourceManager.soundButtonSpriteOff);
		  this.registerTouchArea(gameSourceManager.soundButtonSpriteOn);
		}
   
		
	     //°´¼ü¹¦ÄÜ
	      private void initmenueTouch()
	      {    	  
	    	  
	    	  gameSourceManager.setGameButtonSprite.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(ButtonSprite arg0, float arg1, float arg2) {
					// TODO Auto-generated method stub			
					gameSourceManager.touchSound.play();
					bcmSmoothCamera.setCenter(CAMERA_WIDTH/2,-CAMERA_HEIGHT/2);
				}
			});
	    	
	    	  gameSourceManager.backGameButtonSprite.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(ButtonSprite arg0, float arg1, float arg2) {
					// TODO Auto-generated method stub
					gameSourceManager.touchSound.play();
					bcmSmoothCamera.setCenter(CAMERA_WIDTH/2,CAMERA_HEIGHT/2);	
				}
			});
	    	
	    	  gameSourceManager.musicButtonSpriteOn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(ButtonSprite arg0, float arg1, float arg2) {
					// TODO Auto-generated method stub
					gameSourceManager.touchSound.play();
					gameSourceManager.bg_twoMusic.pause();
					gameSourceManager.musicButtonSpriteOn.setVisible(false);
					gameSourceManager.musicButtonSpriteOff.setVisible(true);	
				}
			});
	    	
	    	  gameSourceManager.musicButtonSpriteOff.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(ButtonSprite arg0, float arg1, float arg2) {
					// TODO Auto-generated method stub
					gameSourceManager.touchSound.play();
					gameSourceManager.bg_twoMusic.play();
					gameSourceManager.musicButtonSpriteOff.setVisible(false);
					gameSourceManager.musicButtonSpriteOn.setVisible(true);
				}
			});
	    	
	    	  gameSourceManager.soundButtonSpriteOff.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(ButtonSprite arg0, float arg1, float arg2) {
					// TODO Auto-generated method stub
					gameSourceManager.touchSound.play();
					gameSourceManager.soundButtonSpriteOff.setVisible(false);
					gameSourceManager.soundButtonSpriteOn.setVisible(true);
				}
			});
	    	
	    	  gameSourceManager.soundButtonSpriteOn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(ButtonSprite arg0, float arg1, float arg2) {
					// TODO Auto-generated method stub
					gameSourceManager.touchSound.play();
					gameSourceManager.soundButtonSpriteOff.setVisible(true);
					gameSourceManager.soundButtonSpriteOn.setVisible(false);
				}
			});
	    	
	    	  gameSourceManager.newGameButtonSprite.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(ButtonSprite arg0, float arg1, float arg2) {
					// TODO Auto-generated method stub
					bcmActivity.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							gameSourceManager.touchSound.play();
						   if(!sharedPreferences.getBoolean("IS_LOGIN",false)){
							  AchievementScene gameAchievementScene = new AchievementScene(activity); 
							  bcmScene.setChildScene(gameAchievementScene,false,true,true);
							  loadGame.loadGame(gameAchievementScene);
						   }else{
							   GameMapScene gameMapScene = new GameMapScene(activity,0);
							   bcmScene.setChildScene(gameMapScene,false,true,true);
							   loadGame.loadGame(gameMapScene);
						   }
						
						}
					});
				}
			});
	    	  
	    	  gameSourceManager.loadGameButtonSprite.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(ButtonSprite arg0, float arg1, float arg2) {
					// TODO Auto-generated method stub
					bcmActivity.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							gameSourceManager.touchSound.play();
						    GameMapScene gameMapScene = new GameMapScene(activity,1);
							bcmScene.setChildScene(gameMapScene,false,true,true);
							loadGame.loadGame(gameMapScene);
						}
					});
				}
			});
	    	  
	    	  gameSourceManager.moreGameButtonSprite.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(ButtonSprite arg0, float arg1, float arg2) {
					// TODO Auto-generated method stub
					gameSourceManager.touchSound.play();
				}
			});
	    	  
	    	
	   }
		

}

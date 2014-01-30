package beichenming.menue;


import java.lang.annotation.Retention;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.LoopEntityModifier.ILoopEntityModifierListener;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.IOnAreaTouchListener;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.TickerText;
import org.andengine.entity.text.TickerText.TickerTextOptions;
import org.andengine.extension.tmx.TMXLayer;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.HorizontalAlign;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.LoopModifier;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.opengl.GLES20;
import android.util.Log;
import android.widget.Toast;
import beichenming.manger.Gamemanger;
import beichenming.manger.Sourcemanger;
import beichenming.manger.TextSource;
import beichenming.pocketmonster.LoadGame;


public class AchievementScene extends MyScene{
    
	public static Sourcemanger gameSourceManager = Sourcemanger.getInstance();

	protected static final int LAYER_BGROUND = 0;    //背景图层one
	protected static final int LAYER_MENU = LAYER_BGROUND + 1;  //背景图层two
	protected Scene bcmScene;
	protected final GameLogo tGameLogo ;
	public LoadGame loadGame;                              //load游戏
	
	private SharedPreferences sharedPreferences;
	
	//人物选择标号
    private static int boygirlFlag;
	private LoopEntityModifier entityModifier;

	
	public AchievementScene(GameLogo activity) {
		super(activity);
		// TODO Auto-generated constructor stub
		this.bcmScene = this;
		tGameLogo = activity;
		this.sharedPreferences = activity.getSharedPreferences("loginHero",activity.MODE_PRIVATE);
	}

	
	@Override
	public void init() {
		// TODO Auto-generated method stub
		this.boygirlFlag = 1;
		gameSourceManager.loadGameGB_oneTexture(engine,context);
		gameSourceManager.loadGameGB_oneSprite(engine,context,activity.myCamera);
		loadGame = new LoadGame(engine,context);
	    setintroductionString(this);
	}

	
	@Override
	public void draw() {
		// TODO Auto-generated method stub
		this.setOnAreaTouchListener(activity);
	    this.getChildByIndex(LAYER_BGROUND).attachChild(gameSourceManager.bg_fourSprite);
		this.getChildByIndex(LAYER_MENU).attachChild(gameSourceManager.introductionText_one); 
		this.getChildByIndex(LAYER_MENU).attachChild(gameSourceManager.damuAnimatedSprite);
	    gameSourceManager.keyboardMusic.play();
	    initAllButton(this);
	    Gamemanger.nowScence = 0;
	    gameSourceManager.unloadGame2();
	}
    
	
	private void setintroductionString(final Scene myScene)
	{
		//设置文字效果
		 final LoopEntityModifier entityModifier1 = new LoopEntityModifier(new IEntityModifierListener() {
				
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
					// TODO Auto-generated method stub
				}
				
				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					// TODO Auto-generated method stub
                  gameSourceManager.introductionFont_one.unload();
                  myScene.getChildByIndex(LAYER_MENU).detachChild(gameSourceManager.introductionText_one);
          		  myScene.getChildByIndex(LAYER_MENU).attachChild(gameSourceManager.introductionText_two); 
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
			}, new SequenceEntityModifier(new AlphaModifier(13.5f, 1.0f, 1.0f)));
		  
	    gameSourceManager.introductionText_one.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);  
		gameSourceManager.introductionText_one.registerEntityModifier(entityModifier1); 
		
		 final LoopEntityModifier entityModifier2 = new LoopEntityModifier(new IEntityModifierListener() {
				
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
					// TODO Auto-generated method stub
				}
				
				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					// TODO Auto-generated method stub
				  myScene.getChildByIndex(LAYER_MENU).attachChild(gameSourceManager.arrowHeadSprite);
                  gameSourceManager.keyboardMusic.pause();
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
			}, new SequenceEntityModifier(new AlphaModifier(10.5f, 1.0f, 1.0f)));
		gameSourceManager.introductionText_two.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);  
		gameSourceManager.introductionText_two.registerEntityModifier(entityModifier2);
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

	
    private  void initAllButton(final Scene myScene)
    {  
    	myScene.registerTouchArea(gameSourceManager.arrowHeadSprite);
	    myScene.registerTouchArea(gameSourceManager.select_leftSprite);
	    myScene.registerTouchArea(gameSourceManager.select_rightSprite);
	    myScene.registerTouchArea(gameSourceManager.acceptBtSprite);
	    gameSourceManager.arrowHeadSprite.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(ButtonSprite arg0, float arg1, float arg2) {
				// TODO Auto-generated method stub
				gameSourceManager.damuBitmapTextureAtlas.unload();
				gameSourceManager.introductionFont_two.unload();
				gameSourceManager.arrowHeadBitmapTextureAtlas.unload();
				myScene.unregisterTouchArea(gameSourceManager.arrowHeadSprite);
				myScene.getChildByIndex(LAYER_MENU).detachChild(gameSourceManager.damuAnimatedSprite);
			    myScene.getChildByIndex(LAYER_MENU).detachChild(gameSourceManager.introductionText_two);
			    myScene.getChildByIndex(LAYER_MENU).detachChild(gameSourceManager.arrowHeadSprite);
			    myScene.getChildByIndex(LAYER_MENU).attachChild(gameSourceManager.renwuchoosetText);
			    myScene.getChildByIndex(LAYER_MENU).attachChild(gameSourceManager.acceptBtSprite);
			    myScene.getChildByIndex(LAYER_MENU).attachChild(gameSourceManager.boy_oneintroductionText);
			    myScene.getChildByIndex(LAYER_MENU).attachChild(gameSourceManager.boygirl_oneSprite);
			    myScene.getChildByIndex(LAYER_MENU).attachChild(gameSourceManager.boygirl_twoSprite);
			    myScene.getChildByIndex(LAYER_MENU).attachChild(gameSourceManager.boygirl_threeSprite);
			    myScene.getChildByIndex(LAYER_MENU).attachChild(gameSourceManager.boygirl_fourSprite);
			    gameSourceManager.boygirl_twoSprite.setVisible(false);
			    gameSourceManager.boygirl_threeSprite.setVisible(false);
			    gameSourceManager.boygirl_fourSprite.setVisible(false);
			    myScene.getChildByIndex(LAYER_MENU).attachChild(gameSourceManager.select_leftSprite);
			    myScene.getChildByIndex(LAYER_MENU).attachChild(gameSourceManager.select_rightSprite);
			    gameSourceManager.touchSound.play();
			}
		});
	    
	    gameSourceManager.acceptBtSprite.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(ButtonSprite arg0, float arg1, float arg2) {
				// TODO Auto-generated method stub
				tGameLogo.runOnUiThread(new Runnable() {
					@Override
					public void run() {
					    Editor editor = sharedPreferences.edit();
					    editor.putBoolean("IS_LOGIN",true);
					    editor.putInt("HeroName", boygirlFlag);
					    editor.commit();
					    GameMapScene gameMapScene = new GameMapScene(activity,0);
					    bcmScene.setChildScene(gameMapScene,false,true,true);
						loadGame.loadGame(gameMapScene);
					}
				});
			}
		});
	    
	    
	    gameSourceManager.select_leftSprite.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(ButtonSprite arg0, float arg1, float arg2) {
				// TODO Auto-generated method stub
				switch (boygirlFlag) {
				case 1:
					setBoyGirlMove_left(gameSourceManager.boygirl_oneSprite,gameSourceManager.boygirl_fourSprite,gameSourceManager.boygirl_oneSprite.getWidth());
                      boygirlFlag = 4;
                      gameSourceManager.boy_fourintroductionText = new TickerText(270, 60, gameSourceManager.boygirlintroductionFont, TextSource.girl_twoIntroductionString, new TickerTextOptions(HorizontalAlign.LEFT, 16),engine.getVertexBufferObjectManager());
                      bcmScene.getChildByIndex(LAYER_MENU).detachChild(gameSourceManager.boy_oneintroductionText);
                      bcmScene.getChildByIndex(LAYER_MENU).attachChild(gameSourceManager.boy_fourintroductionText);
					break;

                case 2:
                	setBoyGirlMove_left(gameSourceManager.boygirl_twoSprite,gameSourceManager.boygirl_oneSprite,gameSourceManager.boygirl_twoSprite.getWidth());
                       boygirlFlag = 1;
                       gameSourceManager.boy_oneintroductionText = new TickerText(270, 60, gameSourceManager.boygirlintroductionFont, TextSource.boy_oneIntroductionString, new TickerTextOptions(HorizontalAlign.LEFT, 16),engine.getVertexBufferObjectManager());
                       bcmScene.getChildByIndex(LAYER_MENU).detachChild(gameSourceManager.boy_twointroductionText);
                       bcmScene.getChildByIndex(LAYER_MENU).attachChild(gameSourceManager.boy_oneintroductionText);
					break;
					
                case 3:
                	setBoyGirlMove_left(gameSourceManager.boygirl_threeSprite,gameSourceManager.boygirl_twoSprite,gameSourceManager.boygirl_threeSprite.getWidth());
                       boygirlFlag = 2;
                       gameSourceManager.boy_twointroductionText = new TickerText(270, 60, gameSourceManager.boygirlintroductionFont, TextSource.boy_twoIntroductionString, new TickerTextOptions(HorizontalAlign.LEFT, 16),engine.getVertexBufferObjectManager());
                       bcmScene.getChildByIndex(LAYER_MENU).detachChild(gameSourceManager.boy_threeintroductionText);
                       bcmScene.getChildByIndex(LAYER_MENU).attachChild(gameSourceManager.boy_twointroductionText);
					break;
				
                case 4:
                	setBoyGirlMove_left(gameSourceManager.boygirl_fourSprite,gameSourceManager.boygirl_threeSprite,gameSourceManager.boygirl_fourSprite.getWidth());
                       boygirlFlag = 3;
                       gameSourceManager.boy_threeintroductionText = new TickerText(270, 60, gameSourceManager.boygirlintroductionFont, TextSource.girl_oneIntroductionString, new TickerTextOptions(HorizontalAlign.LEFT, 16),engine.getVertexBufferObjectManager());
                       bcmScene.getChildByIndex(LAYER_MENU).detachChild(gameSourceManager.boy_fourintroductionText);
                       bcmScene.getChildByIndex(LAYER_MENU).attachChild(gameSourceManager.boy_threeintroductionText);
	                break;
				}
			}
		});
	    
	    
	    gameSourceManager.select_rightSprite.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(ButtonSprite arg0, float arg1, float arg2) {
				// TODO Auto-generated method stub
				switch (boygirlFlag) {
				case 1:
					setBoyGirlMove_right(gameSourceManager.boygirl_oneSprite,gameSourceManager.boygirl_twoSprite,gameSourceManager.boygirl_oneSprite.getWidth());
                      boygirlFlag = 2;
                      gameSourceManager.boy_twointroductionText = new TickerText(270, 60, gameSourceManager.boygirlintroductionFont, TextSource.boy_twoIntroductionString, new TickerTextOptions(HorizontalAlign.LEFT, 16),engine.getVertexBufferObjectManager());
                      bcmScene.getChildByIndex(LAYER_MENU).detachChild(gameSourceManager.boy_oneintroductionText);
                      bcmScene.getChildByIndex(LAYER_MENU).attachChild(gameSourceManager.boy_twointroductionText);
					break;

                case 2:
                	setBoyGirlMove_right(gameSourceManager.boygirl_twoSprite,gameSourceManager.boygirl_threeSprite,gameSourceManager.boygirl_twoSprite.getWidth());
                       boygirlFlag = 3;
                       gameSourceManager.boy_threeintroductionText = new TickerText(270, 60, gameSourceManager.boygirlintroductionFont, TextSource.girl_oneIntroductionString, new TickerTextOptions(HorizontalAlign.LEFT, 16),engine.getVertexBufferObjectManager());
                       bcmScene.getChildByIndex(LAYER_MENU).detachChild(gameSourceManager.boy_twointroductionText);
                       bcmScene.getChildByIndex(LAYER_MENU).attachChild(gameSourceManager.boy_threeintroductionText);
					break;
					
                case 3:
                	setBoyGirlMove_right(gameSourceManager.boygirl_threeSprite,gameSourceManager.boygirl_fourSprite,gameSourceManager.boygirl_threeSprite.getWidth());
                       boygirlFlag = 4;
                       gameSourceManager.boy_fourintroductionText = new TickerText(270, 60, gameSourceManager.boygirlintroductionFont, TextSource.girl_twoIntroductionString, new TickerTextOptions(HorizontalAlign.LEFT, 16),engine.getVertexBufferObjectManager());
                       bcmScene.getChildByIndex(LAYER_MENU).detachChild(gameSourceManager.boy_threeintroductionText);
                       bcmScene.getChildByIndex(LAYER_MENU).attachChild(gameSourceManager.boy_fourintroductionText);
					break;
				
                case 4:
                	setBoyGirlMove_right(gameSourceManager.boygirl_fourSprite,gameSourceManager.boygirl_oneSprite,gameSourceManager.boygirl_fourSprite.getWidth());
                       boygirlFlag = 1;
                       gameSourceManager.boy_oneintroductionText = new TickerText(270, 60, gameSourceManager.boygirlintroductionFont, TextSource.boy_oneIntroductionString, new TickerTextOptions(HorizontalAlign.LEFT, 16),engine.getVertexBufferObjectManager());
                       bcmScene.getChildByIndex(LAYER_MENU).detachChild(gameSourceManager.boy_fourintroductionText);
                       bcmScene.getChildByIndex(LAYER_MENU).attachChild(gameSourceManager.boy_oneintroductionText);
	                break;
				}
			}
		});
	      
    }
    
    
    //人物移动效果右边
    public void setBoyGirlMove_right(final Sprite mySprite_one,final Sprite mySprite_two,final float length)
    {
    	  Log.d("right", "d");
    	   this.entityModifier = new LoopEntityModifier(new IEntityModifierListener() {
				
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
					// TODO Auto-generated method stub
					  mySprite_two.setVisible(true);
					  mySprite_two.registerEntityModifier(new SequenceEntityModifier(
							  new ParallelEntityModifier(
									new MoveXModifier(0.5f, 30, 90),
								    new ScaleModifier(0.5f,0.0f,1.0f))
	    			));
				}
				
				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					// TODO Auto-generated method stub
					mySprite_one.setVisible(false);
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
					new MoveXModifier(0.5f, 90, 150),
					new ScaleModifier(0.5f,1.0f,0.0f))
	    			));
    	 
    	   mySprite_one.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);  
    	   mySprite_one.registerEntityModifier(entityModifier);
    	   gameSourceManager.touchSound.play();
    }
    
    
    //人物移动效果左边
    public void setBoyGirlMove_left(final Sprite mySprite_one,final Sprite mySprite_two,final float length)
    {
    	  Log.d("left", "d");
    	   this.entityModifier = new LoopEntityModifier(new IEntityModifierListener() {
				
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
					// TODO Auto-generated method stub
					  mySprite_two.setVisible(true);
					  mySprite_two.registerEntityModifier(new SequenceEntityModifier(
							  new ParallelEntityModifier(
	    			 new MoveXModifier(0.5f, 150, 90)
	    			,new ScaleModifier(0.5f,0.0f,1.0f)
	    			)));
				}
				
				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					// TODO Auto-generated method stub
					mySprite_one.setVisible(false);
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
	    			new MoveXModifier(0.5f, 90,30)
	    			,new ScaleModifier(0.5f,1.0f,0.0f)
	    			)));
    	 
    	   mySprite_one.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);  
    	   mySprite_one.registerEntityModifier(entityModifier);
    	   gameSourceManager.touchSound.play();
    }

    

}

package beichenming.menue;


import java.io.IOException;

import javax.microedition.khronos.opengles.GL10;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.music.MusicManager;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.audio.sound.SoundManager;
import org.andengine.engine.FixedStepEngine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.SmoothCamera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.engine.options.resolutionpolicy.FixedResolutionPolicy;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.Entity;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.IEntityModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.LoopEntityModifier.ILoopEntityModifierListener;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.scene.IOnAreaTouchListener;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.util.FPSLogger;
import org.andengine.extension.physics.box2d.FixedStepPhysicsWorld;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.ui.activity.BaseActivity;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.call.Callable;
import org.andengine.util.call.Callback;
import org.andengine.util.color.Color;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.LoopModifier;
import org.andengine.util.modifier.IModifier.DeepCopyNotSupportedException;
import org.andengine.util.modifier.IModifier.IModifierListener;
import org.andengine.util.progress.IProgressListener;
import org.andengine.util.progress.ProgressCallable;

import com.badlogic.gdx.math.Vector2;

import beichenming.manger.GameStatus;
import beichenming.manger.Gamemanger;
import beichenming.manger.IConstants;
import beichenming.manger.Sourcemanger;
import beichenming.pocketmonster.GameResource;
import beichenming.pocketmonster.LoadGame;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.SensorManager;
import android.service.wallpaper.WallpaperService.Engine;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

/*  Author : beichenming 2013.07.02
 *  载入游戏Logo
 */

public class GameLogo extends BaseGameActivity implements IOnAreaTouchListener,IConstants{
     
	//定义屏幕高度和宽度
	private static final int CAMERA_WIDTH = 480;
	private static final int CAMERA_HEIGHT = 320;
	
	// Camera movement speeds
	final float maxVelocityX = 200;
	final float maxVelocityY = 2000;
	// Camera zoom speed
	final float maxZoomFactorChange = 5;
	
	final GameLogo tGameLogo = this;
	//定义摄像头
	protected SmoothCamera myCamera;
    
	//定义主场景
	protected static Scene myScene;    //logo场景
	protected static Scene mainScene;  //游戏画面场景
	public LoadGame loadGame;          //加载游戏
	
	
	//定义屏幕场景图层
	protected static final int LAYER_BGROUND = 0;    //背景图层one
	protected static final int LAYER_MENU = LAYER_BGROUND + 1;  //背景图层two
	
	
	private static Sourcemanger gameSourcemanger = Sourcemanger.getInstance();
    
	


	@Override
	public org.andengine.engine.Engine onCreateEngine(
			EngineOptions pEngineOptions) {
		// TODO Auto-generated method stub
		return new FixedStepEngine(pEngineOptions, 60);
	}


	@Override
	public EngineOptions onCreateEngineOptions() {
		// TODO Auto-generated method stub
	    this.myCamera = new SmoothCamera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT, maxVelocityX, maxVelocityY, maxZoomFactorChange);
	    
	    EngineOptions engineOptions = new EngineOptions(true,ScreenOrientation.LANDSCAPE_FIXED,new RatioResolutionPolicy(1.5f),myCamera);
//	    EngineOptions engineOptions = new EngineOptions(true,ScreenOrientation.LANDSCAPE_FIXED,new FillResolutionPolicy(),myCamera);
	    engineOptions.setWakeLockOptions(WakeLockOptions.SCREEN_ON); 
	    
	    //设置游戏sound和music
	    engineOptions.getAudioOptions().setNeedsMusic(true);
	    engineOptions.getAudioOptions().setNeedsSound(true);
	   
	    return engineOptions;
	}
	
	
	@Override
	public void onCreateResources(OnCreateResourcesCallback arg0)
    {
		// TODO Auto-generated method stub       
       gameSourcemanger.loadGameTextures(mEngine, getApplicationContext(), getAssets(), getMusicManager(), getSoundManager(), getFontManager(), getTextureManager());
       loadGame = new LoadGame(mEngine, tGameLogo);
       arg0.onCreateResourcesFinished();
	}
	
	
	
	@SuppressWarnings("deprecation")
	@Override
	public void onCreateScene(OnCreateSceneCallback arg0)
	{
		// TODO Auto-generated method stub
		    myScene = new Scene();
		    mainScene = new Scene(2);
             
		  mainScene.setTouchAreaBindingOnActionDownEnabled(true);
		//通知回调函数，我们完成了创建scene对象
		arg0.onCreateSceneFinished(myScene);
		
	}
	
	
	@Override
	public void onPopulateScene(Scene arg0, OnPopulateSceneCallback arg1)
    {
		// TODO Auto-generated method stub
		gameSourcemanger.loadGameSprite(mEngine,myCamera,getAssets());
		this.initAttachChild();
		this.initRegisterEntityModifier();
		this.initGamelogo();
		arg1.onPopulateSceneFinished();
	}
	
	
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		GameStatus.mGameRunning = false;             //游戏暂停
	}


	@Override
	protected synchronized void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		GameStatus.mGameRunning = true;             //游戏开始
	}


	//添加资源场景
	private void initAttachChild()
	{     
		  myScene.attachChild(gameSourcemanger.dynamicBG);	
		  mainScene.getChildByIndex(LAYER_BGROUND).attachChild(gameSourcemanger.bg_oneSprite);
		  mainScene.getChildByIndex(LAYER_MENU).attachChild(gameSourcemanger.pushstartText);
	}
	
	
	//添加场景注册
	private void initRegisterEntityModifier()
	{
	  mainScene.registerTouchArea(gameSourcemanger.bg_oneSprite);
      mainScene.setBackgroundEnabled(true);
      mainScene.setOnAreaTouchListener(this);
	}
	

		@Override
		public boolean onAreaTouched(TouchEvent arg0, ITouchArea arg1,
				float arg2, float arg3) {
			// TODO Auto-generated method stub
         if(arg0.isActionDown())
		  {
        	 if(arg1.equals(gameSourcemanger.bg_oneSprite))
			  {  
				gameSourcemanger.touchSound.play();     //touch音效
			    this.set_pushstart();
			 }
		  }
			return false;
		}
    
		
		
		
	     //设置push_start场景淡入动画
	      private void set_pushstart()
	      {
		      final LoopEntityModifier entityModifier = new LoopEntityModifier(new IEntityModifierListener() {
					
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
						// TODO Auto-generated method stub
					}
					
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						// TODO Auto-generated method stub
						GameLogo.this.runOnUiThread(new Runnable() {
							@Override
							public void run() {
								GameMenuScene gameMenuScene = new GameMenuScene(tGameLogo); 
								mainScene.setChildScene(gameMenuScene,false,true,true);
								loadGame.loadGame(gameMenuScene);
							}
						});
						
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
				}, new SequenceEntityModifier(new AlphaModifier(2.0f,1.0f,0.0f)));
		      
		      gameSourcemanger.bg_oneSprite.registerEntityModifier(entityModifier); 
	      }
	      
	      
	      //初始化游戏logo
	      private void initGamelogo()
	      {
				final LoopEntityModifier entityModifier = new LoopEntityModifier(new IEntityModifierListener() {
					
					@Override
					public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
						// TODO Auto-generated method stub
					   gameSourcemanger.logo_Music.play();    //播放logo音乐
					}
					
					@Override
					public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
						// TODO Auto-generated method stub
						GameLogo.this.runOnUiThread(new Runnable() {
							@Override
							public void run() {
							 	myScene.setChildScene(mainScene,false,true,true);
							 	gameSourcemanger.bg_oneMusic.play();
							 	Gamemanger.nowScence = 1;
							}
						});
						
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
				}, new SequenceEntityModifier(	
						 new RotationModifier(1.0f, -20, 20),
					     new RotationModifier(1.0f, 20, 0),
						 new ScaleModifier(2.0f, 1, 0.5f))
				);
				  gameSourcemanger.dynamicBG.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
				  gameSourcemanger.dynamicBG.registerEntityModifier(entityModifier);		
	      }
		  


		@Override
		public boolean onKeyDown(int keyCode, KeyEvent event) {
			// TODO Auto-generated method stub
			if(keyCode == KeyEvent.KEYCODE_BACK)
			{
			  switch (Gamemanger.nowScence) {
			case 0:
			  return true;
			  
			case 1:
			     //弹出确定退出对话框
	            new AlertDialog.Builder(this)
	            .setTitle("退出游戏")
	            .setMessage("您确定退出游戏吗？")
	            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
	                
	                @Override
	                public void onClick(DialogInterface dialog, int which) {
	                    // TODO Auto-generated method stub
	                    Intent exit = new Intent(Intent.ACTION_MAIN);
	                    exit.addCategory(Intent.CATEGORY_HOME);
	                    exit.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	                    startActivity(exit);
	                    System.exit(0);
	                }
	            })
	            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
	                
	                @Override
	                public void onClick(DialogInterface dialog, int which) {
	                    // TODO Auto-generated method stub
	                    dialog.cancel();
	                }
	            })
	            .show();
	            return true;
	         default:
	        		
	        	 break;
			}
		}
			return super.onKeyDown(keyCode, event);
	}
	     
	     
	
}

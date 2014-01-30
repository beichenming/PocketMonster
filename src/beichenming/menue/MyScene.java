package beichenming.menue;

import java.util.ArrayList;

import org.andengine.audio.music.MusicManager;
import org.andengine.audio.sound.SoundManager;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.SmoothCamera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontManager;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.ui.activity.BaseGameActivity;

import android.content.Context;


public abstract class MyScene extends Scene {
   
	protected BitmapTextureAtlas mFontTexture;
	protected Font mFont;
//	private ChangeableText loadText;
	private IUpdateHandler loadHandler;
	
	protected IUpdateHandler mHandler;
	protected GameLogo activity;		//Activity类的引用
	protected Engine engine;			       //Engine类的引用
	protected Camera camera;			        //Camera类的引用
	
	protected  TextureManager textureManager;	//TextureManager类的引用
	protected  FontManager fontManager;			//FontManager类的引用
	protected MusicManager musicManager;		//MusicManager类的引用
	protected SoundManager soundManager;		//SoundManager类的引用
	protected Context context;
	protected float touchX, touchY;				//点触的坐标	
	private short sleepTime;						//睡眠时间
	private long runTime, runTime2;
	private static final int ChildCount = 5;
	
	/*********构造个Scene*********/
	public MyScene(GameLogo activity){
		
		//初始化相关
		super(ChildCount);
		this.activity = activity;
		engine = this.activity.getEngine();
		textureManager = engine.getTextureManager();
		fontManager = engine.getFontManager();
		musicManager = engine.getMusicManager();
		soundManager = engine.getSoundManager();
		camera = engine.getCamera();
		context = activity.getApplicationContext();
		IAsyncCallback callback = new IAsyncCallback() {

			@Override
			public void workToDo() {
				// TODO Auto-generated method stub
				init();		//执行初始化方法，加载资源
				
			}

			@Override
			public void onComplete() {
				// TODO Auto-generated method stu
				draw(); 	//执行画方法，绘制资源
				/******在Scene中按键监听*******/
				setOnSceneTouchListener(new IOnSceneTouchListener(){

					@Override
					public boolean onSceneTouchEvent(Scene arg0, TouchEvent arg1) {
						// TODO Auto-generated method stub
						touchX = arg1.getX();
						touchY = arg1.getY();
						
						if(arg1.isActionUp()){
							eventUp();
						}							//按钮松开
							
						if(arg1.isActionMove()){
							eventMove();
						}							//按钮移动
							
						if(arg1.isActionDown()){
							eventDown();
						}							//按钮按下	
						return true;		//该值为ture则一直监听，为false则只监听一次		
					}
					
				});
				
				/*******在Scene中性程监听*********/
				mHandler = new IUpdateHandler() {

					@Override
					public void onUpdate(float pSecondsElapsed) {
						// TODO Auto-generated method stub
							logic(pSecondsElapsed); //在scene中执行逻辑方法
					}
					
					@Override
					public void reset() {

					}
				
				};
				registerUpdateHandler(mHandler);
			}
		
		};
				// TODO Auto-generated method stub	
		new AsyncTaskLoader().execute(callback);
	  
	}
	
	protected void finalize() throws Throwable {
		textureManager.onDestroy();
		fontManager.onDestroy();
		System.gc();
		super.finalize();
	}
	
	/*******构造加载资源方法*****/
	public abstract void init();
	
	
	/*******构造绘制方法*********/
	public abstract void draw();
	
	
	/*******构造性程监听方法*****/
	public abstract void logic(float SecondsElapsed);
	
	
//	/********资源清空的方法******/
//	public abstract void logic();
	
	/*******按键被按下的事件*****/
	public abstract void eventDown();
	
	
	/*******按键被移动的事件******/
	public abstract void eventMove();
	
	
	/*******按键被松口的事件*******/
	public abstract void eventUp();
	
}

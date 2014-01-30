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
	protected GameLogo activity;		//Activity�������
	protected Engine engine;			       //Engine�������
	protected Camera camera;			        //Camera�������
	
	protected  TextureManager textureManager;	//TextureManager�������
	protected  FontManager fontManager;			//FontManager�������
	protected MusicManager musicManager;		//MusicManager�������
	protected SoundManager soundManager;		//SoundManager�������
	protected Context context;
	protected float touchX, touchY;				//�㴥������	
	private short sleepTime;						//˯��ʱ��
	private long runTime, runTime2;
	private static final int ChildCount = 5;
	
	/*********�����Scene*********/
	public MyScene(GameLogo activity){
		
		//��ʼ�����
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
				init();		//ִ�г�ʼ��������������Դ
				
			}

			@Override
			public void onComplete() {
				// TODO Auto-generated method stu
				draw(); 	//ִ�л�������������Դ
				/******��Scene�а�������*******/
				setOnSceneTouchListener(new IOnSceneTouchListener(){

					@Override
					public boolean onSceneTouchEvent(Scene arg0, TouchEvent arg1) {
						// TODO Auto-generated method stub
						touchX = arg1.getX();
						touchY = arg1.getY();
						
						if(arg1.isActionUp()){
							eventUp();
						}							//��ť�ɿ�
							
						if(arg1.isActionMove()){
							eventMove();
						}							//��ť�ƶ�
							
						if(arg1.isActionDown()){
							eventDown();
						}							//��ť����	
						return true;		//��ֵΪture��һֱ������Ϊfalse��ֻ����һ��		
					}
					
				});
				
				/*******��Scene���Գ̼���*********/
				mHandler = new IUpdateHandler() {

					@Override
					public void onUpdate(float pSecondsElapsed) {
						// TODO Auto-generated method stub
							logic(pSecondsElapsed); //��scene��ִ���߼�����
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
	
	/*******���������Դ����*****/
	public abstract void init();
	
	
	/*******������Ʒ���*********/
	public abstract void draw();
	
	
	/*******�����Գ̼�������*****/
	public abstract void logic(float SecondsElapsed);
	
	
//	/********��Դ��յķ���******/
//	public abstract void logic();
	
	/*******���������µ��¼�*****/
	public abstract void eventDown();
	
	
	/*******�������ƶ����¼�******/
	public abstract void eventMove();
	
	
	/*******�������ɿڵ��¼�*******/
	public abstract void eventUp();
	
}

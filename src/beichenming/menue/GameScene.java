package beichenming.menue;

import java.util.ArrayList;
import java.util.HashMap;

import javax.microedition.khronos.opengles.GL10;
import javax.security.auth.PrivateCredentialPermission;

import org.andengine.engine.camera.SmoothCamera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.camera.hud.controls.BaseOnScreenControl;
import org.andengine.engine.camera.hud.controls.DigitalOnScreenControl;
import org.andengine.engine.camera.hud.controls.BaseOnScreenControl.IOnScreenControlListener;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.physics.PhysicsHandler;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.shape.IShape;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;
import org.andengine.extension.physics.box2d.FixedStepPhysicsWorld;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.extension.tmx.TMXLayer;
import org.andengine.extension.tmx.TMXLoader;
import org.andengine.extension.tmx.TMXObject;
import org.andengine.extension.tmx.TMXObjectGroup;
import org.andengine.extension.tmx.TMXProperties;
import org.andengine.extension.tmx.TMXTile;
import org.andengine.extension.tmx.TMXTileProperty;
import org.andengine.extension.tmx.TMXTiledMap;
import org.andengine.extension.tmx.TMXLoader.ITMXTilePropertiesListener;
import org.andengine.extension.tmx.util.exception.TMXLoadException;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.util.debug.Debug;
import org.andengine.util.math.MathUtils;




import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

import android.R.integer;
import android.drm.DrmUtils.ExtendedMetadataParser;
import android.hardware.SensorManager;
import android.opengl.Visibility;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;
import beichenming.manger.GameStatus;
import beichenming.manger.Gamemanger;
import beichenming.manger.IConstants;
import beichenming.manger.LoadMapManger;
import beichenming.manger.MapArrayList;
import beichenming.manger.MyBox;
import beichenming.manger.Sourcemanger;
import beichenming.manger.TextSource;
import beichenming.manger.boxXY;

public class GameScene extends MyScene implements IConstants{
	
	//定义屏幕高度和宽度
	private static final int CAMERA_WIDTH = 480;
	private static final int CAMERA_HEIGHT = 320;
    public static Sourcemanger gameSourceManager = Sourcemanger.getInstance();
    private static Gamemanger gamemanger = Gamemanger.getInstance();
	protected static final int LAYER_BGROUND = 0;                     //背景图层one
	protected static final int LAYER_MENU = LAYER_BGROUND + 1;        //背景图层two
	private static final int LAYER_MAN = LAYER_MENU + 1;
	private static final int LAYER_TREE = LAYER_MAN + 1;
	private static final int LAYER_SCORE = LAYER_TREE + 1;	
	protected GameScene bcmScene;
	protected SmoothCamera bcmSmoothCamera;
	protected GameLogo bcmGameLogo;
	protected HUD bcmHud;
    private CountDownTimer mCountDownTimer;                       //游戏计时器
	
	private MapArrayList mapArrayList = new MapArrayList();      //地图数组
	private MyBox existBox;                                            //可以获得的盒子
	
	
    private MyTiledMap map;
	private Rectangle playerbox;
	private PhysicsWorld mPhysicsWorld;            //创建物理世界
	private PhysicsConnector mPhysicsConnector;     
	private Body mPlayerBody;                      //人物刚体
	private long SPEED = 200;                      //动画帧数时间
	private float Ver = 2;                        //人物移动速度
    private int mTime = 0;                       //可以搬动或者放下的位置时间(经过mtime时间开始提示)
	private long totalTime;                      //游戏时间定义
	private long totalScore = 0;                 //游戏分数统计
	private long winscore;                      //胜利时候的分数
	private int MapIndex;                       //地图索引

	
	private HashMap<String,MyBox> mHashMap;                //定义hash表

	private boolean btAflag = false;                      //是否按下A键
	private boolean btEflag = false;                      //是否按下E键
	private boolean isDown = true;                      //盒子是否放下
	
	private LoadMapManger loadMapManger;
	private String mapnameString;
	
	public GameScene(GameLogo activity,int maptype,int mapindex) {
		super(activity);
		// TODO Auto-generated constructor stub
		this.bcmScene = this;
		this.bcmSmoothCamera = activity.myCamera;
		this.bcmGameLogo = activity;
	    this.loadMapManger = new LoadMapManger(maptype,mapindex);
		MapIndex = loadMapManger.getMapTypeNum();
		mapnameString = loadMapManger.getmap_name();
		winscore = loadMapManger.getMapscore();
	}


	@Override
	public void init() {
		// TODO Auto-generated method stub
		this.mPhysicsWorld =  new FixedStepPhysicsWorld(60, new Vector2(0, 0),false, 8, 1);
		gameSourceManager.loadGameGB_twoTexture(engine,context);
		gameSourceManager.loadGameGB_twoSprite(engine,context,activity.myCamera);
		this.setDigitalOnScreenControl();
		this.registerUpdateHandler(mPhysicsWorld);	
		this.map = new MyTiledMap(this, bcmGameLogo, mPhysicsWorld,loadMapManger);
	    this.setBodyPlay();
		this.initJewels();
	}

	
	@Override
	public void draw() {
		// TODO Auto-generated method stub
		
		bcmHud = new HUD();
		gameSourceManager.gamemapnameText.setText(mapnameString);
		this.setOnAreaTouchListener(activity);
		this.getChildByIndex(LAYER_MAN).attachChild(gameSourceManager.hero_oneAnimatedSprite);
		this.getChildByIndex(LAYER_MAN).attachChild(gameSourceManager.promptSprite);
		for(int i =0 ;i < 7 ;i ++){
			this.gameSourceManager.ui_oneSprite.attachChild(this.gameSourceManager.bodySprites[i]);
		}
		
		for(int i = 0 ;i < 2 ;i ++){
		  this.getChildByIndex(LAYER_TREE).attachChild(gameSourceManager.specialAnimationAnimatedSprite[i]);
		}
		bcmHud.attachChild(gameSourceManager.ui_oneSprite);
		bcmHud.attachChild(gameSourceManager.gamescoreText);
		bcmHud.attachChild(gameSourceManager.gameTimeText);
		bcmHud.attachChild(gameSourceManager.gamemapnameText);
		bcmHud.attachChild(gameSourceManager.btAButtonSprite);
		bcmHud.attachChild(gameSourceManager.btEButtonSprite);
		bcmHud.registerTouchArea(gameSourceManager.btAButtonSprite);
		bcmHud.registerTouchArea(gameSourceManager.btEButtonSprite);
		bcmSmoothCamera.setHUD(bcmHud);
		bcmSmoothCamera.setBoundsEnabled(true);
		bcmSmoothCamera.setChaseEntity(gameSourceManager.hero_oneAnimatedSprite);
		this.setChildScene(gameSourceManager.mDigitalOnScreenControl);   
		Gamemanger.nowScence = 2;
	    BTA_Event();
	    prepareGame();
	    startCountDownTimer();
	    autoTips();
	}
	
	
	//游戏手柄设置
	private void setDigitalOnScreenControl()
	{
		//游戏手柄设置
//		final PhysicsHandler physicsHandler = new PhysicsHandler(gameSourceManager.hero_oneAnimatedSprite);
//		gameSourceManager.hero_oneAnimatedSprite.registerUpdateHandler(physicsHandler);
	    gameSourceManager.mDigitalOnScreenControl = new DigitalOnScreenControl(0, CAMERA_HEIGHT - gameSourceManager.mOnScreenControlBaseITextureRegion.getHeight(), camera, gameSourceManager.mOnScreenControlBaseITextureRegion, gameSourceManager.mOnScreenControlKnobITextureRegion, 0.1f,engine.getVertexBufferObjectManager(),new IOnScreenControlListener() {
			
			@Override
			public void onControlChange(BaseOnScreenControl arg0, float arg1, float arg2) {
				// TODO Auto-generated method stub
			    if(GameStatus.mGameRunning){
			     gamemanger.setHeroMove_control(arg1, arg2,SPEED);
				 mPlayerBody.setLinearVelocity(arg1 * Ver, arg2* Ver);
			    }
			  }
		});
	  
	    
		/* Make the controls semi-transparent. */
	    gameSourceManager.mDigitalOnScreenControl.getControlBase().setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
	    gameSourceManager.mDigitalOnScreenControl.getControlBase().setAlpha(0.5f);
	    gameSourceManager.mDigitalOnScreenControl.getControlBase().setScaleCenter(0, 128);
	}
   

	
   //注册物理刚体(主人公)
   public void setBodyPlay()
   {
	  gameSourceManager.hero_oneAnimatedSprite.setPosition(map.playerX,map.playerY);
	   final FixtureDef playerFixtureDef = PhysicsFactory.createFixtureDef(0,0, 0.5f);

         playerbox = new Rectangle(gameSourceManager.hero_oneAnimatedSprite.getX(), 
				gameSourceManager.hero_oneAnimatedSprite.getY(),
				gameSourceManager.hero_oneAnimatedSprite.getWidth(), 
				gameSourceManager.hero_oneAnimatedSprite.getHeight() - 12,
				engine.getVertexBufferObjectManager());
		playerbox.setVisible(false);
		bcmScene.getChildByIndex(LAYER_MENU).attachChild(playerbox);
		mPlayerBody = PhysicsFactory.createBoxBody(this.mPhysicsWorld, playerbox,BodyType.DynamicBody, playerFixtureDef);
		this.mPhysicsConnector = new PhysicsConnector(gameSourceManager.hero_oneAnimatedSprite, mPlayerBody, true, false) {

			@Override
			public void onUpdate(float pSecondsElapsed) {
				
				final IShape shape = this.mShape;
				final Body body = this.mBody;
				if (this.mUpdatePosition) {
					final Vector2 position = body.getPosition();
					final float pixelToMeterRatio = this.mPixelToMeterRatio;
					shape.setPosition(position.x * pixelToMeterRatio
							- this.mShapeHalfBaseWidth, position.y
							* pixelToMeterRatio - (gameSourceManager.hero_oneAnimatedSprite.getHeight() - 12));	
				}
				bcmSmoothCamera.updateChaseEntity();
			}
		};
		this.mPhysicsWorld.registerPhysicsConnector(mPhysicsConnector);
   }
   
   
   /**
	 * 游戏准备时间
	 */
	private void prepareGame(){

		this.bcmScene.registerUpdateHandler(new TimerHandler(1.0f, new ITimerCallback() {
			
			@Override
			public void onTimePassed(TimerHandler arg0) {
				// TODO Auto-generated method stub
				bcmScene.unregisterUpdateHandler(arg0);
				GameStatus.mGameRunning = true;
				GameStatus.heroMoveing = false;
			}
		}));
	}
	
	/*
	 * 游戏开始计时器开始计时
	 */
	private void startCountDownTimer(){
		totalTime = loadMapManger.getMaptime();
	   mCountDownTimer = new CountDownTimer(totalTime, 1000) {
		
		@Override
		public void onTick(long millisUntilFinished) {
			// TODO Auto-generated method stub
		    gameSourceManager.gameTimeText.setText(millisUntilFinished/1000+"");
		    if(millisUntilFinished / 1000 < 10){
//		      gameSourceManager.gameTimeText.setColor(255,0,0);          //游戏时间提示
		    }
		}
		
		@Override
		public void onFinish() {
			// TODO Auto-generated method stub
			
		}
	}.start();			
}
    
	
	@Override
	public void logic(float SecondsElapsed) {
		// TODO Auto-generated method stub
         if(GameStatus.mGameRunning){
        	 
        	 if(GameStatus.heroMoveing){
        		 gameSourceManager.promptSprite.setVisible(false);
        	 }
        	 switch(GameStatus.STATE){
        	 case GameStatus.PRESS:
        		if(btAflag){
  			      swapInHashMap();
  			      GameStatus.STATE = GameStatus.NORMAL;
        		}else{
        			if(isDown){
        		       MoveBox(); 
        		       isDown = false;
        			}
        		}
        		 break;
        	 case GameStatus.CHECK:
				  removeHorizontal();
				  removeVrtical();
				  refreshCheck();
        		 break;
        	 case GameStatus.FALL:
        		  refreshScale();
        		  if(winscore <= totalScore){
        			  this.displayAllBox();
        		  }
        		  GameStatus.STATE = GameStatus.NORMAL;
        		 break;
             default :
            	 break;
        	 }
         }
	}
    
	
	//在线程之外发送消息句柄
	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
//			super.handleMessage(msg);
			switch (msg.what) {
			case 1:                        //自动提示
				doTips();
				break;

			default:
				break;
			}
		}	
	};
	
	
	
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


	

	
	/**
	 * 初始化盒子
	 */
	private void initJewels(){			
		this.mHashMap = new HashMap<String, MyBox>();
		
		//初始化哈希表
		for(int i = 0 ; i < mapArrayList.MapW[MapIndex] ; i ++)
		 for(int j = 0 ;j < mapArrayList.MapH[MapIndex] ; j ++)
		 {   
		     int boxWidth = mapArrayList.MapBoxWidth[MapIndex];
		     int boxHeight = mapArrayList.MapBoxHeight[MapIndex]; 
		     int boxStartX = mapArrayList.MapStartX[MapIndex];
		     int boxStartY = mapArrayList.MapStartY[MapIndex];
			 String key1 = getKey(i * boxWidth + boxStartX, j * boxHeight + boxStartY);
			 mHashMap.put(key1, null);
		 }

		//初始化盒子
		for(int i = 0 ;i < map.boxXYs.size();i ++)
		{   
			 int boxX = map.boxXYs.get(i).boxX;
		     int boxY = map.boxXYs.get(i).boxY;
			 String key = getKey(boxX,boxY);
			 if(map.boxXYs.get(i).haveBody)
			 {  
				 MyBox value = getRandomBox(boxX,boxY);
				 while(checkHorizontal(value).size() >= 3 || checkVertical(value).size() >= 3){
						value = getRandomBox(boxX,boxY);
					}	
				 value.setMyBox2(gameSourceManager.boxITextureRegions[value.getStyle()],engine,mPhysicsWorld,this,context);
				 value.setBoxBody(map.boxXYs.get(i).boxBody);
	             mHashMap.put(key, value);
			     this.getChildByIndex(LAYER_MENU).attachChild(mHashMap.get(key).getBoxSprite());
			 }
		}
		
	}
	
	
	/**
	 * 随机获取获取一个盒子
	 */
	public MyBox getRandomBox(final int X,final int Y){
		int style = MathUtils.random(0, 6);
		MyBox mybox = new MyBox(X, Y);
		mybox.setStyle(style);
		return mybox;
	}
	
	/**
	 * 行/列转换成HashMap中到key
	 * @return  HashMap的key
	 */
	private String getKey(final int X, final int Y){
		return String.valueOf(X) + String.valueOf(Y);
	}	
	
	
	//按下A触发的事件
	public void BTA_Event()
	{
		gameSourceManager.btAButtonSprite.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(ButtonSprite arg0, float arg1, float arg2) {
				// TODO Auto-generated method stub
			isDown = true;
			if(GameStatus.STATE == GameStatus.PRESS || GameStatus.STATE == GameStatus.FALL || 
				GameStatus.STATE == GameStatus.CHECK || GameStatus.heroMoveing ){
					return ;
			  }
		
		     if(!btAflag){                             //人物身上没有盒子的时候
			   existBox = nearBox();            
			  if(existBox != null){   
				   btAflag = true;
				   GameStatus.STATE = GameStatus.PRESS;	
			  }
			} else{                                  //人物身上有盒子的时候
				  GameStatus.STATE = GameStatus.PRESS;
	         	  btAflag = false;
			}
		     
		  }
		});	
	}
	
	//移除盒子
	private void swapInHashMap()
    {    
	     existBox.removeAnimate();
		 mHashMap.remove(getKey(existBox.getRow(), existBox.getCol()));
		 mHashMap.put(getKey(existBox.getRow(), existBox.getCol()),null);	
	}
	
	
	
	/**
	 * 检测水平方向 水平方向能消去到队列的个数
	 */
	private ArrayList<MyBox> checkHorizontal(final MyBox mybox){
		ArrayList<MyBox> deadArrayList  = new ArrayList<MyBox>();
		int x = mapArrayList.MapBoxWidth[MapIndex];
		if(mybox != null){
		    int curRow = mybox.getRow();
			final int curCol = mybox.getCol();		
			final int curStyle = mybox.getStyle();
			//向左检测
			while((curRow - x) >= mapArrayList.MapStartX[MapIndex]){
				if(mHashMap.get(getKey(curRow - x, curCol)) != null){
					if(curStyle == mHashMap.get(getKey(curRow - x, curCol)).getStyle()){
						deadArrayList.add(mHashMap.get(getKey(curRow - x, curCol)));
					}else {//出现不连续的，跳过去
						curRow = mapArrayList.MapStartX[MapIndex];
					}				
				}
				curRow -= x;
			}
			
			curRow = mybox.getRow();//回来原位重新开始
			deadArrayList.add(mHashMap.get(getKey(curRow, curCol)));
			//向右检测
			while((curRow + x) < mapArrayList.MapWidth[MapIndex]){
				if(mHashMap.get(getKey(curRow + x, curCol)) != null){
					if(curStyle == mHashMap.get(getKey(curRow + x, curCol)).getStyle()){
						deadArrayList.add(mHashMap.get(getKey(curRow + x, curCol)));
					}else {//出现不连续的，跳过去
						curRow = mapArrayList.MapBoxWidth[MapIndex];
					}
				}
				curRow += x;
			}			
		}
		return deadArrayList;
	}
	
	
	/**
	 * 检测垂直方向 垂直方向能消去到队列的个数
	 */
	private ArrayList<MyBox>  checkVertical(final MyBox myBox){		
		ArrayList<MyBox> deadArrayList  = new ArrayList<MyBox>();
		int y = mapArrayList.MapBoxHeight[MapIndex];
		if(myBox != null){
			final int curRow = myBox.getRow();
			int curCol = myBox.getCol();			
			final int curStyle = myBox.getStyle();
			//向上检测
			while((curCol - y) >= 0){
				if(mHashMap.get(getKey(curRow, curCol - y)) != null){
					if(curStyle == mHashMap.get(getKey(curRow, curCol - y)).getStyle()){
						deadArrayList.add(mHashMap.get(getKey(curRow, curCol - y)));
					}else {//出现不连续的，跳过去，检查下侧
						curCol = mapArrayList.MapStartY[MapIndex];
					}
				}
				curCol -= y;
			}
		
			curCol =myBox.getCol();	//回来原位重新开始
			deadArrayList.add(mHashMap.get(getKey(curRow, curCol)));
			while((curCol + y) < mapArrayList.MapHeight[MapIndex]){//向下检测
				if(mHashMap.get(getKey(curRow, curCol + y)) != null){
					if(curStyle == mHashMap.get(getKey(curRow, curCol + y)).getStyle()){
						deadArrayList.add(mHashMap.get(getKey(curRow, curCol + y)));
					}else {//出现不连续的，跳过去
						curCol = mapArrayList.MapHeight[MapIndex];
					}
				}
				curCol += y;
			}
		}
		return deadArrayList;
	}
	
	
	/**
	 * 水平消去盒子
	 */
	private void removeHorizontal(){
		int k = 0;
    	for(int i = 0; i < mapArrayList.MapH[MapIndex]; i++)
    	{
    		for(int j = 0; j < mapArrayList.MapW[MapIndex] - 2; j++)
    		{
    			int startX = mapArrayList.MapStartX[MapIndex];
    			int startY = mapArrayList.MapStartY[MapIndex];
    			int width = mapArrayList.MapBoxWidth[MapIndex];
    			int height = mapArrayList.MapBoxHeight[MapIndex];  
    			if(mHashMap.get(getKey(j * width + startX, i * height + startY)) != null){
    			if(mHashMap.get(getKey(j * width + startX, i * height + startY)).getState() == STATE_NORMAL){
        			for(k = 1; j + k < mapArrayList.MapW[MapIndex] && mHashMap.get(getKey(j * width + startX + k * width, i * height + startY)) != null; k++){
        			 if(!(mHashMap.get(getKey(j * width + startX, i * height + startY)).getStyle() == mHashMap.get(getKey(j * width + startX + k * width, i * height + startY)).getStyle()
  				   			&&  mHashMap.get(getKey(j * width + startX, i * height + startY)).getState() == mHashMap.get(getKey(j * width + startX + k * width, i * height + startY)).getState())){
        			      break;
        		 		}
        			 }
        			
        			 if(k >= 3){
    		 			removeVrtical();       //这样调T(十)字都可以消
    		 			for(int n = 0; n < k; n++){
    		 				mHashMap.get(getKey((j++) * width + startX, i * height + startY)).setState(STATE_SCALEINT);
    		 			}
        			}
    			}
    		}
    	  }
    	}
	}
	
	
	
	/**
	 * 垂直消去盒子
	 */
	private void removeVrtical(){
		int k = 0;
    	for(int i = 0; i < mapArrayList.MapW[MapIndex]; i++)
    	{
    		int startX = mapArrayList.MapStartX[MapIndex];
			int startY = mapArrayList.MapStartY[MapIndex];
			int width = mapArrayList.MapBoxWidth[MapIndex];
			int height = mapArrayList.MapBoxHeight[MapIndex];
			
    		for(int j = 0; j < mapArrayList.MapH[MapIndex] - 2; j++)
    		{
    		  if(mHashMap.get(getKey(i * width + startX, j * height + startY)) != null){
    			if(mHashMap.get(getKey(i * width + startX, j * height + startY)).getState() == STATE_NORMAL){
        			for(k = 1; j + k < mapArrayList.MapH[MapIndex] && mHashMap.get(getKey(i * width + startX, (j + k) * height + startY)) != null ; k++)
        			 if(!(mHashMap.get(getKey(i * width + startX, j * height + startY)).getState() == mHashMap.get(getKey(i * width + startX, (j + k) * height + startY)).getState()
        			  && mHashMap.get(getKey(i * width + startX, j * height + startY)).getStyle() == mHashMap.get(getKey(i * width + startX, (j + k) * height + startY)).getStyle())){
        				 break;
        			 }
        			
		 			if(k >= 3){
		 				for(int n = 0; n < k; n++){
		 					mHashMap.get(getKey(i * width + startX, (j++) * height + startY)).setState(STATE_SCALEINT);
		 				}
		 			}
    			}
    		}
    	  }
    	}
	}
	
	
	
	//盒子消去
	private void refreshScale()
	{ 
		gameSourceManager.xiaoquSound.play();
		for(int i = 0 ; i < mapArrayList.MapW[MapIndex] ; i ++)
 		 for(int j = 0; j < mapArrayList.MapH[MapIndex] ; j ++)
		 {
			 int x = mapArrayList.MapStartX[MapIndex] + i * mapArrayList.MapBoxWidth[MapIndex];
			 int y = mapArrayList.MapStartY[MapIndex] + j * mapArrayList.MapBoxHeight[MapIndex];
			 if(mHashMap.get(getKey(x, y)) != null){
				 
				 if(mHashMap.get(getKey(x, y)).getState() == STATE_SCALEINT){
				        mHashMap.get(getKey(x, y)).deleteBoxAnimate();
					    mHashMap.remove(getKey(x, y));
					    mHashMap.put(getKey(x, y),null);
				 }
			 }
		 }
	    gameSourceManager.gamescoreText.setText("" + totalScore);                    //计算分数
	}
	
	
	
	//盒子消去检测数量
	private void refreshCheck()
	{    
		int checknum  = 0;
		for(int i = 0 ; i < mapArrayList.MapW[MapIndex] ; i ++)
 		 for(int j = 0; j < mapArrayList.MapH[MapIndex] ; j ++)
		 {
			 int x = mapArrayList.MapStartX[MapIndex] + i * mapArrayList.MapBoxWidth[MapIndex];
			 int y = mapArrayList.MapStartY[MapIndex] + j * mapArrayList.MapBoxHeight[MapIndex];
			 if(mHashMap.get(getKey(x, y)) != null){
				 if(mHashMap.get(getKey(x, y)).getState() == STATE_SCALEINT ){
				     checknum ++;
				 }
			 }
		 }
	   if(checknum > 0 ){
		   gameScore(checknum);                              //计算分数
		   GameStatus.STATE = GameStatus.FALL;
	   }else{
		   GameStatus.STATE = GameStatus.NORMAL;
	   }
	   
	}
	
	
	
	//获取主角所面对的盒子
	public MyBox nearBox()
	{
		float HeroX = gameSourceManager.hero_oneAnimatedSprite.getX();
		float HeroY = gameSourceManager.hero_oneAnimatedSprite.getY();
        MyBox nearBox = null;
    	int direction = gamemanger.getHerodirection();
    	boxXY temp = mapArrayList.MapboxDire(HeroX, HeroY, direction,MapIndex);
        if(temp != null)
    	 {
        	nearBox = mHashMap.get(getKey(temp.boxX, temp.boxY));
            Log.d("boxx + boxy",temp.boxX + " " + temp.boxY);
    	 }
    	Log.d("herox + heroy", (int)HeroX + " " + (int)HeroY);
		return nearBox;
	}
	
	
	
	//盒子放置到能够放置的位置
	public void MoveBox()
	{
		float HeroX = gameSourceManager.hero_oneAnimatedSprite.getX();
		float HeroY = gameSourceManager.hero_oneAnimatedSprite.getY();
    	int direction = gamemanger.getHerodirection();
    	boxXY temp = mapArrayList.MapboxDire(HeroX, HeroY, direction,MapIndex);
        if(temp != null){
        	MyBox boxs = mHashMap.get(getKey(temp.boxX, temp.boxY));
        	if(boxs == null && map.IsBoxLegal(temp.boxX, temp.boxY) && existBox != null){
        	  int style = existBox.getStyle();
        	   MyBox mybox = new MyBox(temp.boxX,temp.boxY);
        	   mybox.setStyle(style);
        	   mybox.setMyBox(gameSourceManager.boxITextureRegions[style],engine,mPhysicsWorld,this,context);
        	   mHashMap.remove(getKey(temp.boxX, temp.boxY));
        	   mHashMap.put(getKey(temp.boxX, temp.boxY), mybox);
			   this.getChildByIndex(LAYER_MENU).attachChild(mybox.getBoxSprite());
			   mybox.brithAnimate();                                                   //盒子消失动画
			   existBox = null; 
        	}
       
        }
	}
	
	
	
	/**
	 * 盒子放置和搬动提示
	 */
	private void autoTips(){		
		bcmScene.registerUpdateHandler(new TimerHandler(1f, true, new ITimerCallback() {			
			@Override
			public void onTimePassed(TimerHandler pTimerHandler) {
				if(GameStatus.mGameRunning){
					if(!GameStatus.heroMoveing && GameStatus.STATE == GameStatus.NORMAL){
						mTime ++;
						if(mTime >= 5){
							//提示
							Message msg = new Message();
							msg.what = 1;
							handler.sendMessage(msg);
							mTime = 0;
						}
					}else{
						mTime = 0;
					}
				}
			}
		}));
	}
	
	
	/**
	 * 执行盒子放置和搬起提示
	 */
	private void doTips(){
	   
		float HeroX = gameSourceManager.hero_oneAnimatedSprite.getX();
		float HeroY = gameSourceManager.hero_oneAnimatedSprite.getY();
    	int direction = gamemanger.getHerodirection();
    	boxXY temp = mapArrayList.MapboxDire(HeroX, HeroY, direction,MapIndex);
         if(temp != null){
        	 if(mHashMap.get(getKey(temp.boxX, temp.boxY)) == null && existBox != null && map.IsBoxLegal(temp.boxX, temp.boxY)){
			gameSourceManager.promptSprite.setPosition(temp.boxX + 5,temp.boxY);
			gameSourceManager.promptSprite.setVisible(true);
			gameSourceManager.promptSprite.registerEntityModifier(new SequenceEntityModifier(
					new MoveYModifier(1.0f, temp.boxY - 8, temp.boxY - 2), new MoveYModifier(1.0f,temp.boxY - 2, temp.boxY - 8)));
         } else if((mHashMap.get(getKey(temp.boxX, temp.boxY)) != null && existBox == null)){
        	   mHashMap.get(getKey(temp.boxX, temp.boxY)).boxPrompt();
         }
       }
	}
	
	//盒子记分方式   
	private void gameScore(int num){
	    totalScore += 30;
	    num -= 3;
	    for(int i = 1 ;i <= num ; i ++){
	    	totalScore += 10*i;
	    }
	}
	
	
	//结束时消灭所有的box
	private void displayAllBox(){
		for(int i = 0 ; i < mapArrayList.MapW[MapIndex] ; i ++)
 		 for(int j = 0; j < mapArrayList.MapH[MapIndex] ; j ++)
		 {
			 int x = mapArrayList.MapStartX[MapIndex] + i * mapArrayList.MapBoxWidth[MapIndex];
			 int y = mapArrayList.MapStartY[MapIndex] + j * mapArrayList.MapBoxHeight[MapIndex];
			 if(mHashMap.get(getKey(x, y)) != null){
					    mHashMap.get(getKey(x, y)).deleteBoxAnimate();
					    mHashMap.remove(getKey(x, y));
					    mHashMap.put(getKey(x, y),null);
			 }
		 }
	}
	

	
	
}

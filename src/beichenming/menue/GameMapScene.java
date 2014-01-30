package beichenming.menue;

import org.andengine.engine.camera.SmoothCamera;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.LoopEntityModifier.ILoopEntityModifierListener;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;
import org.andengine.util.adt.bounds.IFloatBounds;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.LoopModifier;

import android.R.integer;
import android.opengl.GLES20;
import android.text.StaticLayout;
import android.util.Log;
import beichenming.manger.Gamemanger;
import beichenming.manger.LoadMapManger;
import beichenming.manger.MapArrayList;
import beichenming.manger.Sourcemanger;
/*
 * ��ͼѡ��scene
 */
import beichenming.pocketmonster.LoadGame;
public class GameMapScene extends MyScene{
	//������Ļ�߶ȺͿ��
	private static final int CAMERA_WIDTH = 480;
	private static final int CAMERA_HEIGHT = 320;
	public static Sourcemanger gameSourceManager = Sourcemanger.getInstance();
    private static Gamemanger gamemanger = Gamemanger.getInstance();
	protected static final int LAYER_BGROUND = 0;                     //����ͼ��one
	protected static final int LAYER_MENU = LAYER_BGROUND + 1;        //����ͼ��two
	private static final int LAYER_MAP = LAYER_MENU + 1;              //����ͼ��three
     
	protected GameMapScene bcmScene;
	protected SmoothCamera bcmSmoothCamera;
	protected GameLogo bcmGameLogo;
	
	private LoadGame loadGame;                                      //������Ϸ
	
	private int mapIndexNum = 0;                                   // ��ͼ�������
	
    private int mapType = 0;                                         //��ͼĿ¼
    
    private int mapPanDuan;                                        //�ж�������ͨ�����������ⳡ��
    
    
	
	public GameMapScene(GameLogo activity,int index) {
		super(activity);
		// TODO Auto-generated constructor stub
		this.bcmScene = this;
		this.bcmSmoothCamera = activity.myCamera;
		this.bcmGameLogo = activity;
		if(index == 0)
		{
			mapPanDuan = 8;
		}
		if(index == 1)
		{
			mapPanDuan = 2;
		}
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		//��ʼ��������Դ
        gameSourceManager.loadGameGB_threeTexture(engine, context);
        gameSourceManager.loadGameGB_threeSprite(engine, context, bcmSmoothCamera);
        loadGame = new LoadGame(engine,context);
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub
		this.getChildByIndex(LAYER_BGROUND).attachChild(gameSourceManager.selectMapBgSprite);
		this.getChildByIndex(LAYER_MENU).attachChild(gameSourceManager.btHomeSprite);
		this.getChildByIndex(LAYER_MENU).attachChild(gameSourceManager.selectMapRightSprite);
		this.getChildByIndex(LAYER_MENU).attachChild(gameSourceManager.selectMapLeftSprite);
		if(mapPanDuan == 8){
		for(int i = 0 ;i < 8 ;i ++){
		 this.getChildByIndex(LAYER_MENU).attachChild(gameSourceManager.navigationSprites[i]);
		}
		this.gameSourceManager.navigationSprites[0].setVisible(true);
		
		for(int i = 0 ;i < 8 ;i ++){
			this.getChildByIndex(LAYER_MENU).attachChild(gameSourceManager.mainMapSprites[i]);
		}
		this.gameSourceManager.mainMapSprites[0].setVisible(true);
		}
		if(mapPanDuan == 2){
			this.getChildByIndex(LAYER_MENU).attachChild(gameSourceManager.mainMapSprites[8]);
			this.getChildByIndex(LAYER_MENU).attachChild(gameSourceManager.mainMapSprites[9]);
			this.gameSourceManager.mainMapSprites[8].setVisible(true);
		}
		this.getChildByIndex(LAYER_MENU).attachChild(gameSourceManager.selectMapText);
		this.initRegisterEntityModifier();
		this.initSetBtEvent();
	}

	@Override
	public void logic(float SecondsElapsed) {
		// TODO Auto-generated method stub
		if(mapIndexNum == 0){
			gameSourceManager.selectMapLeftSprite.setVisible(false);
		}
		if(mapIndexNum == mapPanDuan - 1){
			gameSourceManager.selectMapRightSprite.setVisible(false);
		}
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
	
	
	//��ťע��
	private void initRegisterEntityModifier()
	{
		this.registerTouchArea(gameSourceManager.selectMapLeftSprite);
		this.registerTouchArea(gameSourceManager.selectMapRightSprite);
		this.registerTouchArea(gameSourceManager.btHomeSprite);
		if(mapPanDuan == 8){
		for(int i = 0 ;i < 8 ;i ++){
			this.registerTouchArea(gameSourceManager.mainMapSprites[i]);
		}
	  }
		if(mapPanDuan == 2){
			this.registerTouchArea(gameSourceManager.mainMapSprites[8]);
			this.registerTouchArea(gameSourceManager.mainMapSprites[9]);
		}
	}
	
	
	//��ť�¼�
	private void initSetBtEvent(){
	    
		//��ť
		gameSourceManager.selectMapLeftSprite.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(ButtonSprite arg0, float arg1, float arg2) {
				// TODO Auto-generated method stub
				if(mapPanDuan == 8){
				if(mapIndexNum > 0){
					if(!gameSourceManager.selectMapRightSprite.isVisible()){
					   gameSourceManager.selectMapRightSprite.setVisible(true);
					}
					setMainMap_left(gameSourceManager.mainMapSprites[mapIndexNum], gameSourceManager.mainMapSprites[mapIndexNum - 1]);
				    gameSourceManager.navigationSprites[mapIndexNum].setVisible(false);
					mapIndexNum--;
					gameSourceManager.navigationSprites[mapIndexNum].setVisible(true);
				}
			 }
				
			 if(mapPanDuan == 2){
					if(mapIndexNum > 0){
						if(!gameSourceManager.selectMapRightSprite.isVisible()){
						   gameSourceManager.selectMapRightSprite.setVisible(true);
						}
						setMainMap_left(gameSourceManager.mainMapSprites[mapIndexNum + 8], gameSourceManager.mainMapSprites[mapIndexNum - 1 + 8]);
						mapIndexNum--;
					}
			 }
			}
		});
		
		
		//�Ұ�ť
		gameSourceManager.selectMapRightSprite.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(ButtonSprite arg0, float arg1, float arg2) {
				// TODO Auto-generated method stub
				if(mapPanDuan == 8){
				if(mapIndexNum < 7){
				  if(!gameSourceManager.selectMapLeftSprite.isVisible()){
					  gameSourceManager.selectMapLeftSprite.setVisible(true);
				  }
				  setMainMap_right(gameSourceManager.mainMapSprites[mapIndexNum], gameSourceManager.mainMapSprites[mapIndexNum + 1]);
			      gameSourceManager.navigationSprites[mapIndexNum].setVisible(false);
				  mapIndexNum++;
				  gameSourceManager.navigationSprites[mapIndexNum].setVisible(true);
				}
			}
				if(mapPanDuan == 2){
					if(mapIndexNum < 1){
						  if(!gameSourceManager.selectMapLeftSprite.isVisible()){
							  gameSourceManager.selectMapLeftSprite.setVisible(true);
						  }
						  setMainMap_right(gameSourceManager.mainMapSprites[mapIndexNum + 8], gameSourceManager.mainMapSprites[mapIndexNum + 1 + 8]);
						  mapIndexNum++;
						}
				}
		 }
		});
		
		
		//home��ť
		gameSourceManager.btHomeSprite.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(ButtonSprite arg0, float arg1, float arg2) {
				// TODO Auto-generated method stub
			    
			}
		});
		
		//���ص�ͼ
	for(int dd = 0 ;dd < MapArrayList.MapAllnum ;dd ++){
		gameSourceManager.mainMapSprites[dd].setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(ButtonSprite arg0, float arg1, float arg2) {
				// TODO Auto-generated method stub
				for(int i = 0 ;i < MapArrayList.MapAllnum ; i++){
					if(arg0 == gameSourceManager.mainMapSprites[i]){
						mapType = i;
					}
				}
				bcmGameLogo.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						GameChildMapScene gameChildMapScene = new GameChildMapScene(bcmGameLogo,mapType); 
						bcmScene.setChildScene(gameChildMapScene,false,true,true);
						loadGame.loadGame(gameChildMapScene);
					}
				});	
			}
		});
	}	
		
		
	}
	
	
	   //��ͼЧ������Ч���ұ�
    public void setMainMap_right(final Sprite mySprite_one,final Sprite mySprite_two)
    {

    	   LoopEntityModifier entityModifier = new LoopEntityModifier(new IEntityModifierListener() {
				
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
					// TODO Auto-generated method stub
					 mySprite_two.setVisible(true);
					  mySprite_two.registerEntityModifier(new SequenceEntityModifier(
							  new ParallelEntityModifier(
									new MoveXModifier(0.1f, CAMERA_WIDTH,CAMERA_WIDTH/2 - mySprite_two.getWidth()/2)
//                                    new AlphaModifier(0.5f, 0.0f,1.0f)
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
					new MoveXModifier(0.1f, CAMERA_WIDTH/2 - mySprite_one.getWidth()/2,-mySprite_one.getWidth())
//					 new AlphaModifier(0.5f, 1.0f,0.0f)
					)));
    	 
    	   mySprite_one.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);  
    	   mySprite_one.registerEntityModifier(entityModifier);
    	   gameSourceManager.touchSound.play();
    }
    
    
    //��ͼ����Ч�����
    public void setMainMap_left(final Sprite mySprite_one,final Sprite mySprite_two)
    {

    	  LoopEntityModifier entityModifier = new LoopEntityModifier(new IEntityModifierListener() {
				
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
					// TODO Auto-generated method stub
					  mySprite_two.setVisible(true);
					  mySprite_two.registerEntityModifier(new SequenceEntityModifier(
							  new ParallelEntityModifier(
					 new MoveXModifier(0.1f, -mySprite_one.getWidth() , CAMERA_WIDTH/2 - mySprite_one.getWidth()/2)
//	    			 new AlphaModifier(0.5f, 0.0f,1.0f)
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
					 new MoveXModifier(0.1f, CAMERA_WIDTH/2 - mySprite_two.getWidth()/2,CAMERA_WIDTH)
//	    			new AlphaModifier(0.5f, 1.0f,0.0f)
	    			)));
    	 
    	   mySprite_one.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);  
    	   mySprite_one.registerEntityModifier(entityModifier);
    	   gameSourceManager.touchSound.play();
    }
    
	

}

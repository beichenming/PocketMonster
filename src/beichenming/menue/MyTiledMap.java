package beichenming.menue;

import java.util.ArrayList;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.SmoothCamera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
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
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.BuildableTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.debug.Debug;
import org.andengine.util.math.MathUtils;
import org.apache.commons.logging.Log;
import org.xml.sax.Attributes;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

import android.app.Activity;
import beichenming.manger.Gamemanger;
import beichenming.manger.LoadMapManger;
import beichenming.manger.MapArrayList;
import beichenming.manger.Sourcemanger;
import beichenming.manger.TextSource;
import beichenming.manger.boxXY;

public class MyTiledMap {
     
	protected static final int LAYER_BGROUND = 0;    //背景图层one
	protected static final int LAYER_MENU = LAYER_BGROUND + 1;  //背景图层two
	private static final int LAYER_MAN = LAYER_MENU + 1;
	private static final int LAYER_TREE = LAYER_MAN + 1;
	private static final int LAYER_SCORE = LAYER_TREE + 1;	
    private static Gamemanger gamemanger = Gamemanger.getInstance();
    public static Sourcemanger gameSourceManager = Sourcemanger.getInstance();
	public TMXTiledMap mapTiledMap;                            //游戏地图
	public TMXTiledMap mapTiledMapf;                           //副本地图
	private GameScene bcmScene;
	private Activity bcmActivity;
	private SmoothCamera bcmSmoothCamera;
	private Engine bcmEngine;
	private PhysicsWorld bcmPhysicsWorld;                      //创建物理世界
	//人物初始化坐标
	public float playerX; 
	public float playerY;
    private BuildableBitmapTextureAtlas bg1BuildableBitmapTextureAtlas,bg2BuildableBitmapTextureAtlas;   // 二层背景
    private ITextureRegion bg1ITextureRegion,bg2ITextureRegion;                             
    private Sprite bg1Sprite,bg2Sprite;
	public ArrayList<boxXY> boxXYs = new ArrayList<boxXY>();     //可存放砖块的位置坐标记录
	public int MapIndex ;                                    //地图索引
	public String maptildString ;
	public String mapBgNameoneString ;
	public String mapBgNametwoString ;
	
	public MyTiledMap(GameScene scene,GameLogo activity,PhysicsWorld mPhysicsWorld,LoadMapManger loadMapManger)
	{   
		this.MapIndex = loadMapManger.getMapTypeNum();
		this.maptildString = loadMapManger.getMapNametiled();
		this.mapBgNameoneString = loadMapManger.getMapBg_one();
		this.mapBgNametwoString = loadMapManger.getMapBg_two();
		this.bcmScene = scene;
		this.bcmActivity = activity;
		this.bcmSmoothCamera = activity.myCamera;
		this.bcmEngine = activity.getEngine();
		this.bcmPhysicsWorld = mPhysicsWorld;
		this.loadTiledMap();
		this.readMapObjects(mapTiledMap);
		gamemanger.mapWidth = MapArrayList.MapWidthAll[MapIndex];
		gamemanger.mapHeight =  MapArrayList.MapHeightAll[MapIndex];
		bcmSmoothCamera.setBounds(0,0,gamemanger.mapWidth,gamemanger.mapHeight);     //设置屏幕最大移动范围
		this.setBackGround();
      
	}
	
		
	 /*
	  * 加载二层背景
	  */
	private void setBackGround()
	{   
		this.bg2BuildableBitmapTextureAtlas = new BuildableBitmapTextureAtlas(bcmEngine.getTextureManager(), MapArrayList.MapWidthAll[MapIndex],MapArrayList.MapHeightAll[MapIndex],TextureOptions.BILINEAR);
		this.bg2ITextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bg2BuildableBitmapTextureAtlas,bcmActivity.getAssets(),mapBgNameoneString);
		try {
			bg2BuildableBitmapTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 0));
		} catch (TextureAtlasBuilderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.bg2BuildableBitmapTextureAtlas.load();
		this.bg2Sprite = new Sprite(0, 0,this.bg2ITextureRegion, bcmEngine.getVertexBufferObjectManager());
		bcmScene.getChildByIndex(LAYER_BGROUND).attachChild(bg2Sprite);
		
		this.bg1BuildableBitmapTextureAtlas = new BuildableBitmapTextureAtlas(bcmEngine.getTextureManager(), MapArrayList.MapWidthAll[MapIndex],MapArrayList.MapHeightAll[MapIndex],TextureOptions.BILINEAR);
		this.bg1ITextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bg1BuildableBitmapTextureAtlas,bcmActivity.getAssets(),mapBgNametwoString);
		try {
			bg1BuildableBitmapTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 0));
		} catch (TextureAtlasBuilderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.bg1BuildableBitmapTextureAtlas.load();
		this.bg1Sprite = new Sprite(0, 0,this.bg1ITextureRegion, bcmEngine.getVertexBufferObjectManager());
		bcmScene.getChildByIndex(LAYER_TREE).attachChild(bg1Sprite);
	}
	
	
	
	  //加载地图
	   private void loadTiledMap()
	   {
		   //加载地图
			try {
				final TMXLoader tmxLoader = new TMXLoader(bcmActivity.getAssets(), bcmEngine.getTextureManager(),TextureOptions.BILINEAR_PREMULTIPLYALPHA,bcmEngine.getVertexBufferObjectManager(),new ITMXTilePropertiesListener() {
						
						@Override
						public void onTMXTileWithPropertiesCreated(TMXTiledMap pTMXTiledMap,TMXLayer pTMXLayer, TMXTile pTMXTile,TMXProperties<TMXTileProperty> pTMXTileProperties) {
							// TODO Auto-generated method stub
						
						}
					});
					this.mapTiledMap = tmxLoader.loadFromAsset(maptildString);
				} catch (final TMXLoadException tmxle) {
					Debug.e(tmxle);
				}
		  
//				for (int i = 0; i < this.mapTiledMap.getTMXLayers().size(); i++)
//				{
//					TMXLayer layer = this.mapTiledMap.getTMXLayers().get(i);
//						bcmScene.getChildByIndex(LAYER_BGROUND).attachChild(layer);
//				}
	   }
	   
	   
	   //获取地图属性
	   private void readMapObjects(TMXTiledMap map) 
	   {
			
			for (final TMXObjectGroup group : this.mapTiledMap.getTMXObjectGroups()) 
			{	
				if (group.getTMXObjectGroupProperties().containsTMXProperty("walk","no"))
				{
					for (final TMXObject object : group.getTMXObjects()) 
					{
					    Rectangle wallRect = new Rectangle(object.getX(),object.getY(), object.getWidth(),object.getHeight(),bcmEngine.getVertexBufferObjectManager());
						final FixtureDef boxFixtureDef = PhysicsFactory.createFixtureDef(0, 0, 1f);
						PhysicsFactory.createBoxBody(bcmPhysicsWorld,wallRect,BodyType.StaticBody, boxFixtureDef);
//						wallRect.setVisible(false);
//						bcmScene.getChildByIndex(LAYER_MENU).attachChild(wallRect);
					}
				}
				
				if (group.getTMXObjectGroupProperties().containsTMXProperty("player", "yes")) 
				{ 
					// This is our "wall" layer. Create the boxes from it
					for (final TMXObject object : group.getTMXObjects()) 
					{
						playerX = object.getX();
						playerY = object.getY();
//						Log.d("x y", object.getX() + " " + object.getY());
						break;
					}
				}
			
				if (group.getTMXObjectGroupProperties().containsTMXProperty("box", "yes")) 
				{ 
					for (final TMXObject object : group.getTMXObjects()) 
					{
							boxXY myboxXy = new boxXY();
							myboxXy.boxX = object.getX();
							myboxXy.boxY = object.getY();
							boxXYs.add(myboxXy);
							int style =  MathUtils.random(1,5);
							myboxXy.haveBody = false;
							if(style % 2 == 1){
								 Rectangle wallRect = new Rectangle(object.getX(),object.getY(), object.getWidth(),object.getHeight(),bcmEngine.getVertexBufferObjectManager());
								 final FixtureDef boxFixtureDef = PhysicsFactory.createFixtureDef(0, 0.5f, 1f);
								 myboxXy.boxBody = PhysicsFactory.createBoxBody(bcmPhysicsWorld,wallRect,BodyType.StaticBody, boxFixtureDef);
								 myboxXy.haveBody = true;
							}
					}
				}
				
			}
			
		}
	   
	   /*
	    * 判断box下落位置的合法性
	    */
	   public boolean IsBoxLegal(final int x,final int y)
	   {
		   for(int i = 0 ;i < boxXYs.size() ; i ++){
			   if(x == boxXYs.get(i).boxX && y == boxXYs.get(i).boxY){
				   return true;
			   }
		   }
		   return false;
	   }
	   
}

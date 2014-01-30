package beichenming.manger;


import java.io.IOException;

import javax.microedition.khronos.opengles.GL10;
import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.music.MusicManager;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.audio.sound.SoundManager;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.SmoothCamera;
import org.andengine.engine.camera.hud.controls.AnalogOnScreenControl;
import org.andengine.engine.camera.hud.controls.AnalogOnScreenControl.IAnalogOnScreenControlListener;
import org.andengine.engine.camera.hud.controls.BaseOnScreenControl;
import org.andengine.engine.camera.hud.controls.BaseOnScreenControl.IOnScreenControlListener;
import org.andengine.engine.camera.hud.controls.DigitalOnScreenControl;
import org.andengine.engine.handler.physics.PhysicsHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.LoopEntityModifier.ILoopEntityModifierListener;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.AnimatedSprite.IAnimationListener;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TickerText;
import org.andengine.entity.text.TickerText.TickerTextOptions;
import org.andengine.entity.util.FPSLogger;
import org.andengine.extension.physics.box2d.FixedStepPhysicsWorld;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.extension.tmx.TMXLayer;
import org.andengine.extension.tmx.TMXLoader;
import org.andengine.extension.tmx.TMXLoader.ITMXTilePropertiesListener;
import org.andengine.extension.tmx.TMXProperties;
import org.andengine.extension.tmx.TMXTile;
import org.andengine.extension.tmx.TMXTileProperty;
import org.andengine.extension.tmx.TMXTiledMap;
import org.andengine.extension.tmx.util.exception.TMXLoadException;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.font.FontManager;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.bitmap.BitmapTextureFormat;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.util.HorizontalAlign;
import org.andengine.util.color.Color;
import org.andengine.util.debug.Debug;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.LoopModifier;

import com.badlogic.gdx.math.Vector2;



import beichenming.menue.GameLogo;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.hardware.SensorManager;
import android.opengl.GLES20;
import android.renderscript.Type;
import android.text.StaticLayout;
import android.util.Log;
import android.widget.Toast;

public class Sourcemanger {
    
	public static Sourcemanger INSTANCE;
	
	//字体内容初始化
	public static TextSource textSource  = new TextSource();
	
	public Music logo_Music;     //游戏进入logo背景音乐
    public Music bg_oneMusic;   //进入push start时的背景音乐
    public Music bg_twoMusic;   //进入菜单时的背景音乐
    public Music keyboardMusic; //游戏介绍背景音乐
    
	public Sound touchSound ;   //按键声音
	public Sound xiaoquSound;   //消去声音
	
	public Font pushstartFont; //进入游戏push start
	public Font gamestartFont; //游戏开始字体
	public Font renwuchooseFont; //人物选择介绍
	public Font introductionFont_one;  //游戏内容介绍1
	public Font introductionFont_two;   //游戏内容介绍2
	public Font boygirlintroductionFont; //游戏人物介绍
	public Font gameTimeScoreFont;           //游戏时间字体
	public Font selectMapFont;                //选择关卡
	public Font gameMapNameFont;            //地图名字
	
    
	//游戏游戏UI
  	public BuildableBitmapTextureAtlas ui_oneBuildableBitmapTextureAtlas;
  	public ITextureRegion ui_oneITextureRegion;
  	public Sprite ui_oneSprite;
  	
	
	
	//音乐和音效按钮
	public BuildableBitmapTextureAtlas musicSoundBuildableBitmapTextureAtlas;
	public ITiledTextureRegion musicITiledTextureRegionOn,musicITiledTextureRegionOff,soundITiledTextureRegionOn,soundITiledTextureRegionOff;
	public ButtonSprite musicButtonSpriteOn, musicButtonSpriteOff,soundButtonSpriteOn,soundButtonSpriteOff;
	public Text musicText,soundText;
	
	
	
	//定义屏幕高度和宽度
	private static final int CAMERA_WIDTH = 480;
	private static final int CAMERA_HEIGHT = 320;
	
	
	//游戏手柄控制器
	public DigitalOnScreenControl mDigitalOnScreenControl;
	
	//游戏手柄
	public BuildableBitmapTextureAtlas mOnScreenControlTexture;
	public ITextureRegion mOnScreenControlBaseITextureRegion;
	public ITextureRegion mOnScreenControlKnobITextureRegion;

    //游戏按钮
	public BuildableBitmapTextureAtlas mOnScreenBTextureAtlas;
	public ITiledTextureRegion mOnScreenBtAITiledTextureRegion;
	public ITiledTextureRegion mOnScreenBtEITiledTextureRegion;
	public ButtonSprite btAButtonSprite;
	public ButtonSprite btEButtonSprite;
	
	
	
    //定义logo
    public BuildableBitmapTextureAtlas logoBitmapTextureAtlas;
    public TiledTextureRegion logoTiledTextureRegion;
    public AnimatedSprite dynamicBG;
    
    
    //游戏背景图片one
  	public BitmapTextureAtlas mainBitmapTextureAtlas;
  	public ITextureRegion mainITextureRegion;
  	public Sprite bg_oneSprite;
  	public Text pushstartText;
	
  	
	//游戏背景图片two
  	public BitmapTextureAtlas menueBitmapTextureAtlas;
  	public ITextureRegion menueITextureRegion;
  	public Sprite bg_twoSprite;
	
  	
	//游戏背景图片three
  	public BitmapTextureAtlas setBitmapTextureAtlas;
  	public ITextureRegion setITextureRegion;
	public Sprite bg_threeSprite;
	
	
	//游戏背景图片four
  	public BitmapTextureAtlas introductionBitmapTextureAtlas;
  	public ITextureRegion introductionITextureRegion;
	public Sprite bg_fourSprite;
	
  	
	
	//主菜单游戏按钮
	public BuildableBitmapTextureAtlas newGameBitmapTextureAtlas,loadGameBitmapTextureAtlas,moreGameBitmapTextureAtlas;
	public ITiledTextureRegion newGameITiledTextureRegion,loadGameITiledTextureRegion,moreGameITiledTextureRegion;
	public ButtonSprite newGameButtonSprite,loadGameButtonSprite,moreGameButtonSprite;
	public Text mNewGameText,mLoadGameText,mGameMoreText;
	
	
	//游戏设置按钮返回等按钮
	public BuildableBitmapTextureAtlas litleButtonBitmapTextureAtlas;
	public ITiledTextureRegion setGameITiledTextureRegion,backGameITiledTextureRegion;
	public ButtonSprite  setGameButtonSprite,backGameButtonSprite;
	
	
	//进入游戏对话框
	public BitmapTextureAtlas textbox_oneBitmapTextureAtlas ;
	public ITextureRegion textbox_oneITextureRegion ;
	public Sprite text_oneSprite ;
	public Text introductionText_one;
	public Text introductionText_two;
	public Text renwuchoosetText;
	public Text boy_oneintroductionText;
	public Text boy_twointroductionText;
	public Text boy_threeintroductionText;
	public Text boy_fourintroductionText;
	
	
	
	//大木博士logo
	public BuildableBitmapTextureAtlas damuBitmapTextureAtlas;
	public TiledTextureRegion damuTiledTextureRegion;
	public AnimatedSprite damuAnimatedSprite;
	
	
	//游戏人物选择
	public BuildableBitmapTextureAtlas boygirlBitmapTextureAtlas;
	public ITextureRegion boygirl_oneITextureRegion;
	public ITextureRegion boygirl_twoITextureRegion;
	public ITextureRegion boygirl_threeITextureRegion;
	public ITextureRegion boygirl_fourITextureRegion;
	public Sprite boygirl_oneSprite;
	public Sprite boygirl_twoSprite;
	public Sprite boygirl_threeSprite;
	public Sprite boygirl_fourSprite;
	
	
	//向下箭头
	public BuildableBitmapTextureAtlas arrowHeadBitmapTextureAtlas ;
	public ITextureRegion arrowHeadITextureRegion;
	public ButtonSprite arrowHeadSprite;
	
	
	//人物选择确定按钮
	public BuildableBitmapTextureAtlas acceptBtBitmapTextureAtlas ;
	public ITextureRegion acceptBtITextureRegion;
	public ButtonSprite acceptBtSprite;
	
	
	//人物选择按钮
	public BuildableBitmapTextureAtlas selectBuildableBitmapTextureAtlas;
	public ITiledTextureRegion select_leftITiledTextureRegion,select_rightITiledTextureRegion;
  	public ButtonSprite select_leftSprite,select_rightSprite;
	
  	
  	//地图选择背景
  	public BuildableBitmapTextureAtlas selectMapBgBuildableBitmapTextureAtlas;
  	public ITextureRegion  selectMapBgITextureRegion;
  	public Sprite selectMapBgSprite;
  	
  	//地图滑动栏
  	public BuildableBitmapTextureAtlas navigationBuildableBitmapTextureAtlas;
  	public ITextureRegion[] navigationITextureRegion;
  	public Sprite[] navigationSprites;
  	
  	
  	//地图滑动箭头
  	public BuildableBitmapTextureAtlas btMapBuildableBitmapTextureAtlas;
  	public ITextureRegion selectMapLefTextureRegion,selectMapRightITextureRegion;
  	public ButtonSprite selectMapLeftSprite,selectMapRightSprite;
  	
  	//home键
  	public ITextureRegion btHomeITextureRegion;
  	public ButtonSprite btHomeSprite;
  	
  	
  	//关卡选择
  	public Text selectMapText;
  	
  	
  	//目录关卡地图
  	public BuildableBitmapTextureAtlas[] mainMapBuildableBitmapTextureAtlas;
  	public ITextureRegion[] mainMapITextureRegion;
  	public ButtonSprite[] mainMapSprites;
  	
  	
  	
  	//游戏一号主人公
  	public BuildableBitmapTextureAtlas hero_oneBitmapTextureAtlas;
	public TiledTextureRegion hero_oneTiledTextureRegion;
  	public AnimatedSprite hero_oneAnimatedSprite;
  	
  	//游戏box
  	public BuildableBitmapTextureAtlas[] boxBuildBitmapTextureAtlas;
  	public ITextureRegion[] boxITextureRegions;

  	//提示发光
  	public BuildableBitmapTextureAtlas promptBuildableBitmapTextureAtlas;
  	public ITextureRegion promptITextureRegion;
  	public Sprite promptSprite;
  	
  	//人物身上带的盒子
  	public BuildableBitmapTextureAtlas[]  bodyBuildableBitmapTextureAtlas;
  	public ITextureRegion[] bodyITextureRegion;
  	public Sprite[] bodySprites;
  	
  	
  	//游戏特效动画
  	public BuildableBitmapTextureAtlas[] specialAnimationBuildableBitmapTextureAtlas;
  	public TiledTextureRegion[] specialAnimationTiledTextureRegion;
  	public AnimatedSprite[] specialAnimationAnimatedSprite;
     
  	//游戏倒计时时间
  	public Text gameTimeText;
  	public Text gamescoreText;
  	public Text gamemapnameText;
  	
  	//子关卡背景
  	public BuildableBitmapTextureAtlas mapShowBgBuildableBitmapTextureAtlas;
  	public ITextureRegion mapShowBgITextureRegion;
  	public Sprite mapShowBgSprite;
  	
  	
  	//子地图
  	public BuildableBitmapTextureAtlas[] childrenMapBuildableBitmapTextureAtlas;
  	public ITextureRegion[] childrenMapITextureRegion;
  	public ButtonSprite[] childreButtonSprites;
  	
  	
  	
	public synchronized static Sourcemanger getInstance()
	{
		if(INSTANCE == null){
			INSTANCE = new Sourcemanger();
		}
		return INSTANCE;
	}
	
	
	public synchronized void loadGameTextures(Engine engine, Context context,AssetManager assetManager,MusicManager musicManager,SoundManager soundManager,FontManager fontManager,TextureManager textureManager)
	{  
		//游戏资源路径
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		//sound目标路径文件夹
		SoundFactory.setAssetBasePath("sound/");   		
		//music目标路径文件夹
		MusicFactory.setAssetBasePath("music/");
		//font目标路径文件
		FontFactory.setAssetBasePath("font/");
		
		//游戏music初始化
		  try {
				this.logo_Music = MusicFactory.createMusicFromAsset(musicManager, context,TextSource.logo_music);
				this.logo_Music.setLooping(false);
				this.bg_oneMusic = MusicFactory.createMusicFromAsset(musicManager, context, TextSource.bg_onemusic);
				this.bg_oneMusic.setLooping(true);
			 } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}   	
		  
		//游戏sound初始化  
		  try {
				this.touchSound = SoundFactory.createSoundFromAsset(soundManager, context, TextSource.Action_buttonsound);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		   }
		  
		  
		//游戏font初始化
	    this.pushstartFont = FontFactory.createFromAsset(fontManager, textureManager, 128, 32, TextureOptions.BILINEAR,assetManager, TextSource.cambriabFont, 20f, true,org.andengine.util.color.Color.WHITE_ABGR_PACKED_INT);
	    this.pushstartFont.load();
	    
	    
		//游戏logo
		logoBitmapTextureAtlas = new BuildableBitmapTextureAtlas(engine.getTextureManager(), 512, 512,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		logoTiledTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.logoBitmapTextureAtlas, context, TextSource.boylogoPng,1,1);
		try {
			this.logoBitmapTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 1));
		} catch (TextureAtlasBuilderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		engine.getTextureManager().loadTexture(this.logoBitmapTextureAtlas);	
		
		
		//游戏背景one
		this.mainBitmapTextureAtlas = new BitmapTextureAtlas(engine.getTextureManager(), 512, 512,TextureOptions.BILINEAR);
		this.mainITextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mainBitmapTextureAtlas, context,TextSource.bg_onePng,0,0);
		this.mainBitmapTextureAtlas.load();
	  
        
	}
	
	
	public synchronized void loadGameSprite(Engine mEngine,final SmoothCamera smoothCamera,AssetManager assetManager)
	{ 
		//游戏logo动画设置
	    final float centerX = (CAMERA_WIDTH - this.logoTiledTextureRegion.getWidth())/2;
		final float centerY = (CAMERA_HEIGHT - this.logoTiledTextureRegion.getHeight())/2;		
		dynamicBG = new AnimatedSprite(centerX, centerY, this.logoTiledTextureRegion,mEngine.getVertexBufferObjectManager());
		
		//游戏背景one和pushstart字体设置
		float centX1 = 190;
		float centY1 = 280;
		bg_oneSprite = new Sprite(0, 0, this.mainITextureRegion, mEngine.getVertexBufferObjectManager());
	    pushstartText = new Text(centX1,centY1, pushstartFont,textSource.pushStartString,mEngine.getVertexBufferObjectManager());
        
 	  	this.initGamebg_one();
 	  	
	}
	
	
	//释放logo和push start精灵
	public synchronized void unloadGame1()
	{
		this.logoBitmapTextureAtlas.unload();          //logo释放
		this.mainBitmapTextureAtlas.unload();         //背景释放
		this.pushstartFont.unload();                  //push start字体释放
		this.logo_Music.stop();                       //logo背景音乐停止
        this.bg_oneMusic.stop();                      //背景音乐1释放
        this.dynamicBG.detachSelf();                  //logo删除实体
        this.bg_oneSprite.detachSelf();               //背景1删除实体
        this.pushstartText.detachSelf();              //pushstart删除实体
		System.gc();                                  //通知系统进行垃圾回收
	}
	
	
	public synchronized void loadGameTextures1(Engine engine, Context context,AssetManager assetManager,MusicManager musicManager,SoundManager soundManager,FontManager fontManager,TextureManager textureManager)
	{
		//游戏music初始化
		  try {
				this.bg_twoMusic = MusicFactory.createMusicFromAsset(musicManager, context, TextSource.bg_twomusic);
				this.bg_twoMusic.setLooping(true);
			 } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}   	
		  
		    this.gamestartFont = FontFactory.createFromAsset(fontManager, textureManager, 256, 256,TextureOptions.BILINEAR, assetManager, TextSource.hwcyFont,18f,true, org.andengine.util.color.Color.WHITE_ABGR_PACKED_INT);
		    this.gamestartFont.load();
		    
		    
		  //游戏背景two
			this.menueBitmapTextureAtlas =  new BitmapTextureAtlas(engine.getTextureManager(),512, 512,TextureOptions.BILINEAR);
			this.menueITextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.menueBitmapTextureAtlas, context,TextSource.bg_fivePng,0,0);
			this.menueBitmapTextureAtlas.load();
			
			//游戏背景three
			this.setBitmapTextureAtlas = new BitmapTextureAtlas(engine.getTextureManager(), 640, 512,TextureOptions.BILINEAR);
			this.setITextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.setBitmapTextureAtlas, context,TextSource.bg_threePng,0,0);
			this.setBitmapTextureAtlas.load();
			
			
			//主菜单游戏按钮
			this.newGameBitmapTextureAtlas = new BuildableBitmapTextureAtlas(engine.getTextureManager(), 300, 50,TextureOptions.BILINEAR);
			this.newGameITiledTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.newGameBitmapTextureAtlas, assetManager,TextSource.menue_buttonPng, 2, 1);
			this.loadGameBitmapTextureAtlas = new BuildableBitmapTextureAtlas(engine.getTextureManager(), 300, 50,TextureOptions.BILINEAR);
			this.loadGameITiledTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.loadGameBitmapTextureAtlas, assetManager,TextSource.menue_buttonPng, 2, 1);
			this.moreGameBitmapTextureAtlas = new BuildableBitmapTextureAtlas(engine.getTextureManager(), 300, 50,TextureOptions.BILINEAR);
			this.moreGameITiledTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.moreGameBitmapTextureAtlas, assetManager,TextSource.menue_buttonPng, 2, 1);
			        
			try {
				newGameBitmapTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 1));
				loadGameBitmapTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 1));
				moreGameBitmapTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 1));
			} catch (TextureAtlasBuilderException e) {
						// TODO Auto-generated catch block
				e.printStackTrace();
			}
			       
			this.newGameBitmapTextureAtlas.load();
			this.loadGameBitmapTextureAtlas.load();
			this.moreGameBitmapTextureAtlas.load();
			
			//游戏设置按钮
	        this.litleButtonBitmapTextureAtlas = new BuildableBitmapTextureAtlas(engine.getTextureManager(), 500,256,TextureOptions.BILINEAR);
	        this.setGameITiledTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.litleButtonBitmapTextureAtlas, assetManager, TextSource.set_btPng,2,1);
	        this.backGameITiledTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.litleButtonBitmapTextureAtlas,assetManager, TextSource.set_btPng,2,1);
	       
	        
	        try {
				litleButtonBitmapTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 1));
			} catch (TextureAtlasBuilderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        this.litleButtonBitmapTextureAtlas.load();
	        
	        
	        //音效和音乐设置
	        this.musicSoundBuildableBitmapTextureAtlas = new BuildableBitmapTextureAtlas(engine.getTextureManager(),256, 125,TextureOptions.BILINEAR);
	        this.musicITiledTextureRegionOff = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.musicSoundBuildableBitmapTextureAtlas, assetManager,TextSource.bt_offPng,1,1);
	        this.musicITiledTextureRegionOn = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.musicSoundBuildableBitmapTextureAtlas, assetManager, TextSource.bt_onPng,1,1);
			this.soundITiledTextureRegionOff = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.musicSoundBuildableBitmapTextureAtlas, assetManager, TextSource.bt_offPng,1,1);
			this.soundITiledTextureRegionOn = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.musicSoundBuildableBitmapTextureAtlas, assetManager, TextSource.bt_onPng,1,1);
			
			try {
				this.musicSoundBuildableBitmapTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 1));
			} catch (TextureAtlasBuilderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			this.musicSoundBuildableBitmapTextureAtlas.load();
		
		      
	}
	
	
	public synchronized void loadGameSprite1(Engine mEngine,final SmoothCamera smoothCamera,AssetManager assetManager)
	{
		  //游戏背景two和three设置
	    this.bg_twoSprite = new Sprite(0, 0, this.menueITextureRegion, mEngine.getVertexBufferObjectManager());
	    this.bg_threeSprite = new Sprite(0,-CAMERA_HEIGHT, this.setITextureRegion, mEngine.getVertexBufferObjectManager());
	    
	    //游戏菜单设置
	    float centx2 = this.newGameITiledTextureRegion.getWidth();
 	    float centy2 = (CAMERA_HEIGHT - this.newGameITiledTextureRegion.getHeight()*2);  
 	    this.newGameButtonSprite = new ButtonSprite(30,centy2, this.newGameITiledTextureRegion,mEngine.getVertexBufferObjectManager());
   	    this.loadGameButtonSprite = new ButtonSprite(2*centx2 - 20,centy2, this.loadGameITiledTextureRegion,mEngine.getVertexBufferObjectManager());
        this.moreGameButtonSprite = new ButtonSprite(3*centx2 + 30,centy2, this.moreGameITiledTextureRegion,mEngine.getVertexBufferObjectManager());
        this.mNewGameText = new Text(30 + 12, centy2 + 5, gamestartFont,textSource.newGameString, mEngine.getVertexBufferObjectManager());   
        this.mLoadGameText = new Text(2*centx2 - 10 + 6, centy2 + 5, gamestartFont,textSource.loadGameString, mEngine.getVertexBufferObjectManager());   	   
        this.mGameMoreText = new Text(3*centx2 + 30 + 24, centy2 + 5, gamestartFont,textSource.moreGameString, mEngine.getVertexBufferObjectManager());
        this.setGameButtonSprite = new ButtonSprite(20,20, this.setGameITiledTextureRegion,mEngine.getVertexBufferObjectManager());
        this.backGameButtonSprite = new ButtonSprite(20,20-CAMERA_HEIGHT, this.backGameITiledTextureRegion,mEngine.getVertexBufferObjectManager());
 	    
        
        float msX = this.musicITiledTextureRegionOff.getWidth();
        float msY = this.musicITiledTextureRegionOff.getHeight();
        
        this.musicButtonSpriteOff = new ButtonSprite(CAMERA_WIDTH/2 - 3*msX/2, 7*msY/2 - CAMERA_HEIGHT,this.musicITiledTextureRegionOff,mEngine.getVertexBufferObjectManager());
        this.musicButtonSpriteOn = new ButtonSprite(CAMERA_WIDTH/2 - 3*msX/2, 7*msY/2 - CAMERA_HEIGHT,this.musicITiledTextureRegionOn,mEngine.getVertexBufferObjectManager());
        this.soundButtonSpriteOff = new ButtonSprite(CAMERA_WIDTH/2 + msX/2, 7*msY/2 - CAMERA_HEIGHT, this.soundITiledTextureRegionOff, mEngine.getVertexBufferObjectManager());
        this.soundButtonSpriteOn = new ButtonSprite(CAMERA_WIDTH/2 + msX/2, 7*msY/2 - CAMERA_HEIGHT,this.soundITiledTextureRegionOn,mEngine.getVertexBufferObjectManager());
        this.musicButtonSpriteOff.setVisible(false);
        this.soundButtonSpriteOff.setVisible(false);
        this.musicText = new Text(CAMERA_WIDTH/2  - 3*msX/2 , 5*msY/2 - CAMERA_HEIGHT,this.gamestartFont,TextSource.musicString,mEngine.getVertexBufferObjectManager());
        this.soundText = new Text(CAMERA_WIDTH/2 + msX/2 , 5*msY/2 - CAMERA_HEIGHT, this.gamestartFont,TextSource.soundString, mEngine.getVertexBufferObjectManager());
        
	}
	
	
	
	
	
	//释放menu菜单精灵
	public synchronized void unloadGame2()
	{ 
		this.menueBitmapTextureAtlas.unload();
		this.setBitmapTextureAtlas.unload();
		this.newGameBitmapTextureAtlas.unload();
		this.loadGameBitmapTextureAtlas.unload();
		this.moreGameBitmapTextureAtlas.unload();
		this.litleButtonBitmapTextureAtlas.unload();
		this.musicSoundBuildableBitmapTextureAtlas.unload();
		this.gamestartFont.unload();
		this.bg_twoMusic.stop();
		this.bg_twoSprite.detachSelf();
		this.bg_threeSprite.detachSelf();
		this.newGameButtonSprite.detachSelf();
		this.loadGameButtonSprite.detachSelf();
		this.moreGameButtonSprite.detachSelf();
		this.setGameButtonSprite.detachSelf();
		this.backGameButtonSprite.detachSelf();
		this.musicButtonSpriteOff.detachSelf();
		this.musicButtonSpriteOn.detachSelf();
		this.soundButtonSpriteOff.detachSelf();
		this.soundButtonSpriteOn.detachSelf();
		this.mNewGameText.detachSelf();
		this.mLoadGameText.detachSelf();
		this.mGameMoreText.detachSelf();
		this.musicText.dispose();
		this.soundText.dispose();
		System.gc();
	}
	
	
	
	public synchronized void loadGameGB_oneTexture(Engine engine, Context context)
	{
		  try {
				this.keyboardMusic = MusicFactory.createMusicFromAsset(engine.getMusicManager(), context, TextSource.keyboardmusic);
				this.keyboardMusic.setLooping(true);
			 } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}   	
		  
		//游戏介绍文字
		this.introductionFont_one =  FontFactory.createFromAsset(engine.getFontManager(), engine.getTextureManager(), 512, 512, TextureOptions.BILINEAR,context.getAssets(),TextSource.hwcyFont, 20f, true,org.andengine.util.color.Color.BLACK_ABGR_PACKED_INT);
		this.introductionFont_one.load();
		this.introductionFont_two =  FontFactory.createFromAsset(engine.getFontManager(), engine.getTextureManager(), 512, 512, TextureOptions.BILINEAR,context.getAssets(),TextSource.hwcyFont, 20f, true,org.andengine.util.color.Color.BLACK_ABGR_PACKED_INT);
		this.introductionFont_two.load();
		this.renwuchooseFont = FontFactory.createFromAsset(engine.getFontManager(), engine.getTextureManager(), 100, 64, TextureOptions.BILINEAR,context.getAssets(), TextSource.hwcyFont, 25f, true,org.andengine.util.color.Color.WHITE_ABGR_PACKED_INT);
		this.renwuchooseFont.load();
		this.boygirlintroductionFont = FontFactory.createFromAsset(engine.getFontManager(), engine.getTextureManager(), 512, 512,TextureOptions.BILINEAR, context.getAssets(), TextSource.hwcyFont, 15f, true,org.andengine.util.color.Color.BLACK_ABGR_PACKED_INT);
		this.boygirlintroductionFont.load();
		
		
		//游戏背景four
		this.introductionBitmapTextureAtlas = new BitmapTextureAtlas(engine.getTextureManager(), 512, 512,TextureOptions.BILINEAR);
		this.introductionITextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.introductionBitmapTextureAtlas, context,TextSource.bg_sixPng,0,0);
		this.introductionBitmapTextureAtlas.load();
		
		//游戏人物选择
		this.boygirlBitmapTextureAtlas = new BuildableBitmapTextureAtlas(engine.getTextureManager(), 600, 600,TextureOptions.BILINEAR);
		this.boygirl_oneITextureRegion =  BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.boygirlBitmapTextureAtlas, context,TextSource.boy_onePng);
		this.boygirl_twoITextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.boygirlBitmapTextureAtlas, context,TextSource.boy_twoPng);
		this.boygirl_threeITextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.boygirlBitmapTextureAtlas, context,TextSource.girl_onePng);
		this.boygirl_fourITextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.boygirlBitmapTextureAtlas, context,TextSource.girl_twoPng);
		
		try {
			this.boygirlBitmapTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 1));
		} catch (TextureAtlasBuilderException e4) {
			// TODO Auto-generated catch block
			e4.printStackTrace();
		}
		this.boygirlBitmapTextureAtlas.load();
		
		
		//大木博士
		this.damuBitmapTextureAtlas = new BuildableBitmapTextureAtlas(engine.getTextureManager(), 300, 100,TextureOptions.BILINEAR);
		this.damuTiledTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.damuBitmapTextureAtlas, context, TextSource.damudouctorPng,4,1);
		try {
			this.damuBitmapTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 1));
		} catch (TextureAtlasBuilderException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		engine.getTextureManager().loadTexture(this.damuBitmapTextureAtlas);	
		
		
		//向下箭头
		this.arrowHeadBitmapTextureAtlas = new BuildableBitmapTextureAtlas(engine.getTextureManager(), 32,32,TextureOptions.BILINEAR);
		this.arrowHeadITextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.arrowHeadBitmapTextureAtlas, context,TextSource.arrowheadPng);
		try {
			this.arrowHeadBitmapTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 0));
		} catch (TextureAtlasBuilderException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		this.arrowHeadBitmapTextureAtlas.load();
		
		//人物确定按钮
		this.acceptBtBitmapTextureAtlas = new BuildableBitmapTextureAtlas(engine.getTextureManager(), 32,32,TextureOptions.BILINEAR);
		this.acceptBtITextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.acceptBtBitmapTextureAtlas, context,TextSource.arrowheadPng);
		try {
			this.acceptBtBitmapTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 0));
		} catch (TextureAtlasBuilderException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		this.acceptBtBitmapTextureAtlas.load();
		
		
		//人物选择按钮
		this.selectBuildableBitmapTextureAtlas = new BuildableBitmapTextureAtlas(engine.getTextureManager(), 256, 256,TextureOptions.BILINEAR);
		this.select_leftITiledTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.selectBuildableBitmapTextureAtlas, context.getAssets(),TextSource.select_leftPng, 2, 1);
		this.select_rightITiledTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.selectBuildableBitmapTextureAtlas, context.getAssets(),TextSource.select_rightPng, 2, 1);
		 
		try {
			  selectBuildableBitmapTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 1));
			} catch (TextureAtlasBuilderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		this.selectBuildableBitmapTextureAtlas.load();
	      
	   
	    	  	 
	}
	
	public synchronized void loadGameGB_oneSprite(Engine mEngine,Context context,SmoothCamera camera)
	{
		
		 mEngine.registerUpdateHandler(new FPSLogger());
		 
		 
		//游戏介绍文字
		this.introductionText_one = new TickerText(25, 30, this.introductionFont_one, textSource.introduction_oneString, new TickerTextOptions(HorizontalAlign.LEFT, 8),mEngine.getVertexBufferObjectManager());
	    this.introductionText_two = new TickerText(25, 30, this.introductionFont_two, textSource.introduction_twoString, new TickerTextOptions(HorizontalAlign.LEFT, 8),mEngine.getVertexBufferObjectManager());
	   
	    //人物选择文字
	    this.renwuchoosetText =  new Text(CAMERA_WIDTH/2 - 40,20,this.renwuchooseFont,textSource.renwuChooseString, mEngine.getVertexBufferObjectManager());
	    
	    //游戏人物介绍
	    this.boy_oneintroductionText = new TickerText(CAMERA_WIDTH/2 + 30, 60, this.boygirlintroductionFont, textSource.boy_oneIntroductionString, new TickerTextOptions(HorizontalAlign.LEFT, 16),mEngine.getVertexBufferObjectManager());
//	    this.boy_twointroductionText = new TickerText(CAMERA_WIDTH/2 + 30, 60, this.boygirlintroductionFont, textSource.boy_twoIntroductionString, new TickerTextOptions(HorizontalAlign.LEFT, 16),mEngine.getVertexBufferObjectManager());
//	    this.boy_threeintroductionText = new TickerText(CAMERA_WIDTH/2 + 30, 60, this.boygirlintroductionFont, textSource.girl_oneIntroductionString, new TickerTextOptions(HorizontalAlign.LEFT, 16),mEngine.getVertexBufferObjectManager());
//	    this.boy_fourintroductionText = new TickerText(CAMERA_WIDTH/2 + 30, 60, this.boygirlintroductionFont, textSource.girl_twoIntroductionString, new TickerTextOptions(HorizontalAlign.LEFT, 16),mEngine.getVertexBufferObjectManager());
	    
	    
	    //游戏背景four初始化
		this.bg_fourSprite = new Sprite(0, 0, this.introductionITextureRegion, mEngine.getVertexBufferObjectManager());
	   
		//游戏人物选择
		this.boygirl_oneSprite = new Sprite(CAMERA_WIDTH/4 - 30, 50, this.boygirl_oneITextureRegion, mEngine.getVertexBufferObjectManager());
		this.boygirl_twoSprite = new Sprite(CAMERA_WIDTH/4 - 30, 50, this.boygirl_twoITextureRegion, mEngine.getVertexBufferObjectManager());
		this.boygirl_threeSprite = new Sprite(CAMERA_WIDTH/4 - 30, 50, this.boygirl_threeITextureRegion, mEngine.getVertexBufferObjectManager());
		this.boygirl_fourSprite = new Sprite(CAMERA_WIDTH/4 - 30, 50, this.boygirl_fourITextureRegion, mEngine.getVertexBufferObjectManager());
		
		
		//大木博士
		this.damuAnimatedSprite = new AnimatedSprite(400,250, this.damuTiledTextureRegion,mEngine.getVertexBufferObjectManager());
		this.damuAnimatedSprite.animate(new long[]{200,200,200,200}, true);
		
		
		//向下的箭头
		this.arrowHeadSprite = new ButtonSprite(CAMERA_WIDTH/2 - 16, CAMERA_HEIGHT - 40, this.arrowHeadITextureRegion, mEngine.getVertexBufferObjectManager());
        this.arrowHeadSprite.registerEntityModifier(new LoopEntityModifier(new SequenceEntityModifier(new ScaleModifier(1, 1.0f, 0.8f),new ScaleModifier(1, 0.8f, 1.0f)),5000));
		
        //人物确定按钮
		this.acceptBtSprite = new ButtonSprite(CAMERA_WIDTH/2 - 16, CAMERA_HEIGHT - 40, this.acceptBtITextureRegion, mEngine.getVertexBufferObjectManager());
        this.acceptBtSprite.registerEntityModifier(new LoopEntityModifier(new SequenceEntityModifier(new ScaleModifier(1, 1.0f, 0.8f),new ScaleModifier(1, 0.8f, 1.0f)),5000));
        
        
        //人物选择按钮
        this.select_leftSprite =  new ButtonSprite(20,150, this.select_leftITiledTextureRegion,mEngine.getVertexBufferObjectManager());
        this.select_rightSprite =  new ButtonSprite(CAMERA_WIDTH/2 - 20,150, this.select_rightITiledTextureRegion,mEngine.getVertexBufferObjectManager());

        
	    
	}
	
	
	//释放新游戏载入资源
	public synchronized void unloadGameGB_one()
	{
		this.introductionBitmapTextureAtlas.unload();
		this.damuBitmapTextureAtlas.unload();
		this.arrowHeadBitmapTextureAtlas.unload();
		this.acceptBtBitmapTextureAtlas.unload();
		this.selectBuildableBitmapTextureAtlas.unload();
//		this.loadingBitmapTextureAtlas1.unload();
		this.introductionFont_one.unload();
		this.introductionFont_two.unload();
		this.renwuchooseFont.unload();
		this.boygirlintroductionFont.unload();
//		this.loadinggameFont1.unload();
		this.introductionText_one.detachSelf();
		this.introductionText_two.detachSelf();
		this.renwuchoosetText.detachSelf();
		this.boy_oneintroductionText.detachSelf();
		this.bg_fourSprite.detachSelf();
		this.boygirl_oneSprite.detachSelf();
		this.boygirl_twoSprite.detachSelf();
		this.boygirl_threeSprite.detachSelf();
		this.boygirl_fourSprite.detachSelf();
		this.damuAnimatedSprite.detachSelf();
		this.arrowHeadSprite.detachSelf();
		this.acceptBtSprite.detachSelf();
		this.select_leftSprite.detachSelf();
		this.select_rightSprite.detachSelf();
		System.gc();
	}
	
	
	public synchronized void loadGameGB_threeTexture(Engine engine, Context context)
	{   
		
		//地图选择背景
		this.selectMapBgBuildableBitmapTextureAtlas = new BuildableBitmapTextureAtlas(engine.getTextureManager(), 512, 512,TextureOptions.BILINEAR);
		this.selectMapBgITextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(selectMapBgBuildableBitmapTextureAtlas,context.getAssets(),TextSource.bg_twoPng);
		try {
			this.selectMapBgBuildableBitmapTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 0));
		} catch (TextureAtlasBuilderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.selectMapBgBuildableBitmapTextureAtlas.load();
		
		//地图导航栏
		this.navigationITextureRegion = new ITextureRegion[8];
		this.navigationBuildableBitmapTextureAtlas = new BuildableBitmapTextureAtlas(engine.getTextureManager(),128,128,TextureOptions.BILINEAR);
		this.navigationITextureRegion[0] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(navigationBuildableBitmapTextureAtlas, context.getAssets(),TextSource.ui_two_01Png);
		this.navigationITextureRegion[1] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(navigationBuildableBitmapTextureAtlas, context.getAssets(),TextSource.ui_two_02Png);
		this.navigationITextureRegion[2] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(navigationBuildableBitmapTextureAtlas, context.getAssets(),TextSource.ui_two_03Png);
		this.navigationITextureRegion[3] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(navigationBuildableBitmapTextureAtlas, context.getAssets(),TextSource.ui_two_04Png);
		this.navigationITextureRegion[4] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(navigationBuildableBitmapTextureAtlas, context.getAssets(),TextSource.ui_two_05Png);
		this.navigationITextureRegion[5] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(navigationBuildableBitmapTextureAtlas, context.getAssets(),TextSource.ui_two_06Png);
		this.navigationITextureRegion[6] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(navigationBuildableBitmapTextureAtlas, context.getAssets(),TextSource.ui_two_07Png);
		this.navigationITextureRegion[7] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(navigationBuildableBitmapTextureAtlas, context.getAssets(),TextSource.ui_two_08Png);
		try {
			this.navigationBuildableBitmapTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 1));
		} catch (TextureAtlasBuilderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.navigationBuildableBitmapTextureAtlas.load();
		
		//地图选择按钮
		this.btMapBuildableBitmapTextureAtlas = new BuildableBitmapTextureAtlas(engine.getTextureManager(),256,128,TextureOptions.BILINEAR);
		this.selectMapLefTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(btMapBuildableBitmapTextureAtlas,context.getAssets(),TextSource.bt_mapPng);
		this.selectMapRightITextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(btMapBuildableBitmapTextureAtlas,context.getAssets(),TextSource.bt_mapPng);
		//home键
	    this.btHomeITextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.btMapBuildableBitmapTextureAtlas, context.getAssets(),TextSource.bt_homePng); 
	    
		try {
			this.btMapBuildableBitmapTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 1));
		} catch (TextureAtlasBuilderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.btMapBuildableBitmapTextureAtlas.load();
		
	   //关卡字体
		this.selectMapFont = FontFactory.createFromAsset(engine.getFontManager(), engine.getTextureManager(), 128, 64, TextureOptions.BILINEAR,context.getAssets(),TextSource.hwcyFont, 20f, true,org.andengine.util.color.Color.WHITE_ABGR_PACKED_INT);
		this.selectMapFont.load();
		
	   //目录地图
	   this.mainMapBuildableBitmapTextureAtlas = new BuildableBitmapTextureAtlas[10];
	   this.mainMapITextureRegion = new ITextureRegion[10];
	   for(int i = 0 ;i < 10 ;i ++){
		   this.mainMapBuildableBitmapTextureAtlas[i] = new BuildableBitmapTextureAtlas(engine.getTextureManager(), 512,  256,TextureOptions.BILINEAR);
	   }
	   
	   this.mainMapITextureRegion[0] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mainMapBuildableBitmapTextureAtlas[0],context.getAssets(),TextSource.mapListone1);
	   this.mainMapITextureRegion[1] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mainMapBuildableBitmapTextureAtlas[1],context.getAssets(),TextSource.mapListone2);
	   this.mainMapITextureRegion[2] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mainMapBuildableBitmapTextureAtlas[2],context.getAssets(),TextSource.mapListone3);
	   this.mainMapITextureRegion[3] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mainMapBuildableBitmapTextureAtlas[3],context.getAssets(),TextSource.mapListone4);
	   this.mainMapITextureRegion[4] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mainMapBuildableBitmapTextureAtlas[4],context.getAssets(),TextSource.mapListone5);
	   this.mainMapITextureRegion[5] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mainMapBuildableBitmapTextureAtlas[5],context.getAssets(),TextSource.mapListone6);
	   this.mainMapITextureRegion[6] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mainMapBuildableBitmapTextureAtlas[6],context.getAssets(),TextSource.mapListone7);
	   this.mainMapITextureRegion[7] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mainMapBuildableBitmapTextureAtlas[7],context.getAssets(),TextSource.mapListone8);
	   this.mainMapITextureRegion[8] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mainMapBuildableBitmapTextureAtlas[8],context.getAssets(),TextSource.mapListone9);
	   this.mainMapITextureRegion[9] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mainMapBuildableBitmapTextureAtlas[9],context.getAssets(),TextSource.mapListone10);
	   for(int i = 0 ;i < 10 ;i ++){
		   try {
			this.mainMapBuildableBitmapTextureAtlas[i].build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 1));
		} catch (TextureAtlasBuilderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   this.mainMapBuildableBitmapTextureAtlas[i].load();
	   }
	   
	   
		
	}
	
	public synchronized void loadGameGB_threeSprite(Engine mEngine,Context context,SmoothCamera camera)
	{
		 //地图选择背景
		 this.selectMapBgSprite = new Sprite(0, 0,this.selectMapBgITextureRegion,mEngine.getVertexBufferObjectManager());
		
		//地图导航栏
		this.navigationSprites = new Sprite[8];
		this.navigationSprites[0] = new Sprite(CAMERA_WIDTH / 2 - this.navigationITextureRegion[0].getWidth()/2 ,CAMERA_HEIGHT - 20,this.navigationITextureRegion[0],mEngine.getVertexBufferObjectManager());
		this.navigationSprites[1] = new Sprite(CAMERA_WIDTH / 2 - this.navigationITextureRegion[1].getWidth()/2 ,CAMERA_HEIGHT - 20,this.navigationITextureRegion[1],mEngine.getVertexBufferObjectManager());
		this.navigationSprites[2] = new Sprite(CAMERA_WIDTH / 2 - this.navigationITextureRegion[2].getWidth()/2 ,CAMERA_HEIGHT - 20,this.navigationITextureRegion[2],mEngine.getVertexBufferObjectManager());
		this.navigationSprites[3] = new Sprite(CAMERA_WIDTH / 2 - this.navigationITextureRegion[3].getWidth()/2 ,CAMERA_HEIGHT - 20,this.navigationITextureRegion[3],mEngine.getVertexBufferObjectManager());
		this.navigationSprites[4] = new Sprite(CAMERA_WIDTH / 2 - this.navigationITextureRegion[4].getWidth()/2 ,CAMERA_HEIGHT - 20,this.navigationITextureRegion[4],mEngine.getVertexBufferObjectManager());
		this.navigationSprites[5] = new Sprite(CAMERA_WIDTH / 2 - this.navigationITextureRegion[5].getWidth()/2 ,CAMERA_HEIGHT - 20,this.navigationITextureRegion[5],mEngine.getVertexBufferObjectManager());
	    this.navigationSprites[6] = new Sprite(CAMERA_WIDTH / 2 - this.navigationITextureRegion[6].getWidth()/2 ,CAMERA_HEIGHT - 20,this.navigationITextureRegion[6],mEngine.getVertexBufferObjectManager());
        this.navigationSprites[7] = new Sprite(CAMERA_WIDTH / 2 - this.navigationITextureRegion[7].getWidth()/2 ,CAMERA_HEIGHT - 20,this.navigationITextureRegion[7],mEngine.getVertexBufferObjectManager());
		for(int i = 0 ;i < 8 ;i ++){
			this.navigationSprites[i].setVisible(false);
			this.navigationSprites[i].setScale(0.8f);
		}
		
		
		//地图选择按钮
		this.selectMapLeftSprite = new ButtonSprite(15, CAMERA_HEIGHT/2 - this.selectMapLefTextureRegion.getHeight()/2, this.selectMapLefTextureRegion,mEngine.getVertexBufferObjectManager());
		this.selectMapRightSprite = new ButtonSprite(CAMERA_WIDTH - 79, CAMERA_HEIGHT/2 - this.selectMapRightITextureRegion.getHeight()/2, this.selectMapRightITextureRegion, mEngine.getVertexBufferObjectManager());
		this.selectMapRightSprite.setRotation(90f);
		this.selectMapLeftSprite.setRotation(-90f);	
		
		//home键
		this.btHomeSprite = new ButtonSprite(15, 5,this.btHomeITextureRegion,mEngine.getVertexBufferObjectManager());
		
		//关卡选择
		this.selectMapText = new Text(CAMERA_WIDTH/2 - 40,3,this.selectMapFont,TextSource.selectMapString, mEngine.getVertexBufferObjectManager());
		
		//目录地图
	    this.mainMapSprites = new ButtonSprite[10];
	    for(int i = 0 ;i < 10 ;i ++){
	    	this.mainMapSprites[i] = new ButtonSprite(CAMERA_WIDTH/2 - this.mainMapITextureRegion[i].getWidth()/2,CAMERA_HEIGHT/2 - this.mainMapITextureRegion[i].getHeight()/2,this.mainMapITextureRegion[i],mEngine.getVertexBufferObjectManager());
	        this.mainMapSprites[i].setVisible(false);
	    }
	    
	    
	    
	}
	
	
	//释放资源
	public synchronized void unloadGameGB_three()
	{
		this.selectMapBgBuildableBitmapTextureAtlas.unload();
		this.btMapBuildableBitmapTextureAtlas.unload();
		this.navigationBuildableBitmapTextureAtlas.unload();
		this.selectMapBgSprite.detachSelf();
		for(int i = 0 ; i < 5 ;i ++)
		{
			this.navigationSprites[i].detachSelf();
		}
		this.selectMapLeftSprite.detachSelf();
		this.selectMapRightSprite.detachSelf();
	}
	
	
	public synchronized void loadGameGB_twoTexture(Engine engine, Context context)
	{
		 engine.registerUpdateHandler(new FPSLogger());
		 
		//游戏UI加载
		 this.ui_oneBuildableBitmapTextureAtlas = new BuildableBitmapTextureAtlas(engine.getTextureManager(),512,64,BitmapTextureFormat.RGBA_8888,TextureOptions.BILINEAR);
	     this.ui_oneITextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.ui_oneBuildableBitmapTextureAtlas, context,TextSource.ui_onePng);
		 try {
			this.ui_oneBuildableBitmapTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 1));
		} catch (TextureAtlasBuilderException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		 this.ui_oneBuildableBitmapTextureAtlas.load();
		
	     //游戏sound初始化  
		  try {
				this.xiaoquSound = SoundFactory.createSoundFromAsset(engine.getSoundManager(), context, TextSource.xiaoqu_buttonsound);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		   }
		  
	     
		 //游戏盒子资源
		 boxBuildBitmapTextureAtlas = new BuildableBitmapTextureAtlas[7];
		 boxITextureRegions = new ITextureRegion[10];
		 for(int i = 0 ;i < 7 ;i ++){
		     boxBuildBitmapTextureAtlas[i] = new BuildableBitmapTextureAtlas(engine.getTextureManager(),64,64,BitmapTextureFormat.RGBA_8888,TextureOptions.BILINEAR);
		 }
		 boxITextureRegions[0] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.boxBuildBitmapTextureAtlas[0], context,TextSource.box1Png);
		 boxITextureRegions[1] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.boxBuildBitmapTextureAtlas[1], context,TextSource.box2Png);
		 boxITextureRegions[2] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.boxBuildBitmapTextureAtlas[2], context,TextSource.box3Png);
		 boxITextureRegions[3] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.boxBuildBitmapTextureAtlas[3], context,TextSource.box4Png);
		 boxITextureRegions[4] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.boxBuildBitmapTextureAtlas[4], context,TextSource.box5Png);
		 boxITextureRegions[5] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.boxBuildBitmapTextureAtlas[5], context,TextSource.box6Png);
		 boxITextureRegions[6] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.boxBuildBitmapTextureAtlas[6], context,TextSource.box7Png);
		 
		 for(int i = 0 ;i < 7 ;i ++){
			try {
				boxBuildBitmapTextureAtlas[i].build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 1));
			} catch (TextureAtlasBuilderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			boxBuildBitmapTextureAtlas[i].load();	
		 }
		
		 bodyBuildableBitmapTextureAtlas = new BuildableBitmapTextureAtlas[7];
		 bodyITextureRegion = new ITextureRegion[7];
		 
		 for(int i = 0 ;i < 7 ; i ++)
		 {
			 bodyBuildableBitmapTextureAtlas[i] = new BuildableBitmapTextureAtlas(engine.getTextureManager(),32,32,TextureOptions.BILINEAR);
		 }
		 bodyITextureRegion[0] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bodyBuildableBitmapTextureAtlas[0],context,TextSource.smallbox1Png);
		 bodyITextureRegion[1] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bodyBuildableBitmapTextureAtlas[1],context,TextSource.smallbox2Png);
		 bodyITextureRegion[2] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bodyBuildableBitmapTextureAtlas[2],context,TextSource.smallbox3Png);
		 bodyITextureRegion[3] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bodyBuildableBitmapTextureAtlas[3],context,TextSource.smallbox4Png);
		 bodyITextureRegion[4] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bodyBuildableBitmapTextureAtlas[4],context,TextSource.smallbox5Png);
		 bodyITextureRegion[5] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bodyBuildableBitmapTextureAtlas[5],context,TextSource.smallbox6Png);
		 bodyITextureRegion[6] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bodyBuildableBitmapTextureAtlas[6],context,TextSource.smallbox7Png);
		 
		 for(int i = 0 ;i < 7 ;i ++){
			 try {
				bodyBuildableBitmapTextureAtlas[i].build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 1));
			} catch (TextureAtlasBuilderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 bodyBuildableBitmapTextureAtlas[i].load();
		 }
		 
		 
		 //提示闪亮图标
		 promptBuildableBitmapTextureAtlas = new BuildableBitmapTextureAtlas(engine.getTextureManager(),32,32,TextureOptions.BILINEAR); 
		 promptITextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.promptBuildableBitmapTextureAtlas, context,TextSource.handPng);
		 
		 try {
			promptBuildableBitmapTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 1));
		} catch (TextureAtlasBuilderException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		 promptBuildableBitmapTextureAtlas.load(); 
		 
		 //特殊动画
		 this.specialAnimationBuildableBitmapTextureAtlas = new BuildableBitmapTextureAtlas[10];
	     this.specialAnimationTiledTextureRegion = new TiledTextureRegion[10];
	     this.specialAnimationBuildableBitmapTextureAtlas[0] = new BuildableBitmapTextureAtlas(engine.getTextureManager(),300,75,TextureOptions.BILINEAR);  
	     this.specialAnimationTiledTextureRegion[0] = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(specialAnimationBuildableBitmapTextureAtlas[0], context,TextSource.specialAnimation_onePng, 6, 3);
	     this.specialAnimationBuildableBitmapTextureAtlas[1] = new BuildableBitmapTextureAtlas(engine.getTextureManager(),240,100,TextureOptions.BILINEAR);
	     this.specialAnimationTiledTextureRegion[1] = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(specialAnimationBuildableBitmapTextureAtlas[1], context,TextSource.specialAnimation_twoPng, 5, 4);
	     
	     for(int i = 0 ;i < 2 ;i ++){
	     try {
			this.specialAnimationBuildableBitmapTextureAtlas[i].build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 0));
	       } catch (TextureAtlasBuilderException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		 }
	     this.specialAnimationBuildableBitmapTextureAtlas[i].load();
	   }
	     
		  //加载游戏手柄
	     mOnScreenControlTexture = new BuildableBitmapTextureAtlas(engine.getTextureManager(), 256, 128,TextureOptions.BILINEAR);
	  	 mOnScreenControlBaseITextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mOnScreenControlTexture,context,TextSource.Joystick_onePng);
	  	 mOnScreenControlKnobITextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mOnScreenControlTexture,context,TextSource.Joystick_twoPng);
	  	 try {
			this.mOnScreenControlTexture.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 1));
		} catch (TextureAtlasBuilderException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	  	 mOnScreenControlTexture.load();
	  	 
	  	 //游戏按钮
	     mOnScreenBTextureAtlas =  new BuildableBitmapTextureAtlas(engine.getTextureManager(), 256, 128,TextureOptions.BILINEAR);
	     mOnScreenBtAITiledTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mOnScreenBTextureAtlas, context.getAssets(),TextSource.bt_aPng, 2, 1);
	  	 mOnScreenBtEITiledTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mOnScreenBTextureAtlas, context.getAssets(),TextSource.bt_ePng, 2, 1);
	  	 try {
			mOnScreenBTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 1));
		} catch (TextureAtlasBuilderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  	 
	  	mOnScreenBTextureAtlas.load();
	  	
	  	 //游戏一号主人公
		hero_oneBitmapTextureAtlas = new BuildableBitmapTextureAtlas(engine.getTextureManager(), 256,256,BitmapTextureFormat.RGBA_8888,TextureOptions.BILINEAR);
		hero_oneTiledTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.hero_oneBitmapTextureAtlas, context, "renwu/Gary_two.png",4,4);
		try {
			hero_oneBitmapTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 1));
		} catch (TextureAtlasBuilderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		engine.getTextureManager().loadTexture(this.hero_oneBitmapTextureAtlas);	
	    
		//设置时间倒计时(游戏分数)
//      this.gameTimeScoreFont = FontFactory.createFromAsset(engine.getFontManager(), engine.getTextureManager(), 256, 64, context.getAssets(), "hwcy.ttf", 30f, true,org.andengine.util.color.Color.WHITE_ABGR_PACKED_INT);
		this.gameTimeScoreFont = FontFactory.createFromAsset(engine.getFontManager(), engine.getTextureManager(),256, 64, BitmapTextureFormat.RGBA_8888,TextureOptions.BILINEAR, context.getAssets(),TextSource.constanbFont,25f, true, org.andengine.util.color.Color.WHITE_ARGB_PACKED_INT);
	    this.gameTimeScoreFont.load();
	    
	    //游戏地图名称
	    this.gameMapNameFont = FontFactory.createFromAsset(engine.getFontManager(), engine.getTextureManager(),256, 64, BitmapTextureFormat.RGBA_8888,TextureOptions.BILINEAR, context.getAssets(),TextSource.hwcyFont,20f, true, org.andengine.util.color.Color.WHITE_ARGB_PACKED_INT);
	    this.gameMapNameFont.load();
	    
	}
	
	
	
	public synchronized void loadGameGB_twoSprite(Engine mEngine,Context context,SmoothCamera camera)
	{ 
		//ui加载
		this.ui_oneSprite = new Sprite((CAMERA_WIDTH - this.ui_oneITextureRegion.getWidth())/2, -10, this.ui_oneITextureRegion, mEngine.getVertexBufferObjectManager());
	
		//游戏主人公加载
		this.hero_oneAnimatedSprite = new AnimatedSprite(0, 0, this.hero_oneTiledTextureRegion,mEngine.getVertexBufferObjectManager()); 
        this.hero_oneAnimatedSprite.setAlpha(0.9f); 
        
        //游戏闪光精灵
        this.promptSprite = new Sprite(0, 0, this.promptITextureRegion,mEngine.getVertexBufferObjectManager());
        this.promptSprite.setVisible(false);
        
        //人物身上的精灵
       this.bodySprites = new Sprite[7];
       for(int i = 0 ;i < 7 ;i ++){
        this.bodySprites[i] = new Sprite(CAMERA_WIDTH - 170,10,this.bodyITextureRegion[i],mEngine.getVertexBufferObjectManager());
        this.bodySprites[i].setVisible(false);
       }
         
       
       //特效动画
       this.specialAnimationAnimatedSprite = new AnimatedSprite[10];
       for(int i = 0 ;i < 2 ; i ++){
        this.specialAnimationAnimatedSprite[i] = new AnimatedSprite(0 , 0,specialAnimationTiledTextureRegion[i],mEngine.getVertexBufferObjectManager());
        this.specialAnimationAnimatedSprite[i].setVisible(false);
       }
       
       
        //游戏按钮
        this.btAButtonSprite = new ButtonSprite(CAMERA_WIDTH - 60, CAMERA_HEIGHT -  mOnScreenBtEITiledTextureRegion.getHeight()*2, mOnScreenBtAITiledTextureRegion, mEngine.getVertexBufferObjectManager());
        this.btEButtonSprite = new ButtonSprite(CAMERA_WIDTH - 120, CAMERA_HEIGHT - mOnScreenBtEITiledTextureRegion.getHeight(), mOnScreenBtEITiledTextureRegion, mEngine.getVertexBufferObjectManager());
	
        //时间倒计时
        this.gameTimeText = new Text(CAMERA_WIDTH - 80, 1 ,this.gameTimeScoreFont, TextSource.timeString, 10,mEngine.getVertexBufferObjectManager());
	    this.gameTimeText.setAlpha(0.8f);
	    
        //分数计算器
        this.gamescoreText = new Text(70, 1 ,this.gameTimeScoreFont,TextSource.scoreString, 12,mEngine.getVertexBufferObjectManager());
	    this.gamescoreText.setAlpha(0.8f);
	    
	    //游戏地图名称
	    this.gamemapnameText = new Text(CAMERA_WIDTH/3, 2 ,this.gameMapNameFont,"d", 12,mEngine.getVertexBufferObjectManager());
	    this.gamemapnameText.setAlpha(0.8f);
	}
	
	
	public synchronized void loadGameGB_fourTexture_one(Engine engine, Context context)
	{ 
		//子关卡背景
		this.mapShowBgBuildableBitmapTextureAtlas = new BuildableBitmapTextureAtlas(engine.getTextureManager(),512,512,TextureOptions.BILINEAR);
		this.mapShowBgITextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mapShowBgBuildableBitmapTextureAtlas, context.getAssets(),TextSource.bg_twoPng);
		try {
			this.mapShowBgBuildableBitmapTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 0));
		} catch (TextureAtlasBuilderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.mapShowBgBuildableBitmapTextureAtlas.load();
		
		//子关卡
		this.childrenMapBuildableBitmapTextureAtlas = new BuildableBitmapTextureAtlas[6];
		this.childrenMapITextureRegion = new ITextureRegion[6];
		for(int i = 0 ;i < 6 ;i ++){
			this.childrenMapBuildableBitmapTextureAtlas[i] = new BuildableBitmapTextureAtlas(engine.getTextureManager(),256,256);
		}
		this.childrenMapITextureRegion[0] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(childrenMapBuildableBitmapTextureAtlas[0],context.getAssets(),TextSource.mapNum1_09);
		this.childrenMapITextureRegion[1] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(childrenMapBuildableBitmapTextureAtlas[1],context.getAssets(),TextSource.mapNum1_016);
		this.childrenMapITextureRegion[2] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(childrenMapBuildableBitmapTextureAtlas[2],context.getAssets(),TextSource.mapNum1_01);
		this.childrenMapITextureRegion[3] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(childrenMapBuildableBitmapTextureAtlas[3],context.getAssets(),TextSource.mapNum1_018);
		this.childrenMapITextureRegion[4] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(childrenMapBuildableBitmapTextureAtlas[4],context.getAssets(),TextSource.mapNum1_3530);
		this.childrenMapITextureRegion[5] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(childrenMapBuildableBitmapTextureAtlas[5],context.getAssets(),TextSource.mapNum1_00);
		
		
		for(int i = 0 ;i <  6 ;i ++){
			try {
				this.childrenMapBuildableBitmapTextureAtlas[i].build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 1));
			} catch (TextureAtlasBuilderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   this.childrenMapBuildableBitmapTextureAtlas[i].load();
		}
		
		
	}
	
	
	public synchronized void loadGameGB_fourSprite_one(Engine mEngine,Context context,SmoothCamera camera)
	{   
		//子关卡背景
		this.mapShowBgSprite = new Sprite(0,0,this.mapShowBgITextureRegion,mEngine.getVertexBufferObjectManager());
		
		//子地图
		this.childreButtonSprites = new ButtonSprite[6];
		for(int i = 0 ;i < 6 ;i ++){
			this.childreButtonSprites[i] = new ButtonSprite(-CAMERA_WIDTH,
					CAMERA_HEIGHT/2 - this.childrenMapITextureRegion[i].getHeight()/2,
					this.childrenMapITextureRegion[i],mEngine.getVertexBufferObjectManager());
		}
		
	}
	
	
	public synchronized void loadGameGB_fourTexture_two(Engine engine, Context context)
	{ 
		//子关卡背景
		this.mapShowBgBuildableBitmapTextureAtlas = new BuildableBitmapTextureAtlas(engine.getTextureManager(),512,512,TextureOptions.BILINEAR);
		this.mapShowBgITextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mapShowBgBuildableBitmapTextureAtlas, context.getAssets(),TextSource.bg_twoPng);
		try {
			this.mapShowBgBuildableBitmapTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 0));
		} catch (TextureAtlasBuilderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.mapShowBgBuildableBitmapTextureAtlas.load();
		
		//子关卡
		this.childrenMapBuildableBitmapTextureAtlas = new BuildableBitmapTextureAtlas[5];
		this.childrenMapITextureRegion = new ITextureRegion[5];
		for(int i = 0 ;i < 5 ;i ++){
			this.childrenMapBuildableBitmapTextureAtlas[i] = new BuildableBitmapTextureAtlas(engine.getTextureManager(),256,256);
		}
		this.childrenMapITextureRegion[0] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(childrenMapBuildableBitmapTextureAtlas[0],context.getAssets(),TextSource.mapNum2_019);
		this.childrenMapITextureRegion[1] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(childrenMapBuildableBitmapTextureAtlas[1],context.getAssets(),TextSource.mapNum2_03);
		this.childrenMapITextureRegion[2] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(childrenMapBuildableBitmapTextureAtlas[2],context.getAssets(),TextSource.mapNum2_2411);
		this.childrenMapITextureRegion[3] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(childrenMapBuildableBitmapTextureAtlas[3],context.getAssets(),TextSource.mapNum2_2556);
		this.childrenMapITextureRegion[4] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(childrenMapBuildableBitmapTextureAtlas[4],context.getAssets(),TextSource.mapNum2_3523);
		
		
		for(int i = 0 ;i <  5 ;i ++){
			try {
				this.childrenMapBuildableBitmapTextureAtlas[i].build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 1));
			} catch (TextureAtlasBuilderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   this.childrenMapBuildableBitmapTextureAtlas[i].load();
		}
		
		
	}
	
	
	public synchronized void loadGameGB_fourSprite_two(Engine mEngine,Context context,SmoothCamera camera)
	{   
		//子关卡背景
		this.mapShowBgSprite = new Sprite(0,0,this.mapShowBgITextureRegion,mEngine.getVertexBufferObjectManager());
		
		//子地图
		this.childreButtonSprites = new ButtonSprite[5];
		for(int i = 0 ;i < 5 ;i ++){
			this.childreButtonSprites[i] = new ButtonSprite(-CAMERA_WIDTH,
					CAMERA_HEIGHT/2 - this.childrenMapITextureRegion[i].getHeight()/2,
					this.childrenMapITextureRegion[i],mEngine.getVertexBufferObjectManager());
		}
		
	}
	
	
	
	public synchronized void loadGameGB_fourTexture_three(Engine engine, Context context)
	{ 
		//子关卡背景
		this.mapShowBgBuildableBitmapTextureAtlas = new BuildableBitmapTextureAtlas(engine.getTextureManager(),512,512,TextureOptions.BILINEAR);
		this.mapShowBgITextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mapShowBgBuildableBitmapTextureAtlas, context.getAssets(),TextSource.bg_twoPng);
		try {
			this.mapShowBgBuildableBitmapTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 0));
		} catch (TextureAtlasBuilderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.mapShowBgBuildableBitmapTextureAtlas.load();
		
		//子关卡
		this.childrenMapBuildableBitmapTextureAtlas = new BuildableBitmapTextureAtlas[4];
		this.childrenMapITextureRegion = new ITextureRegion[4];
		for(int i = 0 ;i < 4 ;i ++){
			this.childrenMapBuildableBitmapTextureAtlas[i] = new BuildableBitmapTextureAtlas(engine.getTextureManager(),256,256);
		}
		this.childrenMapITextureRegion[0] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(childrenMapBuildableBitmapTextureAtlas[0],context.getAssets(),TextSource.mapNum2_3523);
		this.childrenMapITextureRegion[1] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(childrenMapBuildableBitmapTextureAtlas[1],context.getAssets(),TextSource.mapNum2_3523);
		this.childrenMapITextureRegion[2] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(childrenMapBuildableBitmapTextureAtlas[2],context.getAssets(),TextSource.mapNum2_3523);
		this.childrenMapITextureRegion[3] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(childrenMapBuildableBitmapTextureAtlas[3],context.getAssets(),TextSource.mapNum2_3523);
		
		
		for(int i = 0 ;i <  4 ;i ++){
			try {
				this.childrenMapBuildableBitmapTextureAtlas[i].build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 1));
			} catch (TextureAtlasBuilderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   this.childrenMapBuildableBitmapTextureAtlas[i].load();
		}
		
		
	}
	
	
	public synchronized void loadGameGB_fourSprite_three(Engine mEngine,Context context,SmoothCamera camera)
	{   
		//子关卡背景
		this.mapShowBgSprite = new Sprite(0,0,this.mapShowBgITextureRegion,mEngine.getVertexBufferObjectManager());
		
		//子地图
		this.childreButtonSprites = new ButtonSprite[4];
		for(int i = 0 ;i < 4 ;i ++){
			this.childreButtonSprites[i] = new ButtonSprite(-CAMERA_WIDTH,
					CAMERA_HEIGHT/2 - this.childrenMapITextureRegion[i].getHeight()/2,
					this.childrenMapITextureRegion[i],mEngine.getVertexBufferObjectManager());
		}
		
	}
	
	
	public synchronized void loadGameGB_fourTexture_four(Engine engine, Context context)
	{ 
		//子关卡背景
		this.mapShowBgBuildableBitmapTextureAtlas = new BuildableBitmapTextureAtlas(engine.getTextureManager(),512,512,TextureOptions.BILINEAR);
		this.mapShowBgITextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mapShowBgBuildableBitmapTextureAtlas, context.getAssets(),TextSource.bg_twoPng);
		try {
			this.mapShowBgBuildableBitmapTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 0));
		} catch (TextureAtlasBuilderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.mapShowBgBuildableBitmapTextureAtlas.load();
		
		//子关卡
		this.childrenMapBuildableBitmapTextureAtlas = new BuildableBitmapTextureAtlas[6];
		this.childrenMapITextureRegion = new ITextureRegion[6];
		for(int i = 0 ;i < 6 ;i ++){
			this.childrenMapBuildableBitmapTextureAtlas[i] = new BuildableBitmapTextureAtlas(engine.getTextureManager(),256,256);
		}
		this.childrenMapITextureRegion[0] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(childrenMapBuildableBitmapTextureAtlas[0],context.getAssets(),TextSource.mapNum2_3523);
		this.childrenMapITextureRegion[1] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(childrenMapBuildableBitmapTextureAtlas[1],context.getAssets(),TextSource.mapNum2_3523);
		this.childrenMapITextureRegion[2] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(childrenMapBuildableBitmapTextureAtlas[2],context.getAssets(),TextSource.mapNum2_3523);
		this.childrenMapITextureRegion[3] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(childrenMapBuildableBitmapTextureAtlas[3],context.getAssets(),TextSource.mapNum2_3523);
		this.childrenMapITextureRegion[4] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(childrenMapBuildableBitmapTextureAtlas[4],context.getAssets(),TextSource.mapNum2_3523);
		this.childrenMapITextureRegion[5] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(childrenMapBuildableBitmapTextureAtlas[5],context.getAssets(),TextSource.mapNum2_3523);
		
		
		for(int i = 0 ;i <  6 ;i ++){
			try {
				this.childrenMapBuildableBitmapTextureAtlas[i].build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 1));
			} catch (TextureAtlasBuilderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   this.childrenMapBuildableBitmapTextureAtlas[i].load();
		}
		
		
	}
	
	
	public synchronized void loadGameGB_fourSprite_four(Engine mEngine,Context context,SmoothCamera camera)
	{   
		//子关卡背景
		this.mapShowBgSprite = new Sprite(0,0,this.mapShowBgITextureRegion,mEngine.getVertexBufferObjectManager());
		
		//子地图
		this.childreButtonSprites = new ButtonSprite[6];
		for(int i = 0 ;i < 6 ;i ++){
			this.childreButtonSprites[i] = new ButtonSprite(-CAMERA_WIDTH,
					CAMERA_HEIGHT/2 - this.childrenMapITextureRegion[i].getHeight()/2,
					this.childrenMapITextureRegion[i],mEngine.getVertexBufferObjectManager());
		}
		
	}
	
	
	public synchronized void loadGameGB_fourTexture_five(Engine engine, Context context)
	{ 
		//子关卡背景
		this.mapShowBgBuildableBitmapTextureAtlas = new BuildableBitmapTextureAtlas(engine.getTextureManager(),512,512,TextureOptions.BILINEAR);
		this.mapShowBgITextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mapShowBgBuildableBitmapTextureAtlas, context.getAssets(),TextSource.bg_twoPng);
		try {
			this.mapShowBgBuildableBitmapTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 0));
		} catch (TextureAtlasBuilderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.mapShowBgBuildableBitmapTextureAtlas.load();
		
		//子关卡
		this.childrenMapBuildableBitmapTextureAtlas = new BuildableBitmapTextureAtlas[6];
		this.childrenMapITextureRegion = new ITextureRegion[6];
		for(int i = 0 ;i < 6 ;i ++){
			this.childrenMapBuildableBitmapTextureAtlas[i] = new BuildableBitmapTextureAtlas(engine.getTextureManager(),256,256);
		}
		this.childrenMapITextureRegion[0] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(childrenMapBuildableBitmapTextureAtlas[0],context.getAssets(),TextSource.mapNum2_3523);
		this.childrenMapITextureRegion[1] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(childrenMapBuildableBitmapTextureAtlas[1],context.getAssets(),TextSource.mapNum2_3523);
		this.childrenMapITextureRegion[2] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(childrenMapBuildableBitmapTextureAtlas[2],context.getAssets(),TextSource.mapNum2_3523);
		this.childrenMapITextureRegion[3] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(childrenMapBuildableBitmapTextureAtlas[3],context.getAssets(),TextSource.mapNum2_3523);
		this.childrenMapITextureRegion[4] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(childrenMapBuildableBitmapTextureAtlas[4],context.getAssets(),TextSource.mapNum2_3523);
		this.childrenMapITextureRegion[5] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(childrenMapBuildableBitmapTextureAtlas[5],context.getAssets(),TextSource.mapNum2_3523);
		
		
		for(int i = 0 ;i <  6 ;i ++){
			try {
				this.childrenMapBuildableBitmapTextureAtlas[i].build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 1));
			} catch (TextureAtlasBuilderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   this.childrenMapBuildableBitmapTextureAtlas[i].load();
		}
		
		
	}
	
	
	public synchronized void loadGameGB_fourSprite_five(Engine mEngine,Context context,SmoothCamera camera)
	{   
		//子关卡背景
		this.mapShowBgSprite = new Sprite(0,0,this.mapShowBgITextureRegion,mEngine.getVertexBufferObjectManager());
		
		//子地图
		this.childreButtonSprites = new ButtonSprite[6];
		for(int i = 0 ;i < 6 ;i ++){
			this.childreButtonSprites[i] = new ButtonSprite(-CAMERA_WIDTH,
					CAMERA_HEIGHT/2 - this.childrenMapITextureRegion[i].getHeight()/2,
					this.childrenMapITextureRegion[i],mEngine.getVertexBufferObjectManager());
		}
		
	}
	
	
	public synchronized void loadGameGB_fourTexture_six(Engine engine, Context context)
	{ 
		//子关卡背景
		this.mapShowBgBuildableBitmapTextureAtlas = new BuildableBitmapTextureAtlas(engine.getTextureManager(),512,512,TextureOptions.BILINEAR);
		this.mapShowBgITextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mapShowBgBuildableBitmapTextureAtlas, context.getAssets(),TextSource.bg_twoPng);
		try {
			this.mapShowBgBuildableBitmapTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 0));
		} catch (TextureAtlasBuilderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.mapShowBgBuildableBitmapTextureAtlas.load();
		
		//子关卡
		this.childrenMapBuildableBitmapTextureAtlas = new BuildableBitmapTextureAtlas[6];
		this.childrenMapITextureRegion = new ITextureRegion[6];
		for(int i = 0 ;i < 6 ;i ++){
			this.childrenMapBuildableBitmapTextureAtlas[i] = new BuildableBitmapTextureAtlas(engine.getTextureManager(),256,256);
		}
		this.childrenMapITextureRegion[0] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(childrenMapBuildableBitmapTextureAtlas[0],context.getAssets(),TextSource.mapNum2_3523);
		this.childrenMapITextureRegion[1] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(childrenMapBuildableBitmapTextureAtlas[1],context.getAssets(),TextSource.mapNum2_3523);
		this.childrenMapITextureRegion[2] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(childrenMapBuildableBitmapTextureAtlas[2],context.getAssets(),TextSource.mapNum2_3523);
		this.childrenMapITextureRegion[3] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(childrenMapBuildableBitmapTextureAtlas[3],context.getAssets(),TextSource.mapNum2_3523);
		this.childrenMapITextureRegion[4] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(childrenMapBuildableBitmapTextureAtlas[4],context.getAssets(),TextSource.mapNum2_3523);
		this.childrenMapITextureRegion[5] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(childrenMapBuildableBitmapTextureAtlas[5],context.getAssets(),TextSource.mapNum2_3523);
		
		
		for(int i = 0 ;i <  6 ;i ++){
			try {
				this.childrenMapBuildableBitmapTextureAtlas[i].build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 1));
			} catch (TextureAtlasBuilderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   this.childrenMapBuildableBitmapTextureAtlas[i].load();
		}
		
		
	}
	
	
	public synchronized void loadGameGB_fourSprite_six(Engine mEngine,Context context,SmoothCamera camera)
	{   
		//子关卡背景
		this.mapShowBgSprite = new Sprite(0,0,this.mapShowBgITextureRegion,mEngine.getVertexBufferObjectManager());
		
		//子地图
		this.childreButtonSprites = new ButtonSprite[6];
		for(int i = 0 ;i < 6 ;i ++){
			this.childreButtonSprites[i] = new ButtonSprite(-CAMERA_WIDTH,
					CAMERA_HEIGHT/2 - this.childrenMapITextureRegion[i].getHeight()/2,
					this.childrenMapITextureRegion[i],mEngine.getVertexBufferObjectManager());
		}
		
	}
	
	
	public synchronized void loadGameGB_fourTexture_seven(Engine engine, Context context)
	{ 
		//子关卡背景
		this.mapShowBgBuildableBitmapTextureAtlas = new BuildableBitmapTextureAtlas(engine.getTextureManager(),512,512,TextureOptions.BILINEAR);
		this.mapShowBgITextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mapShowBgBuildableBitmapTextureAtlas, context.getAssets(),TextSource.bg_twoPng);
		try {
			this.mapShowBgBuildableBitmapTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 0));
		} catch (TextureAtlasBuilderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.mapShowBgBuildableBitmapTextureAtlas.load();
		
		//子关卡
		this.childrenMapBuildableBitmapTextureAtlas = new BuildableBitmapTextureAtlas[6];
		this.childrenMapITextureRegion = new ITextureRegion[6];
		for(int i = 0 ;i < 6 ;i ++){
			this.childrenMapBuildableBitmapTextureAtlas[i] = new BuildableBitmapTextureAtlas(engine.getTextureManager(),256,256);
		}
		this.childrenMapITextureRegion[0] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(childrenMapBuildableBitmapTextureAtlas[0],context.getAssets(),TextSource.mapNum2_3523);
		this.childrenMapITextureRegion[1] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(childrenMapBuildableBitmapTextureAtlas[1],context.getAssets(),TextSource.mapNum2_3523);
		this.childrenMapITextureRegion[2] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(childrenMapBuildableBitmapTextureAtlas[2],context.getAssets(),TextSource.mapNum2_3523);
		this.childrenMapITextureRegion[3] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(childrenMapBuildableBitmapTextureAtlas[3],context.getAssets(),TextSource.mapNum2_3523);
		this.childrenMapITextureRegion[4] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(childrenMapBuildableBitmapTextureAtlas[4],context.getAssets(),TextSource.mapNum2_3523);
		this.childrenMapITextureRegion[5] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(childrenMapBuildableBitmapTextureAtlas[5],context.getAssets(),TextSource.mapNum2_3523);
		
		
		for(int i = 0 ;i <  6 ;i ++){
			try {
				this.childrenMapBuildableBitmapTextureAtlas[i].build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 1));
			} catch (TextureAtlasBuilderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   this.childrenMapBuildableBitmapTextureAtlas[i].load();
		}
		
		
	}
	
	
	public synchronized void loadGameGB_fourSprite_seven(Engine mEngine,Context context,SmoothCamera camera)
	{   
		//子关卡背景
		this.mapShowBgSprite = new Sprite(0,0,this.mapShowBgITextureRegion,mEngine.getVertexBufferObjectManager());
		
		//子地图
		this.childreButtonSprites = new ButtonSprite[6];
		for(int i = 0 ;i < 6 ;i ++){
			this.childreButtonSprites[i] = new ButtonSprite(-CAMERA_WIDTH,
					CAMERA_HEIGHT/2 - this.childrenMapITextureRegion[i].getHeight()/2,
					this.childrenMapITextureRegion[i],mEngine.getVertexBufferObjectManager());
		}
		
	}
	
	
	public synchronized void loadGameGB_fourTexture_eight(Engine engine, Context context)
	{ 
		//子关卡背景
		this.mapShowBgBuildableBitmapTextureAtlas = new BuildableBitmapTextureAtlas(engine.getTextureManager(),512,512,TextureOptions.BILINEAR);
		this.mapShowBgITextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mapShowBgBuildableBitmapTextureAtlas, context.getAssets(),TextSource.bg_twoPng);
		try {
			this.mapShowBgBuildableBitmapTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 0));
		} catch (TextureAtlasBuilderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.mapShowBgBuildableBitmapTextureAtlas.load();
		
		//子关卡
		this.childrenMapBuildableBitmapTextureAtlas = new BuildableBitmapTextureAtlas[3];
		this.childrenMapITextureRegion = new ITextureRegion[3];
		for(int i = 0 ;i < 3 ;i ++){
			this.childrenMapBuildableBitmapTextureAtlas[i] = new BuildableBitmapTextureAtlas(engine.getTextureManager(),256,256);
		}
		this.childrenMapITextureRegion[0] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(childrenMapBuildableBitmapTextureAtlas[0],context.getAssets(),TextSource.mapNum2_3523);
		this.childrenMapITextureRegion[1] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(childrenMapBuildableBitmapTextureAtlas[1],context.getAssets(),TextSource.mapNum2_3523);
		this.childrenMapITextureRegion[2] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(childrenMapBuildableBitmapTextureAtlas[2],context.getAssets(),TextSource.mapNum2_3523);
	
		
		for(int i = 0 ;i <  3 ;i ++){
			try {
				this.childrenMapBuildableBitmapTextureAtlas[i].build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 1));
			} catch (TextureAtlasBuilderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   this.childrenMapBuildableBitmapTextureAtlas[i].load();
		}
		
		
	}
	
	
	public synchronized void loadGameGB_fourSprite_eight(Engine mEngine,Context context,SmoothCamera camera)
	{   
		//子关卡背景
		this.mapShowBgSprite = new Sprite(0,0,this.mapShowBgITextureRegion,mEngine.getVertexBufferObjectManager());
		
		//子地图
		this.childreButtonSprites = new ButtonSprite[3];
		for(int i = 0 ;i < 3 ;i ++){
			this.childreButtonSprites[i] = new ButtonSprite(-CAMERA_WIDTH,
					CAMERA_HEIGHT/2 - this.childrenMapITextureRegion[i].getHeight()/2,
					this.childrenMapITextureRegion[i],mEngine.getVertexBufferObjectManager());
		}
		
	}
	
	
	public synchronized void loadGameGB_fourTexture_nine(Engine engine, Context context)
	{ 
		//子关卡背景
		this.mapShowBgBuildableBitmapTextureAtlas = new BuildableBitmapTextureAtlas(engine.getTextureManager(),512,512,TextureOptions.BILINEAR);
		this.mapShowBgITextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mapShowBgBuildableBitmapTextureAtlas, context.getAssets(),TextSource.bg_twoPng);
		try {
			this.mapShowBgBuildableBitmapTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 0));
		} catch (TextureAtlasBuilderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.mapShowBgBuildableBitmapTextureAtlas.load();
		
		//子关卡
		this.childrenMapBuildableBitmapTextureAtlas = new BuildableBitmapTextureAtlas[6];
		this.childrenMapITextureRegion = new ITextureRegion[6];
		for(int i = 0 ;i < 6 ;i ++){
			this.childrenMapBuildableBitmapTextureAtlas[i] = new BuildableBitmapTextureAtlas(engine.getTextureManager(),256,256);
		}
		this.childrenMapITextureRegion[0] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(childrenMapBuildableBitmapTextureAtlas[0],context.getAssets(),TextSource.mapNum1_09);
		this.childrenMapITextureRegion[1] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(childrenMapBuildableBitmapTextureAtlas[1],context.getAssets(),TextSource.mapNum1_016);
		this.childrenMapITextureRegion[2] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(childrenMapBuildableBitmapTextureAtlas[2],context.getAssets(),TextSource.mapNum1_01);
		this.childrenMapITextureRegion[3] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(childrenMapBuildableBitmapTextureAtlas[3],context.getAssets(),TextSource.mapNum1_018);
		this.childrenMapITextureRegion[4] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(childrenMapBuildableBitmapTextureAtlas[4],context.getAssets(),TextSource.mapNum1_3530);
		this.childrenMapITextureRegion[5] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(childrenMapBuildableBitmapTextureAtlas[5],context.getAssets(),TextSource.mapNum1_00);
		
		
		for(int i = 0 ;i <  6 ;i ++){
			try {
				this.childrenMapBuildableBitmapTextureAtlas[i].build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 1));
			} catch (TextureAtlasBuilderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   this.childrenMapBuildableBitmapTextureAtlas[i].load();
		}
		
		
	}
	
	
	public synchronized void loadGameGB_fourSprite_nine(Engine mEngine,Context context,SmoothCamera camera)
	{   
		//子关卡背景
		this.mapShowBgSprite = new Sprite(0,0,this.mapShowBgITextureRegion,mEngine.getVertexBufferObjectManager());
		
		//子地图
		this.childreButtonSprites = new ButtonSprite[6];
		for(int i = 0 ;i < 6 ;i ++){
			this.childreButtonSprites[i] = new ButtonSprite(-CAMERA_WIDTH,
					CAMERA_HEIGHT/2 - this.childrenMapITextureRegion[i].getHeight()/2,
					this.childrenMapITextureRegion[i],mEngine.getVertexBufferObjectManager());
		}
		
	}
	
	
	public synchronized void loadGameGB_fourTexture_ten(Engine engine, Context context)
	{ 
		//子关卡背景
		this.mapShowBgBuildableBitmapTextureAtlas = new BuildableBitmapTextureAtlas(engine.getTextureManager(),512,512,TextureOptions.BILINEAR);
		this.mapShowBgITextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mapShowBgBuildableBitmapTextureAtlas, context.getAssets(),TextSource.bg_twoPng);
		try {
			this.mapShowBgBuildableBitmapTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 0));
		} catch (TextureAtlasBuilderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.mapShowBgBuildableBitmapTextureAtlas.load();
		
		//子关卡
		this.childrenMapBuildableBitmapTextureAtlas = new BuildableBitmapTextureAtlas[6];
		this.childrenMapITextureRegion = new ITextureRegion[6];
		for(int i = 0 ;i < 6 ;i ++){
			this.childrenMapBuildableBitmapTextureAtlas[i] = new BuildableBitmapTextureAtlas(engine.getTextureManager(),256,256);
		}
		this.childrenMapITextureRegion[0] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(childrenMapBuildableBitmapTextureAtlas[0],context.getAssets(),TextSource.mapNum1_09);
		this.childrenMapITextureRegion[1] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(childrenMapBuildableBitmapTextureAtlas[1],context.getAssets(),TextSource.mapNum1_016);
		this.childrenMapITextureRegion[2] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(childrenMapBuildableBitmapTextureAtlas[2],context.getAssets(),TextSource.mapNum1_01);
		this.childrenMapITextureRegion[3] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(childrenMapBuildableBitmapTextureAtlas[3],context.getAssets(),TextSource.mapNum1_018);
		this.childrenMapITextureRegion[4] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(childrenMapBuildableBitmapTextureAtlas[4],context.getAssets(),TextSource.mapNum1_3530);
		this.childrenMapITextureRegion[5] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(childrenMapBuildableBitmapTextureAtlas[5],context.getAssets(),TextSource.mapNum1_00);
		
		
		for(int i = 0 ;i <  6 ;i ++){
			try {
				this.childrenMapBuildableBitmapTextureAtlas[i].build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 1));
			} catch (TextureAtlasBuilderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   this.childrenMapBuildableBitmapTextureAtlas[i].load();
		}
		
		
	}
	
	
	public synchronized void loadGameGB_fourSprite_ten(Engine mEngine,Context context,SmoothCamera camera)
	{   
		//子关卡背景
		this.mapShowBgSprite = new Sprite(0,0,this.mapShowBgITextureRegion,mEngine.getVertexBufferObjectManager());
		
		//子地图
		this.childreButtonSprites = new ButtonSprite[6];
		for(int i = 0 ;i < 6 ;i ++){
			this.childreButtonSprites[i] = new ButtonSprite(-CAMERA_WIDTH,
					CAMERA_HEIGHT/2 - this.childrenMapITextureRegion[i].getHeight()/2,
					this.childrenMapITextureRegion[i],mEngine.getVertexBufferObjectManager());
		}
		
	}
	
	

	
	//初始化游戏背景one(字体闪烁效果pushstart)
	private void initGamebg_one()
	{   
		final LoopEntityModifier entityModifier = new LoopEntityModifier(new IEntityModifierListener() {
			
			@Override
			public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
				// TODO Auto-generated method stub
				
			}
		}, 5000, new ILoopEntityModifierListener() {
			
			@Override
			public void onLoopStarted(LoopModifier<IEntity> arg0, int arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onLoopFinished(LoopModifier<IEntity> arg0, int arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
		}, new SequenceEntityModifier(	
				new AlphaModifier(1, 0.0f, 1.0f),
				new AlphaModifier(1, 1.0f, 0.0f)
		));
		
		pushstartText.registerEntityModifier(entityModifier);
		pushstartText.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
	}
	
	
	
	
}

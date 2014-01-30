package beichenming.pocketmonster;

import org.andengine.engine.Engine;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;

import android.content.Context;
import beichenming.manger.TextSource;

public class LoadGame {
	//Loading游戏
	private BitmapTextureAtlas loadingBitmapTextureAtlas;
	private ITextureRegion loadingITextureRegion;
	private Sprite loadingSprite;
	private Text loadingText;
	private Font loadinggameFont; //加载资源字体
	
	public LoadGame(Engine engine,Context context){
		
		//游戏font初始化
	    this.loadinggameFont = FontFactory.createFromAsset(engine.getFontManager(), engine.getTextureManager(), 128, 32,TextureOptions.BILINEAR, context.getAssets(), TextSource.hwcyFont, 20f, true,org.andengine.util.color.Color.WHITE_ABGR_PACKED_INT);
	    this.loadinggameFont.load();
	    
		//游戏载入loading界面
        this.loadingBitmapTextureAtlas = new BitmapTextureAtlas(engine.getTextureManager(), 512,512,TextureOptions.BILINEAR);
        this.loadingITextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.loadingBitmapTextureAtlas, context, TextSource.bg_fourPng,0,0); 
        this.loadingBitmapTextureAtlas.load();
		
        float centx2 = 103;
 	    float centy2 = (320 - 30*2);  
 	    
        //游戏载入loading设置
        this.loadingSprite = new Sprite(0, 0, this.loadingITextureRegion, engine.getVertexBufferObjectManager());
        this.loadingText = new Text(2*centx2 - 6, centy2, loadinggameFont,TextSource.loadTextString, engine.getVertexBufferObjectManager());   
	}
	
	//加载资源
	public void loadGame(Scene scene)
	{
		scene.getChildByIndex(0).attachChild(loadingSprite);
		scene.getChildByIndex(0).attachChild(loadingText);
	}
	
	
	//释放资源
	public void unloadSource()
	{
	    this.loadingBitmapTextureAtlas.unload();
	    this.loadinggameFont.unload();
	    System.gc();
	}
	
}

package beichenming.manger;

import javax.microedition.khronos.opengles.GL10;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.SmoothCamera;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.shape.IShape;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.AnimatedSprite.IAnimationListener;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.vbo.ISpriteVertexBufferObject;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.R.integer;
import android.content.Context;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;


public class MyBox implements IConstants , ISprite{
	
	 protected static final int LAYER_BGROUND = 0;    //背景图层one
	 protected static final int LAYER_MENU = LAYER_BGROUND + 1;  //背景图层two
	 private static final int LAYER_MAN = LAYER_MENU + 1;
	 private static final int LAYER_TREE = LAYER_MAN + 1;
	 private static final int LAYER_SCORE = LAYER_TREE + 1;	
     private Body boxBody;                       //body刚体       
     private Sprite boxSprite;                  //box的sprite
     private int boxX;                          //坐标X
     private int boxY;                        //坐标Y
     private int mState;                      //盒子状态
     private int Style;                       //盒子种类
     private PhysicsConnector mPhysicsConnector;        //物理世界
     public static Sourcemanger gameSourceManager = Sourcemanger.getInstance();     //资源
     private Scene boxScene;
     private PhysicsWorld boxPhysicsWorld;
     private BuildableBitmapTextureAtlas deleteBoxBuildableBitmapTextureAtlas;         //box删除动画
     private TiledTextureRegion  deleteBoxTiledTextureRegion;
     private AnimatedSprite deleteBoxAnimatedSprite;                                 
     
     
     
     public MyBox(int x,int y)
     {   
    	 this.boxX  = x;
    	 this.boxY  = y;
    	 this.mState = STATE_NORMAL;
     }
     
     
       /*
        * 创建Box的body和sprite
        */
     public void setMyBox(ITextureRegion mBoxTextureRegion,Engine engine,PhysicsWorld mPhysicsWorld,Scene scene,Context context)
     {
    	 this.boxSprite = new Sprite(boxX, boxY, mBoxTextureRegion,engine.getVertexBufferObjectManager());
    	 this.boxSprite.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
    	 final FixtureDef boxFixtureDef = PhysicsFactory.createFixtureDef(0, 0.5f, 1f);
		 this.boxBody = PhysicsFactory.createBoxBody(mPhysicsWorld,boxSprite,BodyType.StaticBody, boxFixtureDef);
//	     mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(boxSprite, boxBody, true, false));
	     deleteBoxBuildableBitmapTextureAtlas = new BuildableBitmapTextureAtlas(engine.getTextureManager(),448,128,TextureOptions.BILINEAR);
	 	 deleteBoxTiledTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(deleteBoxBuildableBitmapTextureAtlas,context,"specialAnimation_four.png", 4, 1);
	 	 
	 	 try {
			this.deleteBoxBuildableBitmapTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 0));
		} catch (TextureAtlasBuilderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 	 deleteBoxBuildableBitmapTextureAtlas.load();
	 	 deleteBoxAnimatedSprite = new AnimatedSprite(boxX,boxY - 50,deleteBoxTiledTextureRegion,engine.getVertexBufferObjectManager());
	 	 scene.getChildByIndex(LAYER_MAN).attachChild(deleteBoxAnimatedSprite);
	 	 deleteBoxAnimatedSprite.setVisible(false);
	 	 this.boxPhysicsWorld = mPhysicsWorld;
	     this.boxScene = scene;
     }
     
     public void setMyBox2(ITextureRegion mBoxTextureRegion,Engine engine,PhysicsWorld mPhysicsWorld,Scene scene,Context context)
     {
    	 this.boxSprite = new Sprite(boxX, boxY, mBoxTextureRegion,engine.getVertexBufferObjectManager());
    	 this.boxSprite.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
//    	 final FixtureDef boxFixtureDef = PhysicsFactory.createFixtureDef(0, 0.5f, 1f);
//		 this.boxBody = PhysicsFactory.createBoxBody(mPhysicsWorld,boxSprite,BodyType.StaticBody, boxFixtureDef);
//	     mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(boxSprite, boxBody, true, false));
	     deleteBoxBuildableBitmapTextureAtlas = new BuildableBitmapTextureAtlas(engine.getTextureManager(),448,128);
	 	 deleteBoxTiledTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(deleteBoxBuildableBitmapTextureAtlas,context,"specialAnimation_four.png", 4, 1);
	 	 
	 	 try {
			this.deleteBoxBuildableBitmapTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 0));
		} catch (TextureAtlasBuilderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 	 deleteBoxBuildableBitmapTextureAtlas.load();
	 	 deleteBoxAnimatedSprite = new AnimatedSprite(boxX,boxY - 50,deleteBoxTiledTextureRegion,engine.getVertexBufferObjectManager());
	 	 scene.getChildByIndex(LAYER_MAN).attachChild(deleteBoxAnimatedSprite);
	 	 deleteBoxAnimatedSprite.setVisible(false);
	 	 this.boxPhysicsWorld = mPhysicsWorld;
	     this.boxScene = scene;
     }
     
     
      /*
       * 移除box的body和sprite
       */
 	public void removeBox() {
//		final PhysicsConnector facePhysicsConnector = boxPhysicsWorld.getPhysicsConnectorManager().findPhysicsConnectorByShape(boxSprite);
//		boxPhysicsWorld.unregisterPhysicsConnector(facePhysicsConnector);
//		boxPhysicsWorld.destroyBody(facePhysicsConnector.getBody());
 		boxPhysicsWorld.destroyBody(boxBody);
        boxScene.getChildByIndex(LAYER_MAN).detachChild(boxSprite);
		System.gc();
	}
    
 	
 	/*
 	 * box移除特效 
 	 */
 	public void removeAnimate(){
 	   gameSourceManager.specialAnimationAnimatedSprite[0].setPosition(boxX - 7, boxY + 2);
 	   gameSourceManager.specialAnimationAnimatedSprite[0].setVisible(true);
 	   gameSourceManager.bodySprites[Style].setVisible(true);
 	   long[] t = new long[18];
       for(int i = 0;i < 18 ;i ++)
    	  t[i] = 30;
       
 	   gameSourceManager.specialAnimationAnimatedSprite[0].animate(t,0, new IAnimationListener() {
		
		@Override
		public void onAnimationStarted(AnimatedSprite arg0, int arg1) {
			// TODO Auto-generated method stub
			gameSourceManager.bodySprites[Style].registerEntityModifier(new ParallelEntityModifier(
					new ScaleModifier(0.5f,0.0f,1.0f),
					new AlphaModifier(0.5f,0.0f,1.0f)));
			
			boxSprite.registerEntityModifier(new ParallelEntityModifier(
					new ScaleModifier(0.5f,1.0f,0.0f),
					new AlphaModifier(0.5f,1.0f,0.0f)));
		}
		
		@Override
		public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onAnimationFinished(AnimatedSprite arg0) {
			// TODO Auto-generated method stub
			removeBox();  		
			arg0.setVisible(false);
		}
 	 });
 	}
 	
 	
 	//生成box动画
 	public void brithAnimate()
 	{
 	   gameSourceManager.specialAnimationAnimatedSprite[1].setPosition(boxX - 5, boxY);
 	   long[] t = new long[20];
       for(int i = 0;i < 20 ;i ++)
    	  t[i] = 25;
       
 	   gameSourceManager.specialAnimationAnimatedSprite[1].animate(t,0, new IAnimationListener() {
		
		@Override
		public void onAnimationStarted(AnimatedSprite arg0, int arg1) {
			// TODO Auto-generated method stub
		  gameSourceManager.specialAnimationAnimatedSprite[1].setVisible(true);
		  gameSourceManager.bodySprites[Style].registerEntityModifier(new ParallelEntityModifier(
					new ScaleModifier(0.5f,1.0f,0.0f),
					new AlphaModifier(0.5f,1.0f,0.0f)));
			
	       boxSprite.registerEntityModifier(new ParallelEntityModifier(
	    			new ScaleModifier(0.5f,0.0f,1.0f),
					new AlphaModifier(0.5f,0.0f,1.0f))); 		
		}
		
		@Override
		public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onAnimationFinished(AnimatedSprite arg0) {
			// TODO Auto-generated method stub
		 	gameSourceManager.bodySprites[Style].setVisible(false);
			GameStatus.STATE = GameStatus.CHECK;
			arg0.setVisible(false);
		}
  	 });
 	}
 	
 	
 	
 	/*
 	 * box删除时特效
 	 */
 	public void deleteBoxAnimate(){  
 	   this.deleteBoxAnimatedSprite.setVisible(true);
 	   long[] t = new long[4];
 	   for(int i = 0 ;i < 4 ;i ++){
 		   t[i] = 250;
 	   }
 	   this.deleteBoxAnimatedSprite.animate(t, 0, new IAnimationListener() {
		
		@Override
		public void onAnimationStarted(AnimatedSprite arg0, int arg1) {
			// TODO Auto-generated method stub
			boxSprite.registerEntityModifier(new ParallelEntityModifier(
			 new ScaleModifier(0.5f,1.0f,0.0f),
			 new AlphaModifier(0.5f,1.0f,0.0f)));
		}
		
		@Override
		public void onAnimationLoopFinished(AnimatedSprite arg0, int arg1, int arg2) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onAnimationFrameChanged(AnimatedSprite arg0, int arg1, int arg2) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onAnimationFinished(AnimatedSprite arg0) {
			// TODO Auto-generated method stub
		   removeBox();  	
	       deleteBoxAnimatedSprite.setVisible(false);
		}
	 });
// 	     deleteBoxBuildableBitmapTextureAtlas.unload();
//       boxScene.getChildByIndex(LAYER_MAN).detachChild(deleteBoxAnimatedSprite); 
 	}
  	
 	
 	
 	/*
 	 * 提示Box的放大效果
 	 */
 	public void boxPrompt(){
 		
 		this.boxSprite.registerEntityModifier(new SequenceEntityModifier(
 				new ScaleModifier(0.5f,1.0f,1.2f),
 				new ScaleModifier(0.5f,1.2f,1.0f)));
 	}
 	
 	
     
	@Override
	public int getRow() {
		// TODO Auto-generated method stub
		return boxX;
	}

	@Override
	public int getCol() {
		// TODO Auto-generated method stub
		return boxY;
	}

	@Override
	public void setMapPosition(int X, int Y) {
		// TODO Auto-generated method stub
		this.boxSprite.setPosition(X, Y);
	}

	public Body getBoxBody() {
		return boxBody;
	}

	public void setBoxBody(Body boxBody) {
		this.boxBody = boxBody;
	}

	public Sprite getBoxSprite() {
		return boxSprite;
	}

	public void setBoxSprite(Sprite boxSprite) {
		this.boxSprite = boxSprite;
	}

	public int getStyle() {
		return Style;
	}

	public void setStyle(int Style) {
		this.Style = Style;
	}
	
	
	public int getState() {
		return mState;
	}


	public void setState(int mState) {
		this.mState = mState;
	}
   

}

package beichenming.manger;

import java.util.ArrayList;

import android.R.id;

public class MapArrayList {
   public static final int[] MapW = new int[100];
   public static final int[] MapH = new int[100];
   public static final int[] MapStartX  = new int[100];
   public static final int[] MapStartY = new int[100];
   public static final int[] MapBoxWidth = new int[100];
   public static final int[] MapBoxHeight = new int[100];
   public static final int[] MapWidth = new int[100];
   public static final int[] MapHeight = new int[100];
   public static final int[] MapWidthAll  = new int[100];
   public static final int[] MapHeightAll = new int[100];
   
   public static final int MapAllnum = 10;   
   
   public MapArrayList()
   {
	   //map1_0.9
	   this.MapW[0] = 9;
       this.MapH[0] = 9;
	   this.MapStartX[0] = 132;
	   this.MapStartY[0] = 54;
	   this.MapBoxWidth[0] = 30;
	   this.MapBoxHeight[0] = 30;
	   this.MapWidth[0] = MapStartX[0] + MapW[0] * MapBoxWidth[0];
	   this.MapHeight[0] = MapStartY[0] + MapH[0] * MapBoxHeight[0];
	   this.MapWidthAll[0] = 498;
	   this.MapHeightAll[0] = 348;
	   
	   //map1_0.10
	   this.MapW[2] = 11;
       this.MapH[2] = 9;
	   this.MapStartX[2] = 78;
	   this.MapStartY[2] = 24;
	   this.MapBoxWidth[2] = 30;
	   this.MapBoxHeight[2] = 30;
	   this.MapWidth[2] = MapStartX[2] + MapW[2] * MapBoxWidth[2];
	   this.MapHeight[2] = MapStartY[2] + MapH[2] * MapBoxHeight[2];
	   this.MapWidthAll[2] = 504;
	   this.MapHeightAll[2] = 336;
	   
	   
	   //map1_0.16
	   this.MapW[1] = 17;
       this.MapH[1] = 6;
	   this.MapStartX[1] = 36;
	   this.MapStartY[1] = 36;
	   this.MapBoxWidth[1] = 30;
	   this.MapBoxHeight[1] = 30;
	   this.MapWidth[1] = MapStartX[1] + MapW[1] * MapBoxWidth[1];
	   this.MapHeight[1] = MapStartY[1] + MapH[1] * MapBoxHeight[1];
	   this.MapWidthAll[1] = 558;
	   this.MapHeightAll[1] = 336;
	   
	   //map1_0.18
	   this.MapW[3] = 9;
       this.MapH[3] = 12;
	   this.MapStartX[3] = 66;
	   this.MapStartY[3] = 138;
	   this.MapBoxWidth[3] = 30;
	   this.MapBoxHeight[3] = 30;
	   this.MapWidth[3] = MapStartX[3] + MapW[3] * MapBoxWidth[3];
	   this.MapHeight[3] = MapStartY[3] + MapH[3] * MapBoxHeight[3];
	   this.MapWidthAll[3] = 600;
	   this.MapHeightAll[3] = 660;
	   
	   //map1_35.30
	   this.MapW[4] = 7;
       this.MapH[4] = 9;
	   this.MapStartX[4] = 126;
	   this.MapStartY[4] = 24;
	   this.MapBoxWidth[4] = 30;
	   this.MapBoxHeight[4] = 30;
	   this.MapWidth[4] = MapStartX[4] + MapW[4] * MapBoxWidth[4];
	   this.MapHeight[4] = MapStartY[4] + MapH[4] * MapBoxHeight[4];
	   this.MapWidthAll[4] = 498;
	   this.MapHeightAll[4] = 336;
	   
	   //map1_0.0
	   this.MapW[5] = 9;
       this.MapH[5] = 10;
	   this.MapStartX[5] = 252;
	   this.MapStartY[5] = 174;
	   this.MapBoxWidth[5] = 30;
	   this.MapBoxHeight[5] = 30;
	   this.MapWidth[5] = MapStartX[5] + MapW[5] * MapBoxWidth[5];
	   this.MapHeight[5] = MapStartY[5] + MapH[5] * MapBoxHeight[5];
	   this.MapWidthAll[5] = 600;
	   this.MapHeightAll[5] = 498;
	   
	   
	 //map2_26.56
	   this.MapW[6] = 8;
       this.MapH[6] = 7;
	   this.MapStartX[6] = 204;
	   this.MapStartY[6] = 18;
	   this.MapBoxWidth[6] = 30;
	   this.MapBoxHeight[6] = 30;
	   this.MapWidth[6] = MapStartX[6] + MapW[6] * MapBoxWidth[6];
	   this.MapHeight[6] = MapStartY[6] + MapH[6] * MapBoxHeight[6];
	   this.MapWidthAll[6] = 540;
	   this.MapHeightAll[6] = 732;
	   
   }
   
   
   
  /*
   * Map_one获取人物方向 
   */
  public boxXY MapboxDire(float x,float y,int direction,int index)
   {
	  if(direction == 1){
		return  getMapdirection1(x,y,index);
	  }else if(direction == 2){
		return  getMapdirection2(x,y,index);
	  }else if(direction == 3){
		return  getMapdirection3(x, y,index);
	  }else{
		return getMapdirection4(x, y,index);
	  } 

   }
    

    //down
    public boxXY getMapdirection1(float x,float y,int index)
    {   
	    for(int i = MapStartX[index] ; i < MapStartX[index] + MapW[index] * MapBoxWidth[index] ; i += MapBoxWidth[index])
	     for(int j = MapStartY[index] - MapBoxHeight[index] ; j < MapStartY[index] + MapH[index] * MapHeight[index] ; j += MapBoxHeight[index])
	     {
	    	 if(x >= i && (x + 20) <= i + MapBoxWidth[index] && y >= j && y <= j + MapBoxHeight[index])
	    	 {
	    		return new boxXY(i,j + MapBoxHeight[index]); 
	    	 }
	     }
	    return null;
    }
   
    
    //left
   public boxXY getMapdirection2(float x,float y,int index)
   { 
	   y += 10;
	   for(int i = MapStartX[index] ; i < MapStartX[index] + MapW[index] * MapBoxWidth[index] ; i += MapBoxWidth[index])
		  for(int j = MapStartY[index] ; j < MapStartY[index] + MapH[index] * MapHeight[index] ; j += MapBoxHeight[index])
		     {
			   if(x >= i && x <= i + MapBoxWidth[index] && y >= j && (y + 10) <= j + MapBoxHeight[index])
		    	 {
		    		return new boxXY(i - MapBoxWidth[index] ,j); 
		    	 }
		     }
		    return null;
   }
   
   
  //right
  public boxXY getMapdirection3(float x,float y,int index)
  {
	  y += 10;
	  for(int i = MapStartX[index] - MapBoxWidth[index] ; i < MapStartX[index] + MapW[index] * MapBoxWidth[index] ; i += MapBoxWidth[index])
		  for(int j = MapStartY[index] ; j < MapStartY[index] + MapH[index] * MapHeight[index] ; j += MapBoxHeight[index])
		     {
			   if(x >= i && x <= (i + MapBoxWidth[index]) && y >= j && (y + 10) <= (j + MapBoxHeight[index]))
		    	 {
					  return new boxXY(i + MapBoxWidth[index] ,j); 
		    	 }
		     }
		    return null;
  }
  
  
  //up
   public boxXY getMapdirection4(float x,float y,int index)
   {
	   y += 13;
	   for(int i = MapStartX[index] ; i < MapStartX[index] + MapW[index] * MapBoxWidth[index] ; i += MapBoxWidth[index])
		 for(int j = MapStartY[index] ; j < MapStartY[index] + MapH[index] * MapHeight[index] ; j += MapBoxHeight[index])
			 {
			   if(x >= i && (x + 20) <= i + MapBoxWidth[index] && y >= j && y <= j + MapBoxHeight[index])
			    {
			    	return new boxXY(i ,j - MapBoxHeight[index]); 
			    }
			}
	   return null;
   }
   
  
   
   
}

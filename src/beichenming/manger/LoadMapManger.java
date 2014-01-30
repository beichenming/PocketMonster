package beichenming.manger;

import android.R.integer;
import android.R.string;

public class LoadMapManger {
    public  int Maptype;
    public  int MapIndex;
    public int Map_oneNum = 8;
	
	
    public LoadMapManger(int maptype,int mapindex){
    	this.MapIndex = mapindex;
    	this.Maptype = maptype;
    }
    
	//返回地图类型
	public  int getMapTypeNum(){
		int num  = -1;
	   if(Maptype == 0 && MapIndex == 0){
		   num = 0;
		   return num;
	   }
	   if(Maptype == 0 && MapIndex == 1){
		   num = 1 ;
		   return num;
	   }
	   if(Maptype == 0 && MapIndex == 2){
		   num = 2 ;
		   return num;
	   }
	   if(Maptype == 0 && MapIndex == 3){
		   num = 3 ;
		   return num;
	   }
	   if(Maptype == 0 && MapIndex == 4){
		   num = 4 ;
		   return num;
	   }
	   if(Maptype == 0 && MapIndex == 5){
		   num = 5 ;
		   return num;
	   }
	   
	   
	   
	   if(Maptype == 1 && MapIndex == 0){
		   num = 6 ;
		   return num;
	   }
	   if(Maptype == 1 && MapIndex == 1){
		   num = 7 ;
		   return num;
	   }
	   if(Maptype == 1 && MapIndex == 2){
		   num = 8 ;
		   return num;
	   }
	   if(Maptype == 1 && MapIndex == 3){
		   num = 9 ;
		   return num;
	   }
	   if(Maptype == 1 && MapIndex == 4){
		   num = 10 ;
		   return num;
	   }
	   
	   
	   
	   if(Maptype == 2 && MapIndex == 0){
		   num = 11 ;
		   return num;
	   }
	   if(Maptype == 2 && MapIndex == 1){
		   num = 12 ;
		   return num;
	   }
	   if(Maptype == 2 && MapIndex == 2){
		   num = 13 ;
		   return num;
	   }
	   if(Maptype == 2 && MapIndex == 3){
		   num = 14 ;
		   return num;
	   }
	   
	   
	   
	   if(Maptype == 3 && MapIndex == 0){
		   num = 15 ;
		   return num;
	   }
	   if(Maptype == 3 && MapIndex == 1){
		   num = 16 ;
		   return num;
	   }
	   if(Maptype == 3 && MapIndex == 2){
		   num = 17 ;
		   return num;
	   }
	   if(Maptype == 3 && MapIndex == 3){
		   num = 18 ;
		   return num;
	   }
	   if(Maptype == 3 && MapIndex == 4){
		   num = 19 ;
		   return num;
	   }
	   if(Maptype == 3 && MapIndex == 5){
		   num = 20 ;
		   return num;
	   }
	   
	   
	   if(Maptype == 4 && MapIndex == 0){
		   num = 21 ;
		   return num;
	   }
	   if(Maptype == 4 && MapIndex == 1){
		   num = 22 ;
		   return num;
	   }
	   if(Maptype == 4 && MapIndex == 2){
		   num = 23 ;
		   return num;
	   }
	   if(Maptype == 4 && MapIndex == 3){
		   num = 24 ;
		   return num;
	   }
	   if(Maptype == 4 && MapIndex == 4){
		   num = 25 ;
		   return num;
	   }
	   if(Maptype == 4 && MapIndex == 5){
		   num = 26 ;
		   return num;
	   }
	   
	   
	   if(Maptype == 5 && MapIndex == 0){
		   num = 27 ;
		   return num;
	   }
	   if(Maptype == 5 && MapIndex == 1){
		   num = 28 ;
		   return num;
	   }
	   if(Maptype == 5 && MapIndex == 2){
		   num = 29 ;
		   return num;
	   }
	   if(Maptype == 5 && MapIndex == 3){
		   num = 30 ;
		   return num;
	   }
	   if(Maptype == 5 && MapIndex == 4){
		   num = 31 ;
		   return num;
	   }
	   if(Maptype == 5 && MapIndex == 5){
		   num = 32 ;
		   return num;
	   }
	   
	   
	   if(Maptype == 6 && MapIndex == 0){
		   num = 33 ;
		   return num;
	   }
	   if(Maptype == 6 && MapIndex == 1){
		   num = 34 ;
		   return num;
	   }
	   if(Maptype == 6 && MapIndex == 2){
		   num = 35 ;
		   return num;
	   }
	   if(Maptype == 6 && MapIndex == 3){
		   num = 36 ;
		   return num;
	   }
	   if(Maptype == 6 && MapIndex == 4){
		   num = 37 ;
		   return num;
	   }
	   if(Maptype == 6 && MapIndex == 5){
		   num = 38 ;
		   return num;
	   }
	   
	   
	   if(Maptype == 7 && MapIndex == 0){
		   num = 39 ;
		   return num;
	   }
	   if(Maptype == 7 && MapIndex == 1){
		   num = 40 ;
		   return num;
	   }
	   if(Maptype == 7 && MapIndex == 2){
		   num = 41 ;
		   return num;
	   }
	   
	   
	   return num;
	}
	
	public  String getMapNametiled(){
		String name = null;
		
		  if(Maptype == 0 && MapIndex == 0){
               name = TextSource.title1_map1_09;
			   return name;
		   }
		  
		   if(Maptype == 0 && MapIndex == 1){
			    name = TextSource.title1_map1_016;
			   return name;
		   }
		   
		   if(Maptype == 0 && MapIndex == 2){
			    name = TextSource.title1_map1_010;
			   return name;
		   }
		   
		   if(Maptype == 0 && MapIndex == 3){
			    name = TextSource.title1_map1_018;
			   return name;
		   }
		   
		   if(Maptype == 0 && MapIndex == 4){
			    name = TextSource.title1_map1_3530;
			   return name;
		   }
		   
		   if(Maptype == 0 && MapIndex == 5){
			    name = TextSource.title1_map1_00;
			   return name;
		   }
		   
		   if(Maptype == 1 && MapIndex == 0){
			    name = TextSource.title2_map2_2656;
			   return name;
		   }
		   
		   
		   
		return name;
	}
	
	
	
	
	public  String getMapBg_one(){
		String name = null;
		 if(Maptype == 0 && MapIndex == 0){
			   name = TextSource.mapBg1_09;
			   return name;
		   }
		 if(Maptype == 0 && MapIndex == 1){
			   name = TextSource.mapBg1_016;
			   return name;
		   }
		 if(Maptype == 0 && MapIndex == 2){
			   name = TextSource.mapBg1_01;
			   return name;
		   }
		 if(Maptype == 0 && MapIndex == 3){
			   name = TextSource.mapBg1_018;
			   return name;
		   }
		 if(Maptype == 0 && MapIndex == 4){
			   name = TextSource.mapBg1_3530;
			   return name;
		   }
		 if(Maptype == 0 && MapIndex == 5){
			   name = TextSource.mapBg1_00;
			   return name;
		   }
		 if(Maptype == 1 && MapIndex == 0){
			   name = TextSource.mapBg2_2656;
			   return name;
		   }
		
		return name;	
	}
	
	
	
	public  String getMapBg_two(){
		String name = null;
		
		  if(Maptype == 0 && MapIndex == 0){
			   name = TextSource.mapBg1_09f;
			   return name;
		   }
		  if(Maptype == 0 && MapIndex == 1){
			   name = TextSource.mapBg1_016f;
			   return name;
		   } 
		  if(Maptype == 0 && MapIndex == 2){
			   name = TextSource.mapBg1_01f;
			   return name;
		   } 
		  if(Maptype == 0 && MapIndex == 3){
			   name = TextSource.mapBg1_018f;
			   return name;
		   } 
		  if(Maptype == 0 && MapIndex == 4){
			   name = TextSource.mapBg1_3530f;
			   return name;
		   } 
		  if(Maptype == 0 && MapIndex == 5){
			   name = TextSource.mapBg1_00f;
			   return name;
		   }
		  
		  if(Maptype == 1 && MapIndex == 0){
			   name = TextSource.mapBg2_2656f;
			   return name;
		   }
		  
		return name;
	}
	
	
	
	//获得地图名称
	public String getmap_name(){
		String name = null;
		
		  if(Maptype == 0 && MapIndex == 0){
			   name = TextSource.mapname1_09;
			   return name;
		   }
		  if(Maptype == 0 && MapIndex == 1){
			   name = TextSource.mapname1_016;
			   return name;
		   } 
		  if(Maptype == 0 && MapIndex == 2){
			   name = TextSource.mapname1_010;
			   return name;
		   } 
		  if(Maptype == 0 && MapIndex == 3){
			   name = TextSource.mapname1_018;
			   return name;
		   } 
		  if(Maptype == 0 && MapIndex == 4){
			   name = TextSource.mapname1_3530;
			   return name;
		   } 
		  if(Maptype == 0 && MapIndex == 5){
			   name = TextSource.mapname1_00;
			   return name;
		   }
		  if(Maptype == 1 && MapIndex == 0){
			   name = TextSource.mapname2_2656;
			   return name;
		   }
		  
		  
		return name;
		
	}
	
	
	public long getMaptime()
	{
		long time = 0;
		if(Maptype == 0 && MapIndex == 0){
			time = TextSource.timeMap1_1;
	        return time;
		}
		return time;
	}
	
	
	public long getMapscore()
	{
		long score = 0;
		if(Maptype == 0 && MapIndex == 0){
			score = TextSource.scoreMap1_1;
			return score;
		}
		return score;
	}
	
	
	
	
}

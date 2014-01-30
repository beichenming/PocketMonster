package beichenming.manger;

public class GameStatus {
	
	/**游戏状态**/
	public static boolean mGameRunning;        //游戏的总开关(可处理来电、任务切换等)
	public static boolean heroMoveing;         //人物是否移动中
	public final static int PRESS = 1;         //放置或者拿开
	public final static int FALL = 2;          //下落
	public final static int NORMAL = 3;        //正常状态
	public final static int CHECK = 0;         //执行检测
	public static int STATE = NORMAL;           //一开始就检测，没有移动命令的时候也一直检测
	
}

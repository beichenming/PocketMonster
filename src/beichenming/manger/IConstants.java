package beichenming.manger;

/**
 * @author Qingfeng
 * @time 2010-11-03
 */
public interface IConstants {
	// ===========================================================
	// Final Fields
	// ===========================================================
	
	/**钻石状�?**/
	final int STATE_NORMAL = 0;  //正常
	final int STATE_SCALEINT = STATE_NORMAL + 1; //缩放
	final int STATE_FALL = STATE_SCALEINT + 1;   //下落
	final int STATE_DEAD = STATE_FALL + 1;//死亡
	

	// ===========================================================
	// Methods
	// ===========================================================
}

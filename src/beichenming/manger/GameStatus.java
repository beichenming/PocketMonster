package beichenming.manger;

public class GameStatus {
	
	/**��Ϸ״̬**/
	public static boolean mGameRunning;        //��Ϸ���ܿ���(�ɴ������硢�����л���)
	public static boolean heroMoveing;         //�����Ƿ��ƶ���
	public final static int PRESS = 1;         //���û����ÿ�
	public final static int FALL = 2;          //����
	public final static int NORMAL = 3;        //����״̬
	public final static int CHECK = 0;         //ִ�м��
	public static int STATE = NORMAL;           //һ��ʼ�ͼ�⣬û���ƶ������ʱ��Ҳһֱ���
	
}

package tetris;

public class GameThread extends Thread {
	private GameArea ga;

	public GameThread(GameArea ga) {
		this.ga = ga;
	}

	@Override
	public void run() {
		
		// ����� 1�ʸ��� 1ĭ�� ���������� 
		while(true) {
			try {
				ga.moveBlockDown();
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		
	}
	
}

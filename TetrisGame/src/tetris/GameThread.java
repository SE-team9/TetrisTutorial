package tetris;

public class GameThread extends Thread {
	private GameArea ga;
	private GameForm gf;
	private int score;
	private int level = 1;
	private int scorePerLevel = 3;
	
	private int pause = 1000;
	private int speedupPerLevel = 100;
	
	public GameThread(GameArea ga, GameForm gf) {
		this.ga = ga;
		this.gf = gf;
		
		gf.updateScore(score);
		gf.updateLevel(level);
	}

	@Override
	public void run() {
		
		// ����� 1�ʸ��� 1ĭ�� ����������
		while(true) {
			ga.spawnBlock(); // ���ο� ��� ���� 
			
			while(ga.moveBlockDown()) {
				try {
					Thread.sleep(pause);	
					
				} catch (InterruptedException e) {
					// ���� �޴� ��ư�� ������ GameThread�� ���ͷ�Ʈ �Ǹ� 
					// �� run �Լ��� ������ ����ǵ���!
					return; 
				}
			}
			
			// ����� ������ ��踦 �Ѿ�� ���� ���� 
			if(ga.isBlockOutOfBounds()) {
				Tetris.gameOver(score);
				break;
			}
			
			// ����� �ٴڿ� ������� ������ ������ ���� ���� ���, ��׶��� ������� ��ȯ 
			ga.moveBlockToBackground();
			
			// ������ ���� ������ŭ ���� ����
		 	score += ga.clearLines();
		 	gf.updateScore(score);
		 	
		 	// scorePerLevel ��ŭ ���� ������ ���� ��� 
		 	int lvl = score / scorePerLevel + 1;
		 	if(lvl > level) {
		 		level = lvl;
		 		gf.updateLevel(level);
		 		pause -= speedupPerLevel; // �ӵ� ����
		 	}
		 	
		}
	}
}
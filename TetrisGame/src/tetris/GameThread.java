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
	}

	@Override
	public void run() {
		
		// 블록이 1초마다 1칸씩 떨어지도록
		while(true) {
			ga.spawnBlock(); // 새로운 블록 생성 
			
			while(ga.moveBlockDown()) {
				try {
					Thread.sleep(pause);	
					
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			// 블록이 보드판 경계를 넘어가면 게임 종료 
			if(ga.isBlockOutOfBounds()) {
				System.out.println("Game Over");
				break;
			}
			
			// 블록이 바닥에 닿았지만 보드판 영역을 넘지 않은 경우, 백그라운드 블록으로 전환 
			ga.moveBlockToBackground();
			
			// 삭제된 행의 개수만큼 점수 증가
		 	score += ga.clearLines();
		 	gf.updateScore(score);
		 	
		 	// scorePerLevel 만큼 점수 얻으면 레벨 상승 
		 	int lvl = score / scorePerLevel + 1;
		 	if(lvl > level) {
		 		level = lvl;
		 		gf.updateLevel(level);
		 		pause -= speedupPerLevel; // 속도 증가
		 	}
		 	
		}
	}
}
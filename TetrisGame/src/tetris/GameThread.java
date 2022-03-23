package tetris;

public class GameThread extends Thread {
	private GameArea ga;

	public GameThread(GameArea ga) {
		this.ga = ga;
	}

	@Override
	public void run() {
		
		// 블록이 1초마다 1칸씩 떨어지도록
		while(true) {
			ga.spawnBlock(); // 새로운 블록 생성 
			
			while(ga.moveBlockDown()) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
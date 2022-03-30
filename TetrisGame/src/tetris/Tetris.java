package tetris;

import java.awt.EventQueue;

import javax.swing.JOptionPane;

public class Tetris {
	private static GameForm gf;
	private static StartupForm sf;
	private static LeaderboardForm lf;
	
	private static AudioPlayer audio = new AudioPlayer();
	
	public static void start() {
		gf.setVisible(true);
		gf.startGame();
	}
	
	public static void showLeaderboard() {
		lf.setVisible(true);
	}
	
	public static void showStartup() {
		sf.setVisible(true);
	}
	
	// ���� ����Ǹ� ���̾�α׿��� �̸� �Է� �޾Ƽ� ������ �Բ� �����ֱ�
	public static void gameOver(int score) {
		playGameover();
		
		String playerName = JOptionPane.showInputDialog("Game Over!\n Please enter your name.");
		gf.setVisible(false);
		
		lf.addPlayer(playerName, score);
	}
	
	public static void playClear() {
		audio.playClearLine();
	}
	
	public static void playGameover() {
		audio.playGameover();
	}
	
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					gf = new GameForm();
					sf = new StartupForm();
					lf = new LeaderboardForm();
					
					sf.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}

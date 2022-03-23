package tetris;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

public class GameForm extends JFrame {
	private JPanel gameAreaPlaceholder;
	private GameArea ga;
	
	private final static int WIDTH = 600;
	private final static int HEIGHT = 450;
	
	// Create the frame.
	public GameForm() {
		gameAreaPlaceholder = new JPanel();
		
		gameAreaPlaceholder.setBounds(200, 0, 200, 400);
		gameAreaPlaceholder.setBorder(LineBorder.createBlackLineBorder());
		gameAreaPlaceholder.setLayout(null);
		
		ga = new GameArea(gameAreaPlaceholder, 10);
		this.add(ga);
		
		startGame();
	}
	
	// 게임 스레드 시작 
	public void startGame() {
		new GameThread(ga).start();
	}

	// Launch the application.
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameForm f = new GameForm();
					f.setSize(WIDTH, HEIGHT);
					f.setLayout(null);
					f.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
}

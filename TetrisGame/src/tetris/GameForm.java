package tetris;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;

import javax.swing.*;
import javax.swing.border.LineBorder;

public class GameForm extends JFrame {
	private JPanel gameAreaPlaceholder;
	private GameArea ga;
	
	private final static int WIDTH = 600;
	private final static int HEIGHT = 450;
	
	private JLabel scoreDisplay;
	private JLabel levelDisplay;
//	private JTextArea keyDisplay;
	
	// Create the frame.
	public GameForm() {
		initGameAreaPlaceholder();
		initDisplay();
		
		ga = new GameArea(gameAreaPlaceholder, 10);
		this.add(ga); // JFrame에 JPanel 추가하기
		
		initControls();
		
		startGame();
	}
	
	// 게임 영역 화면 설정
	private void initGameAreaPlaceholder() {
		gameAreaPlaceholder = new JPanel();
		gameAreaPlaceholder.setBounds(200, 0, 200, 400); // x, y, w, h
		gameAreaPlaceholder.setBorder(LineBorder.createBlackLineBorder());
		gameAreaPlaceholder.setLayout(null);
	}

	private void initDisplay() {
		scoreDisplay = new JLabel("Score: 0");
		scoreDisplay.setBounds(420, 10, 100, 30);
		this.add(scoreDisplay);
		
		levelDisplay = new JLabel("Level: 0");
		levelDisplay.setBounds(420, 40, 100, 30);
		this.add(levelDisplay);

//		keyDisplay = new JTextArea(" ← : 블럭 왼쪽 이동 \n → : 블럭 오른쪽 이동 \n"
//				+ " ↓ : 블럭 아래 한 칸 이동\n ↑ : 블럭 회전\n Enter : 블럭 맨 아래 이동\n" + " ESC : 뒤로 가기\n");
//		keyDisplay.setBounds(20, 210, 160, 120);
//		this.add(keyDisplay);
	}
	
	// key binding
	private void initControls() {
		InputMap im = this.getRootPane().getInputMap();
		ActionMap am = this.getRootPane().getActionMap();
		
		im.put(KeyStroke.getKeyStroke("RIGHT"), "right");
		im.put(KeyStroke.getKeyStroke("LEFT"), "left");
		im.put(KeyStroke.getKeyStroke("UP"), "up");
		im.put(KeyStroke.getKeyStroke("DOWN"), "down");
		
		am.put("right", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ga.moveBlockRight();
			}
		});
		
		am.put("left", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ga.moveBlockLeft();
			}
		});
		
		am.put("up", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ga.rotateBlock();
			}
		});
		
		am.put("down", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ga.dropBlock();
			}
		});
		
	}
	
	// 게임 스레드 시작 
	public void startGame() {
		new GameThread(ga, this).start();
	}
	
	public void updateScore(int score) {
		scoreDisplay.setText("Score: " + score);
	}
	
	public void updateLevel(int level) {
		levelDisplay.setText("Level: " + level);
		
	}
	
	// Launch the application.
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameForm f = new GameForm();
					f.setSize(WIDTH, HEIGHT);
					f.setLayout(null);
					f.setResizable(false);
					f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					f.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
}

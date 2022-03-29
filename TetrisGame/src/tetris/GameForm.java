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
		this.add(ga); // JFrame�� JPanel �߰��ϱ�
		
		initControls();
		
		startGame();
	}
	
	// ���� ���� ȭ�� ����
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

//		keyDisplay = new JTextArea(" �� : �� ���� �̵� \n �� : �� ������ �̵� \n"
//				+ " �� : �� �Ʒ� �� ĭ �̵�\n �� : �� ȸ��\n Enter : �� �� �Ʒ� �̵�\n" + " ESC : �ڷ� ����\n");
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
	
	// ���� ������ ���� 
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

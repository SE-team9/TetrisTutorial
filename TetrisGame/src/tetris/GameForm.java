package tetris;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.LineBorder;

public class GameForm extends JFrame {
	private final static int WIDTH = 600;
	private final static int HEIGHT = 450;
	
	private GameArea ga;	
	private JPanel gameAreaPlaceholder;
	private JLabel scoreDisplay, levelDisplay;
	private JButton btnMainMenu;
	private GameThread gt;
	
	// Create the frame.
	public GameForm() {
		initComponents(); // ui �۾� ó��
		
		ga = new GameArea(gameAreaPlaceholder, 10);
		this.add(ga); // �����ӿ� �г� �߰��ϱ�
		
		initControls();
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
		ga.initBackgroundArray();
		
		gt = new GameThread(ga, this);
		gt.start();
	}
	
	public void updateScore(int score) {
		scoreDisplay.setText("Score: " + score);
	}
	
	public void updateLevel(int level) {
		levelDisplay.setText("Level: " + level);
		
	}
	
	private void initComponents() {
		initThisFrame();
		initGameAreaPlaceholder();
		initDisplay();
		
		btnMainMenu = new JButton("Main Menu");
		btnMainMenu.setBounds(20, 20, 100, 30);
		this.add(btnMainMenu);
		
		btnMainMenu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JButton btn = (JButton) e.getSource();
				if(btn.getText().equals("Main Menu")) {
					gt.interrupt(); // ���� �����尡 ������ ����ǵ��� 
					
					btn.setFocusable(false); // ���� �޴������� Ű ���ε��� �Ѿ�� �ʵ���  
					
					setVisible(false); // ���� ������ �� ���̰� 
					Tetris.showStartup();
				}
			}
		});
	}

	
	private void initThisFrame(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(WIDTH, HEIGHT);
		setLayout(null); // �̰� ���� ����� setBounds �Լ� ���ڴ�� �г� ũ�Ⱑ ������. 
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(false);
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
		levelDisplay = new JLabel("Level: 1");
		scoreDisplay.setBounds(420, 10, 100, 30);
		levelDisplay.setBounds(420, 40, 100, 30);
		
		this.add(scoreDisplay);
		this.add(levelDisplay);
	}
	
	// Launch the application.
	public static void main(String[] args) {
		
	}

	
}

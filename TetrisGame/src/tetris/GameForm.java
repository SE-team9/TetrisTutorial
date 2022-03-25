package tetris;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.border.LineBorder;

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
		this.add(ga); // JFrame에 JPanel 추가하기
		
		initControls();
		
		startGame();
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

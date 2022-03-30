package tetris;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.KeyStroke;

public class StartupForm extends JFrame {
	private final static int WIDTH = 600;
	private final static int HEIGHT = 450;
	private final static int center_horizontal = (WIDTH - 300) / 2;
	
	private JButton[] menuButtons = new JButton[3];
	private String[] menuNames = { "Start Game", "Leaderboard", "Quit" };
	
	public StartupForm() {
		initComponents();
		
		
	}
	
	private void initComponents() {
		initThisFrame();
		initButtons();
		
	}

	private void initThisFrame() {
		setSize(WIDTH, HEIGHT);
		setResizable(false);
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(false);
	}

	private void initButtons() {
		for (int i = 0; i < menuButtons.length; i++) {
			menuButtons[i] = new JButton(menuNames[i]);
			menuButtons[i].setBounds(center_horizontal, 250 + i * 40, 300, 30);
			this.add(menuButtons[i]);
		}
		
		menuButtons[0].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JButton btn = (JButton) e.getSource();
				if(btn.getText().equals(menuNames[0])) {
					setVisible(false);
					Tetris.start();
				}
			}
		});
		
		menuButtons[1].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JButton btn = (JButton) e.getSource();
				if(btn.getText().equals(menuNames[1])) {
					setVisible(false);
					Tetris.showLeaderboard();
				}
			}
		});
		
		menuButtons[2].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JButton btn = (JButton) e.getSource();
				if(btn.getText().equals(menuNames[2])) {
					System.exit(0);
				}
			}
		});
		
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StartupForm frame = new StartupForm();
					frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}

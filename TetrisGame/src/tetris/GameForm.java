package tetris;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.border.LineBorder;

public class GameForm extends JFrame {
	private JPanel gameAreaPlaceholder;
	private GameArea ga;
	private GameThread gt;

	private final static int WIDTH = 600;
	private final static int HEIGHT = 450;

	private JLabel scoreDisplay;
	private JLabel levelDisplay;
	private JTextArea keyDisplay;

	// Create the frame.
	public GameForm() {
		initThisFrame();
		initGameAreaPlaceholder();
		initDisplay();

		ga = new GameArea(gameAreaPlaceholder, 10);
		this.add(ga); // JFrame�� JPanel �߰��ϱ�

		initControls();
	}

	// key binding
	private void initControls() {
		InputMap im = this.getRootPane().getInputMap();
		ActionMap am = this.getRootPane().getActionMap();

		im.put(KeyStroke.getKeyStroke("RIGHT"), "right");
		im.put(KeyStroke.getKeyStroke("LEFT"), "left");
		im.put(KeyStroke.getKeyStroke("UP"), "up");
		im.put(KeyStroke.getKeyStroke("DOWN"), "downOneLine");
		im.put(KeyStroke.getKeyStroke("ENTER"), "downToEnd");
		im.put(KeyStroke.getKeyStroke("ESCAPE"), "back");

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

		am.put("downOneLine", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ga.moveBlockDown();
			}
		});

		am.put("downToEnd", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ga.dropBlock();
			}
		});
		am.put("back", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				back();
			}
		});
	}

	// ���� ������ ����
	public void startGame() {
		// ���� �Ҷ����� ��� �ʱ�ȭ
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

	// �� �ǳ� ȭ�� ����
	private void initThisFrame() {
		this.setSize(600, 450);
		this.setResizable(false);
		this.setLayout(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(false);
	}

	// ���� ���� ȭ�� ����
	private void initGameAreaPlaceholder() {
		gameAreaPlaceholder = new JPanel();
		gameAreaPlaceholder.setBounds(200, 0, 200, 400);
		gameAreaPlaceholder.setBackground(new Color(238, 238, 238));
		gameAreaPlaceholder.setBorder(LineBorder.createBlackLineBorder());
	}

	private void initDisplay() {
		scoreDisplay = new JLabel("Score: 0");
		levelDisplay = new JLabel("Level: 0");
		scoreDisplay.setBounds(420, 10, 100, 30);
		levelDisplay.setBounds(420, 40, 100, 30);
		this.add(scoreDisplay);
		this.add(levelDisplay);

		keyDisplay = new JTextArea(" �� : �� ���� �̵� \n �� : �� ������ �̵� \n"
				+ " �� : �� �Ʒ� �� ĭ �̵�\n �� : �� ȸ��\n Enter : �� �� �Ʒ� �̵�\n" + " ESC : �ڷ� ����\n");
		keyDisplay.setBounds(20, 210, 160, 120);
		this.add(keyDisplay);
	}

	// ���۸޴� ȭ������ �̵�
	private void back() {
		gt.interrupt();
		this.setVisible(false);
		Tetris.showStartup();
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

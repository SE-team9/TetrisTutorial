package tetris;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class LeaderboardForm extends JFrame {
	private final static int WIDTH = 600;
	private final static int HEIGHT = 450;
	private final static int center_horizontal = (WIDTH - 300) / 2;
	private JButton btnMainMenu;
	
	String header[] = { "Player", "Score" };
	Object contents[][] = {}; // 이름은 String, Score는 int니까 타입은 Object
	private JTable leaderboard;
	private DefaultTableModel tm;
	
	public LeaderboardForm() {
		initComponents();
		
	}
	
	public void addPlayer(String playerName, int score) {
		tm.addRow(new Object[] { playerName, score });
		
		this.setVisible(true);
	}
	
	private void initComponents() {
		initThisFrame();
		initButtons();
		initTableData();
		
	}
	
	private void initTableData() {
		tm = new DefaultTableModel(contents, header) {
			
			 @Override
		     public boolean isCellEditable(int row, int column) {
		        // all cells false
		        return false;
		     }
		};
		
		leaderboard = new JTable(tm);
		leaderboard.setPreferredScrollableViewportSize(new Dimension(300, 200));
		leaderboard.setFillsViewportHeight(true);
		
		JScrollPane scrollPane = new JScrollPane(leaderboard);
		scrollPane.setBounds(center_horizontal, 70, 300, 200);
		this.add(scrollPane, BorderLayout.CENTER);
	}
	

	private void initThisFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(WIDTH, HEIGHT);
		setLayout(null);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(false);
	}
	
	private void initButtons() {
		btnMainMenu = new JButton("Main Menu");
		btnMainMenu.setBounds(20, 20, 100, 30);
		this.add(btnMainMenu);
		
		// 메인 메뉴로 돌아가기 
		btnMainMenu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JButton btn = (JButton) e.getSource();
				if(btn.getText().equals("Main Menu")) {
					setVisible(false); // 현재 프레임은 안 보이도록
					
					Tetris.showStartup();
				}
			}
		});
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LeaderboardForm frame = new LeaderboardForm();
					frame.setVisible(true);
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}

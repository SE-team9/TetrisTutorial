package tetris;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SortOrder;
import javax.swing.RowSorter.SortKey;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class LeaderboardForm extends JFrame {
	private final static int WIDTH = 600;
	private final static int HEIGHT = 450;
	private JButton btnMainMenu;
	private JTable leaderboard;
	private DefaultTableModel tm;
	private String leaderboardFile = "leaderboard";
	private TableRowSorter<DefaultTableModel> sorter;
	
	public LeaderboardForm() {
		initComponents();
		
	}
	
	private void initComponents() {
		initThisFrame();
		initButtons();
		initTableData();
		initTableSorter();
	}
	
	// 파일로부터 데이터 가져오기 (de-serialization)
	private void initTableData() {
		String header[] = {"Player", "Score"};
		Object contents[][] = {};
		
		Vector columnIdentifier = new Vector();
		columnIdentifier.add("Palyer");
		columnIdentifier.add("Score");
		
		tm = new DefaultTableModel(contents, header){
			@Override
		     public boolean isCellEditable(int row, int column) {
		        // all cells false
		        return false;
		     }
		};
		
		try {
			FileInputStream fs = new FileInputStream(leaderboardFile);
			ObjectInputStream os = new ObjectInputStream(fs);
			
			// 점수를 문자열이 아닌 int 타입으로 읽어야 두자리 이상의 숫자도 정렬 가능!
			tm.setDataVector((Vector<Vector>) os.readObject(), columnIdentifier);
			
			os.close();
			fs.close();

		}catch(Exception e) {
			e.printStackTrace();
		}
		
		initTable(tm);
	}
	
	private void initTable(DefaultTableModel tm) {
		leaderboard = new JTable(tm);
		
		JScrollPane scrollPane = new JScrollPane(leaderboard);
		scrollPane.setBounds(20, 70, 542, 314);
		getContentPane().add(scrollPane, BorderLayout.CENTER);
	}
	
	private void initTableSorter() {
		sorter = new TableRowSorter<>(tm);
		leaderboard.setRowSorter(sorter);
		
		ArrayList<SortKey> keys = new ArrayList<>();
		keys.add(new SortKey(1, SortOrder.DESCENDING)); // column index, sort order
		sorter.setSortKeys(keys);
	}
	
	// 파일에 데이터 저장하기 (serialization)
	private void saveLeaderboard() {
		try {
			FileOutputStream fs = new FileOutputStream(leaderboardFile);
			ObjectOutputStream os = new ObjectOutputStream(fs);
			
			os.writeObject(tm.getDataVector());
			
			os.close();
			fs.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void addPlayer(String playerName, int score) {
		tm.addRow(new Object[] { playerName, score });
		sorter.sort();
		saveLeaderboard();
		
		this.setVisible(true);
	}

	private void initThisFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(WIDTH, HEIGHT);
		getContentPane().setLayout(null);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(false);
	}
	
	private void initButtons() {
		btnMainMenu = new JButton("Main Menu");
		btnMainMenu.setBounds(20, 20, 100, 30);
		getContentPane().add(btnMainMenu);
		
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
		
	}

}

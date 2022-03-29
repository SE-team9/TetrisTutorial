package tetris;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.RowSorter.SortKey;
import javax.swing.SortOrder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class LeaderboardForm extends JFrame {
	
	private JTable leaderboard;

	private DefaultTableModel tm;
	private String leaderboardFile = "leaderboard";
	private TableRowSorter<TableModel> sorter;

	public LeaderboardForm() {
		initThisFrame();
		initTable();
		initTableData();
		initTableSorter();
		
		initControls();
	}
	
	// ESC�� ������ ���� ȭ������ �̵�
	private void initControls() {
		InputMap im = this.getRootPane().getInputMap();
		ActionMap am = this.getRootPane().getActionMap();
		
		im.put(KeyStroke.getKeyStroke("ESCAPE"), "back");

		am.put("back", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				back();
			}
		});
	}
	
	// ���Ͽ��� �����͸� �����ͼ� ���̺� �����Ϳ� �Է�
	private void initTableData() {
		Vector ci = new Vector();
		ci.add("Player");
		ci.add("Score");

		tm = (DefaultTableModel) leaderboard.getModel();

		try {
			FileInputStream fs = new FileInputStream(leaderboardFile);
			ObjectInputStream os = new ObjectInputStream(fs);

			// unserialize
			tm.setDataVector((Vector<Vector>) os.readObject(), ci);

			os.close();
			fs.close();
		} catch (Exception e) {
		}
	}
	
	// ���� ������ ���̺� ������ ����
	private void initTableSorter() {
		sorter = new TableRowSorter<>(tm);
		leaderboard.setRowSorter(sorter);
		
		ArrayList<SortKey> keys =new ArrayList<>();
		keys.add(new SortKey(1,SortOrder.DESCENDING));
		
		sorter.setSortKeys(keys);
	}

	// ���Ͽ� ������ ����
	private void saveLeaderboard() {
		try {
			FileOutputStream fs = new FileOutputStream(leaderboardFile);
			ObjectOutputStream os = new ObjectOutputStream(fs);

			// serialize
			os.writeObject(tm.getDataVector());

			os.close();
			fs.close();
		} catch (Exception e) {
		}
	}

	// ���� ��ϵ� �����͸� �߰�, �����ϰ� �������� ǥ��
	public void addPlayer(String playerName, int score) {
		tm.addRow(new Object[] { playerName, score });
		sorter.sort();
		saveLeaderboard();

		this.setVisible(true);
	}

	private void initThisFrame() {
		this.setSize(600, 450);
		this.setResizable(false);
		this.setLayout(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(false);
	}
	
	private void back() {
		this.setVisible(false);
		Tetris.showStartup();
	}

	private void initTable() {
		leaderboard = new JTable(0, 2);
		leaderboard.setPreferredScrollableViewportSize(new Dimension(300, 200));
		leaderboard.setFillsViewportHeight(true);
		JScrollPane scrollLeaderboard = new JScrollPane(leaderboard);
		scrollLeaderboard.setBounds(40, 50, 520, 350);
		this.add(scrollLeaderboard);
	}

	public static void main(String[] args) {

		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new LeaderboardForm().setVisible(true);
			}
		});
	}
}

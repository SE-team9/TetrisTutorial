package tetris;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class GameArea extends JPanel {
	// 20행 10열
	private int gridRows;
	private int gridColumns;
	private int gridCellSize;
	
	private int[][] block = { {1, 0}, {1, 0}, {1, 1} };
	
	public GameArea(JPanel placeholder, int columns) {
		this.setBounds(placeholder.getBounds());
		this.setBackground(placeholder.getBackground());
		this.setBorder(placeholder.getBorder());
		
		gridColumns = columns;
		
		// WIDTH divisible by the numbers of columns
		// 20 = 200 / 10 
		gridCellSize = this.getBounds().width / gridColumns;
		
		// HEIGHT divisible by grid-cell size
		// 20 = 400 / 20
		gridRows = this.getBounds().height / gridCellSize;
	}
	
	private void drawBlock(Graphics g) {
		for(int row = 0; row < block.length; row++) {
			for(int col = 0; col < block[0].length; col++) {
				if(block[row][col] == 1) {
					g.setColor(Color.red);
					g.fillRect(col * gridCellSize, row * gridCellSize, gridCellSize, gridCellSize);
					g.setColor(Color.black);
					g.drawRect(col * gridCellSize, row * gridCellSize, gridCellSize, gridCellSize);
				}
			}
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		// 기본 그리드 
//		// draw by column
//		for(int y = 0; y < gridRows; y++) {
//			// draw by row
//			for(int x = 0; x < gridColumns; x++) {
//				g.drawRect(x * gridCellSize, y * gridCellSize, gridCellSize, gridCellSize);
//			}
//		} 
		
		drawBlock(g);
		
	}
}

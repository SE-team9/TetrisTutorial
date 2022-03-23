package tetris;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class GameArea extends JPanel {
	private int gridRows;
	private int gridColumns;
	private int gridCellSize;
	private Color[][] background;
	private TetrisBlock block;
	
	public GameArea(JPanel placeholder, int columns) {
		this.setBounds(placeholder.getBounds());
		this.setBackground(placeholder.getBackground());
		this.setBorder(placeholder.getBorder());
		
		gridColumns = columns;
		
		// WIDTH divisible by the numbers of columns
		gridCellSize = this.getBounds().width / gridColumns;
		
		// HEIGHT divisible by grid-cell size
		gridRows = this.getBounds().height / gridCellSize;
		
		background = new Color[gridRows][gridColumns];
	}
	
	public void spawnBlock() {
		block = new TetrisBlock(new int[][] { {1, 0}, {1, 0}, {1, 1} }, Color.blue);
		block.spawn(gridColumns);
	}
	
	public boolean moveBlockDown() {
		// 블록이 바닥에 닿으면, 백그라운드 블록으로 전환 
		if(checkBottom() == true) {
			moveBlockToBackground();
			return false;
		}
		
		block.moveDown(); 
		repaint(); // 일정한 시간 간격으로 (스레드 사용)
		
		return true;
	}
	
	private void moveBlockToBackground() {
		// 움직이고 있던 블록에 대한 참조
		int[][] shape = block.getShape();
		int h = block.getHeight();
		int w = block.getWidth();
		
		int xPos = block.getX();
		int yPos = block.getY();
		
		Color color = block.getColor();
		
		for(int r = 0; r < h; r++) {
			for(int c = 0; c < w; c++) {
				// 백그라운드 블록의 컬러로 설정 
				if(shape[r][c] == 1) {
					background[r + yPos][c + xPos] = color;
				}
			}
		}
	}
	
	private boolean checkBottom() {
		if(block.getBottomEdge() == gridRows) {
			return true;
		}
		
		return false;
	}
	
	private void drawBlock(Graphics g) {
		int h = block.getHeight();
		int w = block.getWidth();
		Color c = block.getColor();
		int[][] shape = block.getShape();
		
		for(int row = 0; row < h; row++) {
			for(int col = 0; col < w; col++) {
				
				if(shape[row][col] == 1) { // colored cell
					int x = (block.getX() + col) * gridCellSize;
					int y = (block.getY() + row) * gridCellSize;
					
					drawGridSquare(g, c, x, y);
				}
			}
		}
	}
	
	private void drawBackground(Graphics g) {
		Color color; // 백그라운드 블록에 대한 참조
		
		for(int r = 0; r < gridRows; r++) {
			for(int c = 0; c < gridColumns; c++) {
				color = background[r][c];
				
				// moveBlockToBackground 함수에서 컬러가 설정되면 not null
				if(color != null) {
					int x = c * gridCellSize;
					int y = r * gridCellSize;
					
					drawGridSquare(g, color, x, y);
				}
				
			}
		}
	}
	
	// 반복되는 코드는 모듈화!
	private void drawGridSquare(Graphics g, Color c, int x, int y) {
		g.setColor(c);
		g.fillRect(x, y, gridCellSize, gridCellSize);
		g.setColor(Color.black);
		g.drawRect(x, y, gridCellSize, gridCellSize);
	}
	

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		drawBackground(g);
		drawBlock(g);
	}
}

package tetris;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.JPanel;

import tetrisblocks.*;

public class GameArea extends JPanel {
	private int gridRows;
	private int gridColumns;
	private int gridCellSize;
	private Color[][] background;
	
	private TetrisBlock block;
	private TetrisBlock[] blocks;
	
	public GameArea(JPanel placeholder, int columns) {
		this.setBounds(placeholder.getBounds());
		this.setBackground(placeholder.getBackground());
		this.setBorder(placeholder.getBorder());
		
		gridColumns = columns;
		gridCellSize = this.getBounds().width / gridColumns;
		gridRows = this.getBounds().height / gridCellSize;
		
		blocks = new TetrisBlock[] { new IShape(),
									 new JShape(),
									 new LShape(),
									 new OShape(),
									 new SShape(),
									 new TShape(),
									 new ZShape() };
	}
	
	public void initBackgroundArray() {
		background = new Color[gridRows][gridColumns];
	}
	
	// �������� ���ο� ��� ����
	public void spawnBlock() {
		Random r = new Random();
		
		block = blocks[ r.nextInt(blocks.length) ];
		block.spawn(gridColumns);
	}
	
	public boolean isBlockOutOfBounds() {
		if(block.getY() < 0){
			block = null; // Ű �Է��ص� ����� �������� �ʵ��� 
			return true;
		}
		
		return false;
	}
	
	public boolean moveBlockDown() {
		// ����� �ٴڿ� ���� ��� false ���� 
		if(checkBottom() == false) {
			return false;
		}
		
		block.moveDown(); 
		repaint(); // ������ �ð� ���ݸ��� ������Ʈ (������ ���)
		// repaint ���� ����! (�� ���ָ� �Է¿� ������ ������)
		
		return true;
	}
	
	public void moveBlockRight() {
		if(block == null) return;
		
		if(checkRight() == false) return;
		
		block.moveRight();
		repaint();
	}
	
	public void moveBlockLeft() {
		if(block == null) return;
		
		if(checkLeft() == false) return;
		
		block.moveLeft();
		repaint();
	}
	
	public void dropBlock() { // down
		if(block == null) return;
		
		while(checkBottom()) {
			block.moveDown();			
		}
		repaint();
	}
	
	public void rotateBlock() { // up
		if(block == null) return;
		
		block.rotate();
		
		// ȸ����Ų ����� ������ ��踦 �Ѿ�� ��� ���� ó�� 
		if(block.getLeftEdge() < 0) {
			block.setX(0);
		}
		
		if(block.getRightEdge() >= gridColumns) {
			block.setX(gridColumns - block.getWidth());
		}
		
		if(block.getBottomEdge() >= gridRows) {
			block.setY(gridRows - block.getHeight());
		}
		
		repaint();
	}
	
	private boolean checkBottom() {
		if(block.getBottomEdge() == gridRows) {
			return false; // stop
		}
		
		int[][] shape = block.getShape();
		int w = block.getWidth();
		int h = block.getHeight();
		
		for(int col = 0; col < w; col++) {
			// Ư�� ���� �� �ؿ��� �������� �ö󰡴ٰ� 
			for(int row = h - 1; row >= 0; row--) {
				// colored cell�� �߰��߰� 
				if(shape[row][col] != 0) { 
					int x = col + block.getX();
					int y = row + block.getY() + 1; // �ش� ��� �ٷ� �Ʒ���!
					
					// �����ǿ� ���Ե��� ���� ����� �����ϰ� ���� ���� �̵� 
					if(y < 0) break; 
					
					if(background[y][x] != null) { // ��׶��� ����� ������!
						return false; // stop
					}
					break; // ���� ���� ���̻� �˻��� �ʿ� ����.
				}
			}
		}
		
		return true; // keep going
	}
	
	private boolean checkLeft() {
		if(block.getLeftEdge() == 0) {
			return false; // stop
		}
		
		int[][] shape = block.getShape();
		int w = block.getWidth();
		int h = block.getHeight();
		
		for(int row = 0; row < h; row++) {
			for(int col = 0; col < w; col++) {
				if(shape[row][col] != 0) { // colored cell
					int x = col + block.getX() - 1; // �ٷ� ���ʿ�!
					int y = row + block.getY();
					
					// y�� üũ �� ���ָ�, ����� �� ���� ���¿��� Ű �Է��� ���� �� 
					// background[y][x] ���⼭ �迭 �ε��� ���� �߻���. 
					if(y < 0) break; 
					
					if(background[y][x] != null) { // ��׶��� ����� ������!
						return false; // stop
					}
					
					break; // ���� ���� ���̻� �˻��� �ʿ� ����.
				}
			}
		}
		
		return true; // keep going
	}
	
	private boolean checkRight() {
		if(block.getRightEdge() == gridColumns) {
			return false; // stop
		}
		
		int[][] shape = block.getShape();
		int w = block.getWidth();
		int h = block.getHeight();
		
		for(int row = 0; row < h; row++) {
			for(int col = w - 1; col >= 0; col--) {
				if(shape[row][col] != 0) { // colored cell
					int x = col + block.getX() + 1; // �ٷ� �����ʿ�!
					int y = row + block.getY(); 
					
					if(y < 0) break;
					
					if(background[y][x] != null) { // ��׶��� ����� ������!
						return false; // stop
					}
					
					break; // ���� ���� ���̻� �˻��� �ʿ� ����.
				}
			}
		}
		
		return true; // keep going
	}
	
	public void moveBlockToBackground() {
		// �����̰� �ִ� ��Ͽ� ���� ����
		int[][] shape = block.getShape();
		int h = block.getHeight();
		int w = block.getWidth();
		
		int xPos = block.getX();
		int yPos = block.getY();
		
		Color color = block.getColor();
		
		for(int r = 0; r < h; r++) {
			for(int c = 0; c < w; c++) {
				// ��׶��� ����� �÷��� ���� 
				if(shape[r][c] == 1) {
					background[yPos + r][xPos+ c] = color;
				}
			}
		}
	}
	
	public int clearLines() {
		boolean lineFilled;
		int linesCleared = 0;
		
		for(int r = gridRows - 1; r >= 0; r--) {
			// �� ������ ���� �ۿ��� �ʱ�ȭ �ϸ�, �ึ�� ���� ���°� ������Ʈ ���� �����Ƿ� �����ϱ�! 
			lineFilled = true; 
			
			for(int c = 0; c < gridColumns; c++) {
				if(background[r][c] == null) {
					lineFilled = false;
					break;
				}
			}
			
			// �� ���� �� ä���� ������ ���� �� ȭ�� ������Ʈ
			if(lineFilled) {
				linesCleared++;
				
				clearLine(r);
				shiftDown(r);
				clearLine(0);
				
				r++; // ���� ���� �����Ǵ� ��� ���
				
				repaint();
			}
		}
		
		if(linesCleared > 0) {
			Tetris.playClear();
		}
		
		return linesCleared;
	}
	
	private void shiftDown(int r) {
		for(int row = r; row > 0; row--) { // 0���� ���� 
			for(int col = 0; col < gridColumns; col++) {
				background[row][col] = background[row - 1][col];
			}
		}
	}

	private void clearLine(int r) {
		for(int i = 0; i < gridColumns; i++) {
			background[r][i] = null;
		}
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
		Color color; // ��׶��� ��Ͽ� ���� ����
		
		for(int r = 0; r < gridRows; r++) {
			for(int c = 0; c < gridColumns; c++) {
				color = background[r][c];
				
				// moveBlockToBackground �Լ����� �÷��� �����Ǹ� not null
				if(color != null) {
					int x = c * gridCellSize;
					int y = r * gridCellSize;
					
					drawGridSquare(g, color, x, y);
				}
				
			}
		}
	}
	
	// �ݺ��Ǵ� �ڵ�� ���ȭ!
	private void drawGridSquare(Graphics g, Color c, int x, int y) {
		g.setColor(c);
		g.fillRect(x, y, gridCellSize, gridCellSize);
		
		g.setColor(Color.black); // ����� �׵θ��� ����������
		g.drawRect(x, y, gridCellSize, gridCellSize);
	}
	

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		drawBackground(g);
		drawBlock(g);
	}
}

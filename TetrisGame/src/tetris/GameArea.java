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
	
	// ��� �ʱ�ȭ
	public void initBackgroundArray() {
		background = new Color[gridRows][gridColumns];
	}
	
	public void spawnBlock() {
		block = new TetrisBlock(new int[][] { {1, 0}, {1, 0}, {1, 1} }, Color.blue);
		block.spawn(gridColumns);
	}
	
	// ���� ���� ��踦 ���� ������ ���� ����
	public boolean isBlockOutOfBounds() {
		if (block.getY() < 0) {

			block = null;
			return true;
		}
		return false;
	}
	
	public boolean moveBlockDown() {
		// ����� �ٴڿ� ������, ��׶��� ������� ��ȯ 
		if(!checkBottom()) {
			return false;
		}
		
		block.moveDown(); 
		repaint(); // ������ �ð� ���ݸ��� ������Ʈ (������ ���)
		// repaint ���� ����! (�� ���ָ� �Է¿� ������ ������)
		
		return true;
	}
	
	public void moveBlockRight() {
		
		if (block == null) return;
		if(!checkRight()) return;
		
		block.moveRight();
		repaint();
	}
	
	public void moveBlockLeft() {
		if (block == null) return;
		if(!checkLeft()) return;
		
		block.moveLeft();
		repaint();
	}
	
	public void dropBlock() { // down
		if (block == null) return;
		while(checkBottom()) {
			block.moveDown();			
		}
		repaint();
	}
	
	public void rotateBlock() { // up
		
		if (block == null) return;
		// ���� ��ġ���� Ȯ��
		if (!checkRotate())
			return;
		
		block.rotate();
		
		// ȸ�� �� ��ġ �缳��
		if (block.getLeftEdge() < 0)
			block.setX(0);
		if (block.getRightEdge() >= gridColumns)
			block.setX(gridColumns - block.getWidth());
		if (block.getBottomEdge() >= gridRows)
			block.setY(gridRows - block.getHeight());
		
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
					
					if(y < 0) break; // �����ǿ� ���Ե��� ���� ����� �����ϰ� ���� ���� �̵� 
					
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
					
					if(y < 0) break; // �����ǿ� ���Ե��� ���� ����� �����ϰ� ���� ���� �̵�
					
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
					
					if(y < 0) break; // �����ǿ� ���Ե��� ���� ����� �����ϰ� ���� ���� �̵�
					
					if(background[y][x] != null) { // ��׶��� ����� ������!
						return false; // stop
					}
					
					break; // ���� ���� ���̻� �˻��� �ʿ� ����.
				}
			}
		}
		return true; // keep going
	}
	
	
	// ȸ�� �� �ٸ� ��ϰ� ��ġ�� �ʵ��� Ȯ�� (L��� ������ �������� ���� ���߿� LShpae ����� ���� ���� �ʿ�)
	private boolean checkRotate() {
		// ���簴ü�� �����ϰ� ȸ�����Ѽ� Ȯ���Ѵ�. color�κ��� ���߿� �ʿ�������� ����  
		TetrisBlock rotated = new TetrisBlock(block.getShape(),block.getColor());
		rotated.setCurrentRotation(block.getCurrentRotation());
		rotated.setX(block.getX());
		rotated.setY(block.getY());
		rotated.rotate();
		
		if (rotated.getRightEdge() >= gridColumns)
			rotated.setX(gridColumns - rotated.getWidth());

		int[][] shape = rotated.getShape();
		int w = rotated.getWidth();
		int h = rotated.getHeight();

		for (int row = 0; row < h; row++) {
			for (int col = 0; col < w; col++) {
				if (shape[row][col] != 0) {
					// �ش� ĭ Ȯ��
					int x = col + rotated.getX();
					int y = row + rotated.getY();
					if (y < 0)
						break;
					if (background[y][x] != null)
						return false;
				}
			}
		}
		return true;
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
					background[r + yPos][c + xPos] = color;
				}
			}
		}
	}
	
	// �ϼ��� �ٵ� ����
	public int clearLines() {

		boolean lineFilled;
		int linesCleared = 0;

		// �Ʒ�����
		for (int r = gridRows - 1; r >= 0; r--) {
			lineFilled = true;

			for (int c = 0; c < gridColumns; c++) {
				if (background[r][c] == null) {
					lineFilled = false;
					break;
				}
			}
			if (lineFilled) {
				linesCleared++;
				clearLine(r);
				shiftDown(r);
				// �� �� ���� ���� null�̹Ƿ� ���� �����ش�.
				clearLine(0);

				// �Ʒ��� �� �� �� ���������Ƿ� ������ �� ��ġ�������� �ٽ� ����
				r++;

				repaint();
			}
		}
		return linesCleared;
	}

	// ��濡�� r�� ���� 
	private void clearLine(int r) {
		for (int i = 0; i < gridColumns; i++) {
			background[r][i] = null;
		}
	}

	// ������ r�� ���ٵ��� �����ش�.
	private void shiftDown(int r) {
		for (int row = r; row > 0; row--) {
			for (int col = 0; col < gridColumns; col++) {
				background[row][col] = background[row - 1][col];
			}
		}
	}
	
	private void drawBlock(Graphics g) {
		
		if(block == null) return;
		
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

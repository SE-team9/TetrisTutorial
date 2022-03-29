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
		if(!checkBottom()) {
			moveBlockToBackground();
			return false;
		}
		
		block.moveDown(); 
		repaint(); // 일정한 시간 간격마다 업데이트 (스레드 사용)
		// repaint 잊지 말자! (안 해주면 입력에 느리게 반응함)
		
		return true;
	}
	
	public void moveBlockRight() {
		if(!checkRight()) return;
		
		block.moveRight();
		repaint();
	}
	
	public void moveBlockLeft() {
		if(!checkLeft()) return;
		
		block.moveLeft();
		repaint();
	}
	
	public void dropBlock() { // down
		while(checkBottom()) {
			block.moveDown();			
		}
		repaint();
	}
	
	public void rotateBlock() { // up
		
		// 배경과 겹치는지 확인
		if (!checkRotate())
			return;
		
		block.rotate();
		
		// 회전 시 위치 재설정
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
			// 특정 열의 맨 밑에서 위쪽으로 올라가다가 
			for(int row = h - 1; row >= 0; row--) {
				// colored cell을 발견했고 
				if(shape[row][col] != 0) { 
					int x = col + block.getX();
					int y = row + block.getY() + 1; // 해당 블록 바로 아래에!
					
					if(y < 0) break; // 보드판에 포함되지 않은 블록은 무시하고 다음 열로 이동 
					
					if(background[y][x] != null) { // 백그라운드 블록이 있으면!
						return false; // stop
					}
					break; // 현재 열은 더이상 검사할 필요 없음.
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
					int x = col + block.getX() - 1; // 바로 왼쪽에!
					int y = row + block.getY();
					
					if(y < 0) break; // 보드판에 포함되지 않은 블록은 무시하고 다음 열로 이동
					
					if(background[y][x] != null) { // 백그라운드 블록이 있으면!
						return false; // stop
					}
					
					break; // 현재 행은 더이상 검사할 필요 없음.
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
					int x = col + block.getX() + 1; // 바로 오른쪽에!
					int y = row + block.getY(); 
					
					if(y < 0) break; // 보드판에 포함되지 않은 블록은 무시하고 다음 열로 이동
					
					if(background[y][x] != null) { // 백그라운드 블록이 있으면!
						return false; // stop
					}
					
					break; // 현재 행은 더이상 검사할 필요 없음.
				}
			}
		}
		
		return true; // keep going
	}
	
	
	// 회전 시 다른 블록과 겹치지 않도록 확인 (L모양 블럭에서 완전하진 않음 나중에 LShpae 블록은 따로 수정 필요)
	private boolean checkRotate() {
		// 복사객체를 생성하고 회전시켜서 확인한다. color부분은 나중에 필요없어지면 제거  
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
					// 해당 칸 확인
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

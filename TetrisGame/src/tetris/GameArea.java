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
	
	// 랜덤으로 새로운 블록 생성
	public void spawnBlock() {
		Random r = new Random();
		
		block = blocks[ r.nextInt(blocks.length) ];
		block.spawn(gridColumns);
	}
	
	public boolean isBlockOutOfBounds() {
		if(block.getY() < 0){
			block = null; // 키 입력해도 블록이 반응하지 않도록 
			return true;
		}
		
		return false;
	}
	
	public boolean moveBlockDown() {
		// 블록이 바닥에 닿은 경우 false 리턴 
		if(checkBottom() == false) {
			return false;
		}
		
		block.moveDown(); 
		repaint(); // 일정한 시간 간격마다 업데이트 (스레드 사용)
		// repaint 잊지 말자! (안 해주면 입력에 느리게 반응함)
		
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
		
		// 회전시킨 블록이 보드판 경계를 넘어가는 경우 예외 처리 
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
			// 특정 열의 맨 밑에서 위쪽으로 올라가다가 
			for(int row = h - 1; row >= 0; row--) {
				// colored cell을 발견했고 
				if(shape[row][col] != 0) { 
					int x = col + block.getX();
					int y = row + block.getY() + 1; // 해당 블록 바로 아래에!
					
					// 보드판에 포함되지 않은 블록은 무시하고 다음 열로 이동 
					if(y < 0) break; 
					
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
					
					// y값 체크 안 해주면, 블록이 다 쌓인 상태에서 키 입력이 들어올 때 
					// background[y][x] 여기서 배열 인덱스 에러 발생함. 
					if(y < 0) break; 
					
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
					
					if(y < 0) break;
					
					if(background[y][x] != null) { // 백그라운드 블록이 있으면!
						return false; // stop
					}
					
					break; // 현재 행은 더이상 검사할 필요 없음.
				}
			}
		}
		
		return true; // keep going
	}
	
	public void moveBlockToBackground() {
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
					background[yPos + r][xPos+ c] = color;
				}
			}
		}
	}
	
	public int clearLines() {
		boolean lineFilled;
		int linesCleared = 0;
		
		for(int r = gridRows - 1; r >= 0; r--) {
			// 이 변수를 루프 밖에서 초기화 하면, 행마다 변수 상태가 업데이트 되지 않으므로 주의하기! 
			lineFilled = true; 
			
			for(int c = 0; c < gridColumns; c++) {
				if(background[r][c] == null) {
					lineFilled = false;
					break;
				}
			}
			
			// 한 줄이 다 채워져 있으면 삭제 후 화면 업데이트
			if(lineFilled) {
				linesCleared++;
				
				clearLine(r);
				shiftDown(r);
				clearLine(0);
				
				r++; // 여러 줄이 삭제되는 경우 고려
				
				repaint();
			}
		}
		
		if(linesCleared > 0) {
			Tetris.playClear();
		}
		
		return linesCleared;
	}
	
	private void shiftDown(int r) {
		for(int row = r; row > 0; row--) { // 0행은 제외 
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
		
		g.setColor(Color.black); // 블록의 테두리는 검정색으로
		g.drawRect(x, y, gridCellSize, gridCellSize);
	}
	

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		drawBackground(g);
		drawBlock(g);
	}
}

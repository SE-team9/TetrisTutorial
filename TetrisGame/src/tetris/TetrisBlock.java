package tetris;

import java.awt.Color;

public class TetrisBlock {
	private int[][] shape;
	private Color color;
	private int x, y;
	private int[][][] shapes;
	private int currentRotation;
	
	public TetrisBlock(int[][] shape, Color color) {
		this.shape = shape;
		this.color = color;
		
		initShapes();
	}
	
	private void initShapes() {
		shapes = new int[4][][];
		
		for(int i = 0; i < 4; i++) {
			int r = shape[0].length; // col -> row
			int c = shape.length; // row -> col
			shapes[i] = new int[r][c];
			
			for(int y = 0; y < r; y++) {
				for(int x = 0; x < c; x++) {
					shapes[i][y][x] = shape[c - x - 1][y];
				}
			}
			
			shape = shapes[i];
		}
		
	}
	
	public void spawn(int gridWidth) {
		// x, y 위치 정하기 전에 블록 방향과 크기 먼저 초기화
		currentRotation = 0;
		shape = shapes[currentRotation];
		
		// 보드판 맨 윗줄의 가운데에서 새 블록이 떨어지도록
		y = 0 - getHeight();
		x = (gridWidth - getWidth()) / 2;
	}

	public int[][] getShape() { return shape; }
	public Color getColor() { return color; }
	
	public int getHeight() { return shape.length; }
	public int getWidth() { return shape[0].length; }
	
	public int getX() { return x; }
	public void setX(int newX) { x = newX; }
	public int getY() { return y; }
	public void setY(int newY) { y = newY; }
	
	// 회전 확인을 위해 추가한 함수
	public int getCurrentRotation() { return currentRotation; }
	public void setCurrentRotation(int newCurrentRotation) { currentRotation = newCurrentRotation; }
	
	public void moveDown() { y++; }
	public void moveLeft() { x--; }
	public void moveRight() { x++; }
	
	public void rotate() { // 시계 방향으로 회전
		currentRotation++;
		if(currentRotation > 3) currentRotation = 0;
		
		// 방향 바꿔서 2차원 배열 업데이트 
		shape = shapes[currentRotation]; 
	}
	
	public int getBottomEdge() { return y + getHeight(); }
	public int getLeftEdge() { return x; } // getX()와 똑같지만 코드의 가독성을 위해 함수 따로 정의하기
	public int getRightEdge() { return x + getWidth(); }
}

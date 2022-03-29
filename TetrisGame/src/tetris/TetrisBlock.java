package tetris;

import java.awt.Color;
import java.util.Random;

public class TetrisBlock {
	private int[][] shape;
	private Color color;
	private int x, y;
	private int[][][] shapes;
	private int currentRotation;
	
	private Color[] availableColors = { Color.green, Color.red, Color.blue };
	
	public TetrisBlock(int[][] shape) {
		this.shape = shape;
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
			
			// 회전된 블록으로 2차원 배열 업데이트 
			shape = shapes[i];
		}
	}
	
	public void spawn(int gridWidth) {
		Random r = new Random();
		
		// x, y 위치 정하기 전에 블록 방향과 크기 먼저 초기화
		currentRotation = r.nextInt(shapes.length);
		shape = shapes[currentRotation];
		
		// 보드판 범위 내에서 랜덤한 위치에 새 블록이 떨어지도록 
		y = 0 - getHeight();
		x = r.nextInt(gridWidth - getWidth());
		
		color = availableColors[r.nextInt(availableColors.length)];
	}

	public int[][] getShape() { return shape; }
	public Color getColor() { return color; }
	
	public int getHeight() { return shape.length; }
	public int getWidth() { return shape[0].length; }
	
	public int getX() { return x; }
	public void setX(int x) { this.x = x; }
	public int getY() { return y; }
	public void setY(int y) { this.y = y; }
	
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

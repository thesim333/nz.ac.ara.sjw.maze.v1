package game;

/**
 * Created by Simon Winder on 26/03/2017.
 */
public enum Direction {
	UP(-1, 0),
	DOWN(1, 0),
	LEFT(0, -1),
	RIGHT(0, 1);
	
	public int colAdjust;
	public int rowAdjust;
	
	Direction(int row, int col) {
		colAdjust = col;
		rowAdjust = row;
	}
}

package communal;

/**
 * Created by Simon Winder on 26/03/2017.
 */
public class Point implements IPoint {
    private int row;
    private int col;

    public Point(IPoint p) {
        this.row = p.getRow();
        this.col = p.getCol();
    }

    public Point(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public Point() {
        this.row = 0;
        this.col = 0;
    }

    @Override
    public int getRow() {
        return this.row;
    }

    @Override
    public int getCol() {
        return this.col;
    }

    @Override
    public boolean equals(IPoint p) {
        return (this.row == p.getRow() && this.col == p.getCol());
    }

    @Override
    public void setLocation(IPoint p) {
        this.row = p.getRow();
        this.col = p.getCol();
    }

    @Override
    public void setLocation(int row, int col) {
        this.row = row;
        this.col = col;
    }

    @Override
    public void translate(int row, int col) {
        this.row += row;
        this.col += col;
    }

    @Override
    public String toString() {
        return String.format("%d, %d", row, col);
    }
}

package communal;

/**
 * Created by Simon Winder on 26/03/2017.
 */
public interface IPoint {
    boolean equals(IPoint p);
    void setLocation(IPoint p);
    void setLocation(int row, int col);
    void translate(int row, int col);
    int getRow();
    int getCol();
    String toString();
}

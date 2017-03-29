package game;

import communal.IPoint;
import communal.Wall;

/**
 * Created by Simon Winder on 26/03/2017.
 */
public interface IGame {
    void moveTheseus(Direction direction);
    void moveMinotaur();
    boolean isWon();
    boolean isLost();
    int getMoveCount();
    String getLevelName();
    IPoint getTheseusLocation();
    IPoint getMinotaurLocation();
    IPoint getExitLocation();
    Wall getTopWall(IPoint p);
    Wall getLeftWall(IPoint p);
}
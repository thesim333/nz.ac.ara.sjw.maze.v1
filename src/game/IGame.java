package game;

import communal.IPoint;
import communal.Wall;

/**
 * Created by Simon Winder on 26/03/2017.
 */
public interface IGame {
    boolean moveTheseus(Direction direction);
    void moveMinotaur();
    void pauseTheseus();
    boolean isWon();
    boolean isLost();
    int getMoveCount();
    String getLevelName();
    IPoint wheresTheseus();
    IPoint wheresMinotaur();
    IPoint wheresExit();
    Wall getTopWall(IPoint p);
    Wall getLeftWall(IPoint p);
}

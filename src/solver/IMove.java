package solver;

import communal.IPoint;
import game.Direction;

/**
 * Created by Simon Winder on 18/04/2017.
 */
public interface IMove {
    void addPossibleMove(Direction direction);
    boolean nextDirection();
    FullDirection getDirection();
    IPoint getTheseusStart();
    IPoint getMinotaurStart();
}

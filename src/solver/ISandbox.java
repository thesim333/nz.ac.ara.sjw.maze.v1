package solver;

import game.Direction;
import game.ISavable;

import java.util.List;

/**
 * Created by Simon Winder on 19/04/2017.
 */
public interface ISandbox {
    void begin();
    boolean isSolved();
    List<Direction> getSolution();
    void createGameState(ISavable game);
}

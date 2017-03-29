package filer;
import game.ISavable;

/**
 * Created by Simon Winder on 26/03/2017.
 */
public interface ISaver {
    void save(ISavable game);
    void save(ISavable game, String fileName);
    void save(ISavable game, String fileName, String levelName);
}

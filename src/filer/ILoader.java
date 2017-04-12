package filer;

import game.ILoadable;

/**
 * Created by Simon Winder on 27/03/2017.
 */
public interface ILoader {
    void loadNextLevel(ILoadable game);
    void loadLevel(ILoadable game, int level);
    void loadSave(ILoadable game, String fileName);
    void loadSave(ILoadable game);
}

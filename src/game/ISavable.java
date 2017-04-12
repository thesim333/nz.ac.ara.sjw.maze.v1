package game;
import communal.Wall;
import communal.IPoint;

/**
 * Created by Simon Winder on 26/03/2017.
 */
public interface ISavable {
    int getWidthAcross();
    int getDepthDown();
    Wall whatsAbove(IPoint where);
    Wall whatsLeft(IPoint where);
    IPoint wheresTheseus();
    IPoint wheresMinotaur();
    IPoint wheresExit();
    String getLevelName();
}

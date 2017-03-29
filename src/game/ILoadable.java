package game;
import communal.IPoint;

/**
 * Created by Simon Winder on 26/03/2017.
 */
public interface ILoadable {
    void createHorizontalWallArea(int depthDown, int widthAcross);
    void createVerticalWallArea(int depthDown, int widthAcross);
    void addWallAbove(IPoint where);
    void addWallLeft(IPoint where);
    void addTheseus(IPoint where);
    void addMinotaur(IPoint where);
    void addExit(IPoint where);
    void setName(String name);
}

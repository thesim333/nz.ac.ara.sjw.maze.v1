package game;
import communal.IPoint;

/**
 * Created by Simon Winder on 26/03/2017.
 */
public interface ILoadable {
    void setDepthDown(int depthDown);
    void setWidthAcross(int widthAcross);
    void addWallAbove(IPoint where);
    void addWallLeft(IPoint where);
    void addTheseus(IPoint where);
    void addMinotaur(IPoint where);
    void addExit(IPoint where);
    void setName(String name);
}

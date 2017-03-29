import communal.Point;
import communal.Wall;
import filer.Filer;
import filer.ILoader;
import game.Direction;
import game.Game;
import game.IGame;
import game.ILoadable;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Simon Winder on 29/03/2017.
 */
public class Tests {
    @Test
    public void loadsLevel1GameHasThingsLoaded() {
        IGame game = new Game();
        ILoader loader = new Filer();
        loader.loadNextLevel((ILoadable)game);
        assertEquals("Maze 1 (3 x 3)", game.getLevelName());
        assertTrue(new Point(2, 1).equals(game.getTheseusLocation()));
        assertTrue(new Point(0, 1).equals(game.getMinotaurLocation()));
        assertTrue(new Point(1, 3).equals(game.getExitLocation()));
        assertEquals(0, game.getMoveCount());
    }

    @Test
    public void level1TestsLeftWallsSomething() {
        IGame game = new Game();
        ILoader loader = new Filer();
        loader.loadNextLevel((ILoadable)game);
        assertEquals(Wall.SOMETHING, game.getLeftWall(new Point(0, 0)));
        assertEquals(Wall.SOMETHING, game.getLeftWall(new Point(1, 0)));
        assertEquals(Wall.SOMETHING, game.getLeftWall(new Point(2, 0)));
        assertEquals(Wall.SOMETHING, game.getLeftWall(new Point(0, 3)));
        assertEquals(Wall.SOMETHING, game.getLeftWall(new Point(1, 2)));
        assertEquals(Wall.SOMETHING, game.getLeftWall(new Point(2, 3)));
    }

    @Test
    public void level1TestsLeftWallsNothing() {
        IGame game = new Game();
        ILoader loader = new Filer();
        loader.loadNextLevel((ILoadable)game);
        assertEquals(Wall.NOTHING, game.getLeftWall(new Point(0, 1)));
        assertEquals(Wall.NOTHING, game.getLeftWall(new Point(0, 2)));
        assertEquals(Wall.NOTHING, game.getLeftWall(new Point(1, 1)));
        assertEquals(Wall.NOTHING, game.getLeftWall(new Point(1, 3)));
        assertEquals(Wall.NOTHING, game.getLeftWall(new Point(2, 1)));
        assertEquals(Wall.NOTHING, game.getLeftWall(new Point(2, 2)));
    }

    @Test
    public void level1TestsTopWallsSomething() {
        IGame game = new Game();
        ILoader loader = new Filer();
        loader.loadNextLevel((ILoadable)game);
        assertEquals(Wall.SOMETHING, game.getTopWall(new Point(0, 0)));
        assertEquals(Wall.SOMETHING, game.getTopWall(new Point(0, 1)));
        assertEquals(Wall.SOMETHING, game.getTopWall(new Point(0, 2)));
        assertEquals(Wall.SOMETHING, game.getTopWall(new Point(1, 1)));
        assertEquals(Wall.SOMETHING, game.getTopWall(new Point(1, 3)));
        assertEquals(Wall.SOMETHING, game.getTopWall(new Point(2, 1)));
        assertEquals(Wall.SOMETHING, game.getTopWall(new Point(2, 3)));
        assertEquals(Wall.SOMETHING, game.getTopWall(new Point(3, 0)));
        assertEquals(Wall.SOMETHING, game.getTopWall(new Point(3, 1)));
        assertEquals(Wall.SOMETHING, game.getTopWall(new Point(3, 2)));
    }

    @Test
    public void level1TestsTopWallsNothing() {
        IGame game = new Game();
        ILoader loader = new Filer();
        loader.loadNextLevel((ILoadable)game);
        assertEquals(Wall.NOTHING, game.getTopWall(new Point(0, 3)));
        assertEquals(Wall.NOTHING, game.getTopWall(new Point(1, 0)));
        assertEquals(Wall.NOTHING, game.getTopWall(new Point(1, 2)));
        assertEquals(Wall.NOTHING, game.getTopWall(new Point(2, 0)));
        assertEquals(Wall.NOTHING, game.getTopWall(new Point(2, 2)));
        assertEquals(Wall.NOTHING, game.getTopWall(new Point(3, 3)));
    }

    @Test
    public void loadsLevel1TheseusCanMoveLeftIntoEmptySpace() {
        IGame game = new Game();
        ILoader loader = new Filer();
        loader.loadNextLevel((ILoadable)game);
        game.moveTheseus(Direction.LEFT);
        assertTrue(new Point(2, 0).equals(game.getTheseusLocation()));
    }

    @Test
    public void loadsLevel1TheseusCanMoveRightIntoEmptySpace() {
        IGame game = new Game();
        ILoader loader = new Filer();
        loader.loadNextLevel((ILoadable)game);
        game.moveTheseus(Direction.RIGHT);
        assertTrue(new Point(2, 2).equals(game.getTheseusLocation()));
    }

    @Test
    public void loadsLevel1TheseusCanMoveLeftIntoEmptySpaceMoveCountIncreased() {
        IGame game = new Game();
        ILoader loader = new Filer();
        loader.loadNextLevel((ILoadable)game);
        game.moveTheseus(Direction.LEFT);
        assertEquals(1 ,game.getMoveCount());
    }

    @Test
    public void loadsLevel1TheseusCanNotMoveUpIntoWall() {
        IGame game = new Game();
        ILoader loader = new Filer();
        loader.loadNextLevel((ILoadable)game);
        game.moveTheseus(Direction.UP);
        assertTrue(new Point(2, 1).equals(game.getTheseusLocation()));
    }

    @Test
    public void loadsLevel1TheseusCanNotMoveUpIntoWallMoveCountNotIncreased() {
        IGame game = new Game();
        ILoader loader = new Filer();
        loader.loadNextLevel((ILoadable)game);
        game.moveTheseus(Direction.UP);
        assertEquals(0, game.getMoveCount());
    }

    @Test
    public void loadsLevel1TheseusCanNotMoveDownIntoWall() {
        IGame game = new Game();
        ILoader loader = new Filer();
        loader.loadNextLevel((ILoadable)game);
        game.moveTheseus(Direction.DOWN);
        assertTrue(new Point(2, 1).equals(game.getTheseusLocation()));
    }

    @Test
    public void loadsLevel1TheseusCanMoveRightThenUpMoveCountIncreased() {
        IGame game = new Game();
        ILoader loader = new Filer();
        loader.loadNextLevel((ILoadable)game);
        game.moveTheseus(Direction.RIGHT);
        game.moveTheseus(Direction.UP);
        assertEquals(2, game.getMoveCount());
    }

    @Test
    public void loadsLevel1TheseusCanMoveRightThenUp() {
        IGame game = new Game();
        ILoader loader = new Filer();
        loader.loadNextLevel((ILoadable)game);
        game.moveTheseus(Direction.RIGHT);
        game.moveTheseus(Direction.UP);
        assertTrue(new Point(1, 2).equals(game.getTheseusLocation()));
    }

    @Test
    public void loadsLevel1TheseusCanMoveLeftThenUp() {
        IGame game = new Game();
        ILoader loader = new Filer();
        loader.loadNextLevel((ILoadable)game);
        game.moveTheseus(Direction.LEFT);
        game.moveTheseus(Direction.UP);
        assertTrue(new Point(1, 0).equals(game.getTheseusLocation()));
    }

    @Test
    public void loadsLevel1TheseusCanMoveToExitGameWon() {
        IGame game = new Game();
        ILoader loader = new Filer();
        loader.loadNextLevel((ILoadable)game);
        game.moveTheseus(Direction.RIGHT);
        game.moveTheseus(Direction.UP);
        game.moveTheseus(Direction.RIGHT);
        assertTrue(game.isWon());
    }

    @Test
    public void loadsLevel1GameIsNotWon() {
        IGame game = new Game();
        ILoader loader = new Filer();
        loader.loadNextLevel((ILoadable)game);
        assertFalse(game.isWon());
    }

    @Test
    public void loadsLevel1GameIsNotLost() {
        IGame game = new Game();
        ILoader loader = new Filer();
        loader.loadNextLevel((ILoadable)game);
        assertFalse(game.isLost());
    }

    @Test
    public void loadsLevel1MinotaurWillNotMoveAtStartingPositions() {
        IGame game = new Game();
        ILoader loader = new Filer();
        loader.loadNextLevel((ILoadable)game);
        game.moveMinotaur();
        game.moveMinotaur();
        assertTrue(new Point(0, 1).equals(game.getMinotaurLocation()));
    }

    @Test
    public void loadsLevel1MoveTheseusLeftMoveMinotaur() {
        IGame game = new Game();
        ILoader loader = new Filer();
        loader.loadNextLevel((ILoadable)game);
        game.moveTheseus(Direction.LEFT);
        game.moveMinotaur();
        game.moveMinotaur();
        assertTrue(new Point(1, 0).equals(game.getMinotaurLocation()));
    }

    @Test
    public void loadsLevel1Play2Turns() {
        IGame game = new Game();
        ILoader loader = new Filer();
        loader.loadNextLevel((ILoadable)game);
        game.moveTheseus(Direction.LEFT);
        game.moveMinotaur();
        game.moveMinotaur();
        game.moveTheseus(Direction.RIGHT);
        game.moveMinotaur();
        game.moveMinotaur();
        assertTrue(new Point(1, 1).equals(game.getMinotaurLocation()));
    }

    @Test
    public void LoadsLevel1Play2TurnsGameNotOver() {
        IGame game = new Game();
        ILoader loader = new Filer();
        loader.loadNextLevel((ILoadable)game);
        game.moveTheseus(Direction.LEFT);
        game.moveMinotaur();
        game.moveMinotaur();
        game.moveTheseus(Direction.RIGHT);
        game.moveMinotaur();
        game.moveMinotaur();
        assertFalse(game.isLost());
        assertFalse(game.isWon());
    }

    @Test
    public void LoadsLevel1MinotaurMovesToTheseusGameLost() {
        IGame game = new Game();
        ILoader loader = new Filer();
        loader.loadNextLevel((ILoadable)game);
        game.moveTheseus(Direction.LEFT);
        game.moveMinotaur();
        game.moveMinotaur();
        game.moveMinotaur();
        assertTrue(game.isLost());
    }

    @Test
    public void LoadsLevel1MinotaurMovesToTheseusMinotaurPosition() {
        IGame game = new Game();
        ILoader loader = new Filer();
        loader.loadNextLevel((ILoadable)game);
        game.moveTheseus(Direction.LEFT);
        game.moveMinotaur();
        game.moveMinotaur();
        game.moveMinotaur();
        assertTrue(new Point(2, 0).equals(game.getTheseusLocation()));
        assertTrue(new Point(2, 0).equals(game.getMinotaurLocation()));
    }

    @Test
    public void LoadsLevel1PlayWonGame() {
        IGame game = new Game();
        ILoader loader = new Filer();
        loader.loadNextLevel((ILoadable)game);
        game.moveTheseus(Direction.LEFT);
        assertFalse(game.isWon());
        game.moveMinotaur();
        assertFalse(game.isLost());
        game.moveMinotaur();
        assertFalse(game.isLost());
        game.moveTheseus(Direction.RIGHT);
        assertFalse(game.isWon());
        game.moveMinotaur();
        assertFalse(game.isLost());
        game.moveMinotaur();
        assertFalse(game.isLost());
        game.moveTheseus(Direction.RIGHT);
        assertFalse(game.isWon());
        game.moveMinotaur();
        assertFalse(game.isLost());
        game.moveMinotaur();
        assertFalse(game.isLost());
        game.moveTheseus(Direction.UP);
        assertFalse(game.isWon());
        game.moveMinotaur();
        assertFalse(game.isLost());
        game.moveMinotaur();
        assertFalse(game.isLost());
        game.moveTheseus(Direction.RIGHT);
        assertTrue(game.isWon());
        assertTrue(new Point(1, 1).equals(game.getMinotaurLocation()));
    }

    @Test
    public void loadsLevel2() {

    }

    @Test
    public void loadsLevel2LoadNext() {

    }

    @Test
    public void loadLevel3() {

    }

    @Test
    public void loadLevel4() {

    }

    @Test
    public void loadLevel5() {

    }

    @Test
    public void loadLevel6() {

    }

    @Test
    public void loadLevel7() {

    }

    @Test
    public void loadLevel8() {

    }

    @Test
    public void loadLevel9() {

    }

    @Test
    public void loadLevel10() {

    }

    @Test
    public void loadLevel1NotLevel10() {

    }

    @Test
    public void saveGameLoadSave() {

    }
}

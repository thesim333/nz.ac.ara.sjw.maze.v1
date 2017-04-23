import communal.Point;
import communal.Wall;
import filer.Filer;
import filer.ILoader;
import filer.ISaver;
import game.*;
import org.junit.Test;
import solver.ISandbox;
import solver.SandboxGame;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Simon Winder on 29/03/2017.
 */
public class Tests {
    protected IGame loadFirstLevel() {
        IGame game = new Game();
        ILoader loader = new Filer();
        loader.loadNextLevel((ILoadable)game);
        return game;
    }

    @Test
    public void loadsLevel1GameHasThingsLoaded() {
        IGame game = loadFirstLevel();
        assertEquals("Maze 1 (3 x 3)", game.getLevelName());
        assertTrue(new Point(2, 1).equals(game.wheresTheseus()));
        assertTrue(new Point(0, 1).equals(game.wheresMinotaur()));
        assertTrue(new Point(1, 3).equals(game.wheresExit()));
        assertEquals(0, game.getMoveCount());
    }

    @Test
    public void level1TestsLeftWallsSomething() {
        IGame game = loadFirstLevel();
        assertEquals(Wall.SOMETHING, game.getLeftWall(new Point(0, 0)));
        assertEquals(Wall.SOMETHING, game.getLeftWall(new Point(1, 0)));
        assertEquals(Wall.SOMETHING, game.getLeftWall(new Point(2, 0)));
        assertEquals(Wall.SOMETHING, game.getLeftWall(new Point(0, 3)));
        assertEquals(Wall.SOMETHING, game.getLeftWall(new Point(1, 2)));
        assertEquals(Wall.SOMETHING, game.getLeftWall(new Point(2, 3)));
    }

    @Test
    public void level1TestsLeftWallsNothing() {
        IGame game = loadFirstLevel();
        assertEquals(Wall.NOTHING, game.getLeftWall(new Point(0, 1)));
        assertEquals(Wall.NOTHING, game.getLeftWall(new Point(0, 2)));
        assertEquals(Wall.NOTHING, game.getLeftWall(new Point(1, 1)));
        assertEquals(Wall.NOTHING, game.getLeftWall(new Point(1, 3)));
        assertEquals(Wall.NOTHING, game.getLeftWall(new Point(2, 1)));
        assertEquals(Wall.NOTHING, game.getLeftWall(new Point(2, 2)));
    }

    @Test
    public void level1TestsTopWallsSomething() {
        IGame game = loadFirstLevel();
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
        IGame game = loadFirstLevel();
        assertEquals(Wall.NOTHING, game.getTopWall(new Point(0, 3)));
        assertEquals(Wall.NOTHING, game.getTopWall(new Point(1, 0)));
        assertEquals(Wall.NOTHING, game.getTopWall(new Point(1, 2)));
        assertEquals(Wall.NOTHING, game.getTopWall(new Point(2, 0)));
        assertEquals(Wall.NOTHING, game.getTopWall(new Point(2, 2)));
        assertEquals(Wall.NOTHING, game.getTopWall(new Point(3, 3)));
    }

    @Test
    public void loadsLevel1TheseusCanMoveLeftIntoEmptySpace() {
        IGame game = loadFirstLevel();
        boolean moved = game.moveTheseus(Direction.LEFT);
        assertTrue(moved);
        assertTrue(new Point(2, 0).equals(game.wheresTheseus()));
    }

    @Test
    public void loadsLevel1TheseusCanMoveRightIntoEmptySpace() {
        IGame game = loadFirstLevel();
        boolean moved = game.moveTheseus(Direction.RIGHT);
        assertTrue(moved);
        assertTrue(new Point(2, 2).equals(game.wheresTheseus()));
    }

    @Test
    public void loadsLevel1TheseusCanMoveLeftIntoEmptySpaceMoveCountIncreased() {
        IGame game = loadFirstLevel();
        boolean moved = game.moveTheseus(Direction.LEFT);
        assertTrue(moved);
        assertEquals(1 ,game.getMoveCount());
    }

    @Test
    public void loadsLevel1TheseusCanNotMoveUpIntoWall() {
        IGame game = loadFirstLevel();
        boolean moved = game.moveTheseus(Direction.UP);
        assertFalse(moved);
        assertTrue(new Point(2, 1).equals(game.wheresTheseus()));
    }

    @Test
    public void loadsLevel1TheseusCanNotMoveUpIntoWallMoveCountNotIncreased() {
        IGame game = loadFirstLevel();
        boolean moved = game.moveTheseus(Direction.UP);
        assertFalse(moved);
        assertEquals(0, game.getMoveCount());
    }

    @Test
    public void loadsLevel1TheseusCanNotMoveDownIntoWall() {
        IGame game = loadFirstLevel();
        boolean moved = game.moveTheseus(Direction.DOWN);
        assertFalse(moved);
        assertTrue(new Point(2, 1).equals(game.wheresTheseus()));
    }

    @Test
    public void loadsLevel1TheseusCanMoveRightThenUpMoveCountIncreased() {
        IGame game = loadFirstLevel();
        boolean moved = game.moveTheseus(Direction.RIGHT);
        assertTrue(moved);
        moved = game.moveTheseus(Direction.UP);
        assertTrue(moved);
        assertEquals(2, game.getMoveCount());
    }

    @Test
    public void loadsLevel1TheseusCanMoveRightThenUp() {
        IGame game = loadFirstLevel();
        boolean moved = game.moveTheseus(Direction.RIGHT);
        assertTrue(moved);
        moved = game.moveTheseus(Direction.UP);
        assertTrue(moved);
        assertTrue(new Point(1, 2).equals(game.wheresTheseus()));
    }

    @Test
    public void loadsLevel1TheseusCanMoveLeftThenUp() {
        IGame game = loadFirstLevel();
        game.moveTheseus(Direction.LEFT);
        game.moveTheseus(Direction.UP);
        assertTrue(new Point(1, 0).equals(game.wheresTheseus()));
    }

    @Test
    public void loadsLevel1TheseusCanMoveToExitGameWon() {
        IGame game = loadFirstLevel();
        game.moveTheseus(Direction.RIGHT);
        game.moveTheseus(Direction.UP);
        game.moveTheseus(Direction.RIGHT);
        assertTrue(game.isWon());
    }

    @Test
    public void loadsLevel1GameIsNotWon() {
        IGame game = loadFirstLevel();
        assertFalse(game.isWon());
    }

    @Test
    public void loadsLevel1GameIsNotLost() {
        IGame game = loadFirstLevel();
        assertFalse(game.isLost());
    }

    @Test
    public void loadsLevel1PauseTheseusMoveCount0() {
        IGame game = loadFirstLevel();
        game.pauseTheseus();
        assertEquals(1, game.getMoveCount());
    }

    @Test
    public void loadsLevel1MinotaurWillNotMoveAtStartingPositions() {
        IGame game = loadFirstLevel();
        game.moveMinotaur();
        game.moveMinotaur();
        assertTrue(new Point(0, 1).equals(game.wheresMinotaur()));
    }

    @Test
    public void loadsLevel1MoveTheseusLeftMoveMinotaur() {
        IGame game = loadFirstLevel();
        game.moveTheseus(Direction.LEFT);
        game.moveMinotaur();
        game.moveMinotaur();
        assertTrue(new Point(1, 0).equals(game.wheresMinotaur()));
    }

    @Test
    public void loadsLevel1Play2Turns() {
        IGame game = loadFirstLevel();
        game.moveTheseus(Direction.LEFT);
        game.moveMinotaur();
        game.moveMinotaur();
        game.moveTheseus(Direction.RIGHT);
        game.moveMinotaur();
        game.moveMinotaur();
        assertTrue(new Point(1, 1).equals(game.wheresMinotaur()));
    }

    @Test
    public void LoadsLevel1Play2TurnsGameNotOver() {
        IGame game = loadFirstLevel();
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
        IGame game = loadFirstLevel();
        game.moveTheseus(Direction.LEFT);
        game.moveMinotaur();
        game.moveMinotaur();
        game.moveMinotaur();
        assertTrue(game.isLost());
    }

    @Test
    public void LoadsLevel1MinotaurMovesToTheseusCheckPositions() {
        IGame game = loadFirstLevel();
        game.moveTheseus(Direction.LEFT);
        game.moveMinotaur();
        game.moveMinotaur();
        game.moveMinotaur();
        assertTrue(new Point(2, 0).equals(game.wheresTheseus()));
        assertTrue(new Point(2, 0).equals(game.wheresMinotaur()));
    }

    //Hard coded game move.
    @Test
    public void LoadsLevel1PlayWonGame() {
        IGame game = loadFirstLevel();
        assertTrue(game.moveTheseus(Direction.LEFT));
        assertFalse(game.isWon());
        assertFalse(game.isLost());
        game.moveMinotaur();
        assertFalse(game.isLost());
        game.moveMinotaur();
        assertFalse(game.isLost());
        assertTrue(game.moveTheseus(Direction.RIGHT));
        assertFalse(game.isWon());
        assertFalse(game.isLost());
        game.moveMinotaur();
        assertFalse(game.isLost());
        game.moveMinotaur();
        assertFalse(game.isLost());
        assertTrue(game.moveTheseus(Direction.RIGHT));
        assertFalse(game.isWon());
        assertFalse(game.isLost());
        game.moveMinotaur();
        assertFalse(game.isLost());
        game.moveMinotaur();
        assertFalse(game.isLost());
        assertTrue(game.moveTheseus(Direction.UP));
        assertFalse(game.isWon());
        assertFalse(game.isLost());
        game.moveMinotaur();
        assertFalse(game.isLost());
        game.moveMinotaur();
        assertFalse(game.isLost());
        assertTrue(game.moveTheseus(Direction.RIGHT));
        assertTrue(game.isWon());
        assertFalse(game.isLost());
        assertTrue(new Point(1, 1).equals(game.wheresMinotaur()));
    }

    @Test
    public void loadsLevel2() {
        IGame game = new Game();
        ILoader loader = new Filer();
        loader.loadLevel((ILoadable)game, 1);
        assertEquals("Maze 2 (7 x 4)", game.getLevelName());
    }

    @Test
    public void loadsLevel2LoadNext() {
        IGame game = new Game();
        ILoader filer = new Filer();
        filer.loadNextLevel((ILoadable)game);
        game = new Game();
        filer.loadNextLevel((ILoadable)game);
        assertEquals("Maze 2 (7 x 4)", game.getLevelName());
    }

    @Test
    public void loadLevel3() {
        IGame game = new Game();
        ILoader loader = new Filer();
        loader.loadLevel((ILoadable)game, 2);
        assertEquals("Maze 3 (3 x 4)", game.getLevelName());
    }

    @Test
    public void loadLevel4() {
        IGame game = new Game();
        ILoader loader = new Filer();
        loader.loadLevel((ILoadable)game, 3);
        assertEquals("Maze 4 (5 x 5)", game.getLevelName());
    }

    @Test
    public void loadLevel5() {
        IGame game = new Game();
        ILoader loader = new Filer();
        loader.loadLevel((ILoadable)game, 4);
        assertEquals("Maze 5 (7 x 5)", game.getLevelName());
    }

    @Test
    public void loadLevel6() {
        IGame game = new Game();
        ILoader loader = new Filer();
        loader.loadLevel((ILoadable)game, 5);
        assertEquals("Maze 6 (6 x 6)", game.getLevelName());
    }

    @Test
    public void loadLevel7() {
        IGame game = new Game();
        ILoader loader = new Filer();
        loader.loadLevel((ILoadable)game, 6);
        assertEquals("Maze 7 (6 x 6)", game.getLevelName());
    }

    @Test
    public void loadLevel8() {
        IGame game = new Game();
        ILoader loader = new Filer();
        loader.loadLevel((ILoadable)game, 7);
        assertEquals("Maze 8 (9 x 8)", game.getLevelName());
    }

    @Test
    public void loadLevel9() {
        IGame game = new Game();
        ILoader loader = new Filer();
        loader.loadLevel((ILoadable)game, 8);
        assertEquals("Maze 9 (9 x 8)", game.getLevelName());
    }

    @Test
    public void loadLevel10() {
        IGame game = new Game();
        ILoader loader = new Filer();
        loader.loadLevel((ILoadable)game, 9);
        assertEquals("Maze 10 (8 x 8)", game.getLevelName());
    }

    @Test
    public void loadLevel1AfterLevel10() {
        IGame game = new Game();
        ILoader loader = new Filer();
        loader.loadLevel((ILoadable)game, 9);
        game = new Game();
        loader.loadNextLevel((ILoadable)game);
        assertEquals("Maze 1 (3 x 3)", game.getLevelName());
    }

    @Test
    public void saveGameLoadSaveDefault() {
        IGame game = loadFirstLevel();
        assertTrue(new Point(2, 1).equals(game.wheresTheseus()));
        game.moveTheseus(Direction.RIGHT);
        assertTrue(new Point(2, 2).equals(game.wheresTheseus()));
        ISaver saver = new Filer();
        saver.save((ISavable)game);
        ILoader loader = new Filer();
        IGame game2 = new Game();
        loader.loadSave((ILoadable)game2);
        assertTrue(new Point(2, 2).equals(game2.wheresTheseus()));
    }

    @Test
    public void saveGameLoadSaveFileSpecified() {
        IGame game = loadFirstLevel();
        assertTrue(new Point(2, 1).equals(game.wheresTheseus()));
        game.moveTheseus(Direction.LEFT);
        assertTrue(new Point(2, 0).equals(game.wheresTheseus()));
        ISaver saver = new Filer();
        saver.save((ISavable)game, "saves.xml");
        ILoader loader = new Filer();
        IGame game2 = new Game();
        loader.loadSave((ILoadable)game2, "saves.xml");
        assertTrue(new Point(2, 0).equals(game2.wheresTheseus()));
    }

    @Test
    public void saveGameLoadSaveFileLevelSpecified() {
        IGame game = loadFirstLevel();
        assertTrue(new Point(2, 1).equals(game.wheresTheseus()));
        game.moveTheseus(Direction.LEFT);
        game.moveTheseus(Direction.UP);
        assertTrue(new Point(1, 0).equals(game.wheresTheseus()));
        ISaver saver = new Filer();
        saver.save((ISavable)game, "saves.xml", "TestSave");
        ILoader loader = new Filer();
        IGame game2 = new Game();
        loader.loadSave((ILoadable)game2, "saves.xml", "TestSave");
        assertTrue(new Point(1, 0).equals(game2.wheresTheseus()));
    }

    @Test
    public void loadAndSaveAndLoadLevel5() {
        IGame game = new Game();
        ILoader loader = new Filer();
        loader.loadLevel((ILoadable)game, 4);
        assertTrue(game.moveTheseus(Direction.LEFT));
        assertTrue(new Point(1, 1).equals(game.wheresTheseus()));
        ISaver saver = new Filer();
        saver.save((ISavable) game, "saves.xml", "TestSave5");
        IGame game2 = new Game();
        loader.loadSave((ILoadable)game2, "saves.xml", "TestSave5");
        assertTrue(new Point(1, 1).equals(game2.wheresTheseus()));
    }

    @Test
    public void loadAndSolveLevel1() {
        IGame game = this.loadFirstLevel();
        ISandbox solvable = new SandboxGame();
        solvable.createGameState((ISavable)game);
        solvable.begin();
        assertTrue(solvable.isSolved());
    }

    @Test
    public void loadAndSolveLevel2() {
        IGame game = new Game();
        ILoader loader = new Filer();
        loader.loadLevel((ILoadable)game, 1);
        ISandbox solvable = new SandboxGame();
        solvable.createGameState((ISavable)game);
        solvable.begin();
        assertTrue(solvable.isSolved());
    }
}

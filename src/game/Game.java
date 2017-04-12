package game;

import communal.IPoint;
import communal.Point;
import communal.Wall;

/**
 * Created by Simon Winder on 26/03/2017.
 */
public class Game implements IGame, ISavable, ILoadable {
    //walls stored in [row][col] format
	protected Wall[][] topWalls;
    protected Wall[][] leftWalls;
    protected IPoint theseus;
    protected IPoint minotaur;
    protected IPoint exit;
    protected int moveCount = 0;
    protected String levelName;
    protected int depth = 0;
    protected int width = 0;

	@Override
	public void pauseTheseus() {
		this.moveCount++;
	}

	@Override
    public Wall getTopWall(IPoint p) {
        return this.topWalls[p.getRow()][p.getCol()];
    }

    @Override
    public Wall getLeftWall(IPoint p) {
        return this.leftWalls[p.getRow()][p.getCol()];
    }

    @Override
    public void moveMinotaur() {
    	//can move twice
		//Each move must come from controller
		//With check for game state and minotaur position between

		//get minotaur move direction
		// + = LEFT
		// - = RIGHT
        int relativeColPosition = minotaur.getCol() - theseus.getCol();
		// + = UP
		// - = RIGHT
        int relativeRowPosition = minotaur.getRow() - theseus.getRow();

		//try move left/right
		if (relativeColPosition > 0 &&
				this.isWallBlocking(Direction.LEFT, this.minotaur) == Wall.NOTHING) {
			// move left
		   this.moveMyThing(this.minotaur, Direction.LEFT);
		}
		else if (relativeColPosition < 0 &&
				isWallBlocking(Direction.RIGHT, this.minotaur) == Wall.NOTHING) {
			//move right
			this.moveMyThing(this.minotaur, Direction.RIGHT);
		}
		//try move up/down
		else if (relativeRowPosition > 0 &&
				this.isWallBlocking(Direction.UP, this.minotaur) == Wall.NOTHING) {
			//move up
			this.moveMyThing(this.minotaur, Direction.UP);
		}
		else if (relativeRowPosition < 0 &&
				this.isWallBlocking(Direction.DOWN, this.minotaur) == Wall.NOTHING) {
			this.moveMyThing(this.minotaur, Direction.DOWN);
		}
    }

    protected void moveMyThing(IPoint thing, Direction direction) {
        thing.translate(direction.rowAdjust, direction.colAdjust);
    }

    @Override
    public boolean moveTheseus(Direction direction) {
    	if (isWallBlocking(direction, this.theseus) == Wall.NOTHING) {
    		//Move Theseus
    		this.moveMyThing(this.theseus, direction);
    		//count + 1
    		moveCount++;
    		return true;
    	}
    	return false;
    }
    
    protected Wall isWallBlocking(Direction direction, IPoint p) {
    	Wall thisWall = Wall.NOTHING;
    	switch(direction) {
			case UP:
				thisWall = topWalls[p.getRow()][p.getCol()];
				break;
			case DOWN:
				thisWall = topWalls[p.getRow() + 1][p.getCol()];
				break;
			case LEFT:
				thisWall = leftWalls[p.getRow()][p.getCol()];
				break;
			case RIGHT:
				thisWall = leftWalls[p.getRow()][p.getCol() + 1];
		}
		return thisWall;
    }
    
    @Override
    public int getMoveCount() {
    	return moveCount;
    }
    
    @Override
    public boolean isWon() {
    	return theseus.equals(exit);
    }
    
    @Override
    public boolean isLost() {
    	return theseus.equals(minotaur);
    }
    
	@Override
	public int getWidthAcross() {
		return topWalls[0].length;
	}

	@Override
	public int getDepthDown() {
		return topWalls.length;
	}

	@Override
	public Wall whatsAbove(IPoint where) {
		return topWalls[where.getRow()][where.getCol()];
	}

	@Override
	public Wall whatsLeft(IPoint where) {
		return leftWalls[where.getRow()][where.getCol()];
	}

	@Override
	public IPoint wheresTheseus() {
		return theseus;
	}

	@Override
	public IPoint wheresMinotaur() {
		return minotaur;
	}

	@Override
	public IPoint wheresExit() {
		return exit;
	}

	@Override
	public void setDepthDown(int depthDown) {
		this.depth = depthDown;

		if (this.width > 0) {
			this.createWallArea();
		}
	}

	@Override
	public void setWidthAcross(int widthAcross) {
		this.width = widthAcross;

		if (this.depth > 0) {
			this.createWallArea();
		}
	}

	protected void createWallArea() {
        this.leftWalls = new Wall[this.depth][this.width];
        this.topWalls = new Wall[this.depth][this.width];

		for (int i = 0; i < this.depth; ++i){
			for (int j = 0; j < this.width; j++) {
				this.topWalls[i][j] = Wall.NOTHING;
				this.leftWalls[i][j] = Wall.NOTHING;
			}
        }
    }

	@Override
    public String getLevelName() {
        return this.levelName;
    }

    @Override
	public void addWallAbove(IPoint where) {
		this.topWalls[where.getRow()][where.getCol()] = Wall.SOMETHING;
	}

	@Override
	public void addWallLeft(IPoint where) {
		this.leftWalls[where.getRow()][where.getCol()] = Wall.SOMETHING;
	}

	@Override
	public void addTheseus(IPoint where) {
		this.theseus = new Point(where);
	}

	@Override
	public void addMinotaur(IPoint where) {
		this.minotaur = new Point(where);
	}

	@Override
	public void addExit(IPoint where) {
		this.exit = new Point(where);
	}

	@Override
    public void setName(String name) {
        this.levelName = name;
    }
}

package game;

import communal.IPoint;
import communal.Point;
import communal.Wall;

/**
 * Created by Simon Winder on 26/03/2017.
 */
public class Game implements IGame, ISavable, ILoadable {
    //walls stored in [row][col] format
	private Wall[][] topWalls;
    private Wall[][] leftWalls;
    private IPoint theseus;
    private IPoint minotaur;
    private IPoint exit;
    private int moveCount = 0;
    private String levelName;

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
		   this.actuallyMoveMinotaur(Direction.LEFT);
		}
		else if (relativeColPosition < 0 &&
				isWallBlocking(Direction.RIGHT, this.minotaur) == Wall.NOTHING) {
			//move right
			this.actuallyMoveMinotaur(Direction.RIGHT);
		}
		//try move up/down
		else if (relativeRowPosition > 0 &&
				this.isWallBlocking(Direction.UP, this.minotaur) == Wall.NOTHING) {
			//move up
			this.actuallyMoveMinotaur(Direction.UP);
		}
		else if (relativeRowPosition < 0 &&
				this.isWallBlocking(Direction.DOWN, this.minotaur) == Wall.NOTHING) {
			this.actuallyMoveMinotaur(Direction.DOWN);
		}
    }

    private void actuallyMoveMinotaur(Direction direction) {
        minotaur.translate(direction.rowAdjust, direction.colAdjust);
    }

    @Override
    public void moveTheseus(Direction direction) {
    	if (isWallBlocking(direction, theseus) == Wall.NOTHING) {
    		//Move Theseus
    		theseus.translate(direction.rowAdjust, direction.colAdjust);
    		//count + 1
    		moveCount++;
    	}
    }
    
    private Wall isWallBlocking(Direction direction, IPoint p) {
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
    public void createHorizontalWallArea(int depthDown, int widthAcross) {
        this.topWalls = new Wall[depthDown][widthAcross];
        for (int i = 0; i < depthDown; ++i){
        	for (int j = 0; j < widthAcross; j++) {
				this.topWalls[i][j] = Wall.NOTHING;
			}
        }
    }

    @Override
    public void createVerticalWallArea(int depthDown, int widthAcross) {
        this.leftWalls = new Wall[depthDown][widthAcross];
		for (int i = 0; i < depthDown; ++i){
			for (int j = 0; j < widthAcross; j++) {
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

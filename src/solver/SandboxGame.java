package solver;

import communal.IPoint;
import communal.Point;
import communal.Wall;
import game.Direction;
import game.Game;
import game.ISavable;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by Simon Winder on 19/04/2017.
 */
public class SandboxGame extends Game implements ISandbox {
    protected Stack<IMove> solution = new Stack<>();

    @Override
    public void createGameState(ISavable game) {
        this.setDepthDown(game.getDepthDown());
        this.setWidthAcross(game.getWidthAcross());
        this.installWalls(game);
        this.addTheseus(game.wheresTheseus());
        this.addMinotaur(game.wheresMinotaur());
        this.addExit(game.wheresExit());
    }

    protected void installWalls(ISavable game) {
        for (int r = 0; r < this.depth; r++) {
            for (int c = 0; c < width; c++) {
                IPoint p = new Point(r, c);
                if (game.whatsAbove(p) == Wall.SOMETHING) {
                    this.addWallAbove(p);
                }
                if (game.whatsLeft(p) == Wall.SOMETHING) {
                    this.addWallLeft(p);
                }
            }
        }
    }

    @Override
    public void begin() {
        //Try to move in direction of exit, then try to continue
        boolean canAddNewMove = true;
        while (!this.isWon()) {
            if (canAddNewMove) {
                this.setMove();
            }
            canAddNewMove = true;
            if (!this.makeMoves()) {
                if (!revertMove()) {
                    break;
                }
                canAddNewMove = false;
            }
            //check moves aren't repeating
            //pause and minotaur didn't move
            if (!this.pauseWasLegit() || this.gameStateHasRepeated()) {
                if (!this.revertMove()) {
                    break;
                }
                canAddNewMove = false;
            }
        }
    }

    protected boolean pauseWasLegit() {
        //full game turn no movement
        return !(this.solution.peek().getDirection() == FullDirection.PASS &&
                this.solution.peek().getMinotaurStart().equals(this.minotaur));
    }

    protected boolean gameStateHasRepeated() {
        for (int i = this.solution.size() - 2; i >= 0; i--) {
            IMove thisMove = this.solution.elementAt(i);
            if (this.gameStateIsSame(thisMove.getTheseusStart(), thisMove.getMinotaurStart())) {
                return true;
            }
        }
        return false;
    }

    protected boolean gameStateIsSame(IPoint theseus, IPoint minotaur) {
        return (this.theseus.equals(theseus) && this.minotaur.equals(minotaur));
    }

    @Override
    public boolean isSolved() {
        return (!this.solution.isEmpty());
    }

    @Override
    public List<Direction> getSolution() {
        List<Direction> theSolution = new ArrayList<>();
        for (IMove theMove:
             this.solution) {
            theSolution.add(this.getFromFull(theMove.getDirection()));
        }
        return theSolution;
    }

    protected void setMove() {
        IMove theMove = new GameMove(this.theseus, this.minotaur);
        List<Direction> posDirections = this.getDirectionsForMove();

        for (Direction thisDirection:
             posDirections) {
            theMove.addPossibleMove(thisDirection);
        }
        theMove.nextDirection();
        this.solution.push(theMove);
    }

    protected List<Direction> getDirectionsForMove() {
        List<Direction> returnDirections = new ArrayList<>();
        Direction[] theDirections = new Direction[] {Direction.UP, Direction.LEFT, Direction.DOWN, Direction.RIGHT};

        Direction firstMove = this.getLastDirection();
        Direction firstHorizontal = this.getHorizontal();
        Direction firstVertical = this.getVertical();

        if (firstMove != null) {
            returnDirections.add(firstMove);
        }
        if (firstHorizontal != null && firstHorizontal != firstMove) {
            returnDirections.add(firstHorizontal);
        }
        if (firstVertical != null && firstVertical != firstMove) {
            returnDirections.add(firstVertical);
        }
        for (Direction thisDirection:
             theDirections) {
            if (!returnDirections.contains(thisDirection)) {
                returnDirections.add(thisDirection);
            }
        }

        for (int i = 0; i < returnDirections.size(); ) {
            if (this.wallIsBlocking(returnDirections.get(i), this.theseus)) {
                returnDirections.remove(i);
            }
            else {
                i++;
            }
        }
        return returnDirections;
    }

    protected Direction getLastDirection() {
        if (this.solution.isEmpty()) {
            return null;
        }
        else {
            return this.getFromFull(this.solution.peek().getDirection());
        }
    }

    protected Direction getHorizontal() {
        int colDiff = this.theseus.getCol() - this.exit.getCol();
        if (colDiff > 0) {
            return Direction.RIGHT;
        }
        else if (colDiff < 0) {
            return Direction.LEFT;
        }
        else {
            return null;
        }
    }

    protected Direction getVertical() {
        int rowDiff = this.theseus.getRow() - this.exit.getRow();
        if (rowDiff > 0) {
            return Direction.DOWN;
        }
        else if (rowDiff < 0) {
            return Direction.UP;
        }
        else {
            return null;
        }
    }

    protected boolean revertMove() {
        this.theseus.setLocation(this.solution.peek().getTheseusStart());
        this.minotaur.setLocation(this.solution.peek().getMinotaurStart());
        if (!this.solution.peek().nextDirection()) {
            if (this.solution.isEmpty()) {
                return false;
            }
            this.solution.pop();
            return this.revertMove();
        }
        return true;
    }

    protected boolean makeMoves() {
        FullDirection nextMove = this.solution.peek().getDirection();
        if (nextMove == FullDirection.PASS) {
            this.pauseTheseus();
        }
        else {
            this.moveTheseus(this.getFromFull(nextMove));
            if (this.isWon()) {
                return true;
            }
        }
        if (this.isLost()) {
            return false;
        }
        this.moveMinotaur();
        if (this.isLost()) {
            return false;
        }
        this.moveMinotaur();
        return (!this.isLost());
    }

    protected Direction getFromFull(FullDirection fd) {
        Direction direction = null;
        switch (fd) {
            case UP:
                direction = Direction.UP;
                break;
            case DOWN:
                direction = Direction.DOWN;
                break;
            case LEFT:
                direction = Direction.LEFT;
                break;
            case RIGHT:
                direction = Direction.RIGHT;
                break;
        }
        return direction;
    }
}

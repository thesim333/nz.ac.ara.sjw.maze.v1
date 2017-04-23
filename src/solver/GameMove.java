package solver;

import communal.IPoint;
import communal.Point;
import game.Direction;

import java.util.Stack;

/**
 * Created by Simon Winder on 18/04/2017.
 */
public class GameMove implements IMove{
    protected IPoint theseusStart;
    protected IPoint minotaurStart;
    protected Stack<FullDirection> couldBe;
    protected FullDirection moveDirectionIs;

    public GameMove(IPoint theseus, IPoint minotaur) {
        this.theseusStart = new Point(theseus);
        this.minotaurStart = new Point(minotaur);
        this.couldBe = new Stack<>();
        this.couldBe.add(FullDirection.PASS);
    }

    public void addPossibleMove(Direction direction) {
        this.couldBe.push(this.getFullDirection(direction));
    }

    public boolean nextDirection() {
        if (this.couldBe.isEmpty()) {
            return false;
        }
        else {
            this.moveDirectionIs = this.couldBe.pop();
            return true;
        }
    }

    @Override
    public IPoint getTheseusStart() {
        return this.theseusStart;
    }

    @Override
    public IPoint getMinotaurStart() {
        return this.minotaurStart;
    }

    public FullDirection getDirection() {
        return this.moveDirectionIs;
    }

    protected FullDirection getFullDirection(Direction direction) {
        FullDirection thisDirection = FullDirection.PASS;

        switch (direction) {
            case UP:
                thisDirection = FullDirection.UP;
                break;
            case DOWN:
                thisDirection = FullDirection.DOWN;
                break;
            case LEFT:
                thisDirection = FullDirection.LEFT;
                break;
            case RIGHT:
                thisDirection = FullDirection.RIGHT;
                break;
        }
        return thisDirection;
    }
}

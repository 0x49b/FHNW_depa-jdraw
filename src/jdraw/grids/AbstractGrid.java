package jdraw.grids;

import jdraw.framework.PointConstrainer;

import java.awt.*;

/**
 * Created by benjamin on 21.10.2014.
 */
public abstract class AbstractGrid implements PointConstrainer {

    private int step ;

    public AbstractGrid(int step) {
        this.step = step;
    }

    @Override
    public Point constrainPoint(Point p) {
        int i = (int) Math.round( p.x / ((double) getStep()));
        int j = (int) Math.round(p.y / ((double) getStep()));
        return new Point(i* getStep(), j*getStep());
    }

    @Override
    public int getStepX(boolean right) {
        return step;
    }

    @Override
    public int getStepY(boolean down) {
        return step;
    }

    int getStep() {
        return step;
    }

    @Override
    public void activate() {

    }

    @Override
    public void deactivate() {

    }

    @Override
    public void mouseDown() {

    }

    @Override
    public void mouseUp() {

    }
}

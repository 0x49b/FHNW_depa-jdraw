package jdraw.grids;

import jdraw.figures.Handles.Handle;
import jdraw.framework.DrawContext;
import jdraw.framework.Figure;
import jdraw.framework.FigureHandle;

import java.awt.*;
import java.util.AbstractList;
import java.util.List;

/**
 * Created by benjamin on 22.10.2014.
 */
public class SnapGrid extends AbstractGrid {

    private DrawContext context;
    private Iterable<Figure> figureList;

    public SnapGrid(DrawContext context) {
        super(0);
        this.context = context;
    }

    @Override
    public Point constrainPoint(Point p) {
        Point nearest = new Point(Integer.MAX_VALUE,Integer.MAX_VALUE);
        List<FigureHandle> handleList;
        for(Figure f : figureList) {
            handleList = f.getHandles();
            for (FigureHandle h : handleList) {
                if (nearest.distance(p.x, p.y) > h.getLocation().distance(p.x,p.y)) {
                    nearest = h.getLocation();
                }
            }
        }
        return nearest;
    }

    @Override
    public int getStepX(boolean right) {
        //TODO
        return 1;
    }

    @Override
    public int getStepY(boolean down) {
        //TODO
        return 1;
    }

    @Override
    public void activate() {
        figureList = context.getModel().getFigures();
    }

    @Override
    public void deactivate() {
        figureList = null;
    }

    @Override
    public void mouseDown() {

    }

    @Override
    public void mouseUp() {

    }
}

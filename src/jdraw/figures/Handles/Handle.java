package jdraw.figures.Handles;

import jdraw.framework.DrawView;
import jdraw.framework.Figure;
import jdraw.framework.FigureHandle;
import jdraw.framework.HandleState;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Created by benjamin on 10.10.2014.
 */
public class Handle implements FigureHandle {

    HandleState state;

    private static final int SIZE = 10;

    public Handle(HandleState state) {
        this.state = state;
    }

    public void setState(HandleState state) {
        this.state = state;
    }

    public HandleState getState() {
        return state;
    }

    @Override
    public Figure getOwner() {
        return state.getOwner();
    }

    @Override
    public Point getLocation() {
        return state.getAnchor();
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(getLocation().x - SIZE/2, getLocation().y - SIZE/2, SIZE, SIZE);
        g.setColor(Color.BLACK);
        g.drawRect(getLocation().x - SIZE/2, getLocation().y - SIZE/2, SIZE, SIZE);
    }

    @Override
    public Cursor getCursor() {
        return state.getCursor();
    }

    @Override
    public boolean contains(int x, int y) {
        Point p = getLocation();
        return Math.abs(x - p.x) < SIZE / 2
                && Math.abs(y - p.y) < SIZE / 2;
    }

    @Override
    public void startInteraction(int x, int y, MouseEvent e, DrawView v) {
        state.startInteraction(x, y, e, v);
    }

    @Override
    public void dragInteraction(int x, int y, MouseEvent e, DrawView v) {
        state.dragInteraction(x, y, e, v);
    }

    @Override
    public void stopInteraction(int x, int y, MouseEvent e, DrawView v) {
        state.stopInteraction(x, y, e, v);

    }

}

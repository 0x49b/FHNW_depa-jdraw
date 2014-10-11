package jdraw.figures;

import jdraw.framework.DrawView;
import jdraw.framework.Figure;
import jdraw.framework.FigureHandle;
import jdraw.framework.HandleState;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Created by benjamin on 10.10.2014.
 */
public abstract class Handles implements FigureHandle {

    Figure owner;
    Point location;
    HandleState state;
    int startX;
    int startY;

    private static final int SIZE = 10;
    private static final int HALF_SIZE = SIZE/2;



    public Handles(Figure owner, Point location) {
        this.owner = owner;
        this.location = location;
    }

    public void setState(HandleState state) {
        this.state = state;
    }

    @Override
    public Figure getOwner() {
        return owner;
    }

    @Override
    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(location.x - HALF_SIZE, location.y - HALF_SIZE, SIZE, SIZE);
        g.setColor(Color.BLACK);
        g.drawRect(location.x - HALF_SIZE, location.y - HALF_SIZE, SIZE, SIZE);
    }

    @Override
    public Cursor getCursor() {
       return state.getCursor();
    }

    @Override
    public boolean contains(int x, int y) {
        if ((location.x - HALF_SIZE < x) && (x < location.x + HALF_SIZE)
                && (location.y - HALF_SIZE < y) && (y < location.y + HALF_SIZE)) {
            return true;
        }
        return false;
    }

    @Override
    public void startInteraction(int x, int y, MouseEvent e, DrawView v) {
        startX = x;
        startY = y;
    }

    @Override
    public void dragInteraction(int x, int y, MouseEvent e, DrawView v) {
       state.dragInteraction(x,y,e,v);
    }

    @Override
    public void stopInteraction(int x, int y, MouseEvent e, DrawView v) {
        //TODO

    }

}

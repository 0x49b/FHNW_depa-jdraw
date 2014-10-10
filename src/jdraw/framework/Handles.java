package jdraw.framework;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Created by benjamin on 10.10.2014.
 */
public class Handles implements FigureHandle {

    private Figure owner;
    private Point location;

    public Handles(Figure owner, Point location) {
        this.owner = owner;
        this.location = location;
    }

    @Override
    public Figure getOwner() {
        return owner;
    }

    @Override
    public Point getLocation() {
        return location;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(location.x - 5, location.y - 5, 10, 10);
        g.setColor(Color.BLACK);
        g.drawRect(location.x - 5, location.y - 5, 10, 10);
    }

    @Override
    public Cursor getCursor() {
        //TODO
        return null;
    }

    @Override
    public boolean contains(int x, int y) {
        //TODO
        return false;
    }

    @Override
    public void startInteraction(int x, int y, MouseEvent e, DrawView v) {
        //TODO

    }

    @Override
    public void dragInteraction(int x, int y, MouseEvent e, DrawView v) {
        //TODO

    }

    @Override
    public void stopInteraction(int x, int y, MouseEvent e, DrawView v) {
        //TODO

    }
}

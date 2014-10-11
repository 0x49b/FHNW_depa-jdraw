/*
 * Copyright (c) 2000-2014 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */

package jdraw.figures;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import jdraw.framework.*;

/**
 * Represents rectangles in JDraw.
 *
 * @author Christoph Denzler
 */
public class Rect extends Figures implements Figure {
    /**
     *
     */
    private static final long serialVersionUID = 7822544492528250356L;
    /**
     * Use the java.awt.Rectangle in order to save/reuse code.
     */
    private java.awt.Rectangle rectangle;
    /**
     * List for the Handles.
     */
    private List<FigureHandle> handleList = new ArrayList<>();

    /**
     * Create a new rectangle of the given dimension.
     *
     * @param x the x-coordinate of the upper left corner of the rectangle
     * @param y the y-coordinate of the upper left corner of the rectangle
     * @param w the rectangle's width
     * @param h the rectangle's height
     */
    public Rect(int x, int y, int w, int h) {
        super();
        rectangle = new java.awt.Rectangle(x, y, w, h);
        generateHandles();
    }

    private void generateHandles() {
        handleList.clear();
        handleList.add(new RectHandle(this, new Point(rectangle.x, rectangle.y)));
        handleList.add(new RectHandle(this, new Point(rectangle.x + rectangle.width, rectangle.y)));
        handleList.add(new RectHandle(this, new Point(rectangle.x, rectangle.y + rectangle.height)));
        handleList.add(new RectHandle(this, new Point(rectangle.x + rectangle.width, rectangle.y + rectangle.height)));
    }

    private void setHandleLocation() {
        ((Handles) handleList.get(0)).setLocation(new Point(rectangle.x, rectangle.y));
        ((Handles) handleList.get(1)).setLocation(new Point(rectangle.x + rectangle.width, rectangle.y));
        ((Handles) handleList.get(2)).setLocation(new Point(rectangle.x, rectangle.y + rectangle.height));
        ((Handles) handleList.get(3)).setLocation(new Point(rectangle.x + rectangle.width, rectangle.y + rectangle.height));
    }

    /**
     * Draw the rectangle to the given graphics context.
     *
     * @param g the graphics context to use for drawing.
     */
    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
        g.setColor(Color.BLACK);
        g.drawRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }

    @Override
    public void setBounds(Point origin, Point corner) {
        rectangle.setFrameFromDiagonal(origin, corner);
        setHandleLocation();
        for (FigureListener l : listeners) {
            l.figureChanged(new FigureEvent(this));
        }
    }

    @Override
    public void move(int dx, int dy) {
        if (dx != 0 || dy != 0) {
            rectangle.setLocation(rectangle.x + dx, rectangle.y + dy);
            setHandleLocation();
            for (FigureListener l : listeners) {
                l.figureChanged(new FigureEvent(this));
            }
        }
    }

    @Override
    public boolean contains(int x, int y) {
        return rectangle.contains(x, y);
    }

    @Override
    public Rectangle getBounds() {
        return rectangle.getBounds();
    }

    /**
     * Returns a list of 8 handles for this Rectangle.
     *
     * @return all handles that are attached to the targeted figure.
     * @see jdraw.framework.Figure#getHandles()
     */
    public List<FigureHandle> getHandles() {
        return handleList;
    }

    class RectHandle extends Handles {

        private final HandleState NW = new NW();
        private final HandleState NE = new NE();
        private final HandleState SW = new SW();
        private final HandleState SE = new SE();

        public RectHandle(Figure owner, Point location) {
            super(owner, location);
            setStates();
        }

        public void setLocation(Point location) {
            super.setLocation(location);
            setStates();
        }

        private void setStates() {
            Rectangle bound = owner.getBounds();
            Point nw = new Point(bound.getLocation());
            Point sw = new Point(bound.getLocation().x, (int) (bound.getLocation().y + bound.getHeight()));
            Point ne = new Point((int) (bound.getLocation().x + bound.getWidth()), bound.getLocation().y);
            Point se = new Point((int) (bound.getLocation().x + bound.getWidth()), (int) (bound.getLocation().y + bound.getHeight()));

            if (location.equals(nw)) {
                state = NW;
            } else if(location.equals(sw)){
                state = SW;
            } else if(location.equals(ne)) {
                state = NE;
            } else if(location.equals(se)) {
                state = SE;
            }
        }

        @Override
        public void startInteraction(int x, int y, MouseEvent e, DrawView v) {
            super.startInteraction(x,y,e,v);
            state.startInteraction(x,y,e,v);
        }

        class NW implements HandleState {

            private int StartCornerX;
            private int StartCornerY;

            @Override
            public Cursor getCursor() {
                return Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR);
            }

            @Override
            public void startInteraction(int x, int y, MouseEvent e, DrawView v) {
                Rectangle o = owner.getBounds();
                StartCornerX = o.getLocation().x + (int) o.getWidth();
                StartCornerY = o.getLocation().y + (int) o.getHeight();
            }

            @Override
            public void dragInteraction(int x, int y, MouseEvent e, DrawView v) {
                Rectangle bounds = owner.getBounds();
                Point corner = new Point((int) (startX + bounds.getWidth() - x), (int) (startY + bounds.getHeight() - y));
                owner.setBounds(new Point(x, y), new Point(StartCornerX,StartCornerY));
            }
        }

        class NE implements HandleState {

            private int StartCornerY;

            @Override
            public Cursor getCursor() {
                return Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR);
            }

            @Override
            public void startInteraction(int x, int y, MouseEvent e, DrawView v) {
                Rectangle o = owner.getBounds();
                StartCornerY = o.getLocation().y + (int) o.getHeight();
            }

            @Override
            public void dragInteraction(int x, int y, MouseEvent e, DrawView v) {
                Rectangle bounds = owner.getBounds();
                Point corner = new Point((int) (startX + bounds.getWidth() - x), (int) (startY + bounds.getHeight() - y));
                owner.setBounds(new Point(owner.getBounds().getLocation().x, y), new Point(x,StartCornerY));
            }
        }

        class SW implements HandleState {

            private int StartCornerX;
            private int StartCornerY;

            @Override
            public Cursor getCursor() {
                return Cursor.getPredefinedCursor(Cursor.SW_RESIZE_CURSOR);
            }

            @Override
            public void startInteraction(int x, int y, MouseEvent e, DrawView v) {
                Rectangle o = owner.getBounds();
                StartCornerX = o.getLocation().x + (int) o.getWidth();
                StartCornerY = o.getLocation().y + (int) o.getHeight();
            }

            @Override
            public void dragInteraction(int x, int y, MouseEvent e, DrawView v) {
                Rectangle bounds = owner.getBounds();
                Point corner = new Point((int) (startX + bounds.getWidth() - x), (int) (startY + bounds.getHeight() - y));
                owner.setBounds(new Point(x, owner.getBounds().getLocation().y), new Point(StartCornerX,y));
            }
        }

        class SE implements HandleState {

            @Override
            public Cursor getCursor() {
                return Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR);
            }

            @Override
            public void dragInteraction(int x, int y, MouseEvent e, DrawView v) {
                owner.setBounds(new Point(owner.getBounds().getLocation()), new Point(x,y));

            }
        }


    }



}

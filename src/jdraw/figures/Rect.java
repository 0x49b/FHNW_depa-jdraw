/*
 * Copyright (c) 2000-2014 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */

package jdraw.figures;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import jdraw.figures.Handles.*;
import jdraw.framework.*;

/**
 * Represents rectangles in JDraw.
 *
 * @author Christoph Denzler
 */
public class Rect extends AbstractFigure implements Figure, Cloneable {
    /**
     *
     */
    private static final long serialVersionUID = 7822544492528250356L;
    /**
     * Use the java.awt.Rectangle in order to save/reuse code.
     */
    private java.awt.Rectangle rectangle;

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

    public Rect (Rect r) {
        super(r);
        rectangle = new Rectangle(r.rectangle.x, r.rectangle.y, r.rectangle.width, r.rectangle.height);
        generateHandles();
    }

    private void generateHandles() {
        handleList.clear();
        handleList.add(new Handle(new NWHandleState(this)));
        handleList.add(new Handle(new NEHandleState(this)));
        handleList.add(new Handle(new SWHandleState(this)));
        handleList.add(new Handle(new SEHandleState(this)));
        handleList.add(new Handle(new NHandleState(this)));
        handleList.add(new Handle(new SHandleState(this)));
        handleList.add(new Handle(new WHandleState(this)));
        handleList.add(new Handle(new EHandleState(this)));
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
        notifyListeners(new FigureEvent(this));
    }

    @Override
    public void move(int dx, int dy) {
        if (dx != 0 || dy != 0) {
            rectangle.setLocation(rectangle.x + dx, rectangle.y + dy);
            notifyListeners(new FigureEvent(this));
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

    @Override
    public Rect clone() {
        return new Rect(this);
    }
}

package jdraw.figures;

import jdraw.framework.Figure;
import jdraw.framework.FigureEvent;
import jdraw.framework.FigureHandle;
import jdraw.framework.FigureListener;

import java.awt.*;
import java.util.*;

/**
 * Created by benjamin on 25.11.2014.
 */
public class BorderDecorator implements Figure{

    private Figure inner;
    private final static int SIZE = 5;

    public BorderDecorator(Figure inner) {
        this.inner = inner;
    }

    public Figure getInner() {
        return inner;
    }

    @Override
    public Figure clone() {
        return inner.clone();
    }

    public void draw(Graphics g) {
        inner.draw(g);
        g.setColor(Color.GRAY);
        Rectangle r = inner.getBounds();
        g.drawRect(r.x-SIZE, r.y - SIZE, r.width + 2*SIZE, r.height + 2*SIZE);
    }

    public void move(int dx, int dy) {
        inner.move(dx, dy);
    }

    public boolean contains(int x, int y) {
        return inner.contains(x, y);
    }

    public void setBounds(Point origin, Point corner) {
        inner.setBounds(origin, corner);
    }

    public Rectangle getBounds() {
        return inner.getBounds();
    }

    public java.util.List<FigureHandle> getHandles() {
        return inner.getHandles();
    }

    public void addFigureListener(FigureListener listener) {
        inner.addFigureListener(listener);
    }

    public void removeFigureListener(FigureListener listener) {
        inner.removeFigureListener(listener);
    }

    public void swapVertical() {
        inner.swapVertical();
    }

    public void swapHorizontal() {
        inner.swapHorizontal();
    }

    public void notifyListeners(FigureEvent event) {
        inner.notifyListeners(event);
    }
}

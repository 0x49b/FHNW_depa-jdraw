package jdraw.figures;

import jdraw.framework.Figure;
import jdraw.framework.FigureEvent;
import jdraw.framework.FigureHandle;
import jdraw.framework.FigureListener;

import java.awt.*;

/**
 * Created by benjamin on 25.11.2014.
 */
public class LogDecorator implements Figure{

    private Figure inner;

    public LogDecorator(Figure inner) {
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
    }

    public void move(int dx, int dy) {
        System.out.println("Move Figure, dx: " + dx + " - dy: " + dy);
        inner.move(dx, dy);
    }

    public boolean contains(int x, int y) {
        System.out.println("Figure Contains");
        return inner.contains(x, y);
    }

    public void setBounds(Point origin, Point corner) {
        System.out.println("Set Bounds called");
        inner.setBounds(origin, corner);
    }

    public Rectangle getBounds() {
        System.out.println("Get Bounds called");
        return inner.getBounds();
    }

    public java.util.List<FigureHandle> getHandles() {
        System.out.println("Get Handles called");
        return inner.getHandles();
    }

    public void addFigureListener(FigureListener listener) {
        System.out.println("addFigureListener called");
        inner.addFigureListener(listener);
    }

    public void removeFigureListener(FigureListener listener) {
        System.out.println("removeFigureListener called");
        inner.removeFigureListener(listener);
    }

    public void swapVertical() {
        System.out.println("swapVertical called");
        inner.swapVertical();
    }

    public void swapHorizontal() {
        System.out.println("swapHorizontal called");
        inner.swapHorizontal();
    }

    public void notifyListeners(FigureEvent event) {
        System.out.println("notifyListeners called");
        inner.notifyListeners(event);
    }
}

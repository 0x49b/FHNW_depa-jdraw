package jdraw.figures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.util.List;

import jdraw.framework.Figure;
import jdraw.framework.FigureEvent;
import jdraw.framework.FigureHandle;
import jdraw.framework.FigureListener;

public class Ellipse extends Figures implements Figure {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6233002186439043810L;
	private java.awt.geom.Ellipse2D.Double ellipse;
	/**
	 * Create a new ellipse of the given dimension.
	 * 
	 * @param x
	 *            the X coordinate of the upper-left corner of the framing
	 *            rectangle
	 * @param y
	 *            the Y coordinate of the upper-left corner of the framing
	 *            rectangle
	 * @param w
	 *            the width of the framing rectangle
	 * @param h
	 *            the height of the framing rectangle
	 */
	public Ellipse(int x, int y, int w, int h) {
		super();
		ellipse = new Ellipse2D.Double((double) x, (double) y, (double) w,
				(double) h);
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillOval((int) ellipse.x, (int) ellipse.y, (int) ellipse.width,
				(int) ellipse.height);
		g.setColor(Color.BLACK);
		g.drawOval((int) ellipse.x, (int) ellipse.y, (int) ellipse.width,
				(int) ellipse.height);

	}

	@Override
	public void move(int dx, int dy) {
		if (dx != 0 || dy != 0) {
			ellipse.x += dx;
			ellipse.y += dy;
			for (FigureListener l : listeners) {
				l.figureChanged(new FigureEvent(this));
			}
		}

	}

	@Override
	public boolean contains(int x, int y) {
		int w = (int) ellipse.width;
        int h = (int) ellipse.height;
        if ((w | h) < 0) {
            // At least one of the dimensions is negative...
            return false;
        }
        // Note: if either dimension is zero, tests below must return false...
        int x1 = (int) ellipse.x;
        int y1 = (int) ellipse.y;
        if (x < x1 || y < y1) {
            return false;
        }
        w += x1;
        h += y1;
        //    overflow || intersect
        return ((w < x1 || w > x) &&
                (h < y1 || h > y));
	}

	@Override
	public void setBounds(Point origin, Point corner) {
		ellipse.setFrameFromDiagonal(origin, corner);
		for (FigureListener l : listeners) {
			l.figureChanged(new FigureEvent(this));
		}

	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) ellipse.x, (int) ellipse.y, (int) ellipse.width, (int) ellipse.height);
	}

	@Override
	public List<FigureHandle> getHandles() {
		// TODO Auto-generated method stub
		return null;
	}

}

package jdraw.figures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.util.List;

import jdraw.figures.Handles.*;
import jdraw.framework.Figure;
import jdraw.framework.FigureEvent;
import jdraw.framework.FigureHandle;
import jdraw.framework.FigureListener;

public class Ellipse extends AbstractFigure implements Figure {

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
            notifyListeners(new FigureEvent(this));
		}

	}

	@Override
	public boolean contains(int x, int y) {
        return ellipse.contains(x,y);
	}

	@Override
	public void setBounds(Point origin, Point corner) {
		ellipse.setFrameFromDiagonal(origin, corner);
        notifyListeners(new FigureEvent(this));
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) ellipse.x, (int) ellipse.y, (int) ellipse.width, (int) ellipse.height);
	}

}

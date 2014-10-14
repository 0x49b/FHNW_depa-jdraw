package jdraw.figures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.List;

import jdraw.framework.FigureEvent;
import jdraw.framework.FigureHandle;
import jdraw.framework.FigureListener;

public class Line extends AbstractFigure {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1012196420965362270L;
	private java.awt.geom.Line2D.Double line;

    private static final int INTERSIZE = 4;
	
	public Line (int x1, int y1, int x2, int y2) {
		line = new java.awt.geom.Line2D.Double(x1, y1, x2, y2);
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawLine((int) line.x1, (int) line.y1, (int) line.x2, (int) line.y2);
	}

	@Override
	public void move(int dx, int dy) {
		if (dx != 0 || dy != 0) {
			line.x1 += dx;
			line.y1 += dy;
			line.x2 += dx;
			line.y2 += dy;
            notifyListeners(new FigureEvent(this));
		}

	}

	@Override
	public boolean contains(int x, int y) {
		return line.intersects(x - INTERSIZE/2, y - INTERSIZE/2, INTERSIZE, INTERSIZE);
	}

	@Override
	public void setBounds(Point origin, Point corner) {
		line.setLine(origin, corner);
        notifyListeners(new FigureEvent(this));
	}

	@Override
	public Rectangle getBounds() {
		return line.getBounds();
	}

	@Override
	public List<FigureHandle> getHandles() {
		// TODO Auto-generated method stub
		return null;
	}

}

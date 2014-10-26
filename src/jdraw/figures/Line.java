package jdraw.figures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import jdraw.figures.Handles.CornerLineHandleState;
import jdraw.figures.Handles.Handle;
import jdraw.figures.Handles.OrginLineHandleState;
import jdraw.framework.FigureEvent;

public class Line extends AbstractFigure {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1012196420965362270L;
	private java.awt.geom.Line2D.Double line;

    private static final int INTER_SIZE = 4;

    private Point start;
    private Point end;
	
	public Line (int x1, int y1, int x2, int y2) {
		line = new java.awt.geom.Line2D.Double(x1, y1, x2, y2);
        updatePoints();
        handleList.clear();
        handleList.add(new Handle(new OrginLineHandleState(this)));
        handleList.add(new Handle(new CornerLineHandleState(this)));
	}

    private void updatePoints() {
        start = new Point((int) line.x1,(int) line.y1);
        end = new Point((int) line.x2,(int) line.y2);
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
            updatePoints();
            notifyListeners(new FigureEvent(this));
		}

	}

	@Override
	public boolean contains(int x, int y) {
		return line.intersects(x - INTER_SIZE /2, y - INTER_SIZE /2, INTER_SIZE, INTER_SIZE);
	}

	@Override
	public void setBounds(Point origin, Point corner) {
		line.setLine(origin, corner);
        updatePoints();
        notifyListeners(new FigureEvent(this));
	}

	@Override
	public Rectangle getBounds() {
		return line.getBounds();
	}

    public Point getStart() {
        return start;
    }

    public void setStart(Point start) {
        this.start = start;
        line.setLine(start, end);
        notifyListeners(new FigureEvent(this));
    }

    public Point getEnd() {
        return end;
    }

    public void setEnd(Point end) {
        this.end = end;
        line.setLine(start, end);
        notifyListeners(new FigureEvent(this));
    }
}

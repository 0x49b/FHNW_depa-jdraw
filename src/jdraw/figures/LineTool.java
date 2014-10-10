package jdraw.figures;

import java.awt.Point;
import java.awt.event.MouseEvent;

import jdraw.framework.DrawContext;

public class LineTool extends Tools {
	
	private Line newLine = null;

	public LineTool(DrawContext context) {
		super(context);
	}

	@Override
	public void mouseDown(int x, int y, MouseEvent e) {
		if (newLine != null) {
			throw new IllegalStateException();
		}
		anchor = new Point(x, y);
		newLine = new Line(x, y, x, y);
		view.getModel().addFigure(newLine);
	}

	@Override
	public void mouseDrag(int x, int y, MouseEvent e) {
		newLine.setBounds(anchor, new Point(x, y));
		java.awt.Rectangle r = newLine.getBounds();
		this.context.showStatusText("w: " + r.width + ", h: " + r.height);
	}
	
	@Override
	public void mouseUp(int x, int y, MouseEvent e) {
		super.mouseUp(x, y, e);
		newLine = null;
	};

	@Override
	public String getName() {
		return "Line";
	}

}

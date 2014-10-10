package jdraw.figures;

import java.awt.Point;
import java.awt.event.MouseEvent;

import jdraw.framework.DrawContext;
import jdraw.framework.DrawTool;

public class EllipseTool extends Tools implements DrawTool {
	
	/**
	 * Temporary variable. During rectangle creation (during a
	 * mouse down - mouse drag - mouse up cycle) this variable refers
	 * to the new rectangle that is inserted.
	 */
	private Ellipse newEllipse = null;

	public EllipseTool(DrawContext context) {
		super(context);
	}

	@Override
	public void mouseDown(int x, int y, MouseEvent e) {
		if (newEllipse != null) {
			throw new IllegalStateException();
		}
		anchor = new Point(x, y);
		newEllipse = new Ellipse(x, y, 0, 0);
		view.getModel().addFigure(newEllipse);
	}

	@Override
	public void mouseDrag(int x, int y, MouseEvent e) {
		newEllipse.setBounds(anchor, new Point(x, y));
		java.awt.Rectangle r = newEllipse.getBounds();
		this.context.showStatusText("w: " + r.width + ", h: " + r.height);
	}
	
	@Override
	public void mouseUp(int x, int y, MouseEvent e) {
		super.mouseUp(x, y, e);
		newEllipse = null;
	};

	@Override
	public String getName() {
		return "Ellipse";
	}

}

package jdraw.figures;

import java.awt.Point;
import java.awt.event.MouseEvent;

import jdraw.framework.DrawContext;

public class LineTool extends AbstractTool {

	public LineTool(DrawContext context) {
		super(context);
        setName("Line");
	}

	@Override
	public void mouseDown(int x, int y, MouseEvent e) {
        super.mouseDown(x,y,e);
		fig = new Line(x, y, x, y);
		getView().getModel().addFigure(fig);
	}
}

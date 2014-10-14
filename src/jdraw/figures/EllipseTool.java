package jdraw.figures;

import java.awt.Point;
import java.awt.event.MouseEvent;

import jdraw.framework.DrawContext;
import jdraw.framework.DrawTool;

public class EllipseTool extends AbstractTool {
	
	public EllipseTool(DrawContext context) {
		super(context);
        setName("Ellipse");
	}

	@Override
	public void mouseDown(int x, int y, MouseEvent e) {
        super.mouseDown(x,y,e);
        fig = new Ellipse(x, y, 0, 0);
        getView().getModel().addFigure(fig);
	}
}

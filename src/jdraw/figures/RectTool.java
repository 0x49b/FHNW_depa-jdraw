/*
 * Copyright (c) 2000-2014 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */

package jdraw.figures;

import java.awt.Point;
import java.awt.event.MouseEvent;

import jdraw.framework.DrawContext;
import jdraw.framework.DrawTool;

/**
 * This tool defines a mode for drawing rectangles.
 *
 * @see jdraw.framework.Figure
 *
 * @author  Christoph Denzler
 * @version 2.1, 27.09.07
 */
public class RectTool extends AbstractTool {

	/**
	 * Create a new rectangle tool for the given context.
	 * @param context a context to use this tool in.
	 */
	public RectTool(DrawContext context) {
		super(context);
        setName("Rectangle");
	}

	public void mouseDown(int x, int y, MouseEvent e) {
        super.mouseDown(x,y,e);
		getView().getModel().addFigure(setFig(new Rect(x, y, 0, 0)));
	}
}

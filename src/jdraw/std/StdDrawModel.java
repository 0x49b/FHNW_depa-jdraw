/*
 * Copyright (c) 2000-2013 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */

package jdraw.std;

import java.util.LinkedList;
import java.util.List;

import jdraw.framework.DrawCommandHandler;
import jdraw.framework.DrawModel;
import jdraw.framework.DrawModelEvent;
import jdraw.framework.DrawModelEvent.Type;
import jdraw.framework.DrawModelListener;
import jdraw.framework.Figure;
import jdraw.framework.FigureListener;

/**
 * Provide a standard behavior for the drawing model. This class initially does
 * not implement the methods in a proper way. It is part of the course
 * assignments to do so.
 * 
 * @author Benjamin Leber
 *
 */
public class StdDrawModel implements DrawModel {

	private LinkedList<Figure> figureList = new LinkedList<>();
	private List<DrawModelListener> listeners = new LinkedList<>();
	private FigureListener fl;

	public StdDrawModel() {
		fl = (figureEvent) -> {
			for (DrawModelListener drawModelListener : listeners) {
				drawModelListener.modelChanged(new DrawModelEvent(this, figureEvent.getFigure(), Type.FIGURE_CHANGED));
			}
		};
	}

	@Override
	public void addFigure(Figure f) {
		if (!figureList.contains(f)) {
			f.addFigureListener(fl);
			figureList.add(f);
			for (DrawModelListener l : listeners) {
				l.modelChanged(new DrawModelEvent(this, f, Type.FIGURE_ADDED));
			}
		}
	}

	@Override
	public Iterable<Figure> getFigures() {
		return figureList;
	}

	@Override
	public void removeFigure(Figure f) {
		if (figureList.remove(f)) {
			f.removeFigureListener(fl);
			for (DrawModelListener l : listeners) {
				l.modelChanged(new DrawModelEvent(this, f, Type.FIGURE_REMOVED));
			}
		}
	}

	@Override
	public void addModelChangeListener(DrawModelListener listener) {
		listeners.add(listener);
	}

	@Override
	public void removeModelChangeListener(DrawModelListener listener) {
		listeners.remove(listener);
	}

	/** The draw command handler. Initialized here with a dummy implementation. */
	// TODO initialize with your implementation of the undo/redo-assignment.
	private DrawCommandHandler handler = new EmptyDrawCommandHandler();

	/**
	 * Retrieve the draw command handler in use.
	 * 
	 * @return the draw command handler.
	 */
	public DrawCommandHandler getDrawCommandHandler() {
		return handler;
	}

	@Override
	public void setFigureIndex(Figure f, int index) {
		if (!figureList.contains(f)) {
			throw new IllegalArgumentException();
		}
		figureList.remove(f);
		figureList.add(index, f);
		for (DrawModelListener l : listeners) {
			l.modelChanged(new DrawModelEvent(this, f, Type.DRAWING_CHANGED));
		}
	}

	@Override
	public void removeAllFigures() {
		for (Figure figure : figureList) {
			figure.removeFigureListener(fl);
		}
		figureList.clear();
		for (DrawModelListener l : listeners) {
			l.modelChanged(new DrawModelEvent(this, null, Type.DRAWING_CLEARED));
		}
	}

}

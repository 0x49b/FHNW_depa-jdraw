package jdraw.figures;

import java.util.ArrayList;

import jdraw.framework.Figure;
import jdraw.framework.FigureListener;

public abstract class Figures implements Figure {

	/**
	 * 
	 */
	private static final long serialVersionUID = 777242339280368108L;
	protected ArrayList<FigureListener> listeners;

	public Figures() {
		super();
		listeners = new ArrayList<>();
	}

	@Override
	public void addFigureListener(FigureListener listener) {
		listeners.add(listener);
	}

	@Override
	public void removeFigureListener(FigureListener listener) {
		listeners.remove(listener);
	}

	@Override
	public Figure clone() {
		return null;
	}

}
package jdraw.figures;

import java.util.ArrayList;
import java.util.List;

import jdraw.figures.Handles.Handle;
import jdraw.framework.*;

public abstract class AbstractFigure implements Figure {

	/**
	 * 
	 */
	private static final long serialVersionUID = 777242339280368108L;
	private List<FigureListener> listeners = new ArrayList<>();
    /**
     * List for the Handles.
     */
    List<FigureHandle> handleList = new ArrayList<>();

	public AbstractFigure() {

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

    public void notifyListeners(FigureEvent event) {
        List<FigureListener> list = new ArrayList<>(listeners);
        for (FigureListener l : list) {
            l.figureChanged(event);
        }
    }

    public void swapHorizontal() {
        Handle NW = (Handle) handleList.get(0);
        Handle NE = (Handle) handleList.get(1);
        Handle SW = (Handle) handleList.get(2);
        Handle SE = (Handle) handleList.get(3);
        Handle W = (Handle) handleList.get(6);
        Handle E = (Handle) handleList.get(7);
        HandleState NWstate = NW.getState();
        HandleState NEstate = NE.getState();
        HandleState SWstate = SW.getState();
        HandleState SEstate = SE.getState();
        HandleState WState = W.getState();
        HandleState EState = E.getState();
        NW.setState(NEstate); NE.setState(NWstate);
        SW.setState(SEstate); SE.setState(SWstate);
        W.setState(EState); E.setState(WState);
    }

    public void swapVertical() {
        Handle NW = (Handle) handleList.get(0);
        Handle NE = (Handle) handleList.get(1);
        Handle SW = (Handle) handleList.get(2);
        Handle SE = (Handle) handleList.get(3);
        Handle N = (Handle) handleList.get(4);
        Handle S = (Handle) handleList.get(5);
        HandleState NWstate = NW.getState();
        HandleState NEstate = NE.getState();
        HandleState SWstate = SW.getState();
        HandleState SEstate = SE.getState();
        HandleState NState = N.getState();
        HandleState SState = S.getState();
        NW.setState(SWstate); NE.setState(SEstate);
        SW.setState(NWstate); SE.setState(NEstate);
        N.setState(SState); S.setState(NState);
    }

}
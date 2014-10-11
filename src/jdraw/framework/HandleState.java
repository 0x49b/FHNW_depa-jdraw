package jdraw.framework;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Created by benjamin on 10.10.2014.
 */
public interface HandleState {

    public Cursor getCursor();

    public void dragInteraction(int x, int y, MouseEvent e, DrawView v);

    default void startInteraction(int x, int y, MouseEvent e, DrawView v) {
        //DO Nothing in default implementation
    }

}

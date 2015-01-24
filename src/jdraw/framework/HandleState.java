package jdraw.framework;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Created by benjamin on 10.10.2014.
 */
public interface HandleState extends java.io.Serializable {

    public Cursor getCursor();

    public Point getAnchor();

    public Figure getOwner();

    public void dragInteraction(int x, int y, MouseEvent e, DrawView v);

    void startInteraction(int x, int y, MouseEvent e, DrawView v);

    void stopInteraction(int x, int y, MouseEvent e, DrawView v);

}

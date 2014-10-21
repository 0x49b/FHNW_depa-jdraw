package jdraw.figures.Handles;

import jdraw.figures.Line;
import jdraw.framework.DrawView;
import jdraw.framework.Figure;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Created by benjamin on 17.10.2014.
 */
public class OrginLineHandleState extends AbstractHandleState {

    public OrginLineHandleState(Figure owner) {
        super(owner);
        if (!(owner instanceof Line)) {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public Cursor getCursor() {
        return Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR);
    }

    @Override
    public Point getAnchor() {
        Line l = (Line) getOwner();
        return l.getStart();
    }

    @Override
    public void dragInteraction(int x, int y, MouseEvent e, DrawView v) {
        Line l = (Line) getOwner();
        l.setStart(new Point(x, y));
        v.getDrawContext().showStatusText("x: " + x + " y: " + y);
    }

    @Override
    public void stopInteraction(int x, int y, MouseEvent e, DrawView v) {
        v.getDrawContext().showStatusText("");
    }
}

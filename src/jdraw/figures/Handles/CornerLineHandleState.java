package jdraw.figures.Handles;

import jdraw.figures.Line;
import jdraw.framework.DrawView;
import jdraw.framework.Figure;
import jdraw.framework.FigureEvent;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Created by benjamin on 17.10.2014.
 */
public class CornerLineHandleState extends AbstractHandleState {
    private static final long serialVersionUID = 3440433698630372619L;

    public CornerLineHandleState(Figure owner) {
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
        return l.getEnd();
    }

    @Override
    public void startInteraction(int x, int y, MouseEvent e, DrawView v) {
        super.startInteraction(x, y, e, v);

    }

    @Override
    public void dragInteraction(int x, int y, MouseEvent e, DrawView v) {
        Line l = (Line) getOwner();
        l.setEnd(new Point(x, y));
        v.getDrawContext().showStatusText("x: " + x + " y: " + y);
    }

    @Override
    public void stopInteraction(int x, int y, MouseEvent e, DrawView v) {
        v.getDrawContext().showStatusText("");
    }
}

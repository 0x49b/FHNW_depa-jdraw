package jdraw.figures.Handles;

import jdraw.framework.DrawView;
import jdraw.framework.Figure;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Created by benjamin on 17.10.2014.
 */
public class CornerLineHandleState extends AbstractHandleState {
    public CornerLineHandleState(Figure owner) {
        super(owner);
    }

    @Override
    public Cursor getCursor() {
        return Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR);
    }

    @Override
    public Point getAnchor() {
        Rectangle r = getOwner().getBounds();
        return new Point(r.x + r.width, r.y + r.height);
    }

    @Override
    public void startInteraction(int x, int y, MouseEvent e, DrawView v) {
        super.startInteraction(x, y, e, v);

    }

    @Override
    public void dragInteraction(int x, int y, MouseEvent e, DrawView v) {
        Rectangle r = getOwner().getBounds();
        getOwner().setBounds(new Point(r.x,r.y), new Point(x,y));
    }

    @Override
    public void stopInteraction(int x, int y, MouseEvent e, DrawView v) {

    }
}

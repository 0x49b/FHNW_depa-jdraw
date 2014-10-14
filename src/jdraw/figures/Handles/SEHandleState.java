package jdraw.figures.Handles;

import jdraw.framework.DrawView;
import jdraw.framework.Figure;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Created by benjamin on 14.10.2014.
 */
public class SEHandleState extends AbstractHandleState {

    public SEHandleState(Figure owner) {
        super(owner);
    }

    @Override
    public Cursor getCursor() {
        return Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR);
    }

    @Override
    public Point getAnchor() {
        Rectangle r = getOwner().getBounds();
        return new Point(r.x + r.width, r.y + r.height);
    }

    @Override
    public void dragInteraction(int x, int y, MouseEvent e, DrawView v) {
        //TODO Make it correct
        Rectangle r = getOwner().getBounds();
        getOwner().setBounds(new Point(x,y),
                new Point(r.x+r.width,r.y+r.height));
        if (x > r.x+r.width) {
            getOwner().swapHorizontal();
        }
        if (y > r.y+r.height) {
            getOwner().swapVertical();
        }
    }

    @Override
    public void startInteraction(int x, int y, MouseEvent e, DrawView v) {

    }

    @Override
    public void stopInteraction(int x, int y, MouseEvent e, DrawView v) {

    }
}

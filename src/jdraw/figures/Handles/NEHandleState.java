package jdraw.figures.Handles;

import jdraw.framework.DrawView;
import jdraw.framework.Figure;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Created by benjamin on 14.10.2014.
 */
public class NEHandleState extends AbstractHandleState {

    public NEHandleState(Figure owner) {
        super(owner);
    }

    @Override
    public Cursor getCursor() {
        return Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR);
    }

    @Override
    public Point getAnchor() {
        Rectangle r = getOwner().getBounds();
        return new Point(r.x + r.width, r.y);
    }

    @Override
    public void dragInteraction(int x, int y, MouseEvent e, DrawView v) {
        //TODO Make it correct
        Rectangle r = getOwner().getBounds();
        getOwner().setBounds(new Point(startX,y),
                new Point(x,r.y+r.height));
        if (x > r.x+r.width) {
            getOwner().swapHorizontal();
        }
        if (y > r.y+r.height) {
            getOwner().swapVertical();
        }
    }

    @Override
    public void stopInteraction(int x, int y, MouseEvent e, DrawView v) {

    }
}

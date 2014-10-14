package jdraw.figures.Handles;

import jdraw.framework.DrawView;
import jdraw.framework.Figure;
import jdraw.framework.HandleState;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Created by benjamin on 14.10.2014.
 */
public abstract class AbstractHandleState implements HandleState {

    private Figure owner;
    int startX;
    int startY;

    public AbstractHandleState(Figure owner) {
        this.owner = owner;
    }

    @Override
    public Figure getOwner() {
        return owner;
    }

    @Override
    public void startInteraction(int x, int y, MouseEvent e, DrawView v) {
        startX = x;
        startY = y;
    }
}

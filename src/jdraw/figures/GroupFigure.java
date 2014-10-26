package jdraw.figures;

import jdraw.framework.*;

import java.awt.*;
import java.util.List;

/**
 * Created by benjamin on 26.10.2014.
 */
public class GroupFigure extends AbstractFigure implements FigureGroup {

    List<Figure> groupFigures;
    Rectangle bounds;
    DrawView view;
    DrawModel model;

    public GroupFigure(DrawView view) {
        this.view = view;
        this.model = view.getModel();
        groupFigures =  view.getSelection();

        bounds = new Rectangle();
        for(Figure f: groupFigures) {
            bounds.add(f.getBounds());
            model.removeFigure(f);
        }
        model.addFigure(this);
    }

    @Override
    public void draw(Graphics g) {
        for(Figure f: groupFigures) {
            f.draw(g);
        }
    }

    @Override
    public void move(int dx, int dy) {
        for(Figure f : groupFigures) {
            f.move(dx, dy);
        }
    }

    @Override
    public boolean contains(int x, int y) {
        return bounds.contains(x,y);
    }

    @Override
    public void setBounds(Point origin, Point corner) {
        //TODO
    }

    @Override
    public Rectangle getBounds() {
        return bounds;
    }


    @Override
    public Iterable<Figure> getFigureParts() {
        return groupFigures;
    }
}

package jdraw.figures;

import jdraw.figures.Handles.*;
import jdraw.framework.*;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by benjamin on 26.10.2014.
 */
public class GroupFigure extends AbstractFigure implements FigureGroup, Cloneable {

    private static final long serialVersionUID = -884754969026873919L;
    List<Figure> groupFigures;
    Rectangle bounds;
    DrawView view;
    DrawModel model;

    public GroupFigure(DrawView view) {
        this(view, new LinkedList<>());
    }

    public GroupFigure(DrawView view, List<Figure> list) {
        this.view = view;
        this.model = view.getModel();
        groupFigures =  list;

        generateBoundingRectangle();
        generateHandles();
    }

    public GroupFigure(GroupFigure gf) {
        this.view = gf.view;
        this.model = view.getModel();
        groupFigures = new LinkedList<>();
        for (Figure f : gf.groupFigures) {
            groupFigures.add(f.clone());
        }
        generateBoundingRectangle();
        generateHandles();
    }

    private void generateBoundingRectangle() {
        if (groupFigures.size() > 0) {
            bounds = groupFigures.get(0).getBounds();
            for (Figure f : groupFigures) {
                bounds.add(f.getBounds());
            }
        }
    }

    private void generateHandles() {
        handleList.clear();
        handleList.add(new Handle(new NWHandleState(this)));
        handleList.add(new Handle(new NEHandleState(this)));
        handleList.add(new Handle(new SWHandleState(this)));
        handleList.add(new Handle(new SEHandleState(this)));
        handleList.add(new Handle(new NHandleState(this)));
        handleList.add(new Handle(new SHandleState(this)));
        handleList.add(new Handle(new WHandleState(this)));
        handleList.add(new Handle(new EHandleState(this)));
    }

    @Override
    public void draw(Graphics g) {
        for(Figure f: groupFigures) {
            f.draw(g);
        }
        g.setColor(Color.LIGHT_GRAY);
        g.drawRect(bounds.x,bounds.y,bounds.width,bounds.height);
    }

    @Override
    public void move(int dx, int dy) {
        for(Figure f : groupFigures) {
            f.move(dx, dy);
        }
        generateBoundingRectangle();
        notifyListeners(new FigureEvent(this));
    }

    @Override
    public boolean contains(int x, int y) {
        return bounds.contains(x,y);
    }

    @Override
    public void setBounds(Point origin, Point corner) {
        //TODO
        /*System.out.println(origin + " - " + corner);
        for(Figure f : groupFigures) {
            Rectangle figureBounds = f.getBounds();
            double width = corner.x - origin.x; //100% Width
            double heigth = corner.y - origin.y; //100% Height

            double relativePosX = (figureBounds.y - origin.y) / heigth;
            int newOriginX = (int) Math.round(origin.x * relativePosX);
            double relativePosY = (figureBounds.y - origin.y) / heigth;
            int newOriginY = (int) Math.round(origin.y * relativePosY);
            System.out.println(newOriginX + " - " + newOriginY);
            f.setBounds(new Point(newOriginX, newOriginY), corner);


            // f.setBounds();

        }


        generateBoundingRectangle();
        notifyListeners(new FigureEvent(this));

        */
    }

    @Override
    public Rectangle getBounds() {
        return bounds;
    }


    @Override
    public Iterable<Figure> getFigureParts() {
        return groupFigures;
    }

    @Override
    public GroupFigure clone() {
        return new GroupFigure(this);
    }

    @Override
    public final <T> T getInstanceOf(Class<T> type) {
        return (T) this;
    }

    @Override
    public boolean isInstanceOf(Class<?> type) {
        return type.isAssignableFrom(this.getClass());
    }

    public void addFigure(Figure f) {
        groupFigures.add(f);
        generateBoundingRectangle();
        generateHandles();
    }
}

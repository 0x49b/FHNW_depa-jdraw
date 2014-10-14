package jdraw.figures;

import java.awt.*;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import jdraw.framework.DrawContext;
import jdraw.framework.DrawTool;
import jdraw.framework.DrawView;

public abstract class AbstractTool implements DrawTool{

	/** 
	 * the image resource path. 
	 */
	private static final String IMAGES = "/images/";
	/**
	 * The context we use for drawing.
	 */
	private DrawContext context;
	/**
	 * The context's view. This variable can be used as a shortcut, i.e.
	 * instead of calling context.getView().
	 */
    private DrawView view;
	
	/**
	 * Temporary variable.
	 * During rectangle creation this variable refers to the point the
	 * mouse was first pressed.
	 */
	private Point anchor = null;

    /**
     * The name of this Figure, used for selecting the icon and displaying in statusbar.
     */
    private String name = null;

    AbstractFigure fig = null;

	public AbstractTool(DrawContext context) {
		super();
		this.context = context;
		this.view = context.getView();
	}

	/**
	 * Deactivates the current mode by resetting the cursor
	 * and clearing the status bar.
	 * @see jdraw.framework.DrawTool#deactivate()
	 */
	public void deactivate() {
		this.context.showStatusText("");
	}

	/**
	 * Activates the Rectangle Mode. There will be a
	 * specific menu added to the menu bar that provides settings for
	 * Rectangle attributes
	 */
	public void activate() {
		this.context.showStatusText(this.getName() + " Mode");
	}

    /**
     * Initializes a new Rectangle object by setting an anchor
     * point where the mouse was pressed. A new Rectangle is then
     * added to the model.
     * @param x x-coordinate of mouse
     * @param y y-coordinate of mouse
     * @param e event containing additional information about which keys were pressed.
     *
     * @see jdraw.framework.DrawTool#mouseDown(int, int, MouseEvent)
     */
    public void mouseDown(int x, int y, MouseEvent e) {
        if (fig != null) {
            throw new IllegalStateException();
        }
        anchor = new Point(x,y);
    }

    /**
     * During a mouse drag, the Rectangle will be resized according to the mouse
     * position. The status bar shows the current size.
     *
     * @param x   x-coordinate of mouse
     * @param y   y-coordinate of mouse
     * @param e   event containing additional information about which keys were
     *            pressed.
     *
     * @see jdraw.framework.DrawTool#mouseDrag(int, int, MouseEvent)
     */
    public void mouseDrag(int x, int y, MouseEvent e) {
        fig.setBounds(anchor, new Point(x, y));
        java.awt.Rectangle r = fig.getBounds();
        getContext().showStatusText("w: " + r.width + ", h: " + r.height);
    }

	/**
	 * When the user releases the mouse, the Rectangle object is updated
	 * according to the color and fill status settings.
	 * 
	 * @param x   x-coordinate of mouse
	 * @param y   y-coordinate of mouse
	 * @param e   event containing additional information about which keys were
	 *            pressed.
	 * 
	 * @see jdraw.framework.DrawTool#mouseUp(int, int, MouseEvent)
	 */
	public void mouseUp(int x, int y, MouseEvent e) {
		anchor = null;
        if (fig.getBounds().width == 0 && fig.getBounds().height == 0) {
            view.getModel().removeFigure(fig);
        }
        fig = null;
		this.context.showStatusText(this.getName() + " Mode");
	}

	@Override
	public Cursor getCursor() {
		return Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR);
	}

	@Override
	public Icon getIcon() {
		return new ImageIcon(getClass().getResource(IMAGES + this.getName().toLowerCase() + ".png"));
	}

    void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public DrawView getView() {
        return view;
    }

    public DrawContext getContext() {
        return context;
    }
}
package jdraw.figures;

import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import javax.swing.*;

import jdraw.lib.FileClassLoader;
import jdraw.framework.DrawContext;

/**
 * Class for the magic Joker Tool.
 *
 * @author Chregi Glatthard, Benjamin Leber
 */
public class JokerTool extends AbstractTool {

    AbstractTool toDecorate;

    public JokerTool(DrawContext context) {
        super(context);
    }

    private void setToolFromFile() {
        if (toDecorate == null) {
            URL main = JokerTool.class.getResource("JokerTool.class");
            if (!"file".equalsIgnoreCase(main.getProtocol()))
                throw new IllegalStateException("Main class is not stored in a file.");
            File path = new File(main.getPath());
            JFileChooser ch = new JFileChooser(path);
            int returnVal = ch.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File c = ch.getSelectedFile();
                try {
                    FileClassLoader loader = new FileClassLoader(ClassLoader.getSystemClassLoader());
                    Class clazz = loader.createClass(c);
                    String className = clazz.getCanonicalName();
                    int levels = className.replaceAll("[^.]*", "").length();
                    File root = c.getParentFile();
                    for (int i = 0; i < levels; i++) {
                        root = root.getParentFile();
                    }
                    loader.addURL(root.toURI().toURL());
                    Constructor constructor = clazz.getConstructor(DrawContext.class);
                    toDecorate = (AbstractTool) constructor.newInstance(this.getContext());
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                this.getContext().updateTool(this);
            }
        }
    }

    @Override
    public void mouseDown(int x, int y, MouseEvent e) {
        if (toDecorate != null)
        toDecorate.mouseDown(x, y, e);
    }

    @Override
    public void mouseDrag(int x, int y, MouseEvent e) {
        if (toDecorate != null)
        toDecorate.mouseDrag(x, y, e);
    }

    @Override
    public void mouseUp(int x, int y, MouseEvent e) {
        if (toDecorate != null)
        toDecorate.mouseUp(x, y, e);
    }


    @Override
    public Cursor getCursor() {
        if (toDecorate != null) {
            return toDecorate.getCursor();
        }
        return Cursor.getDefaultCursor();
    }

    @Override
    public Icon getIcon() {
        if (toDecorate != null) {
            return toDecorate.getIcon();
        }
        return new ImageIcon(getClass().getResource(IMAGES + "joker.png"));
    }

    @Override
    public String getName() {
        if (toDecorate != null) {
            return toDecorate.getName();
        }
        return "JokerTool";
    }

    @Override
    public void activate() {
        setToolFromFile();
        this.getContext().showStatusText(this.getName() + " Mode");
    }

}

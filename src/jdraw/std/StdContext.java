/*
 * Copyright (c) 2000-2013 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved.
 */
package jdraw.std;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import jdraw.figures.*;
import jdraw.figures.Decorators.BorderDecorator;
import jdraw.figures.Decorators.LogDecorator;
import jdraw.framework.*;
import jdraw.grids.Grid;
import jdraw.grids.SnapGrid;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

/**
 * Standard implementation of interface DrawContext.
 *
 * @author Dominik Gruntz & Christoph Denzler
 * @version 2.6, 24.09.09
 * @see DrawView
 */
public class StdContext extends AbstractContext {

    /**
     *
     */
    private static final long serialVersionUID = -5572498940223321980L;

    private final Grid grid10C = new Grid(10);
    private final Grid grid20C = new Grid(20);
    private final SnapGrid snapGridC = new SnapGrid(this);
    private final LinkedList<Figure> figuresToPaste = new LinkedList<>();

    /**
     * Constructs a standard context with a default set of drawing tools.
     *
     * @param view the view that is displaying the actual drawing.
     */
    public StdContext(DrawView view) {
        super(view, null);
    }

    /**
     * Constructs a standard context. The drawing tools available can be parameterized using <code>toolFactories</code>.
     *
     * @param view          the view that is displaying the actual drawing.
     * @param toolFactories a list of DrawToolFactories that are available to the user
     */
    public StdContext(DrawView view, List<DrawToolFactory> toolFactories) {
        super(view, toolFactories);
    }

    /**
     * Creates and initializes the "Edit" menu.
     *
     * @return the new "Edit" menu.
     */
    @Override
    protected JMenu createEditMenu() {
        JMenu editMenu = new JMenu("Edit");
        final JMenuItem undo = new JMenuItem("Undo");
        undo.setAccelerator(KeyStroke.getKeyStroke("control Z"));
        editMenu.add(undo);
        undo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                getModel().getDrawCommandHandler().undo();
            }
        });

        final JMenuItem redo = new JMenuItem("Redo");
        redo.setAccelerator(KeyStroke.getKeyStroke("control Y"));
        editMenu.add(redo);
        redo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                getModel().getDrawCommandHandler().redo();
            }
        });
        editMenu.addSeparator();

        JMenuItem sa = new JMenuItem("SelectAll");
        sa.setAccelerator(KeyStroke.getKeyStroke("control A"));
        editMenu.add(sa);
        sa.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                for (Figure f : getModel().getFigures()) {
                    getView().addToSelection(f);
                }
                getView().repaint();
            }
        });

        editMenu.addSeparator();
        JMenuItem cut = new JMenuItem("Cut");
        cut.setAccelerator(KeyStroke.getKeyStroke('X', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        cut.addActionListener(actionEvent -> {
            figuresToPaste.clear();
            for (Figure f : getView().getSelection()) {
                getModel().removeFigure(f);
                figuresToPaste.add(f);
            }
            showStatusText("Ausgeschnitten");
        });
        editMenu.add(cut);
        JMenuItem copy = new JMenuItem("Copy");
        copy.setAccelerator(KeyStroke.getKeyStroke('C', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        copy.addActionListener(actionEvent -> {
            figuresToPaste.clear();
            for (Figure f : getView().getSelection()) {
                figuresToPaste.add(f.clone());
            }
            showStatusText("Kopiert");
        });
        editMenu.add(copy);
        JMenuItem paste = new JMenuItem("Paste");
        paste.setAccelerator(KeyStroke.getKeyStroke('V', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        paste.addActionListener(actionEvent -> {
            if (figuresToPaste.isEmpty()) {
                showStatusText("Zuerst kopieren oder ausschneiden!");
            } else {
                Figure f1;
                for (Figure f : figuresToPaste) {
                    f.move(5, 5);
                    getModel().addFigure(f.clone());
                }
                showStatusText("Eingefügt");
            }
        });
        editMenu.add(paste);

        editMenu.addSeparator();
        JMenuItem group = new JMenuItem("Group");
        group.setEnabled(true);
        group.addActionListener(actionEvent -> {
            List<Figure> list = getView().getSelection();
            GroupFigure groupFigure = new GroupFigure(getView(), list);
            getModel().addFigure(groupFigure);
            getView().addToSelection(groupFigure);
            for (Figure f : list) {
                getModel().removeFigure(f);
            }
        });
        editMenu.add(group);

        JMenuItem ungroup = new JMenuItem("Ungroup");
        ungroup.setEnabled(true);
        ungroup.addActionListener(actionEvent -> {
            List<Figure> list = getView().getSelection();
            Iterable<Figure> list2;
            for (Figure f : list) {
                if (f.getInstanceOf(GroupFigure.class) != null) {

                    GroupFigure g = (GroupFigure) f.getInstanceOf(GroupFigure.class);
                    DrawModel model = getView().getModel();
                    list2 = g.getFigureParts();
                    for (Figure f1 : list2) {
                        model.addFigure(f1);
                    }
                    model.removeFigure(f);
                }
            }
            getView().repaint();
        });
        editMenu.add(ungroup);

        editMenu.addSeparator();

        JMenu orderMenu = new JMenu("Order...");
        JMenuItem frontItem = new JMenuItem("Bring To Front");
        frontItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                bringToFront(getView().getModel(), getView().getSelection());
                getView().repaint();
            }
        });
        orderMenu.add(frontItem);
        JMenuItem backItem = new JMenuItem("Send To Back");
        backItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendToBack(getView().getModel(), getView().getSelection());
                getView().repaint();
            }
        });
        orderMenu.add(backItem);
        editMenu.add(orderMenu);

        JMenu grid = new JMenu("Grid...");
        JRadioButtonMenuItem noGrid = new JRadioButtonMenuItem("Kein Grid");
        noGrid.addActionListener(actionEvent -> {
            getView().setConstrainer(null);
        });
        grid.add(noGrid);
        noGrid.setSelected(true);
        JRadioButtonMenuItem grid10 = new JRadioButtonMenuItem("Grid 10");
        grid10.addActionListener(actionEvent -> {
            getView().setConstrainer(grid10C);
        });
        grid.add(grid10);
        JRadioButtonMenuItem grid20 = new JRadioButtonMenuItem("Grid 20");
        grid20.addActionListener(actionEvent -> {
            getView().setConstrainer(grid20C);
        });
        grid.add(grid20);
        JRadioButtonMenuItem snapGrid = new JRadioButtonMenuItem("Snap Grid");
        snapGrid.addActionListener(actionEvent -> {
            getView().setConstrainer(snapGridC);
        });
        grid.add(snapGrid);

        ButtonGroup g = new ButtonGroup();
        g.add(noGrid);
        g.add(grid10);
        g.add(grid20);
        g.add(snapGrid);
        editMenu.add(grid);

        JMenu dec = new JMenu("Decorator...");
        JMenuItem borderDec = new JMenuItem("BorderDecorator");
        borderDec.addActionListener(a -> {
            List<Figure> flist = getView().getSelection();
            for (Figure f : flist) {
                if (f instanceof BorderDecorator) {
                    getModel().removeFigure(f);
                    Figure f2 = ((BorderDecorator) f).getInner();
                    getModel().addFigure(f2);
                    getView().addToSelection(f2);
                } else {
                    getModel().removeFigure(f);
                    Figure f2 = new BorderDecorator(f);
                    getModel().addFigure(f2);
                    getView().addToSelection(f2);
                }
            }
        });
        dec.add(borderDec);
        JMenuItem logDec = new JMenuItem("LogDecorator");
        logDec.addActionListener(a -> {
            List<Figure> flist = getView().getSelection();
            for (Figure f : flist) {
                if (f instanceof LogDecorator) {
                    getModel().removeFigure(f);
                    Figure f2 = ((LogDecorator) f).getInner();
                    getModel().addFigure(f2);
                    getView().addToSelection(f2);
                } else {
                    getModel().removeFigure(f);
                    Figure f2 = new LogDecorator(f);
                    getModel().addFigure(f2);
                    getView().addToSelection(f2);
                }
            }
        });
        dec.add(logDec);
        editMenu.add(dec);

        return editMenu;
    }

    /**
     * Creates and initializes items in the file menu.
     *
     * @return the new "File" menu.
     */
    @Override
    protected JMenu createFileMenu() {
        JMenu fileMenu = new JMenu("File");
        JMenuItem open = new JMenuItem("Open");
        fileMenu.add(open);
        open.setAccelerator(KeyStroke.getKeyStroke("control O"));
        open.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                doOpen();
            }
        });

        JMenuItem save = new JMenuItem("Save");
        save.setAccelerator(KeyStroke.getKeyStroke("control S"));
        fileMenu.add(save);
        save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                doSave();
            }
        });

        JMenuItem exit = new JMenuItem("Exit");
        fileMenu.add(exit);
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        return fileMenu;
    }

    @Override
    protected void doRegisterDrawTools() {
        // TODO Add new figure tools here
        DrawTool rectangleTool = new RectTool(this);
        addTool(rectangleTool);
        DrawTool ellipseTool = new EllipseTool(this);
        addTool(ellipseTool);
        DrawTool lineTool = new LineTool(this);
        addTool(lineTool);
        JokerTool jokerTool = new JokerTool(this);
        addTool(null);
        addTool(jokerTool);
    }

    /**
     * Changes the order of figures and moves the figures in the selection
     * to the front, i.e. moves them to the end of the list of figures.
     *
     * @param model     model in which the order has to be changed
     * @param selection selection which is moved to front
     */
    public void bringToFront(DrawModel model, List<Figure> selection) {
        // the figures in the selection are ordered according to the order in
        // the model
        List<Figure> orderedSelection = new LinkedList<Figure>();
        int pos = 0;
        for (Figure f : model.getFigures()) {
            pos++;
            if (selection.contains(f)) {
                orderedSelection.add(0, f);
            }
        }
        for (Figure f : orderedSelection) {
            model.setFigureIndex(f, --pos);
        }
    }

    /**
     * Changes the order of figures and moves the figures in the selection
     * to the back, i.e. moves them to the front of the list of figures.
     *
     * @param model     model in which the order has to be changed
     * @param selection selection which is moved to the back
     */
    public void sendToBack(DrawModel model, List<Figure> selection) {
        // the figures in the selection are ordered according to the order in
        // the model
        List<Figure> orderedSelection = new LinkedList<Figure>();
        for (Figure f : model.getFigures()) {
            if (selection.contains(f)) {
                orderedSelection.add(f);
            }
        }
        int pos = 0;
        for (Figure f : orderedSelection) {
            model.setFigureIndex(f, pos++);
        }
    }

    /**
     * Handles the opening of a new drawing from a file.
     */
    private void doOpen() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Open Graphic");
        chooser.setDialogType(JFileChooser.OPEN_DIALOG);
        chooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
            @Override
            public String getDescription() {
                return "JDraw Graphic (*.draw)";
            }

            @Override
            public boolean accept(File f) {
                return f.isDirectory() || f.getName().endsWith(".draw");
            }
        });
        int res = chooser.showOpenDialog(this);

        if (res == JFileChooser.APPROVE_OPTION) {
            getModel().removeAllFigures();
            readFromXML(chooser.getSelectedFile());
            // read jdraw graphic
            System.out.println("read file "
                    + chooser.getSelectedFile().getName());
        }
    }

    private void readFromXML(File xmlFile) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {

            DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder();

            Document doc = dBuilder.parse(xmlFile);

            if (doc.getDocumentElement().hasChildNodes()) {
                readFiguresFromXML(doc.getDocumentElement().getChildNodes(), null);
            }

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    private void readFiguresFromXML(NodeList nodeList, GroupFigure gf) {

        for (int count = 0; count < nodeList.getLength(); count++) {
            Node tempNode = nodeList.item(count);
            if (tempNode.hasChildNodes()) {
                GroupFigure groupFigure = new GroupFigure(getView());
                readFiguresFromXML(tempNode.getChildNodes(), groupFigure);
                getModel().addFigure(groupFigure);
            } else if ("Rect".equals(tempNode.getNodeName())) {
                int x=0, y=0, w=0, h=0;
                if (tempNode.hasAttributes()) {
                    NamedNodeMap nodeMap = tempNode.getAttributes();
                    for (int i = 0; i < nodeMap.getLength(); i++) {
                        Node node = nodeMap.item(i);
                        if ("x".equals(node.getNodeName())) { x = (int) Double.parseDouble(node.getNodeValue()); }
                        else if ("y".equals(node.getNodeName())) { y =(int) Double.parseDouble(node.getNodeValue()); }
                        else if ("width".equals(node.getNodeName())) { w = (int) Double.parseDouble(node.getNodeValue()); }
                        else if ("height".equals(node.getNodeName())) { h = (int) Double.parseDouble(node.getNodeValue()); }
                    }
                    if (gf == null) {
                        getModel().addFigure(new Rect(x, y, w, h));
                    } else {
                        gf.addFigure(new Rect(x, y, w, h));
                    }
                }
            } else if ("Ellipse".equals(tempNode.getNodeName())) {
                int x=0, y=0, w=0, h=0;
                if (tempNode.hasAttributes()) {
                    NamedNodeMap nodeMap = tempNode.getAttributes();
                    for (int i = 0; i < nodeMap.getLength(); i++) {
                        Node node = nodeMap.item(i);
                        if ("x".equals(node.getNodeName())) { x = (int) Double.parseDouble(node.getNodeValue()); }
                        else if ("y".equals(node.getNodeName())) { y =(int) Double.parseDouble(node.getNodeValue()); }
                        else if ("width".equals(node.getNodeName())) { w = (int) Double.parseDouble(node.getNodeValue()); }
                        else if ("height".equals(node.getNodeName())) { h = (int) Double.parseDouble(node.getNodeValue()); }
                    }
                    if (gf == null) {
                        getModel().addFigure(new Ellipse(x, y, w, h));
                    } else {
                        gf.addFigure(new Ellipse(x, y, w, h));
                    }

                }
            } else if ("Line".equals(tempNode.getNodeName())) {
                int x1=0, y1=0, x2=0, y2=0;
                if (tempNode.hasAttributes()) {
                    NamedNodeMap nodeMap = tempNode.getAttributes();
                    for (int i = 0; i < nodeMap.getLength(); i++) {
                        Node node = nodeMap.item(i);
                        if ("StartX".equals(node.getNodeName())) { x1 = (int) Double.parseDouble(node.getNodeValue()); }
                        else if ("StartY".equals(node.getNodeName())) { y1 =(int) Double.parseDouble(node.getNodeValue()); }
                        else if ("EndX".equals(node.getNodeName())) { x2 = (int) Double.parseDouble(node.getNodeValue()); }
                        else if ("EndY".equals(node.getNodeName())) { y2 = (int) Double.parseDouble(node.getNodeValue()); }
                    }
                    if (gf == null) {
                        getModel().addFigure(new Line(x1, y1, x2, y2));
                    } else {
                        gf.addFigure(new Line(x1, y1, x2, y2));
                    }
                }
            }


        }

    }

        /**
     * Handles the saving of a drawing to a file.
     */
    private void doSave() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Save Graphic");
        chooser.setDialogType(JFileChooser.SAVE_DIALOG);
        FileFilter filter = new FileFilter() {
            @Override
            public String getDescription() {
                return "JDraw Graphic (*.draw)";
            }

            @Override
            public boolean accept(File f) {
                return f.getName().endsWith(".draw");
            }
        };
        chooser.setFileFilter(filter);
        int res = chooser.showSaveDialog(this);

        if (res == JFileChooser.APPROVE_OPTION) {
            // save graphic
            File file = chooser.getSelectedFile();

            if (chooser.getFileFilter() == filter && !filter.accept(file)) {
                file = new File(chooser.getCurrentDirectory(), file.getName() + ".draw");
                saveToXML(file);
            }
            System.out.println("save current graphic to file " + file.getName());
        }
    }

    private void saveToXML(File file) {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = null;
        try {
            docBuilder = docFactory.newDocumentBuilder();


            // root elements
            Document doc = docBuilder.newDocument();

            Element rootElement = doc.createElement("Figures");
            doc.appendChild(rootElement);

            writeFiguresToXML(getModel().getFigures(), rootElement, doc);

            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(file);

            // Output to console for testing
            //StreamResult result = new StreamResult(System.out);

            transformer.transform(source, result);

        } catch (ParserConfigurationException | TransformerException ex) {
            ex.printStackTrace();
        }

    }

    private void writeFiguresToXML(Iterable<Figure> list, Element parent, Document doc) {
        Element e;
        for( Figure f : list) {
            if (f instanceof GroupFigure) {
                e = doc.createElement("GroupFigure");
                parent.appendChild(e);
                writeFiguresToXML(((GroupFigure) f).getFigureParts(), e, doc);
            } else if (f instanceof Rect) {
                e = doc.createElement("Rect");
                parent.appendChild(e);
                Rectangle r = f.getBounds();
                e.setAttribute("x", String.valueOf(r.getX()));
                e.setAttribute("y", String.valueOf(r.getY()));
                e.setAttribute("height", String.valueOf(r.getHeight()));
                e.setAttribute("width", String.valueOf(r.getWidth()));
            } else if (f instanceof Ellipse) {
                e = doc.createElement("Ellipse");
                parent.appendChild(e);
                Rectangle r = f.getBounds();
                e.setAttribute("x", String.valueOf(r.getX()));
                e.setAttribute("y", String.valueOf(r.getY()));
                e.setAttribute("height", String.valueOf(r.getHeight()));
                e.setAttribute("width", String.valueOf(r.getWidth()));
            } else if (f instanceof Line) {
                e = doc.createElement("Line");
                parent.appendChild(e);
                e.setAttribute("StartX", String.valueOf(((Line) f).getStart().getX()));
                e.setAttribute("StartY", String.valueOf(((Line) f).getStart().getY()));
                e.setAttribute("EndX", String.valueOf(((Line) f).getEnd().getX()));
                e.setAttribute("EndY", String.valueOf(((Line) f).getEnd().getY()));
            }
        }
    }

}

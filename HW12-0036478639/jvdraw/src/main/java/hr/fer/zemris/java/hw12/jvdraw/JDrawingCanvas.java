package hr.fer.zemris.java.hw12.jvdraw;


import hr.fer.zemris.java.hw12.jvdraw.objects.Circle;
import hr.fer.zemris.java.hw12.jvdraw.objects.FCircle;
import hr.fer.zemris.java.hw12.jvdraw.objects.GeometricalObject;
import hr.fer.zemris.java.hw12.jvdraw.objects.Line;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JComponent;

/**
 * Class representing canvas in {@link JVDraw}.
 * This is core object in {@link JVDraw} GUI.
 * It provides drawing of objects.
 * All objects visible on this canvas are part of
 * {@link IDrawingModel} model.
 * 
 * @author Teo Toplak
 *
 */
public class JDrawingCanvas extends JComponent implements IDrawingModelListener {

	/**
	 * what is happening
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * canvasas model
	 */
	private JDrawingModel model;
	/**
	 * Determines what object to draw.
	 * Selectable by toggle buttons in {@link JVDraw}
	 */
	private geomObject drawType;
	/**
	 * first point of drawing
	 */
	private Point point1;
	/**
	 * second point of drawing
	 */
	private Point point2;
	/**
	 * Object currently drawed by user.
	 */
	private GeometricalObject currObject;
	
	/**
	 * Enumeration of all possible objects for
	 * user to draw
	 * @author Teo Toplak
	 *
	 */
	public enum geomObject {
		LINE,CIRCLE,FCIRCLE
	}

	/**
	 * Constructor taking only model as argument
	 * @param model model
	 */
	public JDrawingCanvas(JDrawingModel model) {
		super();
		this.model = model;
		addMouseListener(getMouseListener());
		addMouseMotionListener(getMouseMotionListener());
	}


	@Override
	public void objectsAdded(IDrawingModel source, int index0, int index1) {
		repaint();
	}

	@Override
	public void objectsChanged(IDrawingModel source, int index0, int index1) {
		objectsAdded(source, index0, index1);
	}

	@Override
	public void objectsRemoved(IDrawingModel source, int index0, int index1) {
		objectsAdded(source, index0, index1);
	}


	/**
	 * Custom mouse listener getter used in this canvas.
	 * @return mouse listener
	 */
	public MouseAdapter getMouseListener() {
		return new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(point1==null) {
					point1 = e.getPoint();
				} else {
					model.add(currObject);
					currObject = null;
					point1=null;
					point2=null;
				}
			}
		};
	}
	
	/**
	 * Custom mouse motion listener getter used in this canvas.
	 * @return mouse motion listener
	 */
	public MouseMotionAdapter getMouseMotionListener() {
		return new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				if(point1!=null) {
					point2=e.getPoint();
					repaint();
				}
			}
		};
	}
	
	/**
	 * Sets new draw type on canvas
	 * @param type draw type
	 */
	public void newDrawType(geomObject type) {
		drawType=type;
	}


	/**
	 * Model getter
	 * @return the model
	 */
	public JDrawingModel getModel() {
		return model;
	}

	@Override
	protected void paintComponent(Graphics g) {
		Insets ins = getInsets();
		Dimension size = getSize();
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.black);
        g2d.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );
        g2d.drawRect(ins.left, ins.top, size.width
				- ins.right - ins.left, size.height - ins.top - ins.bottom);
        
        for(int index=0;index<model.getSize();index++) {
			model.getObject(index).paintComponent(g);
		}
        
        if(point1!=null && point2!=null) {
       	 switch (drawType) {
    		case LINE:
    			Line line = new Line("object_"+model.getSize()+": line", point1,
    					point2, model.getForegroundColor());
    			line.paintComponent(g);
    			currObject = line;
    			break;
    		case CIRCLE:
    			double radius = Math.sqrt(Math.pow(point1.x-point2.x,2)+
    					Math.pow(point1.y-point2.y, 2))*2;
    			Circle circle = new Circle("object_"+model.getSize()+":circle", 
    					point1, radius, model.getForegroundColor());
    			circle.paintComponent(g);
    			currObject = circle;
    			break;
    		case FCIRCLE:
    			double radiusF = Math.sqrt(Math.pow(point1.x-point2.x,2)+
    					Math.pow(point1.y-point2.y, 2))*2;
    			FCircle fCircle = new FCircle("object_"+model.getSize()+": filled circle", 
    					point1, radiusF, model.getForegroundColor(),model.getBackgroundColor());
    			fCircle.paintComponent(g);
    			currObject = fCircle;
    			break;
    		}
       }
       
	}
}

package hr.fer.zemris.java.hw12.jvdraw.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;

/**
 * Class represening simple filled circle.
 * @author Teo Toplak
 *
 */
public class FCircle extends GeometricalObject{

	/**
	 * what is happening
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Center of circle
	 */
	private Point center;
	/**
	 * Circle radius
	 */
	private double radius;
	/**
	 * Outline color
	 */
	private Color outlineColor;
	/**
	 * Area color
	 */
	private Color areaColor;
	

	/**
	 * Constructor accepting arguments for all properties
	 * of this class
	 * @param name name
	 * @param center center
	 * @param radius radius
	 * @param outlineColor outline color
	 * @param areaColor area color
	 */
	public FCircle(String name, Point center, double radius,
			Color outlineColor, Color areaColor) {
		super(name);
		this.center = center;
		this.radius = radius;
		this.outlineColor = outlineColor;
		this.areaColor = areaColor;
	}


	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		
		Ellipse2D circle = getEllipseFromCenter(center.getX(), center.getY(), radius, radius);
		g2d.setColor(areaColor);
		g2d.fill(circle);
		g2d.setColor(outlineColor);
		g2d.draw(circle);
		
	}
	
	/**
	 * Method contructs ellipse from upper left corner
	 * of ellipse and its height and width.
	 * Method basically shifts ellipse properties to 
	 * be set from center and not upper left corner.
	 * Used for more user-friendly GUI while drawing.
	 * @param x x coordinate for upper left corner
	 * @param y y cooridnate for upper left corner
	 * @param width width
	 * @param height height
	 * @return ellipse made from given arguments.
	 */
	private Ellipse2D getEllipseFromCenter(double x, double y, double width, double height) {
	    double newX = x - width / 2.0;
	    double newY = y - height / 2.0;
	    Ellipse2D ellipse = new Ellipse2D.Double(newX, newY, width, height);
	    return ellipse;
	} 
	
	/**
	 * Center getter
	 * @return the center
	 */
	public Point getCenter() {
		return center;
	}

	/**
	 * Radius getter
	 * @return the radius
	 */
	public double getRadius() {
		return radius;
	}

	/**
	 * Center setter
	 * @param center the center to set
	 */
	public void setCenter(Point center) {
		this.center = center;
	}

	/**
	 * Radius setter
	 * @param radius the radius to set
	 */
	public void setRadius(double radius) {
		this.radius = radius;
	}

	/**
	 * Area color getter
	 * @return the areaColor
	 */
	public Color getAreaColor() {
		return areaColor;
	}

	/**
	 * Area color setter
	 * @param areaColor the areaColor to set
	 */
	public void setAreaColor(Color areaColor) {
		this.areaColor = areaColor;
	}
	
	

	/**
	 * @return the outlineColor
	 */
	public Color getOutlineColor() {
		return outlineColor;
	}


	/**
	 * @param outlineColor the outlineColor to set
	 */
	public void setOutlineColor(Color outlineColor) {
		this.outlineColor = outlineColor;
	}


	@Override
	public String asText() {
		return "LINE " + center.x + " " + center.y + " " + (int) radius + " "
				+ outlineColor.getRed() + " " + outlineColor.getGreen() + " "
				+ outlineColor.getBlue() + " " + areaColor.getRed() + " "
				+ areaColor.getGreen() + " " + areaColor.getBlue();
	}
	
	
}

package hr.fer.zemris.java.hw12.jvdraw.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

/**
 * Class representing simple line.
 * @author Teo Toplak
 *
 */
public class Line extends GeometricalObject{
	
	/**
	 * what is happening
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Starting point of line
	 */
	private Point start;
	/**
	 * Ending point of line
	 */
	private Point end;
	/**
	 * Color of line
	 */
	private Color color;
	
	@Override
	public void paintComponent(Graphics g) {
		if(start==null) {
			return;
		}
		Graphics2D g2d = (Graphics2D)g;
		g2d.setColor(color);
		g2d.drawLine(start.x, start.y, end.x, end.y);
	}

	/**
	 * Constructor accepting argument for all properties of this line.
	 * @param name name of line
	 * @param start starting point of line
	 * @param end ending point of line
	 * @param color color of line
	 */
	public Line(String name, Point start, Point end, Color color) {
		super(name);
		this.start = start;
		this.end = end;
		this.color = color;
	}
	/**
	 * Staring point getter
	 * @return the start
	 */
	public Point getStart() {
		return start;
	}
	
	/**
	 * Starting point setter
	 * @param start the start to set
	 */
	public void setStart(Point start) {
		this.start = start;
	}
	/**
	 * Ening point getter
	 * @return the end
	 */
	public Point getEnd() {
		return end;
	}
	
	/**
	 * Ending point setter
	 * @param end the end to set
	 */
	public void setEnd(Point end) {
		this.end = end;
	}
	
	/**
	 * Color getter
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}
	/**
	 * Color setter
	 * @param color the color to set
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	@Override
	public String asText() {
		return "LINE " + start.x + " " + start.y + " " + end.x + " " + end.y
				+ " " + color.getRed() + " " + color.getGreen() + " "
				+ color.getBlue();
	}
	
	
}

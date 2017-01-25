package hr.fer.zemris.java.hw12.jvdraw.objects;

import java.awt.Graphics;

import javax.swing.JComponent;

/**
 * Class representing geometrical object.
 * Basically {@link JComponent} which implements name, 
 * and demands from all child classes to implement
 * paintComponent methods and asText method which 
 * provides conventional string.
 * @author Teo Toplak
 *
 */
public abstract class GeometricalObject extends JComponent{

	/**
	 * what is happening
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * name of this object
	 */
	private String name;
	/**
	 * Simple constructor accepting only name of new
	 * object
	 * @param name new object's name
	 */
	public GeometricalObject(String name) {
		super();
		this.name = name;
	}
	
	/**
	 * Method which returns text representing this object.<br>
	 * Representation is made with following convention:<br>
	 * for LINE: x0 y0 x1 y1 red green blue <br>
	 * for CIRCLE: centerx centery radius red green blue <br>
	 * for FCIRCLE: centerx centery radius red green blue red green blue <br>
	 * Where red, blue, green as in RGB color components.
	 * @return text representing drawing
	 */
	public abstract String asText();
	
	/**
	 * Name getter
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * Name setter
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public abstract void paintComponent(Graphics g);
	

	@Override
	public String toString() {
		return name;
	}


}

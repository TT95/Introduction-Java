package hr.fer.zemris.java.gui.charts;

/**
 * Class made for storing x and y values.
 * X and y values are read-only properties.
 * @author Teo Toplak
 *
 */
public class XYValue {

	/**
	 * x value
	 */
	private int x;
	/**
	 * y value
	 */
	private int y;

	/**
	 * Constructor which takes x and y values
	 * @param x x value
	 * @param y y value
	 */
	public XYValue(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	/**
	 * X value getter
	 * @return x value
	 */
	public int getX() {
		return x;
	}
	/**
	 * Y value getter
	 * @return y value
	 */
	public int getY() {
		return y;
	}
	
	
}

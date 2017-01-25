package hr.fer.zemris.java.hw12.jvdraw;

import java.awt.Color;

/**
 * Interface representing color provider.
 * Color provider has contract of providing method
 * getCurrentColor to listeners so that listeners
 * can ask provider for current color.
 * @author Teo Toplak
 *
 */
public interface IColorProvider {
	
	/**
	 * Returns current color
	 * @return current color
	 */
	public Color getCurrentColor();
	
}

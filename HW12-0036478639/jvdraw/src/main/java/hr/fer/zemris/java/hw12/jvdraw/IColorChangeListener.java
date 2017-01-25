package hr.fer.zemris.java.hw12.jvdraw;

import java.awt.Color;

/**
 * Interface describing listener added on list of listeners on
 * {@link IColorProvider} which fires listeners providing them color.
 * When that provider fires it must call this method.
 * 
 * @author Teo Toplak
 *
 */
public interface IColorChangeListener {
	
	/**
	 * Method called when new color is selected.
	 * Method provides information about color provider,
	 * old color and new color
	 * @param source color provider
	 * @param oldColor old color
	 * @param newColor new color
	 */
	public void newColorSelected(IColorProvider source, Color oldColor, Color newColor);
}

package hr.fer.zemris.java.hw12.jvdraw;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 * Class representing status bas for {@link JVDraw}.
 * It shows inforamtion about currently selected foreground
 * and background color in form of text
 * @author Teo Toplak
 *
 */
public class StatusBar extends JLabel implements IColorChangeListener{

	/**
	 * what is happening
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * foreground color
	 */
	private Color foreground;
	/**
	 * background color
	 */
	private Color background;
	
	/**
	 * Constructor accepting foreground and background color
	 * as arguments
	 * @param foreground foreground color
	 * @param background background color
	 */
	public StatusBar(Color foreground, Color background) {
		super();
		setHorizontalAlignment(SwingConstants.LEFT);
		this.foreground = foreground;
		this.background = background;
		writeText();
	}

	@Override
	public void newColorSelected(IColorProvider source, Color oldColor,
			Color newColor) {
		JColorArea area = (JColorArea) source;
		if(area.isForeground()) {
			foreground = source.getCurrentColor();
		} else {
			background = source.getCurrentColor();
		}
		writeText();
	}
	
	/**
	 * Writes current status of colors as text
	 */
	private void writeText() {
		this.setText("Foreground color: (" + foreground.getRed() + ", "
				+ foreground.getGreen() + ", " + foreground.getBlue()
				+ "), background color: (" + background.getRed() + ", "
				+ background.getGreen() + ", " + background.getBlue() + ").");
		this.repaint();
	}
	
}

package hr.fer.zemris.java.hw12.jvdraw;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JColorChooser;
import javax.swing.JComponent;

/**
 * Implementation of {@link IColorProvider} in {@link JVDraw}.
 * This is acctually button, which action is color changing when pressed.
 * In {@link JVDraw} this class is implemented in two ways: <br>
 * - as foreground color area button <br>
 * - as background color area button <br>
 * To determine which instance is which, class provides getters
 * for private property boolean foreground.
 * @author Teo Toplak
 *
 */
public class JColorArea extends JComponent implements IColorProvider{

	/**
	 * what is happening
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Color selected
	 */
	private Color selectedColor;
	/**
	 * list of listeners
	 */
	private List<IColorChangeListener> listeners;
	/**
	 * property determing which color are is this
	 * (read class javadoc for more info)
	 */
	private boolean foreground;
	/**
	 * constant for height of button
	 */
	private final int HEIGHT = 15;
	/**
	 * constant for width of button
	 */
	private final int WIDTH = 15;
	
	/**
	 * Constructor accepting basic instances for
	 * normal fuction
	 * @param selectedColor default color
	 * @param listenerList list of listeners
	 * @param foreground if this button is for foreground color
	 * 	or not
	 */
	public JColorArea(Color selectedColor,
			List<IColorChangeListener> listenerList, boolean foreground) {
		super();
		addMouseListener(getMouseListener(this));
		this.foreground = foreground;
		this.selectedColor = selectedColor;
		this.listeners = listenerList;
		setBorder(BorderFactory.createRaisedSoftBevelBorder());
	}

	/**
	 * Return mouse listener made for this button
	 * @param area color area
	 * @return mouse listener
	 */
	private MouseListener getMouseListener(JColorArea area) {
		return new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				selectedColor = JColorChooser.showDialog(null, "Foreground color", selectedColor);
				if(selectedColor == null) {
					selectedColor = Color.black;
				}
				area.repaint();
				for(IColorChangeListener l : listeners) {
					l.newColorSelected(area, selectedColor, null);
				}
			}
		};
	}

	@Override
	public Color getCurrentColor() {
		return selectedColor;
	}
	
	/**
	 * Adds color change listener
	 * @param l color change listener
	 */
	public void addColorChangeListener(IColorChangeListener l) {
		listeners.add(l);
	}
	
	/**
	 * Removes color change listener
	 * @param l color change listener
	 */
	public void removeColorChangeListener(IColorChangeListener l) {
		listeners.remove(l);
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(WIDTH, HEIGHT);
	}
	
	
	@Override
	protected void paintComponent(Graphics g) {
		
		Insets ins = getInsets();
		Dimension size = getPreferredSize();
		Rectangle rect = new Rectangle(ins.left, ins.top, size.width
				- ins.right - ins.left, size.height - ins.top - ins.bottom);
		g.setColor(selectedColor);
		g.fillRect(rect.x, rect.y, rect.width, rect.height);
		
	}

	/**
	 * Returns true if this button is 
	 * color changer for foreground
	 * @return the foreground
	 */
	public boolean isForeground() {
		return foreground;
	}

	

}

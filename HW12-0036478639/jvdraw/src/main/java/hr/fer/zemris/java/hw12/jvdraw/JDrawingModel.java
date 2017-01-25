package hr.fer.zemris.java.hw12.jvdraw;

import hr.fer.zemris.java.hw12.jvdraw.objects.GeometricalObject;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of core model for {@link JVDraw}.
 * Model mostly backs up {@link JVDraw} GUI.
 * It handles list of all objects created, and offers
 * easy communiaction with them. Also contains list of listener.
 * When objects are added/removed/changed this model fires.
 * Also offers manual fire() method for easier notifying listenrs
 * when model was changed.
 * 
 * @author Teo Toplak
 *
 */
public class JDrawingModel implements IDrawingModel, IColorChangeListener{
	
	/**
	 * list of objects
	 */
	private List<GeometricalObject> objects = new ArrayList<>();
	/**
	 * list of listeners
	 */
	private List<IDrawingModelListener> listeners = new ArrayList<>();
	
	/**
	 * current foreground color
	 */
	private Color foregroundColor;
	/**
	 * current background color
	 */
	private Color backgroundColor;

	/**
	 * Simple constructor accepting only colors to be set
	 * @param foregroundColor foreground color
	 * @param backgroundColor background color
	 */
	public JDrawingModel(Color foregroundColor, Color backgroundColor) {
		this.foregroundColor = foregroundColor;
		this.backgroundColor = backgroundColor;
	}

	@Override
	public int getSize() {
		return objects.size();
	}

	@Override
	public GeometricalObject getObject(int index) {
			return objects.get(index);
		
	}

	@Override
	public void add(GeometricalObject o) {
		objects.add(o);
		//firing
		fire();
	}

	@Override
	public void addDrawingModelListener(IDrawingModelListener l) {
		listeners.add(l);
	}

	@Override
	public void removeDrawingModelListener(IDrawingModelListener l) {
		listeners.remove(l);
	}

	@Override
	public void newColorSelected(IColorProvider source, Color oldColor,
			Color newColor) {
		JColorArea area = (JColorArea) source;
		if(area.isForeground()) {
			foregroundColor = source.getCurrentColor();
		} else {
			backgroundColor = source.getCurrentColor();
		}
	}

	/**
	 * Foreground color getter
	 * @return the foregroundColor
	 */
	public Color getForegroundColor() {
		return foregroundColor;
	}

	/**
	 * Background color getter
	 * @return the backgroundColor
	 */
	public Color getBackgroundColor() {
		return backgroundColor;
	}
	
	/**
	 * Method for manual firing listeners.
	 * Usually used when object was changed
	 */
	public void fire() {
		for(IDrawingModelListener listener : listeners) {
			listener.objectsAdded(this, objects.size(), objects.size());
		}
	}

}

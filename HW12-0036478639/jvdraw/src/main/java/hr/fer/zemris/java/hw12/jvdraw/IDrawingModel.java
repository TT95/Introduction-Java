package hr.fer.zemris.java.hw12.jvdraw;

import hr.fer.zemris.java.hw12.jvdraw.objects.GeometricalObject;

/**
 * Interface describing drawing model.
 * Model mostly backs up {@link JVDraw} GUI.
 * It handles list of all objects created, and offers
 * easy communiaction with them. Also contains list of listener.
 * When objects are added/removed/changed this model fires.
 * @author Teo Toplak
 *
 */
public interface IDrawingModel {
	/**
	 * returns current number of objects
	 * @return current number of objects
	 */
	public int getSize();
	/**
	 * Returns object at selected position in list
	 * @param index index of object in list
	 * @return object in list at given index
	 */
	public GeometricalObject getObject(int index);
	/**
	 * Adds object to list
	 * @param o object added
	 */
	public void add(GeometricalObject o);
	/**
	 * Adds listener to list.
	 * @param l listener to be added
	 */ 
	public void addDrawingModelListener(IDrawingModelListener l);
	/**
	 * Removes listener from model.
	 * @param l listener to be removed
	 */
	public void removeDrawingModelListener(IDrawingModelListener l);
}

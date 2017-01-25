package hr.fer.zemris.java.hw12.jvdraw;

/**
 * Interface representing {@link IDrawingModel} listener.
 * When model fires because of some action on objects list,
 * this listener will be notified.
 * @author Teo Toplak 
 *
 */
public interface IDrawingModelListener {
	/**
	 * Method called when object was added in model
	 * @param source reference to source
	 * @param index0 start index
	 * @param index1 end index (inclusive)
	 */
	public void objectsAdded(IDrawingModel source, int index0, int index1);
	/**
	 * Method called when object was changed in model
	 * @param source reference to source
	 * @param index0 start index
	 * @param index1 end index (inclusive)
	 */
	public void objectsChanged(IDrawingModel source, int index0, int index1);
	/**
	 * Method called when object was removed in model
	 * @param source reference to source
	 * @param index0 start index
	 * @param index1 end index (inclusive)
	 */
	public void objectsRemoved(IDrawingModel source, int index0, int index1);

}

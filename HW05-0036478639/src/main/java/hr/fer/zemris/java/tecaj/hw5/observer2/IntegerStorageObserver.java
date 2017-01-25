package hr.fer.zemris.java.tecaj.hw5.observer2;

/**
 * Interface which demands from objects valueChanged method.
 * @author Teo Toplak
 *
 */
public interface IntegerStorageObserver {
	

	/**
	 * Method which gets called from subject when internal value is changed.
	 * @param change IntegerStorageChange class
	 */
	public void valueChanged(IntegerStorageChange change);
}
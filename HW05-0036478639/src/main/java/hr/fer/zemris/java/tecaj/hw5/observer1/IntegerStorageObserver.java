package hr.fer.zemris.java.tecaj.hw5.observer1;

/**
 * Interface which demands from objects valueChanged method.
 * @author Teo Toplak
 *
 */
public interface IntegerStorageObserver {
	
	/**
	 * Method which gets called from subject when internal value is changed.
	 * @param istorage record storage
	 */
	public void valueChanged(IntegerStorage istorage);
}
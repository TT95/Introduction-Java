package hr.fer.zemris.java.tecaj.hw5.observer2;

/**
 * Class used as Concrete Observers in example of Observer pattern. Counts (and
 * writes to the standard output) the number of times value of the subject has
 * been changed since the registration.
 * 
 * @author Teo Toplak
 *
 */
public class ChangeCounter implements IntegerStorageObserver {

	/**
	 * Variable which counts number of changes.
	 */
	private int counter;
	
	/**
	 * Constructor which takes no arguments and sets value of counter to 0.
	 */
	public ChangeCounter () {
		counter=0;
	}
	
	/**
	 * Implemented method which takes action after value of the subject changed.
	 * Takes argument which represents encapsulation of subject values.
	 */
	public void valueChanged(IntegerStorageChange storageChange) {
		counter++;
		System.out.println("Number of value changes since tracking: " + counter);
	}

}

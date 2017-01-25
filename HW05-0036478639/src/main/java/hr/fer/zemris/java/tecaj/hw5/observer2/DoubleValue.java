package hr.fer.zemris.java.tecaj.hw5.observer2;

/**
 * Class used as Concrete Observers in example of Observer pattern which writes
 * to the standard output double value of the current value which is stored in
 * subject, but only first two times since its registation with subject.
 * 
 * @author Teo Toplak
 *
 */
public class DoubleValue implements IntegerStorageObserver {

	/**
	 * Variable which counts number of times subject changed.
	 * Used for removal from subject list after 2 times subject value has changed.
	 */
	private int counter;
	
	/**
	 * Constructor which sets counter to 2.
	 */
	public DoubleValue() {
		counter=2;
	}

	/**
	 * Implemented method which takes action after value of the subject changed.
	 * Takes argument which represents encapsulation of subject values.
	 */
	public void valueChanged(IntegerStorageChange storageChange) {
		
		System.out.println("Double value: " + 2*storageChange.getValueNow());
		counter--;
		if(counter==0) {
			storageChange.getStorage().removeObserver(this);
		}

	}
}

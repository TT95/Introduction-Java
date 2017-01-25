package hr.fer.zemris.java.tecaj.hw5.observer2;
/**
 * Class used as Concrete Observers in example of Observer pattern which writes
 * a square of the integer stored in the IntegerStorage class (subject).
 * 
 * @author Teo Toplak
 *
 */
public class SquareValue implements IntegerStorageObserver {

	/**
	 * Implemented method which takes action after value of the subject changed.
	 * Takes argument which represents encapsulation of subject values.
	 */
	public void valueChanged(IntegerStorageChange storageChange) {
		
		System.out.println("Provided new value: " + storageChange.getValueNow()
				+ ", square is " + Math.pow(storageChange.getValueNow(), 2));
	}

	
}

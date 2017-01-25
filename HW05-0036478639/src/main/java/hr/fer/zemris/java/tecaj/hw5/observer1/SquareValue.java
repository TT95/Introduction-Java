package hr.fer.zemris.java.tecaj.hw5.observer1;

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
	 * Argument is the subject.
	 */
	public void valueChanged(IntegerStorage istorage) {
		
		System.out.println("Provided new value: " + istorage.getValue()
				+ ", square is " + Math.pow(istorage.getValue(), 2));
	}

	
}

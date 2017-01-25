package hr.fer.zemris.java.tecaj.hw5.observer1;

/**
 * Class which represents main program for testing observer pattern.
 * @author Teo Toplak
 *
 */
public class ObserverExample {

	/**
	 * Method called when executing program. Uses few object types and changes
	 * internal value of subject several times for testing.
	 * 
	 * @param args command-line arguments
	 */
	public static void main(String[] args) {
		IntegerStorage istorage = new IntegerStorage(20);
		IntegerStorageObserver observer = new SquareValue();
		istorage.addObserver(observer);
		istorage.setValue(5);
		istorage.setValue(2);
		istorage.setValue(25);
		istorage.removeObserver(observer);
		istorage.addObserver(new ChangeCounter());
		istorage.addObserver(new DoubleValue());
		istorage.setValue(13);
		istorage.setValue(22);
		istorage.setValue(15);
	}
}

package hr.fer.zemris.java.tecaj.hw5.observer2;

import java.util.ArrayList;

/**
 * Class in example of Object pattern which represents the subject. Stores
 * integer value (after changing this value subject calls observers registered).
 * 
 * @author Teo Toplak
 *
 */
public class IntegerStorage {

	/**
	 * Integer value stored
	 */
	private int value;
	/**
	 * List of observers registered
	 */
	private ArrayList<IntegerStorageObserver>  observers;
	/**
	 * Constructor which takes initial value of integer stored as parameter.
	 * @param initialValue initial value of integer stored.
	 */
	public IntegerStorage(int initialValue) {
		this.value = initialValue;
		this.observers=new ArrayList<>();
	}
	/**
	 * Method which adds obsever to subject's list.
	 * @param observer observer
	 */
	public void addObserver(IntegerStorageObserver observer) {
		observers.add(observer);
	}
	/**
	 * Method which removes observer from subject's list
	 * @param observer observer
	 */
	public void removeObserver(IntegerStorageObserver observer) {
		
		for (int i = 0; i < observers.size(); i++) {
			if (observers.get(i) == observer) {
				observers.remove(i);
				break;
			}
		}

	}
	/**
	 * Clears all observers from subject's list
	 */
	public void clearObservers() {
		observers.clear();
	}
	/**
	 * Getter for integer value stored.
	 * @return integer value stored.
	 */
	public int getValue() {
		return value;
	}
	/**
	 * Sets subject's integer value.
	 * Calls all obsevers to action after setting value takes place.
	 * @param value value to be set
	 */
	public void setValue(int value) {
		// Only if new value is different than the current value:
		if(this.value!=value) {
			// Update current value
			int valueBefore=this.value;
			this.value = value; 
			// Notify all registered observers 
			
			if(observers!=null) {
				
				IntegerStorageChange storageChange= new IntegerStorageChange(this,valueBefore,value);
				//creating clone list to avoid ConcurrentModificationException
				ArrayList<IntegerStorageObserver> observerscopy=new ArrayList<>();
				for(IntegerStorageObserver observer : observers) {
					observerscopy.add(observer);
				}
				for(IntegerStorageObserver observer : observerscopy) {
					observer.valueChanged(storageChange);
				}
			}
		}
	}
}

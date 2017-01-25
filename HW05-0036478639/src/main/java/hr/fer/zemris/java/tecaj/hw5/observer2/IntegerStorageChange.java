package hr.fer.zemris.java.tecaj.hw5.observer2;

/**
 * Class which is used for ecapsulating values and reference of IntegerStorage
 * class as subject. Used for communication between subject and observers,
 * reference of this class is given to observer when subject values changes.
 * 
 * @author Teo Toplak
 *
 */
public class IntegerStorageChange {

	/**
	 * Reference to IntegerStorage
	 */
	private IntegerStorage storage;
	/**
	 * Value before change (on subject).
	 */
	private int valueBefore;
	/**
	 * Current value of subject.
	 */
	private int valueNow;
	
	/**
	 * Constructor which creates instance of IntegerStorageChange with arguments demanded.
	 * @param storage Reference to IntegerStorage
	 * @param valueBefore Value before change (on subject).
	 * @param valueNow Current value of subject.
	 */
	public IntegerStorageChange(IntegerStorage storage, int valueBefore,
			int valueNow) {
		this.storage = storage;
		this.valueBefore = valueBefore;
		this.valueNow = valueNow;
	}
	
	/**
	 * Getter for reference to IntegerStorage
	 * @return reference to IntegerStorage
	 */
	public IntegerStorage getStorage() {
		return storage;
	}
	/**
	 * Value before change getter.
	 * @return value before
	 */
	public int getValueBefore() {
		return valueBefore;
	}
	/**
	 * Current value of subject getter.
	 * @return current value
	 */
	public int getValueNow() {
		return valueNow;
	}
	
	
	
}

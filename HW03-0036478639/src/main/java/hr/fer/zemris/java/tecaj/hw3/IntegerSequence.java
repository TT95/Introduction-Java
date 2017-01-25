package hr.fer.zemris.java.tecaj.hw3;

/**
 * Class which allows user to loop from specified integer to specified integer with given step.
 * Uses Iterable interface, providing adequate iterators.
 */
import java.util.Iterator;

public class IntegerSequence implements Iterable<Integer>{

	/**
	 * starting value of loop
	 */
	private int startingValue;
	/**
	 * final value of loop
	 */
	private int finalVaule;
	/**
	 * step value of loop
	 */
	private int stepValue;
	
	/**
	 * Constructor with possibility of setting starting value, final value and step value.
	 * @param startingValue starting value 
	 * @param finalVaule final value 
	 * @param stepValue step value 
	 */
	public IntegerSequence(int startingValue, int finalVaule, int stepValue) {
		if((startingValue > finalVaule && stepValue > 0) || (startingValue < finalVaule && stepValue < 0)){
			throw new IllegalArgumentException("Wrong arguments!");
		}
		this.startingValue = startingValue;
		this.finalVaule = finalVaule;
		this.stepValue = stepValue;
	}

	/**
	 * Method which returns adequate iterator for looping 
	 */
	@Override
	public Iterator<Integer> iterator () {
		return new IntegerSequenceIterator(startingValue,finalVaule,stepValue); 
	}

	/**
	 * Class which represents iterator made for looping in IntegerSequence
	 */
	private class IntegerSequenceIterator implements Iterator<Integer>{
		
		/**
		 * current (starting) value 
		 */
		private int currentValue;
		/**
		 * final value
		 */
		private int finalVaule;
		/**
		 * step value
		 */
		private int stepValue;

		/**
		 * Constructor with possibility of setting starting value, final value and step value for iterator
		 * @param startingValue
		 * @param finalVaule
		 * @param stepValue
		 */
		public IntegerSequenceIterator(int startingValue, int finalVaule, int stepValue) {
			this.currentValue = startingValue;
			this.finalVaule = finalVaule;
			this.stepValue = stepValue;
		}

		/**
		 * Method which returns true if there is existing element left in sequence
		 * @return boolean
		 */
		public boolean hasNext() {
			if(currentValue>finalVaule){
				return false;
			}
			return true;
		}

		/**
		 * Method which return next element in sequence
		 * @return integer integer
		 */
		public Integer next() {

			if(this.hasNext()==false){
				throw new RuntimeException("Out of elements!");
			}
			int lastCurrentValue=currentValue;
			currentValue+=stepValue;
			return lastCurrentValue;
		}
		
		/**
		 * Method remove() is not supported for this sequence/class.
		 * @throws UnsupportedOperationException
		 */
		public void remove() {
			throw new UnsupportedOperationException("Removing elements from IntegerSequence is unsupported");
		}
	}
}

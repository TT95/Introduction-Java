package hr.fer.zemris.java.tecaj.hw5.db.comparison;

/**
 * Interface which implements strategy and demands satisfied() method for each
 * class implementing it.
 * 
 * @author Teo Toplak
 *
 */
public interface IComparisonOperator {
	/**
	 * Method which returnes true if values meet conditions set by this class.
	 * @param value1 first value
	 * @param value2 second value
	 * @return true if values meet conditions set by this class.
	 */
	public boolean satisfied(String value1, String value2);
}




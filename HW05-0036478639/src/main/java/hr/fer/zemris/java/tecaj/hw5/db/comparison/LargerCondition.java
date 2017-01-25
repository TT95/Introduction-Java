package hr.fer.zemris.java.tecaj.hw5.db.comparison;

import java.text.Normalizer;
import java.text.Normalizer.Form;

/**
 * Class (strategy) representing condition demanding first value to be larger than second.
 * @author Teo Toplak
 * 
 */
public class LargerCondition implements IComparisonOperator {
	
	/**
	 * Method which returnes true if values meet conditions set by this class.
	 * @param value1 first value
	 * @param value2 second value
	 * @return true if values meet conditions set by this class.
	 */
	public boolean satisfied(String value1, String value2) {
		value1=Normalizer.normalize(value1, Form.NFD);
		value2=Normalizer.normalize(value2, Form.NFD);
		if(value1.compareTo(value2) > 0) {
			return true;
		}
		return false;
	}
}

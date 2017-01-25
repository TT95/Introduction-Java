package hr.fer.zemris.java.tecaj.hw5.db.getters;

import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;

/**
 * Interface which implements strategy and demands get() method for each
 * class implementing it.
 * 
 * @author Teo Toplak
 *
 */
public interface IFieldValueGetter {
	
	/**
	 * Gets parameter of student record which class demands.
	 * @param record student record
	 * @return string string
	 */
	public String get(StudentRecord record);
}

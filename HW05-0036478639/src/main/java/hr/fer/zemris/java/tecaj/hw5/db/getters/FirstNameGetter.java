package hr.fer.zemris.java.tecaj.hw5.db.getters;

import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;
/**
 * Class (strategy) which demands first name of student record provided.
 * @author Teo Toplak
 * 
 */
public class FirstNameGetter implements IFieldValueGetter{
	/**
	 * Gets parameter of student record which class demands.
	 * @param record student record
	 * @return string string
	 */
	public String get(StudentRecord record) {
		return record.getFirstName();
	}
}

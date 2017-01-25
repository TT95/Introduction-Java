package hr.fer.zemris.java.tecaj.hw5.db.getters;

import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;
/**
 * Class (strategy) which demandsjmbag of student record provided.
 * @author Teo Toplak
 * 
 */
public class JmbagGetter implements IFieldValueGetter{
	/**
	 * Gets parameter of student record which class demands.
	 * @param record student record
	 * @return string string
	 */
	public String get(StudentRecord record) {
		return record.getJmbag();
	}
}


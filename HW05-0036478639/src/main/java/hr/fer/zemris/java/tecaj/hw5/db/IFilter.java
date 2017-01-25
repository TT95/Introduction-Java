package hr.fer.zemris.java.tecaj.hw5.db;

/**
 * Interface which demands accept method used for accepting records.
 * @author Teo Toplak
 *
 */
public interface IFilter {
	
	/**
	 * Method which accepts record if it matches condtitions in conditions list.
	 * @param record student record
	 * @return true if record matches condtitions
	 */
	public boolean accepts(StudentRecord record);
}

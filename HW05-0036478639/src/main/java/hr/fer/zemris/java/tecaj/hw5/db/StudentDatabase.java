package hr.fer.zemris.java.tecaj.hw5.db;
import java.util.*;

/**
 * Class which creates internal list of student records. Additionally it creates
 * index for fast retrevial of student records when jmbag is known (uses map).
 * 
 * @author Teo Toplak
 *
 */
public class StudentDatabase {

	/**
	 * Internal map of student records
	 */
	private Map<Integer,StudentRecord> map;
	
	/**
	 * Constructor of class which accepts list of student records and uses it
	 * for creating internal map.
	 * 
	 * @param list list of student records
	 */
	public StudentDatabase(List<StudentRecord> list) {
		
		if(list==null){
			throw new IllegalArgumentException("Illegal argument!");
		}
		
		map=new LinkedHashMap<>();
		for(StudentRecord record : list) {
			map.put(record.hashCode(), record);
		}
		
	}
	
	/**
	 * Method used for fast retrevial of student records when jmbag is known.
	 * @param jmbag jmbag of student
	 * @return StudentRecord student record 
	 */
	public StudentRecord forJMBAG(String jmbag) {
		
		return map.get(Integer.parseInt(jmbag));
	}
	
	/**
	 * Method which loops through all student records in its internal list,
	 * calls accept method on each record, and return true if record is
	 * accepted. Method accepts a reference to an object which is an instance of
	 * IFilter interface.
	 * @param filter reference to an object which is an instance of IFilter interface
	 * @return list od student records accepted.
	 */
	public List<StudentRecord> filter(IFilter filter) {
		
		List<StudentRecord> list=new LinkedList<>();
		
		for (Map.Entry<Integer,StudentRecord> entry : map.entrySet()) {
			StudentRecord record=entry.getValue();
			if(filter.accepts(record)) {
				list.add(record);
			}
		}
		
		return list; 
	}
	
}

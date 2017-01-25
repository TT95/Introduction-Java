package hr.fer.zemris.java.tecaj.hw5.db;

import java.util.LinkedList;
import java.util.List;

/**
 * Class used for parsing database information. Accepts list of records
 * containing jmbag,last name,first name and grade of person, makes proper
 * StudentRecord for each line and then creates database out of those records.
 * 
 * @author Teo Toplak
 *
 */
public class DatabaseParser {

	/**
	 * List of records
	 */
	private List<String> records;

	/**
	 * Constructor accepting list of records
	 * @param records list of records
	 */
	public DatabaseParser(List<String> records) {
		this.records = records;
	}
	
	/**
	 * Method which creates StudentRecord for each element in records list and
	 * then creates database out of those records.
	 * 
	 * @return StudentDatabase database created
	 */
	public StudentDatabase makeDatabase(){
		
		List<StudentRecord> list=new LinkedList<>();
		String[] info= new String[4];
		
		for(String record : records) {
			
			try{
				info=record.split("\t");
			} catch (Exception e) {
				System.err.println("Wrong format of text file for database!");
				System.exit(-1);
			}
			
			list.add(new StudentRecord(info[0], info[1], info[2], info[3]));
		}
		
		return new StudentDatabase(list);
	}
	
}

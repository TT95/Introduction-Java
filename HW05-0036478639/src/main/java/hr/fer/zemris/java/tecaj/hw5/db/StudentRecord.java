package hr.fer.zemris.java.tecaj.hw5.db;

/**
 * Class representing student records containing jmbag,last name,first name and grade.
 * @author Teo Toplak
 *
 */
public class StudentRecord {

	/**
	 * jmbag string
	 */
	private String jmbag;
	/**
	 * last name string
	 */
	private String lastName;
	/**
	 * first name string
	 */
	private String firstName;
	/**
	 * final grade string
	 */
	private String finalGrade;
	
	/**
	 * Constructor accepting jmbag,last name,first name and grade as parameter.
	 * @param jmbag jmbag string
	 * @param lastName last name string
	 * @param firstName first name string
	 * @param finalGrade final grade string
	 */
	public StudentRecord(String jmbag, String lastName, String firstName,String finalGrade) {
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.finalGrade = finalGrade;
	}
	
	/**
	 * Jmbag string getter.
	 * @return jmbag string
	 */
	public String getJmbag() {
		return jmbag;
	}
	
	/**
	 * Last name string getter.
	 * @return last name string
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * First name string getter.
	 * @return first name string 
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Final grade string getter.
 	 * @return final grade string
	 */
	public String getFinalGrade() {
		return finalGrade;
	}

	/**
	 * Method which returnes true if given records is equal by its parameters to this record.
	 * @param record StudentRecord record
	 * @return true if given records is equal by its parameters to this record
	 */
	public boolean equals(StudentRecord record) {
		
		if(record==null) {
			throw new IllegalArgumentException("Wrong argument!");
		}
		
		if (record.getFinalGrade().equals(this.getFinalGrade())
				&& record.getFirstName().equals(this.getFirstName())
				&& record.getJmbag() == this.getJmbag()
				&& record.getLastName().equals(this.getLastName())) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * Method returnes hashCode of record.
	 * In this case hash code will be student jmbag.
	 * @return hash code for this record.
	 */
	public int hashCode(){
		return Integer.parseInt(getJmbag());
	}
}

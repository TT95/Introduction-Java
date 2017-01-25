package hr.fer.zemris.java.hw14.model;

/**
 * Simple implementaion of voting poll
 * @author Teo Toplak
 *
 */
public class Poll {
	/**
	 * title
	 */
	private String title;
	/**
	 * message
	 */
	private String message;
	/**
	 * poll id
	 */
	private long id;
	
	/**
	 * Constructor with no arguments
	 */
	public Poll(){
	}
	

	/**
	 * title getter
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * message getter
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	
	
	/**
	 * id getter
	 * @return the id
	 */
	public long getId() {
		return id;
	}


	/**
	 * id setter
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}


	/**
	 * title setter
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * message setter
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
}
package hr.fer.zemris.java.hw15.dao;
/**
 * DAO excetion.
 * Usually occurs when problem with getting info from database
 * happens.
 * 
 * @author Teo Toplak
 *
 */
public class DAOException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor with message and cause
	 * @param message message
	 * @param cause cause
	 */
	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor with message as argument
	 * @param message message
	 */
	public DAOException(String message) {
		super(message);
	}
}

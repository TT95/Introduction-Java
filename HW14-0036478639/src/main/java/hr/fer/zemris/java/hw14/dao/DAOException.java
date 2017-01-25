package hr.fer.zemris.java.hw14.dao;

/**
 * Custom exception representing DAO exception
 * @author Teo Toplak
 *
 */
public class DAOException extends RuntimeException {

	/**
	 * what is happening
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor with no arguments
	 */
	public DAOException() {
	}

	/**
	 * Constructor taking arguments for constructing exception
	 * @param message message
	 * @param cause cause
	 * @param enableSuppression enable suppression
	 * @param writableStackTrace writable stack trace
	 */
	public DAOException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * Constructor taking message and cause for arguments
	 * @param message message
	 * @param cause cause
	 */
	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor taking only message as argument
	 * @param message message
	 */
	public DAOException(String message) {
		super(message);
	}

	/**
	 * Constructor taking only cause as argument
	 * @param cause cause
	 */
	public DAOException(Throwable cause) {
		super(cause);
	}
}

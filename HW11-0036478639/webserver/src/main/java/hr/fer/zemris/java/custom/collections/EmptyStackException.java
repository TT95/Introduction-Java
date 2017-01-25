package hr.fer.zemris.java.custom.collections;

/**
 * Thrown to indicate that stack was tried to be peeked or pushed while already empty.
 * @author Teo Toplak
 *
 */
public class EmptyStackException extends RuntimeException {
	
	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs an EmptyStackException with no detail message.
	 */
	public EmptyStackException() {
		
	}

	/**
	 * Constructs an EmptyStackException with the specified detail message.
	 * @param message the detail message
	 */
	public EmptyStackException(String message){	
		super(message);
	}

}

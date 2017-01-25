package hr.fer.zemris.java.custom.collections;

/**
 * @author Teo Toplak
 * Signals that an stack exception of some sort has occurred.
 */
public class EmptyStackException extends RuntimeException{
	
	//Field descriptor
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructs an EmptyStackException with null as its error detail message.
	 */
	public EmptyStackException(){
	}

	/**
	 * Constructs an EmptyStackException with the specified detail message.
	 * @param message exception message
	 */
	public EmptyStackException(String message){
		super(message);
	}
	
	/**
	 * Constructs an EmptyStackException with the specified cause.
	 * @param cause exception cause
	 */
	public EmptyStackException(Throwable cause){
		super(cause);
	}
	
	/**
	 * Constructs an EmptyStackException with the specified detail message and cause.
	 * @param message
	 * @param cause
	 */
	public EmptyStackException(String message, Throwable cause){
		super(message, cause);
	}
}

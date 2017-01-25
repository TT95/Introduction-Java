package hr.fer.zemris.java.custom.scripting.parser;

/**
 * @author Teo Toplak
 * Signals that an SmartScriptParser exception of some sort has occurred.
 */
public class SmartScriptParserException extends RuntimeException{

	//Field descriptor
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructs an SmartScriptParserException with null as its error detail message.
	 */
	public SmartScriptParserException(){
	}
	
	/**
	 * Constructs an SmartScriptParserException with the specified detail message.
	 * @param message exception message
	 */
	public SmartScriptParserException(String message){
		super(message);
	}
	
	/**
	 * Constructs an SmartScriptParserException with the specified cause.
	 * @param cause exception cause
	 */
	public SmartScriptParserException(Throwable cause){
		super(cause);
	}
	
	/**
	 * Constructs an SmartScriptParserException with the specified detail message and cause.
	 * @param message
	 * @param cause
	 */
	public SmartScriptParserException(String message, Throwable cause){
		super(message, cause);
	}
}

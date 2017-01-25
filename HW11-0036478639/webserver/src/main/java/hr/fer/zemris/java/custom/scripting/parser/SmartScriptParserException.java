package hr.fer.zemris.java.custom.scripting.parser;

/**
 * Thrown to indicate that there has been an error in parsing of the document.
 * @author Teo Toplak
 *
 */
public class SmartScriptParserException extends RuntimeException{
	
	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructs an SmartScriptParserException with no detail message.
	 */
	public SmartScriptParserException() {
		
	}

	/**
	 * Constructs an SmartScriptParserException with the specified detail message.
	 * @param message the detail message
	 */
	public SmartScriptParserException(String message){
		
		super(message);
		
	}

	
}

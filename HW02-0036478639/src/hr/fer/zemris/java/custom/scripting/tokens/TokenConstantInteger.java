package hr.fer.zemris.java.custom.scripting.tokens;

/**
 * Class token with integer value.
 * @author Teo Toplak
 */
public class TokenConstantInteger extends Token{

	/**
	 * token value
	 */
	private int value;
	
	/**
	 * Constructs token with integer value
	 * @param value that represents integer value
	 */

	public TokenConstantInteger(int value) {
		super();
		this.value = value;
	}
	
	/**
	 * Method returns token value
	 */
	public String asText(){
		return value + "";
	}
}

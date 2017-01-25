package hr.fer.zemris.java.custom.scripting.tokens;

/**
 * Class token with double value.
 * @author Teo Toplak
 */
public class TokenConstantDouble extends Token {

	/**
	 * token value
	 */
	private double value;
	
	/**
	 * Constructs token with double value
	 * @param value that represents double value
	 */
	public TokenConstantDouble(double value) {
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

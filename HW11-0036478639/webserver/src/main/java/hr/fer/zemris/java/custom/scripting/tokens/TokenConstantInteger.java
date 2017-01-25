package hr.fer.zemris.java.custom.scripting.tokens;

/**
 * Token representation of a constant integer.
 * @author Teo Toplak
 *
 */
public class TokenConstantInteger extends Token {

	/**
	 * Stored constant integer number
	 */
	private int value;
	
	/**
	 * Constructs a TokenConstantInteger.
	 * @param value constant integer number to be stored in token
	 */
	public TokenConstantInteger(int value) {
		this.value = value;
	}
	
	/**
	 * Gets value property of TokenConstantInteger
	 * @return value property
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Returns stored integer number as string.
	 * @return stored integer number as string, avoiding scientific notation
	 */
	@Override
	public String asText() {
		return Integer.toString(value);
	}
	
}

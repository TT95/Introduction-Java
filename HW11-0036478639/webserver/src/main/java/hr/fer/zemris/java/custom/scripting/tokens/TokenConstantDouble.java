package hr.fer.zemris.java.custom.scripting.tokens;

/**
 * Token representation of a constant double number.
 * @author Teo Toplak
 *
 */
public class TokenConstantDouble extends Token {
	
	/**
	 * Stored constant double number.
	 */
	private double value;
	
	/**
	 * Constructs a TokenConstantDouble.
	 * @param value constant double number to be stored in token
	 */
	public TokenConstantDouble(double value) {
		this.value = value;
	}
	
	/**
	 * Gets value property of TokenConstantDouble
	 * @return value property
	 */	
	public double getValue() {
		return value;
	}

	/**
	 * Returns stored double number as string.
	 * @return stored double number as string
	 */
	@Override
	public String asText() {
		return String.valueOf(value);
	}
	
}

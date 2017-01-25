package hr.fer.zemris.java.custom.scripting.tokens;

/**
 * Token representation of an operator.
 * @author Teo Toplak
 *
 */
public class TokenOperator extends Token {

	/**
	 * Stored operator symbol.
	 */
	private String symbol;
	
	/**
	 * Constructs a TokenOperator.
	 * @param symbol operator symbol to be stored in token
	 */
	public TokenOperator(String symbol) {
		this.symbol = symbol;
	}
	
	/**
	 * Gets symbol property of TokenOperator
	 * @return symbol property
	 */
	public String getSymbol() {
		return symbol;
	}

	/**
	 * Returns stored operator symbol.
	 * @return stored operator symbol
	 */
	@Override
	public String asText() {
		return symbol;
	}

}


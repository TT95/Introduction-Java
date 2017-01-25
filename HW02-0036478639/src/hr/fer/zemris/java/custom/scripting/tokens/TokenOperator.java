package hr.fer.zemris.java.custom.scripting.tokens;

/**
 * Class token with operator value.
 * @author Teo Toplak
 */
public class TokenOperator extends Token{


	/**
	 * token value
	 */
	private String symbol;
	
	/**
	 * Constructs token with operator value
	 * @param value that represents operator value
	 */
	public TokenOperator(String symbol) {
		super();
		this.symbol = symbol;
	}

	/**
	 * Method returns token value
	 */
	public String asText(){
		return symbol + "";
	}
}

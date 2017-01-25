package hr.fer.zemris.java.custom.scripting.tokens;

/**
 * Class token with string value.
 * @author Teo Toplak
 */
public class TokenString extends Token{

	/**
	 * token value
	 */
	private String value;
	
	/**
	 * Constructs token with string value
	 * @param value that represents string value
	 */
	public TokenString(String value) {
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

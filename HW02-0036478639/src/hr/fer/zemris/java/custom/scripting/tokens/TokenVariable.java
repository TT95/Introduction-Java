package hr.fer.zemris.java.custom.scripting.tokens;

public class TokenVariable extends Token{

	/**
	 * token value
	 */
	private String name;
	
	/**
	 * Method returns token value
	 */
	public String asText(){
		return name;
	}

	/**
	 * Constructs token with variable value
	 * @param value that represents variable value
	 */
	public TokenVariable(String name) {
		super();
		this.name = name;
	}
}

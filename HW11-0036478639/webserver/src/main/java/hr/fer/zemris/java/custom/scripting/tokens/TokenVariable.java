package hr.fer.zemris.java.custom.scripting.tokens;

/**
 * Token representation of a variable.
 * @author Teo Toplak
 *
 */
public class TokenVariable extends Token {

	/**
	 * Stored variable name.
	 */
	private String name;
	
	/**
	 * Constructs a TokenVariable.
	 * @param name name to be stored in token
	 */
	public TokenVariable(String name) {
		this.name = name;
	}
	
	/**
	 * Gets name property of TokenVariable
	 * @return name property
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns stored variable name
	 * @return stored variable name
	 */
	@Override
	public String asText() {
		return name;
	}

}

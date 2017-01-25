package hr.fer.zemris.java.custom.scripting.tokens;

/**
 * Token representation of a function.
 * @author Teo Toplak
 *
 */
public class TokenFunction extends Token {

	/**
	 * Stored function name
	 */
	private String name;
	
	/**
	 * Constructs a TokenFunction.
	 * @param name function name to be stored in token
	 */
	public TokenFunction(String name) {
		this.name = name;
	}
	
	/**
	 * Gets name property of TokenFunction
	 * @return name property
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns stored function name.
	 * @return stored function name
	 */
	@Override
	public String asText() {
		return "@" + name;
	}

}


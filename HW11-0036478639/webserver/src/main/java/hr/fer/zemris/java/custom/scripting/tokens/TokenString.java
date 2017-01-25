package hr.fer.zemris.java.custom.scripting.tokens;

/**
 * Token representation of a string.
 * @author Teo Toplak
 *
 */
public class TokenString extends Token {

	/**
	 * Stored string.
	 */
	private String value;
	
	/**
	 * Constructs a TokenString.
	 * @param value string to be stored in token
	 */
	public TokenString(String value) {
		this.value = value;
	}
	
	/**
	 * Gets value property of TokenString
	 * @return value property
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Returns stored string, with escaped characters.
	 * @return stored string, with escaped characters
	 */
	@Override
	public String asText() {
		
		String string = value;
		// replace \ with \\
		string = string.replaceAll("\\\\", "\\\\\\\\");
		// replace newline with \n
		string = string.replaceAll("\\n", "\\\\n");
		// replace return with \r
		string = string.replaceAll("\\r", "\\\\r");
		// replace tab with \t
		string = string.replaceAll("\\t", "\\\\t");
		// replace " with \"
		string = string.replaceAll("\"", "\\\\\"");
		return "\"" + string + "\"";
	}

}

package hr.fer.zemris.java.custom.scripting.tokens;

/**
 * Class token with function value.
 * @author Teo Toplak
 */
public class TokenFunction extends Token{

	/**
	 * token value
	 */
	private String name;
	
	/**
	 * Constructs token with function value
	 * @param value that represents function value
	 */
	public TokenFunction(String name) {
		super();
		this.name = name;
	}
	
	/**
	 * Method returns token value
	 */
	public String asText(){
		return  "@"+name;
	}
}

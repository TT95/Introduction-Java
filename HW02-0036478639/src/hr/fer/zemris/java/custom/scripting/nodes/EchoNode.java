package hr.fer.zemris.java.custom.scripting.nodes;
import hr.fer.zemris.java.custom.scripting.tokens.*;

/**
 * Class which represents echo node.
 * Node which is associated to tag with = name.
 * Contains array of Tokens.
 * @author Teo Toplak
 *
 */

public class EchoNode extends Node{

	/**
	 * tokens-array of tokens.
	 */
	private Token[] tokens;
	
	/**
	 * Constructs echo node using original text and array of tokens.
	 * @param originalText original text
	 * @param tokens array of tokens
	 */
	public EchoNode(Token[] tokens) {
		this.tokens = tokens;
	}

	/**
	 * Method which returns reference to array of tokens
	 * @return
	 */
	public Token[] getTokens() {
		return tokens;
	}
	
	/**
	 * Method which returns node's text appended from its tokens.
	 * @return node's text
	 */
	public String getText(){
		String text="{$= ";
		for(Token token : tokens)
			text+= token.asText() + " ";
		text+="$}";
		return text;
	}
		
	
}

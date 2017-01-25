package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.tokens.Token;

/**
 * Node representing echo tag.
 * @author Teo Toplak
 *
 */
public class EchoNode extends Node {
	
	/**
	 * Echo tag tokens.
	 */
	Token[] tokens;

	/**
	 * Constructs an EchoNode.
	 * @param tokens echo node tokens
	 */
	public EchoNode(Token[] tokens) {	
		super();
		this.tokens = tokens;	
	}

	/**
	 * Returns array of echo node tokens
	 * @return echo node tokens
	 */
	public Token[] getTokens() {
		
		return tokens;
		
	}
	
	@Override
	public void accept(INodeVisitor visitor) {
	visitor.visitEchoNode(this);
	}

}

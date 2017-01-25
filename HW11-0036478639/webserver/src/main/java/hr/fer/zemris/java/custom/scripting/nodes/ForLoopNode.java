package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.tokens.Token;
import hr.fer.zemris.java.custom.scripting.tokens.TokenVariable;

/**
 * Node representing for-loop tag.
 * @author Teo Toplak
 *
 */
public class ForLoopNode extends Node {
	
	/**
	 * For-loop variable
	 */
	TokenVariable variable;
	
	/**
	 * For-loop start expression.
	 */
	Token startExpression;
	
	/**
	 * For-loop end expression.
	 */
	Token endExpression;
	
	/**
	 * For-loop step expression.
	 */
	Token stepExpression;
	
	/**
	 * Constructs a ForLoopNode with four tokens.
	 * @param variable for-loop variable
	 * @param startExpression for-loop start expression
	 * @param endExpression for-loop end expression
	 * @param stepExpression for-loop step expression
	 */
	public ForLoopNode(TokenVariable variable, Token startExpression,
			Token endExpression, Token stepExpression) {
		
		super();
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
		
	}
	
	/**
	 * Constructs a ForLoopNode with three tokens.
	 * @param variable for-loop variable
	 * @param startExpression for-loop start expression
	 * @param endExpression for-loop end expression
	 */
	public ForLoopNode(TokenVariable variable, Token startExpression,
			Token endExpression) {
		
		this(variable, startExpression, endExpression, null);
		
	}

	/**
	 * Gets for-loop variable
	 * @return for-loop variable
	 */
	public TokenVariable getVariable() {
		
		return variable;
		
	}

	/**
	 * Gets for-loop start expression
	 * @return for-loop start expression
	 */
	public Token getStartExpression() {
		
		return startExpression;
		
	}

	/**
	 * Gets for-loop end expression
	 * @return for-loop end expression
	 */
	public Token getEndExpression() {	
		return endExpression;	
	}

	/**
	 * Gets for-loop step expression
	 * @return for-loop step expression
	 */
	public Token getStepExpression() {
		
		return stepExpression;
		
	}
	
	@Override
	public void accept(INodeVisitor visitor) {
	visitor.visitForLoopNode(this);
	}

	
}

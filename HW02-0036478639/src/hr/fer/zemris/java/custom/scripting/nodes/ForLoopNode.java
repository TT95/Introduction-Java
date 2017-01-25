package hr.fer.zemris.java.custom.scripting.nodes;
import hr.fer.zemris.java.custom.scripting.tokens.*;

/**
 * Class which represents for loop node.
 * Contains 3-4 tokens, starting with token variable.
 * Node which is associated with FOR name tag.
 * @author Teo Toplak
 *
 */
public class ForLoopNode extends Node{

	/**
	 * Each of following tokens represents 3-4 tokens on which for loop node is based.
	 */
	private TokenVariable variable;
	private Token startExpression;
	private Token endExpression;
	private Token stepExpression;
	
	
	/**
	 * Constructs ForLoopNode with 3 tokens and original text
	 * @param originalText original text
	 * @param variable token variable
	 * @param startExpression start-expression token
	 * @param endExpression end-expression token
	 */
	public ForLoopNode(TokenVariable variable, Token startExpression,
			Token endExpression) {
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression=null;
	}
	
	/**
	 * Constructs ForLoopNode with 4 tokens and original text
	 * @param originalText original text
	 * @param variable token variable
	 * @param startExpression start-expression token
	 * @param stepExpression step-expression token
	 * @param endExpression end-expression token
	 */
	public ForLoopNode(TokenVariable variable, Token startExpression,
			Token endExpression, Token stepExpression) {
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}

	/**
	 * Token variable getter
	 */
	public TokenVariable getVariable() {
		return variable;
	}
	/**
	 *StartExpression Token getter
	 */
	public Token getStartExpression() {
		return startExpression;
	}
	/**
	 * EndExpression Token getter
	 */
	public Token getEndExpression() {
		return endExpression;
	}
	/**
	 * StepExpression Token getter
	 */
	public Token getStepExpression() {
		return stepExpression;
	}
	
	/**
	 * Method which returns node's text appended from its tokens.
	 * @return node's text
	 */
	public String getText() {
		if(stepExpression==null){
			return "{$FOR "  + variable.asText() + " " + startExpression.asText() + " "
					+ endExpression.asText() + "$}";
		}
		else
			return "{$FOR "  + variable.asText() + " " + startExpression.asText() + " "
			 + stepExpression.asText() + " " + endExpression.asText() + "$}";
	}
	
}

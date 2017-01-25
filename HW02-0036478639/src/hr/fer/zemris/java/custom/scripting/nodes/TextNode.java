package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Class which represents text node.
 * This node is based on original text content, no tokens used.
 * @author Teo Toplak
 */
public class TextNode extends Node{

	private String text;
	
	/**
	 * Costructs TextNode with original text
	 * @param originalText original text
	 */
	public TextNode(String text) {
		this.text=text;
	}

	/**
	 * Method which returns node's text 
	 * @return original text
	 */
	public String getText() {
		return text;
	}
	
}

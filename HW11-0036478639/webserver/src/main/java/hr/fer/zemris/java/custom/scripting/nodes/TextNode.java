package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Node representing document text between tags.
 * @author Teo Toplak
 *
 */
public class TextNode extends Node {

	/**
	 * Node text between tags.
	 */
	String text;

	/**
	 * Constructs a TextNode.
	 * @param text normal text
	 */
	public TextNode(String text) {
		super();
		this.text = text;	
	}

	/**
	 * Gets the text of a text node.
	 * @return text of a text node
	 */
	public String getText() {
		
		String string = text;
		// replace \ with \\
		string = string.replaceAll("\\\\", "\\\\\\\\");
		// replace {$ with \{$
		string = string.replaceAll("(\\{\\$)", "\\\\{\\$");
		
		return string;
		
	}
	
	@Override
	public void accept(INodeVisitor visitor) {
	visitor.visitTextNode(this);
	}
	
}

package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Class which represents document node.
 * Notice that text for this node is always blank "" (Used mostly for creating head of node model).
 * @author Teo Toplak
 */
public class DocumentNode extends Node{

	/**
	 * Constructs DocumentNode
	 * @param originalText original text
	 */
	public DocumentNode(String originalText){
		super();
	}
	
	/**
	 * Method which returns node's text.
	 * In this case of document node, text is always empty (document node doesn't contain any text)
	 */
	public String getText(){
		return "";
	}
	
}

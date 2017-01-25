package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Root node of the document.
 * @author Teo Toplak
 *
 */
public class DocumentNode extends Node {

	@Override
	public void accept(INodeVisitor visitor) {
	visitor.visitDocumentNode(this);
	}
}

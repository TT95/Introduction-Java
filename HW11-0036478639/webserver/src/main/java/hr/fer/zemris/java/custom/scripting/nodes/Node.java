package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.collections.ArrayBackedIndexedCollection;

/**
 * Node of a document model.
 * @author Teo Toplak
 *
 */
public abstract class Node {
	
	/**
	 * Collection of children of this node.
	 */
	ArrayBackedIndexedCollection children;
		
	/**
	 * Adds given child to this node.
	 * @param child child to be added
	 */
	public void addChildNode(Node child) {
		
		if(children == null){
			children = new ArrayBackedIndexedCollection();
		}
		
		children.add(child);
		
	}
	
	/**
	 * Returns number of (direct) children of this node.
	 * @return number of children
	 */
	public int numberOfChildren() {
		
		if(children == null) {
			return 0;
		}
		
		return children.size();
		
	}
	
	/**
	 * Returns selected child of this node.
	 * @param index index of child to be returned
	 * @throws IllegalArgumentException if index less than 0 or greater-or-equal than number of children
	 * @return node node
	 */
	public Node getChild(int index){
		
		if(children == null) {
			throw new IllegalArgumentException();
		}
		
		return (Node)children.get(index);
		
	}
	
	/**
	 * Method accept used in visitor pattern 
	 * @param visitor node visitor
	 */
	public abstract void accept(INodeVisitor visitor);
	
}

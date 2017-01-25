package hr.fer.zemris.java.custom.scripting.nodes;
import hr.fer.zemris.java.custom.collections.*;

/**
 * Class which represents a node.
 * It contains methods that allows us to create children nodes, get information about child
 * nodes and give references to those nodes.
 * @author Teo Toplak
 *
 */
public class Node {

	/**
	 * firstTimeCalled- boolean used for information where 'true' represents Node which doesnt have 
	 * 					children (used primarily for initialization of array-backed collection)
	 * array- array backed collection used for storing objects (primarily child nodes)		
	 */
	private boolean firstTimeCalled;
	private ArrayBackedIndexedCollection array; 
	
	/**
	 * Constructs Node with containing original text
	 * @param originalText original text
	 */
	public Node() {
		this.firstTimeCalled = true;
	}

	/**
	 * Method which adds child node to current node
	 * @param child child node
	 */
	public void addChildNode(Node child){
		if(firstTimeCalled==true){
			firstTimeCalled=false;
			array= new ArrayBackedIndexedCollection();
		}
		array.add(child);
	}
	
	/**
	 * Method which returns number of children
	 * @return number of children
	 */
	public int numberOfChildren(){
		if(firstTimeCalled==true) 
			return 0; 
		return array.size();
	}
	
	/**
	 * Method which return reference to child node according to index set.
	 * @param index index of child node
	 * @return reference to child node
	 */
	public Node getChild(int index){
		return (Node)array.get(index);
	}
	
	/**
	 * Method which returns node's text.
	 * In this case (normal Node) string is empty.
	 * @return original text
	 */
	public String getText() {
		return "";
	}
}

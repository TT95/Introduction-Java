package hr.fer.zemris.java.custom.scripting.exec;

import java.util.HashMap;
import java.util.NoSuchElementException;

/**
 * Class which represends collection as a map that associates strings with
 * stacks. Stack is implemented with nested class which represent nodes. Keys
 * for your ObjectMultistack are instances of the class String.
 * 
 * @author Teo Toplak
 *
 */
public class ObjectMultistack {

	/**
	 * Internal map for storing objects
	 */
	private HashMap<String,MultistackEntry> map;
	
	/**
	 * Constructor with no arguments which creates instance of hashmap.
	 */
	public ObjectMultistack() {
		map=new HashMap<>();
	}
	
	/**
	 * Method which pushes element to stack with given string as a key. If
	 * element on stack before push has the same value as element in argument,
	 * duplicate will be created and pushed on stack.
	 * 
	 * @param name
	 *            key string
	 * @param valueWrapper
	 *            element
	 */
	public void push(String name, ValueWrapper valueWrapper) {
		
		MultistackEntry entry=map.get(name);
		MultistackEntry newEntry=new MultistackEntry(valueWrapper); 
		if(entry==null) {
			map.put(name, newEntry);
		} else {
			newEntry.setNext(entry);
			map.put(name, newEntry);
		}
	}
	
	/**
	 * Method which pushes element from stack with given string as a key.
	 * @param name string key
	 * @return element popped
	 * @throws NoSuchElementException if there is no elements to pop
	 */
	public ValueWrapper pop(String name) {
		MultistackEntry entry=map.get(name);
		if(entry==null) {
			throw new NoSuchElementException("No elements for pop!");
		}
		MultistackEntry nextEntry=entry.getNext();
		map.put(name, nextEntry);
		return entry.getValue();
	}
	
	/**
	 * Method which peeks for element on stack with matched key value.
	 * @param name string key
	 * @return element from stack
	 * @throws NoSuchElementException if there are no elements to peek
	 */
	public ValueWrapper peek(String name) {
		MultistackEntry entry=map.get(name);
		if(entry==null) {
			throw new NoSuchElementException("No elements for peek!");
		}
		return entry.getValue();
	}
	
	/**
	 * Method which returnes true if stack with given key is empty, false otherwise.
	 * @param name string key
	 * @return true if if stack with given key is empty, false otherwise
	 */
	public boolean isEmpty(String name) {
		MultistackEntry entry=map.get(name);
		if(entry==null) {
			return true;
		}
		return false;
	}
	
	/**
	 * Nested static class which represents nodes in stack of this collection.
	 * It has stored ValueWrapper value and reference to next node.
	 * @author Teo Toplak
	 *
	 */
	 private static class  MultistackEntry {
		
		 /**
		  * Next node
		  */
		private MultistackEntry next;
		/**
		 * Value of element
		 */
		private ValueWrapper value;
		
		/**
		 * Constructor which creates instance with next reference as null, and
		 * value with given variable.
		 * 
		 * @param value value of new element
		 */
		public MultistackEntry(ValueWrapper value) {
			this.value=value;
			next=null;
		}

		/**
		 * Next variable getter.
		 * @return next element
		 */
		public MultistackEntry getNext() {
			return next;
		}

		/**
		 * Next variable setter
		 * @param next element
		 */
		public void setNext(MultistackEntry next) {
			this.next = next;
		}

		/**
		 * Value variable getter.
		 * @return value
		 */
		public ValueWrapper getValue() {
			return value;
		}
		
	}
}

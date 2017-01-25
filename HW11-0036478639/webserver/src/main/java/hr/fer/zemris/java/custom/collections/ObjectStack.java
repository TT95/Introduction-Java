package hr.fer.zemris.java.custom.collections;

/**
 * Stack collection of Objects.
 * Collection can not contain null Objects.
 * @author Teo Toplak
 *
 */
public class ObjectStack {
	
	/**
	 * Private instance of ArrayBackedIndexedCollection use for stack element storage.
	 */
	private ArrayBackedIndexedCollection collection;
	
	/**
	 * Constructs an ObjectStack.
	 */
	public ObjectStack(){
		collection = new ArrayBackedIndexedCollection();
	}
	
	/**
	 * Tests if stack is empty.
	 * @return false if empty, true otherwise
	 */
	public boolean isEmpty() {
		return collection.isEmpty();
	}
	
	/**
	 * Returns current size of stack.
	 * @return current size of stack, zero if empty
	 */
	public int size() {
		return collection.size();
	}
	
	/**
	 * Pushes given Object on stack. Object must not be null.
	 * @param value Object to be pushed
	 * @throws IllegalArgumentException if null Object is given
	 */
	public void push(Object value) {
		collection.add(value);
	}
	
	/**
	 * Returns last Object pushed on the stack and removes it from stack.
	 * @return last Object pushed on stack
	 * @throws EmptyStackException if stack is empty
	 */
	public Object pop() {
		Object temp = peek();
		collection.remove(size()-1);
		return temp;
	}
	
	/**
	 * Returns last Object pushed on the stack, but does not remove it from stack.
	 * @return last Object pushed on stack
	 * @throws EmptyStackException if stack is empty
	 */
	public Object peek() {
		if(collection.size() == 0){
			throw new EmptyStackException();
		}
		return collection.get(size()-1);
	}
	
	/**
	 * Removes all Objects from stack.
	 */
	public void clear() {
		collection.clear();
	}

}

package hr.fer.zemris.java.custom.collections;

/**
 * Class that represents stack collection.
 * @author Teo Toplak
 */
public class ObjectStack extends ArrayBackedIndexedCollection{
	
	/**
	 * Method which pushes element from stack
	 * @param value object to be pushed
	 */
	public void push(Object value){
		if(value==null)
			throw new IllegalArgumentException("Provide legal argument");
		add(value);
	}
	
	/**
	 * Method which allows us to see last-stacked element
	 * @return return last-stacked object
	 */
	public Object peek(){
		if(isEmpty())
			throw new EmptyStackException();
		return get(size()-1);	
	}
	
	/**
	 * Method which pops element from stack.
	 * @return last-stacked object
	 */
	public Object pop(){
		Object element= peek();
		remove(size()-1);
		return element;
	}
	
	
}

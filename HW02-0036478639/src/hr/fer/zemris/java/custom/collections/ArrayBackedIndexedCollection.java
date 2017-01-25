package hr.fer.zemris.java.custom.collections;

/**
 * Class that represents resizable array-backed collection.
 * @author Teo Toplak
 *
 *
 */

public class ArrayBackedIndexedCollection {

	/**
	 * Private variable size used for current size of collection (number of elements actually stored).
	 * Private variable capacity used for current capacity of allocated array of object references
	 * Private variable elements used for an array of object references which length is determined by capacity variable
	 */
	private int size=0;
	private int capacity;
	private Object[] elements;
	
	/**
	 * Default constructor - uses default capacity of 16 
	 */
	public ArrayBackedIndexedCollection(){
		capacity=16;
		this.elements=new Object[capacity];
	}

	/**
	 * Constructor with possibility of setting inital capacity 
	 * @param initialCapacity initial capacity of array
	 */
	public ArrayBackedIndexedCollection(int initialCapacity) {
		if(initialCapacity<1){
			throw new IllegalArgumentException("Wrong argument!");
		}
		capacity = initialCapacity;
		this.elements=new Object[capacity];
	}
	
	/**
	 * Method used for checking if selected collection contains no objects
	 * @return return true if array (collection) is empty, otherwise returns false
	 */
	public boolean isEmpty(){
		if(size==0)
			return true;
		return false;
	}
	
	/**
	 * Method which returns the number of currently stored objects in collections
	 * @return number of currently stored objects
	 */
	public int size(){
		return size;
	}
	
	/**
	 * Method which adds the given object into the collection.	 
	 * @param value object intended to be put into collection.
	 */	
	public void add(Object value){
		insert(value,size());
	}
	

	/**
	 * Method used for increasing capacity of collection.
	 * Doubles the capacity when called.
	 */
	private void increaseArray() {
		capacity*=2;
		Object[] elements2=new Object[capacity];
		int count=0;
		for(Object element : elements){
			elements2[count]=element;
			count++;
		}
		elements=elements2;
	}
	
	/**
	 * Method which returns the object that is stored in 
	 * backing array at position given through variable index.
	 * @param index index of wanted Object
	 * @return returns wanted Object
	 */
	public Object get(int index){
		if(!(index<size && index>=0)){
			throw new IllegalArgumentException("Give legal index!"); 
		}
		return elements[index];
	}
	
	/**
	 * Method which removes the object that is stored in backing array at position selected with variable index.
	 * Space created with removing Object will be filled with following Objects in collection by shifting.
	 * @param index index of Object which is about to be removed.
	 */
	public void remove(int index){
		if(!(index<size && index>=0)){
			throw new IllegalArgumentException("Give legal index!"); 
		}
		size-=1;
		while(true){			
			if(index==size){
				elements[index]=null;
				break;
			}
			elements[index]=elements[index+1];
			index++;
		}
	}
	
	/**
	 * Method which inserts the object that is stored in collection at position selected with variable index.
	 * @param value Object that is about to be inserted
	 * @param position position at which Object should be inserted
	 */
	public void insert(Object value, int position){
		if(!(position<=size && position>=0)){
			throw new IllegalArgumentException("Give legal position"); 
		}
		if(size==capacity){
			increaseArray();
		}
		for(int index=size;index>position;index--){
			elements[index]=elements[index-1];
		}
		size++;
		elements[position]=value;
	}
	
	/**
	 * Method which searches the collection and returns the index of the first occurrence
		of the given object.
	 * @param value given Object
	 * @return returns index of Object or -1 if Object is not found.
	 */
	public int indexOf(Object value){
		int index;
		for(index=0;index<size;index++){
			if(value.equals(elements[index])){
				break;
			}
		}
		return index;
	}
	
	/**
	 * Method which returns true only if the collection contains given value.
	 * @param value Object whose presece is questioned.
	 * @return true if Object is present, false otherwise
	 */
	public boolean contains(Object value){
		for(Object element : elements){
			if(value.equals(element))
				return true;
		}
		return false;
	}
	
	/**
	 * Method which removes all elements from the collection.
	 * Method does not resize capacity of collection.
	 */
	public void clear(){
		for(int i=0;i<size;i++){
			elements[i]=null;
		}
		size=0;
	}
}

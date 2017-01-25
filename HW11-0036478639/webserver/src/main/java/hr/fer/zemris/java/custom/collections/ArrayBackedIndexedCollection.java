package hr.fer.zemris.java.custom.collections;

/**
 * Resizable array-backed collections of Objects. 
 * Collection can contain duplicate Objects. It can not contain null Object. 
 * Collection has initial capacity that can be set, default is 16. 
 * If collection is filled to capacity, auto-expands to double the previous size.
 * @author Teo Toplak
 *
 */
public class ArrayBackedIndexedCollection {
	
	/** Current size of collection (number of elements actually stored */
	private int size;
	/** Current capacity of allocated array of object references */
	private int capacity;
	/** An array of object references which length is determined by the capacity variable. */
	Object[] elements;

	/**
	 * Constructs an ArrayBackedIndexedCollection.
	 */
	public ArrayBackedIndexedCollection() {
		this(16);
	}
	
	/**
	 * Constructs an ArrayBackedIndexedCollection with specified initial capacity.
	 * @param initialCapacity initial capacity of collection
	 */
	public ArrayBackedIndexedCollection(int initialCapacity) {
		capacity = initialCapacity;
		elements = new Object[capacity];
	}
	
	/**
	 * Tests if collection is empty.
	 * @return false if empty, true otherwise
	 */
	public boolean isEmpty() {
		if(size == 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * Returns current size of collection.
	 * @return current size of collection, zero if empty
	 */
	public int size() {
		return size;
	}

	/**
	 * Adds Object to the end of collection.
	 * @param value Object to be added
	 * @throws IllegalArgumentException if Object is null
	 */
	public void add(Object value) {
		insert(value, size);
	}
	
	/**
	 * Returns Object from collection on a given index.
	 * @param index index of Object to be retured
	 * @return Object on a given index
	 * @throws IndexOutOfBoundsException if index is less than 0, or greater-or-equal than current size of collection
	 */
	public Object get(int index) {
		if(index <  0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		return elements[index];
	}
	
	/**
	 * Removes Object on a given index from collection.
	 * @param index index
	 * @throws IndexOutOfBoundsException if index is less than 0, or greater-or-equal than current size of collection
	 */
	public void remove(int index) {
		if(index < 0 || index >= size ) {
			throw new IndexOutOfBoundsException();
		}
		size--;
		for(int i = index; i < size; i++) {
			elements[i] = elements[i+1];
		}
	}
	
	/**
	 * Adds Object to the collection on a given position.
	 * @param value Object to be added
	 * @param position position on which Object should be added
	 * @throws IllegalArgumentException if Object is null
	 * @throws IndexOutOfBoundsException if position is less than 0, or greater than current size of collection
	 */
	public void insert(Object value, int position) {
		if(position < 0 || position > size ) {
			throw new IndexOutOfBoundsException();
		}
		if (value == null){
			throw new IllegalArgumentException("Argument value is null.");
		}
		if(size == capacity){
			resizeElements();
		}
		for(int i = size; i > position; i--){
			elements[i] = elements[i-1];
		}
		elements[position] = value;
		size++;
	}
	
	/**
	 * Returns index where a given Object was first found. If there is no occurence of Object, -1 is returned. 
	 * Objects are compared using equals() method. 
	 * @param value Object to be found
	 * @return array index of first occurence of a given Object. -1 if Object was not found
	 */
	public int indexOf(Object value) {
		for(int i = 0; i < size; i++) {
			if (elements[i].equals(value)){
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Tests if collection contains given value. If it does, returns true. Else, returns false. 
	 * The values are compared using equals() method.
	 * @param value value to be tested
	 * @return true if collection contains given value, false otherwise
	 */
	public boolean contains (Object value) {
		if (indexOf(value) == -1){
			return false;
		}
		return true;
	}
	
	/**
	 * Removes all elements from the collection. The allocated array is left at current capacity.
	 */
	public void clear() {
		size = 0;
	}
	
	/**
	 * Doubles the size of elements array and updates capacity variable.
	 */
	private void resizeElements() {
		Object[] newArray = new Object[2*capacity];
		System.arraycopy(elements, 0, newArray, 0, size);
		elements = newArray;
		capacity *= 2;
	}

}

package hr.fer.zemris.java.tecaj.hw4.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Class which represents hashtable.
 * Uses TableEntry class as elements.
 * Resizes after 0.75 percent of capacity is used.
 * Overflow problem is solved with linked lists.
 * @author Teo Toplak
 */

public class SimpleHashtable implements Iterable<SimpleHashtable.TableEntry>{

	/**
	 * size of table
	 */
	private int size;
	
	/**
	 * array of table elements
	 */
	private TableEntry[] table;
	
	/**
	 * variable is used for legal iterating
	 * (if table is modificated variable will change)
	 */
	private int modificationCount;
	
	/**
	 * Constructor with possibility of creating SimpleHashtable without arguments.
	 * Default size is 16.
	 */
	public SimpleHashtable(){
		size=0;
		modificationCount=0;
		table= new TableEntry[16];
	}
	
	/**
	 * Constructor with possibility of creating SimpleHashtable with size as argument.
	 * Size will always be adjusted to the first number divisible with 2. 
	 * @param size starting capacity
	 */
	public SimpleHashtable(int size){
		if(size<1){
			throw new IllegalArgumentException("Illegal hashtable size!");
		}
		while(size % 2 !=0){
			size++;
		}
		this.size=0;
		modificationCount=0;
		table= new TableEntry[size];
	}
	
	/**
	 * Nested class which represents mostly data structure that serves as element in SimpleHastable.
	 */
	public static class TableEntry{
		/**
		 * key of element
		 */
		private Object key;
		
		/**
		 * value of elements
		 */
		private Object value;
		
		/**
		 * reference to next TableEntry (element)
		 */
		private TableEntry next;
		
		/**
		 * Costructor with possibility  of creating TableEntry with key and value given.
		 * @param key key of element
		 * @param value value of elements
		 * @param next reference to next TableEntry (element)
		 */
		public TableEntry(Object key, Object value, TableEntry next) {
			super();
			this.key = key;
			this.value = value;
			this.next = null;
		}

		/**
		 * Getter for value
		 * @return object
		 */
		public Object getValue() {
			return value;
		}

		/**
		 * Setter for value
		 * @param value value
		 */
		public void setValue(Object value) {
			this.value = value;
		}

		/**
		 * Getter for key
		 * @return object
		 */
		public Object getKey() {
			return key;
		}
		
		/**
		 * Method which creates string from element.
		 * Includes key and value.
		 */
		public String toString(){
			return key+"="+value;
		}
		
	}
	
	/**
	 * Method which calculates slot number for particular object.
	 * @param key key
	 * @return integer slot number
	 */
	private int slotNum(Object key){
		return Math.abs(key.hashCode()) % table.length;
	}
	
	/**
	 * Method which returns size of table.
	 * @return int size
	 */
	public int size(){
		return size;
	}
	
	/**
	 * Method which returns true if table contains key given.
	 * @param key key
	 * @return true if table does contain key given, false otherwise
	 */
	public boolean containsKey(Object key){
		
		int slotNum=slotNum(key);
		if(table[slotNum]==null){
			return false;
		}
		TableEntry currentTable = table[slotNum];
		do{
			if(currentTable.getKey().equals(key)){
				return true;
			}
			currentTable=currentTable.next;
		}while(currentTable!=null);
		
		return false;
	}
	
	/**
	 * Method which returns true if table contains value given.
	 * @param value value
	 * @return true if table does contain value given, false otherwise
	 */
	public boolean containsValue(Object value){
		
		for(TableEntry currentTable : table){
			while(currentTable!=null){
				if(currentTable.getValue()==null){
					if(value==null){
						return true;
					}
				}
				else{
					if(currentTable.getValue().equals(value)){
						return true;
					}
				}
				currentTable=currentTable.next;
			}
		}
		return false;
	}
	
	/**
	 * Method which returns true if table is empty
	 * @return true if table is empty
	 */
	public boolean isEmpty(){
		if(size==0){
			return true;
		}
		return false;
	}
	
	/**
	 * Method which puts new element in table with key and value provided.
	 * @param key key
	 * @param value value
	 * @throws IllegalArgumentException if key is null
	 */
	public void put(Object key, Object value){
		
		if(key==null){
			throw new IllegalArgumentException("Illegal key input");
		}
		modificationCount++;
		int slotNum=slotNum(key);
		TableEntry currentTable = table[slotNum];
		if(currentTable==null){
			TableEntry newTable= new TableEntry(key,value,null);
			table[slotNum]=newTable;
			size++;
			if(isSaturated()){
				enlarge();
			}
			return;
		}
		while(true){
			if(currentTable.getKey().equals(key)){
				currentTable.setValue(value);
				return;
			}
			if(currentTable.next==null){
				break;
			}
			currentTable=currentTable.next;
		}
		TableEntry newTable= new TableEntry(key,value,null);
		currentTable.next=newTable;
		size++;
		if(isSaturated()){
			enlarge();
		}
	}
	
	/**
	 * Method which returns element in table with key provided.
	 * @param key key
	 * @return object element
	 */
	public Object get(Object key){
		
		if(!this.containsKey(key)){
			return null;
		}
		
		int slotNum=slotNum(key);
		TableEntry currentTable = table[slotNum];
		while(true){
			if(currentTable.getKey().equals(key)){
				return currentTable.getValue();
			}
			currentTable=currentTable.next;
		}
	}
	
	/**
	 * Method which removes element in table with key provided.
	 * @param key key
	 */
	public void remove(Object key){
		
		if(!this.containsKey(key)){
			return;
		}
		modificationCount++;
		size--;
		int slotNum=slotNum(key);
		TableEntry currentTable = table[slotNum];	
		if(currentTable.getKey().equals(key)){
			table[slotNum]=currentTable.next;
			return;
		}
		while(true){
			if(currentTable.next.getKey().equals(key)){
				currentTable.next=currentTable.next.next;
				return;
			}
			currentTable=currentTable.next;
		}
	}
	
	/**
	 * Method which returns string that represents current state of table.
	 * Example:"[key1=value1, key2=value2, key3=value3 ...]"
	 */
	public String toString(){
		
		StringBuilder string = new StringBuilder("[ ");
		
		for(TableEntry currentTable : table){
			if(currentTable!=null){
				do{
					string.append(currentTable.toString());
					string.append(" ");
					currentTable=currentTable.next;
				}while(currentTable!=null);
			}
		}
		string.append("]");
		return string.toString();
	}
	
	/**
	 * Method which clears table completely.
	 */
	public void clear(){
		size=0;
		modificationCount++;
		for(int i=0;i<table.length;i++){
			table[i]=null;
		}
	}
	
	/**
	 * Method which is used for checking if table's capacity is filled more than 75 percent.
	 * @return true if resizing is needed
	 */
	private boolean isSaturated(){
		if((double) size / table.length >= 0.75){
			return true;
		}
		return false;
	}
	
	/**
	 * Method which resizes table's capacity by double.
	 */
	private void enlarge(){
		modificationCount++;
		SimpleHashtable newHashtable= new SimpleHashtable(table.length*2);
		for(TableEntry pair : this){
			newHashtable.put(pair.key, pair.value);
		}
		table=newHashtable.table;
	}
	
	/**
	 * Factory method for creating iterator used in SimpleHashtable
	 */
	public Iterator<SimpleHashtable.TableEntry> iterator(){
		return new IteratorImpl(modificationCount); 
	}
	
	/**
	 * * Class which allows user to loop through hashtable using iterators.
	 */
	private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry> {
		
		/*
		 * Used for checking if table was modified while iterating 
		 */
		private int modificationCountOld;
		
		/**
		 * current slot position
		 */
		private int slotPosition;
		
		/**
		 * current element position
		 */
		private TableEntry currentTable;
		
		/**
		 * last element position
		 * (used in removing element from table)
		 */
		private TableEntry lastTable;
		
		/**
		 * last slot position
		 * (used in removing element from table)
		 */
		private int lastSlotPosition;
		
		/**
		 * Constructor with possibility of creating iterator witch demands current modificationCount from table.
		 * @param modificationCountOld modificationCount
		 */
		public IteratorImpl(int modificationCountOld) {
			this.modificationCountOld = modificationCountOld;
			slotPosition=-1;
		}
		
		/**
		 * Method which returns true if there is more elements left to iterate in table.
		 * @return true if there is more elements to iterate 
		 * @throws ConcurrentModificationException if collection has been modified while iterating
		 */
		public boolean hasNext() {
			if(modificationCount!=modificationCountOld){
				throw new ConcurrentModificationException("Collection has been modified while iterating!");
			}
			
			for(int i=slotPosition+1;i<table.length;i++){
				if(table[i]!=null){
				return true;
				}
			}	
			try{
				if(currentTable.next!=null){
					return true;			
				}
			}catch(NullPointerException e){
				// in case all elements are deleted .next method will throw exception
			}
		return false;
		}
		
		/**
		 * Method which returns next element from table.
		 * @returns TableEntry element
		 * @throws ConcurrentModificationException if collection has been modified while iterating
		 * @throws NoSuchElementException if there is no elements to return 
		 */
		public SimpleHashtable.TableEntry next() {
			if(modificationCount!=modificationCountOld){
				throw new ConcurrentModificationException("Collection has been modified while iterating!");
			}
	
			//if this is first iteration
			if(currentTable==null){
				for(int i=0;i<table.length;i++){
					if(table[i]!=null){
						lastTable=currentTable;
						lastSlotPosition=slotPosition;
						slotPosition=i;
						currentTable=table[i];
						return table[i];
					}
				}
				throw new NoSuchElementException("No elements to return!"); 
			}
			//if there is more elements in same slot
			if(currentTable.next!=null){
				lastTable=currentTable;
				lastSlotPosition=slotPosition;
				currentTable=currentTable.next;
				return currentTable;
			}
			
			//if this is last element in slot
			for(int i=slotPosition+1;i<table.length;i++){
				if(table[i]!=null){
					lastSlotPosition=slotPosition;
					lastTable=currentTable;
					slotPosition=i;
					currentTable=table[i];
					return table[i];
				}
			}
			throw new NoSuchElementException("No elements to return!"); 			
		}
		
		/**
		 * Method which removes current iterated element from table.
		 * @throws ConcurrentModificationException if collection has been modified while iterating
		 * @throws NoSuchElementException if there is no elements to return 
		 * @throws IllegalStateException if remove method is called twice with iterator
		 */
		public void remove() {
			if(modificationCount!=modificationCountOld){
				throw new ConcurrentModificationException("Collection has been modified while iterating!");
			}
			if(currentTable==null){
				throw new NoSuchElementException("Method \"remove\" has been illegaly called!");
			}
			if(currentTable==lastTable){
				throw new IllegalStateException("Remove method called twice with iterator!");
			}
			modificationCountOld++;
			TableEntry tableToDelete=currentTable;
			currentTable=lastTable;
			slotPosition=lastSlotPosition;
			SimpleHashtable.this.remove(tableToDelete.key);
		}
	}
}

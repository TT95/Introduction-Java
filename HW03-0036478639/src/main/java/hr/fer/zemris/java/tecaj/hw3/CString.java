package hr.fer.zemris.java.tecaj.hw3;

/**
 * Class representing old String implementation in Java.
 * Idea of implementation is for multiple instances of strings to share a single character array and to remember 
   which part of the array belongs to each instance.
 * Strings are unmodifiable.
 * @author Teo Toplak
 */
public class CString {
	
	/**
	 * character array of string
	 */
	private char[] data;
	/**
	 * offset of current string data compared to begining of character array really stored
	 */
	private int offset;
	/**
	 * length of string
	 */
	private int length;

	/**
	 * Constructor with possibility of creating CString with char array, offset and length of string given
	 * @param data char array
	 * @param offset offset of string
	 * @param length length of string
	 */
	public CString(char[] data, int offset, int length){
		this.data = data;
		this.length=length;
		this.offset=offset;
	}
	
	/**
	 * Constructor with possibility of creating CString with char array
	 * @param data char array
	 */
	public CString(char[] data){
		this.data = data;
		this.length=data.length;
		this.offset=0;
	}
	
	/**
	 * Constructor with possibility of creating CString with another CString.
	 * If originals internal character array is larger than needed, new instance will
	   allocate its own character array of minimal required size and copy data, otherwise it
	   will reuse original's character array.
	 * @param original CString
	 */
	public CString(CString original){
		
		if(original.length < original.data.length){
			data = new char[original.length];
			for(int index=0;index<original.length;index++){
				data[index]=original.data[original.offset+index];
			}
			this.length=original.length;
			this.offset=0;
		}
		else{
			this.data=original.data;
			this.length=original.length;
			this.offset=original.offset;
		}
	}
	
	/**
	 * Constructor with possibility of creating CString with given string
	 * @param s string
	 */
	public CString(String s){
		data = s.toCharArray();
		this.length=s.length();
		this.offset=0;
	}
	
	/**
	 * Method which returns length of CString
	 * @return int length
	 */
	public int length(){
		return length;
	}
	
	/**
	 * Method which returns char at specified position in CString
	 * @param index of char in string
	 * @return char char
	 */
	public char charAt(int index){
		
		if(index+offset>=length){
			throw new IllegalArgumentException("Illegal index (out of bounds)");
		}
		return data[index+offset];
	}
	
	/**
	 * Method which allocates a new array, copies string content into it and returns it
	 * @return char[] char array
	 */
	public char[] toCharArray(){
		char[] newArray=new char[length];
		System.arraycopy(data, offset, newArray, 0, length);
		return newArray;
	}
	
	/**
	 * Method which returns CString converted into String
	 * @return string string
	 */
	public String toString(){
		String string="";
		for(int index=offset;index<offset+length;index++){
			string+=Character.toString(data[index]);
		}
		return string;
	}
	
	/**
	 * Method which returns position of specified char in CString
	 * @param c char
	 * @return int index of char in CString
	 */
	public int indexOf(char c){

		for(int index=offset;index<length+offset;index++){
			if(data[index]==c){
				return index;
			}
		}
		return -1;
	}
	
	/**
	 * Method which returns true if CString starts with CString given.
	 * @param s CString
	 * @return boolean
	 */
	public boolean startsWith(CString s){
		
		if(s.length>length){
			return false;
		}
		if(this.substring(0,s.length).isEqual(s)){
			return true;
		}
		return false;
	}
	
	/**
	 * Method which returns true if CString ends with CString given.
	 * @param s CString
	 * @return boolean
	 */
	public boolean endsWith(CString s){
		if(length<s.length){
			return false;
		}
		if(this.substring(length-s.length,length).isEqual(s)){
			return true;
		}
		return false;
	}
	
	/**
	 * Method which returns true if CString contains CString given.
	 * @param s CString
	 * @return boolean
	 */
	public boolean contains(CString s){
		
		if(s.length > length){
			return false;
		}
		for(int count=offset;count<=offset+length-s.length;count++){
			if(this.substring(count,s.length+count).isEqual(s)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Method which returns substring of CString.
	 * Important: If user requests substring that is out of bounds (startIndex bigger than data length), method won't
	   throw exception, but will leave CString as empty ("")!
	 * Position endIndex does not belong to the substring.
	 * @throws IllegalArgumentException 
	 */
	public CString substring(int startIndex, int endIndex){
		if(endIndex<startIndex || startIndex<0){
			throw new IllegalArgumentException("Substring out of bounds!");
		}
		return new CString(data,offset+startIndex,endIndex-startIndex);
	}
	
	/**
	 * Method which returns true if CString is equal to CString given.
	 * @param s CString
	 * @return boolean
	 */
	public boolean isEqual(CString s){
		if(s.length!=length){
			return false;
		}
		for(int index=offset;index<offset+length;index++){
			char a=s.data[index-offset+s.offset];
			char b=data[index];
			if(a!=b){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Method which returns new CString which represents starting part of original string and is of length n.
	 * @param n length
	 * @return CString
	 * @throws IllegalArgumentException
	 */
	public CString left(int n){
		if(n<0){
			throw new IllegalArgumentException("Argument must be >=0!");
		}
		return new CString(this.substring(0,n));
	}
	
	/**
	 * Method which returns new CString which represents starting part of original string and is of length n.
	 * @param n length
	 * @return IllegalArgumentException
	 */
	public CString right(int n){
		if(n<0){
			throw new IllegalArgumentException("Argument must be >=0!");
		}
		return new CString(this.substring(length-n,length));
	}
	
	/**
	 * Method which creates a new CString which is concatenation of current and given string.
	 * @param s CString
	 * @return CString
	 */
	public CString add(CString s){
		char[] newArray= new char[s.length+length];
		int count=0;
		for(int index=offset;index<offset+length;index++){
			newArray[count]=data[index];
			count++;
		}
		for(int index=s.offset;index<s.offset+s.length;index++){
			newArray[count]=s.data[index];
			count++;
		}
		return new CString(newArray);
	}
	
	/**
	 * Method which creates a new CString in which each occurrence of old character is replaces with new character.
	 * @param oldChar old char
	 * @param newChar new char
	 * @return
	 */
	public CString replaceAll(char oldChar, char newChar){
		
		CString newCString= new CString(this);
		for(int index=newCString.offset;index<newCString.offset+newCString.length;index++){
			if(newCString.data[index]==oldChar){
				newCString.data[index]=newChar;
			}
		}
		return newCString;
	}
	
	/**
	 * Method which creates a new CString in which each occurrence of old substring is replaces 
	   with the new substring.
	 * @param oldStr old CString
	 * @param newStr new CString
	 * @return CString
	 */
	public CString replaceAll(CString oldStr, CString newStr){
		
		char[] empty = {};
		CString finalString = new CString(empty);
		int lastCut=0;		
		for(int count=offset;count<=offset+length-oldStr.length;count++){
			if(this.substring(count,oldStr.length+count).isEqual(oldStr)){
				finalString = finalString.add(substring(lastCut,count));
				finalString = finalString.add(newStr);
				lastCut=count+oldStr.length;
				count=lastCut-1;
			}
		}
		finalString=finalString.add(substring(lastCut,this.length));
		return finalString;
	}
}


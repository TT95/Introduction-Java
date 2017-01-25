package hr.fer.zemris.java.custom.scripting.parser;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hr.fer.zemris.java.custom.scripting.nodes.*;
import hr.fer.zemris.java.custom.collections.*;
import hr.fer.zemris.java.custom.scripting.tokens.*;

/**
 * Parser for characteristic structured document format with {$ ... $} tags.
 * Parsing is done through several methods: first it uses parsingMethod to seperate text and tags 
 * into array (separation is done with extractTagsAndText method).Each element of 
 * array is than given to method createNode which after determing appropriate node calls its
 * method (i.e. createTextNode) which then creates tokens and document model.
 * @author Teo Toplak
 */

public class SmartScriptParser {

	/**
	 * stack and starting node of document model - documentNode
	 */
	private DocumentNode documentNode;
	private ObjectStack stack;
	
	/**
	 * Calls SmartScriptParser and initiates parsing, starting with parsingMethod
	 * @param string string
	 */
	public SmartScriptParser(String string){
		parsingMethod(string);
	}
	
	/**
	 * DocumentNode getter
	 * @return document node
	 */
	public DocumentNode getDocumentNode(){
		return documentNode;
	}
	
	/**
	 * Method which creates nodes for each text or tag from parameter string.
	 * Also creates document node and stack.
	 * @param string string
	 */
	private void parsingMethod(String string){
		
		ArrayBackedIndexedCollection array = new ArrayBackedIndexedCollection();
		stack = new ObjectStack();
		documentNode = new DocumentNode("");
		stack.push(documentNode);
		
		//seperate tags and text
		array = extractTagsAndText(string);
		
		//for each text  or tag create new node
		int arraySize = array.size();
		for(int index=0;index<arraySize;index++){
			createNode((String)array.get(index));
		}
		
	}
	/**
	 * Method which seperates text and tags into array
	 * @param document string for parsing
	 * @return ArrayBackedIndexedCollection with stored texts and tags seperated.
	 */
	private ArrayBackedIndexedCollection extractTagsAndText(String document) {
		ArrayBackedIndexedCollection tagsAndText = new ArrayBackedIndexedCollection();

		// creating regex for {$ ... $} tags
		// regex also treats every sequence after \ literally as written
		Pattern tagPattern = Pattern.compile("(?<!\\\\)\\{\\$.*?\\$\\}");
		Matcher tagMatcher = tagPattern.matcher(document);

		// current position in document
		int startPoint = 0;

		// while document contains specified pattern
		while (tagMatcher.find()) {
			String tag = tagMatcher.group();

			// text is before tag
			if (tagMatcher.start() != startPoint) {
				String text = document
						.substring(startPoint, tagMatcher.start());
				tagsAndText.add(text);
			}

			tagsAndText.add(tag);
			startPoint = tagMatcher.end();
		}

		// checking if there is some text at ending
		if (startPoint < document.length()) {
			tagsAndText.add(document.substring(startPoint));
		}

		return tagsAndText;
	}
	
	/**
	 * Method for determing and using characheristic method for each node
	 * which then creates tokens and document model.
	 * @param originalText string representing text or tag
	 */
	private void createNode(String originalText){
		//using this string and modifying it for node determination
		String modifiedText; 
		
		/*given text is textNode if its length<=1, this line exists for using substring(0,2)
		later without exception and faster code */
		if(originalText.length() >1){
			
			//checking if string is text or tag
			if(originalText.substring(0,2).equals("{$")){
				
				//removing tags "{$ $}", trimming afterwards
				modifiedText=originalText.substring(2,originalText.length()-2);
				modifiedText=modifiedText.trim(); 

				String[] array = modifiedText.split("\\s+");

				//for loop node? (does it contain for)
				if(array[0].toLowerCase().equals("for")){
					createForLoopNode(array);
					return;
				}

				//end node?
				else if(array[0].toLowerCase().equals("end")){
					EndNode(array);
					return;
				}

				//echo node?
				else if(modifiedText.substring(0,1).equals("=")){
					//because parser must exept =i for exp.
					if(array[0].length()==1){
						createEchoNode(array,true);
					}
					else{
						String[] newArray= array;
						newArray[0]=newArray[0].substring(1,newArray[0].length());
						createEchoNode(newArray,false);
					}
					
					return;
				}

				throw new SmartScriptParserException("Input isn't legal.");
			}
		}	
		//text node
		//also trimms text
		originalText.replaceAll("^\\s+", "");
		createTextNode(originalText);
		
		
	} 

	/**
	 * Method for creating text node.
	 * Also adds it as a child to last node.
	 * @param originalText string representing text or tag
	 */
	private void createTextNode(String originalText) {
		
		TextNode node= new TextNode(originalText);
		Node node2 = (Node)stack.get(stack.size()-1);
		node2.addChildNode((Node)node);
	}

	/**
	 * Method called when end tag is detected.
	 * Removes last node from stack (should be for loop node).
	 * @param array array which was modified and should contain only one element ("end")
	 */
	private void EndNode(String[] array) {
		
		//if array contains more than just "end" (existence of "end" was checked in method before)
		if(array.length!=1)
			throw new SmartScriptParserException("END-tag isn't legal");
		stack.pop();
		
	}

	/**
	 * Method for creating echo node.
	 * Also adds it as a child to last node.
	 * Uses array parameter for conversion to token(s).
	 * @param array array containing each element in tag separately
	 * @param more if there is variable merged to "=" - false
	 */
	private void createEchoNode(String[] array,boolean more) {
		int raise;
		if(more==true){
			raise=1;
		}
		else{
			raise=0;
		}
		Token[] tokenArray= new Token[array.length-raise];
		for(int index=raise;index<array.length;index++){
			tokenArray[index-raise]=stringToToken(array[index]);
		}
		EchoNode node = new EchoNode(tokenArray);
		Node node2 = (Node)stack.get(stack.size()-1);
		node2.addChildNode((Node)node);
		
	}

	/**
	 * Method for creating for loop node.
	 * Also adds it as a child to last node.
	 * Uses array parameter for conversion to token(s).
	 * @param array array containing each element in tag separately
	 */
	private void createForLoopNode(String[] array) {
		
		ForLoopNode node;
		
		//first token must be token variable, else throw exception
		if(!(stringToToken(array[1]) instanceof TokenVariable))
			throw new SmartScriptParserException("Input isn't legal.");
		
		//costructor for node with 3 tokens
		if(array.length==4)
			node = new ForLoopNode((TokenVariable)stringToToken(array[1]),
					stringToToken(array[2]),stringToToken(array[3]));
		
		//costructor for node with 4 tokens
		else if(array.length==5)
			node = new ForLoopNode((TokenVariable)stringToToken(array[1]),
					stringToToken(array[2]),stringToToken(array[3]),stringToToken(array[4]));
		else
			throw new SmartScriptParserException("Input isn't legal.");
		
		//if last node on stack was for loop node throw exception
		if(stack.get(stack.size()-1) instanceof ForLoopNode)
			throw new SmartScriptParserException("Input isn't legal.");
		Node node2 = (Node)stack.get(stack.size()-1);
		node2.addChildNode((Node)node);
		stack.push(node);
	}
	
	/**
	 * Method which converts string to appropriate token.
	 * @param string string about to be converted into token.
	 * @return Token token which is represented by appropriate string
	 */
	private Token stringToToken(String string){
		
		//is it TokenConstantDouble?
		if(isDouble(string))
			return new TokenConstantDouble(Double.parseDouble(string));
		//is it TokenConstantInteger?
		if(isInteger(string))
			return new TokenConstantInteger(Integer.parseInt(string));
		//is it TokenOperator?
		switch(string){
			case("+"): return new TokenOperator(string);
			case("-"): return new TokenOperator(string);
			case("/"): return new TokenOperator(string);
			case("*"): return new TokenOperator(string);
			case("%"): return new TokenOperator(string); 
		}
		//is it TokenString?
		if(string.substring(0,1).equals("\"") && string.substring(string.length()-1,string.length()).equals("\""))
			return new TokenString(string);
		String stringA=string.substring(0,1);
		//is it TokenFunction? 
		if(stringA.equals("@"))
			return new TokenFunction(string.substring(1,string.length()));
		//is it TokenVariable?
		if(stringA.matches("[a-zA-Z]+"))
			return new TokenVariable(string);
		else throw new SmartScriptParserException("Input isn't legal.");
	}
	
	/**
	 * Method for checking if a given string is integer.
	 * @param string string that represents integer number
	 * @return returns true if string is an integer, false otherwise
	 */
	public boolean isInteger( String input ) {
	    try {
	        Integer.parseInt( input );
	        return true;
	    }
	    catch( Exception e ) {
	        return false;
	    }
	}
	
	/**
	 * Method for checking if a given string is double.
	 * @param string string that represents double number
	 * @return returns true if string is a double, false otherwise
	 */
	public boolean isDouble( String input ) {
	    try {
	        Double.parseDouble( input );
	        return true;
	    }
	    catch( Exception e ) {
	        return false;
	    }
	}
	
}

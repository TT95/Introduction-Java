package hr.fer.zemris.java.custom.scripting.parser;

import hr.fer.zemris.java.custom.collections.ArrayBackedIndexedCollection;
import hr.fer.zemris.java.custom.collections.ObjectStack;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.tokens.Token;
import hr.fer.zemris.java.custom.scripting.tokens.TokenConstantDouble;
import hr.fer.zemris.java.custom.scripting.tokens.TokenConstantInteger;
import hr.fer.zemris.java.custom.scripting.tokens.TokenFunction;
import hr.fer.zemris.java.custom.scripting.tokens.TokenOperator;
import hr.fer.zemris.java.custom.scripting.tokens.TokenString;
import hr.fer.zemris.java.custom.scripting.tokens.TokenVariable;

/**
 * Parses document and creates document model made out of nodes and tokens.
 * @author Teo Toplak
 *
 */
public class SmartScriptParser {
	
	/**
	 * Character array in which document to be parsed is stored.
	 */
	private char[] data;
	/**
	 * Marks the current position of parser in a character array.
	 */
	private int currentPosition;
	/**
	 * Root node of a document model that parser is building.
	 */
	private DocumentNode documentNode;
		
	/**
	 * Class constructor
	 * @param document document to be parsed
	 */
	public SmartScriptParser(String document) {

		try {
			data = document.toCharArray();
		} catch (NullPointerException e){
			throw new SmartScriptParserException("File string is null.");
		}
		currentPosition = 0;
		parser();
	}
	
	/**
	 * Returns root node of a document model that parser has built.
	 * @return root node af a document model
	 */
	public DocumentNode getDocumentNode() {
		return documentNode;
	}
	
	/**
	 * Parses document and builds document model consisting of nodes and tokens.
	 * @throws SmartScriptParserException if document can't be successfully parsed with appropriate error message
	 */
	private void parser(){
		
		documentNode = new DocumentNode();
		ObjectStack nodeStack = new ObjectStack();
		
		nodeStack.push(documentNode);
		
		while(currentPosition<data.length) {
			Node parentNode = (Node)nodeStack.peek();
			Node currentNode = getNode();
			if(currentNode == null) {
				nodeStack.pop();
				if(nodeStack.isEmpty()) {
					throw new SmartScriptParserException("Too many END tags.");
				}
			}
			else {
			parentNode.addChildNode(currentNode);
			}
			if(currentNode instanceof ForLoopNode) {
				nodeStack.push(currentNode);
			}
		}
		
		nodeStack.pop();
		if(!nodeStack.isEmpty()){
			throw new SmartScriptParserException("Some tags are not closed, or closed improperly.");
		}
	}
	
	/**
	 * Gets next node of a document.
	 * @return next node parsed from document 
	 */
	private Node getNode(){
		
		Node currentNode = null;

		if(currentPosition < data.length-1 && data[currentPosition] == '{' && data[currentPosition+1] == '$') {
			currentPosition +=2;
			try {
				currentNode = getTagNode();
			} catch (ArrayIndexOutOfBoundsException e){
				throw new SmartScriptParserException("Unexpected end of file.");
			}
		}
		else {
			try {
				currentNode = getTextNode();
			} catch (ArrayIndexOutOfBoundsException e){
				throw new SmartScriptParserException("Unexpected end of file.");
			}
		}		
		
		return currentNode;
	}
	
	/**
	 * Gets text node from document.
	 * @return text node parsed from document
	 */
	private Node getTextNode(){
		
		int startPosition = currentPosition;
		StringBuilder text = new StringBuilder();
		
		//guards against file-end with tag-start or escaped characters (2 chars)
		while(currentPosition < data.length-1) {
			//if not tag-start
			if (data[currentPosition] != '{' || data[currentPosition+1] != '$'){
				//if escaped char
				if(data[currentPosition] == '\\' && 
						(data[currentPosition+1] == '\\' || data[currentPosition+1] == '{')) {
					//append text before escaped char
					text.append(data, startPosition, currentPosition-startPosition);
					//skip escaping char ( \ ) and reset beginning
					currentPosition++;
					startPosition = currentPosition;
				}
			}
			//if tag-start found, break and end textNode
			else {
				break;
			}
			//normal step for one char
			currentPosition++;
		}
		//collect last char in file
		if(currentPosition == data.length-1){
			currentPosition++;
		}
		
		//append the rest of text
		text.append(data, startPosition, currentPosition-startPosition);
		Node textNode = new TextNode(text.toString());

		return textNode;

	}
	
	/**
	 * Gets tag node from document.
	 * @return tag node parsed from document
	 */
	private Node getTagNode(){
		
		Token currentToken = null;		
		ArrayBackedIndexedCollection tokenList = new ArrayBackedIndexedCollection();
		
		skipBlanks();
		String tagName = getTagName();
		skipBlanks();
		
		while(data[currentPosition] != '$'){
			currentToken = getToken();
			tokenList.add(currentToken);
			skipBlanks();
		}
		
		Node tagNode = null;
		
		//tag-checking for valid tag-name, valid number and type of tokens
		if(tagName.toUpperCase().equals("FOR")){
			int tokenListSize = tokenList.size();
			//first token of FOR tag must be of type variable
			if(!(tokenList.get(0) instanceof TokenVariable)){
				throw new SmartScriptParserException("First token of FOR tag must be variable.");
			}

			for(int i=1; i < tokenListSize; i++){
				//Token of FOR tag can not be of type function
				if(tokenList.get(i) instanceof TokenFunction){
					throw new SmartScriptParserException("Token of FOR tag can not be function.");
				}
				//String Token of FOR tag must be convertible to Int or Double
				if(tokenList.get(i) instanceof TokenString){
					TokenString forLoopStringToken = (TokenString)tokenList.get(i);
					String forLoopString = forLoopStringToken.getValue();
					try{
						Integer.parseInt(forLoopString);
					} catch (NumberFormatException e){
						try {
							Double.parseDouble(forLoopString);
						} catch (NumberFormatException f){
							throw new SmartScriptParserException("TokenString of FOR tag must be resolvable to integer or double.");
						}
					} catch (NullPointerException e){
						throw new SmartScriptParserException("TokenString of FOR tag must be resolvable to integer or double.");
					}
				}
			}
			
			if(tokenList.size() == 4){
				tagNode = new ForLoopNode(	(TokenVariable)tokenList.get(0),
											(Token)tokenList.get(1), 
											(Token)tokenList.get(2), 
											(Token)tokenList.get(3)
						);
			} else if(tokenList.size() == 3) {
				tagNode = new ForLoopNode(	(TokenVariable)tokenList.get(0),
											(Token)tokenList.get(1), 
											(Token)tokenList.get(2)
						);
			} else {
				throw new SmartScriptParserException("Illegal number of FOR tag tokens.");
			}
		} else if(tagName.toUpperCase().equals("END")) {
			//END tag has no tokens
			if(tokenList.size() != 0) {
				throw new SmartScriptParserException("End tag has no tokens");
			}
			tagNode = null;
		} else if(tagName.equals("=")){
			//copy from tokenList collection to array
			Token[] tokens = new Token[tokenList.size()];
			for(int i=0; i < tokenList.size(); i++) {
				tokens[i] = (Token)tokenList.get(i);
			}
			tagNode = new EchoNode(tokens);
		} else {
			throw new SmartScriptParserException("Uknown tag name.");
		}
		
		//assure of proper tag closing
		if(currentPosition >= data.length-1) {
			throw new SmartScriptParserException("Unexpected end of file.");
		} 
		if(data[currentPosition+1] != '}'){
			throw new SmartScriptParserException("Improperly closed tag.");
		}
		//skip tag closing
		currentPosition +=2;
		//null if END tog
		return tagNode;

	}
	
	/**
	 * Gets token from document.
	 * @return token from document
	 * @throws SmartScriptParserException if token is illegal
	 */
	private Token getToken(){
		
		char c = data[currentPosition];
		Token currentToken = null;
		
		if(c == '@'){
			currentToken = getFunctionToken();
		} else if(c == '"') {
			currentToken = getStringToken();
		} else if(Character.isLetter(c)) {
			currentToken = getVariableToken();
		} else if(Character.isDigit(c)) {
			currentToken = getConstantNumberToken();
		} else if(c == '-') {
			if (Character.isDigit(data[currentPosition+1])) {
				currentToken = getConstantNumberToken();
			} else {
				currentToken = getOperatorToken();
			}
		} else {
			switch (c) {
			case '+':
			case '-':
			case '*':
			case '/':
			case '%':
				currentToken = getOperatorToken();
				break;
			case '}':
				throw new SmartScriptParserException("Improperly closed tag.");
			default:
				throw new SmartScriptParserException("Illegal tag content.");
			}
		}
		
		return currentToken;
		
	}
	
	/**
	 * Gets variable token from document.
	 * @return variable token parsed from document
	 */
	private Token getVariableToken(){
		
		String name = getVariableName();
		Token token = new TokenVariable(name);
		return token;
		
	}
	
	/**
	 * Gets function token from document
	 * @return function token parsed from document
	 */
	private Token getFunctionToken(){
		
		currentPosition++;
		String name = getVariableName();
		Token token = new TokenFunction(name);
		return token;
		
	}
		
	/**
	 * Gets operator token from document.
	 * @return operator token parsed from document
	 */
	private Token getOperatorToken(){
		String symbol = Character.toString(data[currentPosition]);
		currentPosition++;
		Token token = new TokenOperator(symbol);
		return token;
		
	}
	
	/**
	 * Gets constant number token from document.
	 * @return constant number token parsed from document, either integer token or double token depending on number type
	 */
	private Token getConstantNumberToken(){
		
		int startPosition = currentPosition;
		boolean doubleFlag = false;
		char c = data[currentPosition];
		if(c == '-'){
			currentPosition++;
		}
		c = data[currentPosition];
		
		while(Character.isDigit(c) || c == '.') {
			if(c == '.') {
				if(doubleFlag){
					break;
				} else {
					doubleFlag = true;
				}
			}
			currentPosition++;
			c = data[currentPosition];
		}
		
		String numberString = new String(data, startPosition, currentPosition-startPosition);
		Token token = null;
		
		if(doubleFlag) {
			token = new TokenConstantDouble(Double.parseDouble(numberString));
		} else {
			token = new TokenConstantInteger(Integer.parseInt(numberString));
		}
		
		return token;

	}
	
	/**
	 * Gets string token from document.
	 * @return string token parsed from document
	 * @throws SmartScriptParserException if end of file reached
	 */
	private Token getStringToken(){
		
		//step over string start ( " )
		currentPosition++;
		int startPosition = currentPosition;
		StringBuilder string = new StringBuilder();
		
		//guards against file-end with tag-start or escaped characters (2 chars)
		while(currentPosition < data.length-1) {
			//if not string-end
			if (data[currentPosition] != '"'){
				//if escaped char
				if(data[currentPosition] == '\\') {
					//de-escaping \n, \r, \t
					char c = data[currentPosition+1];
					if(c == '\\' || c == '"' || c=='n' || c=='r' || c=='t') {
						if(c == 'n') {
							data[currentPosition+1] = '\n';
						} else if(c == 'r'){
							data[currentPosition+1] = '\r';
						} else if(c == 't') {
							data[currentPosition+1] = '\t';
						} else {
						}
						//append text before escaped char
						string.append(data, startPosition, currentPosition-startPosition);
						//skip escaping char ( \ ) and reset beginning
						currentPosition++;
						startPosition = currentPosition;
					}
				}
			}
			//if tag-start found, break and end textNode
			else {
				break;
			}
			//normal step for one char
			currentPosition++;
		}
		//check if last char in file.
		if(currentPosition == data.length-1){
			if(data[currentPosition] != '"') {
					throw new SmartScriptParserException("End of file.");
			}
		}		
		//append the rest of text
		string.append(data, startPosition, currentPosition-startPosition);
		Token token = new TokenString(string.toString());
	
		//step over string close ( " )
		currentPosition++;
		
		return token;
		
	}
	
	/**
	 * Reads and returns valid variable name from document.
	 * @return valid variable name
	 */
	private String getVariableName(){
		
		if(!Character.isLetter(data[currentPosition])){
			throw new  SmartScriptParserException("Illegal variable name.");
		}
		int nameStart = currentPosition;
		currentPosition++;
		
		while(	Character.isLetterOrDigit(data[currentPosition]) ||
				Character.valueOf(data[currentPosition]).equals('_')
		){
			currentPosition++;
			if(currentPosition >= data.length){
				throw new SmartScriptParserException("Unexpected end of file.");
			}
		}
		
		String variableName = new String(data, nameStart, currentPosition-nameStart);
		return variableName;
		
	}
	
	/**
	 * Reads and returns valid tag name from document.
	 * @return valid tag name
	 * @throws SmartScriptParserException if tag name illegal
	 */
	private String getTagName(){
		
		if(Character.valueOf(data[currentPosition]).equals('=')){
			currentPosition++;
			return "=";
		} else if(Character.isLetter(data[currentPosition])) {
			try{
				return getVariableName();
			} catch (SmartScriptParserException e){
				throw new SmartScriptParserException("Illegal tag name.");
			}
		} else {
			throw new SmartScriptParserException("Illegal tag name.");
		}
		
	}
	
	/**
	 * Skips blanks in a document.
	 */
	private void skipBlanks(){
		
		while(currentPosition<data.length){
			char c = data[currentPosition];
			if(c == ' ' || c == '\t' || c == '\r' || c == '\n'){
				currentPosition++;
				continue;
			}
			break;
		}
	}
		
}

package hr.fer.zemris.java.custom.scripting.exec;

import java.io.IOException;
import java.util.Stack;

import hr.fer.zemris.java.custom.scripting.exec.functions.FunctionsMap;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.tokens.Token;
import hr.fer.zemris.java.custom.scripting.tokens.TokenConstantDouble;
import hr.fer.zemris.java.custom.scripting.tokens.TokenConstantInteger;
import hr.fer.zemris.java.custom.scripting.tokens.TokenFunction;
import hr.fer.zemris.java.custom.scripting.tokens.TokenOperator;
import hr.fer.zemris.java.custom.scripting.tokens.TokenString;
import hr.fer.zemris.java.custom.scripting.tokens.TokenVariable;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Goes through nodes tree of smart script and calls
 * appropriate execution methods for each node.
 * Uses visitor pattern.
 * 
 * @author Teo Toplak
 *
 */
public class SmartScriptEngine {

	/**
	 * document node 
	 */
	DocumentNode documentNode;
	/**
	 * context for wtiting legal output
	 */
	private RequestContext requestContext;
	/**
	 * multistack used for storing variable values
	 * during execution of script
	 */
	private ObjectMultistack multistack = new ObjectMultistack();
	
	
	/**
	 * Constructor using document node, context as arguments.
	 * Initializes multistack
	 * @param documentNode document node
	 * @param requestContext context
	 */
	public SmartScriptEngine(DocumentNode documentNode, RequestContext requestContext) {
		this.documentNode=documentNode;
		this.requestContext=requestContext;
		this.multistack = new ObjectMultistack();
	}
	
	/**
	 * Visitor going through tree of nodes calling
	 * execution methods
	 */
	private INodeVisitor visitor = new INodeVisitor() {
		
		
		@Override
		public void visitTextNode(TextNode node) {
			try {
				requestContext.write(node.getText());
			} catch (IOException e) {
				System.err.println("Writing text from StringToken went wrong!");
				System.exit(1);
			}
			
		}

		@Override
		public void visitForLoopNode(ForLoopNode node) {
			String var = node.getVariable().asText();
			ValueWrapper start = new ValueWrapper(node.getStartExpression().asText());
			ValueWrapper step;
			if(node.getStepExpression() == null) {
				step = new ValueWrapper(1);
			} else {
				step =new ValueWrapper(node.getStepExpression().asText());
			}
			ValueWrapper end =new ValueWrapper(node.getEndExpression().asText());
			multistack.push(var, start);
			
			while(multistack.peek(var).numCompare(end)<=0) {
				for (int i=0; i < node.numberOfChildren(); i++) {
					node.getChild(i).accept(this);
				}
				ValueWrapper value = multistack.pop(var);
				value.increment(step);
				multistack.push(var, value);
			}
			multistack.pop(var);
		}

		@Override
		public void visitEchoNode(EchoNode node) {
			Stack<Object> stack = new Stack<>();
			FunctionsMap map = new FunctionsMap();
			Token[] tokens = node.getTokens();
			for (int i=0; i < tokens.length; i++){
				if(tokens[i] instanceof TokenConstantDouble) {
					TokenConstantDouble t = (TokenConstantDouble) tokens[i];
					stack.push(String.valueOf(t.getValue()));
				}
				if(tokens[i] instanceof TokenConstantInteger) {
					TokenConstantInteger t = (TokenConstantInteger) tokens[i];
					stack.push(String.valueOf(t.getValue()));
				}
				if(tokens[i] instanceof TokenString) {
					TokenString t = (TokenString) tokens[i];
					stack.push(t.getValue());
				}
				if(tokens[i] instanceof TokenVariable) {
					TokenVariable t = (TokenVariable) tokens[i];
					stack.push(multistack.peek(t.getName()));
				}
				if(tokens[i] instanceof TokenOperator) {
					TokenOperator t = (TokenOperator) tokens[i];
					String operator = t.getSymbol();
					ValueWrapper y = new ValueWrapper(stack.pop());
					ValueWrapper x = new ValueWrapper(stack.pop());
					switch (operator) {
					case "+":
						x.increment(y);;
						break;
					case "-":
						x.decrement(y);
						break;
					case "*":
						x.multiply(y);
						break;
					case "/":
						x.divide(y);
						break;
					default:
						System.err.println("Cannot calc with given operator!");
						System.exit(1);
					}
					stack.push(String.valueOf(x.getValue()));
				}
				if(tokens[i] instanceof TokenFunction) {
					TokenFunction t = (TokenFunction) tokens[i];
					map.getMap(t.getName()).exec(multistack, stack, requestContext);
					
				}
			}
			String[] arr = new String[stack.size()];
			for(int i=0;i<arr.length;i++) {
				Object o = stack.pop();
				if(o instanceof String) {
					arr[i] = (String)o;
				} else {
					ValueWrapper w = (ValueWrapper)o;
					arr[i] = String.valueOf(w.getValue());
				}
				
			}
			for(int i=arr.length-1;i>=0;i--) {
				try {
					requestContext.write(arr[i]);
				} catch (IOException e) {
					System.err.println("Cannot convert object to string!");
					System.exit(1);
				}
			}
			
			
		}
		@Override
		public void visitDocumentNode(DocumentNode node) {
			for(int i=0;i<node.numberOfChildren();i++) {
				node.getChild(i).accept(this);
			}
		}
	
		
	};

	/**
	 * Method which starts execution.
	 * Puts visitor to top node (document node)
	 * which walks through all nodes callin appropriate
	 * methods.
	 */
	public void execute() {
	documentNode.accept(visitor);
	}
	
}

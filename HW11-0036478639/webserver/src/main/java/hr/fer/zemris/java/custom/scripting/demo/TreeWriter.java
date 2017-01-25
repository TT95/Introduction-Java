package hr.fer.zemris.java.custom.scripting.demo;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;
import hr.fer.zemris.java.custom.scripting.tokens.Token;

/**
 * Goes through nodes tree of smart script and calls
 * method which returns original node text.
 * Uses visitor pattern.
 * @author Teo Toplak
 *
 */
public class TreeWriter {

	/**
	 * Visitor for this class.
	 * For more information read class javadoc
	 * @author Teo Toplak
	 *
	 */
	private static class WriterVisitor implements INodeVisitor{
		
		@Override
		public void visitDocumentNode(DocumentNode node) {
			for(int i=0;i<node.numberOfChildren();i++) {
				node.getChild(i).accept(this);
			}
	
		}
		
		@Override
		public void visitTextNode(TextNode node) {
			System.out.print(node.getText());

		}

		@Override
		public void visitForLoopNode(ForLoopNode node) {
			ForLoopNode forLoopNode = (ForLoopNode)node;
			System.out.print("{$ FOR " + forLoopNode.getVariable().asText() +" "
					+ forLoopNode.getStartExpression().asText() + " " +
					forLoopNode.getEndExpression().asText());
			if(forLoopNode.getStepExpression() != null) {
				System.out.print(" " + forLoopNode.getStepExpression().asText());
			}
			System.out.print(" $}");
			int numberOfChildren = node.numberOfChildren();
			for (int i=0; i < numberOfChildren; i++) {
				node.getChild(i).accept(this);
			}
			System.out.print("{$ END $}");
		}

		@Override
		public void visitEchoNode(EchoNode node) {
			EchoNode echoNode = (EchoNode)node;
			Token[] tokens = echoNode.getTokens();
			System.out.print("{$= ");
			for (int i=0; i < tokens.length; i++){
				System.out.print(tokens[i].asText()+" ");
			}
			System.out.print("$}");
		}
		
	}
	
	/**
	 * Method called when executing program.
	 * @param args path to smart script
	 */
	public static void main(String[] args) {
		if(args.length!=1) {
			System.err.println("Wrong command line input!");
		}
		
		String docBody="";
		try{
		docBody = new String(
				Files.readAllBytes(Paths.get(args[0])),
				StandardCharsets.UTF_8
				);
		}catch(Exception e){
			System.out.println("Failed to set path to document");
			System.exit(-1);
		}
		SmartScriptParser parser = null;
		try {
			parser = new SmartScriptParser(docBody);
		} catch(SmartScriptParserException e) {
			e.printStackTrace();
		System.out.println("Unable to parse document!");
		System.exit(-1);
		}
		
		WriterVisitor visitor = new WriterVisitor();
		parser.getDocumentNode().accept(visitor);
		// by the time the previous line completes its job, the document should have been written
		// on the standard output
	}
}

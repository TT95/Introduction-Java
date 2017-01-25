package hr.fer.zemris.java.hw2;
import java.nio.file.Files;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import hr.fer.zemris.java.custom.scripting.parser.*;
import hr.fer.zemris.java.custom.scripting.nodes.*;

/**
 * Class made for testing SmartScriptParser by writing content of each node from document model.
 * Class uses text document as input which path is provided in command line argument.
 * Uses recursion createOriginalDocumentBody to write content of each node.
 * Testing is also done by parsing text result from first parser (texts should match).
 * @param commmand-line argument, path to text document used as input.
 * @author Teo Toplak
 *
 */
public class SmartScriptTester {

	/**
	 * Method called upon executing program.
	 * Method creates SmartScriptParser, gets document node, and uses it for writing content of 
	 * nodes on standard output.
	 */
	public static void main(String[] args) {
		
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
		System.out.println("Unable to parse document!");
		System.exit(-1);
		} catch(Exception e) {
			System.out.println("If this line ever executes, you have failed this class!");
			System.exit(-1);
		}
		//getting document node
		DocumentNode document = parser.getDocumentNode();
		//getting content of each node through one string
		String originalDocumentBody = createOriginalDocumentBody(document);
		//writing content
		System.out.println(originalDocumentBody); 
		System.out.println(" ------- Parsing output text made from first parser for testing --------");
		
		//creating parser2 and parsing text from first parser
		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		DocumentNode document2 = parser2.getDocumentNode();
		String originalDocumentBody2 = createOriginalDocumentBody(document2);
		System.out.println(originalDocumentBody2);
		
	}
	
	/**
	 * Method for creating string which represents content of each node
	 * linked to provided document node.
	 * Method using recursion for appending final string.
	 * @param document provided document node
	 * @return string representing content of each node.
	 */
	public static String createOriginalDocumentBody(Node document){
		
		String string="";
		int numberOfChildren=document.numberOfChildren();
		for(int index=0;index<numberOfChildren;index++){
			string += createOriginalDocumentBody(document.getChild(index));
		}
		//since "end" tag is lost during parsing, following lines of code merge it back with \n included
		if(document instanceof ForLoopNode)
			string+="{$END$}";
		return document.getText()+string;
	}
	

}

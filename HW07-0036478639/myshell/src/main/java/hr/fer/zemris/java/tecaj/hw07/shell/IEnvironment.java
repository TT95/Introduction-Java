package hr.fer.zemris.java.tecaj.hw07.shell;

import java.io.IOException;


/**
 * Interface used for user-commands interaction within MyShell program. Allows
 * user to set shell symbols, while also allowing commands to write and read
 * user input. Containing reference to map of commands reachable with commads()
 * method.
 * 
 * @author Teo Toplak
 *
 */
public interface IEnvironment {

	/**
	 * Method which reads line/lines from standard input. If more lines is
	 * written (demanded) in shell MORELINESYMBOL will be removed, and appended
	 * string will be returned.
	 * @return string with command line and its arguments
	 * @throws if there is problem with reading consol input
	 */
	String readLine()throws IOException;
	
	/**
	 * Method for writing on standard output.
	 * @param text text to write
	 * @throws IOException if there is IO problem
	 */
	void write(String text)throws IOException;
	
	/**
	 * Method for writing on standard input (new line).
	 * @param text text to write
	 * @throws IOException if there is IO problem
	 */
	void writeln(String text)throws IOException;
	
	/**
	 * Method which returns iterable collection with shell commands.
	 * @return iterable collection with shell commands
	 */
	Iterable<ShellCommand> commands();
	
	/**
	 * Method returns current MULTILINESYMBOL.
	 * @return current MULTILINESYMBOL
	 */
	Character getMultilineSymbol();
	
	/**
	 * Method for setting new MULTILINESYMBOL.
	 * @param new MULTILINESYMBOL
	 */
	void setMultilineSymbol(Character symbol);
	
	/**
	 * Method returns current PROMPTSYMBOL.
	 * @return current PROMPTSYMBOL
	 */
	Character getPromptSymbol();
	
	/**
	 * Method for setting new PROMPTSYMBOL
	 * @param new PROMPTSYMBOL
	 */
	void setPromptSymbol(Character symbol);
	
	/**
	 * Method returns current MORELINESYMBOL.
	 * @return current MORELINESYMBOL
	 */
	Character getMorelinesSymbol();
	
	/**
	 * Method for setting new MORELINESYMBOL
	 * @param new MORELINESYMBOL
	 */
	void setMorelinesSymbol(Character symbol);

}

package hr.fer.zemris.java.tecaj.hw07.shell;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * Class representing implementatiin of {@link IEnvironment} interface.
 * For more detailed information about this class read interface javadoc.
 * @author Teo Toplak
 *
 */
public class Environment implements IEnvironment{

	/**
	 * Character representing MORELINESYMBOL
	 */
	private char moreLinesSymbol;
	/**
	 * Character representing PROMPTSYMBOL
	 */
	private char promptSymbol;
	/**
	 * Character representing MULTILINESYMBOL
	 */
	private char multiLinesSymbol;
	/**
	 * Map containing shell commands as values, and its names as key.
	 */
	Map<String, ShellCommand> commands;
	
	/**
	 * Constructor accepting map with commands as values, and its names as key.
	 * Sets symbols to its default values.	 * 
	 * @param commands map with commands.
	 */
	public Environment(Map<String, ShellCommand> commands) {
		moreLinesSymbol='\\';
		promptSymbol='>';
		multiLinesSymbol='|';
		this.commands=commands;
	}


	@Override
	public String readLine() throws IOException {
		System.out.print(promptSymbol+" ");
		BufferedReader reader = new BufferedReader( new InputStreamReader(System.in));
		String finalString = reader.readLine();
		while(finalString.endsWith(moreLinesSymbol+"")) {
			System.out.print(multiLinesSymbol+" ");
			finalString = finalString.substring(0,finalString.length()-2);
			finalString+=" "+reader.readLine();
		}
		return finalString;
	}
	
	@Override
	public void write(String text) {
		System.out.print(text);
	}

	@Override
	public void writeln(String text) {
		System.out.println(text);
	}

	@Override
	public Iterable<ShellCommand> commands() {
		return commands.values();
	}

	@Override
	public Character getMultilineSymbol() {
		return multiLinesSymbol;
	}

	@Override
	public void setMultilineSymbol(Character symbol) {
		multiLinesSymbol=symbol;
	}

	@Override
	public Character getPromptSymbol() {
		return promptSymbol;
	}

	@Override
	public void setPromptSymbol(Character symbol) {
		promptSymbol=symbol;
	}

	@Override
	public Character getMorelinesSymbol() {
		return moreLinesSymbol;
	}

	@Override
	public void setMorelinesSymbol(Character symbol) {
		moreLinesSymbol=symbol;
	}
	
}

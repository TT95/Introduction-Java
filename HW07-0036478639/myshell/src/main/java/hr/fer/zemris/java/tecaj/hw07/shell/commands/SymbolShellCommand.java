package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.IEnvironment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

/**
 * Command used for changing shell symbols. Shell symbols: PROMPTSYMBOL,
 * MORELINESSYMBOL, MULTILINESYMBOL. Expects two arguments: symbol to change and
 * char representing new symbol.
 * 
 * @author Teo Toplak
 *
 */
public class SymbolShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(IEnvironment env, String arguments)
			throws IOException {
		
		String[] argumentsArray = arguments.split("\\s+");
		if(argumentsArray.length>2) {
			throw new IOException("Wrong arguments for symbol command!");
		}
		if(argumentsArray.length==2) {
			if(argumentsArray[1].length()!=1) {
				throw new IOException("Symbol should be only one character!");
			}
		}

		char lastChar;
		switch(argumentsArray[0].toUpperCase()) {
		case "PROMPT": 
			lastChar= env.getPromptSymbol();
			if(argumentsArray.length==2) {
				env.setPromptSymbol(argumentsArray[1].charAt(0));
			}						
			break;
		case "MORELINES": 
			lastChar= env.getMorelinesSymbol();
			if(argumentsArray.length==2) {
				env.setMorelinesSymbol(argumentsArray[1].charAt(0));
			}
			break;
		case "MULTILINES": 
			lastChar= env.getMultilineSymbol();
			if(argumentsArray.length==2) {
				env.setMultilineSymbol(argumentsArray[1].charAt(0));
			}			
			break;
		default: throw new IOException("Wrong arguments for symbol command!");
		}
		if(argumentsArray.length==2) {
			env.writeln("Symbol for "+ argumentsArray[0] +" changed from '"
					+ lastChar +"' to '"+ argumentsArray[1] +"'");
		} else {
			env.writeln("Symbol for "+ argumentsArray[0] +" is '"+ lastChar +"'");
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "symbol";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new LinkedList<>();
		list.add("Command used for changing shell symbols.");
		list.add("Shell symbols: PROMPTSYMBOL, MORELINESSYMBOL, MULTILINESYMBOL.");
		list.add("Expects two arguments: symbol to change and char representing new symbol.");
		list = Collections.unmodifiableList(list);
		return list;
	}

}

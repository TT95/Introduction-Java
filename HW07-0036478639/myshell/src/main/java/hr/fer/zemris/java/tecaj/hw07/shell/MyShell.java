package hr.fer.zemris.java.tecaj.hw07.shell;

import hr.fer.zemris.java.tecaj.hw07.shell.commands.*;


import java.util.HashMap;
import java.util.Map;

/**
 * Shell with implemented commands mostly used for working on files. Each line
 * that is not the last line of command must end with MORELINESYMBOL. Line which
 * appends to previous starts with MULTILINESYMBOL automatically. For every new
 * command input shell will write PROMPTSYMBOL. Each of this symbols can be
 * changed within running shell. If wrong arguments are provided shell wont
 * terminate, but will keep running and write appropriate message. Use command
 * help to get info for all commands. Path which contains spaces, must be put
 * into quote, for shell to recognize it as a single arguments.
 * 
 * @author Teo Toplak
 *
 */
public class MyShell {

	/**
	 * Method called when shell is executed. No command line arguments are used.
	 * For more detailed information about how to use this method read class
	 * javadoc.
	 * 
	 * @param args command line arguments (none are used).
	 */
	public static void main(String[] args) {
		
		Map<String, ShellCommand> commands = new HashMap<>();
		commands.put("exit", new ExitShellCommand());
		commands.put("ls", new LsShellCommand());
		commands.put("cat",new CatShellCommand());
		commands.put("charsets",new CharsetsShellCommand());
		commands.put("copy",new CopyShellCommand());
		commands.put("help",new HelpShellCommand());
		commands.put("hexdump",new HexdumpShellCommand());
		commands.put("mkdir",new MkdirShellCommand());
		commands.put("symbol",new SymbolShellCommand());
		commands.put("tree",new TreeShellCommand());
		IEnvironment env = new Environment(commands);
		
		System.out.println("Welcome to MyShell v 1.0");
		
		ShellStatus status=ShellStatus.CONTINUE;
		do{
			try {
				String input=env.readLine();
				String[] array = input.split("\\s+",2);
				ShellCommand command = commands.get(array[0]);
				if(command==null) {
					throw new IllegalArgumentException("Command doesn't exist!");
				}
				if(array.length==1) {
					status = command.executeCommand(env, "");
				} else {
					status = command.executeCommand(env, array[1]);
				}
			} catch(Exception ex) {
				System.out.println(ex.getMessage());
				continue;
			}
		}while(status!=ShellStatus.TERMINATE);
	}
}

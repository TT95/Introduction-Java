package hr.fer.zemris.java.tecaj.hw07.shell;

import java.io.IOException;
import java.util.List;

public interface ShellCommand {

	/**
	 * Method which executes command. Uses given environment for communication with shell.
	 * @param env environment used for communication with shell
	 * @param arguments all arguments after command
	 * @return ShellStatus status which tells shell to terminate or continue
	 * @throws IOException when wrong arguments are provided (or some IO problem)
	 */
	ShellStatus executeCommand(IEnvironment env, String arguments) throws IOException;
	
	/**
	 * Method which returns command name.
	 * @return command name
	 */
	String getCommandName();
	
	/**
	 * Method for getting command description. Returns description through list
	 * with description sentences.
	 * @return list with description sentences
	 */
	List<String> getCommandDescription();
}

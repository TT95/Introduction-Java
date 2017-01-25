package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.IEnvironment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

/**
 * Command exit terminates shell running.
 * @author Teo Toplak
 *
 */
public class ExitShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(IEnvironment env, String arguments) throws IOException {
		if(!arguments.isEmpty()) {
			throw new IOException("Wrong number of arguments for charsets command");
		}
		env.writeln("Shell terminated!");
		return ShellStatus.TERMINATE;
	}

	@Override
	public String getCommandName() {
		return "exit";
	}

	@Override
	public List<String> getCommandDescription() {
		
		List<String> list = new LinkedList<>();
		list.add("Command exit terminates shell running.");
		list = Collections.unmodifiableList(list);
		return list;
	}

}

package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.IEnvironment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

/**
 * The mkdir command takes a single argument: directory name. Creates the
 * appropriate directory structure.
 * 
 * @author Teo Toplak
 *
 */
public class MkdirShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(IEnvironment env, String arguments)
			throws IOException {
		
		if(arguments.isEmpty() || arguments.split("\\s+").length>1) {
			throw new IOException("Illegal arguments in mkdir command!");
		}
		File dir = new File(arguments);
		if(dir.isFile()) {
			throw new IOException("Illegal arguments in mkdir command!");
		}
		dir.mkdirs();
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "mkdir";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new LinkedList<>();
		list.add("The mkdir command takes a single argument: directory name.");
		list.add("Creates the appropriate directory structure.");
		list = Collections.unmodifiableList(list);
		return list;
	}

}

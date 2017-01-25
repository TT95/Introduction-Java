package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.IEnvironment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

/**
 * Command help expects one or none argumnets. If command is provided in
 * argument it will write proper description. If none arguments is provided it
 * will list all command names supported by this shell
 * 
 * @author Teo Toplak
 *
 */
public class HelpShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(IEnvironment env, String arguments)
			throws IOException {
		
		if(arguments.split("\\s+").length>2) {
			throw new IOException("Wrong argument for help command!");
		}
		if(arguments.isEmpty()) {
			Iterator<ShellCommand> it = env.commands().iterator();
			while(it.hasNext()) {
				env.writeln(it.next().getCommandName());
			}
			return ShellStatus.CONTINUE;
		} 
		Iterator<ShellCommand> it = env.commands().iterator();
		while(it.hasNext()) {
			ShellCommand command = it.next();
			if(command.getCommandName().equals(arguments)) {
				List<String> list = command.getCommandDescription();
				for(String s : list) {
					env.writeln(s);
				}
				return ShellStatus.CONTINUE;
			}
		}

		throw new IOException("Wrong argument for help command!");
	}

	@Override
	public String getCommandName() {
		return "help";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new LinkedList<>();
		list.add("Command help expects one or none arguments.");
		list.add("If command is provided in argument it will write proper description.");
		list.add("If none arguments is provided it will list"
				+ " all command names supported by this shell.");
		list = Collections.unmodifiableList(list);
		return list;
	}
 
	
}

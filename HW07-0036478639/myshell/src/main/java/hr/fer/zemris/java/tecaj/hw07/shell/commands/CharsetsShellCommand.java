package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.tecaj.hw07.shell.IEnvironment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

/**
 * Command exit takes no arguments and lists names of supported charsets.
 * @author Teo Toplak
 *
 */
public class CharsetsShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(IEnvironment env, String arguments) throws IOException {
		
		if(!arguments.isEmpty()) {
			throw new IOException("Wrong number of arguments for charsets command");
		}
		Map<String,Charset> map= Charset.availableCharsets();
		//didnt use map.foreach cause exception must be thrown
		for(Map.Entry<String, Charset> entry : map.entrySet()) {
			 env.writeln(entry.getValue().toString());
			}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "charsets";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new LinkedList<>();
		list.add("Command exit takes no arguments and lists names of supported charsets.");
		list = Collections.unmodifiableList(list);
		return list;
	}

}

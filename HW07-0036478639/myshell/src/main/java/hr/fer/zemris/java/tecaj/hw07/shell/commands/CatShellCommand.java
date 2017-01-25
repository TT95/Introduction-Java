package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.IEnvironment;
import hr.fer.zemris.java.tecaj.hw07.shell.PathSupport;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

/**
 * Command cat takes one or two arguments.The first argument is path to
 * some file and is mandatory. The second argument is charset name that should
 * be used to interpret chars from bytes. If not provided, a default platform
 * charset is used This command opens given file and writes its content to
 * console.
 * 
 * @author Teo Toplak
 *
 */
public class CatShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(IEnvironment env, String arguments) throws IOException {
		
		String[] argumentsArray= arguments.split(PathSupport.getRegex());
		Charset charset;
		if(!arguments.isEmpty()) {
			argumentsArray[0]=PathSupport.removeQuotes(argumentsArray[0]);
		}
		if(arguments.isEmpty()) {
			throw new IOException("Wrong number of arguments for cat command!");
		} else if(argumentsArray.length==1) {
			charset=Charset.defaultCharset();
		} else if(argumentsArray.length==2){
			try {
				charset=Charset.forName(argumentsArray[1]);
			} catch (Exception e){
				throw new IOException("Charset is not accepted!");
			}
			
		} else {
			throw new IOException("Wrong number of arguments for cat command!");
		}
		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(new BufferedInputStream(
						Files.newInputStream(Paths.get(argumentsArray[0]),
								StandardOpenOption.READ)),
						charset))) {
			while(true) {
				String line= reader.readLine();
				if(line==null) {
					break;
				}
				env.write(line);
			}
			env.writeln("");
		} catch (Exception e) {
			throw new IOException("Wrong path to file!");
		}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "cat";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new LinkedList<>();
		list.add("Command Command cat takes one or two arguments.");
		list.add("The first argument is path to some file and is mandatory.");
		list.add("The second argument is charset name that should"
				+ " be used to interpret chars from bytes.");
		list.add("If not provided, a default platform charset is used");
		list.add("This command opens given file and writes its content to console.");
		list = Collections.unmodifiableList(list);
		return list;
	}
	

}

package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.IEnvironment;
import hr.fer.zemris.java.tecaj.hw07.shell.PathSupport;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

/**
 * The copy command expects two arguments: source file name and destination file
 * name. Copies file to specified directory/destination file. If other argument
 * is directory, and that directory doesnt exist, it wont be created but instead
 * it will considered as a file with no extension name.
 * 
 * @author Teo Toplak
 *
 */
public class CopyShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(IEnvironment env, String arguments)
			throws IOException {
		String[] argumentsArray= arguments.split(PathSupport.getRegex());
		if(argumentsArray.length>2 || argumentsArray.length==1) {
			throw new IOException("Wrong arguments for copy command");
		}
		
		File source= new File(PathSupport.removeQuotes(argumentsArray[0]));
		File destination = new File(PathSupport.removeQuotes(argumentsArray[1]));
		
		//if source file is directory
		if(source.isDirectory()) {
			throw new IOException("Wrong arguments for copy command");
		}
		//if destination is directory
		if(destination.isDirectory()) {
			if(!destination.exists()) {
				throw new IOException("Destination directory doesnt exist!");
			}
			destination = new File(PathSupport.removeQuotes(argumentsArray[1])
					+"/"+source.getName());
		}
		
		//if file already exists at destination
		if(destination.exists()) {
			env.writeln("Do you want to overwrite existing file "
					+ "at destination path (Y/N)?");
			String answer = env.readLine().toLowerCase();
			if(answer.equals("n")) {
				return ShellStatus.CONTINUE;
			}
		}
		
		try {
			InputStream reader = new FileInputStream(source);
			OutputStream writer = new FileOutputStream(destination);
			
			while(true) {
				byte[] buffer = new byte[1024];
				int numberOfBytes = reader.read(buffer);
				if(numberOfBytes<1) {
					break;
				}
				writer.write(buffer,0,numberOfBytes);
			}
			reader.close();
			writer.close();		
		} catch (Exception e) {
			throw new IOException("Problem with reading/writing files");
		}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "copy";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new LinkedList<>();
		list.add("The copy command expects two arguments: "
				+ "source file name and destination file name.");
		list.add("Copies file to specified directory/destination file.");
		list = Collections.unmodifiableList(list);
		return list;
	}

}

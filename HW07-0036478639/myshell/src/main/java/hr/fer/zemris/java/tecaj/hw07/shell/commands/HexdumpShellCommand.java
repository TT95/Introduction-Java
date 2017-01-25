package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.IEnvironment;
import hr.fer.zemris.java.tecaj.hw07.shell.PathSupport;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

/**
 * Command hexdump produces hex output of a file provided. If character in file
 * is not supported, dot will be written instead (for some characters not in
 * standard subset of characters can have double its value so two or more dots
 * can be expected).
 * 
 * @author Teo Toplak
 *
 */
public class HexdumpShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(IEnvironment env, String arguments)
			throws IOException {
		
		if(arguments.isEmpty() || arguments.split(PathSupport.getRegex()).length>2) {
			throw new IOException("Wrong arguments in hexdump command!");
		}
		
		File file = new File(PathSupport.removeQuotes(arguments));
		if(!file.isFile()) {
			throw new IOException("Wrong arguments in hexdump command!");
		}
		
		InputStream is = Files.newInputStream(file.toPath(), StandardOpenOption.READ);
		int counter=0;
		byte[] b = new byte[16];
		while(true) {
			int numberOfBytes = is.read(b);
			if(numberOfBytes < 1) {
				break;
			}
			String hexFirstPart="";
			String hexSecondPart="";
			String textBuffer = "";
			for(int i=0;i<numberOfBytes;i++) {
				String hex = String.format("%02X ", b[i]);
				if(i < 8) {
					hexFirstPart+=hex;
				} else {
					hexSecondPart+=hex;
				}
				char character;
				//try-catch if char is unsupported
				try {
					character = String.format("%c", b[i]).charAt(0);
				} catch (Exception e) {
					character=0;
				}
				
				int charValue=(int)character;
				if(32 > charValue || charValue > 127) {
					textBuffer+=".";
				} else {
					textBuffer+= ""+character;
				}				
			}
			env.writeln(String.format("%08d: %-24s| %-24s| %s", counter,hexFirstPart
					,hexSecondPart,textBuffer));
			counter+=10;
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "hexdump";
	}

	@Override
	public List<String> getCommandDescription() {
		
		List<String> list = new LinkedList<>();
		list.add("Command hexdump produces hex output of a file provided.");
		list = Collections.unmodifiableList(list);
		return list;
	}

}

package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.IEnvironment;
import hr.fer.zemris.java.tecaj.hw07.shell.PathSupport;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

/**
 * "Command ls takes a single argument – directory – and writes a directory
 * listing containing info about date of creation, size, name and attributes.
 * 
 * @author Teo Toplak
 *
 */
public class LsShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(IEnvironment env, String arguments)
			throws IOException {
		
		if(arguments.isEmpty() || arguments.split(PathSupport.getRegex()).length!=1) {
			throw new IOException("Illegal arguments for command ls");
		}
		
		File dir = new File(PathSupport.removeQuotes(arguments));
		if(!dir.isDirectory()) {
			throw new IOException("Argument for ls should be directory!"); 
		}
		File[] children = dir.listFiles();
		//getting max file size for table creation
		Long maxLong=0L;
		for(File file : children) {
			if(file.length()>maxLong) {
				maxLong=file.length();
			}
		}
		int maxSpaces=maxLong.toString().length();
		for(File file : children) {		
			String date=getDate(file.toPath());
			String atr=getAttributes(file);
			String size=String.valueOf(file.length());
			String name=file.getName();		
			env.writeln(String.format("%s %"+maxSpaces+"s %s %s",atr,size,date,name));
		}
		
		return ShellStatus.CONTINUE;
	}
	private String getAttributes(File file) {
		String atr="";
		if(file.isDirectory()) {
			atr="d";
		} else {
			atr="-";
		} if(file.canRead()) {
			atr+="r";
		} else {
			atr+="-";
		} if(file.canWrite()) {
			atr+="w";
		} else {
			atr+="-";
		} if(file.canExecute()) {
			atr+="x";
		} else {
			atr+="-";
		}
		return atr;
	}
	
	private String getDate(Path p) throws IOException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		BasicFileAttributeView faView = Files.getFileAttributeView(p,
				BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);
		BasicFileAttributes attributes = faView.readAttributes();
		FileTime fileTime = attributes.creationTime();
		return sdf.format(new Date(fileTime.toMillis()));
	}

	@Override
	public String getCommandName() {
		return "ls";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new LinkedList<>();
		list.add("Command ls takes a single argument – directory – "
				+ "and writes a directory listing.");
		list = Collections.unmodifiableList(list);
		return list;
	}

}

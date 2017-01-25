package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.IEnvironment;
import hr.fer.zemris.java.tecaj.hw07.shell.PathSupport;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;


/**
 * The tree command expects a single argument: directory name. Prints a tree.
 * 
 * @author Teo Toplak
 *
 */
public class TreeShellCommand implements ShellCommand {

	
	
	@Override
	public ShellStatus executeCommand(IEnvironment env, String arguments)
			throws IOException {
		
		if(arguments.split(PathSupport.getRegex()).length>1 || arguments.isEmpty()) {
			throw new IOException("Illegal arguments in command tree");
		}
		
		Path root=Paths.get(PathSupport.removeQuotes(arguments));
		if(!root.toFile().isDirectory()) {
			throw new IOException("Argument should be directory!");
		}
		Files.walkFileTree(root, new IndentiraniIspis(env));
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "tree";
	}

	@Override
	public List<String> getCommandDescription() {
		
		List<String> list = new LinkedList<>();
		list.add("The tree command expects a single argument: directory name.");
		list.add("Prints a tree.");
		list = Collections.unmodifiableList(list);
		return list;
	}
	
	private static class IndentiraniIspis implements FileVisitor<Path> {

		private int indentLevel;
		private IEnvironment env;

		public IndentiraniIspis(IEnvironment env) {
			this.env = env;
		}

		@Override
		public FileVisitResult postVisitDirectory(Path arg0, IOException arg1)
				throws IOException {
			indentLevel-=2;
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult preVisitDirectory(Path arg0,
				BasicFileAttributes arg1) throws IOException {
			if(indentLevel==0) {
				env.writeln(arg0.toFile().getName());
			} else {
				env.write(String.format("%"+indentLevel+"s%s%n", "", arg0.getFileName()));
			}
			indentLevel+=2;
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFile(Path arg0, BasicFileAttributes arg1)
				throws IOException {
			env.write(String.format("%"+indentLevel+"s%s%n", "", arg0.getFileName()));
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFileFailed(Path arg0, IOException arg1)
				throws IOException {
			return FileVisitResult.CONTINUE;
		}
		
	}


}

package hr.fer.zemris.java.hw10.jnotepadpp;

import java.nio.file.Path;

/**
 * Class storing info for {@link JNotepadPP}.
 * Stores info for each opened text file in notepad.
 * Stores info about path of document and (un)changed state.
 * In notepad this class is stored in list which length is always
 *  equal to currently opened documents.
 * @author Teo Toplak
 *
 */
public class TabInfo {
	/**
	 * path to document
	 */
	Path path;
	/**
	 * true if document has been modified
	 */
	boolean changed;
	/**
	 * Csontructor taking path to document and 
	 * state of document
	 * @param path path to document 
	 * @param changed true if document has been modified
	 */
	public TabInfo(Path path, boolean changed) {
		super();
		this.path = path;
		this.changed = changed;
	}
	/**
	 * returns path of document
	 * @return the path
	 */
	public Path getPath() {
		return path;
	}
	/**
	 * Sets path of document
	 * @param path the path to set
	 */
	public void setPath(Path path) {
		this.path = path;
	}
	/**
	 * Returns state of document
	 * @return the changed
	 */
	public boolean isChanged() {
		return changed;
	}
	/**
	 * Sets state of document
	 * @param changed the changed to set
	 */
	public void setChanged(boolean changed) {
		this.changed = changed;
	}
	
	
}

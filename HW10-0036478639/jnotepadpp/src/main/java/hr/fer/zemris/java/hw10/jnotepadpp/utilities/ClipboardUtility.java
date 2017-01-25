package hr.fer.zemris.java.hw10.jnotepadpp.utilities;

import hr.fer.zemris.java.hw10.jnotepadpp.actions.PasteSelectedPartAction;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

/**
 * Utility class used for working with clipboard.
 * Used in actions like {@link PasteSelectedPartAction}
 * where working with system clipboard is needed.
 * @author Teo Toplak
 *
 */
public class ClipboardUtility {
	
	/**
	 * Sets given string to clipboard
	 * @param string string for clipboard
	 */
	public static void toClipboard(String string) {
		StringSelection stringSelection = new StringSelection(string);
		Clipboard clpbrd = Toolkit.getDefaultToolkit ().getSystemClipboard ();
		clpbrd.setContents (stringSelection, null);
	}
}

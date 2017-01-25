package hr.fer.zemris.java.hw10.jnotepadpp.utilities;

import hr.fer.zemris.java.hw10.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw10.jnotepadpp.strategy.text.ITextOperation;

import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

/**
 * Context in terms of strategy pattern.
 * Used for working with text through caret position
 * @author Teo Toplak
 *
 */
public class TextUtility {

	/**
	 * Uses strategy on given text.
	 * @param notepad notepad
	 * @param strategy concrete strategy
	 */
	public static void utility(JNotepadPP notepad,ITextOperation strategy) {
		if(notepad.getTabsList().isEmpty()) {
			return;
		}
		JTextArea text = notepad.getCurrTabText();
		Document doc = text.getDocument();
		int start = Math.min(text.getCaret().getDot(), text
				.getCaret().getMark());
		int length = Math.max(text.getCaret().getDot(), text
				.getCaret().getMark())
				- start;
		try {
			strategy.operation(start, length,doc);
		} catch (BadLocationException ignorable) { }
	}
}

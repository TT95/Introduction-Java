package hr.fer.zemris.java.hw10.jnotepadpp.strategy.text;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

/**
 * Strategy for working with text.
 * Used for working with text through caret positions, where
 * utility provides caret position (offset) and length of selected text
 * @author Teo Toplak
 *
 */
public interface ITextOperation {
	
	/**
	 * Using start offset of selected text, length of selected text and
	 * reference to currently opened {@link Document} object operates to
	 * execute action given.
	 * @param start start of selected text
	 * @param length length of selected text
	 * @param doc {@link Document} object 
	 * @throws BadLocationException if offset is outside legal set of
	 * numbers
	 */
	public void operation(int start, int length, Document doc) throws BadLocationException;
}

package hr.fer.zemris.java.hw10.jnotepadpp.actions;

import hr.fer.zemris.java.hw10.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw10.jnotepadpp.localization.ILocalizationProvider;
import hr.fer.zemris.java.hw10.jnotepadpp.utilities.SortUtility;
import hr.fer.zemris.java.hw10.jnotepadpp.utilities.TextUtility;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JTextArea;
/**
 * Class representing DescendingAction.
 * Accessible through menu/toolbar in {@link JNotepadPP}.
 * @author Teo Toplak
 *
 */
public class DescendingAction extends AbstractAction{

	private static final long serialVersionUID = 1L;
	private JNotepadPP notepad;
	/**
	 * Constructor taking key string, {@link ILocalizationProvider} localization provider
	 * for notepad and notepad reference. Also can be used for setting mnenonic and
	 * accelerator key
	 * @param key string key
	 * @param lp localization provider
	 * @param notepad notepad reference
	 */
	public DescendingAction(String key, ILocalizationProvider lp,JNotepadPP notepad) {
		this.notepad=notepad;
		putValue(NAME, lp.getString(key));
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E);
		
		lp.addLocalizationListener(()-> {
			putValue(NAME, lp.getString(key));
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		TextUtility.utility(notepad,(start,length,doc)-> {
			JTextArea text = notepad.getCurrTabText();
			int startLine = text.getLineStartOffset(text.getLineOfOffset(start));
			int endLine = text.getLineEndOffset(text.getLineOfOffset(start+length));
			String fin = SortUtility.sort(doc.getText(startLine, endLine-startLine), false);
			doc.remove(startLine, endLine-startLine);
			doc.insertString(startLine, fin, null);
		});
	}
}
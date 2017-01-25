package hr.fer.zemris.java.hw10.jnotepadpp.actions;

import hr.fer.zemris.java.hw10.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw10.jnotepadpp.TabInfo;
import hr.fer.zemris.java.hw10.jnotepadpp.listeners.NotepadListeners;
import hr.fer.zemris.java.hw10.jnotepadpp.localization.ILocalizationProvider;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
/**
 * Class representing NewBlankAction.
 * Accessible through menu/toolbar in {@link JNotepadPP}.
 * @author Teo Toplak
 *
 */
public class NewBlankAction extends AbstractAction{
	private static final long serialVersionUID = 1L;
	private JNotepadPP notepad;
	String key;
	/**
	 * Constructor taking key string, {@link ILocalizationProvider} localization provider
	 * for notepad and notepad reference. Also can be used for setting mnenonic and
	 * accelerator key
	 * @param key string key
	 * @param lp localization provider
	 * @param notepad notepad reference
	 */
	public NewBlankAction(String key, ILocalizationProvider lp,JNotepadPP notepad) {
		this.notepad=notepad;
		this.key = key;
		putValue(NAME, lp.getString(key));
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);
		
		lp.addLocalizationListener(()-> {
			putValue(NAME, lp.getString(key));
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JTextArea text = new JTextArea();
		text.setFont(notepad.getDefaultFont());
		text.addCaretListener(NotepadListeners.getCaretListener(notepad));
		text.getDocument().addDocumentListener(NotepadListeners.getDocumentListener(notepad));
		notepad.getPane().addTab(
				notepad.getFlp().getString(key) + " "
						+ (numberOfBlanks(key) + 1), notepad.getIconUnchanged(),
				new JScrollPane(text), notepad.getFlp().getString(key));
		notepad.getTabsList().add(new TabInfo(null, true));
	}
	
	/**
	 * Counts number of blank opened documents.
	 * Used to set number of new blank document.
	 * @param key string key
	 * @return count of blank documents opened
	 */
	public int numberOfBlanks(String key) {
		int count=0;
		for(int i=0;i<notepad.getTabsList().size();i++) {
			if(notepad.getPane().getTitleAt(i).contains(notepad.getFlp().getString(key))) {
				count++;
			}
		}
		return count;
	}
}

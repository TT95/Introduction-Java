package hr.fer.zemris.java.hw10.jnotepadpp.actions;

import hr.fer.zemris.java.hw10.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw10.jnotepadpp.localization.ILocalizationProvider;
import hr.fer.zemris.java.hw10.jnotepadpp.utilities.TextUtility;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
/**
 * Class representing DeletingSelectedPartAction.
 * Accessible through menu/toolbar in {@link JNotepadPP}.
 * @author Teo Toplak
 *
 */
public class DeleteSelectedPartAction extends AbstractAction{
	
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
	public DeleteSelectedPartAction(String key, ILocalizationProvider lp,JNotepadPP notepad) {
		this.notepad=notepad;
		putValue(NAME, lp.getString(key));
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_D);
		
		lp.addLocalizationListener(()-> {
			putValue(NAME, lp.getString(key));
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		TextUtility.utility(notepad,(start,length,doc)-> {
			doc.remove(start, length);
			notepad.setCurrTabChange(true);
		});
	}
}

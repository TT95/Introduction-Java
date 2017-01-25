package hr.fer.zemris.java.hw10.jnotepadpp.actions;

import hr.fer.zemris.java.hw10.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw10.jnotepadpp.localization.ILocalizationProvider;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
/**
 * Class representing CloseTabAction.
 * Accessible through menu/toolbar in {@link JNotepadPP}.
 * @author Teo Toplak
 *
 */
public class CloseTabAction extends AbstractAction{

	private static final long serialVersionUID = 1L;
	private JNotepadPP notepad;
	private SaveDocumentAction saveAction;
	/**
	 * Constructor taking key string, {@link ILocalizationProvider} localization provider
	 * for notepad and notepad reference. Also can be used for setting mnenonic and
	 * accelerator key
	 * @param key string key
	 * @param lp localization provider
	 * @param notepad notepad reference
	 * @param saveAction save action for notepad
	 */
	public CloseTabAction(String key, ILocalizationProvider lp,JNotepadPP notepad,
			SaveDocumentAction saveAction) {
		this.notepad=notepad;
		this.saveAction=saveAction;
		putValue(NAME, lp.getString(key));
		putValue(Action.MNEMONIC_KEY,KeyEvent.VK_L);
		
		lp.addLocalizationListener(()-> {
			putValue(NAME, lp.getString(key));
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(notepad.getTabsList().isEmpty()) {
			return;
		}
		int res=0;
		if(notepad.getCurrTabChange()) {
			res = JOptionPane.showConfirmDialog(notepad, 
					"Save "+ notepad.getPane().getTitleAt(notepad.getPane().getSelectedIndex()) +" ?",
					"Save", JOptionPane.YES_NO_CANCEL_OPTION);
		}
		if(res == JOptionPane.YES_OPTION) {
			((ActionListener) saveAction).actionPerformed(new ActionEvent(this, 0, ""));
		} else if(res == JOptionPane.CANCEL_OPTION){
			return;
		}
		int i = notepad.getPane().getSelectedIndex();
		notepad.getPane().remove(i);
		notepad.getTabsList().remove(i);
	}
}


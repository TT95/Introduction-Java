package hr.fer.zemris.java.hw10.jnotepadpp.actions;

import hr.fer.zemris.java.hw10.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw10.jnotepadpp.localization.ILocalizationProvider;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
/**
 * Class representing StatisticalInfoAction.
 * Accessible through menu/toolbar in {@link JNotepadPP}.
 * @author Teo Toplak
 *
 */
public class StatisticalInfoAction extends AbstractAction{
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
	public StatisticalInfoAction(String key, ILocalizationProvider lp,JNotepadPP notepad) {
		this.notepad=notepad;
		putValue(NAME, lp.getString(key));
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_I);
		
		lp.addLocalizationListener(()-> {
			putValue(NAME, lp.getString(key));
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//if no tabs
		if(notepad.getPane().getSelectedIndex()==-1) {
			return;
		}
		JTextArea textArea = notepad.getCurrTabText();
		String text = textArea.getText();
		char[] arr = text.toCharArray();
		int numOfChars=0;
		int numOfNonBlankChars=0;
		int numOfLines=1;
		for(char c : arr) {
			numOfChars++;
			if(c == '\n' || c == '\r') {
				numOfLines++;
			} else if(Character.isLetterOrDigit(c)){
				numOfNonBlankChars++;
			}
		}
		JOptionPane.showMessageDialog(notepad, "Your document has "
				+ numOfChars + " number of characters, "
				+ numOfNonBlankChars
				+ " number of non-blank characters and " + numOfLines
				+ " lines.");
	}
}

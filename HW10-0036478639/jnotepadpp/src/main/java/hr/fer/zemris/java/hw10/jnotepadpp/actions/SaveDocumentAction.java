package hr.fer.zemris.java.hw10.jnotepadpp.actions;

import hr.fer.zemris.java.hw10.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw10.jnotepadpp.localization.ILocalizationProvider;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import javax.swing.event.ChangeEvent;
/**
 * Class representing SaveDocumentAction.
 * Accessible through menu/toolbar in {@link JNotepadPP}.
 * @author Teo Toplak
 *
 */
public class SaveDocumentAction extends AbstractAction{
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
	public SaveDocumentAction(String key, ILocalizationProvider lp,JNotepadPP notepad) {
		this.notepad=notepad;
		putValue(NAME, lp.getString(key));
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		
		lp.addLocalizationListener(()-> {
			putValue(NAME, lp.getString(key));
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(notepad.getTabsList().isEmpty()) {
			return;
		}
		Path openedFilePath = notepad.getCurrTabPath();
		if (openedFilePath == null) {
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle("Save document");
			if (fc.showSaveDialog(notepad) != JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(notepad,
						"Save failed", "Message",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			Path file = fc.getSelectedFile().toPath();
			if (Files.exists(file)) {
				int rez = JOptionPane
						.showConfirmDialog(
								notepad,
								"Selected file ("
										+ file
										+ ") already exists. Replace file?",
								"Warning", JOptionPane.YES_NO_OPTION,
								JOptionPane.WARNING_MESSAGE);
				if (rez != JOptionPane.YES_OPTION) {
					return;
				}
			}
			openedFilePath = file;
			if(notepad.getCurrTabPath()==null) {
				notepad.getPane().setTitleAt(notepad.getPane().getSelectedIndex(), 
						file.getFileName().toString());
				notepad.getPane().setToolTipTextAt(notepad.getPane().getSelectedIndex(), file.toString());
			}
			notepad.setCurrTabPath(file);
		}
		try {
			Files.write(openedFilePath,
					notepad.getCurrTabText().getText().getBytes(StandardCharsets.UTF_8));
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(notepad,
					"Error while writing file " + openedFilePath
							+ ": " + e1.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		notepad.setCurrTabChange(false);
		//for triggering listener to change window name
		notepad.getPane().getChangeListeners()[0].stateChanged(new ChangeEvent(this));
	}
}
